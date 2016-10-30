/**
 * 项目名称：手机在线 <br/>
 * 文件名称: MenuAdapter.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/30.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.uuzz.android.ui.adapter.BaseListViewAdapter;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.CollectArticle;

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

    public void setUrl(String url) {
        this.url = url;
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
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        if(TextUtils.equals(mDatas.get(position), COLLECT)) {
            NetHelper.getDataFromNet(mContext, new ReqBaseDataProc(mContext, new CollectArticle(name, label, imageInfo, url, dataSummary)), new AbstractCallBack(mContext) {
                @Override
                public void onSuccess(AbstractResponse pResponse) {
                    Toaster.showShortToast(mContext, mContext.getString(R.string.collect_success));
                }
            });
        }
        if(TextUtils.equals(mDatas.get(position), SHARE)) {
            // TODO: 谌珂 2016/10/30 分享
        }
    }
}
