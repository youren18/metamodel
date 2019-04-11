package proxy;

import annotation.Delete;
import annotation.Insert;
import annotation.Query;
import annotation.Update;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

public class OrmInvocationHandler implements InvocationHandler {
    private Object object;
    public OrmInvocationHandler(Object object){
        this.object = object;
    }
    /**
     * Processes a method invocation on a proxy instance and returns
     * the result.  This method will be invoked on an invocation handler
     * when a method is invoked on a proxy instance that it is
     * associated with.
     *
     * @param proxy  表示动态代理的目标对象
     * @param method 代表要执行的方法
     * @param args   方法的参数数组
     * @return the value to return from the method invocation on the
     * proxy instance.  If the declared return type of the interface
     * method is a primitive type, then the value returned by
     * this method must be an instance of the corresponding primitive
     * wrapper class; otherwise, it must be a type assignable to the
     * declared return type.  If the value returned by this method is
     * {@code null} and the interface method's return type is
     * primitive, then a {@code NullPointerException} will be
     * thrown by the method invocation on the proxy instance.  If the
     * value returned by this method is otherwise not compatible with
     * the interface method's declared return type as described above,
     * a {@code ClassCastException} will be thrown by the method
     * invocation on the proxy instance.
     * @throws Throwable the exception to throw from the method
     *                   invocation on the proxy instance.  The exception's type must be
     *                   assignable either to any of the exception types declared in the
     *                   {@code throws} clause of the interface method or to the
     *                   unchecked exception types {@code java.lang.RuntimeException}
     *                   or {@code java.lang.Error}.  If a checked exception is
     *                   thrown by this method that is not assignable to any of the
     *                   exception types declared in the {@code throws} clause of
     *                   the interface method, then an
     *                   {@link UndeclaredThrowableException} containing the
     *                   exception that was thrown by this method will be thrown by the
     *                   method invocation on the proxy instance.
     * @see UndeclaredThrowableException
     *
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
                return AnnoationHandler.handlerQuery(method, args);
            return null;
        } else if (method.isAnnotationPresent(Insert.class)){//根据插入注解调用插入处理函数
            return AnnoationHandler.handleInsertOne(method, args);
        } else if (method.isAnnotationPresent(Delete.class)){//根据删除注解调用删除处理函数
            return AnnoationHandler.handleDelete(method, args);
        } else if (method.isAnnotationPresent(Update.class)){//根据更新注解调用更新处理函数
            return AnnoationHandler.handleUpdate(method, args);
        }

        return null;
    }
}
