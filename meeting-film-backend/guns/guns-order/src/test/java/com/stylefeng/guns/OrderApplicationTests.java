package com.stylefeng.guns;

import com.stylefeng.guns.api.order.OrderService;
import com.stylefeng.guns.order.OrderApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class OrderApplicationTests {
    @Autowired
    private OrderService orderService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void isSeatExistsTest() {
        orderService.isSeatExists("", "1,2,25");
    }

    @Test
    public void isSeatsSoldTest() {
        orderService.isSeatsSold("1", "5,6");
    }
}
