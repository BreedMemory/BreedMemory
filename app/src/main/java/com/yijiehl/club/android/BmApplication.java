/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: BmApplication.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/9.  <br/>
 */
package com.yijiehl.club.android;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.uuzz.android.CkApplication;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: BmApplication <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/9 <br/>
 * @author 谌珂 <br/>
 */
public class BmApplication extends CkApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        PlatformConfig.setWeixin("wx4703b3851c791b7c", "ae345ef7c4ee055cdeee5325b6bb767b");
        PlatformConfig.setQQZone("1105790102", "4SRtowOI3Au1hGWx");
        UMShareAPI.get(this);
    }
}
