package com.teemo.schoolmap.application;

import android.app.ActivityManager;
import android.app.Application;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;

import java.util.List;


/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/17 14:45
 * @description 公共初始化
 */
public class SchoolMapApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initEasemob();
        initGalleryFinal();
    }

    private void initGalleryFinal() {
        Fresco.initialize(getApplicationContext());
    }

    // 初始化 Easemob
    private void initEasemob() {
        Log.i("Easemob", "Start Init");
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        options.setAcceptInvitationAlways(true);

        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(this.getPackageName())) {
            Log.e("initEasemob", "enter the service process!");
            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        //初始化
        EMClient.getInstance().init(this, options);
        EaseUI.getInstance().init(this, null);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(false);
        Log.i("Easemob", "End Init");
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        for (Object aL : l) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (aL);
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                }
            } catch (Exception e) {
                Log.d("Process", "Error>> :" + e.toString());
            }
        }
        return processName;
    }

}
