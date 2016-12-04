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


    public UpdateRelationAccount(String dataName, String mobileNum, String relationCode, String accessAuth1, String accessAuth2, String accessAuth5, String accessAuth6, String dataCode) {
        this.dataName = dataName;
        this.mobileNum = mobileNum;
        this.relationCode = relationCode;
        this.accessAuth1 = accessAuth1;
        this.accessAuth2 = accessAuth2;
        this.accessAuth5 = accessAuth5;
        this.accessAuth6 = accessAuth6;
        this.dataCode = dataCode;
    }

    private String dataName;
    private String mobileNum;
    private String relationCode;
    private String accessAuth1;
    private String accessAuth2;
    private String accessAuth5;
    private String accessAuth6;
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

    public String getAccessAuth1() {
        return accessAuth1;
    }

    public void setAccessAuth1(String accessAuth1) {
        this.accessAuth1 = accessAuth1;
    }

    public String getAccessAuth2() {
        return accessAuth2;
    }

    public void setAccessAuth2(String accessAuth2) {
        this.accessAuth2 = accessAuth2;
    }

    public String getAccessAuth5() {
        return accessAuth5;
    }

    public void setAccessAuth5(String accessAuth5) {
        this.accessAuth5 = accessAuth5;
    }

    public String getAccessAuth6() {
        return accessAuth6;
    }

    public void setAccessAuth6(String accessAuth6) {
        this.accessAuth6 = accessAuth6;
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
