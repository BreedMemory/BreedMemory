/**
 * 项目名称：手机大管家
 * 文件名称: AddMotherHealthData.java
 * Created by 谌珂 on 2016/10/26.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: AddMotherHealthData<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class AddMotherHealthData extends MotherHealthData {
    public AddMotherHealthData(String statTime, String statValue01, String statValue10, String statValue11, String statValue12) {
        super(statTime, statValue01, statValue10, statValue11, statValue12);
    }

    @Override
    protected OperateType getOperateType() {
        return OperateType.CREATE;
    }
}
