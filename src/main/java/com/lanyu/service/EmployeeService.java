package com.lanyu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanyu.common.R;
import com.lanyu.entity.Employee;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;


@Service
public interface EmployeeService extends IService<Employee> {

    public R<String> addemployee(HttpServletRequest request, Employee employee);
    public R<Page> getPage(int page,int pageSize,String name);
    public R<String> update(HttpServletRequest request,Employee employee);
}
