package com.yijiehl.club.android.network.response.innerentity;

import com.yijiehl.club.android.network.response.base.RespBaseSearchResult;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: Vaccine <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/19 <br/>
 *
 * @author 张志新 <br/>
 */
public class Vaccine extends RespBaseSearchResult {

    /**
     * 数据编码，
     */
    private String dataCode;
    /**
     * 内容,内容、说明等
     */
    private String dataContent;
    /**
     * 提醒时间,可为空，自己添加有值
     */
    private long opTime;

    public long getOpTime() {
        return opTime;
    }

    public void setOpTime(long opTime) {
        this.opTime = opTime;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }
}
