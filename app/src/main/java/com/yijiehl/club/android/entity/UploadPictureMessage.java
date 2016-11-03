/**
 * 项目名称：手机大管家
 * 文件名称: UploadPictureMessage.java
 * Created by 谌珂 on 2016/10/16.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.entity;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: UploadPictureMessage<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class UploadPictureMessage {
    public UploadPictureMessage(String nativePath, String url, long timestamp) {
        this.nativePath = nativePath;
        this.url = url;
        this.timestamp = timestamp;
    }

    private String nativePath;
    private String url;
    private long timestamp;

    public String getNativePath() {
        return nativePath;
    }

    public void setNativePath(String nativePath) {
        this.nativePath = nativePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "nativePath:" + nativePath + ", url:"+ url;
    }
}
