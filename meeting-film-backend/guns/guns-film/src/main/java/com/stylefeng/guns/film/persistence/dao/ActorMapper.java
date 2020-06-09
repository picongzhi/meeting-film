package com.stylefeng.guns.film.persistence.dao;

import com.stylefeng.guns.api.film.ActorVO;
import com.stylefeng.guns.film.persistence.model.Actor;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 演员表 Mapper 接口
 * </p>
 *
 * @author picongzhi
 * @since 2020-05-30
 */
public interface ActorMapper extends BaseMapper<Actor> {
    List<ActorVO> getActors(@Param("filmId") String filmId);
}
