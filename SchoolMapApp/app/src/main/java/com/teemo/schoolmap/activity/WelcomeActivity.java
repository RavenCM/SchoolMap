package com.teemo.schoolmap.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.teemo.schoolmap.R;
import com.teemo.schoolmap.util.ActivityUtil;
import com.teemo.schoolmap.util.BitmapUtil;
import com.teemo.schoolmap.config.WelcomeConfig;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017.3.19
 * @description 欢迎界面activity
 */
public class WelcomeActivity extends AppCompatActivity {
    private ImageView ivWelcome;
    private Button btnSkipWelcome;

    private Timer timer;
    private TimerTask timerTask;
    private int currentSecond = WelcomeConfig.SHOW_TIME;

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
        ivWelcome = (ImageView) this.findViewById(R.id.iv_welcome);
        btnSkipWelcome = (Button) this.findViewById(R.id.btn_skip_welcome);

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnSkipWelcome.setText(currentSecond + " S");
                        currentSecond--;
                        if (currentSecond < 0){
                            timer.cancel();
                            btnSkipWelcome.setVisibility(View.GONE);
                        }
                    }
                });
            }
        };
        timer.schedule(timerTask, 1000);
    }
}
