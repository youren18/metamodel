package proxy;

import annotation.Query;
import connect.Connect;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AnnoationHandler {
    public static Object handlerQuery(Method method, Object[] objects){
        Query query = method.getDeclaredAnnotation(Query.class);
        String selectSQL = query.query();
        System.out.println(selectSQL);
        DataContext dataContext = (new Connect()).createConnect();
        Schema schema = dataContext.getDefaultSchema();
        String tableName = method.getAnnotation(annotation.Table.class).tableName();
        Table table = schema.getTableByName(tableName);
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
}
