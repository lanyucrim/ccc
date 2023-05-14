package com.lanyu.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanyu.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

//    @Update("update dish set status=#{status} where id=#{id}")

    @Delete("delete from setmeal_dish where setmeal_id=#{id}")
    void deleteBySetmealId(Long id);

}
