package com.lanyu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanyu.common.R;
import com.lanyu.dto.DishDto;
import com.lanyu.entity.Dish;
import com.lanyu.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;


    @GetMapping("/page")
    private R<Page> getPage(int page,int pageSize,String name)
    {
        return dishService.getPage(page,pageSize,name);
    }

    @PostMapping("status/{status}")
    private R<String> updateStatus(@PathVariable Integer status,String ids)
    {
        return dishService.updateStatus(status,ids);
    }

    @DeleteMapping
    private R<String> deleteStatus(String ids)
    {
        return dishService.deleteStatus(ids);
    }

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto)
    {
        log.info(dishDto.toString());

        return dishService.addDish(dishDto);
    }



    @GetMapping("/{id}")
    public R<DishDto> getByIdWithFlavor(@PathVariable Long id)
    {
        return dishService.getByIdWithFlavor(id);
    }


    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto)
    {
        return dishService.updateWithFlavor(dishDto);
    }


    @GetMapping("/list")
    public R<List<DishDto>> getList(Dish dish)
    {
        return dishService.getList(dish);
    }

}
