package com.stylefeng.guns.api.film;

import lombok.Data;

import java.io.Serializable;

@Data
public class FilmDetailVO implements Serializable {
    private String filmId;
    private String filmName;
    private String filmEnName;
    private String imgAddress;
    private String score;
    private String scoreNum;
    private String totalBox;
    private String info1;
    private String info2;
    private String info3;
    private InfoResponseVO info4;
}
