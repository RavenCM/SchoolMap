package com.teemo.schoolmap.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teemo.schoolmap.R;
import com.teemo.schoolmap.application.bean.User;
import com.teemo.schoolmap.application.config.UrlConfig;
import com.teemo.schoolmap.application.uitl.ActivityUtil;
import com.teemo.schoolmap.application.uitl.HttpUtil;
import com.teemo.schoolmap.application.uitl.InternetUtil;
import com.teemo.schoolmap.application.uitl.Md5Util;
import com.teemo.schoolmap.application.uitl.SharedPreferencesUtil;
import com.teemo.schoolmap.widget.LoadingViewDialog;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Response;

/**
 * @author Teemo
 * @version 1.0
 * @Date 2017/3/26 17:41
 * @description
 */

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText etEmail;
    private EditText etPassword;
    private CircleImageView civAvatar;
    private Button btnLogin;
    private static AlertDialog dialog;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtil.noTitle(this);
        setContentView(R.layout.activity_sign_in);
        init();
    }

    private void init() {
        etEmail = (EditText) findViewById(R.id.et_email);
        Drawable drawableEmail = getResources().getDrawable(R.drawable.email, null);
        drawableEmail.setBounds(0, 0, 64, 64);
        etEmail.setCompoundDrawables(drawableEmail, null, null, null);
        etEmail.setText(SharedPreferencesUtil.readUserInfo(SignInActivity.this).getUserBasisInformation().getEmail());
        etEmail.setText("admin@admin.com");

        etPassword = (EditText) findViewById(R.id.et_password);
        Drawable drawablePassword = getResources().getDrawable(R.drawable.password, null);
        drawablePassword.setBounds(0, 0, 64, 64);
        etPassword.setCompoundDrawables(drawablePassword, null, null, null);
        etPassword.setText(SharedPreferencesUtil.readUserInfo(SignInActivity.this).getPassword());
        etPassword.setText("admin");

        civAvatar = (CircleImageView) findViewById(R.id.civ_avatar);
        String avatarPath = SharedPreferencesUtil.readUserInfo(SignInActivity.this).getUserExtraInformation().getAvatarPath();
        // TODO: 设置图片路径

        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        dialog = LoadingViewDialog.builder(this);
        handler = new SignInHandler(this, btnLogin, this);
    }

    @Override
    public void onClick(View v) {
        btnLogin.setEnabled(false);
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if ("".equals(email) || "".equals(password)) {
            Toast.makeText(SignInActivity.this, "邮箱/密码不能为空！", Toast.LENGTH_SHORT).show();
            btnLogin.setEnabled(true);
            return;
        }
        if (!InternetUtil.isNetworkConnected(SignInActivity.this.getApplicationContext())) {
            Toast.makeText(SignInActivity.this, "请检查网络是否可用！", Toast.LENGTH_SHORT).show();
            btnLogin.setEnabled(true);
            return;
        }
        dialog.show();
        // 发送登录请求
        new LoginThread(email, password, this).start();
    }

    private class LoginThread extends Thread {
        private String email;
        private String password;
        private Activity activity;

        LoginThread(String email, String password, Activity activity) {
            this.email = email;
            this.password = password;
            this.activity = activity;
        }

        @Override
        public void run() {
            Map<String, Object> argumentMap = new HashMap<>();
            argumentMap.put("userBasisInformation.email", email);
            argumentMap.put("password", Md5Util.sign(password));
            Message message = handler.obtainMessage();
            try {
                Response response = HttpUtil.getCall(UrlConfig.USER_LOGIN, argumentMap).execute();
                if (200 == response.code()) {
                    // 登录成功
                    SharedPreferencesUtil.writeUserInfo(activity, new ObjectMapper().readValue(response.body().string(), User.class));
                    message.what = SignInHandler.LOGIN_SUCCESS;
                }
                Log.i("login", response.message());
            } catch (Exception e) {
                message.what = SignInHandler.LOGIN_ERROR;
                e.printStackTrace();
            }
            handler.sendMessage(message);
        }
    }

    private static class SignInHandler extends Handler {
        private Context context;
        private Button btnLogin;
        private Activity activity;

        static final int LOGIN_SUCCESS = 0x01;
        static final int LOGIN_ERROR = 0x00;

        SignInHandler(Context context, Button btnLogin, Activity activity) {
            this.context = context;
            this.btnLogin = btnLogin;
            this.activity = activity;
        }

        @Override
        public void handleMessage(Message msg) {
            dialog.dismiss();
            switch (msg.what) {
                case LOGIN_SUCCESS:
                    activity.setResult(LOGIN_SUCCESS);
                    activity.finish();
                    break;
                case LOGIN_ERROR:
                    Toast.makeText(context, "登录异常", Toast.LENGTH_SHORT).show();
                    btnLogin.setEnabled(true);
                    break;
            }
        }
    }
}
