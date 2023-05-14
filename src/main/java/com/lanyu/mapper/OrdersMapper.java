package com.lanyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanyu.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
