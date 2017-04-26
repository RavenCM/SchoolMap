package com.teemo.schoolmap.application.bean;

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

    public Menu(int icon, String title, Class clazz) {
        this.icon = icon;
        this.title = title;
        this.clazz = clazz;
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
}