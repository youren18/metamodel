package executor;

import cache.Cache;
import cache.CacheKey;
import cache.PerCache;

import java.lang.reflect.Method;

public class Executor {

    private PerCache cache;
    private boolean close;
    private AnnoationHandler annoationHandler;

    public Executor(){
        cache = new PerCache("cache");
        this.close = false;
        annoationHandler = new AnnoationHandler();
    }

    public Object query(Method method, Object[] args){
        if (close){
            throw new IllegalStateException("executor has been closed");
        }
        String selectSQL = method.getDeclaredAnnotation(annotation.Query.class).value();//得到注解sql语句
        CacheKey cacheKey = createCacheKey(selectSQL, args);
        Object result = cache.getObject(cacheKey);
        if (result != null){
            return result;
        }
        Class<?> returnType = method.getReturnType();//得到返回类型
        if (!returnType.getName().contains("java.util.List")) {
            Object object = annoationHandler.handlerQueryOne(method, args);
            cache.putObject(cacheKey, object);
            return object;
        }
        Object object = annoationHandler.handlerQueryList(method, args);
        cache.putObject(cacheKey, object);
        return object;
    }

    public int insert(Method method, Object[] args){
        cache.clear();
        return annoationHandler.handleInsertOne(method, args);
    }

    public int update(Method method, Object[] args){
        cache.clear();
        return annoationHandler.handleUpdate(method, args);
    }

    public int delete(Method method, Object[] args){
        cache.clear();
        return annoationHandler.handleDelete(method, args);
    }

    public void close(){
        close = true;
        cache.clear();

    }

    public boolean isClosed(){
        return close;
    }

    public CacheKey createCacheKey(String sql, Object[] objects){
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(sql);
        cacheKey.update(objects);

        return cacheKey;
    }

}
