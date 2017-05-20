package com.teemo.schoolmap.user.service.impl;

import com.teemo.schoolmap.common.mybatis.mapper.CommonMapper;
import com.teemo.schoolmap.user.dto.Poi;
import com.teemo.schoolmap.user.service.IPoiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Teemo on 2017.05.18.
 */
@Service
public class PoiServiceImpl implements IPoiService {

    @Autowired
    private CommonMapper<Poi> poiCommonMapper;

    @Override
    public List<Poi> getPoi() {
        Poi poi = new Poi();
        poi.setIsEnable(1);
        return poiCommonMapper.select(poi);
    }

    @Override
    public List<Poi> getPoi(int userId) {
        Poi poi = new Poi();
        poi.setCreatedBy(userId);
        return poiCommonMapper.select(poi);
    }

    @Override
    @Transactional
    public boolean addPou(Poi poi) {
        poiCommonMapper.insert(poi);
        return true;
    }

    @Override
    @Transactional
    public boolean updatePoi(Poi poi) {
        poiCommonMapper.updateSelective(poi);
        return true;
    }
}
