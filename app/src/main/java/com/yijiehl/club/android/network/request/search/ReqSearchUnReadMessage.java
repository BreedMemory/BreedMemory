package com.yijiehl.club.android.network.request.search;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.response.RespSearchMyMessage;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqSearchUnReadMessage <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/12/04 <br/>
 *
 * @author 张志新 <br/>
 */
public class ReqSearchUnReadMessage extends ReqBaseSearch {

    public ReqSearchUnReadMessage(Context context) {
        super(context);
    }

    @Override
    public String getBizType() {
        return "notice_item_unread";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return RespSearchMyMessage.class;
    }
}
