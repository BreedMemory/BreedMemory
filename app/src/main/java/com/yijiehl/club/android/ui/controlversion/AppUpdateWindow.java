package com.yijiehl.club.android.ui.controlversion;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.uuzz.android.util.FileUtil;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.net.HttpFactory;
import com.uuzz.android.util.net.httpcore.BaseHttp;
import com.uuzz.android.util.net.httpcore.RequestParams;
import com.uuzz.android.util.net.response.base.ResponseContent;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.common.Common;
import com.yijiehl.club.android.network.response.innerentity.ClientVersionInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.view.TasksCompletedView;

/**
 * @author LuoJ
 * @date 2015-2-28
 * @package com.xingcomm.android.videoconference.base.activity -- AppUpdate.java
 * @Description 应用更新
 */
public class AppUpdateWindow extends Activity implements View.OnClickListener{
    public static final int APP_UPDATE_WINDOW = 542;

    private volatile boolean isUpdating;

    public static final String VERSION_INFO = "versionInfo";

    private ClientVersionInfo versionInfo;

    private TasksCompletedView tcv;

    private Button btnUpdate,btnCancel;

    private DetailPopupWindow detailPopupWindow;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    public void initView() {
		setContentView(R.layout.window_app_update);
        versionInfo= (ClientVersionInfo) getIntent().getSerializableExtra(VERSION_INFO);
        tcv= (TasksCompletedView) findViewById(R.id.tcv);
        tcv.setDefaultText(getString(R.string.tx_new_version));
        tcv.setSubhead(getString(R.string.tx_view_details));
        tcv.setCompletedText(getString(R.string.tx_install));
        tcv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail();
            }
        });
        btnUpdate= (Button) findViewById(R.id.btn_update);
        btnUpdate.setOnClickListener(this);
        btnCancel= (Button) findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(this);
        if(versionInfo.forceFlag == 1){
            btnCancel.setVisibility(View.GONE);
        }
        //
        detailPopupWindow=new DetailPopupWindow(this);
        if(null!=versionInfo)detailPopupWindow.setDetail(versionInfo.releaseInfo);
	}

    public void process() {
        final ScaleAnimation sa =new ScaleAnimation(
                0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        sa.setDuration(400);
        sa.setFillAfter(true);
        tcv.startAnimation(sa);
        AlphaAnimation aa=new AlphaAnimation(0f,1.0f);
        aa.setDuration(800);
        aa.setFillAfter(true);
        btnUpdate.startAnimation(aa);
        if(View.VISIBLE==btnCancel.getVisibility()){
            btnCancel.startAnimation(aa);
        }
    }

    public void showDetail(){
        detailPopupWindow.showAtScreenCenter();
    }

    public void dismissDetail(){
        detailPopupWindow.dismiss();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btn_update && !isUpdating){
            isUpdating = true;
            AlphaAnimation aa=new AlphaAnimation(1.0f,0f);
            aa.setDuration(800);
            aa.setFillAfter(true);
            findViewById(R.id.layout_btn).startAnimation(aa);
            dismissDetail();
            process();
            final String path = FileUtil.getRootFilePath() + versionInfo.fileName;
            RequestParams req = new RequestParams<>("http://" + Common.SERVICE_URL + "versiondw.htm?" + "ct=android&dwid="+ versionInfo.downCode + "&inc=0"
                    , null, null, null, -1, true, path, true);
            HttpFactory.getHttpHelper(HttpFactory.DOWNLOAD_DATA).httpRequest(req, new BaseHttp.HttpRequestListener() {
                @Override
                public void doInMainThread(ResponseContent result) {
                    if(result == null) {
                        Toaster.showShortToast(AppUpdateWindow.this, getString(R.string.update_failed));
                        return;
                    }
                    ActivitySvc.installApk(AppUpdateWindow.this, path);
                }

                @Override
                public void onCancelled() {}

                @Override
                public void updateProgress(final int progress) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tcv.setProgress(progress);
                        }
                    });
                }
            });
        }else if(v.getId()==R.id.btn_cancel){
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if(versionInfo.forceFlag == 0){
            super.onBackPressed();
        }
    }

    public class DetailPopupWindow extends BasePopDialog{
        public DetailPopupWindow(Context context) {
            super(context);
        }
        @Override
        public int setContentLayout() {
            return R.layout.pop_app_update_detail;
        }
        private TextView tvDetail;
        @Override
        public void initView(View rootView) {
            tvDetail= (TextView) rootView.findViewById(R.id.tv_detail);
            tvDetail.setMovementMethod(ScrollingMovementMethod.getInstance());
            animationStyle=R.style.app_update_detail_anim;
            rootView.findViewById(R.id.layout_root).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
        public void setDetail(String text){
            if (!TextUtils.isEmpty(text))tvDetail.setText(Html.fromHtml(text));
        }
    }
}
