package com.example.text1.jxbd;

import android.app.Application;
import android.util.Log;

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
    SubjectTitle mSubjectTitle;
    List<Question> mQuestionList;
    private static ExamApplication instance;  //申请私有的静态的实例化对象，去访问的是静态类

    @Override
    public void onCreate() {
        super.onCreate();
        instance =this;

        initData();
    }
    public static ExamApplication getInstance(){
        return instance;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils<SubjectTitle> utils1 = new OkHttpUtils<>(instance);
                String url1 = "http://101.251.196.90:8080/JztkServer/examInfo";
                utils1.url(url1)
                        .targetClass(SubjectTitle.class)
                        .execute(new OkHttpUtils.OnCompleteListener<SubjectTitle>() {
                            @Override
                            public void onSuccess(SubjectTitle result) {
                                Log.e("main", "result=" + result);
                                mSubjectTitle = result;
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main", "error=" + error);
                            }
                        });
                OkHttpUtils<String> utils2 = new OkHttpUtils<>(instance);
                String url2 = "http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils2.url(url2)
                        .targetClass(String.class)
                        .execute(new OkHttpUtils.OnCompleteListener<String>() {
                            @Override
                            public void onSuccess(String jsonStr) {
                                Result result = ResultUtils.getListResultFromJson(jsonStr);
                                if (result != null && result.getError_code() == 0) {
                                    List<Question> list = result.getResult();
                                    if (list != null && list.size() > 0) {
                                        mQuestionList = list;
                                    }
                                }
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main", "error=" + error);
                            }
                        });
            }
        }).start();
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
