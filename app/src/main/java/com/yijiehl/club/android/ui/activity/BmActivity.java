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
        super.onDestroy();
        CacheDataDAO.getInstance(this).deleteObserver(this);
    }

    @Override
    protected void configHeadLeftView() {
        mHeadLeftContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_header_touchable));
        IconTextView lReturnBtn = new IconTextView(this);
        mHeadLeftContainer.addView(lReturnBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) lReturnBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        lReturnBtn.setText(getString(R.string.icon_return));

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
    }

    @Override
    public void update(Observable observable, Object data) {
        if(observable instanceof CacheDataDAO) {
            Message msg = Message.obtain();
            switch (msg.what) {
                case CacheDataDAO.CACHE_DATA:
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
