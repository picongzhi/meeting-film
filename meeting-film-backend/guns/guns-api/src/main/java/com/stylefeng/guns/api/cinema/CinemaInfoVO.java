package com.stylefeng.guns.api.cinema;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaInfoVO implements Serializable {
    private String cinemaId;
    private String imgUrl;
    private String cinemaName;
    private String cinemaAddress;
    private String cinemaPhone;
}
