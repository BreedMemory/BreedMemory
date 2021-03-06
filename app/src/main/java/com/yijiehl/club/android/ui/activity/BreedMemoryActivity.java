/**
 * 项目名称：手机大管家
 * 文件名称: BreedMemoryActivity.java
 * Created by 谌珂 on 2016/11/13.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uuzz.android.util.TimeUtil;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchBreedMemory;
import com.yijiehl.club.android.network.response.RespSearchHealthData;
import com.yijiehl.club.android.network.response.innerentity.HealthData;
import com.yijiehl.club.android.ui.adapter.BreedMemoryAdapter;

import java.util.List;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: BreedMemoryActivity<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
@ContentView(R.layout.activity_breed_memory_layout)
public class BreedMemoryActivity extends BmActivity implements ViewPager.OnPageChangeListener {

    Handler mHander = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    @ViewInject(R.id.rl_container)
    private RelativeLayout mContainer;
    @ViewInject(R.id.tv_date)
    private TextView mDate;
    @ViewInject(R.id.tv_content)
    private TextView mContent;
    @ViewInject(R.id.vp_content_container)
    private ViewPager mViewPager;

    private BreedMemoryAdapter memoryAdapter;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        memoryAdapter = new BreedMemoryAdapter(this);
        mViewPager.setAdapter(memoryAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(memoryAdapter.getCount()/2);
        NetHelper.getDataFromNet(this, new ReqSearchBreedMemory(this), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                List<HealthData> datas = ((RespSearchHealthData)pResponse).getResultList();
                memoryAdapter.setDatas(datas);
                memoryAdapter.notifyDataSetChanged();
                onPageSelected(mViewPager.getCurrentItem());
            }
        });
        mRootLayout.setFitsSystemWindows(false);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mContainer.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                 | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                 | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                                 | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                                 | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mContent.setText("");
        if(memoryAdapter == null) {
            return;
        }

        int day = position - memoryAdapter.getCount()/2;
        long timestamp = System.currentTimeMillis() + day*24L*60*60*1000;
        StringBuilder sb = new StringBuilder();
        sb.append(TimeUtil.getTime(timestamp, TimeUtil.DEFAULT_FORMAT_YYYYMMDD)).append(" ").append(TimeUtil.getWeekStr(timestamp));
        mDate.setText(sb.toString());

        if(memoryAdapter.getDatas() != null) {
            for (HealthData data : memoryAdapter.getDatas()) {
                if(TextUtils.equals(data.getStatTime(), TimeUtil.getTime(timestamp, TimeUtil.DEFAULT_FORMAT_YYYYMMDD))) {
                    mContent.setText(data.getDataInfo1());
                    break;
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
