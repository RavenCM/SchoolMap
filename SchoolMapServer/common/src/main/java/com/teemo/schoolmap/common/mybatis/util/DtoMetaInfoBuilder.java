package com.teemo.schoolmap.common.mybatis.util;

import com.teemo.schoolmap.common.mybatis.component.DtoMetaInfo;

/**
 * @author qingsheng.chen@hand-china.com	2017/4/10 19:53
 * @version 1.0
 * @name schoolmap-server
 * @description
 */
public class DtoMetaInfoBuilder {

    public static DtoMetaInfo build(Object dto){
        DtoMetaInfo dtoMetaInfo = new DtoMetaInfo();
        dtoMetaInfo = ReflectUtil.getMetaInfo(dto, dtoMetaInfo);
        return dtoMetaInfo;
    }
}
