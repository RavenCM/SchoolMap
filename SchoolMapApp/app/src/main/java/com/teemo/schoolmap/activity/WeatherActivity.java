package com.teemo.schoolmap.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.weather.LocalWeatherForecastResult;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.LocalWeatherLiveResult;
import com.amap.api.services.weather.WeatherSearch;
import com.amap.api.services.weather.WeatherSearchQuery;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.application.uitl.ActivityUtil;
import com.teemo.schoolmap.fragment.MapFragment;

import xyz.matteobattilana.library.Common.Constants;
import xyz.matteobattilana.library.WeatherView;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.05.04 12:09
 * @description
 */
public class WeatherActivity extends AppCompatActivity implements AMapLocationListener, WeatherSearch.OnWeatherSearchListener {

    public AMapLocationClient aMapLocationClient = null;
    public AMapLocationClientOption aMapLocationClientOption = null;
    private WeatherView mWeatherView;

    private TextView tvWeather;
    private TextView tvWind;
    private TextView tvTemperature;
    private TextView tvHumidity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.fullScreenWithNoTitle(this);
        setContentView(R.layout.activity_weather);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        init();
    }

    private void init() {
        aMapLocationClient = new AMapLocationClient(getApplicationContext());
        aMapLocationClient.setLocationListener(this);
        aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClientOption.setLocationMode(MapFragment.getCurrentLocationMode());
        aMapLocationClientOption.setOnceLocation(true);
        aMapLocationClientOption.setOnceLocationLatest(true);
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.startLocation();

        mWeatherView = (WeatherView) findViewById(R.id.weather);
        tvWeather = (TextView) findViewById(R.id.tv_weather);
        tvWind = (TextView) findViewById(R.id.tv_wind);
        tvTemperature = (TextView) findViewById(R.id.tv_temperature);
        tvHumidity = (TextView) findViewById(R.id.tv_humidity);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。
                //Optional
                WeatherSearchQuery query = new WeatherSearchQuery(aMapLocation.getCity(), WeatherSearchQuery.WEATHER_TYPE_LIVE);
                WeatherSearch search = new WeatherSearch(WeatherActivity.this);
                search.setOnWeatherSearchListener(this);
                search.setQuery(query);
                search.searchWeatherAsyn(); //异步搜索
            } else {
                Toast.makeText(WeatherActivity.this, "获取位置信息失败！原因：" + aMapLocation.getErrorInfo(), Toast.LENGTH_SHORT).show();
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AMapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }

    }

    @Override
    public void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i) {
        if (i == 1000) {
            if (localWeatherLiveResult != null && localWeatherLiveResult.getLiveResult() != null) {
                LocalWeatherLive weatherLive = localWeatherLiveResult.getLiveResult();
                tvWeather.setText(weatherLive.getWeather());
                WeatherViewOption weatherViewOption = new WeatherViewOption(weatherLive.getWeather(), weatherLive.getWindPower());
                mWeatherView.setWeather(weatherViewOption.weatherStatus)
                        .setCurrentLifeTime(3000)
                        .setCurrentFadeOutTime(1000)
                        .setCurrentParticles(weatherViewOption.particles)
                        .setFPS(9)
                        .setCurrentAngle(weatherViewOption.angle)
                        .setOrientationMode(Constants.orientationStatus.ENABLE)
                        .startAnimation();
                tvWind.setText("风力和风向：" + weatherLive.getWindPower() + " " + weatherLive.getWindDirection());
                tvTemperature.setText("温度：" + weatherLive.getTemperature() + "°");
                tvHumidity.setText("湿度："  + weatherLive.getHumidity() + "%");
            }
        } else {
            Toast.makeText(WeatherActivity.this, "获取天气失败！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i) {

    }

    private class WeatherViewOption {
        private Constants.weatherStatus weatherStatus;
        private int particles;
        private int angle;

        WeatherViewOption(String weather, String windPower) {
            switch (weather) {
                case "大雨":
                case "暴雨":
                case "大暴雨":
                case "特大暴雨":
                case "中雨-大雨":
                case "大雨-暴雨":
                case "暴雨-大暴雨":
                case "大暴雨-特大暴雨":
                    weatherStatus = Constants.weatherStatus.RAIN;
                    particles = 100;
                    break;
                case "阵雨":
                case "雷阵雨":
                case "雷阵雨并伴有冰雹":
                case "中雨":
                    weatherStatus = Constants.weatherStatus.RAIN;
                    particles = 50;
                    break;
                case "小雨":
                case "冻雨":
                case "小雨-中雨":
                case "中雪-大雪":
                case "大雪-暴雪":
                    weatherStatus = Constants.weatherStatus.RAIN;
                    particles = 25;
                    break;
                case "大雪":
                case "暴雪":
                case "小雪-中雪":
                    weatherStatus = Constants.weatherStatus.SNOW;
                    particles = 100;
                    break;
                case "雨夹雪":
                case "阵雪":
                case "中雪":
                    weatherStatus = Constants.weatherStatus.SNOW;
                    particles = 50;
                    break;
                case "小雪":
                case "弱高吹雪":
                    weatherStatus = Constants.weatherStatus.SNOW;
                    particles = 25;
                    break;
                default:
                    weatherStatus = Constants.weatherStatus.SUN;
                    particles = 0;
                    break;
            }
            if (windPower.equals("≤3")) {
                angle = 0;
            } else if (Integer.parseInt(windPower) < 6) {
                angle = 15;
            } else {
                angle = 30;
            }
        }
    }
}
