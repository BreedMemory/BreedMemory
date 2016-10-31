/**
 * 项目名称：手机在线 <br/>
 * 文件名称: AddBabyHealthData.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/27.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: AddBabyHealthData <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/27 <br/>
 * @author 谌珂 <br/>
 */
public class AddBabyHealthData extends BabyHealthData {

    /**
     * @param statTime    日期
     * @param statValue03 身高
     * @param statValue01 体重
     * @param statValue35 病名
     * @param statValue36 开始时间
     * @param statValue37 持续天数
     */
    public AddBabyHealthData(int childId, String statTime, String statValue03, String statValue01, String statValue35, String statValue36, String statValue37) {
        super(childId, statTime, statValue03, statValue01, statValue35, statValue36, statValue37);
    }

    @Override
    protected OperateType getOperateType() {
        return OperateType.CREATE;
    }
}
