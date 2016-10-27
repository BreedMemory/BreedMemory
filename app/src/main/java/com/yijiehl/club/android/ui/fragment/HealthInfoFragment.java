/**
 * 项目名称：手机在线 <br/>
 * 文件名称: HealthInfoFragment.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/25.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.fragment;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseSearch;
import com.yijiehl.club.android.network.request.search.ReqSearchBabyData;
import com.yijiehl.club.android.network.request.search.ReqSearchMotherDataList;
import com.yijiehl.club.android.network.response.RespSearchHealthData;
import com.yijiehl.club.android.network.response.RespSearchHealthDataList;
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
            }
        });
    }

    /**
     * 描 述：获取宝宝i的当天数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/25 <br/>
     * @param index 宝宝索引
     */
    private void getBabyDataListHeightImpl(final int index) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherDataList(getActivity(), ReqBaseSearch.StatisticalTarget.BODY_HEIGHT), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mBabyDataListHeight.add(index, (RespSearchHealthDataList)pResponse);
            }
        });
    }

    /**
     * 描 述：获取宝宝i的当天数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/25 <br/>
     * @param index 宝宝索引
     */
    private void getBabyDataListWeightImpl(final int index) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherDataList(getActivity(), ReqBaseSearch.StatisticalTarget.BODY_WEIGHT), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mBabyDataListWeight.add(index, (RespSearchHealthDataList)pResponse);
            }
        });
    }

    /**
     * 描 述：获取宝宝i的当天数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/25 <br/>
     * @param index 宝宝索引
     */
    private void getBabyDataListHeadImpl(final int index) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherDataList(getActivity(), ReqBaseSearch.StatisticalTarget.HEAD_PERIMETER), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mBabyDataListHead.add(index, (RespSearchHealthDataList)pResponse);
            }
        });
    }

    /**
     * 描 述：获取宝宝i的当天数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/25 <br/>
     * @param index 宝宝索引
     */
    private void getBabyDataListChestImpl(final int index) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchMotherDataList(getActivity(), ReqBaseSearch.StatisticalTarget.CHEST_PERIMETER), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                mBabyDataListChest.add(index, (RespSearchHealthDataList)pResponse);
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(mUserInfo == null && isVisibleToUser) {
            CacheDataDAO.getInstance(null).getCacheDataAsync(ContextUtils.getSharedString(getActivity(), R.string.shared_preference_user_id),
                    getString(R.string.shared_preference_user_info));
        }
    }

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
