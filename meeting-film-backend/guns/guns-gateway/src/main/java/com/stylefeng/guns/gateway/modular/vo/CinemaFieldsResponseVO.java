package com.stylefeng.guns.gateway.modular.vo;

import com.stylefeng.guns.api.cinema.CinemaInfoVO;
import com.stylefeng.guns.api.cinema.FilmInfoVO;
import lombok.Data;

import java.util.List;

@Data
public class CinemaFieldsResponseVO {
    private CinemaInfoVO cinemaInfo;
    private List<FilmInfoVO> filmList;
}
