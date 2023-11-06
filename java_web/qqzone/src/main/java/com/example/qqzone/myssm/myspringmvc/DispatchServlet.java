package com.example.qqzone.myssm.myspringmvc;

import com.example.qqzone.myssm.ioc.BeanFactory;
import com.example.qqzone.myssm.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class DispatchServlet extends ViewBaseServlet {
    private BeanFactory beanFactory ;

    @Override
    public void init() throws ServletException {
        super.init();
        ServletContext application = getServletContext();
        Object beanFactoryObj = application.getAttribute("beanFactory");
        if(beanFactoryObj!=null){
            beanFactory = (BeanFactory)beanFactoryObj ;
        }else{
            throw new RuntimeException("IOC容器获取失败！");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        req.setCharacterEncoding("UTF-8");
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastDotIndex = servletPath.lastIndexOf(".do") ;
        servletPath = servletPath.substring(0,lastDotIndex);
        Object controllerBeanObj = beanFactory.get(servletPath);

        String action = req.getParameter("operate");
        if(StringUtil.isEmpty(action)){
            action = "index" ;
        }
        Method[] declaredMethods = controllerBeanObj.getClass().getDeclaredMethods();
        for(Method method : declaredMethods){
            if(action.equals(method.getName())){
                Parameter[] parameters = method.getParameters();
                Object[] values = new Object[parameters.length];
                for(int i=0;i<parameters.length;i++){
                    if(parameters[i].getType() == HttpServletRequest.class){
                        values[i] = req;
                    }else if (parameters[i].getType() == HttpServletResponse.class){
                        values[i] = resp;
                    }else if(parameters[i].getType() == Integer.class){
                        if(req.getParameter(parameters[i].getName())!=null){
                            values[i] =   Integer.valueOf(req.getParameter(parameters[i].getName()));
                        }
                    }else{
                        values[i] =   req.getParameter(parameters[i].getName());;
                    }

                }
                try {
                    Object returnValue = method.invoke(controllerBeanObj, values);
                    if(returnValue.toString().startsWith("redirect:")){
                        String redirectStr = returnValue.toString().substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    }else {
                        this.processTemplate(returnValue.toString(),req,resp);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                return;
            }
        }


    }
}
