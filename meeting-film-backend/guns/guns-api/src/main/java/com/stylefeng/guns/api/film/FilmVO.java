package com.stylefeng.guns.api.film;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class FilmVO implements Serializable {
    private int filmNum;
    private List<FilmInfo> filmInfos;
    private int totalPage;
    private int nowPage;
}
