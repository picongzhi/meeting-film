package com.stylefeng.guns.api.film;

import java.util.List;

public interface FilmService {
    /**
     * 获取banners
     *
     * @return
     */
    List<BannerVO> getBanners();

    /**
     * 获取热映影片
     *
     * @param isLimit
     * @param nums
     * @return
     */
    FilmVO getHotFilms(boolean isLimit, int nums, int nowPage, int sortId, int sourceId, int catId, int yearId);

    /**
     * 获取即将上映影片
     *
     * @param islimit
     * @param nums
     * @return
     */
    FilmVO getSoonFilms(boolean islimit, int nums, int nowPage, int sortId, int sourceId, int catId, int yearId);

    /**
     * 获取经典影片
     *
     * @param nums
     * @param nowPage
     * @param sortId
     * @param sourceId
     * @param catId
     * @param yearId
     * @return
     */
    FilmVO getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int catId, int yearId);

    /**
     * 获取票房排行榜
     *
     * @return
     */
    List<FilmInfo> getBoxRanking();

    /**
     * 获取人气排行榜
     *
     * @return
     */
    List<FilmInfo> getExpectRanking();

    /**
     * 获取Top100
     *
     * @return
     */
    List<FilmInfo> getTop();

    /**
     * 获取分类列表
     *
     * @return
     */
    List<CatVO> getCats();

    /**
     * 获取片源列表
     *
     * @return
     */
    List<SourceVO> getSources();

    /**
     * 获取年代列表
     *
     * @return
     */
    List<YearVO> getYears();

    /**
     * 根据id或name获取影片详细信息
     *
     * @param searchType 1: 按名称，2：按id
     * @param idOrName
     * @return
     */
    FilmDetailVO getFilmDetail(int searchType, String idOrName);

    /**
     * 获取影片描述信息
     *
     * @param filmId
     * @return
     */
    FilmDescVO getFilmDesc(String filmId);

    /**
     * 获取影片图片
     *
     * @param filmId
     * @return
     */
    ImgVO getImgs(String filmId);

    /**
     * 获取导演信息
     *
     * @param filmId
     * @return
     */
    ActorVO getDirectorInfo(String filmId);

    /**
     * 获取演员信息
     *
     * @param filmId
     * @return
     */
    List<ActorVO> getActors(String filmId);
}
