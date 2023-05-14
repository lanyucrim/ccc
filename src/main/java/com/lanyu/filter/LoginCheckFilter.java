package com.lanyu.filter;



//检查用户是否完成登录


import com.alibaba.fastjson2.JSON;
import com.lanyu.common.BaseContext;
import com.lanyu.common.R;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;


@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    //路径匹配器(过滤器，到时候学拦截器)
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        String requestURI =request.getRequestURI();

        log.info(requestURI);

        //定义不需要处理的uri
        String[] urls=new String[]
                {
                        "/employee/login",
                        "/employee/logout",
                        "/backend/**",
                        "/front/**",
                        "/common/**",
                        "/user/sendMsg",
                        "/user/login"
                };

        boolean check=check(urls,requestURI);
        if (check)
        {

            filterChain.doFilter(request,response);

            return;
        }
        if(request.getSession().getAttribute("employee")!=null)
        {
            Long empId=(Long) request.getSession().getAttribute("employee");

            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);

            return;
        }

        if(request.getSession().getAttribute("user")!=null)
        {
            Long empId=(Long) request.getSession().getAttribute("user");

            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);

            return;
        }

        log.info("被拒绝访问");
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;


    }

    public boolean check(String[]urls, String requestURI)
    {
        for(String url:urls)
        {
            boolean match=PATH_MATCHER.match(url,requestURI);
            if (match)
            {
                return true;
            }
        }
        return false;
    }


}
