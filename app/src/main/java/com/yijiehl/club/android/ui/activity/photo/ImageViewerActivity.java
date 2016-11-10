package com.yijiehl.club.android.ui.activity.photo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.CollectPicture;
import com.yijiehl.club.android.network.request.dataproc.DeletePicture;
import com.yijiehl.club.android.svc.ShareSvc;
import com.yijiehl.club.android.ui.activity.BmActivity;
import com.yijiehl.club.android.ui.adapter.ImageViewerAdapter;
import com.yijiehl.club.android.ui.dialog.BaseDialog;
import com.yijiehl.club.android.ui.dialog.MessageDialog;

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
    public static final String CODES = "CODES";
    public static final String DESCS = "DESCS";
    public static final String INDEX = "INDEX";

    private boolean isHide;

    @ViewInject(R.id.rl_photo_bottom)
    private View mBottomContainer;
    @ViewInject(R.id.tv_tag)
    private TextView mTag;
    @ViewInject(R.id.pager)
    private ViewPager mViewPager;


    ImageViewerAdapter mAdapter;
    private ArrayList<String> urls;
    private ArrayList<String> codes;
    private ArrayList<String> descs;
    private boolean isNative = true;
    private int index;

    @Override
    protected String getHeadTitle() {
        return "照片详情";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mContentView.getLayoutParams();
        lp.addRule(RelativeLayout.BELOW, 0);
        urls = getIntent().getStringArrayListExtra(UploadPhotoActivity.PATH);
        codes = getIntent().getStringArrayListExtra(CODES);
        descs = getIntent().getStringArrayListExtra(DESCS);
        isNative = getIntent().getBooleanExtra(NATIVE, false);
        index = getIntent().getIntExtra(INDEX, 0);
        if(0 > index || urls.size() <= index) {
            index = 0;
        }
        mAdapter = new ImageViewerAdapter(this, urls, isNative, new ImageViewerAdapter.PageSelectedListener() {
            @Override
            public void onPageSelector(int position) {
                if(descs == null || TextUtils.isEmpty(descs.get(position))) {
                    mTag.setVisibility(View.GONE);
                } else {
                    mTag.setVisibility(View.VISIBLE);
                    mTag.setText(descs.get(position));
                }
            }
        });
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mAdapter);
        mViewPager.setCurrentItem(index);

        mAdapter.setListener(new View.OnClickListener() {
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

    /**
     * 描 述：分享<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/31 <br/>
     */
    @OnClick(R.id.iv_image_share)
    private void share() {
        // DONE: 谌珂 2016/10/31 分享
        ShareSvc.sharePhoto(this,urls.get(mViewPager.getCurrentItem()),descs.get(mViewPager.getCurrentItem()));
    }

    /**
     * 描 述：收藏<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/31 <br/>
     */
    @OnClick(R.id.iv_image_collect)
    private void collect() {
        // DONE: 谌珂 2016/10/31 收藏
        NetHelper.getDataFromNet(this, new ReqBaseDataProc(this, new CollectPicture(urls.get(mViewPager.getCurrentItem()))), new AbstractCallBack(this) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                Toaster.showShortToast(ImageViewerActivity.this, R.string.collect_success);
            }
        });
    }

    /**
     * 描 述：删除<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/31 <br/>
     */
    @OnClick(R.id.iv_image_delete)
    private void delete() {
        // DONE: 谌珂 2016/10/31 删除
        if (codes == null || codes.size() == 0) {
            Toaster.showShortToast(ImageViewerActivity.this, getString(R.string.can_not_delete));
            return;
        }
        MessageDialog dialog = MessageDialog.getInstance(this);
        dialog.setMessage(R.string.do_you_delete_me);
        dialog.showDoubleBtnDialog(new BaseDialog.OnBtnsClickListener() {
            @Override
            public void onLeftClickListener(View v, BaseDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onRightClickListener(View v, final BaseDialog dialog) {
                NetHelper.getDataFromNet(ImageViewerActivity.this, new ReqBaseDataProc(ImageViewerActivity.this, new DeletePicture(codes.get(mViewPager.getCurrentItem()))), new AbstractCallBack(ImageViewerActivity.this) {
                    @Override
                    public void onSuccess(AbstractResponse pResponse) {
                        mAdapter.getPaths().remove(mViewPager.getCurrentItem());
                        dialog.dismiss();
                        Toaster.showShortToast(ImageViewerActivity.this, getString(R.string.delete_success));
                        finish();
                    }
                });
            }
        });
    }
}
