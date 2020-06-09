package com.stylefeng.guns.api.film;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ActorResponseVO implements Serializable {
    private ActorVO director;
    private List<ActorVO> actors;
}
