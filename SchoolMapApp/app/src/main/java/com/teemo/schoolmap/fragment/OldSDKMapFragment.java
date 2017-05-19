package com.teemo.schoolmap.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.activity.WeatherActivity;
import com.teemo.schoolmap.adapter.MenuListAdapter;
import com.teemo.schoolmap.application.bean.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.05.13 15:57
 * @description
 */
public class OldSDKMapFragment extends Fragment implements LocationSource, AMapLocationListener, AdapterView.OnItemClickListener, View.OnClickListener {
    private AMap aMap;
    private OnLocationChangedListener onLocationChangedListener;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption aMapLocationClientOption;
    private Context context;

    private ListView lvMenu;
    private ImageButton ibMore;

    private MenuListAdapter menuListAdapter;

    private MapView mapView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("MapFragment", "onCreateView");
        View parent = inflater.inflate(R.layout.fragment_map, container, false);
        init(parent, savedInstanceState);
        return parent;
    }

    private void init(View parent, Bundle savedInstanceState) {
        context = this.getContext();
        if (mapView == null) {
            mapView = (MapView) parent.findViewById(R.id.mp_main);
            mapView.onCreate(savedInstanceState);

            aMap = mapView.getMap();
            aMap.setLocationSource(this);
            // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
            aMap.setMyLocationEnabled(true);
            // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
            aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);

            lvMenu = (ListView) parent.findViewById(R.id.lv_menu);
            ibMore = (ImageButton) parent.findViewById(R.id.ib_more);

            List<Menu> menuList = new ArrayList<>();
            menuList.add(new Menu(R.drawable.weather, "查看天气", WeatherActivity.class));
            menuListAdapter = new MenuListAdapter(this.getContext(), menuList);
            lvMenu.setOnItemClickListener(this);
            lvMenu.setAdapter(menuListAdapter);
            ibMore.setOnClickListener(this);
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
        if (aMapLocationClient == null) {
            //初始化定位
            aMapLocationClient = new AMapLocationClient(context);
            //初始化定位参数
            aMapLocationClientOption = new AMapLocationClientOption();
            //设置定位回调监听
            aMapLocationClient.setLocationListener(this);
            //设置为高精度定位模式
            aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            aMapLocationClient.setLocationOption(aMapLocationClientOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            aMapLocationClient.startLocation();//启动定位
        }
    }

    @Override
    public void deactivate() {
        onLocationChangedListener = null;
        if (aMapLocationClient != null) {
            aMapLocationClient.stopLocation();
            aMapLocationClient.onDestroy();
        }
        aMapLocationClient = null;

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (onLocationChangedListener != null && aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                onLocationChangedListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AMapErr", errText);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mapView = null;
        if (null != aMapLocationClient) {
            aMapLocationClient.stopLocation();
            aMapLocationClient.onDestroy();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_more:
                lvMenu.setVisibility(lvMenu.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_menu:
                Log.i("onItemClick", position + "");
                startActivity(new Intent(OldSDKMapFragment.this.getContext(), ((Menu) menuListAdapter.getItem(position)).getClazz()));
                OldSDKMapFragment.this.getActivity().finish();
                break;
        }
    }
}
