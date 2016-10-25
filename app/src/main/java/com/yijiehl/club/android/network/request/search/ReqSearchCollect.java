package com.yijiehl.club.android.network.request.search;


import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.response.RespSearchCollect;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqSearchCollect <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/24 <br/>
 *
 * @author 张志新 <br/>
 */
public class ReqSearchCollect extends ReqBaseSearch{

    public ReqSearchCollect(Context context) {
        super(context);
    }

    @Override
    protected String getBizType() {
        return "ext_favorite_item_my";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return RespSearchCollect.class;
    }
}
