/**
 * 项目名称：孕育迹忆
 * 文件名称: HostFragment.java
 * Created by 谌珂 on 2016/9/11.
 */
package com.yijiehl.club.android.ui.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.uuzz.android.ui.view.CircleImageView;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.ScreenTools;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.search.ReqSearchActivitys;
import com.yijiehl.club.android.network.response.RespSearchActivitys;
import com.yijiehl.club.android.network.response.innerentity.ActivityInfo;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.ui.activity.ActivitysActivity;
import com.yijiehl.club.android.ui.activity.ArticalDetailActivity;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.MineActivity;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

/**
 * 项目名称：孕育迹忆<br/>
 * 类  名: HostFragment<br/>
 * 类描述: <br/>
 *
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
@ContentView(R.layout.fragment_host)
public class HostFragment extends BaseHostFragment {
    /**
     * 提示语的完整区域
     */
    @ViewInject(R.id.ll_tip_background)
    private View mTip;
    /**
     * 提示语的容器
     */
    @ViewInject(R.id.ll_tip_container)
    private LinearLayout mTipContainer;
    /**
     * 圆形照片
     */
    @ViewInject(R.id.ci_main_picture)
    private CircleImageView mMainPicture;
    /**
     * 会所的圆形logo
     */
    @ViewInject(R.id.im_club_logo)
    private ImageView mClubLogo;
    /**
     * 建议
     */
    @ViewInject(R.id.tv_advice)
    private TextView mAdvice;
    /**
     * 活动背景图
     */
    @ViewInject(R.id.im_activity_background)
    private ImageView mActivityImage;
    /**
     * 活动名称
     */
    @ViewInject(R.id.tv_activity_name)
    private TextView mActivityName;
    /**
     * 活动时间
     */
    @ViewInject(R.id.tv_activity_time)
    private TextView mActivityTime;
    /**
     * 会所长logo
     */
    @ViewInject(R.id.im_logo_info_activity)
    private ImageView mClubLogoInfoActivity;
    /**
     * 会所长logo
     */
    @ViewInject(R.id.im_logo_info_question)
    private ImageView mClubLogoInfoQuestion;
    /**
     * 问答的问题
     */
    @ViewInject(R.id.tv_question_name)
    private TextView mQuestion;
    /**
     * 成长文章标题
     */
    @ViewInject(R.id.tv_grow_up_title)
    private TextView mGrowUpTitle;
    /**
     * 成长文章描述
     */
    @ViewInject(R.id.tv_grow_up_desc)
    private TextView mGrowUpDesc;
    /**
     * 消息提示icon
     */
    @ViewInject(R.id.tv_message_tip_icon)
    private TextView mMessageTipIcon;

    /**
     * 活动信息
     */
    private ActivityInfo mActivityInfo;
    /**
     * 请求活动信息的特征码
     */
    private String mDataCacheActivitys;


    @Nullable
    @Override
    protected View.OnClickListener getLeftBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DONE: 谌珂 2016/9/5 跳转到个人账户
                startActivity(new Intent(getActivity(), MineActivity.class));
            }
        };
    }

    @Nullable
    @Override
    protected View.OnClickListener getRightBtnClickListener() {
        return null;
    }

    @Override
    protected boolean isLeftBtnVisible() {
        ((MainActivity)getActivity()).getmLeftBtn().setText(R.string.icon_me);
        return true;
    }

    @Override
    protected boolean isRightBtnVisible() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //构建查询活动内容的Request
        ReqSearchActivitys lReqSearchActivitys = new ReqSearchActivitys(getActivity(), true);
        mDataCacheActivitys = NetHelper.createObjectName(lReqSearchActivitys);
        //查询活动内容
        NetHelper.getDataFromNet(getActivity(), lReqSearchActivitys, new AbstractCallBack(getActivity()) {
            @Override
            public void onSuccess(AbstractResponse pResponse) {
                RespSearchActivitys activity = (RespSearchActivitys) pResponse;
                fillActivityInfo(activity);
            }
        });
        UserInfo info = JSON.parseObject(ContextUtils.getSharedString(getActivity(), R.string.shared_preference_user_info), UserInfo.class);
        //提示语
        makeUpTip(info.getWelcomeInfo());
        //用户照片
        if (TextUtils.isEmpty(info.getImageInfo())) {
            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.test_main_image, mMainPicture);
        } else {
            ImageLoader.getInstance().displayImage(ActivitySvc.createResourceUrl(getActivity(), info.getImageInfo()), mMainPicture);
        }

        //会所logo
        if (TextUtils.isEmpty(info.getIconInfo2())) {
            mClubLogo.setVisibility(View.GONE);
        } else {
            ImageLoader.getInstance().displayImage(ActivitySvc.createResourceUrl(getActivity(), info.getIconInfo1()), mClubLogo);
        }

        //会所长logo 活动模块
        if (TextUtils.isEmpty(info.getIconInfo1())) {
            mClubLogoInfoActivity.setVisibility(View.INVISIBLE);
        } else {
            ImageLoader.getInstance().displayImage(ActivitySvc.createResourceUrl(getActivity(), info.getIconInfo2()), mClubLogoInfoActivity);
        }

        //会所长logo 问答模块
        if (TextUtils.isEmpty(info.getIconInfo1())) {
            mClubLogoInfoQuestion.setVisibility(View.INVISIBLE);
        } else {
            ImageLoader.getInstance().displayImage(ActivitySvc.createResourceUrl(getActivity(), info.getIconInfo2()), mClubLogoInfoQuestion);
        }
        //会所健康建议
        mAdvice.setText(info.getBaseInfo());

        mGrowUpDesc.setText(Html.fromHtml(getString(R.string.grow_up_gas_station)));
    }

    /**
     * 描 述：填充活动信息<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/14 <br/>
     */
    private synchronized void fillActivityInfo(RespSearchActivitys activity) {
        if (activity != null && activity.getResultList() != null && activity.getResultList().size() > 0) {
            mActivityInfo = activity.getResultList().get(0);
        } else {
            return;
        }
        if(TextUtils.isEmpty(mActivityInfo.getImageInfo())) {
            ImageLoader.getInstance().displayImage("drawable://" + R.drawable.shouye_huodong_bg, mActivityImage);
        } else {
            ImageLoader.getInstance().displayImage(ActivitySvc.createResourceUrl(getActivity(), mActivityInfo.getImageInfo()), mActivityImage);
        }

        mActivityName.setText(mActivityInfo.getDataName());
        mActivityTime.setText(mActivityInfo.getStartTimeStr());
    }

    /**
     * 描 述：生成提示语<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/11 <br/>
     *
     * @param tip 接口返回的提示语
     */
    private void makeUpTip(String tip) {
        if (TextUtils.isEmpty(tip)) {        //没有提示语则不显示
            mTip.setVisibility(View.GONE);
        } else {
            mTip.setVisibility(View.VISIBLE);
        }
        mTipContainer.removeAllViews();
        String regular = "\\d";
        String[] split = tip.split(regular);   //分离出汉字数列
        Pattern pattern = Pattern.compile(regular);
        Matcher m = pattern.matcher(tip);      //创建正则匹配对象
        for (String s : split) {   //循环汉字数列
            if (TextUtils.isEmpty(s) && m.find()) {   //如果数字在第一位则第一个字符串数组一定为空，其他情况下不可能为空
                //生成一组数字
                addNumber(m.group());
            }
            addText(s);
            if (TextUtils.isEmpty(s) && m.find()) {   //查找字符串中间的数字
                //生成一组数字
                addNumber(m.group());
            }
        }
    }

    /**
     * 描 述：生成提示短语并添加到提示语容器内<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/11 <br/>
     *
     * @param text 提示短语
     */
    private void addText(String text) {
        TextView v = new TextView(getActivity());
        mTipContainer.addView(v);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        v.setTextSize(COMPLEX_UNIT_DIP, 18);
        v.setTextColor(getResources().getColor(R.color.textColorPrimary));
        v.setText(text);
    }

    /**
     * 描 述：便利字符串中的数字，产生对应图片并添加到提示语容器内<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/11 <br/>
     *
     * @param numbers 只包含数字的字符串
     */
    private void addNumber(String numbers) {
        int resId;
        Field field;
        ImageView v;
        try {
            Class cls = Class.forName("com.yijiehl.club.android.R$drawable");
            for (int i = 0; i < numbers.length(); i++) {
                field = cls.getDeclaredField("number_" + numbers.charAt(i));
                resId = (int) field.get(null);
                v = new ImageView(getActivity());
                mTipContainer.addView(v);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v.getLayoutParams();
                layoutParams.width = ScreenTools.dip2px(getActivity(), 21);
                layoutParams.height = ScreenTools.dip2px(getActivity(), 26);
                layoutParams.setMargins(ScreenTools.dip2px(getActivity(), 2), 0, 0, 0);
                v.setImageBitmap(BitmapFactory.decodeResource(getResources(), resId));
            }
        } catch (Exception e) {
            logger.e("parse number tip error!", e);
        }
    }

    @OnClick(R.id.im_logo)
    private void startWebView() {
        // TODO: 谌珂 2016/9/11 跳转到会所简介 第一次进app跳转到会所选择
    }

    @OnClick({R.id.im_collect_activity, R.id.im_collect_grow_up, R.id.im_collect_photo})
    private void collect(View v) {
        // TODO: 谌珂 2016/9/12 根据view id判断收藏什么元素
        Toaster.showShortToast(getActivity(), "收藏成功");
        // TODO: 谌珂 2016/9/19 测试代码
//        final File file = new File("/sdcard/Pictures/1453448257206.jpg");
//        UploadPicture ipload = new UploadPicture(file);
//        ReqBaseDataProc proc = new ReqBaseDataProc(getActivity(), ipload);
//        NetHelper.getDataFromNet(getActivity(), proc, new AbstractCallBack(getActivity()) {
//            @Override
//            public void onSuccess(AbstractResponse pResponse) {
//                BaseResponse data = (BaseResponse)pResponse;
//                data.getReturnMsg().getResultCode();
//                ReqUploadFile uploadFile = new ReqUploadFile(getActivity(), CRM_PHOTO_DETAIL, file);
//                NetHelper.getDataFromNet(getActivity(), uploadFile, null);
//            }
//        });
    }

    @OnClick({R.id.im_share_activity, R.id.im_share_grow_up, R.id.im_share_photo, R.id.im_share_question})
    private void share(View v) {
        // TODO: 谌珂 2016/9/12 根据view id判断分享什么元素
        Toaster.showShortToast(getActivity(), "分享成功");
    }

    @Override
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {
        if (TextUtils.equals(mDataCacheActivitys, pCacheDataEntity.getmName()) && mActivityInfo == null) {
            fillActivityInfo(JSON.parseObject(pCacheDataEntity.getmData(), RespSearchActivitys.class));
        }
    }

    @OnClick({R.id.ci_main_picture,R.id.tv_advice})
    private void skipHealthFragment(){
        ((MainActivity)getActivity()).setCurrentPage(1);
    }

    @OnClick(R.id.ll_photo_container)
    private void skipAlbumFragment() {
        ((MainActivity)getActivity()).setCurrentPage(2);
    }

    @OnClick(R.id.im_question_background)
    private void skipAskAnswerFragment() {
        ((MainActivity)getActivity()).setCurrentPage(3);
    }

    @OnClick(R.id.im_grow_up_background)
    private void skipGrowUpFragment() {
        ((MainActivity)getActivity()).setCurrentPage(4);
    }


    @OnClick(R.id.im_activity_background)
    private void toActicitys() {
        startActivity(new Intent(getActivity(), ActivitysActivity.class));
    }

    @OnClick(R.id.tv_activity_name)
    private void toDetailActivitys() {
        // TODO: 2016/10/6 此处临时跳转一固定问题解答页面，后期要根据具体问题跳转到具体解答页面
        Intent intent = new Intent(getActivity(), ArticalDetailActivity.class);
        intent.putExtra("url", "http://biz.yijiehulian.com/showpgclfybiz.htm?clfy=activity_main&dd=XXXXXXXXX&bd=showdetail");
        startActivity(intent);
    }

    @OnClick(R.id.tv_question_name)
    private void toAnswerQue() {
        // TODO: 2016/10/6 此处临时跳转一固定问题解答页面，后期要根据具体问题跳转到具体解答页面
        Intent intent = new Intent(getActivity(), ArticalDetailActivity.class);
        intent.putExtra("url", "http://biz.yijiehulian.com/showpgclfybiz.htm?clfy=kb_article_main&dd=XXXXXXXXX&bd=showdetail");
        startActivity(intent);
    }

    @OnClick(R.id.tv_grow_up_title)
    private void toDetailGrow(){
        // TODO: 2016/10/6 此处临时跳转一固定问题解答页面，后期要根据具体问题跳转到具体解答页面
        Intent intent = new Intent(getActivity(), ArticalDetailActivity.class);
        intent.putExtra("url","http://biz.yijiehulian.com/showpgclfybiz.htm?clfy=kb_growup_main&dd=XXXXXXXXX&bd=showdetail");
        startActivity(intent);
    }
}
