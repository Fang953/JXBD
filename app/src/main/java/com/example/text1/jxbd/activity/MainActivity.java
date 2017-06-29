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
        OkHttpUtils<SubjectTitle> utils =new OkHttpUtils<>(getApplicationContext());
        String url="http://101.251.196.90:8080/JztkServer/examInfo";
        utils .url(url)
                .targetClass(SubjectTitle.class)
                .execute(new OkHttpUtils.OnCompleteListener<SubjectTitle>() {
                    @Override
                    public void onSuccess(SubjectTitle result) {
                        Log.e("main","result="+result );
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });
        startActivity(new Intent(MainActivity.this ,ExamActivity .class ));

    }

    public void exit(View view) {
    }
}
