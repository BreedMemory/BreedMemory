/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: MineActivity.java <br/>
 * Created by 张志新 on 2016/9/11.  <br/>
 */
package com.yijiehl.club.android.ui.activity;/**
 * Created by asus on 2016/9/11.
 */

import android.view.View;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.yijiehl.club.android.R;

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
    @OnClick({R.id.layout_health_data, R.id.layout_medicine_remind, R.id.layout_my_message
            , R.id.layout_my_collect,R.id.layout_photo_manage, R.id.layout_club_introduction, R.id.layout_my_exit})
    private void chooseMine(View v) {
        switch (v.getId()) {
            case R.id.layout_health_data:
                break;
            case R.id.layout_medicine_remind:
                break;
            case R.id.layout_my_message:
                break;
            case R.id.layout_my_collect:
                break;
            case R.id.layout_photo_manage:
                break;
            case R.id.layout_club_introduction:
                break;
            case R.id.layout_my_exit:
                break;

        }
    }
}
