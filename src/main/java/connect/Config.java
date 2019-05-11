package connect;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class Config {
    private static final String PRO_USERNAME = "username";
    private static final String PRO_PASSWARD = "password";
    private static final String PRO_TYPE = "type";
    private static final String PRO_URL = "url";
    private static final String PRO_DRIVER = "driver";
    private static final String PRO_RESOURCE = "resource";

    private String resource;
    private final Map<String, Object> map;

    public Config(Properties properties){
        this();
        //final Set<String> propertyNames = properties.stringPropertyNames();
        update(properties);
    }

    public Config(String resource){
        this();
        this.resource = resource;
        Properties properties = getProperties(resource);
        update(properties);
    }




    public Config(){
       this(new HashMap<String, Object>());
    }

    public Config(Map<String, Object> map){
        resource = null;
        this.map = map;
    }

    public String getString(String key){
        Object object = map.get(key);
        if(object == null){
            return null;
        }
        return object.toString();
    }

    private void update(Properties properties){

    }

    private Properties getProperties(String filePath)
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

    public String getType(){
        return getString(PRO_TYPE);
    }

    public String getUrl() {
        return getString(PRO_URL);
    }

    public String getDriver(){
        return getString(PRO_DRIVER);
    }

    public String getUsername(){
        return getString(PRO_USERNAME);
    }

    public String getPassward(){
        return getString(PRO_PASSWARD);
    }

    public String getResource(){
        return getString(PRO_RESOURCE);
    }
}
