/**
 * 项目名称：手机在线 <br/>
 * 文件名称: ReqSearchMotherDataList.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/24.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request.search;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.response.RespSearchHealthData;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: ReqSearchMotherDataList <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/24 <br/>
 * @author 谌珂 <br/>
 */
public class ReqSearchBabyData extends ReqBaseSearch {
    public ReqSearchBabyData(Context context, String id) {
        super(context);
        this.dataId = id;
        this.dataClfy = "latest";
    }

    public ReqSearchBabyData(Context context, String time, String id) {
        super(context);
        this.startTime = time;
        this.dataId = id;
    }

    @Override
    public String getBizType() {
        return "crm_hldata_item_child";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return RespSearchHealthData.class;
    }
}
