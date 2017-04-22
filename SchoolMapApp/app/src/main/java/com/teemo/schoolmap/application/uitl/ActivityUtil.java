package com.teemo.schoolmap.application.uitl;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;


/**
 * Created by Teemo on 2017/3/19.
 * ClassName: SchoolMapApp
 * Description: 封装 AppCompatActivity 常用的方法
 */
public class ActivityUtil {
    /**
     * 全屏并且隐藏状态栏
     *
     * @param activity activity
     */
    public static void fullScreenWithNoTitle(Activity activity) {
        fullScreen(activity);
        noTitle(activity);
    }

    /**
     * 全屏
     *
     * @param activity activity
     */
    private static void fullScreen(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 去掉标题栏
     *
     * @param activity activity
     */
    public static void noTitle(Activity activity) {
        if (activity instanceof AppCompatActivity) {
            ((AppCompatActivity) activity).supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        } else {
            activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
    }
}
