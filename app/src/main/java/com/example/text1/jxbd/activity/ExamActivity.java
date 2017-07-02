package com.example.text1.jxbd.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        initData();
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
        SubjectTitle subjectTitle = ExamApplication.getInstance().getSubjectTitle();
        if(subjectTitle!=null){
            showData(subjectTitle);
        }
        List<Question> questionList=ExamApplication .getInstance().getQuestionList() ;
        if(questionList != null){
            showQuestion(questionList);
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
}
