package com.yijiehl.club.android.network.request.search;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.response.RespSearchArticle;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqSearchKnowledge <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/24 <br/>
 *
 * @author 张志新 <br/>
 */
public class ReqSearchKnowledge extends ReqBaseSearch {
    public ReqSearchKnowledge(Context context) {
        super(context);
    }

    public ReqSearchKnowledge(Context context, String type) {
        super(context);
        this.dataClfy = type;
    }

    @Override
    protected String getBizType() {
        return "kb_article_knowledge";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return RespSearchArticle.class;
    }

    @Override
    public boolean isGet() {
        return true;
    }
}
