/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: BaseHostFragment.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/5.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.fragment;

import android.support.annotation.Nullable;
import android.view.View;

import com.yijiehl.club.android.ui.activity.MainActivity;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: BaseHostFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/5 <br/>
 * @author 谌珂 <br/>
 */
public abstract class BaseHostFragment extends BmFragment {
    /**
     * 描 述：获取标题栏左边按钮的点击监听事件<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/5 <br/>
     */
    protected abstract @Nullable View.OnClickListener getLeftBtnClickListener();

    /**
     * 描 述：获取标题栏右边按钮的点击监听事件<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/5 <br/>
     */
    protected abstract @Nullable View.OnClickListener getRightBtnClickListener();

    /**
     * 描 述：是否显示标题栏左边按钮<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/5 <br/>
     */
    protected abstract boolean isLeftBtnVisible();

    /**
     * 描 述：是否显示标题栏右边按钮<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/5 <br/>
     */
    protected abstract boolean isRightBtnVisible();

    /**
     * 描 述：当此Framgent显示时会调用，刷新标题栏<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/5 <br/>
     */
    public void onShow() {
        if(!(getActivity() instanceof MainActivity)) {
            return;
        }
        MainActivity activity = (MainActivity) getActivity();
        //设置点击监听
        activity.getmLeftBtn().setOnClickListener(getLeftBtnClickListener());
        activity.getmRightBtn().setOnClickListener(getRightBtnClickListener());
        //设置是否显示btn
        int visibility;
        visibility = isLeftBtnVisible() ? View.VISIBLE : View.GONE;
        activity.getmHeadLeftContainer().setVisibility(visibility);
        visibility = isRightBtnVisible() ? View.VISIBLE : View.GONE;
        activity.getmHeadRightContainer().setVisibility(visibility);
    }
}
