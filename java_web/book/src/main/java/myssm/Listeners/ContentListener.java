package myssm.Listeners;

import myssm.ioc.BeanFactory;
import myssm.ioc.ClassPathXmlContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ContentListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
        BeanFactory beanFactory = new ClassPathXmlContext(contextConfigLocation);
        servletContext.setAttribute("beanFactory",beanFactory);
    }
}
