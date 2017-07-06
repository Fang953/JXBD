package com.example.text1.jxbd.view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.text1.jxbd.ExamApplication;
import com.example.text1.jxbd.R;
import com.example.text1.jxbd.bean.Question;

import java.util.List;


/**
 * Created by Administrator on 2017/7/4.
 */

public class QuestionAdapter extends BaseAdapter {
    Context mContext;
    List<Question> questionList;

    public QuestionAdapter(Context context) {
        mContext = context;
        questionList = ExamApplication.getInstance().getQuestionList();
    }

    @Override
    public int getCount() {
        return questionList==null?0:questionList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(mContext, R.layout.item_question,null);
        TextView tvNum = (TextView) view.findViewById(R.id.tv_num);
        ImageView ivQuestion =(ImageView) view.findViewById(R.id.iv_question);
        String ua = questionList.get(position).getUserAnswer();
        String ra = questionList.get(position).getAnswer();
        Log.e("adapter","questionList.get(position)="+questionList.get(position));
        if(ua!=null && !ua.equals("")){
            ivQuestion.setImageResource(ua.equals(ra)
                    ?R.mipmap.answer24x24:R.mipmap.Error);
        }else{
            ivQuestion.setImageResource(R.mipmap.unQuestion);
        }
        tvNum.setText("第"+(position+1)+"题");
        return view;
    }
}
