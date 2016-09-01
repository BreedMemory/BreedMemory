/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: DefaultTask.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/1/11.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.uuzz.android.util.net.task;

import android.content.Context;

import com.uuzz.lotteryagent.network.AgentRequest;
import com.uuzz.lotteryagent.network.NetHelper;
import com.uuzz.lotteryagent.network.request.AbstractRequest;
import com.uuzz.lotteryagent.network.response.AbstractResponse;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: DefaultTask <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/1/11 <br/>
 * @author 谌珂 <br/>
 */
public abstract class DefaultTask extends AbstractTask {
    public DefaultTask(AbstractRequest lRequest, Context context) {
        super(lRequest, context);
    }

    @Override
    protected AgentRequest createAgentRequest(AbstractRequest lRequest) {
        return NetHelper.createAgentRequest(lRequest);
    }

    @Override
    protected AbstractResponse createHttpResponse(String data) {
        return NetHelper.createResponse(mContext, data, getResponseClass());
    }

    @Override
    protected boolean isSingleHttp() {
        return false;
    }
}
