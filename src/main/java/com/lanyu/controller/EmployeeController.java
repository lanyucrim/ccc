package com.lanyu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanyu.common.R;
import com.lanyu.entity.Employee;
import com.lanyu.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")//登录
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee)
    {
        String password=employee.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp=employeeService.getOne(queryWrapper);


        if(emp==null)
        {
            return R.error("登陆失败");
        }
        if(!emp.getPassword().equals(password))
        {
            return R.error("登陆失败");
        }

        if(emp.getStatus()==0)
        {
            return R.error("账号禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }


    @PostMapping("/logout")//登出
    public R<String> logout(HttpServletRequest request)
    {
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }


    @PostMapping()
    public R<String> addEmployee(HttpServletRequest request,@RequestBody Employee employee)
    {
        R end =employeeService.addemployee(request,employee);
        log.info(end.getMsg());
        return end;
    }


    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name)
    {
        return employeeService.getPage(page,pageSize,name);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee)
    {
        return employeeService.update(request,employee);
    }


    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id)
    {
        Employee employee=employeeService.getById(id);
        if(employee!=null)
            return R.success(employee);
        return R.error("没有查询到员工信息");

    }
}
