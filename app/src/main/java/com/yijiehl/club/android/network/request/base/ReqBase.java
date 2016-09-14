/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ReqBase.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/13.  <br/>
 */
package com.yijiehl.club.android.network.request.base;

import android.content.Context;

import com.uuzz.android.util.ContextUtils;
import com.yijiehl.club.android.R;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqBase <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/13 <br/>
 * @author 谌珂 <br/>
 */
public abstract class ReqBase extends BmRequest {

    public ReqBase(Context context) {
        this.ucid = ContextUtils.getSharedString(context, R.string.shared_preference_ucid);
        this.secode = ContextUtils.getSharedString(context, R.string.shared_preference_secode);
    }

    /** 用户客户端标识 */
    private String ucid;
    /** 会话唯一编码 */
    private String secode;

    public String getUcid() {
        return ucid;
    }

    public void setUcid(String ucid) {
        this.ucid = ucid;
    }

    public String getSecode() {
        return secode;
    }

    public void setSecode(String secode) {
        this.secode = secode;
    }
}
