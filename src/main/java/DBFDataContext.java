import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFUtils;
import org.apache.metamodel.*;
import org.apache.metamodel.data.*;
import org.apache.metamodel.query.SelectItem;
import org.apache.metamodel.schema.*;
import org.apache.metamodel.util.FileResource;
import org.apache.metamodel.util.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DBFDataContext extends QueryPostprocessDataContext implements UpdateableDataContext {
    private static final Logger logger = LoggerFactory.getLogger(DBFDataContext.class);
    private final Resource resource;
    private final File dbfFile;
    //private  DBFReader dbfReader;

    public DBFDataContext(File file){
        if(file == null){
            throw new IllegalArgumentException("file can not be null");
        }
        dbfFile = file;
        resource = new FileResource(file);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //dbfReader = new DBFReader(inputStream);


    }

    public Resource getResource(){
        return resource;
    }

    public File getDbfFile(){
        return dbfFile;
    }

    public DBFField[] getDBFField(){
        DBFReader dbfReader = getDBFReader();
        if(dbfReader == null){
            return getDBFFieldByNull();
        }
        int fieldCount = dbfReader.getFieldCount();
        DBFField[] fields = new DBFField[fieldCount];
        for (int i = 0; i < fieldCount; ++i){
            fields[i] = dbfReader.getField(i);
        }
        DBFUtils.close(dbfReader);
        //dbfReader.close();
        return fields;
    }

    /**
     * 向空文件中写入数据时获取field属性
     *
     * @return
     */
    public DBFField[] getDBFFieldByNull(){
        return null;
    }

    public Object [][] getObject(){
        DBFReader dbfReader = getDBFReader();
        if (dbfReader == null){
            return null;
        }
        Object object[][] = new Object[dbfReader.getRecordCount()][dbfReader.getFieldCount()];
        for (int i = 0; i < dbfReader.getRecordCount(); ++i){
            object[i] = dbfReader.nextRecord();
        }
        return object;
    }

    @Override
    protected Schema getMainSchema() throws MetaModelException {
//        String schemaName = getMainSchemaName();
//        int index = Math.max(schemaName.lastIndexOf('\\'),schemaName.lastIndexOf('/'));
//        if(index != -1){
//            schemaName = schemaName.substring(index + 1);
//        }
//        MutableSchema schema = new MutableSchema(schemaName);
//        MutableTable table = new MutableTable(schemaName.substring(0,schemaName.length()-4),TableType.TABLE,schema);
//        schema.addTable(table);
//        DBFReader dbfReader = getDBFReader();
//        int length = dbfReader.getFieldCount();
//        for(int i = 0; i < length; ++i){
//            DBFField field = dbfReader.getField(i);
//            MutableColumn column = new MutableColumn(field.getName());
//            DBFDataType type = field.getType();
//            ColumnType columnType = ColumnType.VARCHAR;
//            if (type == DBFDataType.DOUBLE){
//                columnType = ColumnType.DOUBLE;
//            }else if (type == DBFDataType.DATE){
//                columnType = ColumnType.DATE;
//            } else if (type == DBFDataType.CHARACTER){
//                columnType = ColumnType.CHAR;
//            } else if (type == DBFDataType.LOGICAL){
//                columnType = ColumnType.BOOLEAN;
//            } else if (type == DBFDataType.MEMO){
//                columnType = ColumnType.VARCHAR;
//            }
//            column.setTable(table);
//            column.setType(columnType);
//            column.setColumnNumber(i);
//            column.setNativeType(field.getType().toString());
//            column.setColumnSize(field.getLength());
//            table.addColumn(column);
//        }
        //dbfReader.close();
        DBFSchema schema = new DBFSchema(getMainSchemaName(),this);
        if(resource.isExists()){
            schema.setDbfTable(new DBFTable(schema, resource.getName()));
        }
        return schema;

    }

    @Override
    protected String getMainSchemaName() throws MetaModelException {
        return resource.getName();

    }

    @Override
    protected DataSet materializeMainSchemaTable(Table table, List<Column> list, int i) {
        DBFReader dbfReader = getDBFReader();
        synchronized (dbfReader){
            int rowCount = 0;
            final List<SelectItem> selectItems = list.stream().map(SelectItem::new).collect(Collectors.toList());
            final List<Row> listValue = new LinkedList<>();
            final DataSetHeader header = new CachingDataSetHeader(selectItems);
            int max = dbfReader.getRecordCount();
            if(i != -1){
                max = i;
            }
            while(rowCount < max){
                Object[] dbfObject = new Object[list.size()];
                Object[] objects = dbfReader.nextRecord();
                for (int j = 0; j < list.size(); ++j){
                    int fieldNumber = list.get(j).getColumnNumber();
                    dbfObject[j] = objects[fieldNumber];
                }
                rowCount++;
                listValue.add(new DefaultRow(header,dbfObject));
            }
            return new InMemoryDataSet(header,listValue);
        }

    }

    @Override
    protected DataSet materializeMainSchemaTable(Table table, List<Column> list, int first, int last){
        final int rows;
        if (first == 1) {
            rows = last;
        } else {
            rows = last + (first - 1);
        }
        DataSet dataSet = materializeMainSchemaTable(table, list, rows);
        if (first > 1) {
            dataSet = new FirstRowDataSet(dataSet, first);
        }
        return dataSet;

    }

    protected DBFReader getDBFReader(){
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(dbfFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            long size = ((FileInputStream) inputStream).getChannel().size();
            if(size == 0){
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DBFReader(inputStream);
    }

    public ColumnType getDBFColumnType(int index){
        //getDBFReader();
        return null;
    }

    @Override
    protected void finalize() throws Throwable{
        super.finalize();
    }


    /**
     * Submits an {@link UpdateScript} for execution on the {@link DataContext}.
     * <p>
     * Since implementations of the {@link DataContext} vary quite a lot, there
     * is no golden rule as to how an update script will be executed. But the
     * implementors should strive towards handling an {@link UpdateScript} as a
     * single transactional change to the data store.
     *
     * @param update the update script to execute
     * @return a summary of the updates performed
     */
    @Override
    public UpdateSummary executeUpdate(UpdateScript update) {
        final DBFUpdateCallback updateCallback = new DBFUpdateCallback(this);
        update.run(updateCallback);
        return updateCallback.getUpdateSummary();
    }
}
