/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: BmFragment.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/5.  <br/>
 */
package com.yijiehl.club.android.ui.fragment;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;

import com.uuzz.android.ui.fragment.CkFragment;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;

import java.util.Observable;
import java.util.Observer;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: BmFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/5 <br/>
 * @author 谌珂 <br/>
 */
public class BmFragment extends CkFragment implements Observer {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CacheDataDAO.getInstance(getActivity()).addObserver(this);
    }

    @Override
    protected void onDataRestored(Bundle savedInstanceState) {
        CacheDataDAO.getInstance(getActivity()).deleteObserver(this);
        super.onDataRestored(savedInstanceState);
    }

    @Override
    public void update(Observable observable, Object data) {
        if(observable instanceof CacheDataDAO) {
            Message msg = Message.obtain();
            switch (msg.what) {
                case CacheDataDAO.CACHE_DATA:                           //数据缓存被成功取到
                    onReceiveCacheData((CacheDataEntity) msg.obj);
                    break;
            }
        }
    }

    /**
     * 描 述：当缓存数据被取出时调用<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/1 <br/>
     */
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {}
}
