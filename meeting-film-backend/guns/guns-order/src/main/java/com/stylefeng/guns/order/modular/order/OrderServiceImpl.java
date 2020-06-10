package com.stylefeng.guns.order.modular.order;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Sets;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.OrderVO;
import com.stylefeng.guns.order.config.util.FTPUtil;
import com.stylefeng.guns.order.persistence.dao.OrderMapper;
import com.stylefeng.guns.order.persistence.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private FTPUtil ftpUtil;

    @Override
    public boolean isSeatExists(String fieldId, String seats) {
        String seatAddress = orderMapper.getSeatAddressByFieldId(fieldId);
        // TODO: 根据座位文件地址获取座位信息
        String fileStr = ftpUtil.getFileStrFromResource("seat.json");

        JSONObject jsonObject = JSONObject.parseObject(fileStr);
        String ids = jsonObject.getString("ids");
        String[] idsArr = ids.split(",");
        String[] seatsArr = seats.split(",");

        for (String seat : seatsArr) {
            boolean found = false;
            for (String id : idsArr) {
                if (seat.equalsIgnoreCase(id)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isSeatsSold(String fieldId, String seats) {
        EntityWrapper<Orders> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("field_id", fieldId);

        Set<String> seatSet = Sets.newHashSet(seats.split(","));
        List<Orders> orders = orderMapper.selectList(entityWrapper);
        for (Orders order : orders) {
            Set<String> orderSeatSet = Sets.newHashSet(order.getSeatsIds().split(","));
            if (seatSet.stream().anyMatch(orderSeatSet::contains)) {
                return false;
            }
        }

        return true;
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
