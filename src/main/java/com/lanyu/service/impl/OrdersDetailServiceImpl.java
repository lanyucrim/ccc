package com.lanyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanyu.entity.OrderDetail;
import com.lanyu.mapper.OrdersDetailMapper;
import com.lanyu.service.OrdersDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrdersDetailServiceImpl extends ServiceImpl<OrdersDetailMapper, OrderDetail> implements OrdersDetailService {
}
