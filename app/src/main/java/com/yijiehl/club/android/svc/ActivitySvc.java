/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: ActivitySvc.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/7.  <br/>
 */
package com.yijiehl.club.android.svc;

import android.content.Context;
import android.content.Intent;

import com.yijiehl.club.android.ui.activity.MainActivity;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: ActivitySvc <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/7 <br/>
 * @author 谌珂 <br/>
 */
public class ActivitySvc {

    /**
     * 描 述：跳转到主页<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/7 <br/>
     */
    public static void startMainActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }
}
