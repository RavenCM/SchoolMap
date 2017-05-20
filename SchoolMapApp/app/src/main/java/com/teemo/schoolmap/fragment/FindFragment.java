package com.teemo.schoolmap.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.activity.MessageActivity;
import com.teemo.schoolmap.adapter.MessageAdapter;
import com.teemo.schoolmap.application.bean.Message;
import com.teemo.schoolmap.application.config.UrlConfig;
import com.teemo.schoolmap.application.uitl.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/4/17 16:19
 * @description
 */
public class FindFragment extends Fragment implements View.OnClickListener {
    private ImageButton ibAdd;
    private ListView lvFind;

    private List<Message> messageList;
    private MessageAdapter messageAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("FindFragment", "onCreateView");
        View parent = inflater.inflate(R.layout.fragment_find, container, false);
        init(parent);
        return parent;
    }

    private void init(View parent) {
        lvFind = (ListView) parent.findViewById(R.id.lv_find);
        ibAdd = (ImageButton) parent.findViewById(R.id.ib_add);

        ibAdd.setOnClickListener(this);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this.getContext(), messageList);
        lvFind.setAdapter(messageAdapter);
        getMessage();
    }

    private void getMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = HttpUtil.getCall(UrlConfig.MESSAGE, null).execute();
                    if (response.code() == 200){
                        ObjectMapper mapper = new ObjectMapper();
                        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Message.class);
                        messageList = mapper.readValue(response.body().string(), javaType);
                        FindFragment.this.getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                messageAdapter.setMessageList(messageList);
                                messageAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        startActivity(new Intent(FindFragment.this.getContext(), MessageActivity.class));
        FindFragment.this.getActivity().finish();
    }
}
