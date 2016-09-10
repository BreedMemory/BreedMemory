/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: ReqSensitize.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/8.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request;

import android.content.Context;

import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractTask;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.RespLogin;
import com.yijiehl.club.android.network.task.DefaultTask;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: ReqSensitize <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/8 <br/>
 * @author 谌珂 <br/>
 */
public class ReqSensitize extends BmRequest {

    public ReqSensitize(Context context) {
        this.ucid = ContextUtils.getSharedString(context, R.string.shared_preference_ucid);
        this.secode = ContextUtils.getSharedString(context, R.string.shared_preference_secode);
    }

    /** 用户客户端标识 */
    private String ucid;
    /** 会话唯一编码 */
    private String secode;

    public String getUcid() {
        return ucid;
    }

    public void setUcid(String ucid) {
        this.ucid = ucid;
    }

    public String getSecode() {
        return secode;
    }

    public void setSecode(String secode) {
        this.secode = secode;
    }

    @Override
    public String getPath() {
        return "crmverify.htm";
    }

    @Override
    public Class<? extends AbstractTask> getTaskClass() {
        return DefaultTask.class;
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return RespLogin.class;
    }
}
