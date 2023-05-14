package com.lanyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanyu.common.R;
import com.lanyu.dto.SetmealDto;
import com.lanyu.entity.Category;
import com.lanyu.entity.Setmeal;
import com.lanyu.entity.SetmealDish;
import com.lanyu.mapper.CategoryMapper;
import com.lanyu.mapper.SetmealDishMapper;
import com.lanyu.mapper.SetmealMapper;
import com.lanyu.service.SetmealDishService;
import com.lanyu.service.SetmealService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService  {


    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    @Transactional
    public R<String> save(SetmealDto setmealDto) {
        setmealMapper.insert(setmealDto);

        List<SetmealDish> setmealDishList=setmealDto.getSetmealDishes();

        setmealDishList=setmealDishList.stream().map((item)->
        {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());


        setmealDishService.saveBatch(setmealDishList);

        return R.success("成功");
    }

    @Override
    public R<Page> getPage(int page, int pageSize, String name) {
        Page<Setmeal> pageinfo=new Page<>(page,pageSize);

        Page<SetmealDto> setmealDtoPage=new Page<>(page,pageSize);

        LambdaQueryWrapper<Setmeal> lqw=new LambdaQueryWrapper<>();

        lqw.like(Strings.isNotEmpty(name),Setmeal::getName,name);
        lqw.orderByDesc(Setmeal::getUpdateTime);
        lqw.orderByAsc(Setmeal::getStatus);
        setmealMapper.selectPage(pageinfo,lqw);

        BeanUtils.copyProperties(pageinfo,setmealDtoPage,"records");

        List<Setmeal> records=pageinfo.getRecords();

        List<SetmealDto> list=records.stream().map((item)->
        {
            SetmealDto setmealDto=new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Long categoryId=item.getCategoryId();
            Category category=categoryMapper.selectById(categoryId);
            if(category!=null)
            {
                String categoryName=category.getName();
                setmealDto.setCategoryName(categoryName);
            }

            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(list);


        return R.success(setmealDtoPage);
    }

    @Override
    public R<String> deleteSetmeal(Long[] ids) {

        int i;
        for(i=0;i<ids.length;i++)
        {
            setmealMapper.deleteById(ids[i]);
            setmealDishMapper.deleteBySetmealId(ids[i]);
        }

        return R.success("成功");
    }

    @Override
    public R<String> updateStatus(Integer status, Long[] ids) {
        int i;
        for(i=0;i<ids.length;i++)
        {
            setmealMapper.updateSetmealStatus(status,ids[i]);
        }
        return R.success("成功");
    }

    @Override
    public R<List<Setmeal>> getSetmealList(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> lqw=new LambdaQueryWrapper<>();
        lqw.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        lqw.eq(setmeal.getStatus()!=null,Setmeal::getStatus,setmeal.getStatus());
        List<Setmeal> setmealList=setmealMapper.selectList(lqw);


        return R.success(setmealList);
    }


}
