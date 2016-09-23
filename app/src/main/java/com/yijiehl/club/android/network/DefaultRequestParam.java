/**
 * 项目名称：孕育迹忆
 * 文件名称: DefaultRequestParam.java
 * <p/>
 * Created by 谌珂 on 2016/1/3.
 */
package com.yijiehl.club.android.network;

import android.content.Context;
import android.text.TextUtils;

import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.net.httpcore.RequestParams;
import com.uuzz.android.util.net.httpcore.TextHttp;
import com.uuzz.android.util.net.request.IRequest;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.common.Common;
import com.yijiehl.club.android.network.request.upload.ReqUploadFile;

/**
 * 项目名称：孕育迹忆
 * 类  名: DefaultRequestParam
 * 类描述: 孕育迹忆的http请求参数列表，对com.uuzz.android.util.net.request.RequestParams简单的封装
 * @author 谌珂 <br/>
 * 版    本：1.0.0
 */
public class DefaultRequestParam {

    /**
     * 描 述：获取默认的请求参数列表<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/3 注释 <br/>
     * @param pRequest 请求实体对象
     * @param isSingle 是否使用单例httpclient发送请求（若使用com.uuzz.android.util.net.http.DownloadHttp）可忽略此参数
     * @param path 下载文件需要保存的路径
     * @return 请求参数列表
     */
    public static RequestParams getRequestParams(IRequest pRequest, boolean isSingle, String path){
        StringBuilder url = new StringBuilder();
        if(pRequest.isHttps()) {
            url.append("https://");
        } else {
            url.append("http://");
        }
        url.append(Common.SERVICE_URL);
        if(!Common.SERVICE_URL.endsWith("/")){
            url.append("/");
        }
        url.append(pRequest.getPath().toLowerCase());
        return new RequestParams<>(url.toString(), pRequest, null, null, -1, pRequest.isGet(), path, isSingle);
    }

    /**
     * 描 述：获取默认的请求参数列表<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/3 注释 <br/>
     * @param pRequest 请求实体对象
     * @param isSignle 是否使用单例httpclient发送请求（若使用com.uuzz.android.util.net.http.DownloadHttp）可忽略此参数
     * @return 请求参数列表
     */
    public static RequestParams getRequestParams(IRequest pRequest, boolean isSignle){
        return getRequestParams(pRequest, isSignle, null);
    }

    /**
     * 描 述：获取上传文件的请求参数列表<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/3 注释 <br/>
     * @param context 上下文
     * @param pRequest 请求实体对象
     * @param isSingle 是否使用单例httpclient发送请求（若使用com.uuzz.android.util.net.http.DownloadHttp）可忽略此参数
     * @param path 下载文件需要保存的路径
     * @return 请求参数列表
     */
    public static RequestParams getUploadRequestParams(Context context, ReqUploadFile pRequest, boolean isSingle, String path){
        String resourceUrl = ContextUtils.getSharedString(context, R.string.shared_preference_resourceUrl);
        StringBuilder url = new StringBuilder();
        if(!resourceUrl.startsWith("http")) {
            if(pRequest.isHttps()) {
                url.append("https://");
            } else {
                url.append("http://");
            }
        }

        url.append(resourceUrl);
        if(!TextUtils.isEmpty(resourceUrl) && !resourceUrl.endsWith("/")){
            url.append("/");
        }
        path = TextHttp.createParams(pRequest);
        url.append(pRequest.getPath().toLowerCase());
        url.append("?").append(path);
        return new RequestParams<>(url.toString(), pRequest.getFile(), null, null, -1, pRequest.isGet(), path, isSingle);
    }

    /**
     * 描 述：获取上传文件的请求参数列表<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/3 注释 <br/>
     * @param context 上下文
     * @param pRequest 请求实体对象
     * @param isSingle 是否使用单例httpclient发送请求（若使用com.uuzz.android.util.net.http.DownloadHttp）可忽略此参数
     * @return 请求参数列表
     */
    public static RequestParams getUploadRequestParams(Context context, ReqUploadFile pRequest, boolean isSingle){
        return getUploadRequestParams(context, pRequest, isSingle, null);
    }
}
