/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ReqLogin.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.network.request;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractTask;
import com.yijiehl.club.android.network.request.base.ReqBaseLogin;
import com.yijiehl.club.android.network.response.RespLogin;
import com.yijiehl.club.android.network.task.DefaultTask;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqLogin <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/7 <br/>
 * @author 谌珂 <br/>
 */
public class ReqLogin extends ReqBaseLogin {
    public ReqLogin(Context context, String clientSecode, String loginAcct, String verifyCode) {
        super(context, clientSecode);
        this.loginAcct = loginAcct;
        this.verifyCode = verifyCode;
    }

    /** 必填，登录账号，一般是手机 */
    private String loginAcct;
    /** 可选，登录密码和验证码不能同时为空，密码以base64进行加密传输 */
    private String loginPwd;
    /** 可选，登录密码和验证码不能同时为空，用户输入收到或看到的短信/图片验证码 */
    private String verifyCode;

    public String getLoginAcct() {
        return loginAcct;
    }

    public void setLoginAcct(String loginAcct) {
        this.loginAcct = loginAcct;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
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
