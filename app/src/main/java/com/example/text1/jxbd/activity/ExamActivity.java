package com.example.text1.jxbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
    TextView tvSubjectTitle,tvQuestionTitle,tvOption1,tvOption2,tvOption3,tvOption4;
    ImageView jkImageView;
    IExamBiz biz;
    boolean isLoadExamInfo = false;
    boolean isLoadQuestions = false;

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
        loadData();
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_Subject_Title));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        biz=new QuestionBiz();
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();
    }

    private void initView() {
        tvSubjectTitle= (TextView) findViewById(R.id.tv_subjecttitle);
        tvQuestionTitle= (TextView) findViewById(R.id.tv_question_title);
        tvOption1= (TextView) findViewById(R.id.tv_option1);
        tvOption2= (TextView) findViewById(R.id.tv_option2);
        tvOption3= (TextView) findViewById(R.id.tv_option3);
        tvOption4= (TextView) findViewById(R.id.tv_option4);
        jkImageView = (ImageView ) findViewById(R.id.im_exam_image) ;
    }

    private void initData() {
        if(isLoadExamInfo && isLoadQuestions) {
            SubjectTitle subjectTitle = ExamApplication.getInstance().getSubjectTitle();
            if (subjectTitle != null) {
                showData(subjectTitle);
            }
            List<Question> questionList = ExamApplication.getInstance().getQuestionList();
            if (questionList != null) {
                showQuestion(questionList);
            }
        }
    }

    private void showQuestion(List<Question> questionList) {
        Question question = questionList.get(0);
        if(question != null){
            tvQuestionTitle .setText(question .getQuestion());
            tvOption1 .setText(question .getItem1());
            tvOption2 .setText(question .getItem2());
            tvOption3 .setText(question .getItem3());
            tvOption4 .setText(question .getItem4());
            Picasso .with(ExamActivity.this)
                    .load(question .getUrl())
                    .into(jkImageView);
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

    class LoadExamBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            Log.e("LoadExamBroadcast","LoadExamBroadcast,isSuccess="+isSuccess);
            if (isSuccess) {
                isLoadExamInfo = true;
            }
            initData();
        }
    }

    class LoadQuestionBroadcast extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS, false);
            Log.e("LoadQuestionBroadcast","LoadQuestionBroadcast,isSuccess="+isSuccess);
            if (isSuccess){
                isLoadQuestions = true;
            }
            initData();
        }
    }


}
