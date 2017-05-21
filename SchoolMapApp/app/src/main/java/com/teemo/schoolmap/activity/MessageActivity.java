package com.teemo.schoolmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.adapter.ImageAdapter;
import com.teemo.schoolmap.application.bean.Message;
import com.teemo.schoolmap.application.bean.MessageImage;
import com.teemo.schoolmap.application.bean.User;
import com.teemo.schoolmap.application.config.UrlConfig;
import com.teemo.schoolmap.application.uitl.HttpUtil;
import com.teemo.schoolmap.widget.LoadingViewDialog;
import com.zfdang.multiple_images_selector.ImagesSelectorActivity;
import com.zfdang.multiple_images_selector.SelectorSettings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private Message message;
    private GridView gridView;
    private ImageAdapter imageAdapter;
    private AlertDialog loadingViewDialog;

    private static final int REQUEST_CODE = 732;

    private ArrayList<String> mResults = new ArrayList<>();
    private List<String> pathList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        init();
    }

    private void init() {
        message = new Message();
        etContent = (EditText) findViewById(R.id.et_content);
        ibAdd = (ImageButton) findViewById(R.id.ib_add);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        gridView = (GridView) findViewById(R.id.gv_image);

        btnSubmit.setOnClickListener(this);
        ibAdd.setOnClickListener(this);

        imageAdapter = new ImageAdapter(this, pathList);
        gridView.setAdapter(imageAdapter);

        loadingViewDialog = LoadingViewDialog.builder(this);
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
                message.setContent(etContent.getText().toString().trim());
                loadingViewDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (pathList != null && pathList.size() > 0) {
                                List<MessageImage> messageImageList = new ArrayList<>();
                                for (String path : pathList) {
                                    MessageImage messageImage = new MessageImage();
                                    String[] sub = path.split("/");
                                    messageImage.setPath(sub[sub.length - 1]);
                                    messageImageList.add(messageImage);
                                    HttpUtil.uploadFile(UrlConfig.FILE, path, messageImage.getPath(), String.valueOf(User.getInstance().getUserId())).execute();
                                }
                                message.setMessageImageList(messageImageList);
                            }
                            Response response = HttpUtil.postCall(UrlConfig.MESSAGE, new ObjectMapper().writeValueAsString(message), String.valueOf(User.getInstance().getUserId())).execute();
                            if (200 == response.code()) {
                                Intent intent = new Intent(MessageActivity.this, MainActivity.class);
                                intent.putExtra("index", 1);
                                MessageActivity.this.startActivity(intent);
                                MessageActivity.this.finish();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingViewDialog.dismiss();
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MessageActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
                                        loadingViewDialog.dismiss();
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
                // start multiple photos selector
                Intent intent = new Intent(MessageActivity.this, ImagesSelectorActivity.class);
                // max number of images to be selected
                intent.putExtra(SelectorSettings.SELECTOR_MAX_IMAGE_NUMBER, 9);
                // min size of image which will be shown; to filter tiny images (mainly icons)
                intent.putExtra(SelectorSettings.SELECTOR_MIN_IMAGE_SIZE, 100);
                // show camera or not
                intent.putExtra(SelectorSettings.SELECTOR_SHOW_CAMERA, true);
                // pass current selected images as the initial value
                intent.putStringArrayListExtra(SelectorSettings.SELECTOR_INITIAL_SELECTED_LIST, mResults);
                // start the selector
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // get selected images from selector
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mResults = data.getStringArrayListExtra(SelectorSettings.SELECTOR_RESULTS);
                assert mResults != null;
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("Totally %d images selected:", mResults.size())).append("\n");
                pathList = new ArrayList<>();
                for (String result : mResults) {
                    pathList.add(result);
                    sb.append(result).append("\n");
                }
                Log.i("path", sb.toString());
                imageAdapter.setPathList(pathList);
                imageAdapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
