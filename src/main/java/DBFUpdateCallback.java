import org.apache.metamodel.AbstractUpdateCallback;
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
import java.io.Writer;

public class DBFUpdateCallback extends AbstractUpdateCallback implements UpdateCallback{

    private static final Logger logger = LoggerFactory.getLogger(DBFUpdateCallback.class);

    private final Resource resource;
    private final File file; 
    private Writer writer;

    public DBFUpdateCallback(DBFDataContext dataContext){
        super(dataContext);
        resource = dataContext.getResource();
        file = dataContext.getDbfFile();
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
        return null;
    }

    /**
     * Determines whether row delete is supported
     *
     * @return true if row delete is supported
     */
    @Override
    public boolean isDeleteSupported() {
        return false;
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
        return null;
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
        return null;
    }
}
