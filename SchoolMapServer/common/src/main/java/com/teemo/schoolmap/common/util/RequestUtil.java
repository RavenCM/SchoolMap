package com.teemo.schoolmap.common.util;

import com.teemo.schoolmap.common.filter.RequestFilter;
/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 9:39
 * @version 1.0
 * @name schoolmap-server
 * @description
 */
public class RequestUtil {

    /**
     * 获取用户ID
     *
     * @return 用户ID
     */
    public int getUserId() {
        try {
            return Integer.parseInt(RequestFilter.requestHolder.get().getHeader("userid"));
        } catch (Exception e){
            throw new RuntimeException("No userId found in request.");
        }
    }
}
