package com.stylefeng.guns.film.persistence.dao;

import com.stylefeng.guns.api.film.FilmDetailVO;
import com.stylefeng.guns.film.persistence.model.Film;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 影片主表 Mapper 接口
 * </p>
 *
 * @author picongzhi
 * @since 2020-05-30
 */
public interface FilmMapper extends BaseMapper<Film> {
    FilmDetailVO getFilmDetailByName(@Param("filmName") String filmName);

    FilmDetailVO getFilmDetailById(@Param("filmId") String filmId);
}
