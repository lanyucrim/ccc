package com.lanyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lanyu.common.R;
import com.lanyu.entity.Orders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface OrdersService extends IService<Orders> {
    public R<String> submit(Orders orders);
}
