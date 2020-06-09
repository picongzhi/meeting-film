package com.stylefeng.guns.gateway.modular.vo;

import com.stylefeng.guns.api.cinema.CinemaInfoVO;
import com.stylefeng.guns.api.cinema.FilmInfoVO;
import com.stylefeng.guns.api.cinema.HallInfoVO;
import lombok.Data;

@Data
public class CinemaFieldResponseVO {
    private CinemaInfoVO cinemaInfo;
    private FilmInfoVO filmInfo;
    private HallInfoVO hallInfo;
}
