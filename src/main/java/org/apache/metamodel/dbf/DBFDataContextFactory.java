package org.apache.metamodel.dbf;

import org.apache.metamodel.ConnectionException;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.factory.*;

import java.io.File;

public class DBFDataContextFactory extends AbstractDataContextFactory {

    /**
     * Gets the "type" value for the {@link DataContext}s that this factory produces. By convention these are lower-case
     * terms such as "csv" or "jdbc", and separated by dash in case it requires multiple terms, e.g. "fixed-width".
     *
     * @return
     */
    @Override
    protected String getType() {
        return "dbf";
    }

    /**
     * 工厂类，用于调用dbfdatacontext函数，实例化dbfdatacontext函数，
     * 可以被serviceloader加载，根据配置文件直接创建
     * @param properties
     * @param resourceFactoryRegistry
     * @return
     * @throws UnsupportedDataContextPropertiesException
     * @throws ConnectionException
     */
    @Override
    public  DataContext create(DataContextProperties properties, ResourceFactoryRegistry resourceFactoryRegistry) throws UnsupportedDataContextPropertiesException, ConnectionException {
        assert accepts(properties, resourceFactoryRegistry);
        String path = properties.getResourceProperties().getUri().getPath();
        return new DBFDataContext(new File(path));
    }
}
