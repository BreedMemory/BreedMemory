package com.yijiehl.club.android.ui.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

    private final static int SCAN_OK = 1;

    private ProgressDialog progressDialog;//进度条

    //private List<PhotoDirectory> photoDirectories;//相册集合
    private List<Photo> photos;//照片集合
    private List<String> dataPaths = new ArrayList<String>();//所有照片路径集合


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    progressDialog.dismiss();
                    photoGridItemAdapter = new PhotoGridItemAdapter(PhotoPickerActivity.this, dataPaths);
                    photoGrid.setAdapter(photoGridItemAdapter);
                    photoGridItemAdapter.setOnPhotoSelectedListener(new PhotoGridItemAdapter.OnPhotoSelectedListener() {
                        @Override
                        public void photoClick(List<String> number) {
                            if (!number.isEmpty()) {
                                // TODO: 2016/9/26 需要设置字体及背景
                                preView.setTextColor(Color.BLUE);
                                preView.setClickable(true);
                                sureBtn.setClickable(true);
                            } else {
                                preView.setTextColor(Color.BLACK);
                                preView.setClickable(false);
                                sureBtn.setClickable(false);
                            }
                        }

                        @Override
                        public void takePhoto() {

                        }
                    });
                    //photoNum.setText(String.valueOf(dataPaths.size()));
                    break;
            }
        }
    };

    @Override
    protected String getHeadTitle() {
        return "相册";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**获取手机中的照片*/
        getPhotos();

    }

    /**
     * 描 述：利用此方法扫描手机中的所有图片<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/9/23 <br/>
     */
    private void getPhotos() {

        progressDialog = ProgressDialog.show(this, null, "正在加载...");

        MediaStoreHelper.getPhotoDirs(PhotoPickerActivity.this, new MediaStoreHelper.PhotosResultCallback() {
            @Override
            public void onResultCallback(List<PhotoDirectory> directories) {
                for (int i = 0; i < directories.size(); i++) {
                    photos = directories.get(i).getPhotos();
                    for (Photo photo : photos) {
                        dataPaths.add(photo.getPath());
                    }
                }
                mHandler.sendEmptyMessage(SCAN_OK);
            }
        });
    }

    @OnClick(R.id.tv_prview)
    private void prView() {

    }

    @OnClick(R.id.btn_ok)
    private void btnSure() {

    }

}
