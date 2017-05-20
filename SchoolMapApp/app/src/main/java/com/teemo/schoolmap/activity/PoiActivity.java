package com.teemo.schoolmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.application.bean.Poi;
import com.teemo.schoolmap.application.bean.User;
import com.teemo.schoolmap.application.config.UrlConfig;
import com.teemo.schoolmap.application.uitl.HttpUtil;

import java.io.IOException;

import okhttp3.Response;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.05.18 18:12
 * @description
 */
public class PoiActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etTitle;
    private EditText etContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi);

        init();
    }

    private void init() {
        etTitle = (EditText) findViewById(R.id.et_title);
        etContent = (EditText) findViewById(R.id.et_content);

        findViewById(R.id.btn_submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final Poi poi = new Poi();
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();
        if ("".equals(title)){
            Toast.makeText(PoiActivity.this, "标题不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if ("".equals(content)){
            Toast.makeText(PoiActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        poi.setTitle(title);
        poi.setContent(content);
        poi.setLatitude(getIntent().getDoubleExtra("latitude", 0));
        poi.setLongitude(getIntent().getDoubleExtra("longitude", 0));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = HttpUtil.postCall(UrlConfig.POI, new ObjectMapper().writeValueAsString(poi), String.valueOf(User.getInstance().getUserId())).execute();
                    if (200 == response.code()) {
                        PoiActivity.this.startActivity(new Intent(PoiActivity.this, MainActivity.class));
                        PoiActivity.this.finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(PoiActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return false;
    }
}
