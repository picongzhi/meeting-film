package com.stylefeng.guns.gateway.modular.vo;

import com.stylefeng.guns.api.cinema.CinemaVO;
import lombok.Data;

import java.util.List;

@Data
public class CinemaResponseVO {
    private List<CinemaVO> cinemas;
}
