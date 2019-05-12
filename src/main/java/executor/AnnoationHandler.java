package executor;

import annotation.Column;
import annotation.Delete;
import annotation.Insert;
import annotation.Update;
import connect.Connect;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.UpdateCallback;
import org.apache.metamodel.UpdateScript;
import org.apache.metamodel.UpdateableDataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.delete.RowDeletionBuilder;
import org.apache.metamodel.insert.RowInsertionBuilder;
import org.apache.metamodel.query.Query;
import org.apache.metamodel.query.builder.SatisfiedSelectBuilder;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.apache.metamodel.update.RowUpdationBuilder;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class AnnoationHandler {

    /**
     * 处理查询函数，首先通过connect获取datacontext，然后获取table
     * 根据查询注解中的字符串和参数，构建查询的sql语句，
     * 得到查询的结果后，根据返回值的类型动态构造对象，将查询的结果返回
     *
     * @param method  处理的方法
     * @param objects 查询时使用的参数
     * @return
     */

    public <T> T handlerQueryOne(Method method, Object[] objects) {
        List<T> result = handlerQueryList(method, objects);

        return result.get(0);
    }


    @Nullable
    public <T> List<T> handlerQueryList(Method method, Object[] objects) {
        String selectSQL = method.getDeclaredAnnotation(annotation.Query.class).value();//得到注解sql语句
        if (selectSQL.isEmpty()) {
            throw new IllegalArgumentException("expect sql in @Query");
        }
        SqlUtil sqlUtil = new SqlUtil(selectSQL);//解析sql
        List<String> tables = sqlUtil.getTables();//得到表名

        DataContext dataContext = Connect.createConnect();
        Schema schema = dataContext.getDefaultSchema();
        Table table = schema.getTableByName(tables.get(0));
        if (table == null){
            table = schema.getTable(0);
        }
        List<String> selectColumn = sqlUtil.getWhereColumnNames();//得到where需要的列名
        List<String> operator = sqlUtil.getOperator();

        SatisfiedSelectBuilder queryBuild = dataContext.query().from(table).select("*");

        if (selectColumn.size() != objects.length) {
            throw new IllegalArgumentException("unfit sql and argument " + selectSQL);
        }
        //将列名与参数一一对应插入metamodel的查询sql中
        for (int i = 0; i < selectColumn.size(); ++i) {
            if (operator.get(i).equals("=")){
                queryBuild.where(selectColumn.get(i)).eq(objects[i]);
            } else if (operator.get(i).equals(">")) {
                queryBuild.where(selectColumn.get(i)).gt(objects[i]);
            } else if (operator.get(i).equals("<")){
                queryBuild.where(selectColumn.get(i)).lt(objects[i]);
            }

        }
        Query query = queryBuild.toQuery();

        DataSet set = dataContext.executeQuery(query);

        List<T> returnObject = getResult(set, method, table);

        return returnObject;
    }

    /**
     * 将查询的结果变为具体的对象
     *
     * @param set    查询操作的结果
     * @param method 查询的具体方法
     * @param table  查询涉及到的表
     * @return 具体的结果的对象构成的list
     */
    private <T> List<T> getResult(DataSet set, Method method, Table table) {
        try {
            Type genericReturnType = method.getGenericReturnType();
            Type type = null;
            if (genericReturnType instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) genericReturnType).getActualTypeArguments();
                type = actualTypeArguments[0];
                //System.out.println(type1);
            }

            List<T> result = new LinkedList<>();
            //Object object = returnType.newInstance();
            if (type == null) {
                type = method.getReturnType();
            }

            while (set.next()) {
                Object object = Class.forName(String.valueOf(type).substring(6)).newInstance();
                Field[] declaredField = object.getClass().getDeclaredFields();
                for (Field field : declaredField) {

                    //System.out.println(fieldName);
                    String columnName = field.getDeclaredAnnotation(Column.class).value();
                    org.apache.metamodel.schema.Column column = table.getColumnByName(columnName);
                    Object fieldValue = set.getRow().getValue(column);
                    field.setAccessible(true);

                    field.set(object, fieldValue);
                }
                result.add( (T) object);
            }
            return result;
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 处理插入操作，可以一次插入一个对象
     * @param method  具体的方法
     * @param objects 插入的对象
     * @return
     */
    public int handleInsertOne(Method method, Object[] objects) {
        UpdateableDataContext updateableDataContext = Connect.createUpdateContext();
        Schema schema = updateableDataContext.getDefaultSchema();
        SqlUtil sqlUtil = new SqlUtil(method.getDeclaredAnnotation(Insert.class).value());

        Table table = schema.getTableByName(sqlUtil.getTables().get(0));
        updateableDataContext.executeUpdate(new UpdateScript() {
            @Override
            public void run(UpdateCallback callback) {
                RowInsertionBuilder builder = callback.insertInto(table);
                Field[] fields = objects[0].getClass().getDeclaredFields();//得到插入对象的属性
                for (int j = 0; j < fields.length; ++j) {
                    try {
                        String name = fields[j].getDeclaredAnnotation(Column.class).value();//属性对应的列名
                        fields[j].setAccessible(true);
                        Object val = fields[j].get(objects[0]);//得到具体的值
                        builder.value(name, val);//插入metamodel语句中
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                builder.execute();
            }
        });
        return 0;
    }

    /**
     * 实现删除数据功能，现在只支持根据等号删除
     *
     * @param method  具体的方法
     * @param objects 删除sql中用到的参数
     * @return
     */
    public int handleDelete(Method method, Object[] objects) {
        UpdateableDataContext dataContext = Connect.createUpdateContext();
        Schema schema = dataContext.getDefaultSchema();
        SqlUtil sqlUtil = new SqlUtil(method.getDeclaredAnnotation(Delete.class).value());
        Table table = schema.getTableByName(sqlUtil.getTables().get(0));
        dataContext.executeUpdate(new UpdateScript() {
            @Override
            public void run(UpdateCallback callback) {
                RowDeletionBuilder builder = callback.deleteFrom(table);//构造删除的sql
                String sql = method.getDeclaredAnnotation(Delete.class).value();
                if (sql.isEmpty()) {
                    throw new IllegalArgumentException("expect a sql in @Delete");
                }
                List<String> columnName = sqlUtil.getWhereColumnNames();
                if (columnName.size() != objects.length) {
                    throw new IllegalArgumentException("unfit sql and argument for delete sql！");
                }
                for (int i = 0; i < columnName.size(); ++i) {
                    builder.where(columnName.get(i)).eq(objects[i]);//依次将列名与参数对应起来
                }
                builder.execute();
            }
        });
        return 0;
    }

    /**
     * 处理sql语句update操作，可以根据where中的条件进行更新数据，
     * 仅支持等于的条件
     *
     * @param method  具体的方法
     * @param objects 参数
     * @return
     */
    public int handleUpdate(Method method, Object[] objects) {
        UpdateableDataContext dataContext = Connect.createUpdateContext();
        Schema schema = dataContext.getDefaultSchema();
        SqlUtil sqlUtil = new SqlUtil(method.getDeclaredAnnotation(Update.class).value());
        Table table = schema.getTableByName(sqlUtil.getTables().get(0));
        dataContext.executeUpdate(new UpdateScript() {
            @Override
            public void run(UpdateCallback callback) {
                RowUpdationBuilder builder = callback.update(table);
                String sql = method.getDeclaredAnnotation(Update.class).value();
                List<String> setColumn = sqlUtil.getSetColumnNames();
                List<String> whereColumn = sqlUtil.getWhereColumnNames();
                if (setColumn.size() + whereColumn.size() != objects.length) {
                    throw new IllegalArgumentException("unfit sql and argument for @update");
                }
                for (int i = 0; i < setColumn.size(); ++i) {
                    builder.value(setColumn.get(i), objects[i]);
                }

                for (int i = 0; i < whereColumn.size(); ++i) {
                    builder.where(whereColumn.get(i)).eq(objects[i + setColumn.size()]);
                }

                builder.execute();
            }
        });
        return 0;
    }

}
