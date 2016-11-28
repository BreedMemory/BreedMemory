package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: UpdateRelationAccount <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/24 <br/>
 * @author 谌珂 <br/>
 */
public class UpdateRelationAccount extends BaseDataEntity  {


    public UpdateRelationAccount(String dataName, String mobileNum, String relationCode, String hldataMainAuth, String hldataChildAuth, String questionAuth, String photoAuth, String dataCode) {
        this.dataName = dataName;
        this.mobileNum = mobileNum;
        this.relationCode = relationCode;
        this.hldataMainAuth = hldataMainAuth;
        this.hldataChildAuth = hldataChildAuth;
        this.questionAuth = questionAuth;
        this.photoAuth = photoAuth;
        this.dataCode = dataCode;
    }

    private String dataName;
    private String mobileNum;
    private String relationCode;
    private String hldataMainAuth;
    private String hldataChildAuth;
    private String questionAuth;
    private String photoAuth;
    private String dataCode;

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getMobileNum() {
        return mobileNum;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getRelationCode() {
        return relationCode;
    }

    public void setRelationCode(String relationCode) {
        this.relationCode = relationCode;
    }

    public String getHldataMainAuth() {
        return hldataMainAuth;
    }

    public void setHldataMainAuth(String hldataMainAuth) {
        this.hldataMainAuth = hldataMainAuth;
    }

    public String getHldataChildAuth() {
        return hldataChildAuth;
    }

    public void setHldataChildAuth(String hldataChildAuth) {
        this.hldataChildAuth = hldataChildAuth;
    }

    public String getQuestionAuth() {
        return questionAuth;
    }

    public void setQuestionAuth(String questionAuth) {
        this.questionAuth = questionAuth;
    }

    public String getPhotoAuth() {
        return photoAuth;
    }

    public void setPhotoAuth(String photoAuth) {
        this.photoAuth = photoAuth;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    @Override
    protected String getDataModle() {
        return "acct_contact_my";
    }

    @Override
    protected OperateType getOperateType() {
        return OperateType.UPDATE;
    }
}
