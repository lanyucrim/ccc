package com.lanyu.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanyu.common.R;
import com.lanyu.dto.DishDto;
import com.lanyu.entity.Dish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService extends IService<Dish> {

    public R<Page> getPage(int page,int pageSize,String name);
    public R<String> updateStatus(Integer status,String ids);
    public R<String> deleteStatus(String ids);
    public R<String> addDish(DishDto dishDto);
    public R<DishDto> getByIdWithFlavor(Long id);
    public R<String> updateWithFlavor(DishDto dishDto);
    public R<List<DishDto>> getList(Dish dish);
}
