package com.lanyu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lanyu.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    @Update("update setmeal set status=#{status} where id=#{id}")
    void updateSetmealStatus(Integer status,Long id);
}
