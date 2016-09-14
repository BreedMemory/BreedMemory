/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: RespSearchActivitys.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.network.response;

import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.ActivityInfo;

import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: RespSearchActivitys <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/14 <br/>
 * @author 谌珂 <br/>
 */
public class RespSearchActivitys extends BaseResponse {
    private List<ActivityInfo> resultList;

    public List<ActivityInfo> getResultList() {
        return resultList;
    }

    public void setResultList(List<ActivityInfo> resultList) {
        this.resultList = resultList;
    }
}
