/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ImageLoaderUtils.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/3/8.  <br/>
 */
package com.uuzz.android.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.uuzz.android.R;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ImageLoaderUtils <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/3/8 <br/>
 * @author 谌珂 <br/>
 */
public class ImageLoaderUtils {

    /**
     * 描 述：获取ImageLoader默认图片的配置<br/>
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
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
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

    /**
     * 描 述：获取ImageLoader的配置<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/18 <br/>
     * @param options 默认图片配置
     * @return ImageLoader的配置
     */
    public static ImageLoaderConfiguration getImageLoaderConfiguration(Context context, DisplayImageOptions options) {
        int maxMemory = ((int) Runtime.getRuntime().maxMemory())/1024/1024/10;
        return new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(8000, 8000) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(maxMemory)) //你可以通过自己的内存缓存实现
                .memoryCacheSize(maxMemory)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                // TODO: 谌珂 2016/9/5 设置默认图片
                .defaultDisplayImageOptions(ImageLoaderUtils.getDisplayImageOptions(R.drawable.bg_loading))
                .imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
//                .writeDebugLogs() // Remove for release app
                .build();
    }
}
