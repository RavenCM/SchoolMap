package com.teemo.schoolmap.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceClient;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.activity.PoiActivity;
import com.teemo.schoolmap.activity.WeatherActivity;
import com.teemo.schoolmap.adapter.MenuListAdapter;
import com.teemo.schoolmap.application.bean.Menu;
import com.teemo.schoolmap.application.bean.Poi;
import com.teemo.schoolmap.application.config.MapConfig;
import com.teemo.schoolmap.application.config.UrlConfig;
import com.teemo.schoolmap.application.uitl.GpsUtil;
import com.teemo.schoolmap.application.uitl.HttpUtil;
import com.teemo.schoolmap.application.uitl.InternetUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/3/30 20:02
 * @description 地图功能 fragment
 */
public class MapFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, GeoFenceListener, AMap.OnMarkerClickListener {
    private MapView mapView;
    private AMap aMap;
    private UiSettings uiSettings;
    private MyLocationStyle locationStyle;

    private ListView lvMenu;
    private ImageButton ibMore;

    private MenuListAdapter menuListAdapter;

    private EditText etSearch;
    private ImageButton ibSearch;

    private static boolean isNetworkConnected = false;
    private static boolean isGpsConnected = false;
    //定义接收广播的action字符串
    public static final String GEOFENCE_BROADCAST_ACTION = "com.location.apis.geofencedemo.broadcast";

    private BroadcastReceiver geoFenceReceiver;

    private List<Poi> poiList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("MapFragment", "onCreateView");
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
            lvMenu = (ListView) parent.findViewById(R.id.lv_menu);
            ibMore = (ImageButton) parent.findViewById(R.id.ib_more);
            etSearch = (EditText) parent.findViewById(R.id.et_search);
            ibSearch = (ImageButton) parent.findViewById(R.id.ib_search);

            ibSearch.setOnClickListener(this);

            List<Menu> menuList = new ArrayList<>();
            menuList.add(new Menu(R.drawable.weather, "查看天气", WeatherActivity.class));
            menuList.add(new Menu(R.drawable.add, "发布事件", PoiActivity.class));
            menuListAdapter = new MenuListAdapter(this.getContext(), menuList);
            lvMenu.setOnItemClickListener(this);
            lvMenu.setAdapter(menuListAdapter);
            ibMore.setOnClickListener(this);

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
            locationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.location));
            locationStyle.anchor(0.5f, 1f);
            locationStyle.strokeWidth(0.1f);

            aMap.setOnMarkerClickListener(this);

            createGeoFence();

            createPoi();
        }
        // 启动定位
        startLocation();
    }

    private void createPoi() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = HttpUtil.getCall(UrlConfig.POI, null).execute();
                    if (200 == response.code()) {
                        ObjectMapper mapper = new ObjectMapper();
                        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Poi.class);
                        poiList = mapper.readValue(response.body().string(), javaType);
                        for (Poi poi : poiList){
                            LatLng latLng = new LatLng(poi.getLatitude(), poi.getLongitude());
                            Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(poi.getTitle()).snippet(poi.getContent()));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 创建地理围栏
     */
    private void createGeoFence() {
        GeoFenceClient geoFenceClient = new GeoFenceClient(this.getContext());
        geoFenceClient.setActivateAction(GeoFenceClient.GEOFENCE_IN);
        for (int i = 0; i < MapConfig.geoFenceInfos.length; i++) {
            geoFenceClient.addGeoFence(MapConfig.geoFenceInfos[i].dPoints, MapConfig.geoFenceInfos[i].customId);
        }
        geoFenceClient.setGeoFenceListener(this);
        geoFenceClient.createPendingIntent(GEOFENCE_BROADCAST_ACTION);
        geoFenceReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(GEOFENCE_BROADCAST_ACTION)) {
                    //解析广播内容
                    //获取Bundle
                    Bundle bundle = intent.getExtras();
                    //获取围栏行为：
                    int status = bundle.getInt(GeoFence.BUNDLE_KEY_FENCESTATUS);
                    //获取自定义的围栏标识：
                    String customId = bundle.getString(GeoFence.BUNDLE_KEY_CUSTOMID);
                    Toast.makeText(MapFragment.this.getContext(), "进入：" + customId, Toast.LENGTH_SHORT).show();
                    Log.i("geoFenceReceiver", "进入：" + customId);
                    //获取围栏ID:
                    String fenceId = bundle.getString(GeoFence.BUNDLE_KEY_FENCEID);
                    //获取当前有触发的围栏对象：
                    GeoFence fence = bundle.getParcelable(GeoFence.BUNDLE_KEY_FENCE);

                }
            }
        };
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(GEOFENCE_BROADCAST_ACTION);
        this.getActivity().registerReceiver(geoFenceReceiver, filter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_more:
                lvMenu.setVisibility(lvMenu.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.ib_search:

                break;
        }
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
    public static AMapLocationClientOption.AMapLocationMode getCurrentLocationMode() {
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
        this.getActivity().unregisterReceiver(geoFenceReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        mapView = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.lv_menu:
                Log.i("onItemClick", position + "");
                Intent intent = new Intent(MapFragment.this.getContext(), ((Menu) menuListAdapter.getItem(position)).getClazz());
                if (position == 1) {
                    Location location = aMap.getMyLocation();
                    intent.putExtra("latitude", location.getLatitude());
                    intent.putExtra("longitude", location.getLongitude());
                }
                startActivity(intent);
                MapFragment.this.getActivity().finish();
                break;
        }
    }

    @Override
    public void onGeoFenceCreateFinished(List<GeoFence> list, int i, String s) {
        if (i == GeoFence.ADDGEOFENCE_SUCCESS) {//判断围栏是否创建成功
//            Toast.makeText(MapFragment.this.getContext(), "添加围栏成功 !!", Toast.LENGTH_SHORT).show();
            //geoFenceList就是已经添加的围栏列表，可据此查看创建的围栏
        } else {
            Toast.makeText(MapFragment.this.getContext(), "添加围栏失败 !!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
