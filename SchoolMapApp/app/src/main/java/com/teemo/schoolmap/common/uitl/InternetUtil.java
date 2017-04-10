package com.teemo.schoolmap.common.uitl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/3/31 10:13
 * @description
 */
public class InternetUtil {

    /**
     * 获取当前网络状况
     * @param context context
     * @return 当前是否有可用网络
     */
    public static boolean isNetworkConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * 打开移动数据
     * @param context context
     * @return 打开是否成功
     */
    public static boolean openNetworkConnected(Context context) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<?> telephonyManagerClass = telephonyManager.getClass();
        Class<?>[] argsClass = new Class[1];
        argsClass[0] = boolean.class;
        Method method = telephonyManagerClass.getMethod("setDataEnabled", argsClass);
        method.invoke(telephonyManager, true);
        return true;
    }
}
