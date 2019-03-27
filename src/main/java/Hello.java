import com.mongodb.DB;
import com.mongodb.Mongo;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.UpdateableDataContext;
import org.apache.metamodel.csv.CsvDataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.excel.ExcelDataContext;
import org.apache.metamodel.insert.InsertInto;
import org.apache.metamodel.json.JsonDataContext;
import org.apache.metamodel.mongodb.mongo2.MongoDbDataContext;
import org.apache.metamodel.query.FunctionType;
import org.apache.metamodel.query.Query;
import org.apache.metamodel.salesforce.SalesforceDataContext;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.apache.metamodel.xml.XmlSaxDataContext;

import java.io.File;


public class Hello {
    public static void main(String[] args) {
//        DataContext saleDataContext = new SalesforceDataContext("","");
//        DataContext mongDataContext = new MongoDbDataContext(new DB(new Mongo(),""));
//        DataContext xmlDataContext = new XmlSaxDataContext(new File(""));
//        DataContext jsonDataContext = new JsonDataContext(new File(""));
       // DataContext csvDataContext = new CsvDataContext(new File(""));
//        DataContext excelDataContext = new ExcelDataContext(new File(""));
        UpdateableDataContext myDataContext = new DBFDataContext(new File("E:\\gh.dbf"));
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
        Schema schema = myDataContext.getDefaultSchema();

        Table table = schema.getTable(0);
                myDataContext.executeUpdate(
                new InsertInto(table).value(0,"A").value(1,
                        "").value(2,100).value(3,"239382").value(4,
                "A").value(5,1).value(6,0).value(7,"A").value(8,
                        "A").value(9,"A").value(10,1).value(11,
                        1).value(12,"A").value(13,"A").value(14,"A"));
//        Query query = myDataContext.query().from(table).select(FunctionType.COUNT,"CJJG").where("CJSL").eq(200).toQuery();
//        System.out.println(query.toString());
//
//        DataSet ds = myDataContext.executeQuery(query);
//        while(ds.next()){
//            Row row = ds.getRow();
//            //String d = (String)row.getValue(0);
//
//            long d = (long) row.getValue(0);
//            System.out.println(d);
//        }
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
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();
        session.save(user);
        transaction.commit();
        session.close();
        */