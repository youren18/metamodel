package proxy;

import annotation.Column;
import annotation.Delete;
import annotation.Query;
import annotation.Update;
import connect.Connect;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.UpdateCallback;
import org.apache.metamodel.UpdateScript;
import org.apache.metamodel.UpdateableDataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.delete.RowDeletionBuilder;
import org.apache.metamodel.insert.InsertInto;
import org.apache.metamodel.insert.RowInsertionBuilder;
import org.apache.metamodel.query.builder.SatisfiedSelectBuilder;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.apache.metamodel.update.RowUpdationBuilder;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AnnoationHandler {

    /**
     * 处理查询函数，首先通过connect获取datacontext，然后获取table
     * 根据查询注解中的字符串和参数，构建查询的sql语句，
     * 得到查询的结果后，根据返回值的类型动态构造对象，将查询的结果返回
     * @param method 处理的方法
     * @param objects 查询时使用的参数
     * @return
     */
    @Nullable
    public static Object handlerQuery(Method method, Object[] objects){
        Query query = method.getDeclaredAnnotation(Query.class);
        String selectSQL = query.query();//得到sql语句
        if (selectSQL.isEmpty()){
            throw new IllegalArgumentException("expect sql in @Query");
        }
        DataContext dataContext = Connect.createConnect();
        Schema schema = dataContext.getDefaultSchema();
        Table table = getTable(method,schema);

        SatisfiedSelectBuilder queryBuild = dataContext.query().from(table).select("*");
        List<String> selectColumn = getWhereColumnName(selectSQL);//得到where需要的列名
        if (selectColumn.size() != objects.length){
            throw new IllegalArgumentException("unfit sql and argument " + selectSQL);
        }
        //将列名与参数一一对应插入metamodel的查询sql中
        for (int i = 0; i < selectColumn.size(); ++i){
            queryBuild.where(selectColumn.get(i)).eq(objects[i]);
        }
        org.apache.metamodel.query.Query query1 = queryBuild.toQuery();
        Class<?> returnType = method.getReturnType();//得到返回类型
        DataSet set = dataContext.executeQuery(query1);

        List<Object> returnObject = getResult(set, method);
        if (!returnType.getName().contains("java.util.List")){
            return returnObject.get(0);
        }
        return returnObject;

    }

    private static List<Object> getResult(DataSet set, Method method){
        try {
            Class<?> returnType = method.getReturnType();
            Type type = method.getGenericReturnType();
            if(type instanceof ParameterizedType){
                Type[] actualTypeArguments = ((ParameterizedType)type).getActualTypeArguments();
                for (Type type1 : actualTypeArguments) {
                    System.out.println(type1);
                }

            }

            List<Object> result = new LinkedList<>();

            Object object = returnType.newInstance();

            while(set.next()){
                Field[] declaredField = returnType.getDeclaredFields();
                int i = 0;
                for (Field field : declaredField){
                    String fieldName = field.getName();

                    Object fieldValue = set.getRow().getValue(i);
                    field.setAccessible(true);
                    field.set(object,fieldValue);
                    i++;
                }
            }
            result.add(object);
           return result;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param method
     * @param objects
     * @return
     */
    public static int handleInsertOne(Method method, Object[] objects){
        UpdateableDataContext updateableDataContext = Connect.createUpdateContext();
        Schema schema = updateableDataContext.getDefaultSchema();
        Table table = getTable(method, schema);
        updateableDataContext.executeUpdate(new UpdateScript() {
            @Override
            public void run(UpdateCallback callback) {
                RowInsertionBuilder builder = callback.insertInto(table);
                Field[] fields = objects[0].getClass().getDeclaredFields();
                for (int j = 0; j < fields.length; ++j){
                    try {
                        String name = fields[j].getDeclaredAnnotation(Column.class).columnValue();
                        fields[j].setAccessible(true);
                        Object val = fields[j].get(objects[0]);
                        builder.value(name,val);
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
     * @param method 具体的方法
     * @param objects 删除sql中用到的参数
     * @return
     */
    public static int handleDelete(Method method, Object[] objects){
        UpdateableDataContext dataContext = Connect.createUpdateContext();
        Schema schema = dataContext.getDefaultSchema();
        Table table = getTable(method,schema);
        dataContext.executeUpdate(new UpdateScript() {
            @Override
            public void run(UpdateCallback callback) {
                RowDeletionBuilder builder = callback.deleteFrom(table);//构造删除的sql
                String sql = method.getDeclaredAnnotation(Delete.class).delete();
                if (sql.isEmpty()){
                    throw new IllegalArgumentException("expect a sql in @Delete");
                }
                List<String> columnName = getWhereColumnName(sql);
                if (columnName.size() != objects.length){
                    throw new IllegalArgumentException("unfit sql and argument for delete sql！");
                }
                for (int i = 0; i < columnName.size(); ++i){
                    builder.where(columnName.get(i)).eq(objects[i]);//依次将列名与参数对应起来
                }
                builder.execute();
            }
        });
        return 0;
    }

    /**
     *处理sql语句update操作，可以根据where中的条件进行更新数据，
     * 仅支持等于的条件
     * @param method 具体的方法
     * @param objects 参数
     * @return
     */
    public static int handleUpdate(Method method, Object[] objects){
        UpdateableDataContext dataContext = Connect.createUpdateContext();
        Schema schema = dataContext.getDefaultSchema();
        Table table = getTable(method,schema);
        dataContext.executeUpdate(new UpdateScript() {
            @Override
            public void run(UpdateCallback callback) {
                RowUpdationBuilder builder = callback.update(table);
                String sql = method.getDeclaredAnnotation(Update.class).update();
                List<String> setColumn = getSetColumnName(sql);
                List<String> whereColumn = getWhereColumnName(sql);
                if(setColumn.size() + whereColumn.size() != objects.length){
                    throw new IllegalArgumentException("unfit sql and argument for @update");
                }
                for (int i = 0; i < setColumn.size(); ++i){
                    builder.value(setColumn.get(i), objects[i]);
                }

                for (int i = 0; i < whereColumn.size(); ++i){
                    builder.where(whereColumn.get(i)).eq(objects[i+setColumn.size()]);
                }


                builder.execute();
            }
        });

        return 0;
    }

    /**
     * 获取table，如果通过注解指定了表名，就通过表名获取table，
     * 如果没有指定表名，就获取默认的index为0的table
     * @param method 要处理的方法
     * @param schema metamodel的schema
     * @return 获取的table
     */
    private static Table getTable(Method method, Schema schema){
        Table table = null;
        if (method.isAnnotationPresent(annotation.Table.class)){
            String tableName = method.getAnnotation(annotation.Table.class).tableName();
            table = schema.getTableByName(tableName);
        } else {
            table = schema.getTable(0);
        }
        return table;
    }

    /**
     *
     * @param sql 传入delete注解中的sql语句
     * @return 提取出delete中涉及到的列名并返回
     */
    private static List<String> getWhereColumnName(String sql){
        int indexOfWhere = sql.indexOf("where");
        if (indexOfWhere == -1) {
            throw new IllegalArgumentException("unexpect sql " + sql);
        }
        indexOfWhere += 5;
        sql = sql.substring(indexOfWhere);

        int indexOfEq = sql.indexOf("=");
        if (indexOfEq == -1){
            throw new IllegalArgumentException("illegal sql " + sql);
        }

        List<String> result = new ArrayList<>();
        result.add(sql.substring(0,indexOfEq).trim());
        int indexOfAnd = sql.indexOf("and");
        while(indexOfAnd != -1){
            sql = sql.substring(indexOfAnd + 3);
            indexOfEq = sql.indexOf("=");
            if (indexOfEq == -1){
                throw new IllegalArgumentException("illegal sql " + sql);
            }
            result.add(sql.substring(0,indexOfEq).trim());
            indexOfAnd = sql.indexOf("and");
        }

        return result;
    }

    /**
     * 提取出sql语句中set的列名
     * @param sql
     * @return
     */
    private static List<String> getSetColumnName(String sql){
        int indexOfSet = sql.indexOf("set") + 3;
        int indexOfWhere = sql.indexOf("where");
        if(indexOfSet == -1){
            throw new IllegalArgumentException("expect set in " + sql);
        }
        if (indexOfSet < indexOfWhere){
            sql = sql.substring(indexOfSet, indexOfWhere);
        } else {
            sql = sql.substring(indexOfSet);
        }

        int indexOfEq = sql.indexOf("=");
        if (indexOfEq == -1){
            throw new IllegalArgumentException("expect = in" + sql);
        }
        List<String> result = new ArrayList<>();
        result.add(sql.substring(0,indexOfEq).trim());
        int indexOfComma = sql.indexOf(",");

        while(indexOfComma != -1){
            sql = sql.substring(indexOfComma + 1);
            indexOfEq = sql.indexOf("=");
            result.add(sql.substring(0,indexOfEq).trim());
            indexOfComma = sql.indexOf(",");
        }
        return result;
    }


}