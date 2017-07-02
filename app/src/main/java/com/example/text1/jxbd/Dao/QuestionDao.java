package com.example.text1.jxbd.Dao;

import android.content.Intent;
import android.util.Log;

import com.example.text1.jxbd.ExamApplication;
import com.example.text1.jxbd.bean.Question;
import com.example.text1.jxbd.bean.Result;
import com.example.text1.jxbd.bean.SubjectTitle;
import com.example.text1.jxbd.utils.OkHttpUtils;
import com.example.text1.jxbd.utils.ResultUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class QuestionDao implements IExamDao {
    @Override
    public void loadExamInfo() {
        OkHttpUtils<SubjectTitle> utils1 = new OkHttpUtils<>(ExamApplication.getInstance());
        String url1 = "http://101.251.196.90:8080/JztkServer/examInfo";
        utils1.url(url1)
                .targetClass(SubjectTitle.class)
                .execute(new OkHttpUtils.OnCompleteListener<SubjectTitle>() {
                    @Override
                    public void onSuccess(SubjectTitle result) {
                        Log.e("main", "result=" + result);
                        ExamApplication.getInstance().setSubjectTitle(result);
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_Subject_Title )
                                .putExtra(ExamApplication.LOAD_DATA_SUCCESS,true));
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main", "error=" + error);
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_Subject_Title )
                                .putExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
                    }
                });
    }

    @Override
    public void loadQuestionLists() {
        OkHttpUtils<String> utils2 = new OkHttpUtils<>(ExamApplication.getInstance());
        String url2 = "http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
        utils2.url(url2)
                .targetClass(String.class)
                .execute(new OkHttpUtils.OnCompleteListener<String>() {
                    @Override
                    public void onSuccess(String jsonStr) {
                        boolean success = false;
                        Result result = ResultUtils.getListResultFromJson(jsonStr);
                        if (result != null && result.getError_code() == 0) {
                            List<Question> list = result.getResult();
                            if (list != null && list.size() > 0) {
                                ExamApplication.getInstance().setQuestionList(list);
                                success = true;
                            }
                        }
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                                .putExtra(ExamApplication.LOAD_DATA_SUCCESS,success));
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main", "error=" + error);
                        ExamApplication.getInstance()
                                .sendBroadcast(new Intent(ExamApplication.LOAD_EXAM_QUESTION)
                                .putExtra(ExamApplication.LOAD_DATA_SUCCESS,false));
                    }
                });
    }
}
