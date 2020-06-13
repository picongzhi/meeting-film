package com.stylefeng.guns.order.persistence.dao;

import com.stylefeng.guns.api.order.OrderVO;
import com.stylefeng.guns.order.persistence.model.Orders;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author picongzhi
 * @since 2020-06-04
 */
public interface OrderMapper extends BaseMapper<Orders> {
    String getSeatAddressByFieldId(@Param("fieldId") String fieldId);

    OrderVO getOrderInfoById(@Param("fieldId") String orderId);

    List<OrderVO> getOrderInfosByUserId(@Param("userId") Integer userId);

    String getSoldSeatsByFieldId(@Param("fieldId") Integer fieldId);
}
