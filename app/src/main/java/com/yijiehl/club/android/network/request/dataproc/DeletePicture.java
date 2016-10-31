/**
 * 项目名称：手机大管家
 * 文件名称: DeletePicture.java
 * Created by 谌珂 on 2016/10/31.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: DeletePicture<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class DeletePicture extends BaseDataEntity {
    public DeletePicture(String dataCode) {
        this.dataCode = dataCode;
    }

    private String dataCode;

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    @Override
    protected String getDataModle() {
        return "crm_photo_detail";
    }

    @Override
    protected OperateType getOperateType() {
        return OperateType.DELETE;
    }
}
