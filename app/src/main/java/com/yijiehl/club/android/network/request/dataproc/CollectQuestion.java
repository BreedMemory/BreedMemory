package com.yijiehl.club.android.network.request.dataproc;

/**
 * 项目名称：孕育记忆 <br/>
 * 类  名: CollectQuestion <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/10/24 <br/>
 * @author 张志新 <br/>
 */
public class CollectQuestion extends Collect{

    public CollectQuestion(String dataName, String dataLabel, String imageInfo, String dataShowUrl, String dataInfo) {
        this.dataName = dataName;
        this.dataLabel = dataLabel;
        this.imageInfo = imageInfo;
        this.dataShowUrl = dataShowUrl;
        this.dataInfo = dataInfo;
    }
    @Override
    protected String initDataType() {
        return "question ";
    }
}
