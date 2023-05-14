package com.lanyu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanyu.common.R;
import com.lanyu.entity.Employee;
import com.lanyu.mapper.EmployeeMapper;
import com.lanyu.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public R<String> addemployee(HttpServletRequest request,Employee employee) {

        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        Long empId=(Long)request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);


        employeeMapper.insert(employee);

        return R.success("新增员工成功");
    }

    @Override
    public R<Page> getPage(int page, int pageSize, String name) {

        Page pageInfo =new Page(page,pageSize);
        LambdaQueryWrapper<Employee> lqw=new LambdaQueryWrapper();

        lqw.like(Strings.isNotEmpty(name),Employee::getName,name);

        lqw.orderByDesc(Employee::getUpdateTime);
//        log.info("page ={} ,pageSize={}",page,pageSize);
        employeeMapper.selectPage(pageInfo,lqw);

        return R.success(pageInfo);
    }

    @Override
    public R<String> update(HttpServletRequest request,Employee employee) {
//        Long empId=(Long)request.getSession().getAttribute("employee");
//
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);
        int a=employeeMapper.updateById(employee);
        return a==1?R.success("成功"):R.error("失败");
    }
}
