/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: UploadHttp.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/19.  <br/>
 */
package com.uuzz.android.util.net.httpcore;

import android.util.Log;

import com.uuzz.android.util.net.response.base.ResponseContent;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.net.HttpURLConnection;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: UploadHttp <br/>
 * 类描述: 上传图片的类，不通用<br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/19 <br/>
 * @author 谌珂 <br/>
 */
public class UploadHttp<E> extends BaseHttp<E, String> {
    @Override
    protected ResponseContent<String> doPost(String url, E params, Header[] headers, String charset, int timeout, boolean isSingle) {
        ResponseContent<String> mResponseContent = null;
        try {
            HttpClient mHttpClient = SingleHttpClient.getHttpsInstance();
            HttpPost mHttpPost = new HttpPost(url);
            //添加实体到post请求中

            File file = (File) params;
            mHttpPost.setEntity(new FileEntity(file, "text/xml"));
            if(headers != null && headers.length > 0){
                mHttpPost.setHeaders(headers);
            }
            mHttpPost.addHeader("content-type", "text/xml");
            mHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeout);
            HttpResponse response;
            try {
                response = mHttpClient.execute(mHttpPost); //请求并返回结果
            } catch (Exception e) {
                return null;
            }
            int statuCode = response.getStatusLine().getStatusCode();
            Header[] allHeaders = response.getAllHeaders();
            HttpEntity entity = response.getEntity(); //拿到返回的实体
            String content = EntityUtils.toString(entity, charset); //把实体按照编码转成字符串
            mResponseContent = new ResponseContent<String>();
            mResponseContent.setEntity(content);
            mResponseContent.setHeads(allHeaders);
            mResponseContent.setmResultCode(statuCode);
            if(statuCode == HttpURLConnection.HTTP_OK){
                return mResponseContent;
            }
            return mResponseContent;

        } catch (Exception e) {
            Log.e("UploadHttp", "upload failed", e);
        }

        return mResponseContent;
    }

    @Override
    protected ResponseContent<String> doGet(String url, E params, Header[] headers, String charset, int timeout, boolean isSingle) {
        return null;
    }
}
