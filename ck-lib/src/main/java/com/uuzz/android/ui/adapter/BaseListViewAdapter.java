package com.uuzz.android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseListViewAdapter<T> extends BaseAdapter implements AdapterView.OnItemClickListener {

    protected Context mContext;
    protected List<T> mDatas;

    public BaseListViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setDatas(List<T> mDatas) {
        this.mDatas = mDatas;
        refresh();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    /**
     * 描 述：添加新数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/8/19 <br/>
     */
    public void addDatas(List<T> datas) {
        if(datas == null || datas.size() < 1) {
            return;
        }
        if(mDatas == null) {
            mDatas = datas;
        } else {
            mDatas.addAll(datas);
        }
        refresh();
    }

    /**
     * 描 述：添加新数据<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/8/19 <br/>
     */
    public void addData(T data) {
        if(data == null) {
            return;
        }
        if(mDatas == null) {
            mDatas = new ArrayList<>();
        }
        mDatas.add(data);
        refresh();
    }

    /**
     * 描 述：清空所有信息<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/27 <br/>
     */
    public void clear() {
        if(mDatas != null) {
            mDatas.clear();
        }
        refresh();
    }

    /**
     * 描 述：如果Context是Activity的实例主线程刷新列表，否则不刷新<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/8/19 <br/>
     */
    protected void refresh() {
        if (Activity.class.isAssignableFrom(mContext.getClass())) {
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public int getCount() {
        if(mDatas == null) {
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
