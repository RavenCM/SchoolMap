package com.teemo.schoolmap.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.teemo.schoolmap.R;
import com.teemo.schoolmap.common.uitl.ActivityUtil;
import com.teemo.schoolmap.fragment.MapFragment;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/3/30 19:49
 * @description 应用功能 activity
 */
public class MainActivity extends FragmentActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.noTitle(this);
        setContentView(R.layout.acvitity_main);

        init();
    }

    private void init() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MapFragment mapFragment = new MapFragment();
        fragmentTransaction.add(R.id.fl_content, mapFragment);

        fragmentTransaction.commit();

        fragmentTransaction.show(mapFragment);
    }
}
