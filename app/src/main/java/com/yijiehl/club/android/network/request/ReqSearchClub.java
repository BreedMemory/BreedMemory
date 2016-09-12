/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: ReqSearchClub.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/12.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.response.RespSearchClubs;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: ReqSearchClub <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/12 <br/>
 * @author 谌珂 <br/>
 */
public class ReqSearchClub extends ReqBaseSearch {
    @Override
    protected String getBizType() {
        return "crm_org_main";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return RespSearchClubs.class;
    }
}
