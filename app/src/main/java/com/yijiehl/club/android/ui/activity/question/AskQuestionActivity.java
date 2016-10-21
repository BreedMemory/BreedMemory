package com.yijiehl.club.android.ui.activity.question;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.SaveInstance;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.upload.ReqUploadFile;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.svc.UploadPictureSvc;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.activity.photo.PhotoPickerActivity;
import com.yijiehl.club.android.ui.activity.photo.UploadPhotoActivity;
import com.yijiehl.club.android.ui.adapter.UploadImageAdapter;

import java.util.ArrayList;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: AskQuestionActivity<br/>
 * 类描述: <br/>
 *
 * @author 张志新 <br/>
 *         实现的主要功能<br/>
 *         版    本：1.0.0<br/>
 */
@ContentView(R.layout.activity_ask_question)
public class AskQuestionActivity extends BmActivity implements AdapterView.OnItemClickListener {

    /**
     * 提问内容
     */
    @ViewInject(R.id.et_ask_content)
    private EditText mAskContent;
    /**
     * 图片容器
     */
    @ViewInject(R.id.gv_photo_container)
    private GridView mPhotoContainer;
    /**
     * 图片适配器
     */
    UploadImageAdapter mAdapter;
    /**
     * 图片文件路径
     */
    @SaveInstance
    private ArrayList<String> mFilePaths=new ArrayList<>();
    /**
     * 来自上传图片请求的时间戳，用于监听着返回匹配任务
     */
    @SaveInstance
    private long mTaskId;


    @Override
    protected String getHeadTitle() {
        return getString(R.string.ask_question);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UploadPictureSvc.getInstance().addObserver(this);
        mFilePaths = getIntent().getStringArrayListExtra(UploadPhotoActivity.PATH);

        if (mTaskId == 0) {
            mTaskId = getIntent().getLongExtra(UploadPhotoActivity.TASK, 0);
        }
        mAdapter = new UploadImageAdapter(this);
        mAdapter.setDatas(mFilePaths);
        mPhotoContainer.setAdapter(mAdapter);
        mPhotoContainer.setOnItemClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PhotoPickerActivity.PHOTO_PICKER_ACTIVITY && resultCode == Activity.RESULT_OK) {
            //获取选择的图片
            mFilePaths = data.getStringArrayListExtra(UploadPhotoActivity.PATH);
            if(mFilePaths == null || mFilePaths.size() == 0) {   //选择的图片为空终止
                return;
            }
            mAdapter.setDatas(mFilePaths);
            mTaskId = System.currentTimeMillis();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ActivitySvc.startImagePicker(this, mFilePaths);
    }

    @OnClick(R.id.btn_release)
    private void release() {
        UploadPictureSvc
                .getInstance()
                .uploadMultiplePicture(getApplicationContext(),
                        ReqUploadFile.UploadType.KB_QUESTION_MAIN,
                        mAskContent.getText().toString(),
                        mFilePaths,
                        mTaskId);
        finish();
    }

    @Override
    public void onDestroy() {
        UploadPictureSvc.getInstance().deleteObserver(this);
        super.onDestroy();
    }
}
