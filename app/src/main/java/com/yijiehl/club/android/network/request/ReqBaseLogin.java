/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ReqBaseLogin.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.network.request;

import android.content.Context;

import com.yijiehl.club.android.common.Common;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: ReqBaseLogin <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/7 <br/>
 * @author 谌珂 <br/>
 */
public abstract class ReqBaseLogin extends BmRequest {
    public ReqBaseLogin(Context context, String clientSecode) {
        clientInfo = new ClientInfo(context);
        this.clientSecode = clientSecode;
    }

    /** 客户端会话编码，必须保证与登录接口值一样 */
    protected String clientSecode;
    /** 客户端类型 */
    protected String clientType = "android";
    /** 客户端详细信息，有很多项 */
    protected ClientInfo clientInfo;
    /** 一个json串, 用于存放以后扩展的其它请求参数 */
    protected String cfgParams;

    public String getClientSecode() {
        return clientSecode;
    }

    public void setClientSecode(String clientSecode) {
        this.clientSecode = clientSecode;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getClientNum() {
        return clientNum;
    }

    public void setClientNum(String clientNum) {
        this.clientNum = clientNum;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getCfgParams() {
        return cfgParams;
    }

    public void setCfgParams(String cfgParams) {
        this.cfgParams = cfgParams;
    }
}
