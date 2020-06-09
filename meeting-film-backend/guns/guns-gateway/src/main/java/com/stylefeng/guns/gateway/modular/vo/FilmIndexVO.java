package com.stylefeng.guns.gateway.modular.vo;

import com.stylefeng.guns.api.film.BannerVO;
import com.stylefeng.guns.api.film.FilmInfo;
import com.stylefeng.guns.api.film.FilmVO;
import lombok.Data;

import java.util.List;

@Data
public class FilmIndexVO {
    private List<BannerVO> banners;
    private FilmVO hotFilms;
    private FilmVO soonFilms;
    private List<FilmInfo> boxRanking;
    private List<FilmInfo> expectRanking;
    private List<FilmInfo> top100;
}
