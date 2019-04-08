package connect;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextPropertiesImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Connect{

    public Connect(){

    }

    public DataContext createConnect(){
        return createConnect("E:\\code\\java\\sd\\src\\main\\resources\\myconnect.properties");
    }

    public DataContext createConnect(String filePath){
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataContextPropertiesImpl dataContextProperties = new DataContextPropertiesImpl(properties);
        //DataContext myDataContext =  org.apache.metamodel.dbf.DBFDataContextFactory.create();
        DataContext myDataContext = DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(dataContextProperties);
        return myDataContext;
    }
}
