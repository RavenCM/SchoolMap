package com.teemo.schoolmap.application.uitl;

import android.app.Activity;

import com.teemo.schoolmap.application.bean.User;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/17 16:35
 * @description 用户操作相关的操作
 */
public class UserUtil {

    /**
     * 判断是否已经有保存的用户信息
     *
     * @return 是否已经有保存的用户信息
     */
    public static boolean hasSavedUser(Activity activity) {
        User user = SharedPreferencesUtil.readUserInfo(activity);
        return !"".equals(user.getPassword()) && !"".equals(user.getUserBasisInformation().getEmail());
    }

    /**
     * 判断用户是否在自己的服务器登录过
     *
     * @return 用户是否在自己的服务器登录过
     */
    public static boolean isAlreadyLoggedIn(Activity activity) {
        User user = SharedPreferencesUtil.readUserInfo(activity);
        return user.getUserId() != null && user.getUserId() != -1;
    }
}
