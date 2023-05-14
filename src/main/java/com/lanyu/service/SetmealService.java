package com.lanyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanyu.common.R;
import com.lanyu.dto.SetmealDto;
import com.lanyu.entity.Setmeal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public interface SetmealService extends IService<Setmeal> {
    public R<String> save(SetmealDto setmealDto);
    public R<Page> getPage(int page, int pageSize, String name);
    public R<String> deleteSetmeal(Long[] ids);
    public R<String> updateStatus(@PathVariable Integer status, Long[] ids);

    public R<List<Setmeal>> getSetmealList(Setmeal setmeal);
}
