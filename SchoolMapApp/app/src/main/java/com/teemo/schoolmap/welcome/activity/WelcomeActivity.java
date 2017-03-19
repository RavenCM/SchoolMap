package com.teemo.schoolmap.welcome.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.teemo.schoolmap.R;
import com.teemo.schoolmap.common.util.ActivityUtil;
import com.teemo.schoolmap.common.util.BitmapUtil;
import com.teemo.schoolmap.welcome.config.WelcomeConfig;


/**
 * Created By Teemo On 2017.3.19
 * ClassName: WelcomeActivity
 */
public class WelcomeActivity extends AppCompatActivity {
    private ImageView ivWelcome;
    private Button btnSkipWelcome;

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
    }
}
