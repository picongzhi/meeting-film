package com.stylefeng.guns.order.persistence.dao;

import com.stylefeng.guns.order.persistence.model.Order;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author picongzhi
 * @since 2020-06-04
 */
public interface OrderMapper extends BaseMapper<Order> {
    String getSeatAddressByFieldId(@Param("fieldId") String fieldId);
}
