/**
 * 项目名称：孕育迹忆 <br/>
 * 文件名称: MainActivity.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/9/5.  <br/>
 */
package com.yijiehl.club.android.ui.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.uuzz.android.ui.view.FootGroupBtn;
import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.SaveInstance;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchMyMessage;
import com.yijiehl.club.android.ui.activity.user.MineActivity;
import com.yijiehl.club.android.ui.adapter.HostViewPagerAdapter;

/**
 * 项目名称：孕育迹忆 <br/>
 * 类  名: MainActivity <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/9/5 <br/>
 * @author 谌珂 <br/>
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BmActivity {

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            return false;
        }
    });

    /** 内容容器 */
    @ViewInject(R.id.vp_content_container)
    private ViewPager mViewPager;
    /** 底部tab容器 */
    @ViewInject(R.id.footer)
    private LinearLayout mFootContainer;
    /** 底部tab中间的圆形背景图 */
    @ViewInject(R.id.im_middle_background)
    private ImageView mFootMiddleBackground;
    /** 照片的footbtn */
    @ViewInject(R.id.fgb_photo)
    private FootGroupBtn mFootPhotoBtn;
    /** 当前页面的索引 */
    @SaveInstance
    public int mCurrentPage;
    /** ViewPager缓存页面数目;当前页面的相邻N各页面都会被缓存 */
    private int cachePagers = 4;
    /**第一次回退时间*/
    private long touchTime = 0;

    private ObtainMyMessageTask mObtainMyMessageTask = new ObtainMyMessageTask();

    @Override
    protected String getHeadTitle() {
        return getString(R.string.host_page);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager.setCurrentItem(mCurrentPage);
        setFootFocus(mCurrentPage);
        //创建适配器
        HostViewPagerAdapter mAdapter = new HostViewPagerAdapter(getFragmentManager(), this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(mAdapter);
        mViewPager.setOffscreenPageLimit(cachePagers);
    }

    @Override
    protected void configHeadLeftView() {
        mHeadLeftContainer.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_header_touchable));
        mLeftBtn = new IconTextView(this);
        mHeadLeftContainer.addView(mLeftBtn);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mLeftBtn.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        mLeftBtn.setText(R.string.icon_me);
        mHeadLeftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MineActivity.class));
            }
        });
    }

    /**
     * 描 述：点击底部tab按钮后切换ViewPager的内容，并记录页面索引<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/5 <br/>
     */
    @OnClick({R.id.fgb_grow_up, R.id.fgb_health, R.id.fgb_host, R.id.fl_photo, R.id.fgb_question})
    private void switchContent(View v) {
        mViewPager.setCurrentItem(mFootContainer.indexOfChild(v));
    }

    /**
     * 描 述：根据索引给底部tab设置样式<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/5 <br/>
     * @param index 被选中页面的索引
     */
    public void setFootFocus(int index) {
        mCurrentPage = index;
        for (int i = 0; i < mFootContainer.getChildCount(); i++) {
            if (i == mCurrentPage) {
                if (i == 2) {          //如果当前选中的是照片模块单独修改样式
                    mFootMiddleBackground.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_foot_btn_middle_pressed));
                    mFootPhotoBtn.setFocus();
                } else {
                    ((FootGroupBtn) mFootContainer.getChildAt(i)).setFocus();
                }
            } else {
                if (i == 2) {
                    mFootMiddleBackground.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.bg_foot_btn_middle));
                    mFootPhotoBtn.setUnFocus();
                } else {
                    ((FootGroupBtn) mFootContainer.getChildAt(i)).setUnFocus();
                }
            }
        }
    }

    /**
     * 描 述：跳转到对应的fragment<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/8 <br/>
     */
    public void setCurrentPage(int index) {
        mViewPager.setCurrentItem(index);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (mCurrentPage != 0) {
                    mViewPager.setCurrentItem(0);
                    mCurrentPage=0;
                    return true;
                } else {
                    long currentTime = System.currentTimeMillis();
                    if ((currentTime - touchTime) >= 2000) {
                        Toaster.showShortToast(this,R.string.click_again);
                        touchTime = currentTime;
                        return true;
                    }
                }
        }
        return super.onKeyUp(keyCode, event);
    }

    class ObtainMyMessageTask implements Runnable {

        @Override
        public void run() {
            NetHelper.getDataFromNet(MainActivity.this, new ReqSearchMyMessage(MainActivity.this), new AbstractCallBack(MainActivity.this) {
                @Override
                public void onSuccess(AbstractResponse pResponse) {
                    mHandler.postDelayed(mObtainMyMessageTask, 30*1000);
                }
            });
        }
    }
}
