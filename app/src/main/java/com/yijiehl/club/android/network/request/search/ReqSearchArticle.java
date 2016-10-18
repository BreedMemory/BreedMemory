package com.yijiehl.club.android.network.request.search;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.response.RespSearchArticle;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqSearchArticle <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/14 <br/>
 *
 * @author 张志新 <br/>
 */
public class ReqSearchArticle extends ReqBaseSearch {

    /**
     * 描 述：查询文章<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/9/14 <br/>
     * @param context 上下文 查询全部的文章
     */
    public ReqSearchArticle(Context context) {
        super(context);
    }

    /**
     * 描 述：查询文章<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/9/14 <br/>
     * @param context 上下文
     * @param isHealth 如果为true查询健康的文章，false查询教育的文章
     */
    public ReqSearchArticle(Context context, boolean isHealth) {
        super(context);
        this.dataClfy = isHealth ? "health" : "education";
    }

    @Override
    protected String getBizType() {
        return "kb_article_growup";
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
;