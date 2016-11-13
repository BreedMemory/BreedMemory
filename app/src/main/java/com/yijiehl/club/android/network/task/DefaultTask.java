/**
 * 项目名称：工具库 <br/>
 * 文件名称: DefaultTask.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/1/11.  <br/>
 */
package com.yijiehl.club.android.network.task;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.net.httpcore.RequestParams;
import com.uuzz.android.util.net.request.IRequest;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.response.base.ResponseContent;
import com.uuzz.android.util.net.task.AbstractTask;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.DefaultRequestParam;
import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.svc.ActivitySvc;


/**
 * 项目名称：工具库 <br/>
 * 类  名: DefaultTask <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/1/11 <br/>
 * @author 谌珂 <br/>
 */
public class DefaultTask extends AbstractTask {

    public DefaultTask(IRequest lRequest, Context pContext, boolean isCloseLoadCom) {
        super(lRequest, pContext, isCloseLoadCom);
    }

    @Override
    protected RequestParams createRequestParam(IRequest pRequest, boolean isSingleHttp) {
        logger.d("request entity:" + JSON.toJSONString(pRequest));
        return DefaultRequestParam.getRequestParams(pRequest, isSingleHttp);
    }

    @Override
    public void doInMainThread(ResponseContent<String> result) {
        if(mListener == null){
            logger.e("http callback is null!");
            closeLoadingCom();
            return;
        }
        if(result == null || result.getmResultCode() != 200){   //说明请求的内容还是有问题
            mListener.onFailed(mContext.getString(com.uuzz.android.R.string.net_error));
            closeLoadingCom();
            return;
        }
        logger.d("response entity:" + result.getEntity());
        BaseResponse responseData = (BaseResponse)createHttpResponse(result.getEntity());
        if(responseData == null) {
            logger.e("http response entity is null");
            if(mListener != null) {
                mListener.onFailed(mContext.getString(R.string.net_error));
            }
            closeLoadingCom();
            return;
        }
        if(responseData.getReturnMsg() != null &&!responseData.getReturnMsg().isSuccess()) {
            logger.e(responseData.getReturnMsg().getEnMessage());
            closeLoadingCom();
            if(responseData.isNeedLogin()) {
                ActivitySvc.startLoginActivity(mContext);
                return;
            }
            if(mListener != null) {
                mListener.onFailed(responseData.getReturnMsg().getMessage());
            }
            return;
        }
        //缓存接口数据
        CacheDataDAO.getInstance(mContext).insertCacheDateAsync(mContext, String.valueOf(mRequest.hashCode()), result.getEntity());

        if(mListener != null) {
            mListener.onSuccess(responseData);
        }
        closeLoadingCom();
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
            logger.e("create response failed! response is :" + data, e);
            return null;
        }

    }

    @Override
    protected boolean isSingleHttp() {
        return false;
    }
}
