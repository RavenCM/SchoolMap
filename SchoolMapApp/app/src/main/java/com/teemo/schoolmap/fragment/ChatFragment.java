package com.teemo.schoolmap.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.teemo.schoolmap.R;
import com.teemo.schoolmap.activity.SignInActivity;
import com.teemo.schoolmap.application.bean.User;
import com.teemo.schoolmap.application.uitl.UserUtil;
import com.teemo.schoolmap.widget.LoadingViewWidget;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/17 16:21
 * @description 聊天界面Fragment
 */
public class ChatFragment extends Fragment {

    private RelativeLayout rlChat;

    private LoadingViewWidget loadingViewWidget;

    private static final int LOGIN_SUCCESS = 0x01;
    private static final int LOGIN_ERROR = 0x00;
    private static final int LOGIN_ACTIVITY = 0x02;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_chat, container, false);
        init(parent);
        return parent;
    }

    private void init(View parent) {
        rlChat = (RelativeLayout) parent.findViewById(R.id.rl_chat);

        login();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_ACTIVITY) {
            if (resultCode == LOGIN_SUCCESS) {
                easemobLogin();
            }
            if (resultCode == LOGIN_ERROR) {
                Toast.makeText(this.getContext(), "未完成登录", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void login() {
        // 如果没有保存的用户信息，跳转到登录页面
        if (!UserUtil.isAlreadyLoggedIn(this.getActivity())) {
            startActivityForResult(new Intent(this.getContext(), SignInActivity.class), LOGIN_ACTIVITY);
        } else {
            // 如果用户在自己服务器已经登录，则不需要同步登录到本地服务器，只需要异步登录到 环信即可
            Log.i("User", User.getInstance().toString());
            easemobLogin();
        }

    }

    private void easemobLogin() {

    }
}
