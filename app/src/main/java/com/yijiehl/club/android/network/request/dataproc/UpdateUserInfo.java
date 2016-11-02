/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: UpdateUserInfo.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.network.request.dataproc;

import com.yijiehl.club.android.network.response.innerentity.UserInfo;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: UpdateUserInfo <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/14 <br/>
 * @author 谌珂 <br/>
 */
public class UpdateUserInfo extends BaseDataEntity {
    public UpdateUserInfo(String dataName, String genderCode, String mobileNum, String orgId, String birthdate) {
        this.dataName = dataName;
        this.genderCode = genderCode;
        this.mobileNum = mobileNum;
        this.orgId = orgId;
        this.birthdate = birthdate;
    }

    public UpdateUserInfo(UserInfo userInfo) {
        this.dataName = userInfo.getAcctName();
        this.genderCode = userInfo.getGenderCode();
        this.mobileNum = userInfo.getMobileNum();
        this.orgId = userInfo.getOrgId();
        this.birthdate = userInfo.getBirthday();
    }

    /** 姓名等 */
    private String dataName;
    /** 昵称、短名称等 */
    private String shortName;
    /** 3个取值为m、f、u 分别表示 male、 female、unknow，即男、女、未知 */
    private String genderCode;
    /** 个性签名 */
    private String signature;
    /** Email地址 */
    private String emailAddr;
    /** 手机 */
    private String mobileNum;
    /** 比如办公室电话、其它手机号码等 */
    private String contactInfo;
    /** 可为空，值为空或为1表示手机号优先 */
    private String mobilePrior;
    /** 比如部门、会所、俱乐部等组织标识 */
    private String orgId;
    /** 数据格式采用标准的日期串，母婴行业一般填写的是小孩出生日期或预产期，形如 2008-08-08，对应的格式串为 yyyy-MM-dd */
    private String birthdate;
    /** 地址信息 */
    private String areaInfo;
    /** 录入时用户/客户能进行自我描述或需求描述，一般500个字以内 */
    private String dataDesc;

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
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

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getDataDesc() {
        return dataDesc;
    }

    public void setDataDesc(String dataDesc) {
        this.dataDesc = dataDesc;
    }

    public String getAreaInfo() {
        return areaInfo;
    }

    public void setAreaInfo(String areaInfo) {
        this.areaInfo = areaInfo;
    }

    @Override
    protected String getDataModle() {
        return "acct_main";
    }

    @Override
    protected OperateType getOperateType() {
        return OperateType.UPDATE;
    }
}
