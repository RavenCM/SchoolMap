package com.teemo.schoolmap.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.adapter.PoiAdapter;
import com.teemo.schoolmap.application.bean.Poi;
import com.teemo.schoolmap.application.bean.User;
import com.teemo.schoolmap.application.config.UrlConfig;
import com.teemo.schoolmap.application.uitl.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.05.20 13:23
 * @description
 */
public class PoisAcitvity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private Button btnAllPoi;
    private Button btnValidPoi;
    private Button btnInvalidPoi;
    private ListView lvPoi;

    private List<Poi> poiList;
    private PoiAdapter poiAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pois);

        init();
    }

    private void init() {
        btnAllPoi = (Button) findViewById(R.id.btn_all_poi);
        btnValidPoi = (Button) findViewById(R.id.btn_valid_poi);
        btnInvalidPoi = (Button) findViewById(R.id.btn_invalid_poi);
        lvPoi = (ListView) findViewById(R.id.lv_poi);

        poiList = new ArrayList<>();
        poiAdapter = new PoiAdapter(this, poiList);
        lvPoi.setAdapter(poiAdapter);
        lvPoi.setOnItemClickListener(this);
        getPoi();

        btnAllPoi.setOnClickListener(this);
        btnValidPoi.setOnClickListener(this);
        btnInvalidPoi.setOnClickListener(this);
    }

    private void getPoi() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = HttpUtil.getCall(UrlConfig.MY_POI, null).execute();
                    if (200 == response.code()) {
                        ObjectMapper mapper = new ObjectMapper();
                        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, Poi.class);
                        poiList = mapper.readValue(response.body().string(), javaType);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                poiAdapter.setPoiList(poiList);
                                poiAdapter.notifyDataSetChanged();
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
        resetBtn();
        switch (v.getId()) {
            case R.id.btn_all_poi:
                btnAllPoi.setBackgroundResource(R.drawable.btn_big_select);
                poiAdapter.setPoiList(poiList);
                poiAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_valid_poi:
                btnValidPoi.setBackgroundResource(R.drawable.btn_big_select);
                List<Poi> validPoiList = new ArrayList<>();
                for (Poi poi : poiList) {
                    if (poi.getIsEnable() == 1) {
                        validPoiList.add(poi);
                    }
                }
                poiAdapter.setPoiList(validPoiList);
                poiAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_invalid_poi:
                List<Poi> invalidPoiList = new ArrayList<>();
                btnInvalidPoi.setBackgroundResource(R.drawable.btn_big_select);
                for (Poi poi : poiList) {
                    if (poi.getIsEnable() == 0) {
                        invalidPoiList.add(poi);
                    }
                }
                poiAdapter.setPoiList(invalidPoiList);
                poiAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void resetBtn() {
        btnAllPoi.setBackgroundResource(R.drawable.btn_big);
        btnValidPoi.setBackgroundResource(R.drawable.btn_big);
        btnInvalidPoi.setBackgroundResource(R.drawable.btn_big);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        new AlertDialog.Builder(PoisAcitvity.this)
                .setTitle(poiAdapter.getPoiList().get(position).getTitle())
                .setMessage(poiAdapter.getPoiList().get(position).getContent())
                .setIcon(R.drawable.poi)
                .setNegativeButton("返回", null)
                .setPositiveButton(poiAdapter.getPoiList().get(position).getIsEnable() == 1 ? "取消任务" : "重新开启", poiAdapter.getPoiList().get(position).getIsEnable() == 1 ? new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    poiAdapter.getPoiList().get(position).setIsEnable(0);
                                    Response response = HttpUtil.putCall(UrlConfig.CANCEL_POI + poiAdapter.getPoiList().get(position).getPoiId(), new ObjectMapper().writeValueAsString(poiList.get(position)), String.valueOf(User.getInstance().getUserId())).execute();
                                    if (200 == response.code()) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(PoisAcitvity.this, "取消成功", Toast.LENGTH_SHORT).show();
                                                poiAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    } else {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(PoisAcitvity.this, "取消失败", Toast.LENGTH_SHORT).show();
                                                poiAdapter.getPoiList().get(position).setIsEnable(1);
                                                poiAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                } : new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    poiAdapter.getPoiList().get(position).setIsEnable(1);
                                    Response response = HttpUtil.putCall(UrlConfig.CANCEL_POI + poiAdapter.getPoiList().get(position).getPoiId(), new ObjectMapper().writeValueAsString(poiList.get(position)), String.valueOf(User.getInstance().getUserId())).execute();
                                    if (200 == response.code()) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(PoisAcitvity.this, "重启成功", Toast.LENGTH_SHORT).show();
                                                poiAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    } else {

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(PoisAcitvity.this, "重启失败", Toast.LENGTH_SHORT).show();
                                                poiAdapter.getPoiList().get(position).setIsEnable(0);
                                                poiAdapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }).show();
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
