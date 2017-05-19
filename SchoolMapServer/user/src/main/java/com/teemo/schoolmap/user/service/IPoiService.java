package com.teemo.schoolmap.user.service;

import com.teemo.schoolmap.user.dto.Poi;

import java.util.List;

/**
 * Created by Teemo on 2017.05.18.
 */
public interface IPoiService {

    List<Poi> getPoi();

    boolean addPou(Poi poi);
}
