package com.stylefeng.guns.cinema.modular.cinema;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.cinema.*;
import com.stylefeng.guns.cinema.persistence.dao.*;
import com.stylefeng.guns.cinema.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    private CinemaMapper cinemaMapper;

    @Autowired
    private BrandDictMapper brandDictMapper;

    @Autowired
    private AreaDictMapper areaDictMapper;

    @Autowired
    private HallDictMapper hallDictMapper;

    @Autowired
    private FieldMapper fieldMapper;

    @Override
    public Page<CinemaVO> getCinemas(CinemaQueryVO cinemaQueryVO) {
        EntityWrapper<Cinema> entityWrapper = new EntityWrapper<>();
        if (cinemaQueryVO.getBrandId() != 99) {
            entityWrapper.eq("brand_id", cinemaQueryVO.getBrandId());
        }

        if (cinemaQueryVO.getDistrictId() != 99) {
            entityWrapper.eq("area_id", cinemaQueryVO.getDistrictId());
        }

        if (cinemaQueryVO.getHallType() != 99) {
            entityWrapper.like("hall_ids", "%#" + cinemaQueryVO.getHallType() + "#%");
        }

        Page<Cinema> cinemaPage = new Page<>(cinemaQueryVO.getNowPage(), cinemaQueryVO.getPageSize());
        List<Cinema> cinemas = cinemaMapper.selectPage(cinemaPage, entityWrapper);
        List<CinemaVO> cinemaVOs = new ArrayList<>();
        for (Cinema cinema : cinemas) {
            CinemaVO cinemaVO = new CinemaVO();
            cinemaVO.setUuid(String.valueOf(cinema.getUuid()));
            cinemaVO.setCinemaName(cinema.getCinemaName());
            cinemaVO.setAddress(cinema.getCinemaAddress());
            cinemaVO.setMinimumPrice(String.valueOf(cinema.getMinimumPrice()));

            cinemaVOs.add(cinemaVO);
        }

        long count = cinemaMapper.selectCount(entityWrapper);
        Page<CinemaVO> result = new Page<>();
        result.setRecords(cinemaVOs);
        result.setSize(cinemaQueryVO.getPageSize());
        result.setCurrent(cinemaQueryVO.getNowPage());
        result.setTotal(count);

        return result;
    }

    @Override
    public List<BrandVO> getBrands(int brandId) {
        BrandDict selectedBrand = brandDictMapper.selectById(brandId);
        if (selectedBrand == null) {
            brandId = 99;
        }

        List<BrandDict> brandDicts = brandDictMapper.selectList(null);
        List<BrandVO> brandVOs = new ArrayList<>();
        for (BrandDict brandDict : brandDicts) {
            BrandVO brandVO = new BrandVO();
            brandVO.setBrandId(String.valueOf(brandDict.getUuid()));
            brandVO.setBrandName(brandDict.getShowName());
            if (brandDict.getUuid() == brandId) {
                brandVO.setActive(true);
            }

            brandVOs.add(brandVO);
        }

        return brandVOs;
    }

    @Override
    public List<AreaVO> getAreas(int areaId) {
        AreaDict selectedArea = areaDictMapper.selectById(areaId);
        if (selectedArea == null) {
            areaId = 99;
        }

        List<AreaDict> areaDicts = areaDictMapper.selectList(null);
        List<AreaVO> areaVOs = new ArrayList<>();
        for (AreaDict areaDict : areaDicts) {
            AreaVO areaVO = new AreaVO();
            areaVO.setAreaId(String.valueOf(areaDict.getUuid()));
            areaVO.setAreaName(areaDict.getShowName());
            if (areaDict.getUuid() == areaId) {
                areaVO.setActive(true);
            }

            areaVOs.add(areaVO);
        }

        return areaVOs;
    }

    @Override
    public List<HallTypeVO> getHallTypes(int hallType) {
        HallDict selectedHallType = hallDictMapper.selectById(hallType);
        if (selectedHallType == null) {
            hallType = 99;
        }

        List<HallDict> hallDicts = hallDictMapper.selectList(null);
        List<HallTypeVO> hallTypeVOs = new ArrayList<>();
        for (HallDict hallDict : hallDicts) {
            HallTypeVO hallTypeVO = new HallTypeVO();
            hallTypeVO.setHallTypeId(String.valueOf(hallDict.getUuid()));
            hallTypeVO.setHallTypeName(hallDict.getShowName());
            if (hallDict.getUuid() == hallType) {
                hallTypeVO.setActive(true);
            }

            hallTypeVOs.add(hallTypeVO);
        }

        return hallTypeVOs;
    }

    @Override
    public CinemaInfoVO getCinemaInfoById(int cinemaId) {
        Cinema cinema = cinemaMapper.selectById(cinemaId);

        CinemaInfoVO cinemaInfoVO = new CinemaInfoVO();
        cinemaInfoVO.setCinemaId(String.valueOf(cinema.getUuid()));
        cinemaInfoVO.setCinemaName(cinema.getCinemaName());
        cinemaInfoVO.setCinemaAddress(cinema.getCinemaAddress());
        cinemaInfoVO.setCinemaPhone(cinema.getCinemaPhone());
        cinemaInfoVO.setImgUrl(cinema.getImgAddress());

        return cinemaInfoVO;
    }

    @Override
    public List<FilmInfoVO> getFilmInfoByCinemaId(int cinemaId) {
        return fieldMapper.getFilmInfos(cinemaId);
    }

    @Override
    public HallInfoVO getFilmFieldInfo(int fieldId) {
        return fieldMapper.getHallInfo(fieldId);
    }

    @Override
    public FilmInfoVO getFilmInfoByFieldId(int fieldId) {
        return fieldMapper.getFilmInfo(fieldId);
    }

    @Override
    public FieldFilmVO getFieldFilmInfo(int fieldId) {
        Field field = fieldMapper.selectById(fieldId);
        FieldFilmVO fieldFilmVO = new FieldFilmVO();
        fieldFilmVO.setCinemaId(String.valueOf(field.getCinemaId()));
        fieldFilmVO.setPrice(String.valueOf(field.getPrice()));

        return fieldFilmVO;
    }
}
