package com.yijiehl.club.android.network.request.search;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.response.RespSearchQuestion;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqSearchQuestion <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/14 <br/>
 *
 * @author 张志新 <br/>
 */
public class ReqSearchQuestion extends ReqBaseSearch {


    public ReqSearchQuestion(Context context,String key){
        super(context);
        setKeyword(key);
    }
    public ReqSearchQuestion(Context context, String type,String key,int start) {
        super(context);
        this.dataClfy = type;
        setKeyword(key);
        this.start = start;
    }


    @Override
    protected String getBizType() {
        return "kb_question_main";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return RespSearchQuestion.class;
    }

}
