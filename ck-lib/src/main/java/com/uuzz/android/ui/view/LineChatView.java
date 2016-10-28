/**
 * 项目名称：手机在线 <br/>
 * 文件名称: LineChatView.java <br/>
 * <p>
 * Created by 谌珂 on 2016/10/28.  <br/>
 * Copyright 2011 北京壹平台科技有限公司. All rights reserved.[版权声明]
 */
package com.uuzz.android.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.uuzz.android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：手机在线 <br/>
 * 类  名: LineChatView <br/>
 * 类描述: <br/>
 * 实现的主要功能 <br/>
 * 版    本：1.7.3 <br/>
 * 修改时间：2016/10/28 <br/>
 * @author 谌珂 <br/>
 */
public class LineChatView extends View {

    /** 折线颜色 */
    private int mLineColor;
    /** 未选中端点颜色 */
    private int mUnSelectedPointColor;
    /** 线宽 */
    private int mLineWidth;
    /** 未选中端点半径 */
    private int mUnSelectedPointRadius;
    /** 选中端点半径 */
    private int mSelectedPointRadius;
    /** 每页端点数 */
    private int mPointCount;
    /** 是否使用贝塞尔曲线 */
    private boolean isBesselLine;


    /** 统计图值集合 */
    private List<Integer> values;


    public LineChatView(Context context) {
        this(context, null);
    }

    public LineChatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LineChatView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 描 述：初始化<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/28 <br/>
     */
    private void initView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.LineChatView);
        mLineColor = a.getColor(R.styleable.LineChatView_line_color, Color.WHITE);
        mUnSelectedPointColor = a.getColor(R.styleable.LineChatView_unselected_point_color, Color.WHITE);
        mLineWidth = a.getInt(R.styleable.LineChatView_line_width, 2);
        mUnSelectedPointRadius = a.getInt(R.styleable.LineChatView_unselected_point_radius, 8);
        mSelectedPointRadius = a.getInt(R.styleable.LineChatView_unselected_point_radius, 15);
        mPointCount = a.getInt(R.styleable.LineChatView_point_count, 7);
        isBesselLine = a.getBoolean(R.styleable.LineChatView_bessel, true);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {

    }

    public void setmLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
    }

    public void setmUnSelectedPointColor(int mUnSelectedPointColor) {
        this.mUnSelectedPointColor = mUnSelectedPointColor;
    }

    public void setmLineWidth(int mLineWidth) {
        this.mLineWidth = mLineWidth;
    }

    public void setmUnSelectedPointRadius(int mUnSelectedPointRadius) {
        this.mUnSelectedPointRadius = mUnSelectedPointRadius;
    }

    public void setmSelectedPointRadius(int mSelectedPointRadius) {
        this.mSelectedPointRadius = mSelectedPointRadius;
    }

    public void setmPointCount(int mPointCount) {
        this.mPointCount = mPointCount;
    }

    public void setBesselLine(boolean besselLine) {
        isBesselLine = besselLine;
    }

    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    public void setValues(int[] values) {
        this.values = new ArrayList<>();
        for (Integer value: values){
            this.values.add(value);
        }
    }
}
