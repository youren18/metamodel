package org.apache.metamodel.dbf;

import org.apache.metamodel.MetaModelException;
import org.apache.metamodel.create.AbstractTableCreationBuilder;
import org.apache.metamodel.schema.Column;
import org.apache.metamodel.schema.MutableTable;
import org.apache.metamodel.schema.Schema;
import org.apache.metamodel.schema.Table;

import java.util.List;
final class DBFCreateTableBuilder extends AbstractTableCreationBuilder<DBFUpdateCallback> {

    public DBFCreateTableBuilder(DBFUpdateCallback updateCallback, Schema schema, String name){
        super(updateCallback,schema,name);
        if(!(schema instanceof DBFSchema)){
            throw new IllegalArgumentException("not a valid dbf schema" + schema);
        }
    }
    /**
     * 创建并返回新的table
     *
     * Commits the built table and requests that the table structure should be
     * written to the {@link DBFDataContext}.
     *
     * @return the {@link Table} that was build
     * @throws MetaModelException if the {@link DBFDataContext} was not able to create the table
     */
    @Override
    public Table execute() throws MetaModelException {

        DBFUpdateCallback updateCallback = getUpdateCallback();

        MutableTable table = getTable();
        List<String> columnsName = table.getColumnNames();

        DBFDataContext dbfDataContext = (DBFDataContext) updateCallback.getDataContext();

        DBFSchema schema = (DBFSchema) table.getSchema();
        DBFTable dbfTable = new DBFTable(schema,table.getColumns(), table.getName());
        schema.setDbfTable(dbfTable);
        return dbfTable;
    }

    /**
     * 根据已有的table创建一个新的table，由于dbfreader不能读取空文件eofexception
     * 因此重写like函数，将原来table的column属性复制过来
     * @param table
     * @return
     */
    @Override
    public DBFCreateTableBuilder like(Table table){
        List<Column> columns = table.getColumns();
        MutableTable copyTable = getTable();
        for (Column column : columns){
            copyTable.addColumn(column);
        }
        //org.apache.metamodel.dbf.DBFUpdateCallback updateCallback = getUpdateCallback();
        //DBFWriter writer
        return this;
    }

}
