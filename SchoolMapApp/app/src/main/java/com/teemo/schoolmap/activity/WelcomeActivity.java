package com.teemo.schoolmap.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.teemo.schoolmap.R;
import com.teemo.schoolmap.common.config.MessageConfig;
import com.teemo.schoolmap.common.config.WelcomeConfig;
import com.teemo.schoolmap.common.uitl.ActivityUtil;
import com.teemo.schoolmap.common.uitl.BitmapUtil;


import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.3.19
 * @description 欢迎界面activity
 */
public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivWelcome;
    private Button btnSkipWelcome;

    private int currentSecond = WelcomeConfig.SHOW_TIME;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.fullScreenWithNoTitle(this);
        setContentView(R.layout.activity_welcome);

        init();
        randomSetWelcomeImage();
    }


    /**
     * 随机设置一张欢迎界面壁纸
     */
    private void randomSetWelcomeImage() {
        // [1, WALL_PAPER_NUMBER]
        int wallPaperNo = (int) (Math.random() * WelcomeConfig.WALLPAPER_NUMBER);
        // FIXME: 宽高的获取可能有问题
        int requireWidth = ivWelcome.getMaxWidth();
        int requireHeight = ivWelcome.getMaxHeight();
        Bitmap bitmap = BitmapUtil.getFitSampleBitmap(getResources(), WelcomeConfig.wallpaperIdList[wallPaperNo], requireWidth, requireHeight);
        BitmapDrawable drawable = new BitmapDrawable(this.getResources(), bitmap);
        ivWelcome.setImageDrawable(drawable);
    }

    /**
     * 初始化组件
     */
    private void init() {
        // 初始化View
        ivWelcome = (ImageView) this.findViewById(R.id.iv_welcome);
        btnSkipWelcome = (Button) this.findViewById(R.id.btn_skip_welcome);
        // 设置时间监听
        btnSkipWelcome.setOnClickListener(this);
        // 设置定时器
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnSkipWelcome.setText(WelcomeConfig.SKIP_TEXT + "（" + currentSecond + " S）");
                        currentSecond--;
                        if (currentSecond < 0) {
                            handler.sendEmptyMessage(MessageConfig.WELCOME_ACTIVITY_SKIP);
                        }
                    }
                });
            }
        };
        // 启动定时器
        timer.schedule(timerTask, 1000, 1000);
        // 初始化Handler
        handler = new WelcomeHandler(timer, timerTask, btnSkipWelcome, this);
    }

    @Override
    public void onClick(View v) {
        currentSecond = 0;
        handler.sendEmptyMessage(MessageConfig.WELCOME_ACTIVITY_SKIP);
    }

    private static class WelcomeHandler extends Handler {
        private Timer timer;
        private TimerTask timerTask;
        private Button btnSkipWelcome;
        private Activity activity;

        WelcomeHandler(Timer timer, TimerTask timerTask, Button btnSkipWelcome, Activity activity) {
            this.timer = timer;
            this.timerTask = timerTask;
            this.btnSkipWelcome = btnSkipWelcome;
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MessageConfig.WELCOME_ACTIVITY_SKIP) {
                timerTask.cancel();
                timer.cancel();
                btnSkipWelcome.setVisibility(View.GONE);
                activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
                activity.finish();
            }
        }
    }
}
