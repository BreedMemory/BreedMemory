package com.yijiehl.club.android.ui.activity.question;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.uuzz.android.util.FileUtil;
import com.uuzz.android.util.ObservableTag;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.SaveInstance;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.entity.UploadPictureMessage;
import com.yijiehl.club.android.network.request.ReqQuiz;
import com.yijiehl.club.android.network.request.upload.ReqUploadFile;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.svc.UploadPictureSvc;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.activity.photo.PhotoPickerActivity;
import com.yijiehl.club.android.ui.activity.photo.UploadPhotoActivity;
import com.yijiehl.club.android.ui.adapter.UploadImageAdapter;

import java.util.ArrayList;
import java.util.Observable;

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
    private ArrayList<String> mFilePaths;
    /**
     * 来自上传图片请求的时间戳，用于监听着返回匹配任务
     */
    @SaveInstance
    private long mTaskId;

    /** 请求权限成功后回调 */
    private ReadMediaTask mReadMediaTask = new ReadMediaTask();

    /**标识是否带有文件*/
    private boolean hasFile;


    @Override
    protected String getHeadTitle() {
        return getString(R.string.ask_question);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilePaths = getIntent().getStringArrayListExtra(UploadPhotoActivity.PATH);

        if (mTaskId == 0) {
            mTaskId = getIntent().getLongExtra(UploadPhotoActivity.TASK, 0);
        }
        mAdapter = new UploadImageAdapter(this, UploadImageAdapter.MODE_REMOTE);
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
            hasFile=true;
            mTaskId = System.currentTimeMillis();
            UploadPictureSvc.getInstance().addObserver(this);
            UploadPictureSvc
                    .getInstance()
                    .uploadMultiplePicture(getApplicationContext(),
                            ReqUploadFile.UploadType.CRM_PHOTO_DETAIL,
                            mAskContent.getText().toString(),
                            mFilePaths,
                            mTaskId);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        super.update(observable, data);
        if(observable != UploadPictureSvc.getInstance()) {
            return;
        }
        Message msg = (Message) data;
        final UploadPictureMessage lUploadPictureMessage = (UploadPictureMessage) msg.obj;
        switch (msg.what) {
            case ObservableTag.UPLOAD_SUCCESS:
                mAdapter.addData(lUploadPictureMessage.getUrl());
                break;
            case ObservableTag.UPLOAD_FAILED:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toaster.showShortToast(AskQuestionActivity.this, lUploadPictureMessage.getNativePath() + getString(R.string.upload_failed));
                    }
                });
                break;
            case ObservableTag.UPLOAD_COMPLETE:
                UploadPictureSvc.getInstance().deleteObserver(this);
                mTaskId = 0;
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(mAdapter.getItemViewType(position) != UploadImageAdapter.TYPE_ADD) {
            return;
        }
        if(mTaskId != 0) {
            Toaster.showShortToast(this, getString(R.string.uploading_picture));
            return;
        }
        checkPromissions(FileUtil.createPermissions(), mReadMediaTask);
    }

    /**
     * 描 述：请求权限成功后打开图片选择<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/8 <br/>
     */
    private class ReadMediaTask implements Runnable {
        @Override
        public void run() {
            ActivitySvc.startImagePicker(AskQuestionActivity.this, null);
        }
    }

    @OnClick(R.id.btn_release)
    private void release() {
        // TODO: 谌珂 2016/10/21 请求接口提问
        if(TextUtils.isEmpty(mAskContent.getText().toString())||mFilePaths.size()==0){
            Toaster.showShortToast(this,"请填写提问内容");
            return;
        }
        NetHelper.getDataFromNet(this, new ReqQuiz(this, mAskContent.getText().toString(), true, hasFile), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {

            }
        }, false);
    }
}
