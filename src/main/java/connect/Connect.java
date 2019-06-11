package connect;

import org.apache.metamodel.DataContext;
import org.apache.metamodel.UpdateableDataContext;
import org.apache.metamodel.dbf.DBFDataContext;
import org.apache.metamodel.factory.DataContextFactoryRegistryImpl;
import org.apache.metamodel.factory.DataContextPropertiesImpl;
import org.apache.metamodel.jdbc.JdbcDataContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connect{

    public Connect(){

    }

    public static DataContext createConnect(){
        return createConnect("/myconnect.properties");
    }

    public static DataContext createConnect(String filePath){
        Properties properties = getProperties(filePath);
        DataContextPropertiesImpl dataContextProperties = new DataContextPropertiesImpl(properties);
        //DataContext myDataContext =  org.apache.metamodel.dbf.DBFDataContextFactory.create();
        DataContext myDataContext = DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(dataContextProperties);
        return myDataContext;
    }

    public static UpdateableDataContext createUpdateContext(){
        return createUpdateContext("/myconnect.properties");
    }

    public static UpdateableDataContext createUpdateContext(String filePath){
        Properties properties = getProperties(filePath);
        if (properties.getProperty("type").equals("jdbc")){
            String url = properties.getProperty("url");
            String drive = properties.getProperty("driver-class");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            try {
                Class.forName(drive);
                Connection conn = DriverManager.getConnection(url,username,password);
                UpdateableDataContext updateableDataContext = new JdbcDataContext(conn);
                return updateableDataContext;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if(properties.getProperty("type").equals("dbf")){
            UpdateableDataContext updateableDataContext = new DBFDataContext(new File("e:/film.dbf"));
            return updateableDataContext;
        }
        else {
            throw new IllegalArgumentException("not support update data type!");
        }
        return null;
    }

    private static Properties getProperties(String filePath)
    {
        InputStream in = Connect.class.getResourceAsStream(filePath);
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
