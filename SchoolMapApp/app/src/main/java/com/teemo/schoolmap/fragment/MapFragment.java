package com.teemo.schoolmap.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.common.config.MapConfig;
import com.teemo.schoolmap.common.uitl.GpsUtil;
import com.teemo.schoolmap.common.uitl.InternetUtil;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/3/30 20:02
 * @description 地图功能 fragment
 */
public class MapFragment extends Fragment{
    private MapView mapView;
    private AMap aMap;
    private UiSettings uiSettings;
    private MyLocationStyle locationStyle;

    private static boolean isNetworkConnected = false;
    private static boolean isGpsConnected = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_map, container, false);
        init(parent, savedInstanceState);
        return parent;
    }

    /**
     * 初始化（仅初始化一次）
     *
     * @param parent             父View
     * @param savedInstanceState savedInstanceState
     */
    private void init(View parent, Bundle savedInstanceState) {
        if (mapView == null) {
            // 检查当前网络状态
            checkLocationModel();
            // 初始化mapView
            mapView = (MapView) parent.findViewById(R.id.mp_main);
            mapView.onCreate(savedInstanceState);
            // 初始化 AMap 设置默认缩放级别
            aMap = mapView.getMap();
            aMap.moveCamera(CameraUpdateFactory.zoomTo(MapConfig.MAP_DEFAULT_ZOOM));
            // 初始化 uiSetting 并且设置默认状态
            uiSettings = aMap.getUiSettings();
            uiSettings.setCompassEnabled(true);             // 指南针
            uiSettings.setScaleControlsEnabled(true);       // 缩放按钮
            uiSettings.setMyLocationButtonEnabled(true);    // 手动定位按钮
            // 初始化 locationStyle
            locationStyle = new MyLocationStyle();
            locationStyle.myLocationType(MapConfig.MAP_DEFAULT_LOCATION_TYPE);
            locationStyle.strokeWidth(0f);
        }
        // 启动定位
        startLocation();
    }

    /**
     * 启动定位
     */
    private void startLocation() {
        // TODO: 从配置中读取自定义配置
        aMap.setMyLocationStyle(locationStyle);
        aMap.setMyLocationEnabled(true);
    }

    /**
     * 检测当前网络和GPS状态，并且弹出提示框
     */
    private void checkLocationModel() {
        isNetworkConnected = InternetUtil.isNetworkConnected(getContext());
        isGpsConnected = GpsUtil.isGpsConnected(getContext());
        final AlertDialog.Builder gpsBuilder = new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.alert)
                .setTitle(R.string.gps_not_connected)
                .setMessage(R.string.open_gps)
                .setPositiveButton(R.string.confirm, null);
        AlertDialog.Builder networkBuilder = new AlertDialog.Builder(getContext())
                .setIcon(R.drawable.alert)
                .setTitle(R.string.network_not_connected)
                .setMessage(R.string.open_network)
                .setPositiveButton(R.string.confirm, null);
        if (!isNetworkConnected) {
            if (!isGpsConnected) {
                networkBuilder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gpsBuilder.show();
                    }
                });
            }
            networkBuilder.show();
        } else {
            if (!isGpsConnected) {
                gpsBuilder.show();
            }
        }

    }

    /**
     * 根据当前网络状态获取适当的定位模式
     *
     * @return 定位模式
     */
    private static AMapLocationClientOption.AMapLocationMode getCurrentLocationMode() {
        return isNetworkConnected && isGpsConnected ? AMapLocationClientOption.AMapLocationMode.Hight_Accuracy : isNetworkConnected ? AMapLocationClientOption.AMapLocationMode.Battery_Saving : AMapLocationClientOption.AMapLocationMode.Device_Sensors;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        aMap = null;
        uiSettings = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
