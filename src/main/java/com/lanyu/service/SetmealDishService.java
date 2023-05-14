package com.lanyu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lanyu.common.R;
import com.lanyu.dto.SetmealDto;
import com.lanyu.entity.SetmealDish;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public interface SetmealDishService extends IService<SetmealDish> {

}
