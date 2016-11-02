/**
 * 项目名称：手机在线 <br/>
 * 文件名称: AddRelationAccount.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/24.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: AddRelationAccount <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/24 <br/>
 * @author 谌珂 <br/>
 */
public class AddRelationAccount extends BaseDataEntity {

    public AddRelationAccount(String dataName, String mobileNum, String relationCode) {
        this.dataName = dataName;
        this.mobileNum = mobileNum;
        this.relationCode = relationCode;
    }

    private String dataName;
    private String mobileNum;
    private String relationCode;

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

    @Override
    protected String getDataModle() {
        return "acct_contact_my";
    }

    @Override
    protected OperateType getOperateType() {
        return OperateType.CREATE;
    }
}
