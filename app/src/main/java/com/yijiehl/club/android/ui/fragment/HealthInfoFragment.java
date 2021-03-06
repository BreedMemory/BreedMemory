/**
 * 项目名称：手机在线 <br/>
 * 文件名称: HealthInfoFragment.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/25.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.ui.view.LineChatView;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.request.search.ReqSearchBabyData;
import com.yijiehl.club.android.network.request.search.ReqSearchBabyDataList;
import com.yijiehl.club.android.network.request.search.ReqSearchMotherData;
import com.yijiehl.club.android.network.request.search.ReqSearchMotherDataList;
import com.yijiehl.club.android.network.response.RespSearchHealthData;
import com.yijiehl.club.android.network.response.RespSearchHealthDataList;
import com.yijiehl.club.android.network.response.innerentity.HealthDataListItem;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: HealthInfoFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/25 <br/>
 * @author 谌珂 <br/>
 */
public abstract class HealthInfoFragment extends BmFragment {

    /** 图表周为单位时每页显示的点数 */
    public static final int CHAT_STEP_WEEK = 8;
    /** 图表月为单位时每页显示的点数 */
    public static final int CHAT_STEP_MONTH = 31;

    /** 用户数据 */
    protected UserInfo mUserInfo;

    /** 母亲当天数据 */
    protected RespSearchHealthData mMotherData;
    /** 宝宝当天数据 */
    protected List<RespSearchHealthData> mBabyDatas = new ArrayList<>();
    /** 母亲体温数据统计 */
    protected RespSearchHealthDataList mMotherDataListTemperature;
    /** 母亲体重数据统计 */
    protected RespSearchHealthDataList mMotherDataListWeight;
    /** 母亲体温数据统计 */
    protected RespSearchHealthDataList mMotherDataListChest;
    /** 母亲体重数据统计 */
    protected RespSearchHealthDataList mMotherDataListWaist;
    /** 母亲体重数据统计 */
    protected RespSearchHealthDataList mMotherDataListHip;
    /** 宝宝身高数据统计 */
    protected List<RespSearchHealthDataList> mBabyDataListHeight = new ArrayList<>();
    /** 宝宝体重数据统计 */
    protected List<RespSearchHealthDataList> mBabyDataListWeight = new ArrayList<>();
    /** 宝宝头围数据统计 */
    protected List<RespSearchHealthDataList> mBabyDataListHead = new ArrayList<>();
    /** 宝宝胸围数据统计 */
    protected List<RespSearchHealthDataList> mBabyDataListChest = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMotherHealthData();
        if(mUserInfo == null) {
            CacheDataDAO.getInstance(null).getCacheDataAsync(ContextUtils.getSharedString(getActivity(), R.string.shared_preference_user_id),
                    getString(R.string.shared_preference_user_info));
        }
    }

    /**
     * 描 述：获取母亲健康信息<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void getMotherHealthData() {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherData(getActivity()), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mMotherData = (RespSearchHealthData) pResponse;
                if(mMotherData == null || mMotherData.getResultList() == null || mMotherData.getResultList().size() == 0) {
                    return;
                }
                onMotherHealthDataReceived();
            }
        });
    }

    /**
     * 描 述：填充图表数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     * @param chat 图表控件
     * @param data 数据集合
     */
    protected void fillChatData(LineChatView chat, RespSearchHealthDataList data) {
        if(data == null || data.getResultList() == null || data.getResultList().size() == 0) {
            return;
        }
        List<Float> values = new ArrayList<>();
        for (HealthDataListItem item: data.getResultList()) {
            values.add(Float.valueOf(item.getStatValue()));
        }
        chat.setValues(values);
    }

    /**
     * 描 述：获取到母亲信息后调用<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected abstract void onMotherHealthDataReceived();

    /**
     * 描 述：获取母亲体温统计数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void getMotherDataListTemperature() {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherDataList(getActivity(), ReqBaseSearch.StatisticalTarget.BODY_TEMPERATURE), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mMotherDataListTemperature = (RespSearchHealthDataList) pResponse;
                onMotherDataListTemperatureReceived();
            }
        });
    }

    /**
     * 描 述：当妈妈体温列表请求成功后返回<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void onMotherDataListTemperatureReceived() {}

    /**
     * 描 述：获取母亲体重统计数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void getMotherDataListWeight() {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherDataList(getActivity(), ReqBaseSearch.StatisticalTarget.BODY_WEIGHT), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mMotherDataListWeight = (RespSearchHealthDataList) pResponse;
                onMotherDataListWeightReceived();
            }
        });
    }

    /**
     * 描 述：当妈妈体重列表请求成功后返回<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void onMotherDataListWeightReceived() {}

    /**
     * 描 述：获取母亲胸围统计数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void getMotherDataListChest() {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherDataList(getActivity(), ReqBaseSearch.StatisticalTarget.CHEST_PERIMETER), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mMotherDataListChest = (RespSearchHealthDataList) pResponse;
                onMotherDataListChestReceived();
            }
        });
    }

    /**
     * 描 述：当妈妈胸围列表请求成功后返回<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void onMotherDataListChestReceived() {}

    /**
     * 描 述：获取母亲腰围统计数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void getMotherDataListWaist() {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherDataList(getActivity(), ReqBaseSearch.StatisticalTarget.WAIST_PERIMETER), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mMotherDataListWaist = (RespSearchHealthDataList) pResponse;
                onMotherDataListWaistReceived();
            }
        });
    }

    /**
     * 描 述：当妈妈腰围列表请求成功后返回<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void onMotherDataListWaistReceived() {}

    /**
     * 描 述：获取母亲臀围统计数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void getMotherDataListHip() {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherDataList(getActivity(), ReqBaseSearch.StatisticalTarget.HIPS_PERIMETER), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mMotherDataListHip = (RespSearchHealthDataList) pResponse;
                onMotherDataListHipReceived();
            }
        });
    }

    /**
     * 描 述：当妈妈臀围列表请求成功后返回<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void onMotherDataListHipReceived() {}

    /**
     * 描 述：获取所有宝宝所有数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/25 <br/>
     */
    protected void getBabyData() {
        if(mUserInfo.getChildrenInfo() == null) {
            return;
        }
        for (int i = 0; i < mUserInfo.getChildrenInfo().size(); i++) {
            getBabyDataImpl(i);
            getBabyDataListHeightImpl(i);
            getBabyDataListWeightImpl(i);
            getBabyDataListHeadImpl(i);
            getBabyDataListChestImpl(i);
        }
    }

    /**
     * 描 述：获取宝宝i的当天数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/25 <br/>
     * @param index 宝宝索引
     */
    private void getBabyDataImpl(final int index) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchBabyData(getActivity(), mUserInfo.getChildrenInfo().get(index).getValue()), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mBabyDatas.add(index, (RespSearchHealthData)pResponse);
                onBabyDataReceived(index);
            }
        });
    }

    /**
     * 描 述：当获取到宝宝信息后回调<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/3 <br/>
     */
    protected void onBabyDataReceived(int index) {

    }

    /**
     * 描 述：获取宝宝i的身高列表数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/25 <br/>
     * @param index 宝宝索引
     */
    private void getBabyDataListHeightImpl(final int index) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchBabyDataList(getActivity(), ReqBaseSearch.StatisticalTarget.BODY_HEIGHT, mUserInfo.getChildrenInfo().get(index).getValue()), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mBabyDataListHeight.add(index, (RespSearchHealthDataList)pResponse);
                onBabyDataListHeightReceived(index);
            }
        });
    }

    /**
     * 描 述：当宝宝身高列表请求成功后返回<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void onBabyDataListHeightReceived(int index) {}

    /**
     * 描 述：获取宝宝i体重的列表数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/25 <br/>
     * @param index 宝宝索引
     */
    private void getBabyDataListWeightImpl(final int index) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchBabyDataList(getActivity(), ReqBaseSearch.StatisticalTarget.BODY_WEIGHT, mUserInfo.getChildrenInfo().get(index).getValue()), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mBabyDataListWeight.add(index, (RespSearchHealthDataList)pResponse);
                onBabyDataListWeightReceived(index);
            }
        });
    }

    /**
     * 描 述：当宝宝体重列表请求成功后返回<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void onBabyDataListWeightReceived(int index) {}

    /**
     * 描 述：获取宝宝i头围的列表数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/25 <br/>
     * @param index 宝宝索引
     */
    private void getBabyDataListHeadImpl(final int index) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchBabyDataList(getActivity(), ReqBaseSearch.StatisticalTarget.HEAD_PERIMETER, mUserInfo.getChildrenInfo().get(index).getValue()), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mBabyDataListHead.add(index, (RespSearchHealthDataList)pResponse);
                onBabyDataListHeadReceived(index);
            }
        });
    }

    /**
     * 描 述：当宝宝头围列表请求成功后返回<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void onBabyDataListHeadReceived(int index) {}

    /**
     * 描 述：获取宝宝i头围的列表数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/25 <br/>
     * @param index 宝宝索引
     */
    private void getBabyDataListChestImpl(final int index) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchBabyDataList(getActivity(), ReqBaseSearch.StatisticalTarget.CHEST_PERIMETER, mUserInfo.getChildrenInfo().get(index).getValue()), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mBabyDataListChest.add(index, (RespSearchHealthDataList)pResponse);
                onBabyDataListChestReceived(index);
            }
        });
    }

    /**
     * 描 述：当宝宝胸围列表请求成功后返回<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/2 <br/>
     */
    protected void onBabyDataListChestReceived(int index) {}

    @Override
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {
        if (TextUtils.equals(getString(R.string.shared_preference_user_info), pCacheDataEntity.getmName())) {
            mUserInfo = JSON.parseObject(pCacheDataEntity.getmData(), UserInfo.class);
            onUserInfoAvailable();
        }
    }

    /**
     * 描 述：当UserInfo可用时的回调<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/27 <br/>
     */
    protected void onUserInfoAvailable() {

    }

    public abstract int getCheckId();

}
