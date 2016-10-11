package com.yijiehl.club.android.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.uuzz.android.ui.view.ImageViewer;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;

import java.util.ArrayList;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: ImagePagerActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/27 <br/>
 *
 * @author 张志新 <br/>
 */
@ContentView(R.layout.activity_image_pager)
public class ImagePagerActivity extends BmActivity {
    private boolean isHide;

    @ViewInject(R.id.iv_detail_show)
    private ImageViewer ivShow;
    @ViewInject(R.id.rl_photo_bottom)
    private View mBottomContainer;

    private ArrayList<String> urls;
    private boolean isNative = true;

    @Override
    protected String getHeadTitle() {
        return "照片详情";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urls = getIntent().getStringArrayListExtra("image_urls");
        isNative=getIntent().getBooleanExtra("isNative",false);
        isNative = true;
        if(isNative) {
            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.shouye_zhaopian_bg, ivShow);
        }else{
            ImageLoader.getInstance().displayImage(urls.get(0), ivShow);
        }

        ivShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHide) {
                    mHeader.setVisibility(View.VISIBLE);
                    mBottomContainer.setVisibility(View.VISIBLE);
                    isHide = false;
                } else {
                    mHeader.setVisibility(View.GONE);
                    mBottomContainer.setVisibility(View.GONE);
                    isHide = true;
                }
            }
        });
    }
}
