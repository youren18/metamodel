import Util.MybatisUtil;
import entity.Film;
import entity.Stu;
import entity.User;
import mapper.FilmMapping;
import mapper.Mapper;

import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import proxy.ProxyMapper;
import executor.SqlUtil;
import session.Session;

import java.io.File;
import java.sql.*;
import java.util.Date;
import java.util.List;

public class DBFTest {

    @Test
    public void testFilmSelect(){
        Session session = new Session("/myconnect.properties");
        FilmMapping mapper = session.getMapper(FilmMapping.class);
        //Film film = mapper.selectById(4);
        //System.out.println(film.toString());
//        Film film = new Film();
//        film.setFid(112);
//        film.setDname("tom");
//        film.setFname("tom and jerry");
//        film.setFtype("comic");
//        film.setGrade(100);
//        film.setLength(100);
//        mapper.inserFilm(film);
        Film film = mapper.selectById(1);
        Film film1 = mapper.selectByIdAndDname(1, "hav");
        List<Film> film2 = mapper.selectByDname("rpy");
        System.out.println("select1:");
        System.out.println(film);
        System.out.println("select2:");
        System.out.println(film1);
        System.out.println("select3");
        for (Film f : film2){
            System.out.println(f.toString());
        }






//        Date firstDate = new Date();
//        for (int i = 0; i < 100; ++i){
//            int random = (int)(1+Math.random()*(100-1+1));
//            film = mapper.selectById(random);
//        }
//        Date secondDate = new Date();
//        System.out.println(secondDate.getTime() - firstDate.getTime());
    }

    @Test
    public void testJDBC(){
        String driverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/mystu?serverTimezone=GMT%2B8";
        String user = "root";
        String password = "password";
        Connection conn = null;
        Date firstDate = new Date();

        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sqlString = "insert into film values(120,'tom and jerry','fun','herry',110,12)";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlString);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Date secondDate = new Date();
        System.out.println(secondDate.getTime() - firstDate.getTime());


//        for (int i = 0; i < 100; ++i){
//        try {
//            Class.forName(driverName);
//            conn = DriverManager.getConnection(url, user, password);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        String sqlString = "select * from film where film.fid = ?";
//        ResultSet pst = null;
//        try {
//            PreparedStatement preparedStatement = conn.prepareStatement(sqlString);
//
//            int random = (int)(1+Math.random()*(100-1+1));
//            preparedStatement.setString(1, String.valueOf(random));
//            pst = preparedStatement.executeQuery();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        }
//        Date secondDate = new Date();
//        System.out.println(secondDate.getTime() - firstDate.getTime());

    }


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

        Session session = new Session("/myconnect.properties");
        Mapper mapper = session.getMapper(Mapper.class);

        Stu stu = mapper.findByAge(1);
        if(stu != null)
            System.out.println(stu.toString());
//        List<Stu> stu = mapper.findAll(12);
//        for (Stu s : stu){
//            System.out.println(s.toString());
//        }
//        Date secondDate = new Date();
//
//        List<Stu> stus = mapper.findAll(12);
//        for (Stu s : stus){
//            System.out.println(s.toString());
//        }
//        System.out.println(secondDate.getTime() - firstDate.getTime());
//        System.out.println(new Date().getTime() - secondDate.getTime());
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
        Session session = new Session("/myconnect.properties");
        FilmMapping mapper = session.getMapper(FilmMapping.class);
        Film film = new Film();
        int i = 109;
        film.setFid(i);
        film.setFname("tom and jerry");
        film.setFtype("fun");
        film.setDname("herry");
        film.setLength(111);
        film.setGrade(66);
        Date firstDate = new Date();
        mapper.inserFilm(film);
        Date secondDate = new Date();
        System.out.println(secondDate.getTime() - firstDate.getTime());

//        film.setFid(5);
//        film.setFname("tom and jerry");
//        film.setFtype("fun");
//        film.setDname("herry");
//        film.setLength(111);
//        film.setGrade(66);
//        mapper.inserFilm(film);

//        Stu stu = new Stu();
//        stu.setId(24);
//        stu.setAge(11);
//        stu.setName("name");
//        mapper.insertStu(stu);


    }

    @Test
    public void testDelete(){
        Session session = new Session("/myconnect.properties");
        FilmMapping mapper = session.getMapper(FilmMapping.class);
        mapper.deleteFilmById(1);
    }

    @Test
    public void testUpdate(){
        Session session = new Session("/myconnect.properties");
        FilmMapping mapper = session.getMapper(FilmMapping.class);
        mapper.updateFilm("pop",2);
    }

    @Test
    public void testMybatisInsert(){

        SqlSessionFactory factory = MybatisUtil.getFactory();
        SqlSession sqlSession = factory.openSession(true);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //mapper.insertT(new User(11,"he11"));
        //Date firstDate = new Date();
        User user = mapper.selectUser(12);
        //Date secondDate = new Date();
        //user = mapper.selectUser(10);
        System.out.println(user.toString());
//        System.out.println(secondDate.getTime() - firstDate.getTime());
//        System.out.println(new Date().getTime() - secondDate.getTime());
        sqlSession.close();
    }


}
