package com.yijiehl.club.android.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
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

    @ViewInject(R.id.iv_detail_show)
    private ImageView ivShow;

    private ArrayList<String> urls;

    @Override
    protected String getHeadTitle() {
        return "照片详情";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        urls=getIntent().getStringArrayListExtra("image_urls");
        ImageLoader.getInstance().displayImage("file:///" +urls.get(0),ivShow);
    }
}
