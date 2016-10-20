package com.yijiehl.club.android.network.response.innerentity;

import com.yijiehl.club.android.network.response.base.RespBaseSearchResult;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: Article <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/14 <br/>
 *
 * @author 张志新 <br/>
 */
public class Article extends RespBaseSearchResult {

    private boolean isCollected;

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    /**
     * 数据编码,该值可用于修改和查看详情
     */
    private String dataCode;
    /**
     * 数据标签
     */
    private String dataLable;
    /**
     * 数据摘要/概述,文章摘要/概述
     */
    private String dataSummary;
    /**
     * 数据内容,可为空
     */
    private String dataContent;
    /**
     * 图片URL,比如封面图片URL，只有后缀路径，值形如
     * /resacc.htm?cd=xxxxxxxxxxx_1.jpg
     */
    private String imageInfo;
    /**
     * 显示URL,如值不为空可以点击进入查看显示页面，还可用于分享等
     */
    private String dataShowUrl;
    /**
     * 查看次数,可为空
     */
    private String viewNum;
    /**
     * 创建时间,长整型时间
     */
    private long createTime;

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getDataLable() {
        return dataLable;
    }

    public void setDataLable(String dataLable) {
        this.dataLable = dataLable;
    }

    public String getDataSummary() {
        return dataSummary;
    }

    public void setDataSummary(String dataSummary) {
        this.dataSummary = dataSummary;
    }

    public String getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(String imageInfo) {
        this.imageInfo = imageInfo;
    }

    public String getDataContent() {
        return dataContent;
    }

    public void setDataContent(String dataContent) {
        this.dataContent = dataContent;
    }

    public String getDataShowUrl() {
        return dataShowUrl;
    }

    public void setDataShowUrl(String dataShowUrl) {
        this.dataShowUrl = dataShowUrl;
    }

    public String getViewNum() {
        return viewNum;
    }

    public void setViewNum(String viewNum) {
        this.viewNum = viewNum;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
