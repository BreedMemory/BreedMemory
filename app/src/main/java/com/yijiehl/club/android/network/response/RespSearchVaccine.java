package com.yijiehl.club.android.network.response;

import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.SignIn;
import com.yijiehl.club.android.network.response.innerentity.Vaccine;

import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: RespSearchVaccine <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/19 <br/>
 *
 * @author 张志新 <br/>
 */
public class RespSearchVaccine extends BaseResponse {
    private List<Vaccine> resultList;

    public List<Vaccine> getResultList() {
        return resultList;
    }

    public void setResultList(List<Vaccine> resultList) {
        this.resultList = resultList;
    }

}
