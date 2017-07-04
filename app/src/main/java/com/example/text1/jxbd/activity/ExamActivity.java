package com.example.text1.jxbd.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ExamActivity extends AppCompatActivity{
    TextView tvSubjectTitle,tvQuestionTitle,tvOption1,tvOption2,tvOption3,tvOption4,tvLoad,tvNo,tvTime;
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
        tvTime= (TextView) findViewById(R.id.tv_time);
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
                    initTimer(subjectTitle);
                }
                showQuestion(biz.getQuestion());
            }else{
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);  //设置隐藏
                tvLoad.setText("下载失败，点击重新下载");
            }
        }
    }

    //剩余时间的显示
    private void initTimer(SubjectTitle subjectTitle) {
        int sumTime = subjectTitle.getLimitTime() * 60 * 1000;
        //Log.e("time","sumTime="+sumTime);
        final long overTime = sumTime + System.currentTimeMillis();
        //Log.e("time","overTime="+overTime);
        final Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long Res=overTime - System.currentTimeMillis();  //剩余时长=结束时间-当前时间
                //Log.e("time","Res="+Res);
                final long min = Res/1000/60;              //剩余时长的分钟
                final long second = Res/1000%60;           //剩余时长的秒钟
                //Log.e("time","min="+min+",second"+second);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTime.setText("剩余时间："+ min +"分" + second +"秒");
                    }
                });
            }
        },0,1000);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        commit(null);
                    }
                });
            }
        },sumTime);
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
            resetOptions();
            String userAnswer = question.getUserAnswer();
            if(userAnswer!=null && !userAnswer.equals("")){
                int userCB = Integer.parseInt(userAnswer)-1;
                cbArray[userCB].setChecked(true);
            }
        }
    }

    //清空选择
    private void resetOptions() {
        for(CheckBox cb : cbArray){
            cb.setChecked(false);
        }
    }

    //保存正确答案
    private void saveUserAnswer(){
        for(int i = 0;i < cbArray.length;i++){
            if(cbArray [i].isChecked()){
                biz.getQuestion().setUserAnswer(String.valueOf(i+1));
                return;
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

    //上一题
    public void preQuestion(View view) {
        saveUserAnswer();
        showQuestion(biz.preQuestion());
    }

    //上一题
    public void nextQuestion(View view) {
        saveUserAnswer();
        showQuestion(biz.nextQuestion());
    }

    //返回显示考生成绩
    public void commit(View view) {
        saveUserAnswer();
        int score = biz.commitExam();
        View inflate = View.inflate(this, R.layout.layout_result, null);
        TextView tvResult=(TextView) inflate.findViewById(R.id.tv_result);
        tvResult.setText("您的分数为：\n" + score + "分！");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.exam_commit32x32)
                .setTitle("交卷")
                .setView(inflate)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.create().show();
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
