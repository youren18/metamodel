package org.apache.metamodel.dbf;

import org.apache.metamodel.MetaModelException;
import org.apache.metamodel.UpdateCallback;
import org.apache.metamodel.UpdateScript;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.delete.AbstractRowDeletionBuilder;
import org.apache.metamodel.schema.Table;
import org.apache.metamodel.util.Action;
import org.apache.metamodel.util.FileHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

final class DBFDeleteBuilder extends AbstractRowDeletionBuilder {

    private static final Logger logger = LoggerFactory.getLogger(DBFDeleteBuilder.class);

    private final DBFUpdateCallback dbfUpdateCallback;

    public DBFDeleteBuilder(DBFUpdateCallback updateCallback, Table table){
        super(table);
        dbfUpdateCallback = updateCallback;
    }

    /**
     *
     * Commits the row deletion operation. This operation will delete rows in
     * the {@link DBFDataContext}.
     *
     * @throws MetaModelException if the operation was rejected
     */
    @Override
    public void execute() throws MetaModelException {
        final File file = FileHelper.createTempFile("metamodel_deletion",".dbf");
        final DBFDataContext dbfDataContext = new DBFDataContext(file);

        dbfDataContext.executeUpdate(new UpdateScript() {
            @Override
            public void run(UpdateCallback callback) {
                final Table originaltable = getTable();//根据原有table创建新的table
                final Table copyTable = callback.createTable(dbfDataContext.getDefaultSchema(),originaltable.getName())
                        .like(originaltable)
                        .execute();

                if(isTruncateTableOperation()){

                    return;
                }
                final DataSet dataSet = dbfUpdateCallback.getDataContext().query().from(originaltable)
                        .select(originaltable.getColumns()).execute();//查出原来的所有数据
                try{
                    while(dataSet.next()){
                        final Row row = dataSet.getRow();
                        if(!deleteRow(row)){//将不满足删除条件的行插入新的table中
                            callback.insertInto(copyTable).like(row).execute();
                        }
                    }
                }finally {
                    dataSet.close();
                }
            }
        });

        final InputStream in = FileHelper.getInputStream(file);//最后用新表的数据替换原来文件的数据
        try{
            dbfUpdateCallback.getResource().write(new Action<OutputStream>() {
                @Override
                public void run(OutputStream arg) throws Exception {
                    FileHelper.copy(in,arg);
                    arg.close();
                }
            });
        }finally {
            FileHelper.safeClose(in);
        }

        if(!file.delete()){
            logger.warn("can not delete temp file");
            //System.out.println("temp");
        }

    }
}
