package com.stylefeng.guns.api.cinema;

import com.baomidou.mybatisplus.plugins.Page;

import java.util.List;

public interface CinemaService {
    /**
     * 查询影院列表
     *
     * @param cinemaQueryVO
     * @return
     */
    Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO);

    /**
     * 获取品牌列表
     *
     * @param brandId
     * @return
     */
    List<BrandVO> getBrands(int brandId);

    /**
     * 获取行政区域列表
     *
     * @param areaId
     * @return
     */
    List<AreaVO> getAreas(int areaId);

    /**
     * 获取影厅类型列表
     *
     * @param hallType
     * @return
     */
    List<HallTypeVO> getHallTypes(int hallType);

    /**
     * 根据影院编号获取影院信息
     *
     * @param cinemaId
     * @return
     */
    CinemaInfoVO getCinemaInfoById(int cinemaId);

    /**
     * 根据影院编号获取电影信息和放映场次信息
     *
     * @param cinemaId
     * @return
     */
    List<FilmInfoVO> getFilmInfoByCinemaId(int cinemaId);

    /**
     * 根据放映场次id获取放映信息
     *
     * @param fieldId
     * @return
     */
    HallInfoVO getFilmFieldInfo(int fieldId);

    /**
     * 根据放映场次id获取放映电影信息
     *
     * @param fieldId
     * @return
     */
    FilmInfoVO getFilmInfoByFieldId(int fieldId);
}
