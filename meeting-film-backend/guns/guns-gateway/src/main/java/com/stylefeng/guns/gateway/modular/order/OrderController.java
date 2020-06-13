package com.stylefeng.guns.gateway.modular.order;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.api.order.OrderVO;
import com.stylefeng.guns.gateway.common.CurrentUser;
import com.stylefeng.guns.gateway.modular.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference(interfaceClass = OrderService.class, check = false)
    private OrderService orderService;

    @PostMapping("/buyTickets")
    public ResponseVO buyTickets(Integer fieldId, String soldSeats, String seatsName) {
        try {
            boolean exists = orderService.isSeatExists(String.valueOf(fieldId), soldSeats);
            if (!exists) {
                log.error("座位不存在");
                return ResponseVO.serviceFail("座位不存在");
            }

            boolean sold = orderService.isSeatsSold(String.valueOf(fieldId), soldSeats);
            if (sold) {
                log.error("座位已售出");
                return ResponseVO.serviceFail("座位已售出");
            }

            String userId = CurrentUser.getCurrentUserId();
            OrderVO orderVO = orderService.saveOrder(fieldId, soldSeats, seatsName, Integer.parseInt(userId));
            if (orderVO == null) {
                log.error("购票业务异常");
                return ResponseVO.serviceFail("购票业务异常");
            }

            return ResponseVO.success(orderVO);
        } catch (Exception e) {
            log.error("购票业务异常", e);
            return ResponseVO.serviceFail("购票业务异常");
        }

    }

    @PostMapping("/getOrderInfo")
    public ResponseVO getOrderInfo(@RequestParam(name = "nowPage", required = false, defaultValue = "1") Integer nowPage,
                                   @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        return null;
    }
}
