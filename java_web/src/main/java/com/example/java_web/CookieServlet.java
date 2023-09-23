package com.example.java_web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CookieServlet", value = "/CookieServlet")
public class CookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies !=null){
            for (Cookie cookie: cookies) {
                System.out.println("客户端cookie,key="+cookie.getName()+",value="+cookie.getValue());
            }
        }


        //创建cookie
        Cookie cookie = new Cookie("key1","value1");
        //设置cookie
        response.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
