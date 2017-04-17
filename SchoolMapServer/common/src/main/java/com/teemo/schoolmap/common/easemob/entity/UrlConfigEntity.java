package com.teemo.schoolmap.common.easemob.entity;

import org.springframework.http.HttpMethod;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/14 16:04
 * @version 1.0
 * @name schoolmap-server
 * @description
 */
public class UrlConfigEntity {

    /**
     * url path
     */
    private String path;

    /**
     * url method
     */
    private HttpMethod method;

    /**
     * url request type
     */
    private Object request;

    /**
     * url response type
     */
    private Class responseType;

    public UrlConfigEntity(String path, HttpMethod method) {
        this(path, method, null, null);
    }

    public UrlConfigEntity(String path, HttpMethod method, Object request, Class responseType) {
        this.path = path;
        this.method = method;
        this.request = request;
        this.responseType = responseType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public Object getRequest() {
        return request;
    }

    public void setRequest(Object request) {
        this.request = request;
    }

    public Class getResponseType() {
        return responseType;
    }

    public void setResponseType(Class responseType) {
        this.responseType = responseType;
    }
}
