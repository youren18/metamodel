package cache;

public interface Cache {
    String getId();


    void putObject(Object key, Object value);

    /**
     *
     * @param key
     * @return 存储的值
     */
    Object getObject(Object key);

    /**
     *
     * @param key
     * @return 删除的object
     */
    Object deleteObject(Object key);

    //清空
    void clear();

    //获取大小
    int getSize();
}
