/**
 * 项目名称：手机大管家
 * 文件名称: ReqSearchExtraFile.java
 * Created by 谌珂 on 2016/10/27.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request.search;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.response.RespSearchExtraFile;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: ReqSearchExtraFile<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class ReqSearchExtraFile extends ReqBaseSearch {
    public ReqSearchExtraFile(Context context, String relateId) {
        super(context);
        this.relateId = relateId;
    }

    private String relateId;

    public String getRelateId() {
        return relateId;
    }

    public void setRelateId(String relateId) {
        this.relateId = relateId;
    }

    @Override
    protected String getBizType() {
        return "ext_file_item";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return RespSearchExtraFile.class;
    }
}
