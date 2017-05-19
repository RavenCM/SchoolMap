package com.teemo.schoolmap.application.bean;

import java.util.Map;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/24 14:44
 * @description
 */
public class Menu{
    private int icon;
    private String title;
    private Class clazz;
    private Map<String, Object> parameterMap;

    public Menu(int icon, String title, Class clazz) {
        this.icon = icon;
        this.title = title;
        this.clazz = clazz;
    }

    public Menu(int icon, String title, Class clazz, Map<String, Object> parameterMap) {
        this.icon = icon;
        this.title = title;
        this.clazz = clazz;
        this.parameterMap = parameterMap;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public Map<String, Object> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, Object> parameterMap) {
        this.parameterMap = parameterMap;
    }
}