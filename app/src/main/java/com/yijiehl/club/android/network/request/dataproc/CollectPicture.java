/**
 * 项目名称：手机大管家
 * 文件名称: CollectPicture.java
 * Created by 谌珂 on 2016/10/31.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: CollectPicture<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
public class CollectPicture extends Collect {
    public CollectPicture(String url) {
        this.imageInfo = url;
    }

    @Override
    protected String initDataType() {
        return "photo";
    }
}
