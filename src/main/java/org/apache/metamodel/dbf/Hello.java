package org.apache.metamodel.dbf;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;
import connect.Connect;
import entity.Stu;
import mapper.Mapper;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.query.Query;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import proxy.ProxyMapper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class Hello {
    public static void main(String args[]) throws IOException {

        // let us create field definitions first
        // we will go for 3 fields

        DBFField[] fields = new DBFField[3];

        fields[0] = new DBFField();
        fields[0].setName("emp_code");
        fields[0].setType(DBFDataType.CHARACTER);
        fields[0].setLength(10);

        fields[1] = new DBFField();
        fields[1].setName("emp_name");
        fields[1].setType(DBFDataType.CHARACTER);
        fields[1].setLength(20);

        fields[2] = new DBFField();
        fields[2].setName("salary");
        fields[2].setType(DBFDataType.NUMERIC);
        fields[2].setLength(12);
        fields[2].setDecimalCount(2);

        DBFWriter writer = new DBFWriter(new FileOutputStream(args[0]));
        writer.setFields(fields);

        // now populate DBFWriter

        Object rowData[] = new Object[3];
        rowData[0] = "1000";
        rowData[1] = "John";
        rowData[2] = new Double(5000.00);

        writer.addRecord(rowData);

        rowData = new Object[3];
        rowData[0] = "1001";
        rowData[1] = "Lalit";
        rowData[2] = new Double(3400.00);

        writer.addRecord(rowData);

        rowData = new Object[3];
        rowData[0] = "1002";
        rowData[1] = "Rohit";
        rowData[2] = new Double(7350.00);

        writer.addRecord(rowData);

        // write to file
        writer.close();
    }










    //public static void main(String[] args) {

//        Mapper mapper = ProxyMapper.getMapper(Mapper.class);
//        Stu stus = mapper.findone(2);
//        System.out.println(stus.toString());

//        DataContext myDataContext = new Connect().createConnect("E:\\code\\java\\sd\\src\\main\\resources\\myconnect.properties");
//        Schema schema = myDataContext.getDefaultSchema();
//        Table table = schema.getTable(0);
//
//        Query query = myDataContext.query().from(table).select("*").where("id").eq(2).toQuery();
//
//        System.out.println(query.toString());
//
//        DataSet ds = myDataContext.executeQuery(query);
//        while(ds.next()){
//            Row row = ds.getRow();
//            Object[] objects = row.getValues();
//            for (Object o : objects){
//                System.out.println(o);
//            }
//        }
//        ds.close();

//        DataContext saleDataContext = new SalesforceDataContext("","");
//        DataContext mongDataContext = new MongoDbDataContext(new DB(new Mongo(),""));
//        DataContext xmlDataContext = new XmlSaxDataContext(new File(""));
//        DataContext jsonDataContext = new JsonDataContext(new File(""));
//        DataContext csvDataContext = new CsvDataContext(new File(""));
        //DataContext excelDataContext = new ExcelDataContext(new File(""));
 //       UpdateableDataContext myDataContext = new org.apache.metamodel.dbf.DBFDataContext(new File("E:\\gh.dbf"));
        /*
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("E:\\code\\java\\sd\\src\\main\\resources\\myconnect.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataContextPropertiesImpl dataContextProperties = new DataContextPropertiesImpl(properties);
        DataContext mysqlDataContext = DataContextFactoryRegistryImpl.getDefaultInstance().createDataContext(dataContextProperties);
        */
//        Schema schema = myDataContext.getDefaultSchema();
//        Table table = schema.getTable(0);
//                myDataContext.executeUpdate(
//                        new Update(table).where("CJSL").eq(11).value(0,"11111")
                        //new DeleteFrom(table).where("CJSL").eq(200)
//                new InsertInto(table).value(0,"A").value(1,
//                        "").value(2,100).value(3,"239382").value(4,
//                "A").value(5,1).value(6,0).value(7,"A").value(8,
//                        "A").value(9,"A").value(10,1).value(11,
//                        1).value(12,"A").value(13,"A").value(14,"A")
//                        );
//        Query query = myDataContext.query().from(table).select(FunctionType.COUNT,"CJJG").where("CJSL").eq(200).toQuery();
//        System.out.println(query.toString());
//
//        DataSet ds = myDataContext.executeQuery(query);
//        while(ds.next()){
//            Row row = ds.getRow();
//            String d = (String)row.getValue(0);
//
//            long d = (long) row.getValue(0);
//            System.out.println(d);
//        }
//        ds.close();
        /*
        query = myDataContext.query().from(table).select("CJJG").where("CJSL").eq(1200).toQuery();
        System.out.println(query.toString());
        ds = myDataContext.executeQuery(query);
        while(ds.next()){
            Row row = ds.getRow();
            //String d = (String)row.getValue(0);
            BigDecimal d = (BigDecimal) row.getValue(0);
            System.out.println(d);
        }
        */
        //System.out.println("hello");
        //File file = new File("e:\\1.xls");
        //DataContext dataContext = DataContextFactory.createExcelDataContext(file);
  //  }
}

//        Schema schema = dataContext.getDefaultSchema();
//        List<Table> tables = schema.getTables();
//        Table table = tables.get(0);
//        Column n1 = table.getColumnByName("科目代码");
//        Column n2 = table.getColumnByName("日期");
//        Query query = dataContext.query().from(table).select(n2).where(n1).eq("10020101").toQuery();
//        System.out.println(query.toString());
//        DataSet dataSet = dataContext.executeQuery(query);
//
//        while(dataSet.next()){
//            Row row = dataSet.getRow();
//            String dd = (String)row.getValue(n2);
//            System.out.println(dd);
//        }
 /*
        User user = new User();
        user.setName("hello");
        Configuration configuration = new Configuration();
        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(user);
        transaction.commit();
        session.close();
        */
