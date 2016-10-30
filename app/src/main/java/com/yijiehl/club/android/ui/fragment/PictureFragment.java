/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PictureFragment.java <br/>
 * Created by 张志新 on 2016/9/13.  <br/>
 */
package com.yijiehl.club.android.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.uuzz.android.ui.view.ptr.PtrClassicFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrDefaultHandler;
import com.uuzz.android.ui.view.ptr.PtrFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrListView;
import com.uuzz.android.util.FileUtil;
import com.uuzz.android.util.ObservableTag;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.entity.UploadPictureMessage;
import com.yijiehl.club.android.network.request.search.ReqSearchAlbum;
import com.yijiehl.club.android.network.request.search.ReqSearchPersonalPhoto;
import com.yijiehl.club.android.network.response.ResSearchPhotos;
import com.yijiehl.club.android.network.response.RespSearchAlbums;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.svc.UploadPictureSvc;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.photo.PhotoPickerActivity;
import com.yijiehl.club.android.ui.activity.photo.UploadPhotoActivity;
import com.yijiehl.club.android.ui.activity.user.MineActivity;
import com.yijiehl.club.android.ui.adapter.PictureClubAdapter;
import com.yijiehl.club.android.ui.adapter.PicturePersonAdapter;

import java.util.ArrayList;
import java.util.Observable;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: PictureFragment <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/13 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.fragment_picture)
public class PictureFragment extends BaseHostFragment {
    /**
     * 标题选择
     */
    @ViewInject(R.id.rg_title)
    private RadioGroup mTitle;
    /**
     * 无照片上传容器
     */
    @ViewInject(R.id.click_uploading)
    private LinearLayout noData;
    /**
     * 无照片上传图标
     */
    @ViewInject(R.id.im_no_data)
    private View noDataImageView;
    /**
     * 无照片上传文字
     */
    @ViewInject(R.id.tv_no_data)
    private View noDataTextView;
    /**
     * 有照片上传图标
     */
    @ViewInject(R.id.iv_uploading)
    private ImageView upLoading;

    /**
     * 内容列表
     */
    @ViewInject(R.id.lv_listview)
    protected PtrListView mListView;
    /**
     * 下拉刷新容器
     */
    @ViewInject(R.id.load_more_list_view_ptr_frame)
    protected PtrClassicFrameLayout mPtrFrameLayout;

    /** 会所照片适配器 */
    private PictureClubAdapter mPictureClubAdapter;
    /** 个人照片适配器 */
    private PicturePersonAdapter mPicturePersonAdapter;

    /** 请求权限成功后回调 */
    private ReadMediaTask mReadMediaTask = new ReadMediaTask();
    private long mTaskId;

    @Nullable
    @Override
    protected View.OnClickListener getLeftBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DONE: 谌珂 2016/9/5 跳转到个人账户
                startActivity(new Intent(getActivity(), MineActivity.class));
            }
        };
    }

    @Nullable
    @Override
    protected View.OnClickListener getRightBtnClickListener() {
        return null;
    }

    @Override
    protected boolean isLeftBtnVisible() {
        ((MainActivity)getActivity()).getmLeftBtn().setText(R.string.icon_me);
        return true;
    }

    @Override
    protected boolean isRightBtnVisible() {
//        ((MainActivity)getActivity()).getmRightBtn().setModle(IconTextView.MODULE_TEXT);
//        ((MainActivity)getActivity()).getmRightBtn().setText(R.string.select);
//        if(mTitle == null) {
//            return true;
//        }
//        switch (mTitle.getCheckedRadioButtonId()) {
//            case R.id.rb_person:
//                return true;
//            default:
//                return false;
//        }
        return false;
    }

    @Override
    protected int getTitle() {
        return R.string.photo;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UploadPictureSvc.getInstance().addObserver(this);
        obtainPersonalPhoto(true);
        obtainAlbumPhoto(true);
        //初始化适配器
        mPictureClubAdapter = new PictureClubAdapter(getActivity());
        mPicturePersonAdapter = new PicturePersonAdapter(this);
        //初始化列表
        mListView.setAdapter(mPicturePersonAdapter);
        mListView.setEmptyView(noData);
        //设置上拉回调
        mListView.setLoadMoreListener(new PtrListView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                // DONE: 2016/9/6 分页请求网络并刷新数据，网络请求结束后关闭加载动画 mListView.loadComplete();
                switch (mTitle.getCheckedRadioButtonId()) {
                    case R.id.rb_person:
                        obtainPersonalPhoto(false);
                        break;
                    case R.id.rb_club:
                        // DONE: 谌珂 2016/10/19 请求会所相册
                        obtainAlbumPhoto(false);
                        break;
                }
            }
        });
        //设置下拉回调
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                View v = mListView.getChildAt(0);
                return v == null || mListView.getFirstVisiblePosition() == 0 && v.getTop() == 0;
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                switch (mTitle.getCheckedRadioButtonId()) {
                    case R.id.rb_person:
                        obtainPersonalPhoto(true);
                        break;
                    case R.id.rb_club:
                        // DONE: 谌珂 2016/10/19 请求会所相册
                        obtainAlbumPhoto(true);
                        break;
                }
            }
        });
        //切换数据
        mTitle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_person:
                        mListView.setOnItemClickListener(null);
                        mListView.setAdapter(mPicturePersonAdapter);
                        mListView.setEmptyView(noData);
                        noDataImageView.setVisibility(View.VISIBLE);
                        noDataTextView.setVisibility(View.VISIBLE);
                        if (mPicturePersonAdapter.getCount() == 0) {
                            upLoading.setVisibility(View.GONE);
                        } else {
                            upLoading.setVisibility(View.VISIBLE);
                        }
                        mPicturePersonAdapter.notifyDataSetChanged();
                        break;
                    case R.id.rb_club:
                        mListView.setOnItemClickListener(mPictureClubAdapter);
                        mListView.setAdapter(mPictureClubAdapter);
                        noDataImageView.setVisibility(View.GONE);
                        noDataTextView.setVisibility(View.GONE);
                        upLoading.setVisibility(View.GONE);
                        mPictureClubAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PhotoPickerActivity.PHOTO_PICKER_ACTIVITY && resultCode == Activity.RESULT_OK) {
            //获取选择的图片
            ArrayList<String> paths = data.getStringArrayListExtra(UploadPhotoActivity.PATH);
            if(paths == null || paths.size() == 0) {   //选择的图片为空终止
                return;
            }
            mTaskId = System.currentTimeMillis();
            ActivitySvc.startUploadPhoto(getActivity(), paths, mTaskId);
        }
    }

    /**
     * 描 述：获取个人相片<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/19 <br/>
     * @param isRefresh 是否是刷新任务
     */
    private void obtainPersonalPhoto(boolean isRefresh) {
        obtainPersonalPhoto(isRefresh, null);
    }

    /**
     * 描 述：获取个人相片<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/19 <br/>
     * @param isRefresh 是否是刷新任务
     * @param keyWord 搜索关键词，如果此参数不为空则忽略isRefresh
     */
    private void obtainPersonalPhoto(final boolean isRefresh, final String keyWord) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchPersonalPhoto(getActivity(), keyWord, (isRefresh||!TextUtils.isEmpty(keyWord))?0:mPicturePersonAdapter.getAllCount()), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                ResSearchPhotos data = (ResSearchPhotos) pResponse;

                if (isRefresh || !TextUtils.isEmpty(keyWord)) {   //如果是刷新或者搜索则完全替换数据
                    if (data.getResultList() == null || data.getResultList().size() == 0) {
                        upLoading.setVisibility(View.GONE);
                    } else {
                        upLoading.setVisibility(View.VISIBLE);

                    }
                    mPicturePersonAdapter.setData(data.getResultList());
                } else {
                    mPicturePersonAdapter.addData(data.getResultList());
                }
                mListView.loadComplete();
                if(data.getResultList().size() < 10) {
                    mListView.lockLoad(true);
                }
                mPtrFrameLayout.refreshComplete();
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                mListView.loadComplete();
                mPtrFrameLayout.refreshComplete();
            }
        });
    }

    /**
     * 描 述：获取个人相片<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/19 <br/>
     * @param isRefresh 是否是刷新任务
     */
    private void obtainAlbumPhoto(boolean isRefresh) {
        obtainAlbumPhoto(isRefresh, null);
    }

    /**
     * 描 述：获取个人相片<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/19 <br/>
     * @param isRefresh 是否是刷新任务
     * @param keyWord 搜索关键词，如果此参数不为空则忽略isRefresh
     */
    private void obtainAlbumPhoto(final boolean isRefresh, final String keyWord) {
        NetHelper.getDataFromNet(getActivity(), new ReqSearchAlbum(getActivity(), keyWord, (isRefresh||!TextUtils.isEmpty(keyWord))?0:mPictureClubAdapter.getCount()), new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchAlbums data = (RespSearchAlbums) pResponse;

                if (isRefresh || !TextUtils.isEmpty(keyWord)) {   //如果是刷新或者搜索则完全替换数据
                    mPictureClubAdapter.setDatas(data.getResultList());
                } else {
                    mPictureClubAdapter.addDatas(data.getResultList());
                }
                mListView.loadComplete();
                if(data.getResultList().size() < 10) {
                    mListView.lockLoad(true);
                }
                mPtrFrameLayout.refreshComplete();
            }

            @Override
            public void onFailed(String msg) {
                super.onFailed(msg);
                mListView.loadComplete();
                mPtrFrameLayout.refreshComplete();
            }
        });
    }

    @OnClick({R.id.click_uploading, R.id.iv_uploading})
    public void upload() {
        // DONE: 2016/9/26
        ((BmActivity)getActivity()).checkPromissions(FileUtil.createPermissions(), mReadMediaTask);
    }

    /**
     * 描 述：请求权限成功后打开图片选择<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/8 <br/>
     */
    private class ReadMediaTask implements Runnable {
        @Override
        public void run() {
            ActivitySvc.startImagePicker(PictureFragment.this, null);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        Message msg = (Message) data;
        if (observable != UploadPictureSvc.getInstance() || ObservableTag.UPLOAD_COMPLETE != msg.what) {
            return;
        }
        UploadPictureMessage lUploadPictureMessage = (UploadPictureMessage) msg.obj;
        if (mTaskId == lUploadPictureMessage.getTimestamp()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // DONE: 谌珂 2016/10/16 重新拉接口获取图片
                    obtainPersonalPhoto(true);
                    Toaster.showShortToast(getActivity(), getString(R.string.upload_complete));
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        UploadPictureSvc.getInstance().deleteObserver(this);
        super.onDestroy();
    }
}
