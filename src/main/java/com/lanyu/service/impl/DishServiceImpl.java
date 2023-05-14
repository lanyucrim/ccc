package com.lanyu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanyu.common.R;
import com.lanyu.dto.DishDto;
import com.lanyu.entity.Category;
import com.lanyu.entity.Dish;
import com.lanyu.entity.DishFlavor;
import com.lanyu.mapper.CategoryMapper;
import com.lanyu.mapper.DishFlavorMapper;
import com.lanyu.mapper.DishMapper;
import com.lanyu.service.CategoryService;
import com.lanyu.service.DishFlavorService;
import com.lanyu.service.DishService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {


    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;//最好换成mapper，不然bean之间重复引用

    @Autowired
    private CategoryMapper categoryMapper;


    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    public R<Page> getPage(int page, int pageSize, String name) {
        Page<Dish> pageinfo=new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage=new Page<>(page,pageSize);

        LambdaQueryWrapper<Dish> lqw=new LambdaQueryWrapper<>();

        lqw.like(Strings.isNotEmpty(name),Dish::getName,name);

        lqw.orderByAsc(Dish::getSort);

        dishMapper.selectPage(pageinfo,lqw);

        BeanUtils.copyProperties(pageinfo,dishDtoPage,"records");

        List<Dish> records=pageinfo.getRecords();
        List<DishDto> list= records.stream().map((item)->
        {
            DishDto dishDto=new DishDto();
            BeanUtils.copyProperties(item,dishDto);

            Long categoryId=item.getCategoryId();
            Category category=categoryMapper.selectById(categoryId);

            if(category!=null)
            {
                String categoryName=category.getName();
                dishDto.setCategoryName(categoryName);
            }




            return dishDto;



        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);


        return R.success(dishDtoPage);
    }

    @Override
    public R<String> updateStatus(Integer status, String ids) {

        String []idss=ids.split(",");
        int i;
        for (i=0;i<idss.length;i++)
        {
            Long id=Long.valueOf(idss[i]);
            dishMapper.updateDishStatus(status,id);

        }


        return R.success("成功");
    }

    @Override
    public R<String> deleteStatus(String ids) {
        String []idss=ids.split(",");
        int i;
        for (i=0;i<idss.length;i++)
        {
            Long id=Long.valueOf(idss[i]);
            dishMapper.deleteById(id);
        }
        return R.success("成功");
    }

    @Override
    @Transactional
    public R<String> addDish(DishDto dishDto) {

//        this.save(dishDto);
        try {
            dishMapper.insert(dishDto);

            Long dishId=dishDto.getId();

            List<DishFlavor> flavors=dishDto.getFlavors();

            flavors=flavors.stream().map((item)->
            {
                item.setDishId(dishId);
                return item;
            }).collect(Collectors.toList());

            dishFlavorService.saveBatch(flavors);
            return R.success("成功");

        } catch (Exception e) {
            return R.error("失败");
        }


    }

    @Override
    public R<DishDto> getByIdWithFlavor(Long id) {
        Dish dish=dishMapper.selectById(id);

        DishDto dishDto=new DishDto();
        BeanUtils.copyProperties(dish,dishDto);

        LambdaQueryWrapper<DishFlavor> lqw=new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors=dishFlavorMapper.selectList(lqw);
        dishDto.setFlavors(flavors);



        return R.success(dishDto);
    }

    @Override
    @Transactional
    public R<String> updateWithFlavor(DishDto dishDto) {
        dishMapper.updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> lqw=new LambdaQueryWrapper<>();
        lqw.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(lqw);

        List<DishFlavor> flavors=dishDto.getFlavors();
        flavors=flavors.stream().map((item)->
        {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);

        return R.success("ok");
    }

    @Override
    public R<List<DishDto>> getList(Dish dish) {
        LambdaQueryWrapper<Dish> lqw=new LambdaQueryWrapper<>();

        lqw.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        lqw.eq(Dish::getStatus,1);
        lqw.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> dishList=dishMapper.selectList(lqw);
        List<DishDto> dishDtos=dishList.stream().map((item)->
        {
            DishDto dishDto=new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long dishid=item.getId();
            LambdaQueryWrapper<DishFlavor> lqw1=new LambdaQueryWrapper<>();
            lqw1.eq(DishFlavor::getDishId,dishid);
            List<DishFlavor> dishFlavors=dishFlavorMapper.selectList(lqw1);
            dishDto.setFlavors(dishFlavors);
            return dishDto;


        }).collect(Collectors.toList());

        return R.success(dishDtos);
    }
}
