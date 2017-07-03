package com.example.text1.jxbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.text1.jxbd.Biz.IExamBiz;
import com.example.text1.jxbd.Biz.QuestionBiz;
import com.example.text1.jxbd.ExamApplication;
import com.example.text1.jxbd.R;
import com.example.text1.jxbd.bean.Question;
import com.example.text1.jxbd.bean.SubjectTitle;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity{
    TextView tvSubjectTitle,tvQuestionTitle,tvOption1,tvOption2,tvOption3,tvOption4,tvLoad,tvNo;
    CheckBox cb01,cb02,cb03,cb04;
    CheckBox[] cbArray=new CheckBox[4];
    LinearLayout layoutLoading,layout03,layout04;
    ImageView jkImageView;
    ProgressBar dialog;
    IExamBiz biz;
    boolean isLoadExamInfo = false;
    boolean isLoadQuestions = false;

    boolean isLoadExamInfoReceiver = false;
    boolean isLoadQuestionsReceiver = false;

    LoadExamBroadcast mLoadExamBroadcast;
    LoadQuestionBroadcast mLoadQuestionBroadcast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mLoadExamBroadcast = new LoadExamBroadcast();
        mLoadQuestionBroadcast =new LoadQuestionBroadcast();
        setListener();
        initView();
        biz=new QuestionBiz();
        loadData();
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_Subject_Title));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        layoutLoading.setEnabled(false);
        dialog .setVisibility(View.VISIBLE);
        tvLoad .setText("下载数据...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void initView() {
        layoutLoading =(LinearLayout) findViewById(R.id.layout_loading);
        layout03 =(LinearLayout) findViewById(R.id.layout_03);
        layout04 =(LinearLayout) findViewById(R.id.layout_04);
        dialog =(ProgressBar) findViewById(R.id.load_dialog);
        tvSubjectTitle= (TextView) findViewById(R.id.tv_subjecttitle);
        tvQuestionTitle= (TextView) findViewById(R.id.tv_question_title);
        tvNo= (TextView) findViewById(R.id.tv_question_no);
        tvOption1= (TextView) findViewById(R.id.tv_option1);
        tvOption2= (TextView) findViewById(R.id.tv_option2);
        tvOption3= (TextView) findViewById(R.id.tv_option3);
        tvOption4= (TextView) findViewById(R.id.tv_option4);
        cb01 = (CheckBox) findViewById(R.id.cb_01);
        cb02 = (CheckBox) findViewById(R.id.cb_02);
        cb03 = (CheckBox) findViewById(R.id.cb_03);
        cb04 = (CheckBox) findViewById(R.id.cb_04);
        cbArray[0] = cb01;
        cbArray[1] = cb02;
        cbArray[2] = cb03;
        cbArray[3] = cb04;
        tvLoad = (TextView) findViewById(R.id.tv_load);
        jkImageView = (ImageView ) findViewById(R.id.im_exam_image) ;
        layoutLoading .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        cb01.setOnCheckedChangeListener(listener);
        cb02.setOnCheckedChangeListener(listener);
        cb03.setOnCheckedChangeListener(listener);
        cb04.setOnCheckedChangeListener(listener);
    }
    CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) {
                int userAnswer = 0;
                switch (buttonView.getId()) {
                    case R.id.cb_01:
                        userAnswer = 1;
                        break;
                    case R.id.cb_02:
                        userAnswer = 2;
                        break;
                    case R.id.cb_03:
                        userAnswer = 3;
                        break;
                    case R.id.cb_04:
                        userAnswer = 4;
                        break;
                }
                Log.e("checkedChanged","usera=" + userAnswer + ",isChecked="+isChecked );
                if (userAnswer > 0) {
                    for (CheckBox cb : cbArray) {
                        cb.setChecked(false);
                    }
                    cbArray[userAnswer - 1].setChecked(true);
                }
            }
        }
    };

    private void initData() {
        if(isLoadExamInfoReceiver && isLoadQuestionsReceiver){
            if(isLoadExamInfo && isLoadQuestions) {
                layoutLoading.setVisibility(View.GONE);
                SubjectTitle subjectTitle = ExamApplication.getInstance().getSubjectTitle();
                if (subjectTitle != null) {
                    showData(subjectTitle);
                }
                showQuestion(biz.getQuestion());
            }else{
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);  //设置隐藏
                tvLoad.setText("下载失败，点击重新下载");
            }
        }
    }

    private void showQuestion(Question question){
        Log.e("showQuestion","showQuestion,question="+question);
        if(question != null){
            tvNo .setText(biz.getQuestionIndex());
            tvQuestionTitle .setText(question .getQuestion());
            tvOption1 .setText(question .getItem1());
            tvOption2 .setText(question .getItem2());
            tvOption3 .setText(question .getItem3());
            tvOption4 .setText(question .getItem4());

            layout03.setVisibility(question.getItem3().equals("")? View.GONE:View.VISIBLE);
            cb03.setVisibility(question.getItem3().equals("")?View .GONE :View .VISIBLE );
            layout04.setVisibility(question.getItem4().equals("")? View.GONE:View.VISIBLE);
            cb04.setVisibility(question.getItem4().equals("")?View .GONE :View .VISIBLE );

            if(question.getUrl()!=null && !question.getUrl().equals("")) {
                jkImageView .setVisibility(View.VISIBLE);
                Picasso.with(ExamActivity.this)
                        .load(question.getUrl())
                        .into(jkImageView);
            }else{
                jkImageView .setVisibility(View.GONE);
            }
        }
    }

    private void showData(SubjectTitle subjectTitle) {
        tvSubjectTitle .setText(subjectTitle.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadExamBroadcast!=null){
            unregisterReceiver(mLoadExamBroadcast);
        }
        if (mLoadQuestionBroadcast!=null){
            unregisterReceiver(mLoadQuestionBroadcast);
        }
    }

    public void preQuestion(View view) {
        showQuestion(biz.preQuestion());
    }

    public void nextQuestion(View view) {
        showQuestion(biz.nextQuestion());
    }

    //广播一
    class LoadExamBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess="+isSuccess);
            if (isSuccess) {
                isLoadExamInfo = true;
            }
            isLoadExamInfoReceiver=true;
            initData();
        }
    }

    //广播二
    class LoadQuestionBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSuccess="+isSuccess);
            if (isSuccess){
                isLoadQuestions = true;
            }
            isLoadQuestionsReceiver =true;
            initData();
        }
    }
}
