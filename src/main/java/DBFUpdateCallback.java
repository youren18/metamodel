import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFUtils;
import com.linuxense.javadbf.DBFWriter;
import org.apache.metamodel.AbstractUpdateCallback;
import org.apache.metamodel.MetaModelHelper;
import org.apache.metamodel.UpdateCallback;
import org.apache.metamodel.create.TableCreationBuilder;
import org.apache.metamodel.delete.RowDeletionBuilder;
import org.apache.metamodel.drop.TableDropBuilder;
import org.apache.metamodel.insert.RowInsertionBuilder;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;
import org.apache.metamodel.util.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBFUpdateCallback extends AbstractUpdateCallback implements UpdateCallback{

    private static final Logger logger = LoggerFactory.getLogger(DBFUpdateCallback.class);

    private final Resource resource;
    private final File file; 
    private Writer writer;
    private final DBFDataContext dbfDataContext;

    public DBFUpdateCallback(DBFDataContext dataContext){
        super(dataContext);
        resource = dataContext.getResource();
        file = dataContext.getDbfFile();
        dbfDataContext = dataContext;
    }

    protected synchronized void writeRow(final String[] strings, final boolean append){
        DBFWriter dbfWriter = null;
        try {
            DBFField[] fields = dbfDataContext.getDBFField();
            Object [][] reData = dbfDataContext.getObject();
            dbfWriter = new DBFWriter(new FileOutputStream(file));
            dbfWriter.setFields(fields);
            Object object[] = new Object[fields.length];
            for (int j = 0; j < reData.length; ++j){
                dbfWriter.addRecord(reData[j]);
            }

            for (int i = 0; i < fields.length; ++i){
                DBFDataType type = fields[i].getType();
                if(type == DBFDataType.CHARACTER){
                    object[i] = strings[i];
                } else if (type == DBFDataType.NUMERIC){
                    object[i] = new BigDecimal(strings[i]);
                } else if (type == DBFDataType.FLOATING_POINT){
                    object[i] = new Float(strings[i]);
                } else if (type == DBFDataType.LONG){
                    object[i] = new Long(strings[i]);
                } else if (type == DBFDataType.DATE){
                    System.out.println("dbfupdatecallback date" + strings[i]);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    ParsePosition parsePosition = new ParsePosition(0);

                    object[i] = dateFormat.parse(strings[i],parsePosition);
                } else if (type == DBFDataType.DOUBLE){
                    object[i] = new Double(strings[i]);
                } else if (type == DBFDataType.MEMO){
                    object[i] = strings[i];
                } else {
                    object[i] = strings[i];
                }
            }
            dbfWriter.addRecord(object);
            DBFUtils.close(dbfWriter);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally{
            DBFUtils.close(dbfWriter);
        }
    }

    public Resource getResource(){return resource;}

    private void validateTable(Table table) {
        if (!(table instanceof DBFTable)) {
            throw new IllegalArgumentException("Not a valid dbf table: " + table);
        }
    }

    /**
     * Initiates the building of a table creation operation.
     *
     * @param schema the schema to create the table in
     * @param name   the name of the new table
     * @return a builder object on which the details of the table can be
     * specified and committed.
     * @throws IllegalArgumentException if the table argument is null or invalid.
     * @throws IllegalStateException    if the connection to the DataContext is read-only or another
     *                                  access restriction is preventing the operation.
     */
    @Override
    public TableCreationBuilder createTable(Schema schema, String name) throws IllegalArgumentException, IllegalStateException {

        return new DBFCreateTableBuilder(this, MetaModelHelper.resolveUnderlyingSchema(schema),name);
    }

    /**
     * Determines whether row delete is supported
     *
     * @return true if row delete is supported
     */
    @Override
    public boolean isDeleteSupported() {
        return true;
    }

    /**
     * Initiates a row deletion builder.
     *
     * @param table
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     * @throws UnsupportedOperationException
     */
    @Override
    public RowDeletionBuilder deleteFrom(Table table) throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException {
        validateTable(table);
        return new DBFDeleteBuilder(this,table);
    }

    /**
     * Determines whether table drop is supported
     *
     * @return true if table drop is supported
     */
    @Override
    public boolean isDropTableSupported() {
        return false;
    }

    @Override
    public TableDropBuilder dropTable(Table table) throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException {
        return null;
    }

    /**
     * Initiates the building of a row insertion operation.
     *
     * @param table the table to insert a row into
     * @return a builder object on which values can be added and the statement
     * can be committed.
     * @throws IllegalArgumentException      if the table argument is null or invalid.
     * @throws IllegalStateException         if the connection to the DataContext is read-only or another
     *                                       access restriction is preventing the operation.
     * @throws UnsupportedOperationException in case {@link #isInsertSupported()} is false
     */
    @Override
    public RowInsertionBuilder insertInto(Table table) throws IllegalArgumentException, IllegalStateException, UnsupportedOperationException {
        validateTable(table);
        return new DBFInsertBuilder(this,table);
    }
}
