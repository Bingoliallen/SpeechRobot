package com.example.common.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.example.common.R;

/**
 * 世界上最遥远的距离不是生与死
 * 而是你亲手制造的BUG就在你眼前
 * 你却怎么都找不到它
 * Created by 王剑洪
 * On 2017/9/28.
 */

public class StarBar extends View {

  //星星排列方向
  public static final int HORIZONTAL = 0;
  public static final int VERTICAL = 1;

  //实心图片
  private Bitmap mSolidBitmap;
  //空心图片
  private Bitmap mHollowBitmap;
  //最大的数量
  private int mStarMaxNumber;
  private float mStarRating;

  private Paint mPaint;
  private int mSpaceWidth;//星星间隔
  private int mStarWidth;//星星宽度
  private int mStarHeight;//星星高度
  private boolean isIndicator;//是否是一个指示器（用户无法进行更改）
  private int mOrientation;

  public StarBar(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public StarBar(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    mPaint = new Paint();
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StarBarView, defStyle, 0);
    mSpaceWidth = a.getDimensionPixelSize(R.styleable.StarBarView_space_width, 0);
    mStarWidth = a.getDimensionPixelSize(R.styleable.StarBarView_star_width, 0);
    mStarHeight = a.getDimensionPixelSize(R.styleable.StarBarView_star_height, 0);
    mStarMaxNumber = a.getInt(R.styleable.StarBarView_star_max, 0);
    mStarRating = a.getFloat(R.styleable.StarBarView_star_rating, 0);
    mSolidBitmap = getZoomBitmap(BitmapFactory.decodeResource(context.getResources(),
        a.getResourceId(R.styleable.StarBarView_star_solid, 0)));
    mHollowBitmap = getZoomBitmap(BitmapFactory.decodeResource(context.getResources(),
        a.getResourceId(R.styleable.StarBarView_star_hollow, 0)));
    mOrientation = a.getInt(R.styleable.StarBarView_star_orientation, HORIZONTAL);
    isIndicator = a.getBoolean(R.styleable.StarBarView_star_isIndicator, false);
    a.recycle();
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    if (!isIndicator) {
      switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
          if (mOrientation == HORIZONTAL) {
            float TotalWidth = mStarMaxNumber * (mStarWidth + mSpaceWidth);
            if (event.getX() <= TotalWidth) {
              float newStarRating = (int) event.getX() / (mStarWidth + mSpaceWidth) + 1;
              setStarRating(newStarRating);
            }
          } else {
            float TotalHeight = mStarMaxNumber * (mStarHeight + mSpaceWidth);
            if (event.getY() <= TotalHeight) {
              float newStarRating = (int) event.getY() / (mStarHeight + mSpaceWidth) + 1;
              setStarRating(newStarRating);
            }
          }
          break;
        case MotionEvent.ACTION_MOVE:
          //float starTotalWidth = mStarMaxNumber * (mStarWidth + mSpaceWidth);
          //if (event.getX() <= starTotalWidth) {
          //  float newStarRating = (int) event.getX() / (mStarWidth + mSpaceWidth) + 1;
          //  setStarRating(newStarRating);
          //}
          break;
        case MotionEvent.ACTION_UP:
          break;
        case MotionEvent.ACTION_CANCEL:
          break;
      }
    }
    return super.onTouchEvent(event);
  }

  public float getStarRating() {
    return mStarRating;
  }

  /**
   * 设置星星的进度
   */
  public void setStarRating(float mStarRating) {
    this.mStarRating = mStarRating;
    invalidate();
  }

  public int getStarMaxNumber() {
    return mStarMaxNumber;
  }

  public void setStarMaxNumber(int starMaxNumber) {
    this.mStarMaxNumber = starMaxNumber;
    invalidate();
  }

  public boolean isIndicator() {
    return isIndicator;
  }

  public void setIsIndicator(boolean isIndicator) {
    this.isIndicator = isIndicator;
  }

  /**
   * 获取缩放图片
   */
  public Bitmap getZoomBitmap(Bitmap bitmap) {
    if (mStarWidth == 0 || mStarHeight == 0) {
      return bitmap;
    }
    // 获得图片的宽高
    int width = bitmap.getWidth();
    int height = bitmap.getHeight();

    // 设置想要的大小
    int newWidth = mStarWidth;
    int newHeight = mStarHeight;
    // 计算缩放比例
    float scaleWidth = ((float) newWidth) / width;
    float scaleHeight = ((float) newHeight) / height;
    // 取得想要缩放的matrix参数
    Matrix matrix = new Matrix();
    matrix.postScale(scaleWidth, scaleHeight);
    // 得到新的图片
    Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    return newBmp;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (mHollowBitmap == null || mSolidBitmap == null) {
      return;
    }
    //绘制实心进度
    int solidStarNum = (int) mStarRating;
    //绘制实心的起点位置
    int solidStartPoint = 0;
    if (mOrientation == HORIZONTAL) {
      for (int i = 1; i <= solidStarNum; i++) {
        canvas.drawBitmap(mSolidBitmap, solidStartPoint, 0, mPaint);
        solidStartPoint = solidStartPoint + mSpaceWidth + mSolidBitmap.getWidth();
      }
    } else {
      for (int i = 1; i <= solidStarNum; i++) {
        canvas.drawBitmap(mSolidBitmap, 0, solidStartPoint, mPaint);
        solidStartPoint = solidStartPoint + mSpaceWidth + mSolidBitmap.getHeight();
      }
    }
    //虚心开始位置
    int hollowStartPoint = solidStartPoint;
    //多出的实心部分起点
    int extraSolidStarPoint = hollowStartPoint;
    //虚心数量
    int hollowStarNum = mStarMaxNumber - solidStarNum;
    if (mOrientation == HORIZONTAL) {
      for (int j = 1; j <= hollowStarNum; j++) {
        canvas.drawBitmap(mHollowBitmap, hollowStartPoint, 0, mPaint);
        hollowStartPoint = hollowStartPoint + mSpaceWidth + mHollowBitmap.getWidth();
      }
    } else {
      for (int j = 1; j <= hollowStarNum; j++) {
        canvas.drawBitmap(mHollowBitmap, 0, hollowStartPoint, mPaint);
        hollowStartPoint = hollowStartPoint + mSpaceWidth + mHollowBitmap.getWidth();
      }
    }
    //多出的实心长度
    int extraSolidLength = (int) ((mStarRating - solidStarNum) * mHollowBitmap.getWidth());
    Rect rectSrc = new Rect(0, 0, extraSolidLength, mHollowBitmap.getHeight());
    Rect dstF = new Rect(extraSolidStarPoint, 0, extraSolidStarPoint + extraSolidLength,
        mHollowBitmap.getHeight());
    canvas.drawBitmap(mSolidBitmap, rectSrc, dstF, mPaint);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    if (mOrientation == HORIZONTAL) {
      //判断是横向还是纵向，测量长度
      setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec));
    } else {
      setMeasuredDimension(measureShort(widthMeasureSpec), measureLong(heightMeasureSpec));
    }
  }

  private int measureLong(int measureSpec) {
    int result;
    int specMode = MeasureSpec.getMode(measureSpec);
    int specSize = MeasureSpec.getSize(measureSpec);

    if ((specMode == MeasureSpec.EXACTLY)) {
      result = specSize;
    } else {
      result = (getPaddingLeft() + getPaddingRight()
          + (mSpaceWidth + mStarWidth) * (mStarMaxNumber));
      if (specMode == MeasureSpec.AT_MOST) {
        result = Math.min(result, specSize);
      }
    }
    return result;
  }

  private int measureShort(int measureSpec) {
    int result;
    int specMode = MeasureSpec.getMode(measureSpec);
    int specSize = MeasureSpec.getSize(measureSpec);

    if (specMode == MeasureSpec.EXACTLY) {
      result = specSize;
    } else {
      result = (mStarHeight + getPaddingTop() + getPaddingBottom());
      if (specMode == MeasureSpec.AT_MOST) {
        result = Math.min(result, specSize);
      }
    }
    return result;
  }
}
