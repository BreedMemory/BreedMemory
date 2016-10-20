package com.yijiehl.club.android.network.response;

import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.Answer;
import com.yijiehl.club.android.network.response.innerentity.SignIn;

import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: RespSearchSignIn <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/19 <br/>
 *
 * @author 张志新 <br/>
 */
public class RespSearchSignIn extends BaseResponse{
    private List<SignIn> resultList;

    public List<SignIn> getResultList() {
        return resultList;
    }

    public void setResultList(List<SignIn> resultList) {
        this.resultList = resultList;
    }
}
