package session;

import connect.Config;
import executor.Executor;
import proxy.OrmInvocationHandler;
import proxy.ProxyMapper;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Session{

    Config config;
    Executor executor;

    public Session(String resource){
        config = new Config(resource);
        executor = new Executor();
    }

    public <T> T query(Method method, Object[] args){

        return executor.query(method, args);
    }

    public int insert(Method method, Object[] args){

        return executor.insert(method, args);
    }

    public int update(Method method, Object[] args){

        return executor.update(method, args);
    }

    public int delete(Method method, Object[] args){

        return executor.delete(method, args);
    }

    public <T> T getMapper(Class clazz){

        ProxyMapper proxyMapper = new ProxyMapper(this);
        return proxyMapper.getMapper(clazz);
    }

}
