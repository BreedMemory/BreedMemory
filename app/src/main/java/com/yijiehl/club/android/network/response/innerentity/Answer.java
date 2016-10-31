package com.yijiehl.club.android.network.response.innerentity;

import com.yijiehl.club.android.network.response.base.RespBaseSearchResult;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: Answer <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/14 <br/>
 *
 * @author 张志新 <br/>
 */
public class Answer extends RespBaseSearchResult {

    /**
     * 数据编码
     */
    private String dateCode;
    /**
     * 内容
     */
    private String dataContent;
    /**
     * 描述、补充说明
     */
    private String dataDesc;
    /**
     * 图片URL,可为空
     */
    private String imageInfo;
    /**
     * 分类描述，用于显示
     */
    private String clfyDesc;
    /**
     * 状态
     */
    private int dataStatus;
    /**
     * 回复标记,0表示未回复 大于0表示回复过
     */
    private int eplyFlag;
    /**
     * 回复时间,可为空，长整型时间
     */
    private long replyTime;
    /**
     * 最新/最近回复内容
     */
    private String replyInfo;
    /**
     * 是否已经收藏
     */
    private boolean isCollected;

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public String getDateCode() {
        return dateCode;
    }

    public void setDateCode(String dateCode) {
        this.dateCode = dateCode;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public String getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(String imageInfo) {
        this.imageInfo = imageInfo;
    }

    public String getClfyDesc() {
        return clfyDesc;
    }

    public void setClfyDesc(String clfyDesc) {
        this.clfyDesc = clfyDesc;
    }

    public int getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(int dataStatus) {
        this.dataStatus = dataStatus;
    }

    public int getEplyFlag() {
        return eplyFlag;
    }

    public void setEplyFlag(int eplyFlag) {
        this.eplyFlag = eplyFlag;
    }

    public long getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(long replyTime) {
        this.replyTime = replyTime;
    }

    public String getReplyInfo() {
        return replyInfo;
    }

    public void setReplyInfo(String replyInfo) {
        this.replyInfo = replyInfo;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDataShowUrl() {
        return dataShowUrl;
    }

    public void setDataShowUrl(String dataShowUrl) {
        this.dataShowUrl = dataShowUrl;
    }

    /**

     * 显示URL,如值不为空可以点击进入查看显示页面
     */
    private String dataShowUrl;
    /**
     * 创建时间,长整型时间
     */
    private long createTime;

}
