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
        SqlUtil sqlUtil = new SqlUtil("select * from stu where id = ?");
        List<String> table, whereColumnNames, setColumnNames;
        table = sqlUtil.getTables();
        whereColumnNames = sqlUtil.getWhereColumnNames();
        setColumnNames = sqlUtil.getSetColumnNames();
        System.out.println("table:");
        for (String s : table){
            System.out.println(s);
        }
        System.out.println("where:");
        for (String s : whereColumnNames){
            System.out.println(s);
        }
        System.out.println("set:");
        for (String s : setColumnNames){
            System.out.println(s);
        }
    }

    @Test
    public void testFind(){
//        UserMapper mapper = ProxyMapper.getMapper(UserMapper.class);
//        System.out.println(mapper.selectUser(1));

        Mapper mapper = ProxyMapper.getMapper(Mapper.class);
        Stu stu = mapper.findone(22);
        System.out.println(stu.toString());
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
        stu.setId(22);
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
    public void testUpdate(){
        Mapper mapper = ProxyMapper.getMapper(Mapper.class);
        mapper.updateStubyid("tony1", 0, 22);
    }

    @Test
    public void testMybatisInsert(){
        SqlSessionFactory factory = MybatisUtil.getFactory();
        SqlSession sqlSession = factory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //mapper.insertT(new Stu(10,"he",2));
        sqlSession.close();
    }


}
