/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: AbstractRequest.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/1/4.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.uuzz.android.util.net.request;

import android.content.Context;

import com.alibaba.fastjson.annotation.JSONField;
import com.uuzz.android.util.Common;
import com.uuzz.android.util.net.task.AbstractTask;

import java.util.UUID;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: AbstractRequest <br/>
 * 类描述: 所有业务请求报文的基类<br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/1/4 <br/>
 * @author 谌珂 <br/>
 */
public abstract class AbstractRequest {
    public AbstractRequest(Context context) {
//        session = ContextUtils.getSharedString(context, R.string.sharedperference_session);
    }

    /** 客户端版本号 */
    protected String client_version = Common.APP_VERSION;
    /** 客户端平台 */
    protected String platform = "android";
    /** 系统版本号 */
    protected String os_version = android.os.Build.VERSION.RELEASE;
    /** 随机生成的UUID */
    protected String client_id = UUID.randomUUID().toString().replace("-", "");
    /** 会话标识 */
    protected String session;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getClient_version() {
        return client_version;
    }

    public void setClient_version(String client_version) {
        this.client_version = client_version;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getOs_version() {
        return os_version;
    }

    public void setOs_version(String os_version) {
        this.os_version = os_version;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    /**
     * 描 述：用于自检请求消息是否符合规范的方法，发送请求前检验数据的最后一道屏障<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/4 注释 <br/>
     * @return 检测成功返回true，失败返回false
     */
    @JSONField(serialize = false)
    public boolean selfCheck() {
        return true;
    }

    /**
     * 描 述：获取接口最后的路径<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/4 注释 <br/>
     */
    @JSONField(serialize = false)
    public abstract String getPath();

    /**
     * 描 述：获取需要启动的任务class<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/4 注释 <br/>
     */
    @JSONField(serialize = false)
    public abstract Class<? extends AbstractTask> getTaskClass();
}
