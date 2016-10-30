/**
 * 项目名称：手机在线 <br/>
 * 文件名称: MenuPopupWindow.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/30.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.widget.PopupWindow;

import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.ui.view.NoScrollListView;
import com.uuzz.android.util.ScreenTools;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: MenuPopupWindow <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/30 <br/>
 * @author 谌珂 <br/>
 */
public class MenuPopupWindow extends PopupWindow {

    private NoScrollListView mListView;
    private BaseListViewAdapter mAdapter;

    public MenuPopupWindow(Context context, BaseListViewAdapter adapter) {
        super(context);
        initWindow(context, adapter);
    }

    public MenuPopupWindow(Context context) {
        super(context);
        initWindow(context, null);
    }

    private void initWindow(Context context, BaseListViewAdapter adapter) {
        mListView = new NoScrollListView(context);
        if(adapter != null) {
            mListView.setAdapter(adapter);
            mListView.setOnItemClickListener(adapter);
        }
        setContentView(mListView);
        setWidth(ScreenTools.dip2px(context, 170));
        setHeight(ScreenTools.dip2px(context, 130));
//        setFocusable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
//        setTouchable(true);
        update();
    }

    public BaseListViewAdapter getAdapter() {
        return mAdapter;
    }

    public void setAdapter(BaseListViewAdapter mAdapter) {
        this.mAdapter = mAdapter;
        if(mAdapter != null) {
            mListView.setAdapter(mAdapter);
            mListView.setOnItemClickListener(mAdapter);
        }
    }
}
