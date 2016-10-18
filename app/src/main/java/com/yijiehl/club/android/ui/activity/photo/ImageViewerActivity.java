package com.yijiehl.club.android.ui.activity.photo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.ImageViewerAdapter;

import java.util.ArrayList;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ImageViewerActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/27 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_image_viewer_layout)
public class ImageViewerActivity extends BmActivity {

    public static final String NATIVE = "Native";

    private boolean isHide;

    @ViewInject(R.id.rl_photo_bottom)
    private View mBottomContainer;
    @ViewInject(R.id.pager)
    private ViewPager mViewPager;

    private ArrayList<String> urls;
    private boolean isNative = true;

    @Override
    protected String getHeadTitle() {
        return "照片详情";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urls = getIntent().getStringArrayListExtra(UploadPhotoActivity.PATH);
        isNative=getIntent().getBooleanExtra(NATIVE, false);
        ImageViewerAdapter adapter = new ImageViewerAdapter(this, urls, isNative);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(adapter);

//        ivShow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isHide) {
//                    mHeader.setVisibility(View.VISIBLE);
//                    mBottomContainer.setVisibility(View.VISIBLE);
//                    isHide = false;
//                } else {
//                    mHeader.setVisibility(View.GONE);
//                    mBottomContainer.setVisibility(View.GONE);
//                    isHide = true;
//                }
//            }
//        });
    }
}
