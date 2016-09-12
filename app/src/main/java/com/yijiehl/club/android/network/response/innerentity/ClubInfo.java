/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: ClubInfo.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/12.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.response.innerentity;

import com.yijiehl.club.android.network.response.base.RespBaseSearchResult;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: ClubInfo <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/12 <br/>
 * @author 谌珂 <br/>
 */
public class ClubInfo extends RespBaseSearchResult {
    /** 数据标签 */
    private String dataLable;
    /** 评价信息,第三方认证信息，评价信息evaluation等等 */
    private String evaluaInfo;
    /** 图片URL，可为空，一般用于宣传类图片的展 */
    private String imageInfo;
    /** 正常图标，可能包含文字 */
    private String iconInfo1;
    /** 小图标URL地址 */
    private String iconInfo2;
    /** 数据展现URL */
    private String dataShowUrl;
    /** 网站URL地址，网站URL地址 */
    private String siteInfo;
    /** 联系人 */
    private String contactMan;
    /** 联系电话 */
    private String contactPhone;
    /** 规模，比如50人以下, 50到300人 */
    private String sizeInfo;
    /** 地址信息 */
    private String addrInfo;
    /** 数据描述，介绍或描述 */
    private String dataDesc;

    public String getDataLable() {
        return dataLable;
    }

    public void setDataLable(String dataLable) {
        this.dataLable = dataLable;
    }

    public String getEvaluaInfo() {
        return evaluaInfo;
    }

    public void setEvaluaInfo(String evaluaInfo) {
        this.evaluaInfo = evaluaInfo;
    }

    public String getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(String imageInfo) {
        this.imageInfo = imageInfo;
    }

    public String getIconInfo1() {
        return iconInfo1;
    }

    public void setIconInfo1(String iconInfo1) {
        this.iconInfo1 = iconInfo1;
    }

    public String getIconInfo2() {
        return iconInfo2;
    }

    public void setIconInfo2(String iconInfo2) {
        this.iconInfo2 = iconInfo2;
    }

    public String getDataShowUrl() {
        return dataShowUrl;
    }

    public void setDataShowUrl(String dataShowUrl) {
        this.dataShowUrl = dataShowUrl;
    }

    public String getSiteInfo() {
        return siteInfo;
    }

    public void setSiteInfo(String siteInfo) {
        this.siteInfo = siteInfo;
    }

    public String getContactMan() {
        return contactMan;
    }

    public void setContactMan(String contactMan) {
        this.contactMan = contactMan;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getSizeInfo() {
        return sizeInfo;
    }

    public void setSizeInfo(String sizeInfo) {
        this.sizeInfo = sizeInfo;
    }

    public String getAddrInfo() {
        return addrInfo;
    }

    public void setAddrInfo(String addrInfo) {
        this.addrInfo = addrInfo;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }
}
