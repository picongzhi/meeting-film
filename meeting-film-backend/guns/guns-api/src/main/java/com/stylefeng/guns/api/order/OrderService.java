package com.stylefeng.guns.api.order;

import java.util.List;

public interface OrderService {
    /**
     * 判断场次座位是否为真
     *
     * @param fieldId
     * @param seats
     * @return
     */
    boolean isSeatExists(String fieldId, String seats);

    /**
     * 判断座位有没有售出
     *
     * @param fieldId
     * @param seats
     * @return
     */
    boolean isSeatsSold(String fieldId, String seats);

    /**
     * 创建订单
     *
     * @param fieldId
     * @param soldSeats
     * @param seatsName
     * @param userId
     * @return
     */
    OrderVO saveOrder(Integer fieldId, String soldSeats, String seatsName, Integer userId);

    /**
     * 根据用户获取订单
     *
     * @param userId
     * @return
     */
    List<OrderVO> getOrdersByUserId(Integer userId);

    /**
     * 根据场次获取所有已售的座位
     *
     * @param fieldId
     * @return
     */
    String getSoldSeatsByFieldId(Integer fieldId);
}
