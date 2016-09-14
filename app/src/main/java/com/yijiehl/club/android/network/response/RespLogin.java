/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: RespLogin.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.network.response;

import android.text.TextUtils;

import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: RespLogin <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/7 <br/>
 * @author 谌珂 <br/>
 */
public class RespLogin extends BaseResponse {
    /** 非空, 用于唯一标识一个用户/客户, 不会改变，客户端可以以这个编码创建用户/客户文件夹等来保存用户/客户本地信息。*/
    private String uccode;
    /** 非空, 用于唯一标识一个用户/客户 */
    private String ucid;
    /** 非空, 登录会话编码, 每次登录都会不一样, 用于标识整个会话, 后面所有与服务器通讯都要带上它， 一个三十位左右的唯一串 */
    private String secode;
    /** 当前账号状态编码，不同的状态可以查询数据的权限不一样，操作也可能不同，详细见下面说明
     * init 初始状态，该状态下需要进入录入基本信息界面
     * general 一般账号，普通账号，已录入基本信息但可能还不准备使用会所等相关的服务
     * service_before 服务前，比如入住前，准备或有意向使用服务
     * service_in 服务中，比如入住中，正在使用服务
     * service_after 服务后，比如入住后，已经使用过服务了 */
    private String acctStatus;
    /** 可为空，消息收发服务器地址，可能与平台不是同一台服务器，形如 http://msg.yoursite.com/ ，值为空时取登录的URL前缀 */
    private String msgUrl;
    /** 可为空，一般用于图片、文件等资源的操作与访问，如下面的文件上传、下载接口，值由服务端动态提供 */
    private String resourceUrl;
    /** 返回客户端的配置信息，这里面有些信息需要保存在本地，登录或会话检查后再同步更新 */
    private UserInfo cfgParams;

    public String getUccode() {
        return uccode;
    }

    public void setUccode(String uccode) {
        this.uccode = uccode;
    }

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

    public String getAcctStatus() {
        return acctStatus;
    }

    public void setAcctStatus(String acctStatus) {
        this.acctStatus = acctStatus;
    }

    public String getMsgUrl() {
        return msgUrl;
    }

    public void setMsgUrl(String msgUrl) {
        this.msgUrl = msgUrl;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public UserInfo getCfgParams() {
        return cfgParams;
    }

    public void setCfgParams(UserInfo cfgParams) {
        this.cfgParams = cfgParams;
    }

    public AccountStatus getAccountStatus() {
        return AccountStatus.setValue(acctStatus);
    }

    /**
     * 描 述：用户状态枚举，<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/7 <br/>
     */
    public enum AccountStatus {
        INIT(0, "init"),
        GENERAL(1, "general"),
        SERVICE_BEFORE(2, "service_before"),
        SERVICE_IN(3, "service_in"),
        SERVICE_AFTER(4, "service_after");

        private int value = 0;
        private String name = "";

        AccountStatus(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public static AccountStatus setValue(String name) {
            AccountStatus[] arr$ = values();
            for (AccountStatus c : arr$) {
                if (TextUtils.equals(c.getName(), name)) {
                    return c;
                }
            }
            return INIT;
        }
    }
}
