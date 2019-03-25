import org.apache.metamodel.schema.*;

import java.util.Collection;
import java.util.List;

public class DBFTable extends AbstractTable {
    /**
     * Gets the name of this Table
     *
     * @return the name of this Table
     */
    @Override
    public String getName() {
        return null;
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
        return null;
    }

    /**
     * Gets the schema that this table resides in.
     *
     * @return the schema that the table resides in.
     */
    @Override
    public Schema getSchema() {
        return null;
    }

    /**
     * Gets the table type of this table.
     *
     * @return the type of table
     */
    @Override
    public TableType getType() {
        return null;
    }

    /**
     * Gets all relationships for this table.
     *
     * @return all relationsips for this table. To add relations use
     * TableRelation.createRelation();
     */
    @Override
    public Collection<Relationship> getRelationships() {
        return null;
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
