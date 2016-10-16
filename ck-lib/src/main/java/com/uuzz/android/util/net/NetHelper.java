package com.uuzz.android.util.net;

import android.content.Context;
import android.os.AsyncTask;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.R;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.LoadingCOM;
import com.uuzz.android.util.MD5;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.log.Logger;
import com.uuzz.android.util.net.exception.TaskFailException;
import com.uuzz.android.util.net.httpcore.RequestParams;
import com.uuzz.android.util.net.request.IRequest;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.response.base.ResponseContent;
import com.uuzz.android.util.net.task.AbstractTask;

import java.lang.reflect.Constructor;

public class NetHelper {

    private static final Logger logger = new Logger("NetHelper");

    /**
     * 描 述：异步任务请求网络并返回子线程任务对象<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/4 注释 <br/>
     * @param context context
     * @param pRequest 业务请求消息对象
     * @param pCallBack 回调方法
     * @param isShowLoading 是否弹出Loading
     * @return 线程任务，可用来取消任务进度。如果创建网络任务失败直接返回null
     */
    public static AsyncTask getDataFromNet(Context context, IRequest pRequest, AbstractTask.HttpCallBack pCallBack, boolean isShowLoading) {
        if(isShowLoading) {
            LoadingCOM.getInstance(context).showLoading(true);
        }
        CacheDataDAO.getInstance(context).getCacheDataAsync(ContextUtils.getSharedString(context, R.string.shared_preference_user_id), createObjectName(pRequest));
        Class<? extends AbstractTask> lTaskClass = pRequest.getTaskClass();
        try {
            Constructor<? extends AbstractTask> constructor = lTaskClass.getConstructor(IRequest.class, Context.class, boolean.class);
            AbstractTask abstractTask = constructor.newInstance(pRequest, context, isShowLoading);               //创建Task的实例
            return abstractTask.execute(pCallBack);                               //调用异步请求方法并返回子线程任务对象
        } catch (Exception e) {
            logger.e("Create " + lTaskClass.getSimpleName() + "failed!", e);
            if(pCallBack != null){
                pCallBack.onFailed(null);
                if(isShowLoading) {
                    LoadingCOM.getInstance(context).dismissLoading();
                }
            }
            return null;
        }
    }

    /**
     * 描 述：异步任务请求网络并返回子线程任务对象，不弹Loading<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/4 注释 <br/>
     * @param context context
     * @param pRequest 业务请求消息对象
     * @param pCallBack 回调方法
     * @return 线程任务，可用来取消任务进度。如果创建网络任务失败直接返回null
     */
    public static AsyncTask getDataFromNet(Context context, IRequest pRequest, AbstractTask.HttpCallBack pCallBack) {
        return getDataFromNet(context, pRequest, pCallBack, false);
    }

    /**
     * 描 述：同步任务请求网络并返回响应体<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/4 注释 <br/>
     * @param pRequest 业务请求消息对象
     * @return AgentResponse
     * @throws TaskFailException 网络任务创建失败或网络请求返回码不是200抛出此异常
     */
    public static AbstractResponse getDataFromNet(Context context, IRequest pRequest, boolean isShowLoading) throws TaskFailException {
        Class<? extends AbstractTask> lTaskClass = pRequest.getTaskClass();
        try {
            Constructor<? extends AbstractTask> constructor = lTaskClass.getConstructor(IRequest.class, Context.class, boolean.class);
            AbstractTask abstractTask = constructor.newInstance(pRequest, context, isShowLoading);               //创建Task的实例
            ResponseContent lResponseContent = abstractTask.execute();            //调用同步请求方法
            if(lResponseContent == null) {
                syncTaskErrorCallBack(context);
                throw new TaskFailException("Result is null.");
            }
            if(lResponseContent.getmResultCode() != 200){      //如果返回码不是200则抛出异常
                syncTaskErrorCallBack(context);
                throw new TaskFailException("Result code is: " + lResponseContent.getmResultCode());
            }
            return JSON.parseObject((String)lResponseContent.getEntity(), pRequest.getResponseClass());
        } catch (Exception e) {
            logger.e("Create " + lTaskClass.getSimpleName() + "failed!", e);
            throw new TaskFailException("Create " + lTaskClass.getSimpleName() + "failed!");
        }
    }

    /**
     * 描 述：同步任务异常后的回调<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/3/16 注释 <br/>
     * @param context 如果是Activity实例则在主线程弹出对话框
     */
    private static void syncTaskErrorCallBack(Context context) {
        Toaster.showShortToast(context, R.string.net_error);
    }

    /**
     * 描 述：生成对象信息的特征码<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/14 <br/>
     */
    public static String createObjectName(Object obj) {
        return MD5.mD5Encode(JSON.toJSONString(obj), RequestParams.UTF_8);
    }
}
