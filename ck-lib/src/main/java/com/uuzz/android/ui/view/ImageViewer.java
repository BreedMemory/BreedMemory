package com.uuzz.android.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.uuzz.android.util.log.Logger;

/**
 * 用来编辑图片大小、位置的控件，与AccountImage一起使用，为其提供符合用户标准的头像文件，不支持xml配置属性
 * @author Chunk E-mail:348353610@qq.com
 * @version Created time：2015-7-17 下午3:35:49
 *
 */
public class ImageViewer extends ImageView {
	public static final int ON_CLICK = 500;
	Handler mHandler = new Handler(new Handler.Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if(msg.what == ON_CLICK && mOnClickListener != null) {
				mOnClickListener.onClick(ImageViewer.this);
			}
			return false;
		}
	});

	private OnClickListener mOnClickListener;

	public void setOnClickListener(OnClickListener mOnClickListener) {
		this.mOnClickListener = mOnClickListener;
	}

	Logger logger = new Logger("ImageViewer");

	private final int MOVE_SELECTION = 0;
	private final int CHANGE_SELECTION = 1;
	private final int MOVE_BITMAP = 2;

	//图片矩阵
	Matrix mMatrix;
	// 需要编辑的原图片
	private Bitmap source;
	// 编辑后的图片
	private Bitmap bitmap = null;
	// 记录图片是否是横向填充
	private boolean isHorizontal;
	//判断是否是ontouch事件
	private boolean isTouchEvent;
	//画笔
	Paint paint = new Paint();
	Path path = new Path();
	PathEffect effects = new DashPathEffect(new float[]{5,5,5,5},1);
	//绘制图片的起始坐标
	private int bitmapX;
	private int bitmapY;
	//单手指按下时坐标
	private float downX;
	private float downY;
	private float perMoveX;
	private float perMoveY;
	//两手指之间的距离
	private float oldDistance;
	private float newDistance;
	//两手指的中点坐标
	private PointF midPoint;
	//选区代表的矩形区域
	Rect selection = new Rect();
	//控件宽高
	private int viewWidth;
	private int viewHeight;
	//单根手指按下时的动作标记，与MOVE_SELECTION、CHANGE_SELECTION、MOVE_BITMAP联合使用
	private int moveAction;
	//选区半径（不包含线宽引起的误差）
	private int radius;
	//缩放选区时手指与圆心的距离
	private double oldRadius;
	private double newRadius;
	private boolean isCut;
	//初始化图片时的缩放比例
	private float mScale = 1;
	/** 标记是否是抬起了一根手指 */
	private boolean isPointerUp = false;
	/** 计算点击的次数 */
	private int count = 0;
	/** 第一次点击的时间 long型 */
	private long firstClick = 0;
	/** 最后一次点击的时间 */
	private long lastClick = 0;
	/** 默认的双击事件间隔 */
	private long delay = 200;

	public void setSource(Bitmap source){
		this.source = source;
	}

	public ImageViewer(Context context) {
		super(context);
		mMatrix = getImageMatrix();
		isTouchEvent = false;
		setDrawingCacheEnabled(true);
	}

	public ImageViewer(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mMatrix = getImageMatrix();
		isTouchEvent = false;
		setDrawingCacheEnabled(true);
	}

	public ImageViewer(Context context, AttributeSet attrs) {
		super(context, attrs);
		mMatrix = getImageMatrix();
		isTouchEvent = false;
		setDrawingCacheEnabled(true);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		selection.set(0, 0, getWidth(), getHeight());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if(source == null) {
			try {
				source = ((BitmapDrawable)getDrawable()).getBitmap();
			} catch (Exception e) {
				return;
			}
		}
		if(!isTouchEvent) {
			float offsetWidth = Math.abs(source.getWidth()-getWidth())/(float)getWidth();
			float offsetHeight = Math.abs(source.getHeight()-getHeight())/(float)getHeight();
			if(offsetHeight > offsetWidth) {
				mScale = (float)getHeight()/source.getHeight();
				mMatrix.postScale(mScale, mScale, 0, 0);
				mMatrix.postTranslate(-(source.getWidth()*mScale - getWidth())/2, 0);
			} else {
				mScale = (float)getWidth()/source.getWidth();
				mMatrix.postScale(mScale, mScale, 0, 0);
				mMatrix.postTranslate(0, -(source.getHeight()*mScale - getHeight())/2);
			}
		}
		canvas.drawBitmap(source, mMatrix, paint);
		isTouchEvent = false;
	}
	
	/**
	 * 旋转图片
	 * @param degrees 旋转角度
	 */
	public void rotate(float degrees){
		Matrix matrix = new Matrix();
		matrix.setRotate(degrees);
		source = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
		invalidate();
	}

	/**
	 * 描 述：缩放图片<br/>
	 * 作者：谌珂<br/>
	 * 历 史: (版本) 谌珂 2016/1/27 注释 <br/>
	 * @param sx x缩放倍数
	 * @param sy y缩放倍数
	 * @param px 缩放中心点x坐标
	 * @param py 缩放中心点y坐标
	 */
	private void scaleBitmap(float sx, float sy, float px, float py){
		logger.d("sx="+String.valueOf(sx)+",sy="+String.valueOf(sy)+",px="+String.valueOf(px)+",py="+String.valueOf(py));
		//临时保存矩阵
		float[] matrixValues = new float[9];
		mMatrix.getValues(matrixValues);

		mMatrix.postScale(sx, sy, px, py);
		//缩放后如果图片边界已经超出选区则还原矩阵
		if(sx*matrixValues[Matrix.MSCALE_X] > mScale*4) {
			mMatrix.setValues(matrixValues);
			return;
		}
		setImageMatrix(mMatrix);
	}

	/**
	 * 描 述：缩放图片<br/>
	 * 作者：谌珂<br/>
	 * 历 史: (版本) 谌珂 2016/1/27 注释 <br/>
	 * @param px 缩放中心点x坐标
	 * @param py 缩放中心点y坐标
	 */
	private void autoScaleBitmap(float px, float py){
		//临时保存矩阵
		float[] matrixValues = new float[9];
		mMatrix.getValues(matrixValues);


		//缩放后如果图片边界已经超出选区则还原矩阵
		float scale;
		if(matrixValues[Matrix.MSCALE_X] > mScale*2) {
			scale = mScale/matrixValues[Matrix.MSCALE_X];
		} else {
			scale = 4/matrixValues[Matrix.MSCALE_X];
		}
		mMatrix.postScale(scale, scale, px, py);
	}


	/**
	 * 描 述：平移图片<br/>
	 * 作者：谌珂<br/>
	 * 历 史: (版本) 谌珂 2016/1/27 注释 <br/>
	 */
	private void translateBitmap(float dx, float dy){
		logger.d("translateBitmap:dx=" + String.valueOf(dx) + ",dy=" + String.valueOf(dy));
		mMatrix.postTranslate(dx, dy);
		setImageMatrix(mMatrix);
	}

	private void clear() {
		count = 0;
		firstClick = 0;
		lastClick = 0;
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		setScaleType(ScaleType.MATRIX);
		int action = event.getAction();
		int pointCount = event.getPointerCount();
		if(pointCount == 1){
			//一根手指按下,处理移动图片、选框或选框边角
			switch (action) {
				case MotionEvent.ACTION_DOWN:
					downX = event.getX();
					downY = event.getY();
					perMoveX = event.getX();
					perMoveY = event.getY();
					// 如果第二次点击 距离第一次点击时间过长 那么将第二次点击看为第一次点击
					if (firstClick != 0 && System.currentTimeMillis() - firstClick > delay) {
						clear();
					}
					if (++count == 1) {
						firstClick = System.currentTimeMillis();
					}
					else if (count == 2) {
						lastClick = System.currentTimeMillis();
						// 两次点击小于500ms 也就是连续点击
						if (lastClick - firstClick < delay) {
							mHandler.removeMessages(ON_CLICK);
							clear();
							autoScaleBitmap(perMoveX, perMoveY);
						}
					}
					//计算一次原始距离
					oldRadius = getDiatance((long) (perMoveX -selection.left-radius), (long) (perMoveY -selection.top-radius));
					//根据触点坐标确认动作
					confirmAction((long) (perMoveX -selection.left-radius), (long) (perMoveY -selection.top-radius));
					break;
				case MotionEvent.ACTION_MOVE:
					float dX = event.getX()- perMoveX;
					float dY = event.getY()- perMoveY;
					switch (moveAction) {
						case MOVE_SELECTION:
							//移动选区
							logger.d("move selection");
							moveSelection(dX, dY);
							break;
						case CHANGE_SELECTION:
							//改变选区大小
							logger.d("change selection");
							newRadius = getDiatance((long) (event.getX()-selection.left-radius), (long) (event.getY()-selection.top-radius));
							logger.d("old radius is "+oldRadius);
							logger.d("new radius is "+newRadius);
							scaleSelecition(0.71*(newRadius-oldRadius));
							oldRadius = newRadius;
							break;
						case MOVE_BITMAP:
							if(isPointerUp) {
								perMoveX = event.getX();
								perMoveY = event.getY();
								dX = event.getX()- perMoveX;
								dY = event.getY()- perMoveY;
								isPointerUp = false;
							}
							//移动图片
							logger.d("move bitmap");
							isTouchEvent = true;


							//判断图片移动是否会超出选区的范围，超出则不移动
	//						float[] result = isSelectionOut(dX, dY);
	//						translateBitmap(result[0], result[1]);

							//移动图片
							translateBitmap(dX, dY);
							//////////////////////


		//					if(Math.abs(dX) > 3 || Math.abs(dY) > 3){        //控制移动图片的精度
		//						translateBitmap(dX, dY);
		//					}
							break;
						default:
							break;
					}
					perMoveX = event.getX();
					perMoveY = event.getY();
					break;
				case MotionEvent.ACTION_UP:
					adjustBitmap();
					if (firstClick != 0 && System.currentTimeMillis() - firstClick < delay && Math.abs(event.getX()-downX) < 5 && Math.abs(event.getY()-downY) < 5) {
						mHandler.removeMessages(ON_CLICK);
						mHandler.sendEmptyMessageDelayed(ON_CLICK, delay);
					}
					break;
				default:
					break;
			}
		}else if(pointCount == 2){
			//两根手指按下，处理缩放图片
			switch (action&MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_POINTER_DOWN:
					//保存两点之间距离
					oldDistance = getDistance(event);
					midPoint = getMidPoint(event);
					break;
				case MotionEvent.ACTION_POINTER_UP:
					//通知单手指移动时重新记录按下坐标
					isPointerUp = true;
					//自适应一遍选区
					adjustBitmap();
					break;
				case MotionEvent.ACTION_MOVE:
					//计算新距离
					isTouchEvent = true;
					newDistance = getDistance(event);
					float scale = newDistance/oldDistance;
					oldDistance = newDistance;
					scaleBitmap(scale, scale, midPoint.x, midPoint.y);

					break;
				default:
					break;
			}
		}

		return true;
	}

	/**
	 * 描 述：调整图片适应选区位置和大小<br/>
	 * 作者：谌珂<br/>
	 * 历 史: (版本) 谌珂 2016/1/27 注释 <br/>
	 */
	private void adjustBitmap() {
		float[] matrixValues = new float[9];
		mMatrix.getValues(matrixValues);
		float scaleX = matrixValues[Matrix.MSCALE_X];
		float scaleY = matrixValues[Matrix.MSCALE_Y];

		//图片缩放后实际的边界点
		float left = matrixValues[Matrix.MTRANS_X];
		float top = matrixValues[Matrix.MTRANS_Y];
		float width = source.getWidth() * scaleX;
		float height = source.getHeight() * scaleY;
		float right = left + width;
		float bottom = top + height;

		//计算图片需要的scale，拿到宽、高和现在的倍数的最大值
		float scale = 1;
		if(width < selection.width()) {
			float tempScale = (float)selection.width()/width;
			scale = scale > tempScale ? scale : tempScale;
		}
		if(height < selection.height()) {
			float tempScale = (float)selection.height()/height;
			scale = scale > tempScale ? scale : tempScale;
		}
		isTouchEvent = true;
		//经过最终缩放
		if(scale > 1) {
			scaleBitmap(scale, scale, left + width/2, top + height/2);
		}

		float offSetX = 0;
		float offSetY = 0;
		//计算修复平移距离
		if(left > selection.left && right > selection.right) {  //平移至左边界
			offSetX = selection.left - left;
		} else if(left < selection.left && right < selection.right) { //平移至右边界
			offSetX = selection.right - right;
		}

		if(top > selection.top && bottom > selection.bottom) {  //平移至上边界
			offSetY = selection.top - top;
		} else if(top < selection.top && bottom < selection.bottom) {
			offSetY = selection.bottom - bottom;
		}

		translateBitmap(offSetX, offSetY);
	}

	/**
	 * 描 述：判断是否会缩放出选区<br/>
	 * 作者：谌珂<br/>
	 * 历 史: (版本) 谌珂 2016/1/18 注释 <br/>
     * @return 如果会超出选区返回true
	 * @deprecated
     */
	private boolean isSelectionOut() {
		float[] matrixValues = new float[9];
		mMatrix.getValues(matrixValues);
		float scaleX = matrixValues[Matrix.MSCALE_X];
		float scaleY = matrixValues[Matrix.MSCALE_Y];

		//图片缩放后实际的边界点
		float left = matrixValues[Matrix.MTRANS_X];
		float top = matrixValues[Matrix.MTRANS_Y];
		float right = left + source.getWidth() * scaleX;
		float bottom = top + source.getHeight() * scaleY;

		return (left > selection.left || right < selection.right || top > selection.top || bottom < selection.bottom);
	}

	/**
	 * 描 述：判断是否会移动出选区<br/>
	 * 作者：谌珂<br/>
	 * 历 史: (版本) 谌珂 2016/1/18 注释 <br/>
	 * @param dX 当前x偏移量
	 * @param dY 当前y偏移量
	 * @return 返回允许移动的实际值数组
	 * @deprecated
	 */
	private float[] isSelectionOut(float dX, float dY) {
		float[] matrixValues = new float[9];
		mMatrix.getValues(matrixValues);
		float scaleX = matrixValues[Matrix.MSCALE_X];
		float scaleY = matrixValues[Matrix.MSCALE_Y];
		float translationX = matrixValues[Matrix.MTRANS_X];
		float translationY = matrixValues[Matrix.MTRANS_Y];

		//图片移动后实际的边界点
		float left = translationX + dX;
		float top = translationY + dY;
//		float left = translationX * scaleX + bitmapX + dX;
//		float top = translationY * scaleY + bitmapY + dY;
		float right = left + source.getWidth() * scaleX;
		float bottom = top + source.getHeight() * scaleY;

		float[] result = new float[]{dX, dY};
		if(left > selection.left || right < selection.right) {
			result[0] = 0;
		}
		if(top > selection.top || bottom < selection.bottom) {
			result[1] = 0;
		}

		return result;
	}

	/**
	 * 移动选区
	 * @param dX x偏移量
	 * @param dY y偏移量
	 */
	private void moveSelection(float dX, float dY) {
		if(selection.left+dX >= 0 && selection.top+dY >= 0 && selection.right+dX <= viewWidth && selection.bottom+dY <= viewHeight){
			selection.left = (int) (selection.left+dX);
			selection.top = (int) (selection.top+dY);
			selection.right = (int) (selection.right+dX);
			selection.bottom = (int) (selection.bottom+dY);
			isTouchEvent = true;
			invalidate();
		}
	}

	/**
	 * 根据变化长度缩小选区
	 * @param d 缩放倍数
	 */
	private void scaleSelecition(double d) {
		if(selection.right - selection.left > Math.abs(2d) && selection.bottom - selection.top > Math.abs(2d)){
			selection.left = (int) ((selection.left-d >= 0) ? selection.left-d : 0);
			selection.top = (int) ((selection.top-d >= 0) ? selection.top-d : 0);
			selection.bottom = (int) ((selection.bottom+d <= viewHeight) ? selection.bottom+d : viewHeight);
			selection.right = (int) ((selection.right+d <= viewWidth) ? selection.right+d : viewWidth);
			isTouchEvent = true;
			logger.d("selection is "+selection.toString());
			invalidate();
		}
	}

	/**
	 * 根据触点坐标确认动作,现默认只返回移动图片
	 * @deprecated
	 */
	private void confirmAction(long offsetX, long offsetY) {

		/*
		* 修改为选区不能移动和缩放，所以moveAction === MOVE_BITMAP
		* */
//		//计算触点与圆心的距离
//		long sqrtLength = offsetX*offsetX+offsetY*offsetY;
//		//判断是否在选区范围内
//		if((long)(radius+20)*(radius+20) >= sqrtLength){
//			if(sqrtLength >= (long)(radius-20)*(radius-20)){
//				this.moveAction = CHANGE_SELECTION;
//			}else{
//				this.moveAction = MOVE_SELECTION;
//			}
//			return;
//		}
		
		this.moveAction = MOVE_BITMAP;
	}

	/**
	 * 计算触点到圆心的距离
	 * @param offsetX 相对于圆心的x坐标
	 * @param offsetY 相对于圆心的y坐标
	 * @return 返回点到圆心的距离
	 */
	private double getDiatance(long offsetX, long offsetY) {
		return Math.sqrt(offsetX*offsetX+offsetY*offsetY);
	}

	/**
	 * 计算两根手指之间距离
	 * @param event 消息事件
	 * @return 返回两根手指之间的距离
	 */
	@SuppressLint("FloatMath")
	private float getDistance(MotionEvent event) {
		//第一个点
		float x = event.getX();
		float y = event.getY();
		
		//第二个点
		float x2 = event.getX(1);
		float y2 = event.getY(1);
		float result = (float) Math.sqrt((x-x2)*(x-x2)+(y-y2)*(y-y2));
		logger.d("two pointer distance is "+result);
		return result;
	}
	
	/**
	 * 计算两手指中点
	 * @param event 消息事件
	 * @return 返回两根手指之间中点坐标
	 */
	private PointF getMidPoint(MotionEvent event) {
		PointF point = new PointF();
    	point.x = (event.getX()+event.getX(1))/2;
    	point.y = (event.getY()+event.getY(1))/2;
		logger.d("midPoint:x="+point.x+",y="+point.y);
    	return point;
	}
	
	/**
	 * 获取截取后的图片
	 * @param isScale 为true时返回的图片像素与选区像素一致，为false时将返回原图片中截取部分，为保证图片不失真尽量使用false
	 * @return 返回剪裁后的图片
	 */
	public Bitmap clipBitmap(boolean isScale){
		Bitmap result = Bitmap.createBitmap(selection.width(), selection.height(), Config.ARGB_8888);
		Canvas canvas = new Canvas(result);
		mMatrix.postTranslate(-selection.left, -selection.top);
		canvas.drawBitmap(source, mMatrix, null);

		if(!isScale){
			//把图片缩放到原来大小
			float[] matrixValues = new float[9];
			mMatrix.getValues(matrixValues);
			float scaleX = matrixValues[Matrix.MSCALE_X];
			float scaleY = matrixValues[Matrix.MSCALE_Y];
			bitmap = Bitmap.createBitmap((int)(selection.width()/scaleX), (int)(selection.height()/scaleY), Config.ARGB_8888);

			Canvas scaleCanvas = new Canvas(bitmap);
			mMatrix.reset();
			mMatrix.setScale(1/scaleX, 1/scaleY);
			scaleCanvas.drawBitmap(result, mMatrix, null);
			result.recycle();
		}else{
			bitmap = result;
		}
//		isCut = true;
//		invalidate();
		return bitmap;
	}

	/**
	 * 描 述：释放bitmap对象<br/>
	 * 作 者：谌珂<br/>
	 * 历 史: (版本) 谌珂 2016/3/16 注释 <br/>
	 */
	public void recycleBitmap() {
		source.recycle();
	}
}