package com.yijiehl.club.android.network.request;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractTask;
import com.yijiehl.club.android.network.request.base.ReqBase;
import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.task.DefaultTask;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqLogout <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/13 <br/>
 * @author 张志新 <br/>
 */
public class ReqLogout extends ReqBase{

    public ReqLogout(Context context) {
        super(context);
    }

    @Override
    public String getPath() {
        return "clientlogout.htm";
    }

    @Override
    public Class<? extends AbstractTask> getTaskClass() {
        return DefaultTask.class;
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return BaseResponse.class;
    }
}
