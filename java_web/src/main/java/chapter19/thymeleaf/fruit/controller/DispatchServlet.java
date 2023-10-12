package chapter19.thymeleaf.fruit.controller;

import common.myssm.myspringmvc.DispatchViewBaseServlet;
import common.myssm.util.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DispatchServlet extends DispatchViewBaseServlet {
    private Map<String,Object> beanMap = new HashMap<>();
    @Override
    public void init() throws ServletException {
        super.init();

        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            document = reader.read(this.getClass().getClassLoader().getResourceAsStream("application.xml"));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        Element root = document.getRootElement();

        List<Element> beans = root.elements("bean");
        for (Element bean : beans) {
            String id = bean.attributeValue("id");
            String className = bean.attributeValue("class");

            Object controller = null;
            try {
                controller = Class.forName(className).newInstance();
                beanMap.put(id,controller);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String servletPath = req.getServletPath();
        servletPath = servletPath.substring(1);
        int lastDotIndex = servletPath.lastIndexOf(".do") ;
        servletPath = servletPath.substring(0,lastDotIndex);

        Object controllerBeanObj = beanMap.get(servletPath);

        String action = req.getParameter("action");
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
