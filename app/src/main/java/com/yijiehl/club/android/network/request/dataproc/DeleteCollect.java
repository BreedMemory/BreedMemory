package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：孕育记忆<br/>
 * 类  名: DeleteCollect<br/>
 * 类描述: <br/>
 * @author 张志新 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class DeleteCollect extends BaseDataEntity{

    private String dataCode;


    public DeleteCollect(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    @Override
    protected String getDataModle() {
        return "ext_favorite_item_my";
    }

    @Override
    protected OperateType getOperateType() {
        return OperateType.DELETE;
    }
}
