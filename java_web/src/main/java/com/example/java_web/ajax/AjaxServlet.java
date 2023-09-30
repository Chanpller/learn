package com.example.java_web.ajax;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AjaxServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("请求了ajax");
        response.setContentType("txt/html;charset=UTF-8");
        response.getWriter().println("请求了ajax");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}
