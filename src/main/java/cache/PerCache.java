package cache;

import java.util.HashMap;
import java.util.Map;

public class PerCache implements Cache {

    private String id;

    private Map<Object, Object> cache = new HashMap<Object, Object>();

    public PerCache(String id){
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object key, Object value) {
        cache.put(key, value);
    }

    @Override
    public Object getObject(Object key) {
        return cache.get(key);
    }

    @Override
    public Object deleteObject(Object key) {
        return cache.remove(key);
    }

    @Override
    public void clear() {
        cache.clear();
    }

    @Override
    public int getSize() {
        return cache.size();
    }

    @Override
    public boolean equals(Object o){
        if(getId() == null){
            throw new IllegalArgumentException("cache id can not be null");
        }
        if(this == o){
            return true;
        }
        if (!(o instanceof Cache)) {
            return false;
        }

        Cache otherCache = (Cache) o;
        return getId().equals(otherCache.getId());
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            throw new IllegalArgumentException("cache id can not be null");
        }
        return getId().hashCode();
    }
}
