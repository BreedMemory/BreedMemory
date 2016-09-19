/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ReqUpload.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/19.  <br/>
 */
package com.yijiehl.club.android.network.request.base;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractTask;
import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.task.UploadTask;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqUpload <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/19 <br/>
 * @author 谌珂 <br/>
 */
public class ReqUpload extends BmRequest {
    @Override
    public String getPath() {
        return "resfileup.htm";
    }

    @Override
    public Class<? extends AbstractTask> getTaskClass() {
        return UploadTask.class;
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return BaseResponse.class;
    }
}
