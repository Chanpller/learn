package com.example.java_web;

import com.example.java_web.model.Student;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class TestBeanUtils {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Map<String,Object> map = new HashMap<>();
        map.put("123","123");
        map.put("username","张三");
        BeanUtils.populate(new Student(),map);
    }
}
