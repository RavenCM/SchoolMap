package com.teemo.schoolmap.common.util;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/13 10:07
 * @version 1.0
 * @name schoolmap-server
 * @description
 */
public class TimeUtil {
    /**
     * 获取当前时间
     * @return 当前时间
     */
    public static Timestamp getCurrentDate() {
        return new Timestamp(new Date().getTime());
    }
}
