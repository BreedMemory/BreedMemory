/**
 * 项目名称：手机大管家
 * 文件名称: UploadPhotoActivity.java
 * Created by 谌珂 on 2016/10/15.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.activity.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uuzz.android.ui.view.FlowLayout;
import com.uuzz.android.util.ScreenTools;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.SaveInstance;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.upload.ReqUploadFile;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.svc.UploadPictureSvc;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.activity.EditTabActivity;
import com.yijiehl.club.android.ui.adapter.UploadImageAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: UploadPhotoActivity<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
@ContentView(R.layout.activity_upload_photo_activity)
public class UploadPhotoActivity extends BmActivity implements View.OnClickListener {
    public static final int UPLOAD_PHOTO_ACTIVITY_REQUEST = 111;

    public static final String PATH = "PATH";
    public static final String TASK = "TASK";

    /** 标签容器 */
    @ViewInject(R.id.fl_tab_container)
    private FlowLayout mTabContainer;
    /** 图片容器 */
    @ViewInject(R.id.gv_photo_container)
    private GridView mPhotoContainer;
    /** 图片文件路径 */
    @SaveInstance
    private ArrayList<String> mFilePaths;
    /** 标签 */
    private List<String> mTabs;
    /** 已选中的标签 */
    private List<String> mSelectedTabs = new LinkedList<>();
    /** 来自上传图片请求的时间戳，用于坚挺着返回匹配任务 */
    private long mTaskId;

    @Override
    protected String getHeadTitle() {
        return getString(R.string.upload);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mFilePaths == null || mFilePaths.size() == 0) {
            mFilePaths = getIntent().getStringArrayListExtra(PATH);
        }
        if(mTaskId == 0) {
            mTaskId = getIntent().getLongExtra(TASK, 0);
        }
        UploadImageAdapter adapter = new UploadImageAdapter(this);
        adapter.setmDatas(mFilePaths);
        mPhotoContainer.setAdapter(adapter);
        mPhotoContainer.setOnItemClickListener(adapter);

        // TODO: 谌珂 2016/10/15 查询标签 ，成功后填充标签
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //从编辑标签页面带回来的标签
        if(requestCode == UPLOAD_PHOTO_ACTIVITY_REQUEST && resultCode == Activity.RESULT_OK) {
            if(mTabs == null) {
                mTabs = new ArrayList<>();
            }
            mTabs.add(data.getCharSequenceExtra(EditTabActivity.TAB).toString());
            fillTabs();
        }
    }

    /**
     * 描 述：填充标签<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/15 <br/>
     */
    private void fillTabs() {
        TextView tv;
        int height = ScreenTools.dip2px(this, 30);
        int color = getResources().getColor(R.color.tabColor);
        Drawable background = getResources().getDrawable(R.drawable.bg_border_tab);
        int paddingLeft = getResources().getDimensionPixelOffset(R.dimen.primary_margin);
        int textSize = getResources().getDimensionPixelSize(com.uuzz.android.R.dimen.second_text_size);
        if(mTabs != null) {
            for (String tab : mTabs) {
                tv = new TextView(this);
                mTabContainer.addView(tv);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
                layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                layoutParams.height = height;
                layoutParams.setMargins(0, 0, paddingLeft, 0);
                tv.setPadding(paddingLeft, 0, paddingLeft, 0);
                tv.setGravity(Gravity.CENTER);
                tv.setBackgroundDrawable(background);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
                tv.setTextColor(color);
                tv.setText(tab);
                tv.setOnClickListener(this);
            }
        }
        tv = new TextView(this);
        mTabContainer.addView(tv);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = height;
        layoutParams.setMargins(0, 0, paddingLeft, 0);
        tv.setPadding(paddingLeft, 0, paddingLeft, 0);
        tv.setGravity(Gravity.CENTER);
        tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_border_stroke));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSize);
        tv.setTextColor(getResources().getColor(R.color.textColorLight));
        tv.setText(R.string.add_tab);
        tv.setOnClickListener(this);
    }

    @OnClick(R.id.tv_cancel)
    private void cancel() {
        finish();
    }

    @OnClick(R.id.tv_commit)
    private void upLoad() {
        // DONE: 谌珂 2016/10/15 上传图片
        UploadPictureSvc
                .getInstance()
                .uploadMultiplePicture(getApplicationContext(),
                        ReqUploadFile.UploadType.CRM_PHOTO_DETAIL,
                        getTabs(),
                        mFilePaths,
                        mTaskId);
        finish();
    }

    private String getTabs() {
        if(mSelectedTabs.size() == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String tab : mSelectedTabs) {
            sb.append(tab).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 描 述：跳转到标签编辑页面<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/15 <br/>
     */
    @Override
    public void onClick(View v) {
        TextView tv = (TextView) v;
        String text = tv.getText().toString();
        if(TextUtils.equals(text, getString(R.string.add_tab))) {  //点击了添加标签
            ActivitySvc.startImagePicker(this, mFilePaths, mTaskId);
            finish();
            return;
        }
        if(mSelectedTabs.contains(text)) {    //已经选过的标签
            mSelectedTabs.remove(text);
            tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_border_tab_selected));
            tv.setTextColor(Color.WHITE);
        } else {
            mSelectedTabs.add(text);
            tv.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_border_tab));
            tv.setTextColor(getResources().getColor(R.color.tabColor));
        }
    }
}
