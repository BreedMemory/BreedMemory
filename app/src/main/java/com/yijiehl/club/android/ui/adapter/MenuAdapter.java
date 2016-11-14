/**
 * 项目名称：手机在线 <br/>
 * 文件名称: MenuAdapter.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/30.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.ScreenTools;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.CollectArticle;
import com.yijiehl.club.android.network.request.dataproc.CollectQuestion;
import com.yijiehl.club.android.svc.ShareSvc;

import java.util.ArrayList;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: MenuAdapter <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/30 <br/>
 * @author 谌珂 <br/>
 */
public class MenuAdapter extends BaseListViewAdapter<String> {

    public static final String COLLECT = "收藏";
    public static final String SHARE = "分享";

    private String url;
    private String name;
    private String label;
    private String imageInfo;
    private String dataSummary;
    private OnClick mListener;
    private boolean isQuestion;

    public void setListener(OnClick mListener) {
        this.mListener = mListener;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setQuestion(boolean question) {
        isQuestion = question;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setImageInfo(String imageInfo) {
        this.imageInfo = imageInfo;
    }

    public void setDataSummary(String dataSummary) {
        this.dataSummary = dataSummary;
    }

    public MenuAdapter(Context mContext) {
        super(mContext);
        mDatas = new ArrayList<>();
        mDatas.add(COLLECT);
        mDatas.add(SHARE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            TextView tv = new TextView(mContext);
            tv.setBackgroundColor(Color.WHITE);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.primary_text_size));
            tv.setTextColor(mContext.getResources().getColor(R.color.textColorPrimary));
            tv.setPadding(ScreenTools.dip2px(mContext, 27), ScreenTools.dip2px(mContext, 10), ScreenTools.dip2px(mContext, 78), ScreenTools.dip2px(mContext, 10));
            convertView = tv;
        }
        ((TextView)convertView).setText(mDatas.get(position));
        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        if(mListener != null) {
            mListener.onItemClick();
        }
        if(TextUtils.equals(mDatas.get(position), COLLECT)) {
            if(isQuestion) {
                CollectQuestion req=new CollectQuestion(name, label, imageInfo, url, dataSummary);
                NetHelper.getDataFromNet(mContext, new ReqBaseDataProc(mContext, req), new AbstractCallBack(mContext) {
                    @Override
                    public void onSuccess(AbstractResponse pResponse) {
                        if(mListener != null) {
                            mListener.onCollected();
                        }
                        Toaster.showShortToast(mContext, mContext.getString(R.string.collect_success));
                    }
                });
            } else {
                NetHelper.getDataFromNet(mContext, new ReqBaseDataProc(mContext, new CollectArticle(name, label, imageInfo, url, dataSummary)), new AbstractCallBack(mContext) {
                    @Override
                    public void onSuccess(AbstractResponse pResponse) {
                        if(mListener != null) {
                            mListener.onCollected();
                        }
                        Toaster.showShortToast(mContext, mContext.getString(R.string.collect_success));
                    }
                });
            }
        }
        if(TextUtils.equals(mDatas.get(position), SHARE)) {
            // DONE: 谌珂 2016/10/30 分享
            ShareSvc.shareUrl((Activity) mContext, url, name, dataSummary);
        }
    }

    public interface OnClick {
        void onItemClick();
        void onCollected();
    }
}
