/**
 * 项目名称：工具库 <br/>
 * 文件名称: CkApplication.java <br/>
 * <p/>
 * Created by 谌珂 on 2015/12/29.  <br/>
 */
package com.uuzz.android;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.uuzz.android.util.Common;
import com.uuzz.android.util.ImageLoaderUtils;
import com.uuzz.android.util.ScreenTools;
import com.uuzz.android.util.UEHandler;
import com.uuzz.android.util.log.Logger;

/**
 * 项目名称：工具库 <br/>
 * 类  名: CkApplication <br/>
 * 类描述: 项目的总Application <br/>
 * 实现了文件路径初始化 <br/>
 * 日志类的初始化工作 <br/>
 * 崩溃后的常规操作 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2015/12/29  <br/>
 * @author 谌珂 <br/>
 */
public class CkApplication extends Application {

    protected Logger logger = new Logger("CkApplication");

    @Override
    public void onCreate() {
        super.onCreate();
        Common.PACKAGE_NAME = getPackageName();
        Thread.setDefaultUncaughtExceptionHandler(new UEHandler(this));
        int[] screenSize = ScreenTools.getScreenPixel(this);
        int maxMemory = ((int) Runtime.getRuntime().maxMemory())/1024/1024/8;
        //初始化ImageLoader配置
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(screenSize[0], screenSize[1]) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(maxMemory)) //你可以通过自己的内存缓存实现
                .memoryCacheSize(maxMemory)
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100) //缓存的文件数量
                // TODO: 谌珂 2016/9/5 设置默认图片
                .defaultDisplayImageOptions(ImageLoaderUtils.getDisplayImageOptions(R.mipmap.ic_launcher))
                .imageDownloader(new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
//                .writeDebugLogs() // Remove for release app
                .build();
        ImageLoader.getInstance().init(config);
    }
}
