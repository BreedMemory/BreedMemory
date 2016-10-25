package com.yijiehl.club.android.network.response.innerentity;

import com.yijiehl.club.android.network.response.base.RespBaseSearchResult;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: Collection <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/24 <br/>
 *
 * @author 张志新 <br/>
 */
public class Collection extends RespBaseSearchResult {

    /**
     * 数据编码,该值可用于修改和查看详情
     */
    private String dataCode;
    /**
     * 数据类型,图片、文章、问答等等
     */
    private String dataType;
    /**
     * 数据标签
     */
    private String dataLable;
    /**
     * 摘要/概述信息,可为空，文章/活动摘要/概述等
     */
    private String dataInfo;
    /**
     * 图片URL,比如图片URL，只有后缀路径，值形如
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
    private int viewNum;
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

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataLable() {
        return dataLable;
    }

    public void setDataLable(String dataLable) {
        this.dataLable = dataLable;
    }

    public String getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
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

    public int getViewNum() {
        return viewNum;
    }

    public void setViewNum(int viewNum) {
        this.viewNum = viewNum;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
}
