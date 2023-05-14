package com.lanyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanyu.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {


    @Update("update dish set status=#{status} where id=#{id}")
    void updateDishStatus(Integer status,Long id);
}
