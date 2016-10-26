/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: QuestionFragment.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.question.AskQuestionActivity;
import com.yijiehl.club.android.ui.activity.question.QuestionListActivity;
import com.yijiehl.club.android.ui.activity.user.MineActivity;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: QuestionFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 *
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.fragment_question)
public class QuestionFragment extends BaseHostFragment {

    private UserInfo mUserInfo;
    private AlertDialog alertDialog;

    @Nullable
    @Override
    protected View.OnClickListener getLeftBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DONE: 谌珂 2016/9/5 跳转到个人账户
                startActivity(new Intent(getActivity(), MineActivity.class));
            }
        };
    }

    @Nullable
    @Override
    protected View.OnClickListener getRightBtnClickListener() {
        return null;
    }

    @Override
    protected boolean isLeftBtnVisible() {
        ((MainActivity)getActivity()).getmLeftBtn().setText(R.string.icon_me);
        return true;
    }

    @Override
    protected boolean isRightBtnVisible() {
        return false;
    }

    @Override
    protected int getTitle() {
        return R.string.ask_and_answer;
    }

    @Override
    public void onResume() {
        super.onResume();
        CacheDataDAO.getInstance(null).getCacheDataAsync(ContextUtils.getSharedString(getActivity(), R.string.shared_preference_user_id),
                getString(R.string.shared_preference_user_info));
    }

    @Override
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {
        if (TextUtils.equals(getString(R.string.shared_preference_user_info), pCacheDataEntity.getmName())) {
            mUserInfo = JSON.parseObject(pCacheDataEntity.getmData(), UserInfo.class);
        }
    }

    @OnClick(R.id.layout_search)
    private void search(){
        Toaster.showShortToast(getActivity(),"此搜索功能暂未实现");
    }

    @OnClick(R.id.btn_my_question)
    private void myQuestion() {
        Intent intent=new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra("type","my");
        startActivity(intent);
    }

    @OnClick(R.id.layout_mother_zone)
    private void motherZone() {
        Intent intent=new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra("type","mother");
        startActivity(intent);
    }

    @OnClick(R.id.layout_baby_zone)
    private void babyZone() {
        /*if (!isBabyShow) {
            babyContextList.setVisibility(View.GONE);
            isBabyShow = true;
        } else {
            babyContextList.setVisibility(View.VISIBLE);
            isBabyShow = false;
        }*/
    }

    @OnClick(R.id.layout_zero_month)
    private void zeroMonth() {
        Intent intent=new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra("type","0_3month");
        startActivity(intent);
    }

    @OnClick(R.id.layout_three_months)
    private void threeMonth() {
        Intent intent=new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra("type","3_12month");
        startActivity(intent);
    }

    @OnClick(R.id.layout_one_year)
    private void oneYear() {
        Intent intent=new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra("type","12_18month");
        startActivity(intent);
    }

    @OnClick(R.id.layout_one_half_years)
    private void oneHalfYear() {
        Intent intent=new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra("type","18-36month");
        startActivity(intent);
    }

    @OnClick(R.id.layout_ask)
    private void ask(){
        if(mUserInfo.getStatus().getName().indexOf("general")<0){
            View mAlertLayout= LayoutInflater.from(getActivity()).inflate(R.layout.can_not_ask_dialog, null);
            TextView tvPhone= (TextView) mAlertLayout.findViewById(R.id.tv_dialog_phone);
            if(!TextUtils.isEmpty(mUserInfo.getCustServicePhone())){
                tvPhone.setText(mUserInfo.getCustServicePhone());
            }
            alertDialog=new AlertDialog.Builder(getActivity()).setView(mAlertLayout).show();
            return;
       }
        startActivity(new Intent(getActivity(), AskQuestionActivity.class));
    }
}
