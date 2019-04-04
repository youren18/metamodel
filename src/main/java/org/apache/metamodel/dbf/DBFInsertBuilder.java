package org.apache.metamodel.dbf;

import org.apache.metamodel.MetaModelException;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.insert.AbstractRowInsertionBuilder;
import org.apache.metamodel.insert.RowInsertionBuilder;
import org.apache.metamodel.schema.Table;

final class DBFInsertBuilder extends AbstractRowInsertionBuilder<DBFUpdateCallback> {

    public DBFInsertBuilder(DBFUpdateCallback dbfUpdateCallback, Table table){
        super(dbfUpdateCallback, table);
    }
    /**
     * 插入数据，回调writerow函数
     * Commits the row insertion operation. This operation will write the row to
     * the {@link DBFDataContext}.
     *
     * @throws MetaModelException if the operation was rejected
     */
    @Override
    public void execute() throws MetaModelException {
        Object[] values = getValues();
        String[] stringValues = new String[values.length];
        for (int i = 0; i < stringValues.length; i++) {
            stringValues[i] = values[i] == null ? "" : values[i].toString();
        }
        getUpdateCallback().writeRow(stringValues, getTable(),true);
    }

    @Override
    public RowInsertionBuilder like(Row row){
        //String string[] = new String[row.size()];
        for (int i = 0; i < row.size(); ++i){
            //string[i] = row.getValue(i) == null ? "" : row.getValue(i).toString();
            super.value(i,row.getValue(i),row.getStyle(i));
        }

        //getUpdateCallback().writeRow(string, getTable(), true);
        return this;
    }
}
