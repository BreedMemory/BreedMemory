package com.yijiehl.club.android.network.response.innerentity;

import com.yijiehl.club.android.network.response.base.RespBaseSearchResult;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: SignIn <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/19 <br/>
 * @author 张志新 <br/>
 */
public class SignIn extends RespBaseSearchResult{

    /**创建时间,长整型时间，需要自行转换成需要的时间格式*/
    private long createTime;
    /**积分值，可为空*/
    private long pointNum;

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getPointNum() {
        return pointNum;
    }

    public void setPointNum(long pointNum) {
        this.pointNum = pointNum;
    }
}
