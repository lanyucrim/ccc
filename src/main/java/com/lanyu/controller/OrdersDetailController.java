package com.lanyu.controller;


import com.lanyu.service.OrdersDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orderDetail")
public class OrdersDetailController {


    @Autowired
    private OrdersDetailService ordersDetailService;
}
