package com.example.java_web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        this.doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Method action = this.getClass().getDeclaredMethod(req.getParameter("action"), HttpServletRequest.class, HttpServletResponse.class);
            action.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
