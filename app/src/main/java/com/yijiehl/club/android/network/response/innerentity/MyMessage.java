/**
 * 项目名称：手机大管家
 * 文件名称: MyMessage.java
 * Created by 谌珂 on 2016/10/31.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.response.innerentity;

import com.yijiehl.club.android.network.response.base.RespBaseSearchResult;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: MyMessage<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class MyMessage extends RespBaseSearchResult {

    private String dataContent;
    private String noticeTime;
    private String opNum;

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public String getNoticeTime() {
        return noticeTime;
    }

    public void setNoticeTime(String noticeTime) {
        this.noticeTime = noticeTime;
    }

    public String getOpNum() {
        return opNum;
    }

    public void setOpNum(String opNum) {
        this.opNum = opNum;
    }
}
