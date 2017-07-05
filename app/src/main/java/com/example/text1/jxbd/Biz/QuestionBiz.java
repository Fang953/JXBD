package com.example.text1.jxbd.Biz;

import com.example.text1.jxbd.Dao.IExamDao;
import com.example.text1.jxbd.Dao.QuestionDao;
import com.example.text1.jxbd.ExamApplication;
import com.example.text1.jxbd.bean.Question;

import java.util.List;

/**
 * Created by Administrator on 2017/7/2.
 */

public class QuestionBiz implements IExamBiz{
    IExamDao dao;
    int questionIndex=0;
    List<Question> questionList=null;

    public QuestionBiz(){
        this.dao = new QuestionDao();
    }
    @Override
    public void beginExam() {
        questionIndex =0;
        dao.loadExamInfo();
        dao.loadQuestionLists();
    }

    @Override
    public Question getQuestion() {
        questionList = ExamApplication.getInstance().getQuestionList();
        if(questionList!=null) {
            return questionList.get(questionIndex);
        }else {
            return null;
        }
    }

    @Override
    public Question getQuestion(int index) {
        questionList = ExamApplication.getInstance().getQuestionList();
        questionIndex = index;
        if(questionList!=null) {
            return questionList.get(questionIndex);
        }else {
            return null;
        }
    }

    @Override
    public Question nextQuestion() {
        if(questionList!=null && questionIndex < questionList .size() -1) {
            questionIndex++;
            return questionList.get(questionIndex);
        }else {
            return null;
        }
    }

    @Override
    public Question preQuestion() {
        if(questionList!=null && questionIndex > 0) {
            questionIndex--;
            return questionList.get(questionIndex);
        }else {
            return null;
        }
    }

    @Override
    public int commitExam() {
        int score=0;
        for (Question exam : questionList) {
            String userAnswer = exam.getUserAnswer();
            if (userAnswer!=null && !userAnswer.equals("")){
                if (exam.getAnswer().equals(userAnswer)){
                    score++;
                }
            }
        }
        return score;
    }

    @Override
    public String getQuestionIndex() {
        //设置题号的变化
        return (questionIndex + 1) + ".";
    }
}
