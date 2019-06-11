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
    public static void main(String[] args) throws IOException {
        //String sql = "delete from stu";



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
        DBFField fields[] = new DBFField[6];
        fields[0] = new DBFField();
        fields[0].setType(DBFDataType.NUMERIC);
        fields[0].setName("fid");
        fields[0].setLength(15);

        fields[1] = new DBFField();
        fields[1].setName("fname");
        fields[1].setType(DBFDataType.CHARACTER);
        fields[1].setLength(25);

        fields[2] = new DBFField();
        fields[2].setName("ftype");
        fields[2].setType(DBFDataType.CHARACTER);
        fields[2].setLength(25);

        fields[3] = new DBFField();
        fields[3].setName("dname");
        fields[3].setType(DBFDataType.CHARACTER);
        fields[3].setLength(25);

        fields[4] = new DBFField();
        fields[4].setName("length");
        fields[4].setType(DBFDataType.NUMERIC);
        fields[4].setLength(25);

        fields[5] = new DBFField();
        fields[5].setName("grade");
        fields[5].setType(DBFDataType.NUMERIC);
        fields[5].setLength(25);

        DBFWriter writer = new DBFWriter(new FileOutputStream("e:\\film.dbf"));
        writer.setCharactersetName("GBK");
        writer.setFields(fields);
            Object objects[] = new Object[6];
            objects[0] = 1;
            objects[1] = "hello";
            objects[2] = "type";
            objects[3] = "hav";
            objects[4] = 115;
            objects[5] = 64;
            writer.addRecord(objects);

            objects = new Object[6];
            objects[0] = 2;
            objects[1] = "战神纪";
            objects[2] = "奇幻";
            objects[3] = "哈斯朝鲁";
            objects[4] = 119;
            objects[5] = 36;
            writer.addRecord(objects);

            objects = new Object[6];
            objects[0] = 3;
            objects[1] = "湮灭";
            objects[2] = "奇幻";
            objects[3] = "亚历克斯·嘉兰";
            objects[4] = 112;
            objects[5] = 73;
            writer.addRecord(objects);

            objects = new Object[6];
            objects[0] = 4;
            objects[1] = "heio";
            objects[2] = "tyu";
            objects[3] = "rpy";
            objects[4] = 141;
            objects[5] = 70;
            writer.addRecord(objects);
            writer.close();

        //DBFReader dbfReader = new DBFReader()
    }
}
