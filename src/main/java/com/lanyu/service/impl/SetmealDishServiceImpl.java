package com.lanyu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanyu.common.R;
import com.lanyu.dto.SetmealDto;
import com.lanyu.entity.SetmealDish;
import com.lanyu.mapper.SetmealDishMapper;
import com.lanyu.mapper.SetmealMapper;
import com.lanyu.service.SetmealDishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {



}
