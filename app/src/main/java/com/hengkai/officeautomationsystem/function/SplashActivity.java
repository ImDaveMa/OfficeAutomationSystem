package com.hengkai.officeautomationsystem.function;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.hengkai.officeautomationsystem.R;
import com.hengkai.officeautomationsystem.final_constant.UserInfo;
import com.hengkai.officeautomationsystem.function.login.LoginActivity;
import com.hengkai.officeautomationsystem.utils.SPUtils;

public class SplashActivity extends AppCompatActivity {

    private int timeCount = 0;
    boolean continueCount = true;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            countNum();
            if (continueCount) {
                handler.sendMessageDelayed(handler.obtainMessage(-1), 1000);
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // 第一次执行
        countNum();
        if (continueCount) {
            handler.sendMessageDelayed(handler.obtainMessage(-1), 1000);
        }
    }


    private int countNum() {//数秒
        timeCount++;
        if (timeCount == 3) {//数秒，超过3秒后如果没有网络，则进入下一个页面
            continueCount = false;
            toNextActivity();
        }
        return timeCount;
    }

    public void toNextActivity() {//根据是否保存有 token 判断去登录界面还是主界面
        String token = SPUtils.getString(UserInfo.TOKEN.name(), "");
        boolean isAutoLogin = SPUtils.getBoolean(UserInfo.IS_LOGIN.name(), false);
        boolean isFirst = SPUtils.getBoolean(UserInfo.IS_FIRST_ENTER.name(), true);
        Intent intent;
        if (isFirst) {
            intent = new Intent(this, GuideActivity.class);
        } else {
            if (!TextUtils.isEmpty(token) && isAutoLogin) {
                intent = new Intent(this, MainActivity.class);
            } else {
                intent = new Intent(this, LoginActivity.class);
            }
        }

        startActivity(intent);
        finish();
    }
}
