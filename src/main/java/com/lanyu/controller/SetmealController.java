package com.lanyu.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanyu.common.R;
import com.lanyu.dto.SetmealDto;
import com.lanyu.entity.Setmeal;
import com.lanyu.service.SetmealDishService;
import com.lanyu.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto)
    {

        return setmealService.save(setmealDto);
    }

    @GetMapping("/page")
    public R<Page> getPage(int page, int pageSize, String name)
    {
        return setmealService.getPage(page,pageSize,name);
    }


    @DeleteMapping
    public R<String> deleteSetmeal(Long[] ids)
    {

        return setmealService.deleteSetmeal(ids);
    }


    @PostMapping("status/{status}")
    private R<String> updateStatus(@PathVariable Integer status,Long[] ids)
    {
        return setmealService.updateStatus(status,ids);
    }

    @GetMapping("/list")
    public R<List<Setmeal>> getSetmealList(Setmeal setmeal)
    {
        return setmealService.getSetmealList(setmeal);
    }
}
