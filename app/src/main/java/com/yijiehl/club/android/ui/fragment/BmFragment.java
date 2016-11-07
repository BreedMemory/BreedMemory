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

import com.bumptech.glide.Glide;
import com.uuzz.android.ui.fragment.CkFragment;
import com.uuzz.android.util.ObservableTag;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CacheDataDAO.getInstance(getActivity()).addObserver(this);
    }

    @Override
    public void onDestroy() {
        CacheDataDAO.getInstance(getActivity()).deleteObserver(this);
        super.onDestroy();
    }

    @Override
    protected void onDataRestored(Bundle savedInstanceState) {
        CacheDataDAO.getInstance(getActivity()).deleteObserver(this);
        super.onDataRestored(savedInstanceState);
    }

    @Override
    public void update(Observable observable, Object data) {
        if(observable instanceof CacheDataDAO) {
            final Message msg = (Message) data;
            switch (msg.what) {
                case ObservableTag.CACHE_DATA:                           //数据缓存被成功取到
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onReceiveCacheData((CacheDataEntity) msg.obj);
                        }
                    });
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

    @Override
    public void onStop() {
        super.onStop();
        Glide.get(getActivity()).clearMemory();
    }
}
