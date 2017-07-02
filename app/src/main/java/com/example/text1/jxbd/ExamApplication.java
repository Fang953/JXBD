package com.example.text1.jxbd;

import android.app.Application;
import android.util.Log;

import com.example.text1.jxbd.Biz.IExamBiz;
import com.example.text1.jxbd.Biz.QuestionBiz;
import com.example.text1.jxbd.bean.Question;
import com.example.text1.jxbd.bean.Result;
import com.example.text1.jxbd.bean.SubjectTitle;
import com.example.text1.jxbd.utils.OkHttpUtils;
import com.example.text1.jxbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ExamApplication extends Application {
    public static String LOAD_Subject_Title = "load_subject_title";
    public static String LOAD_EXAM_QUESTION = "load_exam_question";
    public static String LOAD_DATA_SUCCESS = "load_data_success";
    SubjectTitle mSubjectTitle;
    List<Question> mQuestionList;
    private static ExamApplication instance;  //申请私有的静态的实例化对象，去访问的是静态类
    IExamBiz biz;

    @Override
    public void onCreate() {
        super.onCreate();
        instance =this;
    }
    public static ExamApplication getInstance(){
        return instance;
    }

    public SubjectTitle getSubjectTitle() {
        return mSubjectTitle;
    }

    public void setSubjectTitle(SubjectTitle subjectTitle) {
        mSubjectTitle = subjectTitle;
    }

    public List<Question> getQuestionList() {
        return mQuestionList;
    }

    public void setQuestionList(List<Question> questionList) {
        mQuestionList = questionList;
    }
}
