package mapper;

import annotation.Query;
import entity.Film;

public interface FilmMapping {
    @Query("select * from film where film.fid = ?")
    Film selectById(int id);
    @Query("select * from film where dname = ?")
    Film selectByDname(String dname);
    @Query("select * from film where fid = ? and dname = ?")
    Film selectByIdAndDname(int id, String dname);
}
