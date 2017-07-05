package com.example.text1.jxbd.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.text1.jxbd.R;
import com.example.text1.jxbd.bean.SubjectTitle;
import com.example.text1.jxbd.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);
    }
    public void test(View view) {
        startActivity(new Intent(MainActivity.this ,ExamActivity .class));
    }

    public void test3(View view) {
        finish();
    }
}
