package com.stylefeng.guns.cinema.persistence.dao;

import com.stylefeng.guns.api.cinema.FilmInfoVO;
import com.stylefeng.guns.api.cinema.HallInfoVO;
import com.stylefeng.guns.cinema.persistence.model.Field;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 放映场次表 Mapper 接口
 * </p>
 *
 * @author picongzhi
 * @since 2020-05-31
 */
public interface FieldMapper extends BaseMapper<Field> {
    List<FilmInfoVO> getFilmInfos(@Param("cinemaId") Integer cinemaId);

    HallInfoVO getHallInfo(@Param("fieldId") Integer fieldId);

    FilmInfoVO getFilmInfo(@Param("fieldId") Integer fieldId);
}
