package com.stylefeng.guns.api.film;

import java.util.List;

public interface FilmAsyncService {
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
