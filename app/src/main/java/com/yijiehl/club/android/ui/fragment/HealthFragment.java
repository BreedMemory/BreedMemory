/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: HealthFragment.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/18.  <br/>
 */
package com.yijiehl.club.android.ui.fragment;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.user.MineActivity;

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
        // TODO: 谌珂 2016/10/25 根据用户状态转换
        return null;
    }

    @Override
    protected boolean isLeftBtnVisible() {
        ((MainActivity)getActivity()).getmLeftBtn().setText(R.string.icon_me);
        return true;
    }

    @Override
    protected boolean isRightBtnVisible() {
        // TODO: 谌珂 2016/10/25 根据用户状态转换
        return false;
    }

    @Override
    protected int getTitle() {
        return R.string.health;
    }

    @Override
    public void onShow() {
        super.onShow();
        if(mUserInfo == null) {
            CacheDataDAO.getInstance(null).getCacheDataAsync(ContextUtils.getSharedString(getActivity(), R.string.shared_preference_user_id),
                    getString(R.string.shared_preference_user_info));
        }
    }

    @Override
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {
        if (TextUtils.equals(getString(R.string.shared_preference_user_info), pCacheDataEntity.getmName())) {
            mUserInfo = JSON.parseObject(pCacheDataEntity.getmData(), UserInfo.class);
            adaptView();
        }
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
        // TODO: 谌珂 2016/10/26 测试代码
//        switch (mUserInfo.getStatus()) {
//            case SERVICE_IN:
//                // DONE: 谌珂 2016/10/25 显示入住中页面
//                fragment = new ServiceInFragment();
//                break;
//            case SERVICE_AFTER:
//                // DONE: 谌珂 2016/10/25 显示出所后页面
//                fragment = new ServiceAfterFragment();
//                break;
//            default:
//                // DONE: 谌珂 2016/10/25 显示入所前页面
//                fragment = new ServiceBeforeFragment();
//                break;
//        }
        fragment = new ServiceAfterFragment();
        FragmentTransaction lFragmentTransaction = getFragmentManager().beginTransaction();
        lFragmentTransaction.add(R.id.fl_fragment_container, fragment);
        lFragmentTransaction.commitAllowingStateLoss();
    }
}
