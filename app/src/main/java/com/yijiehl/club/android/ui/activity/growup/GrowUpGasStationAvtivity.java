package com.yijiehl.club.android.ui.activity.growup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.ArticleDetailActivity;
import com.yijiehl.club.android.ui.activity.BmActivity;

import java.text.NumberFormat;
import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: GrowUpGasStationAvtivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_growup_gas_station)
public class GrowUpGasStationAvtivity extends BmActivity {

    @ViewInject(R.id.tv_show_money)
    private TextView mShowMoney;

    private UserInfo mUserInfo;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.growup_comeon);
    }


    @Override
    protected void onResume() {
        super.onResume();
        CacheDataDAO.getInstance(null).getCacheDataAsync(ContextUtils.getSharedString(this, R.string.shared_preference_user_id),
                getString(R.string.shared_preference_user_info));
    }

    @Override
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {
        if (TextUtils.equals(getString(R.string.shared_preference_user_info), pCacheDataEntity.getmName())) {
            mUserInfo = JSON.parseObject(pCacheDataEntity.getmData(), UserInfo.class);
            fillInfoList(mUserInfo.getCustAmount());
        }
    }

    /**
     * 描 述：填充金额信息<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/31 <br/>
     */
    private void fillInfoList(String num){
        mShowMoney.setText(num);
    }

    @OnClick({R.id.layout_new_left,R.id.layout_new_right})
    private void lookDetailNew() {
        // TODO: 2016/10/10 此处的url是临时的；
        String url="http://biz.yijiehulian.com/showpgclfybiz.htm?clfy=kb_gift_milk_powder&dd=XXXXXXXXX&bd=showdetail";

        ActivitySvc.startArticle(this,false,url,null,null,null,null);
    }
}
