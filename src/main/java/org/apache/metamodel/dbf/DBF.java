package org.apache.metamodel.dbf;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class DBF {
    public static void main(String[] args) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("E:\\code\\java\\sd\\src\\main\\resources\\myconnect.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            URI uri = new URI(properties.get("resource").toString());
            System.out.println(uri.getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


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
//            if (className.equals("sx.User")){
//                User user = new User();
//
//            }else if(className.equals("sx.Stu")){
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
//        DBFField fields[] = new DBFField[2];
//        fields[0] = new DBFField();
//        fields[0].setType(DBFDataType.NUMERIC);
//        fields[0].setName("id");
//        fields[0].setLength(15);
//        fields[1] = new DBFField();
//        fields[1].setName("name");
//        fields[1].setType(DBFDataType.CHARACTER);
//        fields[1].setLength(15);
//
//        DBFWriter writer = null;
//        try {
//            writer = new DBFWriter(new FileOutputStream(new File("e:\\emp.dbf")));
//            writer.setFields(fields);
//            Object objects[] = new Object[2];
//            objects[0] = 2;
//            objects[1] = "tom";
//            writer.addRecord(objects);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } finally {
//            DBFUtils.close(writer);
//        }
//
//        //DBFReader dbfReader = new DBFReader()
    }
}
