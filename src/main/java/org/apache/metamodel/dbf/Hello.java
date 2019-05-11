package org.apache.metamodel.dbf;

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

import java.util.List;


public class Hello {
    public static void main(String[] args) {

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
        System.out.println("hello");
        //File file = new File("e:\\1.xls");
        //DataContext dataContext = DataContextFactory.createExcelDataContext(file);
    }
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