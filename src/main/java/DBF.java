import com.linuxense.javadbf.*;
import java.io.*;

public class DBF {
    public static void main(String[] args) {
//        File file = new File("e:\\emp.dbf");
//        FileInputStream in = null;
//        try {
//            in = new FileInputStream(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        DBFReader reader = new DBFReader(in);
//        final int count = reader.getFieldCount();
//        DBFField fields[] = new DBFField[count];
//        for (int i = 0; i < count; ++i){
//            fields[i] = reader.getField(i);
//        }
//        fields[2].setDecimalCount(2);
        DBFWriter writer = null;
        try {
            writer = new DBFWriter(new File("e:\\emp.dbf"));
            //writer.setFields(fields);
            Object objects[] = new Object[3];
            objects[0] = "2000";
            objects[1] = "tom";
            objects[2] = 1.67;
            writer.addRecord(objects);
            DBFUtils.close(writer);
        }  finally {
            DBFUtils.close(writer);
        }

        //DBFReader dbfReader = new DBFReader()
    }
}
