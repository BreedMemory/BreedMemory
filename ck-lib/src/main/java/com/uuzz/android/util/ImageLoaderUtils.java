/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: ImageLoaderUtils.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/3/8.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.uuzz.android.util;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: ImageLoaderUtils <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/3/8 <br/>
 * @author 谌珂 <br/>
 */
public class ImageLoaderUtils {

    /**
     * 描 述：获取ImageLoader的配置<br/>
     * 作 者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/3/8 注释 <br/>
     * @param resId 默认显示的图片资源id
     * @return 返回ImageLoader的配置
     */
    public static DisplayImageOptions getDisplayImageOptions(@DrawableRes int resId) {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(resId) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(resId)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(resId)  //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型//
                        //.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时间
                        //设置图片加入缓存前，对bitmap进行设置
                        //.preProcessor(BitmapProcessor preProcessor)
                .resetViewBeforeLoading(false)//设置图片在下载前是否重置，复位
                .displayer(new FadeInBitmapDisplayer(0))//是否图片加载好后渐入的动画时间
                .build();
    }
}
