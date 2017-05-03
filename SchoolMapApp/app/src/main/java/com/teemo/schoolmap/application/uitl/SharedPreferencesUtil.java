package com.teemo.schoolmap.application.uitl;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.teemo.schoolmap.application.bean.User;
import com.teemo.schoolmap.application.config.SharedPreferencesConfig;

import java.sql.Timestamp;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/3/26 16:33
 * @description
 */
public class SharedPreferencesUtil {


    public static void writeUserInfo(Activity activity, User user) {
        if (user == null) {
            Log.i("writeUserInfo", "User is null!");
            return;
        }
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SharedPreferencesConfig.USER_INFO_NAME, SharedPreferencesConfig.USER_INFO_MODE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // user
        editor.putInt("userId", user.getUserId() != null ? user.getUserId() : -1);
        editor.putInt("userTypeId", user.getUserTypeId() != null ? user.getUserTypeId() : -1);
        editor.putString("password", user.getPassword() != null ? user.getPassword() : "");
        editor.putInt("objectVersionNumber", user.getObjectVersionNumber());
        editor.putInt("createdBy", user.getCreatedBy());
        editor.putLong("creationDate", user.getCreationDate() != null ? user.getCreationDate().getTime() : 0);
        editor.putInt("lastUpdatedBy", user.getLastUpdatedBy());
        editor.putLong("lastUpdatedDate", user.getLastUpdatedDate() != null ? user.getLastUpdatedDate().getTime() : 0);
        editor.putInt("isEnable", user.getIsEnable());

        // userBasisInformation
        if (user.getUserBasisInformation() != null) {
            editor.putInt("userBasisInformation.userBasisInformationId", user.getUserBasisInformation().getUserBasisInformationId() != null ? user.getUserBasisInformation().getUserBasisInformationId() : -1);
            editor.putString("userBasisInformation.email", user.getUserBasisInformation().getEmail() != null ? user.getUserBasisInformation().getEmail() : "");
            editor.putString("userBasisInformation.username", user.getUserBasisInformation().getUsername() != null ? user.getUserBasisInformation().getUsername() : "");
            editor.putString("userBasisInformation.sex", user.getUserBasisInformation().getSex() != null ? user.getUserBasisInformation().getSex() : "");
            editor.putInt("userBasisInformation.objectVersionNumber", user.getUserBasisInformation().getObjectVersionNumber());
            editor.putInt("userBasisInformation.createdBy", user.getUserBasisInformation().getCreatedBy());
            editor.putLong("userBasisInformation.creationDate", user.getUserBasisInformation().getCreationDate() != null ? user.getUserBasisInformation().getCreationDate().getTime() : 0);
            editor.putInt("userBasisInformation.lastUpdatedBy", user.getUserBasisInformation().getLastUpdatedBy());
            editor.putLong("userBasisInformation.lastUpdatedDate", user.getUserBasisInformation().getLastUpdatedDate() != null ? user.getUserBasisInformation().getLastUpdatedDate().getTime() : 0);
            editor.putInt("userBasisInformation.isEnable", user.getUserBasisInformation().getIsEnable());
        }

        // userExtraInformation
        if (user.getUserExtraInformation() != null) {
            editor.putInt("userExtraInformation.userExtraInformationId", user.getUserExtraInformation().getUserExtraInformationId() != null ? user.getUserExtraInformation().getUserExtraInformationId() : -1);
            editor.putString("userExtraInformation.telephone", user.getUserExtraInformation().getTelephone() != null ? user.getUserExtraInformation().getTelephone() : "");
            editor.putInt("userExtraInformation.userAddressId", user.getUserExtraInformation().getUserAddressId() != null ? user.getUserExtraInformation().getUserAddressId() : -1);
            editor.putString("userExtraInformation.avatarPath", user.getUserExtraInformation().getAvatarPath() != null ? user.getUserExtraInformation().getAvatarPath() : "");
            editor.putString("userExtraInformation.signature", user.getUserExtraInformation().getSignature() != null ? user.getUserExtraInformation().getSignature() : "");
            editor.putInt("userExtraInformation.objectVersionNumber", user.getUserExtraInformation().getObjectVersionNumber());
            editor.putInt("userExtraInformation.createdBy", user.getUserExtraInformation().getCreatedBy());
            editor.putLong("userExtraInformation.creationDate", user.getUserExtraInformation().getCreationDate() != null ? user.getUserExtraInformation().getCreationDate().getTime() : 0);
            editor.putInt("userExtraInformation.lastUpdatedBy", user.getUserExtraInformation().getLastUpdatedBy());
            editor.putLong("userExtraInformation.lastUpdatedDate", user.getUserExtraInformation().getLastUpdatedDate() != null ? user.getUserExtraInformation().getLastUpdatedDate().getTime() : 0);
            editor.putInt("userExtraInformation.isEnable", user.getUserExtraInformation().getIsEnable());

            if (user.getUserExtraInformation().getUserAddress() != null) {
                // userExtraInformation.userAddress
                editor.putInt("userExtraInformation.userAddress.userAddressId", user.getUserExtraInformation().getUserAddress().getUserAddressId() != null ? user.getUserExtraInformation().getUserAddress().getUserAddressId() : -1);
                editor.putString("userExtraInformation.userAddress.country", user.getUserExtraInformation().getUserAddress().getCountry() != null ? user.getUserExtraInformation().getUserAddress().getCountry() : "");
                editor.putString("userExtraInformation.userAddress.province", user.getUserExtraInformation().getUserAddress().getProvince() != null ? user.getUserExtraInformation().getUserAddress().getProvince() : "");
                editor.putString("userExtraInformation.userAddress.city", user.getUserExtraInformation().getUserAddress().getCity() != null ? user.getUserExtraInformation().getUserAddress().getCity() : "");
                editor.putInt("userExtraInformation.userAddress.objectVersionNumber", user.getUserExtraInformation().getUserAddress().getObjectVersionNumber());
                editor.putInt("userExtraInformation.userAddress.createdBy", user.getUserExtraInformation().getUserAddress().getCreatedBy());
                editor.putLong("userExtraInformation.userAddress.creationDate", user.getUserExtraInformation().getUserAddress().getCreationDate() != null ? user.getUserExtraInformation().getUserAddress().getCreationDate().getTime() : 0);
                editor.putInt("userExtraInformation.userAddress.lastUpdatedBy", user.getUserExtraInformation().getUserAddress().getLastUpdatedBy());
                editor.putLong("userExtraInformation.userAddress.lastUpdatedDate", user.getUserExtraInformation().getUserAddress().getLastUpdatedDate() != null ? user.getUserExtraInformation().getUserAddress().getLastUpdatedDate().getTime() : 0);
                editor.putInt("userExtraInformation.userAddress.isEnable", user.getUserExtraInformation().getUserAddress().getIsEnable());
            }
        }
        editor.commit();
    }

    public static User readUserInfo(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SharedPreferencesConfig.USER_INFO_NAME, SharedPreferencesConfig.USER_INFO_MODE);
        User user = User.getInstance();
        // user
        user.setUserId(sharedPreferences.getInt("userId", -1));
        user.setUserTypeId(sharedPreferences.getInt("userTypeId", -1));
        user.setPassword(sharedPreferences.getString("password", ""));
        user.setObjectVersionNumber(sharedPreferences.getInt("objectVersionNumber", -1));
        user.setCreatedBy(sharedPreferences.getInt("createdBy", -1));
        user.setCreationDate(new Timestamp(sharedPreferences.getLong("creationDate", 0)));
        user.setLastUpdatedBy(sharedPreferences.getInt("lastUpdatedBy", -1));
        user.setLastUpdatedDate(new Timestamp(sharedPreferences.getLong("lastUpdatedDate", 0)));
        user.setIsEnable(sharedPreferences.getInt("isEnable", -1));

        // userBasisInformation
        user.getUserBasisInformation().setUserBasisInformationId(sharedPreferences.getInt("userBasisInformation.userBasisInformationId", -1));
        user.getUserBasisInformation().setUserId(user.getUserId());
        user.getUserBasisInformation().setEmail(sharedPreferences.getString("userBasisInformation.email", ""));
        user.getUserBasisInformation().setUsername(sharedPreferences.getString("userBasisInformation.username", ""));
        user.getUserBasisInformation().setSex(sharedPreferences.getString("userBasisInformation.sex", ""));
        user.getUserBasisInformation().setObjectVersionNumber(sharedPreferences.getInt("userBasisInformation.objectVersionNumber", -1));
        user.getUserBasisInformation().setCreatedBy(sharedPreferences.getInt("userBasisInformation.createdBy", -1));
        user.getUserBasisInformation().setCreationDate(new Timestamp(sharedPreferences.getLong("userBasisInformation.creationDate", 0)));
        user.getUserBasisInformation().setLastUpdatedBy(sharedPreferences.getInt("userBasisInformation.lastUpdatedBy", -1));
        user.getUserBasisInformation().setLastUpdatedDate(new Timestamp(sharedPreferences.getLong("userBasisInformation.lastUpdatedDate", 0)));
        user.getUserBasisInformation().setIsEnable(sharedPreferences.getInt("userBasisInformation.isEnable", -1));

        // userExtraInformation
        user.getUserExtraInformation().setUserExtraInformationId(sharedPreferences.getInt("userExtraInformation.userExtraInformationId", -1));
        user.getUserExtraInformation().setUserId(user.getUserId());
        user.getUserExtraInformation().setTelephone(sharedPreferences.getString("userExtraInformation.telephone", ""));
        user.getUserExtraInformation().setUserAddressId(sharedPreferences.getInt("userExtraInformation.userAddressId", -1));
        user.getUserExtraInformation().setAvatarPath(sharedPreferences.getString("userExtraInformation.avatarPath", ""));
        user.getUserExtraInformation().setSignature(sharedPreferences.getString("userExtraInformation.signature", ""));
        user.getUserExtraInformation().setObjectVersionNumber(sharedPreferences.getInt("userExtraInformation.objectVersionNumber", -1));
        user.getUserExtraInformation().setCreatedBy(sharedPreferences.getInt("userExtraInformation.createdBy", -1));
        user.getUserExtraInformation().setCreationDate(new Timestamp(sharedPreferences.getLong("userExtraInformation.creationDate", 0)));
        user.getUserExtraInformation().setLastUpdatedBy(sharedPreferences.getInt("userExtraInformation.lastUpdatedBy", -1));
        user.getUserExtraInformation().setLastUpdatedDate(new Timestamp(sharedPreferences.getLong("userExtraInformation.lastUpdatedDate", 0)));
        user.getUserExtraInformation().setIsEnable(sharedPreferences.getInt("userExtraInformation.isEnable", -1));

        // userExtraInformation.userAddress
        user.getUserExtraInformation().getUserAddress().setUserAddressId(sharedPreferences.getInt("userExtraInformation.userAddress.userAddressId", -1));
        user.getUserExtraInformation().getUserAddress().setCountry(sharedPreferences.getString("userExtraInformation.userAddress.country", ""));
        user.getUserExtraInformation().getUserAddress().setProvince(sharedPreferences.getString("userExtraInformation.userAddress.province", ""));
        user.getUserExtraInformation().getUserAddress().setCity(sharedPreferences.getString("userExtraInformation.userAddress.city", ""));
        user.getUserExtraInformation().getUserAddress().setObjectVersionNumber(sharedPreferences.getInt("userExtraInformation.userAddress.objectVersionNumber", -1));
        user.getUserExtraInformation().getUserAddress().setCreatedBy(sharedPreferences.getInt("userExtraInformation.userAddress.createdBy", -1));
        user.getUserExtraInformation().getUserAddress().setCreationDate(new Timestamp(sharedPreferences.getLong("userExtraInformation.userAddress.creationDate", 0)));
        user.getUserExtraInformation().getUserAddress().setLastUpdatedBy(sharedPreferences.getInt("userExtraInformation.userAddress.lastUpdatedBy", -1));
        user.getUserExtraInformation().getUserAddress().setLastUpdatedDate(new Timestamp(sharedPreferences.getLong("userExtraInformation.userAddress.lastUpdatedDate", 0)));
        user.getUserExtraInformation().getUserAddress().setIsEnable(sharedPreferences.getInt("userExtraInformation.userAddress.isEnable", -1));
        return user;
    }

}
