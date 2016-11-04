/**
 * 项目名称：手机在线 <br/>
 * 文件名称: RespCheckVersion.java <br/>
 * <p>
 * Created by 谌珂 on 2016/11/4.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.response;

import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.ClientVersionInfo;

import java.util.List;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: RespCheckVersion <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/11/4 <br/>
 * @author 谌珂 <br/>
 */
public class RespCheckVersion extends BaseResponse {
    List<ClientVersionInfo> resultList;

    public List<ClientVersionInfo> getResultList() {
        return resultList;
    }

    public void setResultList(List<ClientVersionInfo> resultList) {
        this.resultList = resultList;
    }
}
