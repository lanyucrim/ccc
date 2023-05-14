package com.lanyu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanyu.common.R;
import com.lanyu.dto.DishDto;
import com.lanyu.entity.Category;
import com.lanyu.service.CategoryService;
import com.lanyu.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PostMapping
    public R<String> saveCategory(@RequestBody Category category)
    {
        return categoryService.saveCategory(category);
    }

    @GetMapping("/page")
    public R<Page> getCategory(int page,int pageSize)
    {
        return categoryService.getCategory(page,pageSize);
    }


    @DeleteMapping
    public R<String> deleteCategory(Long id)
    {

        return categoryService.deleteCategory(id);
    }

    @PutMapping
    public R<String> updateCategory(@RequestBody Category category)
    {
        return categoryService.updateCategory(category);
    }

    @GetMapping("/list")
    public R<List<Category>> list(Category category)
    {
       return categoryService.getlist(category);

    }


}
