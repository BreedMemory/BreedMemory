/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ReqSearchAlbum.java <br/>
 * Created by 张志新 on 2016/9/14.  <br/>
 */
package com.yijiehl.club.android.network.request.search;

import android.content.Context;

import com.uuzz.android.util.net.response.AbstractResponse;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ReqSearchAlbum <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/14 <br/>
 * @author 张志新 <br/>
 */
public class ReqSearchAlbum extends ReqBaseSearch {
    public ReqSearchAlbum(Context context) {
        super(context);
    }
    @Override
    protected String getBizType() {
        return "photo_album_main";
    }

    @Override
    public Class<? extends AbstractResponse> getResponseClass() {
        return null;
    }
}
