/**
 * 项目名称：手机在线 <br/>
 * 文件名称: ServiceInFragment.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/25.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.request.search.ReqSearchMotherData;
import com.yijiehl.club.android.network.request.search.ReqSearchMotherDataList;
import com.yijiehl.club.android.network.response.RespSearchHealthData;
import com.yijiehl.club.android.network.response.RespSearchHealthDataList;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: ServiceInFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/25 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.fragment_health_service_after_layout)
public class ServiceAfterFragment extends HealthInfoFragment {
    /** 总信息 */
    @ViewInject(R.id.tv_health_desc)
    private TextView mInfo;
    /** 显示更多的图标 */
    @ViewInject(R.id.im_more)
    private View mMore;
    /** 显示更多的文字 */
    @ViewInject(R.id.tv_more)
    private View mIcMore;
    /** 图表的大容器 */
    @ViewInject(R.id.ll_form_container)
    private View mFormContainer;
    /** 图表选择器 */
    @ViewInject(R.id.rg_selector)
    private RadioGroup mFormSelector;
    /** 图表小容器 */
    @ViewInject(R.id.fl_form_container)
    private FrameLayout mFormFrameLayout;
    /** 母亲统计图容器 */
    @ViewInject(R.id.form_mother)
    private View mFormMother;
    /** 婴儿统计图容器 */
    @ViewInject(R.id.form_baby)
    private View mFormBaby;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherData(getActivity()), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mMotherData = (RespSearchHealthData) pResponse;
                mInfo.setText(mMotherData.getResultList().get(0).getDataInfo1());
            }
        });
        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherDataList(getActivity(), ReqBaseSearch.StatisticalTarget.BODY_TEMPERATURE), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mMotherDataListTemperature = (RespSearchHealthDataList) pResponse;
            }
        });
        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherDataList(getActivity(), ReqBaseSearch.StatisticalTarget.BODY_WEIGHT), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mMotherDataListWeight = (RespSearchHealthDataList) pResponse;
            }
        });

        getBabyData();

        mFormSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_mother:
                        mFormMother.setVisibility(View.VISIBLE);
                        mFormBaby.setVisibility(View.GONE);
                        break;
                    case R.id.rb_baby0:
                    case R.id.rb_baby1:
                    case R.id.rb_baby2:
                    case R.id.rb_baby3:
                        mFormMother.setVisibility(View.GONE);
                        mFormBaby.setVisibility(View.VISIBLE);
                        fillBabyData(checkedId);
                        break;
                }
            }
        });

        //计算应当显示的宝宝视图按钮
        if(mUserInfo.getChildrenInfo() != null) {
            for (int i = 0; i < mUserInfo.getChildrenInfo().size(); i++) {
                mFormSelector.getChildAt(4-i).setVisibility(View.VISIBLE);
            }
        }
}
