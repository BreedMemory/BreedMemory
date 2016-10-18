/**
 * 项目名称：工具库 <br/>
 * 文件名称: CkApplication.java <br/>
 * <p/>
 * Created by 谌珂 on 2015/12/29.  <br/>
 */
package com.uuzz.android;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.uuzz.android.util.Common;
import com.uuzz.android.util.ImageLoaderUtils;
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

        //初始化ImageLoader配置
        // XXX: 谌珂 2016/9/18 替换默认图片资源 更换图片加载器
        ImageLoaderConfiguration config = ImageLoaderUtils.getImageLoaderConfiguration(this, ImageLoaderUtils.getDisplayImageOptions(android.R.mipmap.sym_def_app_icon));
        ImageLoader.getInstance().init(config);
    }
}
