package com.example.java_web.model;
import java.util.*;

public class Person {
    org.apache.jasper.runtime.PageContextImpl pageContext;
    com.sun.el.ExpressionFactoryImpl expressionFactory;
    private String name;
    private String[] phones;
    private List<String> cities;
    private Map<String,Object> map;
    public int getAge() {
        return 18;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getPhones() {
        return phones;
    }

    public void setPhones(String[] phones) {
        this.phones = phones;
    }

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

}
