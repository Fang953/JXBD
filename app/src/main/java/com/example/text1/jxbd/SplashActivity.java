package com.example.text1.jxbd;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;

/**
 * Created by Administrator on 2017/6/27.
 */

public class SplashActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //闪屏的activity
        setContentView(R.layout .activity_splash);
        mCountDownTimer.start();
    }
    CountDownTimer mCountDownTimer = new CountDownTimer(3000,1000){
        @Override
        public void onTick(long millisUntilFinished) {

        }
        @Override
        public void onFinish() {
            Intent intent = new Intent(SplashActivity.this,MainActivity.class);
            startActivity(intent);
        }
    };
}
