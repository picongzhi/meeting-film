package com.stylefeng.guns.api.film;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfoResponseVO implements Serializable {
    private String filmId;
    private String biography;
    private ImgVO imgs;
    private ActorResponseVO actors;
}
