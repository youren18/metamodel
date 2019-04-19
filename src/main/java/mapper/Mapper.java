package mapper;

import annotation.*;
import entity.Stu;
import java.util.List;


public interface Mapper {
    @Table("stu")
    @Query("select * from stu where id = ?")
    Stu findone(int id);

    @Table("stu")
    @Query("select * from stu where age > ?")
    List<Stu> findAll(int age);

    @Table("stu")
    @Query("select * from stu")
    List<Stu> findAll();

    @Table("stu")
    @Insert("insert into stu")
    int insertStu(Stu stu);

    @Table("stu")
    @Delete("delete from stu where id = ?")
    int deleteStuById(int id);

    @Table("stu")
    @Update("update stu set name = ?, age = ? where id = ?")
    int updateStubyid(String name, int age, int id);
}
