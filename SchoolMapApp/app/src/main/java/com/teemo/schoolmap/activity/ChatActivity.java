package com.teemo.schoolmap.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.teemo.schoolmap.R;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.05.02 17:03
 * @description
 */
public class ChatActivity extends FragmentActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_easeui_chat);

        EaseChatFragment chatFragment = new EaseChatFragment();
        //传入参数
        Bundle args = getIntent().getExtras();
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.f_easeui_chat, chatFragment).commit();
    }
}
