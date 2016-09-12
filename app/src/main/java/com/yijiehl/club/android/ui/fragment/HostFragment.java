/**
 * 项目名称：手机大管家
 * 文件名称: HostFragment.java
 * Created by 谌珂 on 2016/9/11.
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.yijiehl.club.android.ui.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.response.UserInfo;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目名称：手机大管家<br/>
 * 类  名: HostFragment<br/>
 * 类描述: <br/>
 * @author 谌珂 <br/>
 * 实现的主要功能<br/>
 * 版    本：1.0.0<br/>
 */
@ContentView(R.layout.fragment_host)
public class HostFragment extends BaseHostFragment {
    /** 提示语的完整区域 */
    @ViewInject(R.id.ll_tip_background)
    private View mTip;
    /** 提示语的容器 */
    @ViewInject(R.id.ll_tip_container)
    private LinearLayout mTipContainer;
    /** 圆形照片 */
    @ViewInject(R.id.ci_main_picture)
    private CircleImageView mMainPicture;
    /** 会所的圆形logo */
    @ViewInject(R.id.im_club_logo)
    private ImageView mClubLogo;
    /** 建议 */
    @ViewInject(R.id.tv_advice)
    private TextView mAdvice;
    /** 活动背景图 */
    @ViewInject(R.id.im_activity_image)
    private ImageView mActivityImage;
    /** 活动名称 */
    @ViewInject(R.id.tv_activity_name)
    private ImageView mActivityName;
    /** 活动时间 */
    @ViewInject(R.id.tv_activity_time)
    private ImageView mActivityTime;
    /** 会所长logo */
    @ViewInject(R.id.im_logo_info)
    private ImageView mClubLogoInfo;


    @Nullable
    @Override
    protected View.OnClickListener getLeftBtnClickListener() {
        return null;
    }

    @Nullable
    @Override
    protected View.OnClickListener getRightBtnClickListener() {
        return null;
    }

    @Override
    protected boolean isLeftBtnVisible() {
        return false;
    }

    @Override
    protected boolean isRightBtnVisible() {
        return false;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UserInfo info = JSON.parseObject(ContextUtils.getSharedString(getActivity(), R.string.shared_preference_user_info), UserInfo.class);
        //提示语
        makeUpTip(info.getWelcomeInfo());
        //用户照片
        ImageLoader.getInstance().displayImage(info.getImageInfo(), mMainPicture);
        //会所logo
        ImageLoader.getInstance().displayImage(info.getIconInfo1(), mClubLogo);
        //会所健康建议
        mAdvice.setText(info.getBaseInfo());
    }

    /**
     * 描 述：生成提示语<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/11 <br/>
     * @param tip 接口返回的提示语
     */
    private void makeUpTip(String tip) {
        if(TextUtils.isEmpty(tip)) {        //没有提示语则不显示
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
            if(TextUtils.isEmpty(s) && m.find()) {   //如果数字在第一位则第一个字符串数组一定为空，其他情况下不可能为空
                //生成一组数字
                addNumber(m.group());
            }
            addText(s);
            if(TextUtils.isEmpty(s) && m.find()) {   //查找字符串中间的数字
                //生成一组数字
                addNumber(m.group());
            }
        }
    }

    /**
     * 描 述：生成提示短语并添加到提示语容器内<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/11 <br/>
     * @param text 提示短语
     */
    private void addText(String text) {
        TextView v = new TextView(getActivity());
        mTipContainer.addView(v);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) v.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        v.setTextSize(ScreenTools.dip2px(getActivity(), 18));
        v.setTextColor(getResources().getColor(R.color.textColorPrimary));
        v.setText(text);
    }

    /**
     * 描 述：便利字符串中的数字，产生对应图片并添加到提示语容器内<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/11 <br/>
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
        // TODO: 谌珂 2016/9/11 跳转到会所简介
    }

    @OnClick({R.id.im_collect_activity})
    private void collect(View v) {
        // TODO: 谌珂 2016/9/12 根据view id判断收藏什么元素
    }

    @OnClick({R.id.im_share_activity})
    private void share(View v) {
        // TODO: 谌珂 2016/9/12 根据view id判断分享什么元素
    }
}