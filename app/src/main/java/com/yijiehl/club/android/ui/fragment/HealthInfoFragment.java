/**
 * 项目名称：手机在线 <br/>
 * 文件名称: HealthInfoFragment.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/25.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.fragment;

import com.yijiehl.club.android.network.response.RespSearchHealthData;
import com.yijiehl.club.android.network.response.RespSearchHealthDataList;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;

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
public class HealthInfoFragment extends BmFragment {

    /** 用户数据 */
    protected UserInfo mUserInfo;

    /** 母亲当天数据 */
    protected RespSearchHealthData mMotherData;
    /** 宝宝当天数据 */
    protected List<RespSearchHealthData> mBabyDatas;
    /** 母亲当天体温数据 */
    protected RespSearchHealthDataList mMotherDataListTemperature;
    /** 母亲当天体重数据 */
    protected RespSearchHealthDataList mMotherDataListWeight;
    /** 母亲当天体温数据 */
    protected RespSearchHealthDataList mMotherDataListChest;
    /** 母亲当天体重数据 */
    protected RespSearchHealthDataList mMotherDataListWaist;
    /** 母亲当天体重数据 */
    protected RespSearchHealthDataList mMotherDataListHip;
    /** 宝宝当天数据 */
    protected List<RespSearchHealthDataList> mBabyDataList;

    public void setmUserInfo(UserInfo mUserInfo) {
        this.mUserInfo = mUserInfo;
    }
}
