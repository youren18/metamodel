package mapper;

import annotation.*;
import entity.Stu;

import java.util.List;

public interface Mapper {
    @Table(tableName = "stu")
    @Query(query = "select * from stu where id = ?")
    Stu findone(int id);

    @Table(tableName = "stu")
    @Query(query = "select * from stu")
    List<Stu> findAll();

    @Table(tableName = "stu")
    @Insert(insert = "insert into stu")
    int insertStu(Stu stu);

    @Table(tableName = "stu")
    @Delete(delete = "delete from stu where id = ?")
    int deleteStuById(int id);

    @Table(tableName = "stu")
    @Update(update = "update stu set name = 'hello' where id = ?")
    int updateStubyid(int id);
}
