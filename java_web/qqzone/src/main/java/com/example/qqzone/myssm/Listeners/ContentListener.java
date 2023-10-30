package com.example.qqzone.myssm.Listeners;

import com.example.qqzone.myssm.ioc.BeanFactory;
import com.example.qqzone.myssm.ioc.ClassPathXmlContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContentListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String contextConfigLocatio = servletContext.getInitParameter("contextConfigLocatio");
        BeanFactory beanFactory = new ClassPathXmlContext(contextConfigLocatio);
        servletContext.setAttribute("beanFactory",beanFactory);
    }
}
