/**
 * 项目名称：手机在线
 * 文件名称: DefaultRequestParam.java
 * <p/>
 * Created by 谌珂 on 2016/1/3.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.util.net.httpcore.RequestParams;
import com.yijiehl.club.android.Common;
import com.yijiehl.club.android.network.request.BmRequest;

/**
 * 项目名称：手机大管家
 * 类  名: DefaultRequestParam
 * 类描述: 手机大管家默认的http请求参数列表，对com.uuzz.android.util.net.request.RequestParams简单的封装
 * @author 谌珂 <br/>
 * 版    本：1.0.0
 */
public class DefaultRequestParam {



    /**
     * 描 述：获取默认的请求参数列表<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/3 注释 <br/>
     * @param pRequest 请求实体对象
     * @param isSignle 是否使用单例httpclient发送请求（若使用com.uuzz.android.util.net.http.DownloadHttp）可忽略此参数
     * @param path 下载文件需要保存的路径
     * @return 请求参数列表
     */
    public static RequestParams getRequestParams(BmRequest pRequest, boolean isSignle, String path){
        String params = "msg=" + JSON.toJSONString(pRequest);
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
        return new RequestParams<>(url.toString(), params, null, null, -1, false, path, isSignle);
    }

    /**
     * 描 述：获取默认的请求参数列表<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/3 注释 <br/>
     * @param pRequest 请求实体对象
     * @param isSignle 是否使用单例httpclient发送请求（若使用com.uuzz.android.util.net.http.DownloadHttp）可忽略此参数
     * @return 请求参数列表
     */
    public static RequestParams getRequestParams(BmRequest pRequest, boolean isSignle){
        return getRequestParams(pRequest, isSignle, null);
    }
}
