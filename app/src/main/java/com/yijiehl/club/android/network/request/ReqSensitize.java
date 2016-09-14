/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ReqSensitize.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/8.  <br/>
 */
package com.yijiehl.club.android.network.request;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractTask;
import com.yijiehl.club.android.network.request.base.ReqBase;
import com.yijiehl.club.android.network.response.RespLogin;
import com.yijiehl.club.android.network.task.DefaultTask;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqSensitize <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/8 <br/>
 * @author 谌珂 <br/>
 */
public class ReqSensitize extends ReqBase {

    public ReqSensitize(Context context) {
        super(context);
    }

    @Override
    public String getPath() {
        return "crmsechk.htm";
    }

    @Override
    public Class<? extends AbstractTask> getTaskClass() {
        return DefaultTask.class;
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return RespLogin.class;
    }
}
