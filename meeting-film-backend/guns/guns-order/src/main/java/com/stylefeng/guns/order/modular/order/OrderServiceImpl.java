package com.stylefeng.guns.order.modular.order;

import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.OrderVO;
import com.stylefeng.guns.order.persistence.dao.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public boolean isSeatExists(String fieldId, String seats) {
        String seatAddress = orderMapper.getSeatAddressByFieldId(fieldId);
        return false;
    }

    @Override
    public boolean isSeatsSold(String fieldId, String seats) {
        return false;
    }

    @Override
    public OrderVO saveOrder(Integer fieldId, String soldSeats, String seatsName, Integer userId) {
        return null;
    }

    @Override
    public List<OrderVO> getOrdersByUserId(Integer userId) {
        return null;
    }

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        return null;
    }
}
