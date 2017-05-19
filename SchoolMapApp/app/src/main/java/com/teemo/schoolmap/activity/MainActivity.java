package com.teemo.schoolmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.application.config.MainConfig;
import com.teemo.schoolmap.application.uitl.ActivityUtil;
import com.teemo.schoolmap.fragment.ChatFragment;
import com.teemo.schoolmap.fragment.FindFragment;
import com.teemo.schoolmap.fragment.MapFragment;
import com.teemo.schoolmap.fragment.OldSDKMapFragment;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/3/30 19:49
 * @description 应用功能 activity
 */
@SuppressWarnings("deprecation")
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private LinearLayout llMap;
    private LinearLayout llFind;
    private LinearLayout llChat;
    private TextView tvMap;
    private TextView tvFind;
    private TextView tvChat;
    private ImageView ivMap;
    private ImageView ivFind;
    private ImageView ivChat;
    private int index;

    // fragment
    private static Fragment[] fragments = new Fragment[]{new MapFragment(), new FindFragment(), new ChatFragment()};

    private boolean isInit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.noTitle(this);
        setContentView(R.layout.acvitity_main);
        init();
    }

    private void init() {
        Intent intent = getIntent();
        index = intent != null ? intent.getIntExtra("index", MainConfig.DEFAULT_PAGE_INDEX) : MainConfig.DEFAULT_PAGE_INDEX;

        llMap = (LinearLayout) findViewById(R.id.ll_map);
        llFind = (LinearLayout) findViewById(R.id.ll_find);
        llChat = (LinearLayout) findViewById(R.id.ll_chat);
        tvMap = (TextView) findViewById(R.id.tv_map);
        tvFind = (TextView) findViewById(R.id.tv_find);
        tvChat = (TextView) findViewById(R.id.tv_chat);
        ivMap = (ImageView) findViewById(R.id.iv_map);
        ivFind = (ImageView) findViewById(R.id.iv_find);
        ivChat = (ImageView) findViewById(R.id.iv_chat);

        llMap.setOnClickListener(this);
        llFind.setOnClickListener(this);
        llChat.setOnClickListener(this);

        selectTab(index);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_map:
                selectTab(0);
                break;
            case R.id.ll_find:
                selectTab(1);
                break;
            case R.id.ll_chat:
                selectTab(2);
                break;
        }
    }

    /**
     * 重置所有的 Tab
     */
    private void resetTab(FragmentTransaction fragmentTransaction) {
        for (Fragment fragment : fragments) {
            fragmentTransaction.hide(fragment);
        }
        tvMap.setTextColor(getResources().getColor(R.color.unselected));
        tvFind.setTextColor(getResources().getColor(R.color.unselected));
        tvChat.setTextColor(getResources().getColor(R.color.unselected));
        ivMap.setBackgroundResource(R.drawable.map_unselected);
        ivFind.setBackgroundResource(R.drawable.found_unselected);
        ivChat.setBackgroundResource(R.drawable.chat_unselected);
    }

    /**
     * 选择 Tab
     *
     * @param index 索引
     */
    private void selectTab(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!isInit) {
            for (Fragment fragment : fragments) {
                fragmentTransaction.add(R.id.fl_content, fragment);
            }
            isInit = true;
        }
        resetTab(fragmentTransaction);
        fragmentTransaction.show(fragments[index]);
        switch (index) {
            case 0:
                tvMap.setTextColor(getResources().getColor(R.color.selected));
                ivMap.setBackgroundResource(R.drawable.map_selected);
                break;
            case 1:
                tvFind.setTextColor(getResources().getColor(R.color.selected));
                ivFind.setBackgroundResource(R.drawable.found_selected);
                break;
            case 2:
                tvChat.setTextColor(getResources().getColor(R.color.selected));
                ivChat.setBackgroundResource(R.drawable.chat_selected);
                break;
        }
        fragmentTransaction.commit();
    }
}
