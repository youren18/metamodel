package proxy;

import annotation.Delete;
import annotation.Insert;
import annotation.Query;
import annotation.Update;
import executor.AnnoationHandler;
import session.Session;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class OrmInvocationHandler implements InvocationHandler {
    private Object object;
    Session session;
    public OrmInvocationHandler(Object object, Session session){
        this.session = session;
        this.object = object;
    }
    /**

     * @param proxy  表示动态代理的目标对象
     * @param method 代表要执行的方法
     * @param args   方法的参数数组

     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //已经实现的类，不做处理
        if(Object.class.equals(method.getDeclaringClass())){
            return method.invoke(proxy, args);
        }
        if(method.isAnnotationPresent(Query.class)){//对于查询注解，调用处理查询函数
            Query query = method.getDeclaredAnnotation(Query.class);
            if(query != null)
                return session.query(method, args);
            return null;
        } else if (method.isAnnotationPresent(Insert.class)){//根据插入注解调用插入处理函数
            return session.insert(method, args);
        } else if (method.isAnnotationPresent(Delete.class)){//根据删除注解调用删除处理函数
            return session.delete(method, args);
        } else if (method.isAnnotationPresent(Update.class)){//根据更新注解调用更新处理函数
            return session.update(method, args);
        }

        return null;
    }
}
