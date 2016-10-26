/**
 * 项目名称：手机大管家
 * 文件名称: RespSearchExtraFile.java
 * Created by 谌珂 on 2016/10/27.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.response;

import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.ExtraFile;

import java.util.List;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: RespSearchExtraFile<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class RespSearchExtraFile extends BaseResponse {

    private List<ExtraFile> resultList;

    public List<ExtraFile> getResultList() {
        return resultList;
    }

    public void setResultList(List<ExtraFile> resultList) {
        this.resultList = resultList;
    }
}
