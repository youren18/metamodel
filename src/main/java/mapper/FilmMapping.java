package mapper;

import annotation.Delete;
import annotation.Insert;
import annotation.Query;
import annotation.Update;
import entity.Film;

import java.util.List;

public interface FilmMapping {
    @Query("select * from film where fid = ?")
    Film selectById(int id);
    @Query("select * from film where dname = ?")
    List<Film> selectByDname(String dname);
    @Query("select * from film where fid = ? and dname = ?")
    Film selectByIdAndDname(int id, String dname);
    @Insert("insert into film ")
    int inserFilm(Film film);
    @Delete("delete from film where fid = ?")
    int deleteFilmById(int id);
    @Update("update film set fname = ? where fid = ?")
    int updateFilm(String fname, int id);
}
