package proxy;

import java.lang.reflect.Proxy;

public class ProxyMapper {

    public static <T> T getMapper(Class clazz){
        /**
         * 创建一个代理对象，代理对象的方法会执行OrmInvocationHandler中的invoke方法
         */
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new OrmInvocationHandler(clazz));
    }
}
