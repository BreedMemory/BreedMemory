package com.yijiehl.club.android.ui.activity.photo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.entity.MediaStoreHelper;
import com.yijiehl.club.android.entity.Photo;
import com.yijiehl.club.android.entity.PhotoDirectory;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.PhotoGridItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: PhotoPickerActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/26 <br/>
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_photo_picker)
public class PhotoPickerActivity extends BmActivity {

    /**
     * 图片列表
     */
    @ViewInject(R.id.grid_photo_list)
    private GridView photoGrid;
    /**
     * 确认按钮
     */
    @ViewInject(R.id.btn_ok)
    private Button sureBtn;
    /**
     * 预览
     */
    @ViewInject(R.id.tv_prview)
    private TextView preView;
    /**
     * 照片数量
     */
    /*@ViewInject(R.id.tv_photo_num)
    private TextView photoNum;*/

    private PhotoGridItemAdapter photoGridItemAdapter;

    //private List<PhotoDirectory> photoDirectories;//相册集合
    private List<Photo> photos;//照片集合
    private ArrayList<String> dataPaths = new ArrayList<>();//所有照片路径集合

    @Override
    protected String getHeadTitle() {
        return "相册";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoGridItemAdapter = new PhotoGridItemAdapter(PhotoPickerActivity.this, null);
        ArrayList<String> paths = getIntent().getStringArrayListExtra(UploadPhotoActivity.PATH);
        if(paths != null && paths.size() > 0) {    //其他Activity传过来的已选择的图片路径
            photoGridItemAdapter.setmSelectedPhoto(paths);
        } else {
            /**获取手机中的照片*/
            getPhotos();
        }
        photoGrid.setAdapter(photoGridItemAdapter);
        photoGridItemAdapter.setOnPhotoSelectedListener(new PhotoGridItemAdapter.OnPhotoSelectedListener() {
            @Override
            public void photoClick(List<String> number) {
                if (!number.isEmpty()) {
                    preView.setTextColor(getResources().getColor(R.color.black));
                    sureBtn.setTextColor(getResources().getColor(R.color.white));
                    preView.setClickable(true);
                    sureBtn.setClickable(true);
                    sureBtn.setBackgroundResource(R.drawable.pick_photo_sure_yes);
                } else {
                    preView.setTextColor(getResources().getColor(R.color.textColorLight));
                    preView.setClickable(false);
                    sureBtn.setClickable(false);
                    sureBtn.setBackgroundResource(R.drawable.pick_photo_sure_no);
                    sureBtn.setTextColor(getResources().getColor(R.color.textColorLight));
                }
            }

            @Override
            public void takePhoto() {

            }
        });
    }

    /**
     * 描 述：利用此方法扫描手机中的所有图片<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/9/23 <br/>
     */
    private void getPhotos() {
        MediaStoreHelper.getPhotoDirs(PhotoPickerActivity.this, new MediaStoreHelper.PhotosResultCallback() {
            @Override
            public void onResultCallback(List<PhotoDirectory> directories) {
                for (int i = 0; i < directories.size(); i++) {
                    photos = directories.get(i).getPhotos();
                    final ArrayList<String> paths = new ArrayList<>();
                    for (Photo photo : photos) {
                        paths.add(photo.getPath());
                    }
                    dataPaths.addAll(paths);
                }
            }
        });
    }

    private synchronized void updateAdapterData(String path) {
        if(dataPaths == null) {
            dataPaths = new ArrayList<>();
        }
        if(dataPaths.contains(path)) {
            return;
        }
        dataPaths.add(path);
    }

    @OnClick(R.id.tv_prview)
    private void prView() {
        Intent i =new Intent(PhotoPickerActivity.this, ImageViewerActivity.class);
        i.putExtra(ImageViewerActivity.NATIVE, true);
        i.putStringArrayListExtra(UploadPhotoActivity.PATH, photoGridItemAdapter.getmSelectedPhoto());
        startActivity(i);
    }

    @OnClick(R.id.btn_ok)
    private void btnSure() {
        // DONE: 2016/9/29 需要完善页面的跳转，以及finish本activity
        Intent i =new Intent(PhotoPickerActivity.this, UploadPhotoActivity.class);
        i.putStringArrayListExtra(UploadPhotoActivity.PATH, photoGridItemAdapter.getmSelectedPhoto());
        startActivity(i);
        finish();
    }

}
