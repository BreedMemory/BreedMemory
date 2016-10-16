package com.yijiehl.club.android.ui.activity.growup;

import android.content.Intent;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.ArticalDetailActivity;
import com.yijiehl.club.android.ui.activity.BmActivity;

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
    @Override
    protected String getHeadTitle() {
        return getString(R.string.growup_comeon);
    }

    @OnClick({R.id.layout_new_left,R.id.layout_new_right})
    private void lookDetailNew(){
        // TODO: 2016/10/10 此处的url是临时的；
        Intent intent=new Intent(this,ArticalDetailActivity.class);
        intent.putExtra("url","http://biz.yijiehulian.com/showpgclfybiz.htm?clfy=kb_gift_milk_powder&dd=XXXXXXXXX&bd=showdetail");
        startActivity(intent);
    }
}
