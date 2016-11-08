/**
 * 项目名称：手机在线 <br/>
 * 文件名称: ShareCallBack.java <br/>
 * <p>
 * Created by 谌珂 on 2016/11/7.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.share;

import android.content.Context;

import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.uuzz.android.util.Toaster;
import com.yijiehl.club.android.R;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: ShareCallBack <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/11/7 <br/>
 * @author 谌珂 <br/>
 */
public class ShareCallBack implements UMShareListener {
    public ShareCallBack(Context mContent) {
        this.mContent = mContent;
    }

    private Context mContent;

    @Override
    public void onResult (SHARE_MEDIA share_media){
        Toaster.showShortToast(mContent, mContent.getString(R.string.share_success));
    }

    @Override
    public void onError (SHARE_MEDIA share_media, Throwable throwable){
        Toaster.showShortToast(mContent, mContent.getString(R.string.share_failed));
    }

    @Override
    public void onCancel (SHARE_MEDIA share_media){
        Toaster.showShortToast(mContent, mContent.getString(R.string.share_cancel));
    }
}
