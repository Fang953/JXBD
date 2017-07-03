package com.example.text1.jxbd.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class Result {

    /**
     * error_code : 0
     * reason : ok
     * Result : [{"id":6,"question":"这个标志是何含义？","answer":"4","item1":"右转车道","item2":"掉头车道","item3":"左转车道","item4":"直行车道","explains":"表示只准一切车辆直行。此标志设在直行的路口以前适当位置。","url":"http://images.juheapi.com/jztk/c1c2subject1/6.jpg"}]
     */

    private int error_code;
    private String reason;
    private List<Question> result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<Question> getResult() {
        return result;
    }

    public void setResult(List<Question> result) {
        this.result = result;
    }

}