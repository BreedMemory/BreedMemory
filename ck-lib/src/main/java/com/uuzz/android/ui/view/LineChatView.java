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
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;

import com.uuzz.android.R;
import com.uuzz.android.util.ScreenTools;

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


    private Paint paint = new Paint();

    /** 统计图值集合 */
    private List<Float> values;
    /** 统计图值域   下标0为最小值   下标1为最大值 */
    private float[] mRange = new float[]{0, 0};
    /** X轴相邻两个点之间的距离 */
    int xStep;
    /** 计算可以画线的实际高度 */
    int mTrueHeight;
    /** 值域差值 */
    float mRangeLength;



    private OverScroller mOverScroller;
    private VelocityTracker mVelocityTracker;
    private OnValueChanged mOnValueChangedListener;

    private boolean mIsDragging;
    private int mTouchSlop;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mLastTouchX;
    private int mActivePointerId;

    public interface OnValueChanged{
        void onValueChanged(int position, float value);
    }


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
        mLineWidth = a.getDimensionPixelOffset(R.styleable.LineChatView_line_width, 2);
        mUnSelectedPointRadius = a.getDimensionPixelOffset(R.styleable.LineChatView_unselected_point_radius, 8);
        mSelectedPointRadius = a.getDimensionPixelOffset(R.styleable.LineChatView_selected_point_radius, 15);
        mPointCount = a.getInt(R.styleable.LineChatView_point_count, 8);
        isBesselLine = a.getBoolean(R.styleable.LineChatView_bessel, true);
        a.recycle();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        if(getPaddingTop() == 0 || getPaddingBottom() == 0) {
            setPadding(0, ScreenTools.dip2px(context, 30), 0, ScreenTools.dip2px(context, 30));
        }

        mOverScroller = new OverScroller(context, new DecelerateInterpolator());
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed) {
            xStep = getWidth() / (mPointCount-1);
            mTrueHeight = getHeight() - getPaddingTop() - getPaddingBottom();
            mRangeLength = mRange[1] - mRange[0];
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mRange[1] <= mRange[0]) {      //最大值不大于最小值，什么都不画
            return;
        }
        //点I的实际X坐标
        int valueX;
        //点I的实际Y坐标
        int valueY;
        //点I-1的实际X坐标
        int perValueX;
        //点I-1的实际Y坐标
        int perValueY;
        //当前值索引
        int position = computePosition();
        for (int i = 0; i < values.size(); i++) {       //循环画点
            if(i == 0) {              //为了保证点覆盖在线上面  第i个点总是在第i+1次循环中画   最后一个点直接画
                continue;
            }
            valueX = getX(i);
            valueY = getY(i);
            perValueX = getX(i-1);
            perValueY = getY(i-1);

            //画线
            paint.setColor(mLineColor);
            paint.setStrokeWidth(mLineWidth);
            canvas.drawLine(perValueX, perValueY, valueX, valueY, paint);

            //画前一个点
            drawPoint(canvas, perValueX, perValueY, position, i-1);

            if(i == values.size()-1) {                     //到图表末尾直接画当前点
                drawPoint(canvas, valueX, valueY, position, i);
            }
        }

        //如果只有一个点只画出选中点
        if(values.size() == 1) {
            drawPoint(canvas, getX(0), getY(0), 0, 0);
        }
    }

    /**
     * 描 述：画点<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.7.3) 谌珂 2016/10/31 <br/>
     * @param canvas 画布
     * @param x 点x坐标
     * @param y 点y坐标
     * @param position 选中点的索引
     * @param i 当前画的点索引
     */
    private void drawPoint(Canvas canvas, float x, float y, int position, int i) {
        if(position == i) {
            paint.setColor(mLineColor);
            canvas.drawCircle(x, y, mSelectedPointRadius, paint);
        } else {
            paint.setColor(mLineColor);
            canvas.drawCircle(x, y, mUnSelectedPointRadius, paint);
            paint.setColor(mUnSelectedPointColor);
            canvas.drawCircle(x, y, (float) (mUnSelectedPointRadius*0.8), paint);
        }
    }

    /**
     * 描 述：获取点I的X坐标<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/29 <br/>
     */
    private int getX(int position) {
        return position * xStep;
    }

    /**
     * 描 述：获取点I的Y坐标<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/29 <br/>
     */
    private int getY(int position) {
        return  (int) (getPaddingTop() + mTrueHeight *(mRange[1] - values.get(position))/(mRangeLength));
    }

    private void requestParentDisallowInterceptTouchEvent(boolean disallowIntercept) {
        final ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(mVelocityTracker != null){
            mVelocityTracker.addMovement(event);
        }


        int action = event.getActionMasked();

        switch (action){

            case MotionEvent.ACTION_DOWN:
                int area = getWidth()/7;
                if(event.getX() < area || event.getX() > area*6) {
                    requestParentDisallowInterceptTouchEvent(false);
                } else {
                    requestParentDisallowInterceptTouchEvent(true);
                }
                if(!mOverScroller.isFinished())
                    mOverScroller.abortAnimation();

                if(mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                }else
                    mVelocityTracker.clear();

                mVelocityTracker.addMovement(event);
                mActivePointerId = event.getPointerId(0);

                mLastTouchX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:

                int deltaX = (int) (mLastTouchX - event.getX(mActivePointerId));
                if(!mIsDragging && Math.abs(deltaX) > mTouchSlop ) {
                    //do something
                    final ViewParent parent = getParent();
                    if (parent != null)
                        parent.requestDisallowInterceptTouchEvent(true);


                    if (deltaX > 0) {
                        deltaX -= mTouchSlop;
                    } else {
                        deltaX += mTouchSlop;
                    }

                    mIsDragging = true;
                }

                if(mIsDragging){
                    if(canScroll(deltaX)){
                        scrollBy(deltaX, 0);
                    }
                    mLastTouchX = (int) event.getX();
                }

                break;
            case MotionEvent.ACTION_UP:

                if(mIsDragging) {

                    mIsDragging = false;

                    final ViewParent    parent = getParent();
                    if (parent != null)
                        parent.requestDisallowInterceptTouchEvent(false);

                    mVelocityTracker.computeCurrentVelocity(700, mMaximumVelocity);
                    int velocity = (int) mVelocityTracker.getXVelocity(mActivePointerId);
                    if (Math.abs(velocity) > mMinimumVelocity) {
                        mOverScroller.fling(getScrollX(), getScrollY(), -velocity, 0, getMinimumScrollX(), getMaximumScrollX(), 0, 0, 0, 0);
                        invalidateOnAnimation();
                    } else {
                        //align item;
                        adjustItem();
                    }

                    recyclerVelocityTracker();
                }else{
                    //click event
                    int x = (int) event.getX(mActivePointerId);
                    handlerClick(x);
                }

                break;
            case MotionEvent.ACTION_CANCEL:

                if(mIsDragging){
                    adjustItem();
                    mIsDragging = false;
                }

                recyclerVelocityTracker();

                break;
        }


        return true;
    }

    /**
     * 描 述：判断是否可以滑动<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/29 <br/>
     * @param deltaX X变化量
     * @return
     */
    private boolean canScroll(int deltaX){
        int scrollX = getScrollX() + deltaX;
        int left = getMinimumScrollX();
        int right = getMaximumScrollX();

        return scrollX >= left && scrollX <= right;
    }

    /**
     * 描 述：X轴滑动的最大距离<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/29 <br/>
     */
    private int getMaximumScrollX(){
        if(values == null || values.size() == 0) {
            return 0;
        }
        return (values.size()-1) * xStep - getWidth()/2;
    }

    /**
     * 描 述：X轴滑动的最小距离<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/29 <br/>
     */
    private int getMinimumScrollX(){
        return -getWidth()/2;
    }

    /**
     * 描 述：滑动动画开始时重绘<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/29 <br/>
     */
    private void invalidateOnAnimation(){
        if(Build.VERSION.SDK_INT >= 16)
            postInvalidateOnAnimation();
        else
            invalidate();
    }

    /**
     * 描 述：释放滑动资源<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/29 <br/>
     */
    private void recyclerVelocityTracker(){
        if(mVelocityTracker != null)
            mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     * 描 述：单机点区域后滑动到此点<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/29 <br/>
     */
    private void handlerClick(int x){
        if(values == null || values.size() == 0) {
            return;
        }
        x = x + getScrollX();
        int position = x / xStep;
        if(x >= 0 && position < values.size()) {
            Rect actualLoc = getLocationByPosition(position);
            int scrollX = actualLoc.left - getScrollX();
            mOverScroller.startScroll(getScrollX(), getScrollY(), scrollX, getScrollY());
            invalidateOnAnimation();
        }
    }

    /**
     * 获取一个item位置，通过滚动正好将这个item放置在中间
     * @param position 当前选中的索引
     * @return 当前选中区域的矩形
     */
    private Rect getLocationByPosition(int position){
        int areaRadius = xStep/2;
        int scrollX = getX(position) + getMinimumScrollX();
        return new Rect(scrollX, getY(position) - areaRadius, scrollX + xStep, getY(position) + areaRadius);
    }

    /**
     * 调整item使对齐居中
     */
    private void adjustItem( ){
        if(values == null || values.size() == 0) {
            return;
        }
        int position  = computePosition();
        Rect rect = getLocationByPosition(position);
        int scrollX = rect.left - getScrollX();
        if(mOnValueChangedListener != null) {
            mOnValueChangedListener.onValueChanged(position, values.get(position));
        }

        if(scrollX != 0) {
            mOverScroller.startScroll(getScrollX(), getScrollY(), scrollX, 0);
            invalidateOnAnimation();
        }
    }

    /**
     * 计算当前显示的位置
     * @param offset 偏移量
     * @return 当前位置
     */
    private int computePosition(int offset){
        int leftOffset = getWidth() / 2;
        float scrollX = getScrollX()   + leftOffset +  offset;
        return Math.round(scrollX / xStep);
    }

    /**
     * 计算当前显示的位置
     * @return 当前位置
     */
    public int computePosition(){
        return computePosition(0);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mOverScroller.computeScrollOffset()){
            int x = mOverScroller.getCurrX();
            int y = mOverScroller.getCurrY();
            scrollTo(x, y);
            invalidate();
        }else if(!mIsDragging){
            //align item
            adjustItem();
        }
    }

    public void setLineColor(int mLineColor) {
        this.mLineColor = mLineColor;
        invalidate();
    }

    public void setUnSelectedPointColor(int mUnSelectedPointColor) {
        this.mUnSelectedPointColor = mUnSelectedPointColor;
        invalidate();
    }

    public void setLineWidth(int mLineWidth) {
        this.mLineWidth = mLineWidth;
        invalidate();
    }

    public void setUnSelectedPointRadius(int mUnSelectedPointRadius) {
        this.mUnSelectedPointRadius = mUnSelectedPointRadius;
        invalidate();
    }

    public void setSelectedPointRadius(int mSelectedPointRadius) {
        this.mSelectedPointRadius = mSelectedPointRadius;
        invalidate();
    }

    public void setPointCount(int mPointCount) {
        this.mPointCount = mPointCount;
        invalidate();
    }

    public void setBesselLine(boolean besselLine) {
        isBesselLine = besselLine;
        invalidate();
    }

    public void setOnValueChangedListener(OnValueChanged mOnValueChangedListener) {
        this.mOnValueChangedListener = mOnValueChangedListener;
    }

    public List<Float> getValues() {
        return values;
    }

    public void setValues(List<Float> values) {
        this.values = values;
        analyseValue();
    }

    public void setValues(float[] values) {
        this.values = new ArrayList<>();
        for (Float value: values){
            this.values.add(value);
        }
        analyseValue();
    }

    /**
     * 描 述：取出值域边界，放进{@link #mRange}<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/10/29 <br/>
     */
    private void analyseValue() {
        if(values == null || values.size() == 0) {
            mRange[0] = 0;
            mRange[1] = 0;
            return;
        }
        if(values.size() < mPointCount) {
            mPointCount = values.size();
        }
        mRange[0] = values.get(0);
        mRange[1] = values.get(0);
        for (Float value: values) {
            if(value < mRange[0]) {
                mRange[0] = value;
            }
            if(value > mRange[1]) {
                mRange[1] = value;
            }
        }
        invalidate();
    }
}
