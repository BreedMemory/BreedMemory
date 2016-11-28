package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: DeleteRelation <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/24 <br/>
 *
 * @author 张志新 <br/>
 */
public class DeleteRelation extends BaseDataEntity {

    private String dataName;
    private String mobileNum;
    private String relationCode;
    private String dataCode;


    public DeleteRelation(String dataName, String mobileNum, String relationCode, String dataCode) {
        this.dataName = dataName;
        this.mobileNum = mobileNum;
        this.relationCode = relationCode;
        this.dataCode = dataCode;
    }


    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

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
        return OperateType.DELETE;
    }
}
