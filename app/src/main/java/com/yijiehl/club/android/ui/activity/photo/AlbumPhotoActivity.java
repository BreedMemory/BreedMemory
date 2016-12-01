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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.SaveInstance;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.DeletePicture;
import com.yijiehl.club.android.network.request.search.ReqSearchAlbumPhoto;
import com.yijiehl.club.android.network.response.ResSearchPhotos;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.ImageGridAlbumAdapter;
import com.yijiehl.club.android.ui.dialog.BaseDialog;
import com.yijiehl.club.android.ui.dialog.MessageDialog;

import java.util.ArrayList;
import java.util.List;

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
    @ViewInject(R.id.rl_delete)
    RelativeLayout mRelativeDetele;

    @SaveInstance
    private String mDataId;
    @SaveInstance
    private String mTime;

    private ImageGridAlbumAdapter mAdapter;

    private boolean isDeleteState ;


    @Override
    protected String getHeadTitle() {
        return getString(R.string.photo);
    }


    @Override
    protected void configHeadRightView() {
        mRightBtn = new IconTextView(this);
        mHeadRightContainer.addView(mRightBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRightBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mRightBtn.setText(getString(R.string.select));
        mRightBtn.setModle(IconTextView.MODULE_TEXT);
        mHeadRightContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDeleteState){
                    mRightBtn.setText(R.string.cancel);
                    isDeleteState = true;
                    mRelativeDetele.setVisibility(View.VISIBLE);
                    mAdapter.setSelect(true);
                }else{
                    mRightBtn.setText(R.string.select);
                    isDeleteState = false;
                    mRelativeDetele.setVisibility(View.GONE);
                    mAdapter.setSelect(false);
                }
            }
        });
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
        mAdapter = new ImageGridAlbumAdapter(this,mListener);
        gridView.setAdapter(mAdapter);

        refreshPhoto();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(id == -1) {
                    return;
                }
                ArrayList<String> list = new ArrayList<>();
                ArrayList<String> codes = new ArrayList<>();
                ArrayList<String> descs = new ArrayList<>();
                for (int i = 0; i < mAdapter.getDatas().size(); i++) {
                    list.add(ActivitySvc.createWebUrl(mAdapter.getDatas().get(i).getImageInfo()));
                    codes.add(mAdapter.getDatas().get(i).getDataCode());
                    descs.add(mAdapter.getDatas().get(i).getDataLabel());
                }
                ActivitySvc.startImageViewer(AlbumPhotoActivity.this, list, codes, descs, false, position);
            }
        });
    }

    /**
     * 描 述：<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 谌珂 2016/11/25 <br/>
     */
    private void refreshPhoto(){
        NetHelper.getDataFromNet(this, new ReqSearchAlbumPhoto(this, mDataId), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                ResSearchPhotos lPhotoInfo = (ResSearchPhotos) pResponse;
                mAdapter.setDatas(lPhotoInfo.getResultList());
            }
        });
    }
    @Override
    protected void onResume() {
        refreshPhoto();
        super.onResume();
    }


    @OnClick(R.id.iv_delete)
    private void detelePhotos(){

        if(dataCodeList.size() <= 0){
            Toaster.showShortToast(this,"请选择要删除的照片");
            return;
        }
        MessageDialog dialog = MessageDialog.getInstance(this);
        dialog.setMessage(R.string.do_you_delete_me);
        dialog.showDoubleBtnDialog(new BaseDialog.OnBtnsClickListener() {
            @Override
            public void onLeftClickListener(View v, BaseDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onRightClickListener(View v, final BaseDialog dialog) {

                for(int i = 0 ;i < dataCodeList.size() ;i++){
                    NetHelper.getDataFromNet(AlbumPhotoActivity.this, new ReqBaseDataProc(getApplication(), new DeletePicture(dataCodeList.get(i))), new AbstractCallBack(AlbumPhotoActivity.this) {
                        @Override
                        public void onSuccess(AbstractResponse pResponse) {
                            dialog.dismiss();
                            Toaster.showShortToast(AlbumPhotoActivity.this, getString(R.string.delete_success));
                            refreshPhoto();
                            mRightBtn.setText(R.string.select);
                            isDeleteState = false;
                            mRelativeDetele.setVisibility(View.GONE);
                            mAdapter.setSelect(false);
                            dataCodeList.clear();
                            mAdapter.refresh();
                        }

                        @Override
                        public void onFailed(String msg) {
                            super.onFailed(msg);
                            refreshPhoto();
                            mRightBtn.setText(R.string.select);
                            isDeleteState = false;
                            mRelativeDetele.setVisibility(View.GONE);
                            mAdapter.setSelect(false);
                            dataCodeList.clear();
                        }
                    });
                }


            }
        });
    }

    public interface DeleteListPhoto{
        void addDataCode(String dataCode);
        void deleteDataCode(String dataCode);
    }
    private List<String> dataCodeList = new ArrayList<>();
    private DeleteListPhoto mListener = new DeleteListPhoto() {
        @Override
        public void addDataCode(String dataCode) {
            dataCodeList.add(dataCode);
        }

        @Override
        public void deleteDataCode(String dataCode) {
            dataCodeList.remove(dataCode);
        }
    };
}
