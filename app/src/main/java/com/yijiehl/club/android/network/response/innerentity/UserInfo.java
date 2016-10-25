/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: UserInfo.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.network.response.innerentity;

import android.content.Context;
import android.text.TextUtils;

import com.yijiehl.club.android.common.Common;
import com.yijiehl.club.android.network.response.RespLogin;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
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
    /** 昵称 */
    private String shortName;
    /** 性别，可为空，m表示男，f表示女 */
    private String genderCode;
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
    /** 签到信息, 如果有值就表示需要显示签到的界面，值为空就表示不需要签到 */
    private String signinInfo;
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
    /** 预产期或出生日期 */
    private String birthday;
    /** 首页信息 */
    private String mainDataList;
    /**地址信息,通讯地址等*/
    private String areaInfo;
    /**会所电话*/
    private String custServicePhone;

    private RespLogin.AccountStatus status;

    private List<MainDataEntity> childrenInfo;

    public String getAreaInfo() {
        return areaInfo;
    }

    public void setAreaInfo(String areaInfo) {
        this.areaInfo = areaInfo;
    }

    public enum MainDataType {
        HEALTHINFO(0, "healthInfo"),
        RECOMMACTIVITY(1, "recommActivity1"),
        RECOMMQUESTION(2, "recommQuestion1"),
        RECOMMGROWUP(3, "recommGrowup1"),
        ACCTAMOUNT(4, "acctAmount"),
        CUSTSERVICEPHONE(5, "custServicePhone"),
        IMAGECOVER(6, "imageCover"),
        ACTIVITYCOVER(7, "activityCover"),
        ALBUMCOVER(8, "albumCover"),
        ALBUMITEM1(9, "albumItem1"),
        ALBUMITEM2(10, "albumItem2"),
        ALBUMITEM3(11, "albumItem3"),
        CHILDINFO(12, "childInfo");
        private String name;
        int value;

        MainDataType(int value, String name) {
            this.value = value;
            this.name = name;
        }

        public int getValue() {
            return this.value;
        }

        public String getName() {
            return this.name;
        }

        public static MainDataType setValue(String name) {
            MainDataType[] arr$ = values();
            for (MainDataType c : arr$) {
                if (TextUtils.equals(c.getName(), name)) {
                    return c;
                }
            }
            return HEALTHINFO;
        }
    }

    public static class MainDataEntity {
        public MainDataEntity() {
        }

        public MainDataEntity(String desc, String name, String value) {
            this.desc = desc;
            this.name = name;
            this.value = value;
        }

        private String type;
        private String desc;
        private String name;
        private String value;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public String getMainDataList() {
        return mainDataList;
    }

    public void setMainDataList(String mainDataList) {
        this.mainDataList = mainDataList;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getGenderCode() {
        return genderCode;
    }

    public boolean isMale() {
        return TextUtils.equals(genderCode, "m");
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public String getSigninInfo() {
        return signinInfo;
    }

    public void setSigninInfo(String signinInfo) {
        this.signinInfo = signinInfo;
    }

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

    public String getCustServicePhone() {
        return custServicePhone;
    }

    public void setCustServicePhone(String custServicePhone) {
        this.custServicePhone = custServicePhone;
    }

    public List<MainDataEntity> getChildrenInfo() {
        return childrenInfo;
    }

    public void setChildrenInfo(List<MainDataEntity> childrenInfo) {
        this.childrenInfo = childrenInfo;
    }

    public RespLogin.AccountStatus getStatus() {
        return status;
    }

    public void setStatus(RespLogin.AccountStatus status) {
        this.status = status;
    }

    /**
     * 描 述：返回会所链接<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/24 <br/>
     */
    public String getCustServiceUrl(Context context) {
        return "http://" +Common.SERVICE_URL + "showpgclfybiz.htm?clfy=crm_org_main&bd=showdetail&dd="+orgId;
    }

    /**
     * 描 述：返回月子餐链接<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/24 <br/>
     */
    public String getFoodUrl(Context context) {
        return "http://" +Common.SERVICE_URL + "showpgclfybiz.htm?clfy=org_month_meals&bd=showdetail&dd="+orgId;
    }
}
