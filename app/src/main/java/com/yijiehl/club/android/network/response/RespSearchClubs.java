/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: RespSearchClubs.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/12.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.response;

import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.ClubInfo;

import java.util.List;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: RespSearchClubs <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/12 <br/>
 * @author 谌珂 <br/>
 */
public class RespSearchClubs extends BaseResponse {
    private List<ClubInfo> resultList;

    public List<ClubInfo> getResultList() {
        return resultList;
    }

    public void setResultList(List<ClubInfo> resultList) {
        this.resultList = resultList;
    }
}
