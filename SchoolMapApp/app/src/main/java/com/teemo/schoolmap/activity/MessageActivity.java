package com.teemo.schoolmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.application.bean.Message;
import com.teemo.schoolmap.application.bean.User;
import com.teemo.schoolmap.application.config.UrlConfig;
import com.teemo.schoolmap.application.uitl.HttpUtil;

import java.io.IOException;

import okhttp3.Response;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.05.20 21:33
 * @description
 */
public class MessageActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etContent;
    private ImageButton ibAdd;
    private Button btnSubmit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        init();
    }

    private void init() {
        etContent = (EditText) findViewById(R.id.et_content);
        ibAdd = (ImageButton) findViewById(R.id.ib_add);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                final Message message = new Message();
                message.setContent(etContent.getText().toString().trim());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Response response = HttpUtil.postCall(UrlConfig.MESSAGE, new ObjectMapper().writeValueAsString(message), String.valueOf(User.getInstance().getUserId())).execute();
                            if (200 == response.code()) {
                                Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                                intent.putExtra("index", 1);
                                MessageActivity.this.startActivity(intent);
                                MessageActivity.this.finish();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MessageActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.ib_add:

                break;
        }
    }
}
