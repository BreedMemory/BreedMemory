package com.yijiehl.club.android.ui.activity.question;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.FileUtil;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.SaveInstance;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.CreateQuestion;
import com.yijiehl.club.android.network.request.upload.ReqUploadFile;
import com.yijiehl.club.android.network.response.base.BaseResponse;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
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
public class AskQuestionActivity extends BmActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

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
     * 警示框手机号
     */
    @ViewInject(R.id.tv_dialog_phone)
    private TextView mPhone;
    /**
     * 图片适配器
     */
    UploadImageAdapter mAdapter;
    /**
     * 来自上传图片请求的时间戳，用于监听着返回匹配任务
     */
    @SaveInstance
    private long mTaskId;

    @SaveInstance
    private UserInfo userInfo;

    /**
     * 会所手机号
     */
    @ViewInject(R.id.tv_ask_phone)
    private TextView mClubPhone;

    /**
     * 请求权限成功后回调
     */
    private ReadMediaTask mReadMediaTask = new ReadMediaTask();


    @Override
    protected String getHeadTitle() {
        return getString(R.string.question);
    }

    @Override
    protected void configHeadRightView() {
        mRightBtn = new IconTextView(this);
        mHeadRightContainer.addView(mRightBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRightBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mRightBtn.setText(getString(R.string.submit));
        mRightBtn.setModle(IconTextView.MODULE_TEXT);

        mHeadRightContainer.setOnClickListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (mTaskId == 0) {
            mTaskId = getIntent().getLongExtra(UploadPhotoActivity.TASK, 0);
        }
        mAdapter = new UploadImageAdapter(this);
        mPhotoContainer.setAdapter(mAdapter);
        mPhotoContainer.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        CacheDataDAO.getInstance(null).getCacheDataAsync(ContextUtils.getSharedString(this, R.string.shared_preference_user_id),
                getString(R.string.shared_preference_user_info));
    }

    @Override
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {
        if (TextUtils.equals(getString(R.string.shared_preference_user_info), pCacheDataEntity.getmName())) {
            fillInfoList(JSON.parseObject(pCacheDataEntity.getmData(), UserInfo.class));
        }
    }

    /**
     * 描 述：填充个人信息列表<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 张志新 2016/10/23 <br/>
     */
    private void fillInfoList(UserInfo info) {
        userInfo = info;
        mClubPhone.setText(info.getCustServicePhone());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoPickerActivity.PHOTO_PICKER_ACTIVITY && resultCode == Activity.RESULT_OK) {
            //获取选择的图片
            ArrayList<String> filePaths = data.getStringArrayListExtra(UploadPhotoActivity.PATH);
            if (filePaths == null || filePaths.size() == 0) {   //选择的图片为空终止
                return;
            }
            mAdapter.addDatas(filePaths);
            mTaskId = System.currentTimeMillis();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mAdapter.getItemViewType(position) != UploadImageAdapter.TYPE_ADD) {
            return;
        }
        if (mTaskId != 0) {
            Toaster.showShortToast(this, getString(R.string.uploading_picture));
            return;
        }
        checkPromissions(FileUtil.createPermissions(), mReadMediaTask);
    }

    @Override
    public void onClick(View v) {
        // DONE: 谌珂 2016/10/21 请求接口提问
        if (TextUtils.isEmpty(mAskContent.getText().toString())) {
            Toaster.showShortToast(this, "请填写提问内容");
        }

        NetHelper.getDataFromNet(this, new ReqBaseDataProc(this, new CreateQuestion(mAskContent.getText().toString(), mAdapter.getCount() > 1)), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                if (mAdapter.getCount() > 1) {
                    BaseResponse data = (BaseResponse) pResponse;
                    UploadPictureSvc
                            .getInstance()
                            .uploadMultiplePicture(getApplicationContext(),
                                    ReqUploadFile.UploadType.CRM_PHOTO_DETAIL,
                                    mAskContent.getText().toString(),
                                    mAdapter.getDatas(),
                                    System.currentTimeMillis(),
                                    data.getReturnMsg().getResultCode());
                }
                //提问成功，回到我的问题页面
                Intent intent = new Intent(AskQuestionActivity.this, QuestionListActivity.class);
                intent.putExtra(QuestionListActivity.TYPE, QuestionListActivity.MY);
                startActivity(intent);
                finish();

            }
        }, false);
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

    @OnClick(R.id.tv_ask_phone)
    private void call() {
        ActivitySvc.call(this, userInfo.getCustServicePhone());
    }

}
