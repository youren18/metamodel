import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import org.apache.metamodel.MetaModelException;
import org.apache.metamodel.QueryPostprocessDataContext;
import org.apache.metamodel.data.*;
import org.apache.metamodel.query.FilterItem;
import org.apache.metamodel.query.SelectItem;
import org.apache.metamodel.schema.*;
import org.apache.metamodel.util.FileResource;
import org.apache.metamodel.util.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class NewQuery extends QueryPostprocessDataContext{
    private static final Logger logger = LoggerFactory.getLogger(NewQuery.class);
    private final Resource resource;
    private final File dbfFile;
    private  DBFReader dbfReader;

    
    public NewQuery(File file){
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
        dbfReader = new DBFReader(inputStream);


    }

    @Override
    protected Schema getMainSchema() throws MetaModelException {
        String schemaName = getMainSchemaName();
        int index = Math.max(schemaName.lastIndexOf('\\'),schemaName.lastIndexOf('/'));
        if(index != -1){
            schemaName = schemaName.substring(index + 1);
        }
        MutableSchema schema = new MutableSchema(schemaName);
        MutableTable table = new MutableTable(schemaName.substring(0,schemaName.length()-4),TableType.TABLE,schema);
        schema.addTable(table);
        int length = dbfReader.getFieldCount();
        for(int i = 0; i < length; ++i){
            DBFField field = dbfReader.getField(i);
            MutableColumn column = new MutableColumn(field.getName());
            DBFDataType type = field.getType();
            ColumnType columnType = ColumnType.VARCHAR;
            if (type == DBFDataType.DOUBLE){
                columnType = ColumnType.DOUBLE;
            }else if (type == DBFDataType.DATE){
                columnType = ColumnType.DATE;
            } else if (type == DBFDataType.CHARACTER){
                columnType = ColumnType.CHAR;
            } else if (type == DBFDataType.LOGICAL){
                columnType = ColumnType.BOOLEAN;
            } else if (type == DBFDataType.MEMO){
                columnType = ColumnType.VARCHAR;
            }
            column.setTable(table);
            column.setType(columnType);
            column.setColumnNumber(i);
            column.setNativeType(field.getType().toString());
            column.setColumnSize(field.getLength());
            table.addColumn(column);
        }
        //dbfReader.close();
        return schema;

    }

    @Override
    protected String getMainSchemaName() throws MetaModelException {
        return resource.getName();

    }

    @Override
    protected DataSet materializeMainSchemaTable(Table table, List<Column> list, int i) {
        synchronized (dbfReader){
            getDBFReader();
            int rowCount = 0;
            final List<SelectItem> selectItems = list.stream().map(SelectItem::new).collect(Collectors.toList());
            final DataSetHeader header = new CachingDataSetHeader(selectItems);
            final List<Row> listValue = new LinkedList<>();
            int max = dbfReader.getRecordCount();
            if(i != -1){
                max = i;
            }
            while(rowCount < max){
                Object[] objects = dbfReader.nextRecord();
                Object[] dbfObject = new Object[list.size()];
                for (int j = 0; j < list.size(); ++j){
                    int fieldNumber = list.get(j).getColumnNumber();
                    dbfObject[j] = objects[fieldNumber];
                }
                listValue.add(new DefaultRow(header,dbfObject));
                rowCount++;
            }
            return new InMemoryDataSet(header,listValue);
        }

    }

    @Override
    protected DataSet materializeMainSchemaTable(Table table, List<Column> list, int first, int last){
        if(first == 1){
            return materializeMainSchemaTable(table,list,last);
        }
        synchronized (dbfReader){
            getDBFReader();

            int rowCount = 0;
            final List<SelectItem> selectItems = list.stream().map(SelectItem::new).collect(Collectors.toList());
            final DataSetHeader header = new CachingDataSetHeader(selectItems);
            final List<Row> listValue = new LinkedList<>();
            int max = dbfReader.getRecordCount();
            if(last != -1){
                max = last;
            }
            while(rowCount < first - 1){
                rowCount ++;
                dbfReader.nextRecord();
            }
            while(rowCount < max){
                Object[] objects = dbfReader.nextRecord();
                Object[] dbfObject = new Object[list.size()];
                for (int j = 0; j < list.size(); ++j){
                    int fieldNumber = list.get(j).getColumnNumber();
                    dbfObject[j] = objects[fieldNumber];
                }
                listValue.add(new DefaultRow(header,dbfObject));
                rowCount++;
            }
            return new InMemoryDataSet(header,listValue);
        }

    }

    @Override
    protected Number executeCountQuery(Table table, List<FilterItem> whereItems, boolean functionApproximationAllowed){
        return null;
    }

    protected void getDBFReader(){
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(dbfFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        dbfReader = new DBFReader(inputStream);
    }

    public ColumnType getDBFColumnType(int index){
        getDBFReader();
        return null;
    }

}
