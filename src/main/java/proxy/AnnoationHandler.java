package proxy;

import annotation.Column;
import annotation.Query;
import connect.Connect;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.UpdateCallback;
import org.apache.metamodel.UpdateScript;
import org.apache.metamodel.UpdateableDataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.insert.InsertInto;
import org.apache.metamodel.insert.RowInsertionBuilder;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
        String selectSQL = query.query();
        System.out.println(selectSQL);
        DataContext dataContext = Connect.createConnect();
        Schema schema = dataContext.getDefaultSchema();
        Table table = getTable(method,schema);

        org.apache.metamodel.query.Query queryw = dataContext.query().from(table).select("*").where("id").eq((Integer)objects[0]).toQuery();
        System.out.println(queryw.toString());
        Class<?> returnType = method.getReturnType();
        try {
            Object object = returnType.newInstance();

            DataSet set = dataContext.executeQuery(queryw);
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
            return object;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

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

    public static int handleDelete(Method method, Object[] objects){

        return 0;
    }

    public static int handleUpdate(Method method, Object[] objects){

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


}
