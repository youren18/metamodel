package Util;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MybatisUtil {
    public static SqlSessionFactory getFactory(){
        String source = "conf.xml";
        InputStream in = MybatisUtil.class.getClassLoader().getResourceAsStream(source);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        return factory;
    }
}
