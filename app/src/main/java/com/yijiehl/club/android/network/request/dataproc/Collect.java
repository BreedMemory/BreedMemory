/**
 * 项目名称：手机在线 <br/>
 * 文件名称: Collect.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/20.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: Collect <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/20 <br/>
 * @author 谌珂 <br/>
 */
public abstract class Collect extends BaseDataEntity {

    /** 必填, 相片照片photo 一般图片image 活动activity 知识knowledge 问答question 通用文章article  */
    protected String dataType = getDataType();
    /** 比如文章的名称  */
    protected String dataName;
    /** 可为空，多个标签以空格分开  */
    protected String dataLabel;
    /** 一般为缩略图url */
    protected String imageInfo;
    /** 一般不能为空 */
    protected String dataShowUrl;
    /** 可为空，文章/活动摘要/概述等 */
    protected String dataInfo;

    @Override
    protected String getDataModle() {
        return "favorite_item_my";
    }

    protected abstract String getDataType();

    @Override
    protected OperateType getOperateType() {
        return OperateType.CANCEL;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
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

    public String getDataInfo() {
        return dataInfo;
    }

    public void setDataInfo(String dataInfo) {
        this.dataInfo = dataInfo;
    }
}
