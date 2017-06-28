package com.example.text1.jxbd.bean;

/**
 * Created by Administrator on 2017/6/29.
 */

public class result {

    /**
     * id : 13
     * question : 这个标志是何含义？
     * answer : 4
     * item1 : 十字交叉路口预告
     * item2 : 互通立体交叉预告
     * item3 : Y型交叉路口预告
     * item4 : 环行交叉路口预告
     * explains : 环形交叉口是在几条相交道路的平面交叉口中央设置一个半径较大的中心岛，使所有经过交叉口的直行和左转车辆都绕着中心岛作逆时针方向行驶，在其行驶过程中将车流的冲突点变 为交织点，从而保证交叉口的行车安全，提高交叉口的通行能力。
     * url : http://images.juheapi.com/jztk/c1c2subject1/13.jpg
     */

    private int id;
    private String question;
    private String answer;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String explains;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getItem1() {
        return item1;
    }

    public void setItem1(String item1) {
        this.item1 = item1;
    }

    public String getItem2() {
        return item2;
    }

    public void setItem2(String item2) {
        this.item2 = item2;
    }

    public String getItem3() {
        return item3;
    }

    public void setItem3(String item3) {
        this.item3 = item3;
    }

    public String getItem4() {
        return item4;
    }

    public void setItem4(String item4) {
        this.item4 = item4;
    }

    public String getExplains() {
        return explains;
    }

    public void setExplains(String explains) {
        this.explains = explains;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
