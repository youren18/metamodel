import org.apache.metamodel.MetaModelException;
import org.apache.metamodel.insert.AbstractRowInsertionBuilder;
import org.apache.metamodel.schema.Table;

final class DBFInsertBuilder extends AbstractRowInsertionBuilder<DBFUpdateCallback> {

    public DBFInsertBuilder(DBFUpdateCallback dbfUpdateCallback, Table table){
        super(dbfUpdateCallback, table);
    }
    /**
     * Commits the row insertion operation. This operation will write the row to
     * the {@link DataContext}.
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
        getUpdateCallback().writeRow(stringValues, true);
    }
}
