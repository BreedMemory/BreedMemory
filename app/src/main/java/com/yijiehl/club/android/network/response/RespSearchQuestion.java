package com.yijiehl.club.android.network.response;

import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.Answer;
import com.yijiehl.club.android.network.response.innerentity.Article;

import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: RespSearchQuestion <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/14 <br/>
 * @author 张志新 <br/>
 */
public class RespSearchQuestion extends BaseResponse {

    private List<Answer> resultList;

    public List<Answer> getResultList() {
        return resultList;
    }

    public void setResultList(List<Answer> resultList) {
        this.resultList = resultList;
    }
}
