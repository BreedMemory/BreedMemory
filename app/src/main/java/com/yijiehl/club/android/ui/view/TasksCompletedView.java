package com.yijiehl.club.android.ui.view;

import android.graphics.Color;
import android.text.TextUtils;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.yijiehl.club.android.R;


/**
 * 原作者：naiyu(http://snailws.com)
 * 修改时间：2014年3月4日10:48:23
 * 修改者：LuoJ
 * 圆形进度展示组件，外环绘制进度。
 * 
 * 注意xml中引用组件 如果项目为library（命名空间 xmlns:tc="http://schemas.android.com/apk/res-auto"）
 * 不然用 xmlns:tc="http://schemas.android.com/apk/这里用manifest中的package路径"
 * 添加一些自定义属性
 * xml中自定义属性含义，在下方初始化属性处有注释
 * tc:radius="30dp"
 * tc:strokeWidth="10dp"
 * tc:circleColor="@color/circle_color"
 * tc:ringColor="@color/ring_color"
 * tc:ringBackgroundColor="@color/ring_bg_color"
 */
public class TasksCompletedView extends View {

	/**
	 * 画笔
	 */
	private Paint mCirclePaint;// 画实心圆的画笔
	private Paint mRingPaint;// 画圆环的画笔
	private Paint mRingBackgroundPaint;// 画圆环背景的画笔
	private Paint mTextPaint;// 画字体的画笔
    private Paint mDefaultTextPaint;// 画字体的画笔
    private Paint mSubheadPaint;//副标题画笔

	/**
	 * 颜色
	 */
	private int mCircleColor;// 中心圆形颜色
	private int mRingColor;// 外环颜色
	private int mRingBackgroundColor;//外环背景色
    private int mTextColor;//文本颜色
	
	/**
	 * 大小
	 */
	private float mRadius;// 中心圆的半径
	private float mRingRadius;// 外环半径
	private float mStrokeWidth;// 外环宽度
	private float mTxtWidth;// 字的长度
	private float mTxtHeight;// 字的高度
	
	/**
	 * 坐标
	 */
	private int mXCenter;// 圆心x坐标
	private int mYCenter;// 圆心y坐标
	
	/**
	 * 内容
	 */
	private int mTotalProgress = 100;// 总进度
	private int mProgress;// 当前进度
	private String mDefaultText="";
    private String mSubhead;
	private String mCompletedText="ok";//默认完成后显示的内容

    private boolean alwaysShowDefaultText=false;

	public TasksCompletedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 获取自定义的属性
		initAttrs(context, attrs);
		//初始化变量
		initVariable();
	}

	/**
	 * 初始化自定义属性
	 * @param context
	 * @param attrs
	 */
	private void initAttrs(Context context, AttributeSet attrs) {
		TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,R.styleable.TasksCompletedView, 0, 0);
		//中心圆的半径
		mRadius = typeArray.getDimension(R.styleable.TasksCompletedView_tc_radius, 80);
		//中心园的颜色
		mCircleColor = typeArray.getColor(R.styleable.TasksCompletedView_tc_circleColor, 0xFFFFFFFF);
		//外环的宽度
		mStrokeWidth = typeArray.getDimension(R.styleable.TasksCompletedView_tc_strokeWidth, 10);
		//外环的颜色
		mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_tc_ringColor, 0xFFFFFFFF);
		//外环的背景色
		mRingBackgroundColor=typeArray.getColor(R.styleable.TasksCompletedView_tc_ringBackgroundColor, 0xFFFFFFFF);
        //
        mTextColor=typeArray.getColor(R.styleable.TasksCompletedView_tc_textColor, Color.BLACK);
		//外环半径
		mRingRadius = mRadius + mStrokeWidth / 2;
	}

	/**
	 * 初始化变量
	 */
	private void initVariable() {
		mCirclePaint = new Paint();
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setColor(mCircleColor);
		mCirclePaint.setStyle(Paint.Style.FILL);
		
		mRingPaint = new Paint();
		mRingPaint.setAntiAlias(true);
		mRingPaint.setColor(mRingColor);
		mRingPaint.setStyle(Paint.Style.STROKE);
		mRingPaint.setStrokeWidth(mStrokeWidth);
		
		mRingBackgroundPaint= new Paint();
		mRingBackgroundPaint.setAntiAlias(true);
		mRingBackgroundPaint.setColor(mRingBackgroundColor);
		mRingBackgroundPaint.setStyle(Paint.Style.STROKE);
		mRingBackgroundPaint.setStrokeWidth(mStrokeWidth);
		
		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setStyle(Paint.Style.FILL);
		mTextPaint.setARGB(255, 255, 255, 255);
        mTextPaint.setColor(mTextColor);
		mTextPaint.setTextSize(mRadius / 2);

        mDefaultTextPaint = new Paint();
        mDefaultTextPaint.setAntiAlias(true);
        mDefaultTextPaint.setStyle(Paint.Style.FILL);
        mDefaultTextPaint.setARGB(255, 255, 255, 255);
        mDefaultTextPaint.setColor(mTextColor);
        mDefaultTextPaint.setTextSize(mRadius / 2);

        mSubheadPaint = new Paint();
        mSubheadPaint.setAntiAlias(true);
        mSubheadPaint.setStyle(Paint.Style.FILL);
        mSubheadPaint.setARGB(255, 255, 255, 255);
        mSubheadPaint.setColor(mTextColor);
        mSubheadPaint.setTextSize(mRadius / 6);

		FontMetrics fm = mTextPaint.getFontMetrics();
		mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);
		
	}

	@SuppressLint("DrawAllocation")//DrawAllocation --- 避免在绘制或者解析布局(draw/layout)时分配对象。没懂。。。
	@Override
	protected void onDraw(Canvas canvas) {

		//获取中心点坐标
		mXCenter = getWidth() / 2;
		mYCenter = getHeight() / 2;
		
		//绘制中心圆
		canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);

		//外环圆边界
		RectF oval = new RectF();
		oval.left = (mXCenter - mRingRadius);
		oval.top = (mYCenter - mRingRadius);
		oval.right = mXCenter+mRingRadius;
		oval.bottom = mYCenter+mRingRadius;
		
		//外环背景
		canvas.drawArc(oval, -90, 360, false, mRingBackgroundPaint);
		
		if (mProgress > 0 ) {
			
			canvas.drawArc(oval, -90, ((float)mProgress / mTotalProgress) * 360, false, mRingPaint);
			
//			canvas.drawCircle(mXCenter, mYCenter, mRadius + mStrokeWidth / 2, mRingPaint);
			String txt = mProgress + "%";
			if (mProgress==100) {
				txt=mCompletedText;
			}
			mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
			if(!alwaysShowDefaultText){
                canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 4, mTextPaint);
                return;
            }
		}
        if (!TextUtils.isEmpty(mDefaultText)) {
            mTxtWidth = mDefaultTextPaint.measureText(mDefaultText, 0, mDefaultText.length());
            float txtY=mYCenter + mTxtHeight / 4;
            canvas.drawText(mDefaultText, mXCenter - mTxtWidth / 2, txtY, mDefaultTextPaint);
            if (!TextUtils.isEmpty(mSubhead)) {
                float mSubTxtWidth = mSubheadPaint.measureText(mSubhead, 0, mSubhead.length());
                canvas.drawText(mSubhead, mXCenter - mSubTxtWidth / 2, (txtY+mTxtHeight/2), mSubheadPaint);
            }
        }
    }
	
	/**
	 * 设置进度
	 * @param progress
	 */
	public void setProgress(int progress) {
		mProgress = progress;
//		invalidate();
		postInvalidate();
	}

	/**
	 * 设置完成后显示的内容
	 * @param text
	 */
	public void setCompletedText(String text){
		mCompletedText=text;
		postInvalidate();
	}
	
	/**
	 * 设置完成后显示的内容
	 * @param text
	 */
	public void setDefaultText(String text){
		mDefaultText=text;
		postInvalidate();
	}

    public void setSubhead(String text){
        this.mSubhead=text;
    }

    public void alwaysShowDefaultText(){
        this.alwaysShowDefaultText=true;
    }

    public void showProgressText(){
        this.alwaysShowDefaultText=false;
    }

}
