/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: BmActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/9/1.  <br/>
 */
package com.yijiehl.club.android.ui.activity;

import android.os.Bundle;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.uuzz.android.ui.activity.CkActivity;
import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.ObservableTag;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.yijiehl.club.android.R;

import java.util.Observable;
import java.util.Observer;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: BmActivity <br/>
 * 类描述: https://www.processon.com/view/link/57cccb4de4b0942d7a360ae6<br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/1 <br/>
 * @author 谌珂 <br/>
 */
public abstract class BmActivity extends CkActivity implements Observer {

    /** 标题栏左侧按钮 */
    protected IconTextView mLeftBtn;
    /** 标题栏右侧按钮 */
    protected IconTextView mRightBtn;

    public IconTextView getmLeftBtn() {
        return mLeftBtn;
    }

    public IconTextView getmRightBtn() {
        return mRightBtn;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //默认根布局背景色
        getmRootLayout().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mHeader.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mTitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.primary_text_size));
        CacheDataDAO.getInstance(this).addObserver(this);
    }

    @Override
    protected void onDestroy() {
        CacheDataDAO.getInstance(this).deleteObserver(this);
        super.onDestroy();
    }

    @Override
    protected void configHeadLeftView() {
        mHeadLeftContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_header_touchable));
        mLeftBtn = new IconTextView(this);
        mHeadLeftContainer.addView(mLeftBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLeftBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mLeftBtn.setText(getString(R.string.icon_return));

        mHeadLeftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void configHeadRightView() {
        mHeadRightContainer.setVisibility(View.GONE);
        mRightBtn = new IconTextView(this);
        mHeadRightContainer.addView(mRightBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRightBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mRightBtn.setText(getString(R.string.icon_return));
    }

    @Override
    public void update(Observable observable, Object data) {
        if(observable instanceof CacheDataDAO) {
            Message msg = Message.obtain();
            switch (msg.what) {
                case ObservableTag.CACHE_DATA:                               //数据缓存被成功取到
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
