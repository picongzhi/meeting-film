package com.stylefeng.guns.api.cinema;

import lombok.Data;

import java.io.Serializable;

@Data
public class HallInfoVO implements Serializable {
    private String hallFieldId;
    private String hallName;
    private String price;
    private String seatFile;
    private String soldSeats;
}
