package mapper;

import entity.Stu;
import org.apache.ibatis.annotations.Insert;

public interface UserMapper {

    @Insert("insert into stu(id,name,age) values(#{id},#{name},#{age})")
    public void insertT(Stu user);
}
