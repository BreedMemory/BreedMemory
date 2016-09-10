/**
 * 项目名称：孕育迹忆
 * 文件名称: BmRequest.java
 * Created by 谌珂 on 2016/9/3.
 */
package com.yijiehl.club.android.network.request;

import com.uuzz.android.util.net.request.IRequest;
import com.yijiehl.club.android.common.Common;

/**
 * 项目名称：孕育迹忆<br/>
 * 类  名: BmRequest<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public abstract class BmRequest implements IRequest {

    /** 客户端编号 */
    protected String clientNum = Common.CLIENT_NUMBER;

    public String getClientNum() {
        return clientNum;
    }

    public void setClientNum(String clientNum) {
        this.clientNum = clientNum;
    }

    @Override
    public boolean selfCheck() {
        return true;
    }

    /**
     * 描 述：描述是否是https链接<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/3 <br/>
     */
    public boolean isHttps() {
        return false;
    }
}
