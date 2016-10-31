/**
 * 项目名称：手机大管家
 * 文件名称: ReqSearchMyMessage.java
 * Created by 谌珂 on 2016/10/31.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request.search;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.response.RespSearchMyMessage;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: ReqSearchMyMessage<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class ReqSearchMyMessage extends ReqBaseSearch {
    public ReqSearchMyMessage(Context context) {
        super(context);
        this.dataClfy = "all";
    }

    @Override
    protected String getBizType() {
        return "notice_item_my";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return RespSearchMyMessage.class;
    }
}
