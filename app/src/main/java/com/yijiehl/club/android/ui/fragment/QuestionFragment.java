/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: QuestionFragment.java <br/>
 * <p>
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
import com.uuzz.android.ui.activity.CkActivity;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.question.AskQuestionActivity;
import com.yijiehl.club.android.ui.activity.question.QuestionListActivity;
import com.yijiehl.club.android.ui.activity.question.SearchQuestionActivity;
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
        ((MainActivity) getActivity()).getmLeftBtn().setText(R.string.icon_me);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && mUserInfo == null) {
            CacheDataDAO.getInstance(null).getCacheDataAsync(ContextUtils.getSharedString(getActivity(), R.string.shared_preference_user_id),
                    getString(R.string.shared_preference_user_info));
        }
    }

    @Override
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {
        if (TextUtils.equals(getString(R.string.shared_preference_user_info), pCacheDataEntity.getmName())) {
            mUserInfo = JSON.parseObject(pCacheDataEntity.getmData(), UserInfo.class);
        }
    }

    @OnClick(R.id.layout_search)
    private void search() {
        startActivity(new Intent(getActivity(), SearchQuestionActivity.class));
    }

    @OnClick(R.id.btn_my_question)
    private void myQuestion() {
        Intent intent = new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra(QuestionListActivity.TYPE, QuestionListActivity.MY);
        startActivity(intent);
    }

    @OnClick(R.id.layout_mother_zone)
    private void motherZone() {
        Intent intent = new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra(QuestionListActivity.TYPE, QuestionListActivity.MOTHER);
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
        Intent intent = new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra(QuestionListActivity.TYPE, QuestionListActivity.BABY);
        startActivity(intent);
    }

    @OnClick(R.id.layout_zero_month)
    private void zeroMonth() {
        Intent intent = new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra(QuestionListActivity.TYPE, QuestionListActivity.MONTH0);
        startActivity(intent);
    }

    @OnClick(R.id.layout_three_months)
    private void threeMonth() {
        Intent intent = new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra(QuestionListActivity.TYPE, QuestionListActivity.MONTH3);
        startActivity(intent);
    }

    @OnClick(R.id.layout_one_year)
    private void oneYear() {
        Intent intent = new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra(QuestionListActivity.TYPE, QuestionListActivity.MONTH12);
        startActivity(intent);
    }

    @OnClick(R.id.layout_one_half_years)
    private void oneHalfYear() {
        Intent intent = new Intent(getActivity(), QuestionListActivity.class);
        intent.putExtra(QuestionListActivity.TYPE, QuestionListActivity.MONTH18);
        startActivity(intent);
    }

    @OnClick(R.id.layout_ask)
    private void ask() {
        switch (mUserInfo.getStatus()) {
            case GENERAL_BEFORE:
            case GENERAL_AFTER:
                if(alertDialog == null) {
                    View mAlertLayout = LayoutInflater.from(getActivity()).inflate(R.layout.can_not_ask_dialog, null);
                    TextView tvPhone = (TextView) mAlertLayout.findViewById(R.id.tv_dialog_phone);
//                Log.d("====",mUserInfo.getCustServicePhone());
                    if (!TextUtils.isEmpty(mUserInfo.getCustServicePhone())) {
                        tvPhone.setText(mUserInfo.getCustServicePhone());
                        tvPhone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivitySvc.call((CkActivity) getActivity(), mUserInfo.getCustServicePhone());
                            }
                        });
                    }
                    alertDialog = new AlertDialog.Builder(getActivity()).setView(mAlertLayout).show();
                }
                alertDialog.show();
                break;
            default:
                startActivity(new Intent(getActivity(), AskQuestionActivity.class));
                break;
        }

    }
}
