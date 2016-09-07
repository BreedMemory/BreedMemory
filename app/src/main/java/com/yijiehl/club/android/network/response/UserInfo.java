/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: UserInfo.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.network.response;

import java.io.Serializable;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: UserInfo <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/7 <br/>
 * @author 谌珂 <br/>
 */
public class UserInfo implements Serializable {

    /** 自己的名称 */
    private String acctName;
    /** abc@sina.com */
    private String emailAddr;
    /** 手机号码 */
    private String mobileNum;
    /** 其他联系信息 */
    private String contactInfo;
    /** 可为空 0表示非手机号优先 */
    private String mobilePrior;
    /** 会所/组织ID */
    private String orgId;
    /** 部门/会所/组织名称等 */
    private String orgInfo;
    /** 用户/客户图像链接，可为空 */
    private String imageInfo;
    /** 欢迎信息，用于首页展现等，可为空 */
    private String welcomeInfo;
    /** 基本描述信息/简介信息，可为空 */
    private String baseInfo;
    /** 业务/商务模式, 如正常/普通normal，公众public(简化版)等 */
    private String bizMode;
    /** 计费/收费模式，包月，按时长，免费，试用trial等，可为空 */
    private String costMode;
    /** 产品名称,该值如果不为空，则需要在所有分享的信息中加上这个这个前缀, 如 【产品A】 */
    private String productName;
    /** 产品英文名称 */
    private String productEnName;
    /** 服务器的时区 */
    private String sysTimeZone;
    /** 平台服务器地址, 可以由企业自已设定 */
    private String platUrl;
    /** 业务平台名称 */
    private String sysName;
    /** 业务平台英文名称 */
    private String sysEnName;
    /** 正常图标URL地址，一般是应用内部图标，可能图标、文字都有，为空就没有设置图标 */
    private String iconInfo1;
    /** 小图标URL地址，如果有值尺寸可能更小，只有图标没有文字等 */
    private String iconInfo2;

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getMobilePrior() {
        return mobilePrior;
    }

    public void setMobilePrior(String mobilePrior) {
        this.mobilePrior = mobilePrior;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgInfo() {
        return orgInfo;
    }

    public void setOrgInfo(String orgInfo) {
        this.orgInfo = orgInfo;
    }

    public String getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(String imageInfo) {
        this.imageInfo = imageInfo;
    }

    public String getWelcomeInfo() {
        return welcomeInfo;
    }

    public void setWelcomeInfo(String welcomeInfo) {
        this.welcomeInfo = welcomeInfo;
    }

    public String getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(String baseInfo) {
        this.baseInfo = baseInfo;
    }

    public String getBizMode() {
        return bizMode;
    }

    public void setBizMode(String bizMode) {
        this.bizMode = bizMode;
    }

    public String getCostMode() {
        return costMode;
    }

    public void setCostMode(String costMode) {
        this.costMode = costMode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductEnName() {
        return productEnName;
    }

    public void setProductEnName(String productEnName) {
        this.productEnName = productEnName;
    }

    public String getSysTimeZone() {
        return sysTimeZone;
    }

    public void setSysTimeZone(String sysTimeZone) {
        this.sysTimeZone = sysTimeZone;
    }

    public String getPlatUrl() {
        return platUrl;
    }

    public void setPlatUrl(String platUrl) {
        this.platUrl = platUrl;
    }

    public String getSysName() {
        return sysName;
    }

    public void setSysName(String sysName) {
        this.sysName = sysName;
    }

    public String getSysEnName() {
        return sysEnName;
    }

    public void setSysEnName(String sysEnName) {
        this.sysEnName = sysEnName;
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
}
