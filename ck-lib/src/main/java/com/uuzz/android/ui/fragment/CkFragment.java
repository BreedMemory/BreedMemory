/**
 * 项目名称：工具库 <br/>
 * 文件名称: CkFragment.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/1/12.  <br/>
 */
package com.uuzz.android.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uuzz.android.util.ioc.utils.InjectUtils;
import com.uuzz.android.util.log.Logger;

/**
 * 项目名称：工具库 <br/>
 * 类  名: CkFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/1/12 <br/>
 * @author 谌珂 <br/>
 */
public abstract class CkFragment extends Fragment {

    /** 日志对象 */
    protected final Logger logger = new Logger(getClass().getSimpleName());

    protected View mRootView;

    public void setmRootView(View mRootView) {
        this.mRootView = mRootView;
    }

    public View getmRootView() {
        return mRootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onDataRestored(savedInstanceState);
        InjectUtils.injectContentView(this);
        logger.d("Fragment restore instance");
    }

    /**
     * 描 述：当数据被恢复时被调用<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/1 <br/>
     * @param savedInstanceState bundle对象
     */
    protected void onDataRestored(Bundle savedInstanceState) {
        //当activity不正常关闭时恢复数据
        InjectUtils.restoreInstance(this, savedInstanceState);
    }

    /**
     * 描 述：绑定View，绑定View事件<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/1 <br/>
     */
    protected void onViewInjected() {
        InjectUtils.inject(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logger.d("Fragment save instance");
        InjectUtils.saveInstances(this, outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onViewInjected();
    }
}
