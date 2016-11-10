package com.yijiehl.club.android.network.request.search;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.response.RespSearchSignIn;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqSearchSignIn <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/19 <br/>
 *
 * @author 张志新 <br/>
 */
public class ReqSearchSignIn extends ReqBaseSearch{

    public ReqSearchSignIn(Context context) {
        super(context);
    }

    @Override
    public String getBizType() {
        return "ext_signin_item_my";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return RespSearchSignIn.class;
    }

    @Override
    public boolean isGet() {
        return true;
    }
}
