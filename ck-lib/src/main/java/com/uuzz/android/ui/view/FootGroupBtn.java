/**
 * 项目名称：手机大管家 <br/>
 * 文件名称: FootGroupBtn.java <br/>
 * <p/>
 * Created by 谌珂 on 2016/1/11.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.uuzz.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uuzz.android.R;

/**
 * 项目名称：手机大管家 <br/>
 * 类  名: FootGroupBtn <br/>
 * 类描述: 应用大厅底部组合button<br/>
 * 实现的主要功能 <br/>
 * 版    本：1.0.0 <br/>
 * 修改时间：2016/1/11 <br/>
 * @author 谌珂 <br/>
 */
public class FootGroupBtn extends RelativeLayout {

    private Context mContext;

    /** 图标内容 */
    private String mIconValue;
    /** 文字内容 */
    private String mTextValue;
    /** 内容颜色 */
    private int mValueColor;
    /** 内容获取焦点后颜色 */
    private int mValueFocusColor;
    /** 背景失去焦点后颜色 */
    private int mBackgroundColor;
    /** 背景获取焦点后颜色 */
    private int mBackgroundFocusColor;
    /** 提示图标颜色 */
    private int mTipColor;
    /** 图标大小 */
    private int mIconSize;
    /** 文字大小 */
    private int mTextSize;
    /** 图标高度 */
    private int mIconHeight;
    /** 文字高度 */
    private int mTextHeight;

    private IconTextView mIconTextView;
    private TextView mTextView;

    public FootGroupBtn(Context context) {
        this(context, null);
    }

    public FootGroupBtn(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FootGroupBtn(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomAttributes(context, attrs);
        initView();
    }

    public void setTextValue(String mTextValue) {
        this.mTextValue = mTextValue;
    }

    public void setIconValue(String mIconValue) {
        this.mIconValue = mIconValue;
    }

    public void setTextValue(@StringRes int strId) {
        this.mTextValue = getContext().getString(strId);
    }

    public void setIconValue(@StringRes int strId) {
        this.mIconValue = getContext().getString(strId);
    }

    /**
     * 描 述：设置自定义属性<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/12 注释 <br/>
     */
    private void setCustomAttributes(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray a = mContext.obtainStyledAttributes(attrs,
                com.uuzz.android.R.styleable.FootGroupBtn);
        configStyle(a);
    }

    /**
     * 描 述：设置控件样式<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/5 <br/>
     */
    public void setAppearance(@StyleRes int resId) {
        TypedArray a = mContext.obtainStyledAttributes(resId,
                com.uuzz.android.R.styleable.FootGroupBtn);
        configStyle(a);
    }

    private void configStyle(TypedArray a) {
        setGravity(Gravity.CENTER);
        mIconValue = a.getString(R.styleable.FootGroupBtn_icon_value);
        mTextValue = a.getString(R.styleable.FootGroupBtn_text_value);
        mValueColor = a.getColor(R.styleable.FootGroupBtn_value_color, Color.WHITE);
        mValueFocusColor = a.getColor(R.styleable.FootGroupBtn_value_color_focus, mContext.getResources().getColor(R.color.colorPrimary));
        mBackgroundFocusColor = a.getColor(R.styleable.FootGroupBtn_background_color_focus, mContext.getResources().getColor(R.color.colorPrimary));
        mBackgroundColor = a.getColor(R.styleable.FootGroupBtn_background_color, mContext.getResources().getColor(R.color.colorPrimary));
        mTipColor = a.getColor(R.styleable.FootGroupBtn_tip_color, mContext.getResources().getColor(R.color.colorPrimary));
        mIconSize = a.getDimensionPixelOffset(R.styleable.FootGroupBtn_icon_size, 22);
        mTextSize = a.getDimensionPixelOffset(R.styleable.FootGroupBtn_text_size, 22);
        mIconHeight = a.getDimensionPixelOffset(R.styleable.FootGroupBtn_icon_height, 48);
        mTextHeight = a.getDimensionPixelOffset(R.styleable.FootGroupBtn_text_height, 28);
        a.recycle();
    }

    private void initView() {


        //生成图标布局
        mIconTextView = new IconTextView(mContext);
        mIconTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mIconSize);
        mIconTextView.setTextColor(mValueColor);
        mIconTextView.setText(mIconValue);
        mIconTextView.setGravity(Gravity.CENTER);

        //生成文字布局
        mTextView = new TextView(mContext);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        mTextView.setTextColor(mValueColor);
        mTextView.setText(mTextValue);
        mTextView.setGravity(Gravity.CENTER);
        mTextView.setId(R.id.foot_icon_view);

        //添加进RelativeLayout
        addView(mIconTextView);
        addView(mTextView);

        //设置icon宽高和摆放位置
        LayoutParams layoutParams = (LayoutParams) mIconTextView.getLayoutParams();
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = mIconHeight;
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layoutParams.addRule(RelativeLayout.ABOVE, R.id.foot_icon_view);

        //设置text宽高和摆放位置
        layoutParams = (LayoutParams) mTextView.getLayoutParams();
        layoutParams.width = LayoutParams.WRAP_CONTENT;
        layoutParams.height = mTextHeight;
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        setBackgroundColor(mBackgroundColor);
    }

    /**
     * 描 述：设置获取焦点后的样子<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/12 注释 <br/>
     */
    public void setFocus() {
        mIconTextView.setTextColor(mValueFocusColor);
        mTextView.setTextColor(mValueFocusColor);
        setBackgroundColor(mBackgroundFocusColor);
        mIconTextView.invalidate();
        mTextView.invalidate();
    }

    /**
     * 描 述：设置丢失焦点后的样子<br/>
     * 作者：谌珂<br/>
     * 历 史: (版本) 谌珂 2016/1/12 注释 <br/>
     */
    public void setUnFocus() {
        mIconTextView.setTextColor(mValueColor);
        mTextView.setTextColor(mValueColor);
        mIconTextView.invalidate();
        mTextView.invalidate();
        setBackgroundColor(mBackgroundColor);
    }

}
