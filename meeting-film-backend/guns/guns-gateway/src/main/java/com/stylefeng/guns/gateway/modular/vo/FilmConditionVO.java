package com.stylefeng.guns.gateway.modular.vo;

import com.stylefeng.guns.api.film.CatVO;
import com.stylefeng.guns.api.film.SourceVO;
import com.stylefeng.guns.api.film.YearVO;
import lombok.Data;

import java.util.List;

@Data
public class FilmConditionVO {
    private List<CatVO> catInfo;
    private List<SourceVO> sourceInfo;
    private List<YearVO> yearInfo;
}
