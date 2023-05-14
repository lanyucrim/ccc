package com.lanyu.controller;

import com.lanyu.common.R;
import com.lanyu.entity.Orders;
import com.lanyu.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;


    @PostMapping("submit")
    public R<String> submit(@RequestBody Orders orders)
    {
        return null;
    }

}
