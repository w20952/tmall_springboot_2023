package com.david.tmall_springboot_2023.web;

import com.david.tmall_springboot_2023.pojo.Order;
import com.david.tmall_springboot_2023.service.OrderItemService;
import com.david.tmall_springboot_2023.service.OrderService;
import com.david.tmall_springboot_2023.util.Page4Navigator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    @GetMapping("/orders")
    public Page4Navigator<Order> list(@RequestParam(value = "start", defaultValue = "0")int start,
                                      @RequestParam(value = "size", defaultValue = "5") int size) {
        Page page = orderService.list(start, size);

        orderItemService.fill(page.getContent());
        orderItemService.removeOrderFromOrderItem(page.getContent());

        return new Page4Navigator<>(page,5);
    }



}
