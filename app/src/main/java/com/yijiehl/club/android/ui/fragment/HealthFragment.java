/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: HealthFragment.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.svc.LoginObservable;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.health.HealthInfoAfterActivity;
import com.yijiehl.club.android.ui.activity.health.HealthInfoBeforeActivity;
import com.yijiehl.club.android.ui.activity.health.HealthInfoInActivity;
import com.yijiehl.club.android.ui.activity.user.MineActivity;

import java.util.Observable;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: HealthFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/18 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.fragment_health_layout)
public class HealthFragment extends BaseHostFragment {

    /** 用户基本数据 */
    private UserInfo mUserInfo;
    private HealthInfoFragment fragment;
    private volatile boolean isVisible;

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
        // DONE: 谌珂 2016/10/25 根据用户状态转换
        return null;
    }

    @Override
    protected boolean isLeftBtnVisible() {
        ((MainActivity)getActivity()).getmLeftBtn().setText(R.string.icon_me);
        return true;
    }

    @Override
    protected boolean isRightBtnVisible() {
        ((MainActivity)getActivity()).getmRightBtn().setModle(IconTextView.MODULE_ICON);
        ((MainActivity)getActivity()).getmRightBtn().setText(R.string.icon_edit);
        return false;
    }

    @Override
    protected int getTitle() {
        return R.string.health;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LoginObservable.getInstance().addObserver(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        setUserVisibleHint(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if(mUserInfo == null && isVisibleToUser) {
            CacheDataDAO.getInstance(null).getCacheDataAsync(ContextUtils.getSharedString(getActivity(), R.string.shared_preference_user_id),
                    getString(R.string.shared_preference_user_info));
        } else if(mUserInfo != null && isVisibleToUser) {
            configRightBtn();
        }
    }

    @Override
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {
        if (TextUtils.equals(getString(R.string.shared_preference_user_info), pCacheDataEntity.getmName())) {
            mUserInfo = JSON.parseObject(pCacheDataEntity.getmData(), UserInfo.class);
            if(mUserInfo == null) {
                return;
            }
            adaptView();
            configRightBtn();
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        if(observable == LoginObservable.getInstance()) {
            mUserInfo = null;
            fragment = null;
        }
    }

    /**
     * 描 述：配置右侧按钮<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/28 <br/>
     */
    private void configRightBtn() {
        if(!isVisible) {
            return;
        }
        MainActivity activity = (MainActivity) getActivity();
        switch (mUserInfo.getStatus()) {
            case GENERAL_BEFORE:
            case SERVICE_BEFORE:
            case GENERAL_AFTER:
            case SERVICE_AFTER:
                logger.d("right btn visible,  status is " + mUserInfo.getStatus().getName());
                activity.getmHeadRightContainer().setVisibility(View.VISIBLE);
                break;
            default:
                logger.d("right btn invisible,  status is " + mUserInfo.getStatus().getName());
                activity.getmHeadRightContainer().setVisibility(View.GONE);
                break;
        }
        activity.getmHeadRightContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (mUserInfo.getStatus()) {
                    case GENERAL_BEFORE:
                    case SERVICE_BEFORE:
                        intent = new Intent(getActivity(), HealthInfoBeforeActivity.class);
                        startActivity(intent);
                        break;
                    case GENERAL_AFTER:
                    case SERVICE_AFTER:
                        intent = new Intent(getActivity(), HealthInfoAfterActivity.class);
                        intent.putExtra(HealthInfoInActivity.ROLE, fragment.getCheckId());
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    /**
     * 描 述：根据婴儿数据适配视图<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/25 <br/>
     */
    private void adaptView() {
        if(fragment != null) {
            return;
        }
        // DONE: 谌珂 2016/10/26 测试代码
        switch (mUserInfo.getStatus()) {
            case SERVICE_IN:
                // DONE: 谌珂 2016/10/25 显示入住中页面
                fragment = new ServiceInFragment();
                break;
            case SERVICE_AFTER:
            case GENERAL_AFTER:
                // DONE: 谌珂 2016/10/25 显示出所后页面
                fragment = new ServiceAfterFragment();
                break;
            default:
                // DONE: 谌珂 2016/10/25 显示入所前页面
                fragment = new ServiceBeforeFragment();
                break;
        }
        FragmentTransaction lFragmentTransaction = getFragmentManager().beginTransaction();
        lFragmentTransaction.add(R.id.fl_fragment_container, fragment);
        lFragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LoginObservable.getInstance().deleteObserver(this);
    }
}
