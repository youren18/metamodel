package mapper;

import annotation.Query;
import annotation.Table;
import entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Insert("insert into user(ids,name) values(#{id},#{name})")
    public void insertT(User user);


    @Select("select * from user where ids = #{id}")

    public User selectUser(@Param("id") int id);
}
