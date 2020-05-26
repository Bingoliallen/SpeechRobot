package com.example.common.widget.pull2refresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;
import com.example.common.R;
import com.example.common.utils.BitmapUtil;
import com.example.common.utils.ViewUtil;

/**
 * Created by xxdr on 2018/1/9.
 */

public class TaobaoView extends View {

  //圆环进度颜色
  private int ringProgressColor;
  //圆环的宽度
  private float ringWidth;
  //最大值
  private int ringMax;
  //中间的icon
  private Bitmap ringImage;
  //中间X坐标
  private int centerX;
  //中间Y坐标
  private int centerY;
  //画笔
  private Paint mPaint;
  //View宽度
  private int width;
  //View高度
  private int height;
  //进度
  private int progress;
  //半径
  private int radius;
  //是否显示图标
  private boolean isShowIcon = true;
  private @ColorInt
  int color = Color.LTGRAY;
  private boolean stopProgress = false;

  public TaobaoView(Context context) {
    this(context, null);
  }

  public TaobaoView(Context context, @ColorInt int color) {
    super(context);
    this.color = color;
    init(context, null, 0);
  }

  public TaobaoView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TaobaoView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context, attrs, defStyleAttr);
  }

  private void init(Context context, AttributeSet attrs, int defStyleAttr) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TaobaoView);
    ringProgressColor = typedArray.getColor(R.styleable.TaobaoView_ringProgressColor, color);
    ringWidth = typedArray
        .getDimension(R.styleable.TaobaoView_ringWidth, ViewUtil.dp2px(getContext(), 1));
    ringMax = typedArray.getInteger(R.styleable.TaobaoView_ringmax, 100);
    ringImage = BitmapUtil.drawable2Bitmap(context, R.drawable.common_svg_arrow, ringProgressColor);
    mPaint = new Paint();
    mPaint.setColor(ringProgressColor);
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int widthSize = MeasureSpec.getSize(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    int heightSize = MeasureSpec.getSize(heightMeasureSpec);

    if (widthMode == MeasureSpec.AT_MOST) {
      width = ViewUtil.dp2px(getContext(), 26);
    } else {
      width = widthSize;
    }
    if (heightMode == MeasureSpec.AT_MOST) {
      height = ViewUtil.dp2px(getContext(), 26);
    } else {
      height = heightSize;
    }
    setMeasuredDimension(width, height);
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    //获取中心点的位置
    centerX = getWidth() / 2;
    centerY = getHeight() / 2;
    radius = (int) (centerX - ringWidth / 2);
  }


  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    //确定View的宽高
    width = w;
    height = h;
  }

  @Override
  public void draw(Canvas canvas) {
    super.draw(canvas);
    drawProgress(canvas);
    drawImage(canvas);
  }

  /**
   * 绘制图片
   */
  private void drawImage(Canvas canvas) {
    if (isShowIcon) {
      canvas.drawBitmap(ringImage, centerX - ringImage.getWidth() / 2,
          centerY - ringImage.getHeight() / 2, mPaint);
    }
  }

  /**
   * 绘制进度条
   */
  private void drawProgress(Canvas canvas) {
    mPaint.setAntiAlias(true);
    mPaint.setStrokeWidth(ringWidth);
    //设置画笔样式
    mPaint.setStyle(Paint.Style.STROKE);
    RectF rectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
    //绘制圆弧
    canvas.drawArc(rectF, -110, -360 * progress / ringMax, false, mPaint);
  }

  /**
   * 设置进度
   */
  public void setProgress(int progress) {
    if (!stopProgress) {
      if (progress < 0) {
        progress = 0;
      }
      if (progress >= ringMax) {
        progress = ringMax;
      }
      this.progress = progress;
      postInvalidate();
    }
  }

  /**
   * stop progress
   */
  public void stopProgress(boolean flag) {
    stopProgress = flag;
  }

  public boolean getStopProgress() {
    return stopProgress;
  }


  /**
   * 设置是否显示图标
   */
  public void setIsShowIcon(boolean isShow) {
    this.isShowIcon = isShow;
  }
}
