/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: RespLogin.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.network.response;

import android.text.TextUtils;

import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: RespLogin <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/7 <br/>
 * @author 谌珂 <br/>
 */
public class RespSensitize extends RespLogin {
    @Override
    public boolean isNeedLogin() {
        return returnMsg != null && (TextUtils.equals(returnMsg.getResultCode(), "offline") || TextUtils.equals(returnMsg.getResultCode(), "offline_expire"));
    }
}
