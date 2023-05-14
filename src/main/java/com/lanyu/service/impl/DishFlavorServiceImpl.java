package com.lanyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanyu.entity.DishFlavor;
import com.lanyu.mapper.DishFlavorMapper;
import com.lanyu.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
