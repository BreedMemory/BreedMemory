/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: PictureFragment.java <br/>
 * Created by 张志新 on 2016/9/13.  <br/>
 */
package com.yijiehl.club.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uuzz.android.ui.view.ptr.PtrClassicFrameLayout;
import com.uuzz.android.ui.view.ptr.PtrListView;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.innerentity.AlbumInfo;
import com.yijiehl.club.android.network.response.innerentity.PhotoInfo;
import com.yijiehl.club.android.ui.activity.PhotoPickerActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private List<AlbumInfo> dataAlbum;//相册照片数据源

    @Nullable
    @Override
    protected View.OnClickListener getLeftBtnClickListener() {
        return null;
    }

    @Nullable
    @Override
    protected View.OnClickListener getRightBtnClickListener() {
        return null;
    }

    @Override
    protected boolean isLeftBtnVisible() {
        return false;
    }

    @Override
    protected boolean isRightBtnVisible() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: 2016/9/14 首先获取数据再设置自己的设配器

        //首先联网获取List<PhotoInfo> dataPhoto ;//个人照片数据,服务器返回的
//        dataPhotoList=getList((ArrayList<PhotoInfo>) dataPhoto);
//        PicturePersonAdapter picturePersonAdapter=new PicturePersonAdapter(getActivity(),dataPhotoList);
//        mListView.setAdapter(picturePersonAdapter);
      /*  if (dataPhoto.size() == 0) {
            upLoading.setVisibility(View.INVISIBLE);
        } else {
            upLoading.setVisibility(View.VISIBLE);
        }*/
        mListView.setLoadMoreListener(new PtrListView.LoadMoreListener() {

            @Override
            public void onLoadMore() {
                // TODO: 2016/9/6 分页请求网络并刷新数据，网络请求结束后关闭加载动画 mListView.loadComplete();
            }
        });
        mListView.setEmptyView(noData);
    }

    @OnClick(R.id.tv_person)
    private void choosePerson() {
        // TODO: 2016/9/14 需要数据源切换
        mPerson.setTextColor(getResources().getColor(R.color.white));
        mPerson.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_left_pink));
        mClub.setTextColor(getResources().getColor(R.color.colorPrimary));
        mClub.setBackgroundDrawable(getResources().getDrawable(R.drawable.growup_title_right_white));
    }

    @OnClick(R.id.tv_club)
    private void chooseClub() {
        // TODO: 2016/9/14 需要数据源切换
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
            String time = String.valueOf(getTime(bean.getCreateDay()));
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
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String d = format.format(date);
        return d;
    }

    @OnClick(R.id.click_uploading)
    private void upLoading() {
        // TODO: 2016/9/26  
        startActivity(new Intent(getActivity(), PhotoPickerActivity.class));
    }
}
