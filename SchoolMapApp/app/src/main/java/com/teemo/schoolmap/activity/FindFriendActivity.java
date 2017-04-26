package com.teemo.schoolmap.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.adapter.FriendListAdapter;
import com.teemo.schoolmap.application.bean.User;
import com.teemo.schoolmap.application.config.UrlConfig;
import com.teemo.schoolmap.application.uitl.ActivityUtil;
import com.teemo.schoolmap.application.uitl.HttpUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;


/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/24 15:18
 * @description
 */
public class FindFriendActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ImageButton ibSearch;
    private EditText etSearch;
    private ListView lvFriend;

    private static FriendListAdapter friendListAdapter;
    private List<User> userList;
    private static FindFriendHandler handler;

    private static final int NOTIFY_ADAPTER_REFRESH = 0x00;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.noTitle(this);
        setContentView(R.layout.activity_find);

        init();
    }

    private void init() {
        ibSearch = (ImageButton) findViewById(R.id.ib_search);
        etSearch = (EditText) findViewById(R.id.et_search);
        lvFriend = (ListView) findViewById(R.id.lv_friend);

        userList = new ArrayList<>();
        friendListAdapter = new FriendListAdapter(this, userList);
        lvFriend.setAdapter(friendListAdapter);
        lvFriend.setOnItemClickListener(this);
        ibSearch.setOnClickListener(this);
        handler = new FindFriendHandler();
    }

    @Override
    public void onClick(View v) {
        new UserFindThread(this).start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        View dialogView = View.inflate(this, R.layout.dialog_edit, null);
        final EditText etContent = ((EditText) dialogView.findViewById(R.id.et_content));
        etContent.setText("我是 " + User.getInstance().getUserBasisInformation().getUsername());
        new AlertDialog.Builder(this)
                .setTitle("消息")
                .setMessage("请输入添加理由：")
                .setView(dialogView)
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    EMClient.getInstance().contactManager().addContact(String.valueOf(friendListAdapter.getUserList().get(position).getUserId()), etContent.getText().toString().trim());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(FindFriendActivity.this, "添加好友失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        Intent intent = new Intent(FindFriendActivity.this, MainActivity.class);
                        intent.putExtra("index", 2);
                        startActivity(intent);
                    }
                }).show();
    }

    private class UserFindThread extends Thread {
        private Context context;

        UserFindThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            try {
                Map<String, Object> argumentMap = new HashMap<>();
                argumentMap.put("userInfo", etSearch.getText().toString().trim());
                Response response = HttpUtil.getCall(UrlConfig.USERS, argumentMap).execute();
                if (200 == response.code()) {
                    // 查询用户列表成功
                    ObjectMapper mapper = new ObjectMapper();
                    JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, User.class);
                    userList = mapper.readValue(response.body().string(), javaType);
                    friendListAdapter.setUserList(userList);
                    handler.sendEmptyMessage(NOTIFY_ADAPTER_REFRESH);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("UserFindThread", "获取用户失败");
            }
        }
    }

    private static class FindFriendHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NOTIFY_ADAPTER_REFRESH:
                    friendListAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
