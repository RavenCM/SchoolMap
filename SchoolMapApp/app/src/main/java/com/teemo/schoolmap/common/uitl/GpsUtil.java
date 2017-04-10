package com.teemo.schoolmap.common.uitl;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/3/31 10:25
 * @description
 */
public class GpsUtil {

    /**
     * 判断GPS或者AGPS是否打开
     * @param context context
     * @return GPS或者AGPS是否打开
     */
    public static boolean isGpsConnected(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     * 打开GPS开关
     * @param context context
     * @return 打开是否成功
     */
    public static boolean openGpsConnected(Context context) {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        PendingIntent.getBroadcast(context, 0, gpsIntent, 0);
        return true;
    }
}
