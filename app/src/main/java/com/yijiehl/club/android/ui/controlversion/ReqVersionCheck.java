/**
 * 项目名称：手机在线 <br/>
 * 文件名称: ReqVersionCheck.java <br/>
 * <p>
 * Created by 谌珂 on 2016/11/4.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.controlversion;

import com.uuzz.android.util.Common;
import com.uuzz.android.util.net.request.IRequest;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractTask;
import com.yijiehl.club.android.network.response.RespCheckVersion;
import com.yijiehl.club.android.network.task.DefaultTask;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: ReqVersionCheck <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/11/4 <br/>
 * @author 谌珂 <br/>
 */
public class ReqVersionCheck implements IRequest {

    private String ct = "android";
    private String cd = com.yijiehl.club.android.common.Common.CLIENT_NUMBER;
    private String vid;

    public ReqVersionCheck() {
        StringBuilder sb = new StringBuilder();
        String[] split = Common.APP_VERSION.split("\\.");
        sb.append(split[0]);
        if(split[1].length() < 2) {
            sb.append("0");
        }
        sb.append(split[1]);
        if(split[2].length() < 2) {
            sb.append("00");
            sb.append(split[2]);
        } else if (split[2].length() < 3) {
            sb.append("0");
            sb.append(split[2]);
        } else {
            sb.append(split[2]);
        }
        vid = sb.toString();
    }

    @Override
    public String getPath() {
        return "versionchk.htm";
    }

    @Override
    public Class<? extends AbstractTask> getTaskClass() {
        return DefaultTask.class;
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return RespCheckVersion.class;
    }

    @Override
    public boolean selfCheck() {
        return true;
    }

    @Override
    public boolean isHttps() {
        return false;
    }

    @Override
    public boolean isGet() {
        return true;
    }
}
