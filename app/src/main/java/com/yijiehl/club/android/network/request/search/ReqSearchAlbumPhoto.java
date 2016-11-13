/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ReqSearchAlbumPhoto.java <br/>
 * Created by 张志新 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.network.request.search;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.response.ResSearchPhotos;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqSearchAlbumPhoto <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/14 <br/>
 * @author 张志新 <br/>
 */
public class ReqSearchAlbumPhoto extends ReqBaseSearch {
    public ReqSearchAlbumPhoto(Context context, String dataId) {
        super(context);
        this.dataId = dataId;
        this.dataClfy = "album";
    }
    @Override
    public String getBizType() {
        return "photo_detail_main";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return ResSearchPhotos.class;
    }
}
