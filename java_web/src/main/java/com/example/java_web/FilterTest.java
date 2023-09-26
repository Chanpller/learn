package com.example.java_web;

import javax.servlet.*;
import java.io.IOException;

public class FilterTest implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("进入filter1了");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("filter1执行结束");
    }

    @Override
    public void destroy() {

    }
}
