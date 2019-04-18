package mapper;

import annotation.Query;
import annotation.Table;
import entity.User;
import org.apache.ibatis.annotations.Insert;

public interface UserMapper {

    @Insert("insert into stu(id,name,age) values(#{id},#{name},#{age})")
    public void insertT(User user);

    @Table("user")
    @Query("select * from user where ids = ?")
    public User selectUser(int id);
}
