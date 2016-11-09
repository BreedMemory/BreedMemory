/**
 * 项目名称：手机在线 <br/>
 * 文件名称: ShareSvc.java <br/>
 * <p>
 * Created by 谌珂 on 2016/11/7.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.svc;

import android.app.Activity;
import android.text.TextUtils;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.uuzz.android.util.Toaster;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.share.ShareCallBack;

import java.io.File;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: ShareSvc <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/11/7 <br/>
 * @author 谌珂 <br/>
 */
public class ShareSvc {
    /**
     * 描 述：分享url链接<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/7 <br/>
     * @param activity Activity
     * @param url 需要分享的url
     * @param title 分享标题
     * @param desc 分享描述
     */
    public static void shareUrl(Activity activity, String url, String title, String desc) {
        if(TextUtils.isEmpty(desc)) {
            desc = title;
        }
        new ShareAction(activity).withText(desc).withTitle(title).withTargetUrl(url)
                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE)
                .setCallback(new ShareCallBack(activity)).open();
    }

    /**
     * 描 述：分享url链接<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/7 <br/>
     * @param activity Activity
     * @param uri 分享图片的网络url或者本地图片的路径
     * @param title 分享标题
     */
    public static void sharePhoto(Activity activity, String uri, String title) {
        if(TextUtils.isEmpty(uri)) {
            Toaster.showShortToast(activity, R.string.share_no_photo);
            return;
        }
        UMImage image;
        if(uri.startsWith("http")) {
            image = new UMImage(activity, uri);
        } else {
            File file = new File(uri);
            if(!file.exists() || file.isDirectory()) {
                Toaster.showShortToast(activity, R.string.share_no_photo);
                return;
            }
            image = new UMImage(activity, uri);
        }
        new ShareAction(activity).withText(title).withMedia(image)
                .setDisplayList(SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.QQ,SHARE_MEDIA.QZONE)
                .setCallback(new ShareCallBack(activity)).open();
    }
}
