package com.stylefeng.guns.gateway.modular.vo;

import com.stylefeng.guns.api.cinema.AreaVO;
import com.stylefeng.guns.api.cinema.BrandVO;
import com.stylefeng.guns.api.cinema.HallTypeVO;
import lombok.Data;

import java.util.List;

@Data
public class CinemaConditionResponseVO {
    private List<BrandVO> brandList;
    private List<AreaVO> areasList;
    private List<HallTypeVO> hallTypeList;
}
