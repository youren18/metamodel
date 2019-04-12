package org.apache.metamodel.dbf;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFUtils;
import com.linuxense.javadbf.DBFWriter;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBF {
    public static void main(String[] args) {
        String sql = "delete from stu";


//        Properties properties = new Properties();
//        try {
//            properties.load(new FileInputStream("E:\\code\\java\\sd\\src\\main\\resources\\myconnect.properties"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            URI uri = new URI(properties.get("resource").toString());
//            System.out.println(uri.getPath());
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }


//
//        Scanner scanner = new Scanner(System.in);
//        String s = null;
//        while(!(s = scanner.next()).isEmpty()){
//            Properties properties = new Properties();
//            try {
//                properties.load(new FileInputStream("src\\main\\resources\\myconnect.properties"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            String className = properties.getProperty("name");
//            System.out.println(className);
//            if (className.equals("entity.User")){
//                User user = new User();
//
//            }else if(className.equals("entity.Stu")){
//                Stu stu = new Stu();
//            }
//
//            try {
//                Class c = Class.forName(className);
//                Constructor con = c.getConstructor();
//                Object obj = con.newInstance();
//
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//        }
//
//        File file = new File("e:\\emp.dbf");
//        FileInputStream in = null;
//        try {
//            in = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        DBFField fields[] = new DBFField[3];
//        fields[0] = new DBFField();
//        fields[0].setType(DBFDataType.NUMERIC);
//        fields[0].setName("id");
//        fields[0].setLength(15);
//        fields[1] = new DBFField();
//        fields[1].setName("name");
//        fields[1].setType(DBFDataType.CHARACTER);
//        fields[1].setLength(15);
//        fields[2] = new DBFField();
//        fields[2].setName("age");
//        fields[2].setType(DBFDataType.NUMERIC);
//        fields[2].setLength(15);
//        DBFWriter writer = null;
//        try {
//            writer = new DBFWriter(new FileOutputStream(new File("e:\\emp.dbf")));
//            writer.setFields(fields);
//            Object objects[] = new Object[3];
//            objects[0] = 2;
//            objects[1] = "tom";
//            objects[2] = 3;
//            writer.addRecord(objects);
//            Object objectss[] = new Object[3];
//            objectss[0] = 12;
//            objectss[1] = "bigtom";
//            objectss[2] = 13;
//            writer.addRecord(objectss);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            DBFUtils.close(writer);
//        }
////
//        //DBFReader dbfReader = new DBFReader()
    }
}
