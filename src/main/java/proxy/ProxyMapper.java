package proxy;

import java.lang.reflect.Proxy;

public class ProxyMapper {

    public static <T> T getMapper(Class clazz){
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class[]{clazz},new OrmInvocationHandler(clazz));
    }
}
