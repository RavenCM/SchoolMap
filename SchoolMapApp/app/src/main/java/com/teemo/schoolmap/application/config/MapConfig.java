package com.teemo.schoolmap.application.config;

import com.amap.api.location.DPoint;
import com.amap.api.maps.model.MyLocationStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/9 14:14
 * @description
 */
public class MapConfig {
    public static final int MAP_DEFAULT_ZOOM = 17;
    public static final int MAP_DEFAULT_LOCATION_TYPE = MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE;

    public static GeoFenceInfo[] geoFenceInfos = new GeoFenceInfo[9];

    static {
        String customId = "第五教学楼";
        List<DPoint> dPoints = new ArrayList<>();
        dPoints.add(new DPoint(27.902382, 112.923261));
        dPoints.add(new DPoint(27.902885, 112.923255));
        dPoints.add(new DPoint(27.902908, 112.924084));
        dPoints.add(new DPoint(27.902417, 112.924087));
        geoFenceInfos[0] = new GeoFenceInfo(dPoints, customId);

        customId = "土木楼";
        dPoints = new ArrayList<>();
        dPoints.add(new DPoint(27.902879, 112.923098));
        dPoints.add(new DPoint(27.901846, 112.923077));
        dPoints.add(new DPoint(27.901853, 112.922357));
        dPoints.add(new DPoint(27.902538, 112.922291));
        dPoints.add(new DPoint(27.90284, 112.922746));
        geoFenceInfos[1] = new GeoFenceInfo(dPoints, customId);

        customId = "第二体育馆";
        dPoints = new ArrayList<>();
        dPoints.add(new DPoint(27.901124, 112.923372));
        dPoints.add(new DPoint(27.900719, 112.923376));
        dPoints.add(new DPoint(27.900715, 112.923937));
        dPoints.add(new DPoint(27.901135, 112.923891));
        geoFenceInfos[2] = new GeoFenceInfo(dPoints, customId);

        customId = "第一教学楼";
        dPoints = new ArrayList<>();
        dPoints.add(new DPoint(27.895577, 112.923187));
        dPoints.add(new DPoint(27.895576, 112.923882));
        dPoints.add(new DPoint(27.89511, 112.923906));
        dPoints.add(new DPoint(27.895111, 112.923202));
        geoFenceInfos[3] = new GeoFenceInfo(dPoints, customId);

        customId = "第二教学楼";
        dPoints = new ArrayList<>();
        dPoints.add(new DPoint(27.895078, 112.92195));
        dPoints.add(new DPoint(27.895591, 112.921957));
        dPoints.add(new DPoint(27.895563, 112.921261));
        dPoints.add(new DPoint(27.895065, 112.921277));
        geoFenceInfos[4] = new GeoFenceInfo(dPoints, customId);

        customId = "南校图书馆";
        dPoints = new ArrayList<>();
        dPoints.add(new DPoint(27.896133, 112.922074));
        dPoints.add(new DPoint(27.896158, 112.923035));
        dPoints.add(new DPoint(27.895422, 112.923093));
        dPoints.add(new DPoint(27.895431, 112.922118));
        geoFenceInfos[5] = new GeoFenceInfo(dPoints, customId);

        customId = "第三教学楼";
        dPoints = new ArrayList<>();
        dPoints.add(new DPoint(27.897612, 112.923132));
        dPoints.add(new DPoint(27.897104, 112.923168));
        dPoints.add(new DPoint(27.897104, 112.923963));
        dPoints.add(new DPoint(27.897643, 112.923926));
        geoFenceInfos[6] = new GeoFenceInfo(dPoints, customId);

        customId = "四区宿舍";
        dPoints = new ArrayList<>();
        dPoints.add(new DPoint(27.902465, 112.924761));
        dPoints.add(new DPoint(27.902488, 112.924187));
        dPoints.add(new DPoint(27.903504, 112.924143));
        dPoints.add(new DPoint(27.904014, 112.924794));
        dPoints.add(new DPoint(27.9041, 112.925905));
        dPoints.add(new DPoint(27.9036, 112.925667));
        dPoints.add(new DPoint(27.903476, 112.925358));
        dPoints.add(new DPoint(27.903139, 112.925067));
        geoFenceInfos[7] = new GeoFenceInfo(dPoints, customId);

        customId = "南校逸夫教学楼";
        dPoints = new ArrayList<>();
        dPoints.add(new DPoint(27.902052, 112.921024));
        dPoints.add(new DPoint(27.902216, 112.921897));
        dPoints.add(new DPoint(27.902198, 112.922046));
        dPoints.add(new DPoint(27.901516, 112.922072));
        dPoints.add(new DPoint(27.901488, 112.921146));
        geoFenceInfos[8] = new GeoFenceInfo(dPoints, customId);
    }

    public static class GeoFenceInfo{
        public List<DPoint> dPoints;
        public String customId;

        GeoFenceInfo(List<DPoint> dPoints, String customId) {
            this.dPoints = dPoints;
            this.customId = customId;
        }
    }
}
