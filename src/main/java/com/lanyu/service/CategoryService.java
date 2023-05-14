package com.lanyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanyu.common.R;
import com.lanyu.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface CategoryService extends IService<Category> {
    public R<String> saveCategory(Category category);
    public R<Page> getCategory(int page,int pageSize);
    public R<String> deleteCategory(Long id);
    public R<String> updateCategory(Category category);
    public R<List<Category>> getlist(Category category);

}
