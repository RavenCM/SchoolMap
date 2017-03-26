package com.teemo.schoolmap.common.uitl;

import android.app.Activity;
import android.content.SharedPreferences;

import com.teemo.schoolmap.common.application.User;
import com.teemo.schoolmap.common.config.SharedPreferencesConfig;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/3/26 16:33
 * @description
 */
public class SharedPreferencesUtil {


    public static void writeUserInfo(Activity activity, User user){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SharedPreferencesConfig.USER_INFO_NAME, SharedPreferencesConfig.USER_INFO_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", user.getUserId());
        editor.putString("userTypeCode", user.getUserTypeCode());
        editor.putString("userTypeValue", user.getUserTypeValue());
        editor.putString("description", user.getDescription());
        editor.putString("password", user.getPassword());
        editor.putString("email", user.getEmail());
        editor.putString("username", user.getUsername());
        editor.putString("sex", user.getSex());
        editor.putString("telephone", user.getTelephone());
        editor.putString("avatarPath", user.getAvatarPath());
        editor.putString("signature", user.getSignature());
        editor.apply();
    }

    public static User readUserInfo(Activity activity){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SharedPreferencesConfig.USER_INFO_NAME, SharedPreferencesConfig.USER_INFO_MODE);
        User user = User.getInstance();
        user.setUserId(sharedPreferences.getInt("userId", 0));
        user.setUserTypeCode(sharedPreferences.getString("userTypeCode", null));
        user.setUserTypeValue(sharedPreferences.getString("userTypeValue", null));
        user.setDescription(sharedPreferences.getString("description", null));
        user.setPassword(sharedPreferences.getString("password", null));
        user.setEmail(sharedPreferences.getString("email", null));
        user.setUsername(sharedPreferences.getString("username", null));
        user.setSex(sharedPreferences.getString("sex", null));
        user.setTelephone(sharedPreferences.getString("telephone", null));
        user.setAvatarPath(sharedPreferences.getString("avatarPath", null));
        user.setSignature(sharedPreferences.getString("signature", null));
        return user;
    }

}
