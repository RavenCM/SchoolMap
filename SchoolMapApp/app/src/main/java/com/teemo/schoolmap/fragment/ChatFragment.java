package com.teemo.schoolmap.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.activity.FindFriendActivity;
import com.teemo.schoolmap.activity.SignInActivity;
import com.teemo.schoolmap.adapter.FriendListAdapter;
import com.teemo.schoolmap.adapter.MenuListAdapter;
import com.teemo.schoolmap.application.bean.Menu;
import com.teemo.schoolmap.application.bean.User;
import com.teemo.schoolmap.application.config.UrlConfig;
import com.teemo.schoolmap.application.uitl.HttpUtil;
import com.teemo.schoolmap.application.uitl.UserUtil;
import com.teemo.schoolmap.widget.LoadingViewDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/17 16:21
 * @description 聊天界面 Fragment
 */
public class ChatFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private RelativeLayout rlChat;
    private ListView lvMenu;
    private ImageButton ibMore;

    private MenuListAdapter menuListAdapter;

    private static ListView lvFriend;

    private static final int LOGIN_ERROR = 0x00;
    private static final int LOGIN_SUCCESS = 0x01;
    private static final int LOGIN_ACTIVITY = 0x02;
    private static final int GET_USER_FRIEND_SUCCESS = 0x03;
    private static final int GET_USER_FRIEND_ERROR = 0x04;
    private static final int GET_USER_FRIEND_EMPTY = 0x05;
    private static AlertDialog dialog;
    private static List<User> userList;
    private static FriendListAdapter friendListAdapter;

    private static ChatFragmentHandler handler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.fragment_chat, container, false);
        init(parent);
        return parent;
    }

    private void init(View parent) {
        rlChat = (RelativeLayout) parent.findViewById(R.id.rl_chat);
        lvMenu = (ListView) parent.findViewById(R.id.lv_menu);
        lvFriend = (ListView) parent.findViewById(R.id.lv_friend);
        ibMore = (ImageButton) parent.findViewById(R.id.ib_more);
        handler = new ChatFragmentHandler(this.getContext(), (ImageView) parent.findViewById(R.id.iv_login), (TextView) parent.findViewById(R.id.tv_login));
        dialog = LoadingViewDialog.builder(this.getContext());

        List<Menu> menuList = new ArrayList<>();
        menuList.add(new Menu(R.drawable.add_user, "添加好友", FindFriendActivity.class));
        menuListAdapter = new MenuListAdapter(this.getContext(), menuList);
        lvMenu.setOnItemClickListener(this);
        lvMenu.setAdapter(menuListAdapter);

        ibMore.setOnClickListener(this);
        login();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOGIN_ACTIVITY:
                if (resultCode == LOGIN_SUCCESS) {
                    easemobLogin();
                }
                if (resultCode == LOGIN_ERROR) {
                    Toast.makeText(this.getContext(), "未完成登录", Toast.LENGTH_SHORT).show();
                }
                break;
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
        dialog.show();
        EMClient.getInstance().login(String.valueOf(User.getInstance().getUserId()), User.getInstance().getPassword(), new EMCallBack() {
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.i("EMClient", "登录成功！");
                Message message = handler.obtainMessage();
                message.what = LOGIN_SUCCESS;
                handler.sendMessage(message);
            }

            @Override
            public void onError(int i, String s) {
                Message message = handler.obtainMessage();
                message.what = LOGIN_ERROR;
                Bundle bundle = new Bundle();
                bundle.putString("error", s);
                message.setData(bundle);
                handler.sendMessage(message);
                Log.i("EMClient", s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_more:
                lvMenu.setVisibility(lvMenu.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("onItemClick", position + "");
        startActivity(new Intent(ChatFragment.this.getContext(), ((Menu) menuListAdapter.getItem(position)).getClazz()));
    }

    private static class ChatFragmentHandler extends Handler {
        private ImageView ivLoggingIn;
        private TextView tvLoggingIn;
        private Context context;

        ChatFragmentHandler(Context context, ImageView ivLoggingIn, TextView tvLoggingIn) {
            this.context = context;
            this.ivLoggingIn = ivLoggingIn;
            this.tvLoggingIn = tvLoggingIn;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOGIN_SUCCESS:
                    Toast.makeText(context, "登录成功！", Toast.LENGTH_SHORT).show();
                    new GetUserFriendThread(context).start();
                    break;
                case LOGIN_ERROR:
                    dialog.dismiss();
                    tvLoggingIn.setText("登录失败...");
                    ivLoggingIn.setBackgroundResource(R.drawable.warning);
                    Toast.makeText(context, msg.getData().getString("error"), Toast.LENGTH_SHORT).show();
                    break;
                case GET_USER_FRIEND_SUCCESS:
                    tvLoggingIn.setVisibility(View.GONE);
                    ivLoggingIn.setVisibility(View.GONE);
                    dialog.dismiss();
                    lvFriend.setAdapter(friendListAdapter);
                    Toast.makeText(context, "获取用户列表成功！", Toast.LENGTH_SHORT).show();
                    break;
                case GET_USER_FRIEND_EMPTY:
                    dialog.dismiss();
                    tvLoggingIn.setText("用户列表为空，请添加好友...");
                    ivLoggingIn.setBackgroundResource(R.drawable.warning);
                    Toast.makeText(context, "用户列表为空！", Toast.LENGTH_SHORT).show();
                    break;
                case GET_USER_FRIEND_ERROR:
                    dialog.dismiss();
                    tvLoggingIn.setText("获取用户列表失败...");
                    ivLoggingIn.setBackgroundResource(R.drawable.warning);
                    Toast.makeText(context, "获取用户列表失败！", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private static class GetUserFriendThread extends Thread {
        private Context context;

        GetUserFriendThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            /*
             *  初始化好友列表
             *  1. 获取好友 ID List
             *  2. 发送请求获取用户信息
             *  3. 初始化 adapter
             *  4. 设置 adapter
             */
            Message message = handler.obtainMessage();
            try {
                List<String> userIdList = EMClient.getInstance().contactManager().getAllContactsFromServer();
                if (userIdList != null && userIdList.size() > 0) {
                    Map<String, Object> argumentMap = new HashMap<>();
                    argumentMap.put("userId", userIdList);
                    Response response = HttpUtil.getCall(UrlConfig.USERS, argumentMap).execute();
                    if (200 == response.code()) {
                        // 查询用户列表成功
                        ObjectMapper mapper = new ObjectMapper();
                        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, User.class);
                        userList = mapper.readValue(response.body().string(), javaType);
                        friendListAdapter = new FriendListAdapter(context, userList);
                        message.what = GET_USER_FRIEND_SUCCESS;
                    } else {
                        message.what = GET_USER_FRIEND_ERROR;
                    }
                } else {
                    message.what = GET_USER_FRIEND_EMPTY;
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("getContacts", "获取好友列表失败");
                message.what = GET_USER_FRIEND_ERROR;
            }
            handler.sendMessage(message);
        }
    }
}
