package com.teemo.schoolmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import com.hyphenate.chat.EMConversation;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.application.bean.User;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.05.03 14:54
 * @description
 */
public class RecodeActivity extends FragmentActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.easeui_container);
        EaseConversationListFragment conversationListFragment = new EaseConversationListFragment();
        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                //传入参数
                Bundle args = new Bundle();
                args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
                args.putString(EaseConstant.EXTRA_USER_ID, conversation.conversationId());

                Intent intent = new Intent(RecodeActivity.this, ChatActivity.class);
                intent.putExtras(args);
                startActivity(intent);
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.easeui_container, conversationListFragment).commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return false;
    }
}
