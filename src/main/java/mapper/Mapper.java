package mapper;

import annotation.*;
import entity.Stu;
import java.util.List;


public interface Mapper {

    @Query("select * from person where id = ?")
    Stu findone(int id);


    @Query("select * from person where age > ?")
    List<Stu> findAll(int age);


    @Query("select * from person")
    List<Stu> findAll();


    @Insert("insert into person")
    int insertStu(Stu stu);


    @Delete("delete from person where id = ?")
    int deleteStuById(int id);


    @Update("update person set name = ?, age = ? where id = ?")
    int updateStubyid(String name, int age, int id);
}
