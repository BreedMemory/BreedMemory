package com.uuzz.android.ui.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.uuzz.android.R;

/**
 * 显示账户图片的控件，圆形、带外环线
 * @author Chunk E-mail:348353610@qq.com
 * @version Created time：2015-7-8 下午6:40:42
 */
public class CircleImageView extends ImageView {

//    private Logger logger = new Logger("CircleImageView");

	private int defaultWidth;
	private int defaultHeight;
    /** 外边框厚度 */
	private int mOutsideBorderThickness = 0;
    /** 内边框厚度 */
	private int mInsideBorderThickness = 0;
    /** 外边框与内边框之间的距离 */
	private int mOutsideBorderDistance = 0;
    /** 内边框与图片之间的距离 */
	private int mInsideBorderDistance = 0;
    /** 默认说明文字,如果为空则不画说明文字和背景 */
    private String mContent;
    /** 说明文字背景色 */
    private int mContentBackground;
    /** 说明文字颜色 */
    private int mContentColor;
    /** 说明文字大小 */
    private int mContentSize;
    private final int defaultColor = 0xFFFFFFFF;
    // 如果只有其中一个有值，则只画一个圆形边框
    /** 外边框颜色 */
    private int mOutsideBorderColor = 0;
    /** 内边框颜色 */
    private int mInsideBorderColor = 0;
	private Context mContext;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mPadding;
    private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP);
    private Bitmap mFilterBitmap;

	public CircleImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
        setCustomAttributes(context, attrs);
	}

	public CircleImageView(Context context) {
		this(context, null);
	}

	private void setCustomAttributes(Context context, AttributeSet attrs) {
        mContext = context;
        TypedArray a = mContext.obtainStyledAttributes(attrs,
                R.styleable.uuzzcircleimageview);
        mOutsideBorderThickness = a.getDimensionPixelSize(
                R.styleable.uuzzcircleimageview_outside_border_thickness, 0);
        mOutsideBorderDistance = a.getDimensionPixelSize(
                R.styleable.uuzzcircleimageview_outside_border_distance, 0);
        mOutsideBorderColor = a
                .getColor(R.styleable.uuzzcircleimageview_outside_border_color,
                        defaultColor);
        mInsideBorderThickness = a.getDimensionPixelSize(
                R.styleable.uuzzcircleimageview_inside_border_thickness, 0);
        mInsideBorderDistance = a.getDimensionPixelSize(
                R.styleable.uuzzcircleimageview_inside_border_distance, 0);
        mInsideBorderColor = a
                .getColor(R.styleable.uuzzcircleimageview_inside_border_color,
                        defaultColor);
        mContentBackground = a
                .getColor(R.styleable.uuzzcircleimageview_content_background_color,
                        defaultColor);
        mContentColor = a
                .getColor(R.styleable.uuzzcircleimageview_content_color,
                        defaultColor);
        mContent = a.getString(R.styleable.uuzzcircleimageview_content_text);
        mContentSize = a.getDimensionPixelSize(R.styleable.uuzzcircleimageview_content_text_size, 16);
        mPadding = mOutsideBorderThickness + mOutsideBorderDistance + mInsideBorderThickness + mInsideBorderDistance;
        setPadding(mPadding, mPadding, mPadding, mPadding);
        a.recycle();
        setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed) {
            if (defaultWidth == 0) {
                defaultWidth = getWidth();

            }
            if (defaultHeight == 0) {
                defaultHeight = getHeight();
            }
            makeBlockBackSrc(defaultWidth-2*mPadding, defaultHeight-2*mPadding);
        }
    }

    //定义一个绘制白色背景矩形的Bitmap的方法
    private void makeBlockBackSrc(int w, int h) {
        if(mFilterBitmap != null) {
            mFilterBitmap.recycle();
        }
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.WHITE);
        c.drawOval(new RectF(0, 0, w, h), p);
        mFilterBitmap =  bm;
    }

	@Override
	protected void onDraw(Canvas canvas) {
        if(getDrawable() == null) {
            return;
        }

        int count = canvas.saveLayer(0, 0, defaultWidth, defaultHeight, null, Canvas.ALL_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        mPaint.setXfermode(null);
        super.onDraw(canvas);
        mPaint.setXfermode(mMode);
        mPaint.setColor(Color.WHITE);
        if(mFilterBitmap == null || mFilterBitmap.isRecycled()) {
            makeBlockBackSrc(defaultWidth-2*mPadding, defaultHeight-2*mPadding);
        }
        canvas.drawBitmap(mFilterBitmap, mPadding, mPadding, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(count);



        canvas.restoreToCount(count);
        int radius;
        if(mOutsideBorderThickness !=0
        		&& (mOutsideBorderColor & 0xFF000000) != 0){// 外边框距离、外边框厚度不为0且外边框颜色不透明
        	// 定图片半径
        	radius = (defaultWidth < defaultHeight ? defaultWidth
            		: defaultHeight) / 2 - mOutsideBorderThickness - mOutsideBorderDistance;
        	// 画外边框
        	drawCircleBorder(canvas, radius + mOutsideBorderDistance, mOutsideBorderThickness, mOutsideBorderColor);
        	// 如果内边框达到绘制要求则绘制内边框
        	if(mInsideBorderThickness !=0
            		&& (mInsideBorderColor & 0xFF000000) != 0){
        		// 画内边框
        		drawCircleBorder(canvas, radius + mInsideBorderDistance, mInsideBorderThickness, mInsideBorderColor);
        	}
        }else if(mInsideBorderThickness !=0
        		&& (mInsideBorderColor & 0xFF000000) != 0){
        	radius = (defaultWidth < defaultHeight ? defaultWidth
            		: defaultHeight) / 2 - mInsideBorderThickness - mInsideBorderDistance;
        	// 画内边框
        	drawCircleBorder(canvas, radius + mInsideBorderDistance, mInsideBorderThickness, mInsideBorderColor);
        }
	}

    /**
     * 边缘画圆
     */
    private void drawCircleBorder(Canvas canvas, int radius, int thickness, int color) {
        /* 去锯齿 */
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        mPaint.setColor(color);
        /* 设置paint的　style　为STROKE：空心 */
        mPaint.setStyle(Paint.Style.STROKE);
        /* 设置paint的外框宽度 */
        mPaint.setStrokeWidth(thickness);
        canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius+thickness/2, mPaint);
    }

    @Override
    protected void onDetachedFromWindow() {
        if(mFilterBitmap != null && !mFilterBitmap.isRecycled()) {
            mFilterBitmap.recycle();
        }
        super.onDetachedFromWindow();
    }
}
