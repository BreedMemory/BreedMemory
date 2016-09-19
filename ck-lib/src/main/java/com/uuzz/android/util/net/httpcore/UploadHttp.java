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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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
            URL requestUrl = new URL(url);
            HttpURLConnection httpConn = (HttpURLConnection)requestUrl.openConnection();

            httpConn.setDoInput( true );
            httpConn.setDoOutput( true );
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty( "content-type", "text/html" );

            BufferedOutputStream requestOutputStream = new BufferedOutputStream( httpConn.getOutputStream() );

            File file = (File) params;
            FileInputStream fileInputStream = new FileInputStream(file.getPath());   //待上传的文件输入流
            httpConn.connect();
            byte[] readBuf = new byte[1024];
            int readNum;
            long size = file.length();
            if(size == 0) {
                return mResponseContent;
            }
            long upLoadedSize = 0;
            while(( readNum = fileInputStream.read( readBuf, 0, 1024 ) ) > 0 ) {
                requestOutputStream.write( readBuf, 0, readNum );
                upLoadedSize += readNum;
                if(asyncTask != null) {
                    asyncTask.updateProgress((int)((upLoadedSize/size)*100));
                }
                requestOutputStream.flush();
                Log.d("UploadHttp", "upload: " + (int)((upLoadedSize/size)*100) + "%");
            }
            fileInputStream.close();

            //获得响应状态
            int resultCode=httpConn.getResponseCode();
            if(HttpURLConnection.HTTP_OK==resultCode){
                StringBuilder sb = new StringBuilder();
                String readLine;
                BufferedReader responseReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
                while((readLine=responseReader.readLine())!=null){
                    sb.append(readLine).append("\n");
                }
                responseReader.close();
                mResponseContent = new ResponseContent<>();
                mResponseContent.setEntity(sb.toString());
                mResponseContent.setHeads(null);
                mResponseContent.setmResultCode(resultCode);
                return mResponseContent;
            }

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
