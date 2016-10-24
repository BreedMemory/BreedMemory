/**
 * 项目名称：手机在线 <br/>
 * 文件名称: CollectArticle.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/20.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: CollectArticle <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/20 <br/>
 * @author 谌珂 <br/>
 */
public class CollectArticle extends Collect {
    public CollectArticle(String dataName, String dataLabel, String imageInfo, String dataShowUrl, String dataInfo) {
        this.dataName = dataName;
        this.dataLabel = dataLabel;
        this.imageInfo = imageInfo;
        this.dataShowUrl = dataShowUrl;
        this.dataInfo = dataInfo;
    }

    @Override
    protected String initDataType() {
        return "article";
    }
}
