import Util.MybatisUtil;
import entity.Stu;
import mapper.Mapper;

import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import proxy.ProxyMapper;

import java.util.List;

public class DBFTest {

    @Test
    public void testFind(){
        Mapper mapper = ProxyMapper.getMapper(Mapper.class);
        Stu stu = mapper.findone(2);
        System.out.println(stu.toString());
        stu = mapper.findone(1);
        System.out.println(stu.toString());
//        List<Stu> stus = mapper.findAll();
//        for (Stu temp : stus){
//            System.out.println(temp);
//        }
    }

    @Test
    public void testInsert(){
        Mapper mapper = ProxyMapper.getMapper(Mapper.class);
        Stu stu = new Stu();
        stu.setId(100);
        stu.setAge(101);
        stu.setName("jerryjo");
        System.out.println(mapper.insertStu(stu));
    }

    @Test
    public void testDelete(){
        Mapper mapper = ProxyMapper.getMapper(Mapper.class);
        System.out.println(mapper.deleteStuById(1));
    }
    @Test
    public void testMybatisInsert(){
        SqlSessionFactory factory = MybatisUtil.getFactory();
        SqlSession sqlSession = factory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.insertT(new Stu(10,"he",2));
        sqlSession.close();
    }
}
