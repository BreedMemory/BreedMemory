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
import com.yijiehl.club.android.network.response.BaseResponse;


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
        return null;
    }

    @Override
    public void doInMainThread(ResponseContent<String> result) {
        super.doInMainThread(result);
        BaseResponse responseData = (BaseResponse)createHttpResponse(result.getEntity());
        if(!responseData.getReturnMsg().isSuccess()) {
            logger.e(responseData.getReturnMsg().getEnMessage());
            if(mListener != null) {
                mListener.onFailed(responseData.getReturnMsg().getMessage());
            }
        }
        //缓存接口数据
        CacheDataDAO.getInstance(mContext).insertCacheDate(mContext, String.valueOf(mRequest.hashCode()), result.getEntity());

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
        return JSON.parseObject(data, mRequest.getResponseClass());
    }

    @Override
    protected boolean isSingleHttp() {
        return false;
    }
}
