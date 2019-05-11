package executor;

import cache.Cache;
import cache.CacheKey;
import cache.PerCache;

import java.lang.reflect.Method;

public class Executor {

    private PerCache cache;
    private boolean close;


    public Executor(){
        cache = new PerCache("cache");
        this.close = false;
    }

    public <T> T query(Method method, Object[] args){
        String selectSQL = method.getDeclaredAnnotation(annotation.Query.class).value();//得到注解sql语句

        return null;
    }

    public int insert(Method method, Object[] args){

        return 0;
    }

    public int update(Method method, Object[] args){

        return 0;
    }

    public int delete(Method method, Object[] args){

        return 0;
    }

    public void close(){

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
