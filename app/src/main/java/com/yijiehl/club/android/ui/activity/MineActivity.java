/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: MineActivity.java <br/>
 * Created by 张志新 on 2016/9/11.  <br/>
 */
package com.yijiehl.club.android.ui.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.ReqLoginOut;
import com.yijiehl.club.android.network.response.ResultMsg;
import com.yijiehl.club.android.network.response.base.BaseResponse;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: MineActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/11 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_mine)
public class MineActivity extends BmActivity {
    @Override
    protected String getHeadTitle() {
        return getString(R.string.mine);
    }


    // TODO: 2016/9/11 此处需要根据需求再写跳转。。。
    @OnClick(R.id.layout_mine_info)
    private void personInfo() {
        startActivity(new Intent(MineActivity.this, PersonalInfoActivity.class));
    }

    @OnClick(R.id.layout_health_data)
    private void healthData() {

    }

    @OnClick(R.id.layout_medicine_remind)
    private void medicineRemind() {

    }

    @OnClick(R.id.layout_my_message)
    private void myMessage() {

    }

    @OnClick(R.id.layout_my_collect)
    private void myCellect() {

    }

    @OnClick(R.id.layout_photo_manage)
    private void photoManege() {

    }

    @OnClick(R.id.layout_club_introduction)
    private void clubIntro() {

    }

    @OnClick(R.id.layout_my_exit)
    private void exit() {
        NetHelper.getDataFromNet(this, new ReqLoginOut(this), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                startActivity(new Intent(MineActivity.this,LoginActivity.class));
                finish();
            }
        },false);
    }

    @OnClick({R.id.iv_my_money_pic, R.id.tv_show_my_money, R.id.textView})
    private void toGasStation() {
        startActivity(new Intent(this, GrowUpGasStationAvtivity.class));
    }
}
