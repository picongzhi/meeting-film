package com.stylefeng.guns.gateway.modular.order;

import com.stylefeng.guns.gateway.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @PostMapping("/buyTickets")
    public ResponseVO buyTickets(Integer fieldId, String soldSeats, String seatsName) {
        return null;
    }

    @PostMapping("/getOrderInfo")
    public ResponseVO getOrderInfo(@RequestParam(name = "nowPage", required = false, defaultValue = "1") Integer nowPage,
                                   @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize) {
        return null;
    }
}
