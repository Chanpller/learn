package com.example.java_web;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet3 extends HttpServlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
//        super.init(servletConfig);
        System.out.println("2 init 初始化方法");
// 1、可以获取 Servlet 程序的别名 servlet-name 的值
        System.out.println("HelloServlet 程序的别名是:" + servletConfig.getServletName());
// 2、获取初始化参数 init-param
        System.out.println("初始化参数 username 的值是;" + servletConfig.getInitParameter("username"));
        System.out.println("初始化参数 url 的值是;" + servletConfig.getInitParameter("url"));
// 3、获取 ServletContext 对象
        System.out.println(servletConfig.getServletContext());
    }

    /**
     * doGet（）在 get 请求的时候调用
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        System.out.println("2 init 初始化方法");
// 1、可以获取 Servlet 程序的别名 servlet-name 的值
        System.out.println("HelloServlet 程序的别名是:" + getServletConfig().getServletName());
// 2、获取初始化参数 init-param
        System.out.println("初始化参数 username 的值是;" + getServletConfig().getInitParameter("username"));
        System.out.println("初始化参数 url 的值是;" + getServletConfig().getInitParameter("url"));
// 3、获取 ServletContext 对象
        System.out.println(getServletConfig().getServletContext());
        System.out.println("HelloServlet2 的 doGet 方法");
    }
    /**
     * doPost（）在 post 请求的时候调用
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
            IOException {
        System.out.println("HelloServlet2 的 doPost 方法");
    }
}
