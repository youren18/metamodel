package org.apache.metamodel.dbf;

import org.apache.metamodel.schema.AbstractSchema;
import org.apache.metamodel.schema.Table;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBFSchema extends AbstractSchema {

    public static final long serialVersionUID = 1L;

    private final String name;
    private final transient DBFDataContext dbfDataContext;
    private DBFTable dbfTable;

    public DBFSchema(String schemaName, DBFDataContext dataContext){
        super();
        name = schemaName;
        dbfDataContext = dataContext;
    }

    protected void setDbfTable(DBFTable table){
        dbfTable = table;
    }

    protected DBFDataContext getDbfDataContext(){
        return dbfDataContext;
    }

    /**
     * Gets the name of this Schema
     *
     * @return the name of this Schema
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Gets an optional quote string that is used to enclose the name of this
     * structure.
     *
     * @return A quote string used to enclose the name or null if none exists.
     */
    @Override
    public String getQuote() {
        return null;
    }

    /**
     * Gets all tables in this Schema.
     *
     * @return the tables that reside in the schema
     */
    @Override
    public List<Table> getTables() {
        if(dbfTable == null){
            return new ArrayList<>();
        }
        return Collections.singletonList(dbfTable);
    }
}
