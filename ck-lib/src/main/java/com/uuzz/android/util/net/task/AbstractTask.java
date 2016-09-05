/**
 * 项目名称：工具库
 * 文件名称: AbstractTask.java
 * Created by 谌珂 on 2016/1/3.
 */
package com.uuzz.android.util.net.task;

import android.content.Context;
import android.os.AsyncTask;

import com.uuzz.android.R;
import com.uuzz.android.util.Utils;
import com.uuzz.android.util.log.Logger;
import com.uuzz.android.util.net.HttpFactory;
import com.uuzz.android.util.net.httpcore.BaseHttp;
import com.uuzz.android.util.net.httpcore.RequestParams;
import com.uuzz.android.util.net.request.IRequest;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.response.base.ResponseContent;

/**
 * 项目名称：工具库<br/>
 * 类  名: AbstractTask<br/>
 * 类描述: 请求任务的基类<br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public abstract class AbstractTask implements BaseHttp.HttpRequestListener<String> {

    protected Logger logger = new Logger(this.getClass());
    protected Context mContext;
    /** 是否自动关闭加载对话框 */
    protected boolean isCloseLoadCom;
    /** 请求实体对象 */
    protected IRequest mRequest;
    /** http请求参数列表 */
    protected RequestParams mRequestParams;
    /** 发起http请求的对象 */
    protected BaseHttp mHttp = getHttp();
    /** 分析http请求后的回调 */
    protected HttpCallBack mListener;
    protected String mPath;

    @Override
    public void doInMainThread(ResponseContent<String> result) {
        if(mListener == null){
            logger.e("http callback is null!");
            closeLoadingCom();
            return;
        }
        if(result == null || result.getmResultCode() != 200){   //说明请求的内容还是有问题
            mListener.onFailed(mContext.getString(R.string.net_error));
            closeLoadingCom();
            return;
        }

//            AgentResponse lAgentResponse = JSON.parseObject(result.getEntity(), AgentResponse.class);
//            if(!lAgentResponse.isSessionValid(mContext)) {      //如果服务器返回码为10001代表session失效，跳转到登录页面
//                closeLoadingCom();
//                return;
//            }
//
//
//            if(!lAgentResponse.isSuccess()){           //如果服务器的返回码不等于0代表业务传输层有问题
//                logger.e(mPath + " return the result code:" + lAgentResponse.getResult_code() + " from server:" + lAgentResponse.getResult_desc());
//                mListener.onFailed(lAgentResponse.getResult_desc());
//                closeLoadingCom();
//                return;
//            }
//
//            String lBody = lAgentResponse.getBody();
//            if(TextUtils.equals("BQGHoM3lqYcsurCRq3PlUw==", lBody)) {
//                logger.e("Response body is empty!");
//                mListener.onFailed(mContext.getString(R.string.net_error));
//                closeLoadingCom();
//                return;
//            }
//            mListener.onSuccess(createHttpResponse(lAgentResponse.getBody()));
//            closeLoadingCom();
    }

    @Override
    public void onCancelled() {
        if(mListener != null){
            mListener.onCancelled();
        }
        closeLoadingCom();
    }

    @Override
    public void updateProgress(int progress) {
        if(mListener != null){
            mListener.updateProgress(progress);
        }
        closeLoadingCom();
    }

    /**
     * 描 述：关闭加载对话框<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/11 注释 <br/>
     */
    private void closeLoadingCom() {
        if(mContext != null && isCloseLoadCom) {
//            LoadingCOM.getInstance(mContext).dismissLoading();
        }
    }

    public AbstractTask(IRequest lRequest, Context pContext, boolean isCloseLoadCom) {
        this.mContext = pContext;
        this.isCloseLoadCom = isCloseLoadCom;
        this.mRequest = getRequest(lRequest);
        this.mRequestParams = createRequestParam(mRequest, isSingleHttp());
        this.mPath = lRequest.getPath();
    }

    /**
     * 描 述：获取发起http请求的对象,默认返回HttpFactory.TEXT_DATA标记的对象<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/3 注释 <br/>
     */
    public BaseHttp getHttp(){
        return HttpFactory.getHttpHelper(HttpFactory.TEXT_DATA);
    }

    /**
     * 描 述：获取http连接参数<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/3 <br/>
     * @param pRequest http请求实体
     * @param isSingleHttp 是否使用http单例
     * @return http连接参数
     */
    protected abstract RequestParams createRequestParam(IRequest pRequest, boolean isSingleHttp);

    /**
     * 描 述：获取IRequest实例<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/4 注释 <br/>
     * @param lRequest 业务请求消息对象
     * @return IRequest实例
     */
    protected abstract IRequest getRequest(IRequest lRequest);

    /**
     * 描 述：创建http请求后返回的业务消息对象<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/5 注释 <br/>
     * @param data 加密的报文体
     * @return 返回的业务消息对象
     */
    protected abstract AbstractResponse createHttpResponse(String data);

    /**
     * 描 述：是否使用单例httpclient进行请求<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/4 注释 <br/>
     */
    protected abstract boolean isSingleHttp();

    /**
     * 描 述：获取响应体的class<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/4 注释 <br/>
     */
    public Class getResponseClass() {
        return mRequest.getResponseClass();
    }

    /**
     * 同步请求
     */
    public ResponseContent execute() {
        return mHttp.httpRequest(mRequestParams);
    }

    /**
     * 异步请求
     * @param lCallBack 回调方法
     */
    public AsyncTask execute(HttpCallBack lCallBack){
        if(!Utils.isNetworkConnected(mContext)) {         //判断网络是否可用
            closeLoadingCom();
            lCallBack.onFailed(mContext.getString(R.string.net_error));
            return null;
        }
        mListener = lCallBack;
        return mHttp.httpRequest(mRequestParams, this);
    }

    /**
     * 描 述：对http请求返回结果进行简单分析后的回调<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/3 注释 <br/>
     */
    public interface HttpCallBack {

        /**
         * 描 述：当http层完全排除异常后进入此回调<br/>
         * 作者：谌珂<br/>
         * 历 史: (版本) 谌珂 2016/1/3 注释 <br/>
         * @param pResponse 返回的响应消息对象
         */
        void onSuccess(AbstractResponse pResponse);

        /**
         * 描 述：当http层出现异常后的回调<br/>
         * 作者：谌珂<br/>
         * 历 史: (版本) 谌珂 2016/1/3 注释 <br/>
         */
        void onFailed(String msg);

        /**
         * 描 述：当http异步请求任务被取消的回调（一般情况下不需要实现）<br/>
         * 作者：谌珂<br/>
         * 历 史: (版本) 谌珂 2016/1/3 注释 <br/>
         */
        void onCancelled();

        /**
         * 描 述：异步任务中更新进度条的回调（一般情况下不需要实现）<br/>
         * 作者：谌珂<br/>
         * 历 史: (版本) 谌珂 2016/1/3 注释 <br/>
         */
        void updateProgress(int progress);
    }
}
