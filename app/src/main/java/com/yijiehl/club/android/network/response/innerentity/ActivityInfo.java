/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ActivityInfo.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.network.response.innerentity;

import com.yijiehl.club.android.network.response.base.RespBaseSearchResult;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ActivityInfo <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/14 <br/>
 * @author 谌珂 <br/>
 */
public class ActivityInfo extends RespBaseSearchResult {
    /** 活动编号 */
    private String dataCode;
    /** 活动类型 */
    private String dataType;
    /** 数据标签 */
    private String dataLabel;
    /** 可为空，一般用于宣传类图片的展现，如果没有值可能需要放置一张默认图片 */
    private String imageInfo;
    /** 数据展现URL */
    private String dataShowUrl;
    /** 数据内容 */
    private String dataContent;
    /** 数据描述 */
    private String dataDesc;
    /** 开始时间 格式形如 2014-08-08 10:30:00 */
    private String startTimeStr;
    /** 开始时间(长整型) */
    private long startTime;
    /** 结束时间 格式形如 2014-08-08 11:30:00 */
    private String endTimeStr;
    /** 结束时间(长整型) */
    private long endTime;
    /** 参加人数 */
    private int dataNum;
    /** 最大人数 */
    private int dataMax;
    /** 长整型时间 */
    private long createTime;
    /** 状态 0未开始/初始 2进行中 1已完成 -1已取消  */
    private String dataStatus;

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataLabel() {
        return dataLabel;
    }

    public void setDataLabel(String dataLabel) {
        this.dataLabel = dataLabel;
    }

    public String getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(String imageInfo) {
        this.imageInfo = imageInfo;
    }

    public String getDataShowUrl() {
        return dataShowUrl;
    }

    public void setDataShowUrl(String dataShowUrl) {
        this.dataShowUrl = dataShowUrl;
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

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getDataNum() {
        return dataNum;
    }

    public void setDataNum(int dataNum) {
        this.dataNum = dataNum;
    }

    public int getDataMax() {
        return dataMax;
    }

    public void setDataMax(int dataMax) {
        this.dataMax = dataMax;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }
}
