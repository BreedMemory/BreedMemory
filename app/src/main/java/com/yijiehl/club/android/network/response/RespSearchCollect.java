package com.yijiehl.club.android.network.response;

import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.ActivityInfo;
import com.yijiehl.club.android.network.response.innerentity.Collection;

import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: RespSearchCollect <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/24 <br/>
 *
 * @author 张志新 <br/>
 */
public class RespSearchCollect extends BaseResponse {
    private List<Collection> resultList;

    public List<Collection> getResultList() {
        return resultList;
    }

    public void setResultList(List<Collection> resultList) {
        this.resultList = resultList;
    }

}
