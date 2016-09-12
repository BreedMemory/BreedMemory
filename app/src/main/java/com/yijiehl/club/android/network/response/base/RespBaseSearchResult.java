/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: RespBaseSearchResult.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/12.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.response.base;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: RespBaseSearchResult <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/12 <br/>
 * @author 谌珂 <br/>
 */
public class RespBaseSearchResult {
    /** 数据分类，可为空，分类编码 */
    protected String dataClfy;
    /** 数据标识，可对应orgId等属性 */
    protected String dataId;
    /** 数据名称 */
    protected String dataName;

    public String getDataClfy() {
        return dataClfy;
    }

    public void setDataClfy(String dataClfy) {
        this.dataClfy = dataClfy;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }
}
