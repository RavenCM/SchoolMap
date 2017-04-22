package com.teemo.schoolmap.common.component;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 15:36
 * @version 1.0
 * @name schoolmap-server
 * @description 服务统一返回数据格式
 */
@Deprecated
public class ResponseData {

    /**
     * 响应异常信息
     * 当 success 为 true 时，该字段为 null
     * 当 success 为 false 时，该字段为 异常信息
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object message;

    /**
     * 响应数据
     * 当 success 为 true 时，该字段为 响应数据
     * 当 success 为 false 时，该字段为 null
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<?> data;

    /**
     * 响应是否成功
     * 当成功请求到数据时返回 true
     * 当请求出现异常时返回 false
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean success;

    /**
     * 响应数据总数
     * 当 success 为 true 时，并且无分页，该字段为 data 字段的数量
     * 当 success 为 true 时，并且有分页，该字段为请求无分页时的所有数据
     * 当 success 为 false 时，该字段为 null
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long total;

    public ResponseData() {
    }

    public ResponseData(boolean success) {
        this.success = success;
    }

    public ResponseData(List<?> data) {
        this(true);
        setData(data);
        if (data instanceof Page) {
            setTotal(((Page<?>) data).getTotal());
        } else {
            setTotal((long) data.size());
        }
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    private void setData(List<?> data) {
        this.data = data;
    }

    private void setSuccess(boolean success) {
        this.success = success;
    }

    private void setTotal(Long total) {
        this.total = total;
    }

    public Object getMessage() {
        return message;
    }

    public List<?> getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }

    public Long getTotal() {
        return total;
    }
}
