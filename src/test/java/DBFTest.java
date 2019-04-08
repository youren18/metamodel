import entity.Stu;
import mapper.Mapper;

import org.junit.Test;
import proxy.ProxyMapper;

public class DBFTest {

    @Test
    public void testFind(){
        Mapper mapper = ProxyMapper.getMapper(Mapper.class);
        Stu stus = mapper.findone(2);
        System.out.println(stus.toString());
    }
}
