/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PictureFragment.java <br/>
 * Created by 张志新 on 2016/9/13.  <br/>
 */
package com.yijiehl.club.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uuzz.android.ui.view.ptr.PtrClassicFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrDefaultHandler;
import com.uuzz.android.ui.view.ptr.PtrFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrListView;
import com.uuzz.android.util.FileUtil;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.AlbumInfo;
import com.yijiehl.club.android.network.response.innerentity.PhotoInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.svc.UploadPictureSvc;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.user.MineActivity;
import com.yijiehl.club.android.ui.adapter.PictureClubAdapter;
import com.yijiehl.club.android.ui.adapter.PicturePersonAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
     * 个人照片
     */
    @ViewInject(R.id.tv_person)
    private TextView mPerson;
    /**
     * 会所照片
     */
    @ViewInject(R.id.tv_club)
    private TextView mClub;
    /**
     * 无照片上传图标
     */
    @ViewInject(R.id.click_uploading)
    private LinearLayout noData;
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

    private List<PhotoInfo> dataPhoto;//个人照片数据,服务器返回的
    private List<List<PhotoInfo>> dataPhotoList;//个人照片数据源
    private List<AlbumInfo> dataAlbum;//会所相册照片数据源
    /** 请求权限成功后回调 */
    private StartTask mStartTask = new StartTask();
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
        ((BmActivity)getActivity()).checkPromissions(FileUtil.createPermissions(), mStartTask);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((BmActivity)getActivity()).checkPromissions(FileUtil.createPermissions(), mStartTask);
    }

    /**
     * 描 述：请求权限成功后回调<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/8 <br/>
     */
    private class StartTask implements Runnable {

        @Override
        public void run() {
            dataPhoto = new ArrayList<>();
            dataAlbum =new ArrayList<>();
            // TODO: 2016/9/14 首先获取数据再设置自己的设配器
            //首先联网获取List<PhotoInfo> dataPhoto ;//个人照片数据,服务器返回的

            addPersonFakeData();

            if (dataPhoto.size() == 0) {
                upLoading.setVisibility(View.INVISIBLE);
            } else {
                upLoading.setVisibility(View.VISIBLE);
            }

            dataPhotoList = getList((ArrayList<PhotoInfo>) dataPhoto);
            PicturePersonAdapter picturePersonAdapter = new PicturePersonAdapter(getActivity(), dataPhotoList);
            mListView.setAdapter(picturePersonAdapter);

            mListView.setLoadMoreListener(new PtrListView.LoadMoreListener() {

                @Override
                public void onLoadMore() {
                    // TODO: 2016/9/6 分页请求网络并刷新数据，网络请求结束后关闭加载动画 mListView.loadComplete();
                    mListView.loadComplete();
                }
            });
            mListView.setEmptyView(noData);

            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // TODO: 2016/10/3 需要查看相册的详细照片
                    Toaster.showShortToast(getActivity(),"会所相册查看暂未实现");
                }
            });
            mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {

                @Override
                public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                    return false;
                }

                @Override
                public void onRefreshBegin(PtrFrameLayout frame) {
                    mPtrFrameLayout.refreshComplete();
                }
            });
        }
    }

    @OnClick({R.id.click_uploading, R.id.iv_uploading})
    private void noDataUpLoading() {
        // DONE: 2016/9/26
        mTaskId = System.currentTimeMillis();
        ActivitySvc.startImagePicker(getActivity(), null, mTaskId);
    }

    @OnClick(R.id.tv_person)
    private void choosePerson() {
        // TODO: 2016/9/14 需要数据源切换
        addPersonFakeData();
        if (dataPhoto.size() == 0) {
            upLoading.setVisibility(View.INVISIBLE);
        } else {
            upLoading.setVisibility(View.VISIBLE);
        }

        dataPhotoList = getList((ArrayList<PhotoInfo>) dataPhoto);
        PicturePersonAdapter picturePersonAdapter = new PicturePersonAdapter(getActivity(), dataPhotoList);
        mListView.setAdapter(picturePersonAdapter);

        mPerson.setTextColor(getResources().getColor(R.color.white));
        mPerson.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_left_pink));
        mClub.setTextColor(getResources().getColor(R.color.colorPrimary));
        mClub.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_right_white));
    }

    @OnClick(R.id.tv_club)
    private void chooseClub() {
        // TODO: 2016/9/14 需要数据源切换
        upLoading.setVisibility(View.GONE);
        addClubFakeData();
        PictureClubAdapter pictureClubAdapter=new PictureClubAdapter(getActivity(),dataAlbum);
        mListView.setAdapter(pictureClubAdapter);

        mPerson.setTextColor(getResources().getColor(R.color.colorPrimary));
        mPerson.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_left_white));
        mClub.setTextColor(getResources().getColor(R.color.white));
        mClub.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_right_pink));
    }

    /**
     * 描 述：根据服务器返回的数据，获取list的数据源<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/9/16 <br/>
     *
     * @param photoList 要排序的集合
     * @return List<List<PhotoInfo>>代表返回的数据源
     */
    //按照时间日期分类，前提保证先按照绝对的时间顺序排序一次（服务器返回的是最近时间排好的，如果不是，自己根据createDay遍历一遍）
    private List<List<PhotoInfo>> getList(ArrayList<PhotoInfo> photoList) {

        ArrayList<PhotoInfo> templist = photoList;
        List<List<PhotoInfo>> lists = new ArrayList<List<PhotoInfo>>();
        String tempTime = "";
        while (templist.size() > 0) {
            PhotoInfo bean = templist.get(0);
            //String time = String.valueOf(getTime(bean.getCreateDay()));
            //String time=getTime(bean.getCreateDay());
            String time = bean.getCreateDay();
            if (!time.equals(tempTime)) {
                List<PhotoInfo> list = new ArrayList<PhotoInfo>();
                list.add(bean);
                lists.add(list);
            } else {
                lists.get(lists.size() - 1).add(bean);
            }
            tempTime = time;
            templist.remove(bean);
        }
        return lists;
    }

    public String getTime(String time) {
        Date date = new Date(Long.parseLong(time));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(date);
        return d;
    }

    /**
     * 临时添加个人假数据的方法
     */
    private void addPersonFakeData() {
        for (int i = 0; i < 1; i++) {
            addData();
        }
        for (int i = 0; i < 1; i++) {
            addData();
        }
    }

    /**
     * 临时添加会所假数据的方法
     */
    private void addClubFakeData() {
        for (int i = 0; i < 2; i++) {
            AlbumInfo albumInfo1 = new AlbumInfo();
            albumInfo1.setDataNum("24");
            albumInfo1.setCreateTime("2016-10-02");
            albumInfo1.setDataDesc("相册1");
            albumInfo1.setIconInfo1("http://pn.680.com/news/2012-05/2012051910421844_.jpg");
            dataAlbum.add(albumInfo1);
            AlbumInfo albumInfo2 = new AlbumInfo();
            albumInfo2.setDataNum("36");
            albumInfo2.setCreateTime("2016-10-03");
            albumInfo2.setDataDesc("相册2");
            albumInfo2.setIconInfo1("http://www.vnbaby.cn/uploadfile/2013/0121/20130121093919299.jpg");
            dataAlbum.add(albumInfo2);
            AlbumInfo albumInfo3 = new AlbumInfo();
            albumInfo3.setDataNum("12");
            albumInfo3.setCreateTime("2016-10-10");
            albumInfo3.setDataDesc("相册2");
            albumInfo3.setIconInfo1("http://imgx.xiawu.com/xzimg/i4/i7/T1UW9VXi4sXXa4zfs__105950.jpg");
            dataAlbum.add(albumInfo3);
        }
    }

    /**添加假数据；临时*/
    private void addData(){
        PhotoInfo photoInfo = new PhotoInfo();
        photoInfo.setIconInfo1("http://imgx.xiawu.com/xzimg/i4/i7/T1UW9VXi4sXXa4zfs__105950.jpg");
        photoInfo.setAlbumId("kb");
        photoInfo.setAlbumName("逗你玩");
        photoInfo.setCreateDay("2016-10-03");
        PhotoInfo photoInfo1 = new PhotoInfo();
        photoInfo1.setIconInfo1("http://i2.s2.dpfile.com/pc/9f9d763bdeea2976024a3b0abced9788(700x700)/thumb.jpg");
        photoInfo1.setAlbumId("kb");
        photoInfo1.setAlbumName("逗你玩");
        photoInfo1.setCreateDay("2016-10-03");
        PhotoInfo photoInfo2 = new PhotoInfo();
        photoInfo2.setIconInfo1("http://www.vnbaby.cn/uploadfile/2013/0121/20130121093919299.jpg");
        photoInfo2.setAlbumId("kb");
        photoInfo2.setAlbumName("逗你玩");
        photoInfo2.setCreateDay("2016-10-03");
        PhotoInfo photoInfo3 = new PhotoInfo();
        photoInfo3.setIconInfo1("http://pn.680.com/news/2012-05/2012051910421844_.jpg");
        photoInfo3.setAlbumId("kb");
        photoInfo3.setAlbumName("逗你玩");
        photoInfo3.setCreateDay("2016-10-03");
        dataPhoto.add(photoInfo);
        dataPhoto.add(photoInfo1);
        dataPhoto.add(photoInfo2);
        dataPhoto.add(photoInfo3);
    }

    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        Message msg = (Message) data;
        if (UploadPictureSvc.UPLOAD_COMPLETE == msg.what) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // TODO: 谌珂 2016/10/16 重新拉接口获取图片
                    Toaster.showShortToast(getActivity(), "上传完成");
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
