package mapper;

import annotation.Query;
import annotation.Table;
import entity.Stu;

import java.util.List;

public interface Mapper {
    @Table(tableName = "stu")
    @Query(query = "select * from stu where id = ?")
    Stu findone(int id);
}
