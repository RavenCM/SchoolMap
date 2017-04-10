package com.teemo.schoolmap.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.teemo.schoolmap.R;
import com.teemo.schoolmap.common.uitl.ActivityUtil;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/3/26 17:41
 * @description
 */

public class SignInActivity extends AppCompatActivity {

    private EditText etEmail;
    private Drawable drawableEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.noTitle(this);
        setContentView(R.layout.activity_sign_in);

        init();
    }

    private void init() {
        etEmail = (EditText) findViewById(R.id.et_email);
        drawableEmail = getResources().getDrawable(R.drawable.email, null);
        drawableEmail.setBounds(0, 0, 64, 64);
        etEmail.setCompoundDrawables(drawableEmail, null, null, null);
    }
}
