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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.uuzz.android.ui.view.CircleImageView;
import com.uuzz.android.ui.view.IconTextView;
import com.uuzz.android.util.ContextUtils;
import com.uuzz.android.util.ScreenTools;
import com.uuzz.android.util.Toaster;
import com.uuzz.android.util.database.dao.CacheDataDAO;
import com.uuzz.android.util.database.entity.CacheDataEntity;
import com.uuzz.android.util.ioc.annotation.ContentView;
import com.uuzz.android.util.ioc.annotation.OnClick;
import com.uuzz.android.util.ioc.annotation.ViewInject;
import com.uuzz.android.util.net.NetHelper;
import com.uuzz.android.util.net.response.AbstractResponse;
import com.uuzz.android.util.net.task.AbstractCallBack;
import com.yijiehl.club.android.R;
import com.yijiehl.club.android.network.request.ReqSensitize;
import com.yijiehl.club.android.network.request.base.ReqBaseDataProc;
import com.yijiehl.club.android.network.request.dataproc.CollectArticle;
import com.yijiehl.club.android.network.request.dataproc.CollectPicture;
import com.yijiehl.club.android.network.response.RespSensitize;
import com.yijiehl.club.android.network.response.innerentity.MyMessage;
import com.yijiehl.club.android.network.response.innerentity.UserInfo;
import com.yijiehl.club.android.svc.ActivitySvc;
import com.yijiehl.club.android.svc.ShareSvc;
import com.yijiehl.club.android.ui.activity.ActivitysActivity;
import com.yijiehl.club.android.ui.activity.BreedMemoryActivity;
import com.yijiehl.club.android.ui.activity.MainActivity;
import com.yijiehl.club.android.ui.activity.user.MineActivity;
import com.yijiehl.club.android.ui.activity.user.MyMessageActivity;

import java.lang.reflect.Field;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * 消息提醒
     */
    @ViewInject(R.id.rl_message_remind)
    private RelativeLayout mMessageRemind;
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
    private ImageView mActivityImageBackground;
    /**
     * 照片背景图
     */
    @ViewInject(R.id.im_photo_background)
    private ImageView mPhotoImageBackground;
    /**
     * 照片第一张图
     */
    @ViewInject(R.id.iv_photo_1)
    private ImageView mPhotoImage1;
    /**
     * 照片第二张图
     */
    @ViewInject(R.id.iv_photo_2)
    private ImageView mPhotoImage2;
    /**
     * 照片第三张图
     */
    @ViewInject(R.id.iv_photo_3)
    private ImageView mPhotoImage3;
    /**
     * 问答背景图
     */
    @ViewInject(R.id.im_question_background)
    private ImageView mQuestionImageBackground;
    /**
     * 成长背景图
     */
    @ViewInject(R.id.im_grow_up_background)
    private ImageView mGrowUpImageBackground;
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
     * 首页附加数据
     */
    private List<UserInfo.MainDataEntity> mMainDataEntitys;
    /**
     * 用户信息
     */
    private UserInfo mUserInfo;
    private MyMessage mMessage;
    /** 刷新任务 */
    private RefreshTask mRefreshTask = new RefreshTask();

    private HashMap<UserInfo.MainDataType, UserInfo.MainDataEntity> mEntitys = new HashMap<>();

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
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivitySvc.startGasStation(getActivity(), mUserInfo);
            }
        };
    }

    @Override
    protected boolean isLeftBtnVisible() {
        ((MainActivity)getActivity()).getmLeftBtn().setText(R.string.icon_me);
        return true;
    }

    @Override
    protected boolean isRightBtnVisible() {
        ((MainActivity)getActivity()).getmRightBtn().setModle(IconTextView.MODULE_ICON);
        ((MainActivity)getActivity()).getmRightBtn().setText(R.string.icon_gas_station);
        return true;
    }

    @Override
    protected int getTitle() {
        return R.string.host_page;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.with(this).load(R.drawable.shouye_chengzhang_bg).dontAnimate().into(mGrowUpImageBackground);
        Glide.with(this).load(R.drawable.shouye_wenda_bg2).dontAnimate().into(mQuestionImageBackground);
    }

    @Override
    public void onStart() {
        super.onStart();
        // DONE: 谌珂 2016/10/15 替换为从数据库取数据
        CacheDataDAO.getInstance(null).getCacheDataAsync(ContextUtils.getSharedString(getActivity(),
                R.string.shared_preference_user_id), getString(R.string.shared_preference_user_info));
    }

    /**
     * 描 述：刷新首页数据任务，每分钟执行一次<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/11/10 <br/>
     */
    private class RefreshTask implements Runnable {
        @Override
        public void run() {
            if(getActivity() == null) {
                return;
            }
            NetHelper.getDataFromNet(getActivity(), new ReqSensitize(getActivity()), new AbstractCallBack(getActivity()) {
                @Override
                public void onSuccess(AbstractResponse pResponse) {
                    RespSensitize data = (RespSensitize) pResponse;
                    fillActivity(ActivitySvc.saveClientInfoNative(getActivity(), data, null));
                }
            }, false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && mUserInfo != null) {  //页面隐藏则清除刷新任务
            mRefreshTask.run();
        }
    }

    /**
     * 描 述：填充用户信息<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/14 <br/>
     */
    private void fillActivity(UserInfo info) {
        long t = System.currentTimeMillis();
        mUserInfo = info;
        //提示语
        makeUpTip(info.getWelcomeInfo());
        logger.v("fill makeUpTip time is " + (System.currentTimeMillis() - t));
        t = System.currentTimeMillis();

        //会所logo
        if (TextUtils.isEmpty(info.getIconInfo2())) {
            mClubLogo.setVisibility(View.GONE);
        } else {
            Glide.with(this).load(ActivitySvc.createResourceUrl(getActivity(), info.getIconInfo2())).dontAnimate().into(mClubLogo);
        }

        //会所长logo 活动模块
        if (TextUtils.isEmpty(info.getIconInfo1())) {
            mClubLogoInfoActivity.setVisibility(View.GONE);
        } else {
            Glide.with(this).load(ActivitySvc.createResourceUrl(getActivity(), info.getIconInfo1())).dontAnimate().into(mClubLogoInfoActivity);
        }

        //会所长logo 问答模块
        if (TextUtils.isEmpty(info.getIconInfo1())) {
            mClubLogoInfoQuestion.setVisibility(View.GONE);
        } else {
            Glide.with(this).load(ActivitySvc.createResourceUrl(getActivity(), info.getIconInfo1())).dontAnimate().into(mClubLogoInfoQuestion);
        }

        mMainDataEntitys = JSON.parseArray(info.getMainDataList(), UserInfo.MainDataEntity.class);
        logger.v("fill common time is " + (System.currentTimeMillis() - t));
        t = System.currentTimeMillis();
        fillExtraInfo();
        logger.v("fill extra time is " + (System.currentTimeMillis() - t));
    }

    /**
     * 描 述：填充额外信息<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/15 <br/>
     */
    private void fillExtraInfo() {
        mEntitys.clear();
        for (UserInfo.MainDataEntity entity: mMainDataEntitys) {
            long t = System.currentTimeMillis();
            switch (UserInfo.MainDataType.setValue(entity.getType())) {
                case HEALTHINFO:
                    //会所健康建议
                    mAdvice.setText(entity.getValue());
                    mUserInfo.setBaseInfo(entity.getValue());
                    break;
                case RECOMMACTIVITY:
                    mActivityName.setText(entity.getName());
                    mActivityTime.setText(entity.getDesc());
                    entity.setValue(ActivitySvc.createWebUrl(entity.getValue()));
                    mEntitys.put(UserInfo.MainDataType.RECOMMACTIVITY, entity);
                    break;
                case RECOMMQUESTION:
                    mQuestion.setText(entity.getName());
                    entity.setValue(ActivitySvc.createWebUrl(entity.getValue()));
                    mEntitys.put(UserInfo.MainDataType.RECOMMQUESTION, entity);
                    break;
                case RECOMMGROWUP:
                    mGrowUpTitle.setText(entity.getName());
                    entity.setValue(ActivitySvc.createWebUrl(entity.getValue()));
                    mEntitys.put(UserInfo.MainDataType.RECOMMGROWUP, entity);
                    break;
                case ACCTAMOUNT:
                    mUserInfo.setCustAmount(formatMoneyNum(entity.getValue()));
                    mGrowUpDesc.setText(Html.fromHtml(String.format(getString(R.string.grow_up_gas_station), mUserInfo.getCustAmount())));
                    break;
                case IMAGECOVER:
                    //用户照片
                    Glide.with(this).load(ActivitySvc.createResourceUrl(getActivity(), entity.getValue())).dontAnimate().error(R.drawable.test_main_image).into(mMainPicture);
                    break;
                case ACTIVITYCOVER:
                    //活动照片
                    Glide.with(this).load(ActivitySvc.createResourceUrl(getActivity(), entity.getValue())).dontAnimate().placeholder(R.drawable.shouye_huodong_bg).error(R.drawable.shouye_huodong_bg).into(mActivityImageBackground);
                    break;
                case ALBUMCOVER:
                    //照片背景
                    mEntitys.put(UserInfo.MainDataType.ALBUMCOVER, entity);
                    Glide.with(this).load(ActivitySvc.createResourceUrl(getActivity(), entity.getValue())).error(R.drawable.shouye_zhaopian_bg).dontAnimate().into(mPhotoImageBackground);
                    break;
                case ALBUMITEM1:
                    //照片1
                    if(!TextUtils.isEmpty(entity.getValue())) {
                        Glide.with(this).load(ActivitySvc.createResourceUrl(getActivity(), entity.getValue())).dontAnimate().into(mPhotoImage1);
                    }
                    break;
                case ALBUMITEM2:
                    //照片2
                    Glide.with(this).load(ActivitySvc.createResourceUrl(getActivity(), entity.getValue())).dontAnimate().into(mPhotoImage2);
                    break;
                case ALBUMITEM3:
                    //照片3
                    Glide.with(this).load(ActivitySvc.createResourceUrl(getActivity(), entity.getValue())).dontAnimate().into(mPhotoImage3);
                    break;
                case CUSTSERVICEPHONE:
                    //保存会所电话
                    mUserInfo.setCustServicePhone(entity.getValue());
                    break;
                case CHILDINFO:
                    //保存宝宝信息
                    if(TextUtils.isEmpty(entity.getValue())) {
                        break;
                    }
                    if(mUserInfo.getChildrenInfo() == null) {
                        mUserInfo.setChildrenInfo(new ArrayList<UserInfo.MainDataEntity>());
                    } else {
                        mUserInfo.getChildrenInfo().clear();
                    }
                    String names[] = entity.getName().split(",");
                    String values[] = entity.getValue().split(",");
                    String descs[] = entity.getDesc().split(",");
                    for (int i = 0; i < values.length; i++) {
                        String name = null;
                        String desc = null;
                        if(i < names.length) {
                            name = names[i];
                        }
                        if(i < descs.length) {
                            desc = descs[i];
                        }
                        mUserInfo.getChildrenInfo().add(new UserInfo.MainDataEntity(desc, name, values[i]));
                    }
                    break;
            }
            logger.v("fill extra " + entity.getType() + " time is " + (System.currentTimeMillis() - t));
        }
        ActivitySvc.saveUserInfoNativeAsunc(getActivity(), mUserInfo);
    }
    /**
     * 描 述：数字货币格式化<br/>
     * 作 者：张志新<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/31 <br/>
     */

    private String formatMoneyNum(String num){
        NumberFormat nf = NumberFormat.getInstance();
        return nf.format(Double.valueOf(num));
    }
    /**
     * 描 述：生成提示语<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/11 <br/>
     *
     * @param tip 接口返回的提示语
     */
    private void makeUpTip(final String tip) {
        if (TextUtils.isEmpty(tip)) {        //没有提示语则不显示
            mTip.setVisibility(View.GONE);
            return;
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
            if (!TextUtils.isEmpty(s) && m.find()) {   //查找字符串中间的数字
                //生成一组数字
                addNumber(m.group());
            }
        }
        mTip.setVisibility(View.VISIBLE);
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

    @OnClick({R.id.im_club_logo,R.id.im_logo_info_activity,R.id.im_logo_info_question})
    private void startWebView() {
        // DONE: 谌珂 2016/9/11 跳转到会所简介 第一次进app跳转到会所选择
        ActivitySvc.startArticle(this, true,
                mUserInfo.getCustServiceUrl(getActivity()),
                mUserInfo.getOrgInfo(), null, null, mUserInfo.getOrgInfo());
    }

    @OnClick({R.id.im_collect_activity, R.id.im_collect_grow_up, R.id.im_collect_photo})
    private void collect(View v) {
        // DONE: 谌珂 2016/9/12 根据view id判断收藏什么元素
        //Toaster.showShortToast(getActivity(), R.string.collect_success);
        switch (v.getId()) {
            case R.id.im_collect_activity:
                UserInfo.MainDataEntity entity;
                entity = mEntitys.get(UserInfo.MainDataType.RECOMMACTIVITY);
                CollectArticle temp = new CollectArticle(entity.getName(),null,null,entity.getValue(),null);
                NetHelper.getDataFromNet(getActivity(), new ReqBaseDataProc(getActivity(), temp), new AbstractCallBack(getActivity()) {
                    @Override
                    public void onSuccess(AbstractResponse pResponse) {
                        Toaster.showShortToast(getActivity(), getActivity().getString(R.string.collect_success));
                    }
                });
                break;
            case R.id.im_collect_grow_up:
                entity = mEntitys.get(UserInfo.MainDataType.RECOMMGROWUP);
                CollectArticle req = new CollectArticle(entity.getName(),null,null,entity.getValue(),null);
                NetHelper.getDataFromNet(getActivity(), new ReqBaseDataProc(getActivity(), req), new AbstractCallBack(getActivity()) {
                    @Override
                    public void onSuccess(AbstractResponse pResponse) {
                        Toaster.showShortToast(getActivity(), getActivity().getString(R.string.collect_success));
                    }
                });
                break;
            case R.id.im_collect_photo:
                entity = mEntitys.get(UserInfo.MainDataType.ALBUMCOVER);
                NetHelper.getDataFromNet(getActivity(), new ReqBaseDataProc(getActivity(), new CollectPicture(ActivitySvc.createResourceUrl(getActivity(), entity.getValue()))), new AbstractCallBack(getActivity()) {
                    @Override
                    public void onSuccess(AbstractResponse pResponse) {
                        Toaster.showShortToast(getActivity(), R.string.collect_success);
                    }
                });
                break;
        }
    }

    @OnClick({R.id.im_share_activity, R.id.im_share_grow_up, R.id.im_share_photo, R.id.im_share_question})
    private void share(View v) {
        switch (v.getId()) {
            case R.id.im_share_activity:
                UserInfo.MainDataEntity entity;
                entity = mEntitys.get(UserInfo.MainDataType.RECOMMACTIVITY);
                ShareSvc.shareUrl(getActivity(), entity.getValue(),
                        entity.getName(),
                        entity.getDesc());
                break;
            case R.id.im_share_grow_up:
                entity = mEntitys.get(UserInfo.MainDataType.RECOMMGROWUP);
                ShareSvc.shareUrl(getActivity(), entity.getValue(),
                        entity.getName(),
                        entity.getDesc());
                break;
            case R.id.im_share_question:
                entity = mEntitys.get(UserInfo.MainDataType.RECOMMQUESTION);
                ShareSvc.shareUrl(getActivity(), entity.getValue(),
                        entity.getName(),
                        entity.getDesc());
                break;
            case R.id.im_share_photo:
                entity = mEntitys.get(UserInfo.MainDataType.ALBUMCOVER);
                ShareSvc.sharePhoto(getActivity(), ActivitySvc.createResourceUrl(getActivity(), entity.getValue()),
                        entity.getName());
                break;
        }
    }

    @Override
    protected void onReceiveCacheData(CacheDataEntity pCacheDataEntity) {
        if (TextUtils.equals(getString(R.string.shared_preference_user_info), pCacheDataEntity.getmName())) {
            fillActivity(JSON.parseObject(pCacheDataEntity.getmData(), UserInfo.class));
        }
    }

    @OnClick(R.id.ci_main_picture)
    private void skipBreedMemory(){
        startActivity(new Intent(getActivity(), BreedMemoryActivity.class));
    }

    @OnClick({R.id.tv_message_tip_icon,R.id.tv_message_tip})
    private void shipMyMessage(){
        mMessageRemind.setVisibility(View.GONE);
        startActivity(new Intent(getActivity(), MyMessageActivity.class));
    }
    @OnClick(R.id.tv_advice)
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
        // DONE: 2016/10/6 此处临时跳转一固定问题解答页面，后期要根据具体问题跳转到具体解答页面
        ActivitySvc.startArticle(this, true,
                mEntitys.get(UserInfo.MainDataType.RECOMMACTIVITY).getValue(),
                mActivityName.getText().toString(), null, null, null);
    }

    @OnClick(R.id.tv_question_name)
    private void toAnswerQue() {
        // DONE: 2016/10/6 此处临时跳转一固定问题解答页面，后期要根据具体问题跳转到具体解答页面
        ActivitySvc.startArticle(this, true,
                mEntitys.get(UserInfo.MainDataType.RECOMMQUESTION).getValue(),
                mQuestion.getText().toString(), null, null, null);
    }

    @OnClick(R.id.tv_grow_up_title)
    private void toDetailGrow(){
        // DONE: 2016/10/6 此处临时跳转一固定问题解答页面，后期要根据具体问题跳转到具体解答页面
        ActivitySvc.startArticle(this, true,
                mEntitys.get(UserInfo.MainDataType.RECOMMGROWUP).getValue(),
                mGrowUpTitle.getText().toString(), null, null, null);
    }
    @OnClick({R.id.im_gas_station,R.id.tv_grow_up_desc})
    private void toGasStation(){
        ActivitySvc.startGasStation(getActivity(), mUserInfo);
    }
    @OnClick(R.id.im_photo_background)
    private void lookPhoto(){
        UserInfo.MainDataEntity entity = mEntitys.get(UserInfo.MainDataType.ALBUMCOVER);
        if(TextUtils.equals(entity.getValue(), "")) {
            return;
        }
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> codes = new ArrayList<>();
        ArrayList<String> descs = new ArrayList<>();
        list.add(!(entity.getValue().startsWith("http")) ? ActivitySvc.createWebUrl(entity.getValue()):entity.getValue());
        codes.add(entity.getName());
        descs.add(entity.getName());
        ActivitySvc.startImageViewer(getActivity(), list, codes, descs, false, 0);
    }

    public void setmMessage(MyMessage mMessage) {
        this.mMessage = mMessage;
        showMessage();
    }

    private void showMessage() {
        mMessageRemind.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.rl_message_remind)
    private void cancelMessage(){
        mMessageRemind.setVisibility(View.GONE);
    }
}
