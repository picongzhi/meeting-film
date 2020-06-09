package com.stylefeng.guns.film.modular.film;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.*;
import com.stylefeng.guns.api.film.FilmInfo;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.film.persistence.dao.*;
import com.stylefeng.guns.film.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service
public class FilmServiceImpl implements FilmService {
    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private FilmMapper filmMapper;

    @Autowired
    private CatDictMapper catDictMapper;

    @Autowired
    private SourceDictMapper sourceDictMapper;

    @Autowired
    private YearDictMapper yearDictMapper;

    @Autowired
    private FilmInfoMapper filmInfoMapper;

    @Autowired
    private ActorMapper actorMapper;

    @Override
    public List<BannerVO> getBanners() {
        List<Banner> banners = bannerMapper.selectList(null);
        List<BannerVO> bannerVOs = new ArrayList<>();
        for (Banner banner : banners) {
            BannerVO bannerVO = new BannerVO();
            bannerVO.setBannerId(String.valueOf(banner.getUuid()));
            bannerVO.setBannerUrl(banner.getBannerUrl());
            bannerVO.setBannerAddress(banner.getBannerAddress());
            bannerVOs.add(bannerVO);
        }

        return bannerVOs;
    }

    @Override
    public FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int catId, int yearId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        EntityWrapper<Film> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");
        if (isLimit) {
            Page<Film> page = new Page<>(1, nums);
            List<Film> films = filmMapper.selectPage(page, entityWrapper);

            filmVO.setFilmNum(films.size());
            filmVO.setFilmInfos(transformFilmInfos(films));
        } else {
            Page<Film> page = null;
            switch (sortId) {
                case 1:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage, nums, "film_score");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_box_office");
                    break;
            }

            if (sourceId != 99) {
                entityWrapper.eq("film_source", sourceId);
            }

            if (yearId != 99) {
                entityWrapper.eq("film_date", yearId);
            }

            if (catId != 99) {
                entityWrapper.like("film_cats", "%#" + catId + "#%");
            }

            List<Film> films = filmMapper.selectPage(page, entityWrapper);

            int totalCount = filmMapper.selectCount(entityWrapper);
            int totalPage = totalCount / nums + totalCount % nums != 0 ? 1 : 0;

            filmVO.setFilmNum(films.size());
            filmVO.setFilmInfos(transformFilmInfos(films));
            filmVO.setNowPage(nowPage);
            filmVO.setTotalPage(totalPage);
        }

        return filmVO;
    }

    @Override
    public FilmVO getSoonFilms(boolean islimit, int nums, int nowPage, int sortId, int sourceId, int catId, int yearId) {
        FilmVO filmVO = new FilmVO();
        List<FilmInfo> filmInfos = new ArrayList<>();

        EntityWrapper<Film> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");
        if (islimit) {
            Page<Film> page = new Page<>(1, nums);
            List<Film> films = filmMapper.selectPage(page, entityWrapper);

            filmVO.setFilmNum(films.size());
            filmVO.setFilmInfos(transformFilmInfos(films));
        } else {
            Page<Film> page = null;
            switch (sortId) {
                case 1:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                case 2:
                    page = new Page<>(nowPage, nums, "film_time");
                    break;
                case 3:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
                default:
                    page = new Page<>(nowPage, nums, "film_preSaleNum");
                    break;
            }

            if (sourceId != 99) {
                entityWrapper.eq("film_source", sourceId);
            }

            if (yearId != 99) {
                entityWrapper.eq("film_date", yearId);
            }

            if (catId != 99) {
                entityWrapper.like("film_cats", "%#" + catId + "#%");
            }

            List<Film> films = filmMapper.selectPage(page, entityWrapper);

            int totalCount = filmMapper.selectCount(entityWrapper);
            int totalPage = totalCount / nums + totalCount % nums != 0 ? 1 : 0;

            filmVO.setFilmNum(films.size());
            filmVO.setFilmInfos(transformFilmInfos(films));
            filmVO.setNowPage(nowPage);
            filmVO.setTotalPage(totalPage);
        }

        return filmVO;
    }

    @Override
    public FilmVO getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int catId, int yearId) {
        EntityWrapper<Film> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "3");

        Page<Film> page = null;
        switch (sortId) {
            case 1:
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
            case 2:
                page = new Page<>(nowPage, nums, "film_time");
                break;
            case 3:
                page = new Page<>(nowPage, nums, "film_score");
                break;
            default:
                page = new Page<>(nowPage, nums, "film_box_office");
                break;
        }

        if (sourceId != 99) {
            entityWrapper.eq("film_source", sourceId);
        }

        if (yearId != 99) {
            entityWrapper.eq("film_date", yearId);
        }

        if (catId != 99) {
            entityWrapper.like("film_cats", "%#" + catId + "#%");
        }

        List<Film> films = filmMapper.selectPage(page, entityWrapper);

        int totalCount = filmMapper.selectCount(entityWrapper);
        int totalPage = totalCount / nums + totalCount % nums != 0 ? 1 : 0;

        FilmVO filmVO = new FilmVO();
        filmVO.setFilmNum(films.size());
        filmVO.setFilmInfos(transformFilmInfos(films));
        filmVO.setNowPage(nowPage);
        filmVO.setTotalPage(totalPage);

        return filmVO;
    }

    private List<FilmInfo> transformFilmInfos(List<Film> films) {
        List<FilmInfo> filmInfos = new ArrayList<>();
        for (Film film : films) {
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setScore(film.getFilmScore());
            filmInfo.setImgAddress(film.getImgAddress());
            filmInfo.setFilmType(film.getFilmType());
            filmInfo.setFilmScore(film.getFilmScore());
            filmInfo.setFilmName(film.getFilmName());
            filmInfo.setFilmId(String.valueOf(film.getUuid()));
            filmInfo.setExpectNum(film.getFilmPresalenum());
            filmInfo.setBoxNum(film.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(film.getFilmTime()));

            filmInfos.add(filmInfo);
        }

        return filmInfos;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        EntityWrapper<Film> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<Film> page = new Page<>(1, 10, "film_box_office");
        List<Film> films = filmMapper.selectPage(page, entityWrapper);

        return transformFilmInfos(films);
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        EntityWrapper<Film> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "2");

        Page<Film> page = new Page<>(1, 10, "film_preSaleNum");
        List<Film> films = filmMapper.selectPage(page, entityWrapper);

        return transformFilmInfos(films);
    }

    @Override
    public List<FilmInfo> getTop() {
        EntityWrapper<Film> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status", "1");

        Page<Film> page = new Page<>(1, 100, "film_score");
        List<Film> films = filmMapper.selectPage(page, entityWrapper);

        return transformFilmInfos(films);
    }

    @Override
    public List<CatVO> getCats() {
        List<CatDict> catDictList = catDictMapper.selectList(null);
        List<CatVO> catVOList = new ArrayList<>();
        for (CatDict catDict : catDictList) {
            CatVO catVO = new CatVO();
            catVO.setCatId(String.valueOf(catDict.getUuid()));
            catVO.setCatName(catDict.getShowName());

            catVOList.add(catVO);
        }

        return catVOList;
    }

    @Override
    public List<SourceVO> getSources() {
        List<SourceDict> sourceDictList = sourceDictMapper.selectList(null);
        List<SourceVO> sourceVOList = new ArrayList<>();
        for (SourceDict sourceDict : sourceDictList) {
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(String.valueOf(sourceDict.getUuid()));
            sourceVO.setSourceName(sourceDict.getShowName());

            sourceVOList.add(sourceVO);
        }

        return sourceVOList;
    }

    @Override
    public List<YearVO> getYears() {
        List<YearDict> yearDictList = yearDictMapper.selectList(null);
        List<YearVO> yearVOList = new ArrayList<>();
        for (YearDict yearDict : yearDictList) {
            YearVO yearVO = new YearVO();
            yearVO.setYearId(String.valueOf(yearDict.getUuid()));
            yearVO.setYearName(yearDict.getShowName());

            yearVOList.add(yearVO);
        }

        return yearVOList;
    }

    @Override
    public FilmDetailVO getFilmDetail(int searchType, String idOrName) {
        FilmDetailVO filmDetailVO = null;
        if (searchType == 1) {
            filmDetailVO = filmMapper.getFilmDetailByName("%" + idOrName + "%");
        } else {
            filmDetailVO = filmMapper.getFilmDetailById(idOrName);
        }

        return filmDetailVO;
    }

    @Override
    public FilmDescVO getFilmDesc(String filmId) {
        com.stylefeng.guns.film.persistence.model.FilmInfo filmInfo = getFilmInfo(filmId);
        FilmDescVO filmDescVO = new FilmDescVO();
        filmDescVO.setFilmId(filmId);
        filmDescVO.setBiography(filmInfo.getBiography());

        return filmDescVO;
    }

    @Override
    public ImgVO getImgs(String filmId) {
        com.stylefeng.guns.film.persistence.model.FilmInfo filmInfo = getFilmInfo(filmId);
        String filmImgStr = filmInfo.getFilmImgs();
        String[] filmImgs = filmImgStr.split(",");

        ImgVO imgVO = new ImgVO();
        imgVO.setMainImg(filmImgs[0]);
        imgVO.setImg01(filmImgs[1]);
        imgVO.setImg02(filmImgs[2]);
        imgVO.setImg03(filmImgs[3]);
        imgVO.setImg04(filmImgs[4]);

        return imgVO;
    }

    @Override
    public ActorVO getDirectorInfo(String filmId) {
        com.stylefeng.guns.film.persistence.model.FilmInfo filmInfo = getFilmInfo(filmId);
        Actor director = actorMapper.selectById(filmInfo.getDirectorId());

        ActorVO actorVO = new ActorVO();
        actorVO.setImgAddress(director.getActorImg());
        actorVO.setDirectorName(director.getActorName());

        return actorVO;
    }

    @Override
    public List<ActorVO> getActors(String filmId) {
        return actorMapper.getActors(filmId);
    }

    private com.stylefeng.guns.film.persistence.model.FilmInfo getFilmInfo(String filmId) {
        com.stylefeng.guns.film.persistence.model.FilmInfo filmInfo = new com.stylefeng.guns.film.persistence.model.FilmInfo();
        filmInfo.setFilmId(filmId);

        return filmInfoMapper.selectOne(filmInfo);
    }
}
