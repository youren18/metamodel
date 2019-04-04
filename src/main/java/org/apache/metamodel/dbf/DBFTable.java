package org.apache.metamodel.dbf;

import com.linuxense.javadbf.DBFDataType;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import org.apache.metamodel.schema.*;

import java.util.*;

public class DBFTable extends AbstractTable {

    private static final long serialVersionUID = 1L;

    private final DBFSchema dbfSchema;
    private final String tableName;
    private List<Column> columns;

    public DBFTable(DBFSchema schema, String name, List<String> columnName){
        this(schema, name);
        columns = buildColumns(columnName);
    }

    public DBFTable(DBFSchema schema, String name){
        dbfSchema = schema;
        tableName = name;
    }

    public DBFTable(DBFSchema schema, List<Column> columns,String name ){
        dbfSchema = schema;
        tableName = name;
        this.columns = columns;
    }

    private List<Column> buildColumns(){

        DBFReader dbfReader = dbfSchema.getDbfDataContext().getDBFReader();
        if(dbfReader == null){
            return buildColumnsByNull();
        }
        final int columnCount = dbfReader.getFieldCount();
        final List<String> columnHeaders = new ArrayList<>();
        for (int i = 0; i < columnCount; ++i){
            columnHeaders.add(dbfReader.getField(i).getName());
        }
        return buildColumns(columnHeaders);
    }

    /**
     * 得到dbf文件的列的属性，包括名称，类型，长度
     * @param columnNames
     * @return
     */
    private List<Column> buildColumns(final List<String> columnNames){
        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < columnNames.size(); ++i){
            DBFReader reader = dbfSchema.getDbfDataContext().getDBFReader();
            if(reader == null){
                return buildColumnsByNull();
            }
            DBFField field = reader.getField(i);
            DBFDataType type = field.getType();
            ColumnType columnType = ColumnType.STRING;
            if (type == DBFDataType.DOUBLE){
                columnType = ColumnType.DOUBLE;
            }else if (type == DBFDataType.DATE){
                columnType = ColumnType.DATE;
            } else if (type == DBFDataType.CHARACTER){
                columnType = ColumnType.CHAR;
            } else if (type == DBFDataType.LOGICAL){
                columnType = ColumnType.BOOLEAN;
            } else if (type == DBFDataType.LONG){
                columnType = ColumnType.BIGINT;
            } else if (type == DBFDataType.FLOATING_POINT){
                columnType = ColumnType.DOUBLE;
            } else if (type == DBFDataType.NUMERIC){
                columnType = ColumnType.NUMERIC;
            }

            Column column = new MutableColumn(columnNames.get(i),columnType,this,i,field.getLength(),null,field.isNullable(),null,false,null);
            columns.add(column);
        }
        return columns;
    }

    private List<Column> buildColumnsByNull(){
        if (columns != null){
            return columns;
        }
        return null;

    }

    /**
     * Gets the name of this Table
     *
     * @return the name of this Table
     */
    @Override
    public String getName() {
        return tableName;
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
     * Gets the columns of this table.
     *
     * @return the columns of this table.
     */
    @Override
    public List<Column> getColumns() {
        if (columns == null){
            synchronized (this){
                if (columns == null){
                    columns = buildColumns();
                }
            }
        }
        return columns;
    }

    /**
     * Gets the schema that this table resides in.
     *
     * @return the schema that the table resides in.
     */
    @Override
    public Schema getSchema() {
        return dbfSchema;
    }

    /**
     * Gets the table type of this table.
     *
     * @return the type of table
     */
    @Override
    public TableType getType() {
        return TableType.TABLE;
    }

    /**
     * Gets all relationships for this table.
     *
     * @return all relationsips for this table. To add relations use
     * TableRelation.createRelation();
     */
    @Override
    public Collection<Relationship> getRelationships() {
        return Collections.emptyList();
    }

    /**
     * Gets remarks/comments to this table.
     *
     * @return remarks/comments to this table or null if none exist.
     */
    @Override
    public String getRemarks() {
        return null;
    }


}
