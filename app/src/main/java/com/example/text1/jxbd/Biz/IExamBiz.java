package com.example.text1.jxbd.Biz;

import com.example.text1.jxbd.bean.Question;

/**
 * Created by Administrator on 2017/7/2.
 */

public interface IExamBiz {
    void beginExam();
    Question getQuestion();
    Question getQuestion(int index);
    Question nextQuestion();
    Question preQuestion();
    int commitExam();
    String getQuestionIndex();
}
