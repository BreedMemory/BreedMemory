/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: UploadTask.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/19.  <br/>
 */
package com.yijiehl.club.android.network.task;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.util.net.HttpFactory;
import com.uuzz.android.util.net.httpcore.BaseHttp;
import com.uuzz.android.util.net.httpcore.RequestParams;
import com.uuzz.android.util.net.request.IRequest;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.DefaultRequestParam;
import com.yijiehl.club.android.network.request.upload.ReqUploadFile;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: UploadTask <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/19 <br/>
 * @author 谌珂 <br/>
 */
public class UploadTask extends DefaultTask {
    public UploadTask(IRequest lRequest, Context pContext, boolean isCloseLoadCom) {
        super(lRequest, pContext, isCloseLoadCom);
    }

    @Override
    protected RequestParams createRequestParam(IRequest pRequest, boolean isSingleHttp) {
        return DefaultRequestParam.getUploadRequestParams(mContext, (ReqUploadFile) pRequest, isSingleHttp);
    }

    @Override
    protected IRequest getRequest(IRequest lRequest) {
        return lRequest;
    }

    @Override
    protected AbstractResponse createHttpResponse(String data) {
        try {
            return JSON.parseObject(data, mRequest.getResponseClass());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected boolean isSingleHttp() {
        return false;
    }

    @Override
    public BaseHttp getHttp() {
        return HttpFactory.getHttpHelper(HttpFactory.UPLOAD);
    }
}
