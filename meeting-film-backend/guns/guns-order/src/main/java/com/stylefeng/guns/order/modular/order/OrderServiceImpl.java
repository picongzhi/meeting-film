package com.stylefeng.guns.order.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Sets;
import com.stylefeng.guns.api.cinema.CinemaService;
import com.stylefeng.guns.api.cinema.FieldFilmVO;
import com.stylefeng.guns.api.cinema.FilmInfoVO;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.OrderVO;
import com.stylefeng.guns.core.util.UUIDUtil;
import com.stylefeng.guns.order.config.util.FTPUtil;
import com.stylefeng.guns.order.persistence.dao.OrderMapper;
import com.stylefeng.guns.order.persistence.model.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private FTPUtil ftpUtil;

    @Reference(interfaceClass = CinemaService.class, check = false)
    private CinemaService cinemaService;

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
        String uuid = UUIDUtil.genUuid();
        FilmInfoVO filmInfoVO = cinemaService.getFilmInfoByFieldId(fieldId);

        FieldFilmVO fieldFilmVO = cinemaService.getFieldFilmInfo(fieldId);
        int count = soldSeats.split(",").length;
        double totalPrice = getTotalPrice(count, Double.parseDouble(fieldFilmVO.getPrice()));

        Orders orders = new Orders();
        orders.setUuid(uuid);
        orders.setSeatsName(seatsName);
        orders.setSeatsIds(soldSeats);
        orders.setOrderUser(userId);
        orders.setOrderPrice(totalPrice);
        orders.setFilmPrice(Double.parseDouble(fieldFilmVO.getPrice()));
        orders.setFilmId(Integer.parseInt(filmInfoVO.getFilmId()));
        orders.setFieldId(fieldId);
        orders.setCinemaId(Integer.parseInt(fieldFilmVO.getCinemaId()));

        int affected = orderMapper.insert(orders);
        if (affected > 0) {
            OrderVO orderVO = orderMapper.getOrderInfoById(uuid);
            if (orderVO == null) {
                log.error("订单信息查询失败，订单编号为: {}", uuid);
                return null;
            } else {
                return orderVO;
            }
        } else {
            log.info("保存订单失败");
            return null;
        }
    }

    private double getTotalPrice(int count, double price) {
        BigDecimal total = new BigDecimal(price).multiply(new BigDecimal(count));
        return total.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Override
    public List<OrderVO> getOrdersByUserId(Integer userId) {
        if (userId == null) {
            log.error("订单查询业务失败，用户编号为传入");
            return null;
        }

        return orderMapper.getOrderInfosByUserId(userId);
    }

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        return null;
    }
}
