package com.lanyu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanyu.common.CustomException;
import com.lanyu.common.R;
import com.lanyu.entity.Category;

import com.lanyu.entity.Dish;
import com.lanyu.entity.Employee;
import com.lanyu.entity.Setmeal;
import com.lanyu.mapper.CategoryMapper;

import com.lanyu.service.CategoryService;
import com.lanyu.service.DishService;
import com.lanyu.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;



    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public R<String> saveCategory(Category category) {
        category.setIsDeleted(0);
        categoryMapper.insert(category);
        return R.success("成功");
    }

    @Override()
    public R<Page> getCategory(int page, int pageSize) {

        Page<Category> pageInfo =new Page<>(page,pageSize);
        LambdaQueryWrapper<Category> lqw=new LambdaQueryWrapper<>();
        lqw.orderByAsc(Category::getSort);

        categoryMapper.selectPage(pageInfo,lqw);

        return R.success(pageInfo);
    }

    @Override
    public R<String> deleteCategory(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<Dish>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        Long count1=dishService.count(dishLambdaQueryWrapper);
        if(count1>0)
        {
            throw new CustomException("当前分类关联了菜品，不能删除");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        Long count2=setmealService.count(setmealLambdaQueryWrapper);
        if(count2>0)
        {
            throw new CustomException("当前分类关联了套餐，不能删除");
        }


        categoryMapper.deleteById(id);

        return R.success("成功");
    }

    @Override
    public R<String> updateCategory(Category category) {
        int a=categoryMapper.updateById(category);
        return a==1?R.success("成功"):R.error("失败");
    }

    @Override
    public R<List<Category>> getlist(Category category) {
        LambdaQueryWrapper<Category> lqw=new LambdaQueryWrapper<>();
        lqw.eq(category.getType()!=null,Category::getType,category.getType());
        lqw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> categories =categoryMapper.selectList(lqw);
        return R.success(categories);
    }
}
