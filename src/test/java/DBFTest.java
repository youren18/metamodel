import Util.MybatisUtil;
import entity.Stu;
import mapper.Mapper;

import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import proxy.ProxyMapper;
import proxy.SqlUtil;

import java.util.List;

public class DBFTest {

    @Test
    public void testSqlUtil(){
        SqlUtil sqlUtil = new SqlUtil("update stu set name = ?, age = ? where id = ?");
    }

    @Test
    public void testFind(){
        UserMapper mapper = ProxyMapper.getMapper(UserMapper.class);
        System.out.println(mapper.selectUser(1));

//        Mapper mapper = ProxyMapper.getMapper(Mapper.class);
//        Stu stu = mapper.findone(20);
//        System.out.println(stu.toString());
//        List<Stu> stus = mapper.findAll(111);
//        for (Stu s : stus){
//            System.out.println(s.toString());
//        }

//        List<Stu> stus = mapper.findAll();
//        for (Stu temp : stus){
//            System.out.println(temp);
//        }
    }

    @Test
    public void testInsert(){
        Mapper mapper = ProxyMapper.getMapper(Mapper.class);
        Stu stu = new Stu();
        stu.setId(21);
        stu.setAge(11);
        stu.setName("name");

        mapper.insertStu(stu);
    }

    @Test
    public void testDelete(){
        Mapper mapper = ProxyMapper.getMapper(Mapper.class);
        mapper.deleteStuById(2);
    }
    @Test
    public void testMybatisInsert(){
        SqlSessionFactory factory = MybatisUtil.getFactory();
        SqlSession sqlSession = factory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //mapper.insertT(new Stu(10,"he",2));
        sqlSession.close();
    }

    @Test
    public void testUpdate(){
        Mapper mapper = ProxyMapper.getMapper(Mapper.class);
        mapper.updateStubyid("tony1", 12, 2);
    }
}
