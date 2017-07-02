package com.example.text1.jxbd.Biz;

import com.example.text1.jxbd.Dao.IExamDao;
import com.example.text1.jxbd.Dao.QuestionDao;

/**
 * Created by Administrator on 2017/7/2.
 */

public class QuestionBiz implements IExamBiz{
    IExamDao dao;

    public QuestionBiz(){
        this.dao = new QuestionDao();
    }
    @Override
    public void beginExam() {
        dao.loadExamInfo();
        dao.loadQuestionLists();
    }

    @Override
    public void nextQuestion() {

    }

    @Override
    public void preQuestion() {

    }

    @Override
    public void commitExam() {

    }
}
