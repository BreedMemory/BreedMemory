/**
 * 项目名称：手机在线 <br/>
 * 文件名称: AlbumPhotoActivity.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/20.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.activity.photo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.SaveInstance;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchAlbumPhoto;
import com.yijiehl.club.android.network.response.ResSearchPhotos;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.ImageGridAlbumAdapter;

import java.util.ArrayList;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: AlbumPhotoActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/20 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.activity_picture_album)
public class AlbumPhotoActivity extends BmActivity {

    public static final String DATA_ID = "DATA_ID";
    public static final String TIME = "TIME";

    @ViewInject(R.id.tv_show_time)
    TextView showTime;
    @ViewInject(R.id.person_gridview)
    GridView gridView;

    @SaveInstance
    private String mDataId;
    @SaveInstance
    private String mTime;

    private ImageGridAlbumAdapter mAdapter;
    @Override
    protected String getHeadTitle() {
        return getString(R.string.photo);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (TextUtils.isEmpty(mDataId)) {
            mDataId = getIntent().getStringExtra(DATA_ID);
        }
        if (TextUtils.isEmpty(mTime)) {
            mTime = getIntent().getStringExtra(TIME);
        }

        showTime.setText(mTime);
        mAdapter = new ImageGridAlbumAdapter(this);
        gridView.setAdapter(mAdapter);

        NetHelper.getDataFromNet(this, new ReqSearchAlbumPhoto(this, mDataId), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                ResSearchPhotos lPhotoInfo = (ResSearchPhotos) pResponse;
                mAdapter.setDatas(lPhotoInfo.getResultList());
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(id == -1) {
                    return;
                }
                ArrayList<String> list = new ArrayList<>();
                for (int i = 0; i < mAdapter.getDatas().size(); i++) {
                    list.add(mAdapter.getDatas().get(i).getImageInfo());
                }
                ActivitySvc.startImageViewer(AlbumPhotoActivity.this, list, false, position);
            }
        });
    }
}
