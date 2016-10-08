package com.yijiehl.club.android.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.widget.OverScroller;

import com.yijiehl.club.android.R;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Arrays;
import java.util.List;


public class NumberPickerView extends View {

    private Paint   mTextPaint;

    private int mMinValue;

    private int mMaxValue;

    private int mPageSize;

    private int mLastTouchY;


    private int mActivePointerId;

    private int[] mSelector;

    private OverScroller mOverScroller;
    private VelocityTracker mVelocityTracker;

    private boolean mIsDragging;
    private int mTouchSlop;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    /** 是否画左边线 */
    private boolean isDrawLeftLine;
    /** 是否画右边线 */
    private boolean isDrawRightLine;
    private Rect mItemRect = new Rect();
    /** 额外文字描述 */
    private String mTextExtra;
    /** 额外文字描述 */
    private List<String> mTextExtras;
    @ColorInt
    private int mTextColorNormal;

    @ColorInt
    private int mTextColorSelected;


    private Reference<OnValueChanged> mCallbackRef;

    public NumberPickerView(Context context) {
        this(context, null);
    }

    public NumberPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NumberPickerView);
        int mTextSize = array.getDimensionPixelOffset(R.styleable.NumberPickerView_numberTextSize, 22);
        mMaxValue = array.getInt(R.styleable.NumberPickerView_maxValue, 0);
        mMinValue = array.getInt(R.styleable.NumberPickerView_minValue, 0);
        mPageSize = array.getInt(R.styleable.NumberPickerView_numberPageSize, 5);
        mTextColorNormal = array.getColor(R.styleable.NumberPickerView_numberColorNormal, Color.GREEN);
        mTextColorSelected = array.getColor(R.styleable.NumberPickerView_numberColorSelected, Color.RED);
        isDrawLeftLine = array.getBoolean(R.styleable.NumberPickerView_drawLeftLine, false);
        isDrawRightLine = array.getBoolean(R.styleable.NumberPickerView_drawRightLine, false);
        mTextExtra = array.getString(R.styleable.NumberPickerView_extra_desc);
        array.recycle();

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStyle(Paint.Style.STROKE);
        createSelector();

        mOverScroller = new OverScroller(context, new DecelerateInterpolator());
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
    }

    /**
     * 描 述：创建数据集<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/6 <br/>
     */
    private void createSelector() {
        if(mMinValue < mMaxValue){
            mSelector = new int[mMaxValue - mMinValue + 1];
            for(int  selectorIndex = mMinValue; selectorIndex <= mMaxValue; selectorIndex++){
                mSelector[selectorIndex - mMinValue] = selectorIndex;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mSelector == null || mSelector.length < 1)
            return;

        int width = getWidth();
        int height = getHeight();

        int itemHeight = getItemHeight();
        int textHeight = computeTextHeight();
        int centerY = getScrollY() + height / 2;
        int selectedPos = computePosition();
        if(selectedPos > mSelector.length-1) {             //当前选择位置已经超过最大位置
            smoothScrollToValue(mSelector[mSelector.length-1]);
            selectedPos = mSelector.length-1;
        }
        int half = mPageSize / 2 + 1;   //去除已选择的
        int itemIndex = selectedPos > half ? selectedPos - half : 0;
        int itemEnd = (selectedPos + half ) <= mSelector.length ? (selectedPos + half) : mSelector.length;
        for( ; itemIndex < itemEnd; itemIndex++){

            mItemRect.set(0, itemIndex * itemHeight, width, itemIndex * itemHeight + itemHeight);
           // canvas.drawRect(itemRect, mTextPaint);

            if(itemIndex == selectedPos){
                mTextPaint.setColor(mTextColorSelected);
                mTextPaint.setColor(mTextColorSelected);
            }else{
                mTextPaint.setColor(mTextColorNormal);
            }


            /*
                越靠近中间的文字越大。
                distance / (height / 2f) 算出的是递增的0-1之间的。
                1f - distance / (height / 2f) 将值变为递减1-0之间 。

                distance乘0.5可以确保scale不小于0.5
             */
            float distance = Math.abs(mItemRect.centerY() - centerY) * 0.8f;
            float scale = 1f - distance / (height / 2f) ;
            int  alpha = (int) (scale * 255);


            mTextPaint.setAlpha(alpha);
            canvas.save();
//            canvas.scale(scale, scale, itemRect.centerX(),  pivotY);
            int y = (mItemRect.top + mItemRect.bottom - textHeight) /2;
            String content;
            if(mTextExtras == null) {
                content = mSelector[itemIndex] + mTextExtra;
            } else {
                content = mTextExtras.get(itemIndex);
            }
            canvas.drawText(content, mItemRect.width() / 2 , y , mTextPaint);
            canvas.restore();
        }

        mTextPaint.setColor(Color.BLACK);
        canvas.drawLine(0, centerY - itemHeight/2, width, centerY - itemHeight/2, mTextPaint);
        canvas.drawLine(0, centerY + itemHeight/2, width, centerY + itemHeight/2, mTextPaint);

        if(isDrawLeftLine) {
            canvas.drawLine(0, centerY - itemHeight/2, 0, centerY + itemHeight/2, mTextPaint);
        }
        if(isDrawRightLine) {
            canvas.drawLine(width, centerY - itemHeight/2, width, centerY + itemHeight/2, mTextPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        ViewGroup.LayoutParams lp = getLayoutParams();
        if(lp == null)
            lp = new ViewGroup.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT,  ViewGroup.LayoutParams.WRAP_CONTENT);

        int width =  calculateSize(getSuggestedMinimumWidth(), lp.width, widthMeasureSpec);
        int height =  calculateSize(getSuggestedMinimumHeight(), lp.height, heightMeasureSpec);

        width += getPaddingLeft() + getPaddingRight();
        height += getPaddingTop() + getPaddingBottom();


        setMeasuredDimension(width, height);
    }

    private int calculateSize(int suggestedSize, int paramSize, int measureSpec) {
        int result = 0;
        int size = MeasureSpec.getSize(measureSpec);
        int mode = MeasureSpec.getMode(measureSpec);

        switch (MeasureSpec.getMode(mode)){
            case MeasureSpec.AT_MOST:

                if(paramSize == ViewGroup.LayoutParams.WRAP_CONTENT)
                    result = Math.min(suggestedSize, size);
                else if(paramSize == ViewGroup.LayoutParams.MATCH_PARENT)
                    result = size;
                else {
                    result = Math.min(paramSize, size);
                }

                break;
            case MeasureSpec.EXACTLY:
                 result = size;
                break;
            case MeasureSpec.UNSPECIFIED:

                if(paramSize == ViewGroup.LayoutParams.WRAP_CONTENT || paramSize == ViewGroup.LayoutParams.MATCH_PARENT)
                    result = suggestedSize;
                else {
                    result = paramSize;
                }

                break;
        }

        return result;
    }

    @Override
    protected int getSuggestedMinimumHeight() {

        int suggested = super.getSuggestedMinimumWidth();
        if(mSelector != null && mSelector.length > 0 && mPageSize > 0){
            Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
            int height = fontMetricsInt.descent - fontMetricsInt.ascent;
            suggested = Math.max(suggested,  height * mPageSize);
        }

        return suggested;
    }



    @Override
    protected int getSuggestedMinimumWidth() {

        int suggested = super.getSuggestedMinimumHeight();
        if(mSelector != null && mSelector.length > 0 && mPageSize > 0){
            suggested = Math.max(suggested, computeMaximumWidth());
        }

        return suggested;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(mVelocityTracker != null){
            mVelocityTracker.addMovement(event);
        }


        int action = event.getActionMasked();

        switch (action){

            case MotionEvent.ACTION_DOWN:
                if(!mOverScroller.isFinished())
                    mOverScroller.abortAnimation();

                if(mVelocityTracker == null) {
                    mVelocityTracker = VelocityTracker.obtain();
                }else
                    mVelocityTracker.clear();

                mVelocityTracker.addMovement(event);
                mActivePointerId = event.getPointerId(0);

                mLastTouchY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                int deltaY = (int) (mLastTouchY - event.getY(mActivePointerId));
                if(!mIsDragging && Math.abs(deltaY) > mTouchSlop ) {
                    //do something
                    final ViewParent parent = getParent();
                    if (parent != null)
                        parent.requestDisallowInterceptTouchEvent(true);


                    if (deltaY > 0) {
                        deltaY -= mTouchSlop;
                    } else {
                        deltaY += mTouchSlop;
                    }

                    mIsDragging = true;
                }

                if(mIsDragging){
                    if(canScroll(deltaY)){
                        scrollBy(0, deltaY);
                    }
                    mLastTouchY = (int) event.getY();
                }

                break;
            case MotionEvent.ACTION_UP:

                if(mIsDragging) {

                    mIsDragging = false;

                    final ViewParent    parent = getParent();
                    if (parent != null)
                        parent.requestDisallowInterceptTouchEvent(false);

                    mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int velocity = (int) mVelocityTracker.getYVelocity(mActivePointerId);
                    if (Math.abs(velocity) > mMinimumVelocity) {
                        mOverScroller.fling(getScrollX(), getScrollY(), 0, -velocity, 0, 0, getMinimumScrollY(), getMaximumScrollY(), 0, 0);
                        invalidateOnAnimation();
                    } else {
                        //align item;
                        adjustItem();
                    }

                    recyclerVelocityTracker();
                }else{
                    //click event
                    int y = (int) event.getY(mActivePointerId);
                    handlerClick(y);
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

    private void recyclerVelocityTracker(){

        if(mVelocityTracker != null)
                mVelocityTracker.recycle();

        mVelocityTracker = null;
    }
    private void invalidateOnAnimation(){
        if(Build.VERSION.SDK_INT >= 16)
            postInvalidateOnAnimation();
        else
            invalidate();
    }

    private void handlerClick(int y){

        y = y + getScrollY();

        int position = y / getItemHeight();
        if(y >= 0 && position < mSelector.length) {
            Rect actualLoc = getLocationByPosition(position);
            int scrollY = actualLoc.top - getScrollY();
            mOverScroller.startScroll(getScrollX(), getScrollY(), 0, scrollY);
            invalidateOnAnimation();
        }
    }

    /**
     * 获取一个item位置，通过滚动正好将这个item放置在中间
     * @param position 当前选中的索引
     * @return 当前选中区域的矩形
     */
    private Rect getLocationByPosition(int position){
        int scrollY = position * getItemHeight() + getMinimumScrollY();
        return new Rect(0, scrollY, getWidth(), scrollY + getItemHeight());
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

    /**
     * 描 述：缓慢滑动到position指定位置<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/6 <br/>
     * @param position y坐标
     */
    public void smoothScrollTo(int position){
        if(position < 0 || mSelector == null || position > mSelector.length)
            return;

        Rect actualLoc = getLocationByPosition(position);
        int scrollY = actualLoc.top - getScrollY();
        mOverScroller.startScroll(getScrollX(), getScrollY(), 0, scrollY);
        invalidateOnAnimation();
    }

    /**
     * 描 述：缓慢滑动到value对应位置<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/6 <br/>
     * @param value 值
     */
    public void smoothScrollToValue(int value){
        if(mSelector == null)
            return;

        int pos = Arrays.binarySearch(mSelector, value);
        smoothScrollTo(pos);
    }

    /**
     * 描 述：迅速滑动到value对应位置<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/6 <br/>
     * @param value 值
     */
    public void scrollToValue(int value){
        if(mSelector == null)
            return;

        int pos = Arrays.binarySearch(mSelector, value);
        if(pos < 0 || mSelector == null || pos > mSelector.length)
            return;

        Rect actualLoc = getLocationByPosition(pos);
        int scrollY = actualLoc.top - getScrollY();
        scrollTo(0, scrollY);
    }

    /**
     * 调整item使对齐居中
     */
    private void adjustItem( ){
        int position  = computePosition();
        Rect rect = getLocationByPosition(position);
        int scrollY =    rect.top - getScrollY();
        if(mCallbackRef != null && mCallbackRef.get() != null) {
            mCallbackRef.get().onValueChanged(position, mSelector[position]);
        }

        if(scrollY != 0) {
            mOverScroller.startScroll(getScrollX(), getScrollY(), 0, scrollY);
            invalidateOnAnimation();
        }
    }


    private int computePosition(int offset){
        int topOffset = getHeight() / 2;
        int scrollY = getScrollY()   + topOffset +  offset;
        return scrollY / getItemHeight();
    }


    /**
     * 计算当前显示的位置
     * @return 当前位置
     */
    public int computePosition(){
        return computePosition(0);
    }

    private boolean canScroll(int deltaY){
        int scrollY = getScrollY() + deltaY;
        int top = getMinimumScrollY();
        int bottom = getMaximumScrollY();

        return scrollY >= top && scrollY <= bottom;
    }


    private int getMaximumScrollY(){
        return (mSelector.length - 1) * getItemHeight()  + getMinimumScrollY();
    }

    private int getMinimumScrollY(){
        return   -((getHeight() - getItemHeight()) / 2);
    }


    public int getItemHeight(){
        return getHeight() / mPageSize;
    }

    private int computeTextHeight(){
        Paint.FontMetricsInt metricsInt = mTextPaint.getFontMetricsInt();
        return metricsInt.bottom + metricsInt.top;
    }

    private int computeMaximumWidth(){
        int result = (int) mTextPaint.measureText("0000");
        int width;
        for(int objIndex =  0; mSelector != null &&  objIndex < mSelector.length; objIndex++){
            width = (int) mTextPaint.measureText(String.valueOf(mSelector[objIndex]));
            if(width > result)
                result = width;
        }

        return result;
    }


    public void  setListener(OnValueChanged valueChanged){
        mCallbackRef = new SoftReference<>(valueChanged);
    }

    public interface OnValueChanged{
        void onValueChanged(int position, int value);
    }

    /**
     * 描 述：修改最大最小值(仅用于修改数字选择边界)<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/6 <br/>
     * @param minValue 最小值
     * @param maxValue 最大值
     */
    public void setBoundValue(int minValue, int maxValue) {
        this.mMaxValue = maxValue;
        this.mMinValue = minValue;
        createSelector();
        invalidate();
    }

    /**
     * 描 述：修改最大值(仅用于修改数字选择边界)<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/6 <br/>
     * @param maxValue 最大值
     */
    public void setMaxValue(int maxValue) {
        setBoundValue(mMinValue, maxValue);
    }

    /**
     * 描 述：设置字符串集合<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/6 <br/>
     * @param textExtras 字符串集合
     */
    public void setExtras(List<String> textExtras) {
        mTextExtras = textExtras;
        setBoundValue(1, textExtras.size());
    }

    /**
     * 描 述：获取选值<br/>
     * 作 者：谌珂<br/>
     * 历 史: (1.0.0) 谌珂 2016/9/6 <br/>
     */
    public int getValue() {
        return mSelector[computePosition()];
    }

    public void setVaule(int value) {
        scrollToValue(value);
    }
}
