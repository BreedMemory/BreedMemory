/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ReqSendVerifyCode.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.network.request;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractTask;
import com.yijiehl.club.android.network.response.BaseResponse;
import com.yijiehl.club.android.network.task.DefaultTask;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqSendVerifyCode <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/7 <br/>
 * @author 谌珂 <br/>
 */
public class ReqSendVerifyCode extends ReqBaseLogin {
    public ReqSendVerifyCode(Context context, String clientSecode, String loginAcct) {
        super(context, clientSecode);
        this.loginAcct = loginAcct;
    }

    /** 必填，登录账号，一般是手机 */
    private String loginAcct;
    /** 生成验证码方式 */
    private String buildMode = "sms";

    @Override
    public String getPath() {
        return "buildverifycode.htm";
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
