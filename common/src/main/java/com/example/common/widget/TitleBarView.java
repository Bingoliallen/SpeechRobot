package com.example.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.common.R;
import com.example.common.utils.DrawableUtil;
import com.example.common.utils.ViewUtil;

/**
 * Created by xxdr on 2017/8/7.
 */

public class TitleBarView extends RelativeLayout {

  public static final int LEFT_TEXT = 0;
  public static final int RIGHT_TEXT = 1;

  /**
   * Title 的属性
   */
  private int mTitleWidth = LayoutParams.WRAP_CONTENT;            //可单独set
  private int mTitleHeight = LayoutParams.WRAP_CONTENT;           //可单独set
  private String mTitleText;                                      //可单独set
  private float mTitleTextSize;                                   //可单独set
  private int mTitleTextColor;                                    //可单独set
  private boolean mTitleTextBold;                                 //可单独set

  /**
   * LeftTextView 的属性
   */
  private int mLeftWidth;             //可单独set
  private int mLeftHeight;            //可单独set
  private String mLeftText;           //可单独set
  private boolean mLeftTextBold;      //可单独set
  private float mLeftTextSize;        //可单独set
  private int mLeftTextColor;         //可单独set
  private Drawable mLeftDrawable;     //可单独set
  private int mLeftDrawableWidth;     //可单独set
  private int mLeftDrawableHeight;    //可单独set
  private int mLeftDrawableColor;     //可单独set

  /**
   * RightTextView 的属性
   */
  private int mRightWidth;            //可单独set
  private int mRightHeight;           //可单独set
  private String mRightText;          //可单独set
  private boolean mRightTextBold;     //可单独set
  private float mRightTextSize;       //可单独set
  private int mRightTextColor;        //可单独set
  private Drawable mRightDrawable;    //可单独set
  private int mRightDrawableWidth;    //可单独set
  private int mRightDrawableHeight;   //可单独set
  private int mRightDrawableColor;    //可单独set

  /**
   * 内部控件
   */
  private TextView mTvLeft;
  private TextView mTvRight;
  private TextView mTvTitle;

  private OnClickListener mOnClickListener;

  public TitleBarView(Context context) {
    this(context, null);
  }

  public TitleBarView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TitleBarView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    //获取自定义属性值
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView);
    mTitleText = typedArray.getString(R.styleable.TitleBarView_title);
    mTitleTextSize = typedArray.getDimension(R.styleable.TitleBarView_title_text_size, 20);
    mTitleTextBold = typedArray.getBoolean(R.styleable.TitleBarView_title_text_bold, false);
    mTitleTextColor = typedArray.getColor(R.styleable.TitleBarView_title_text_color, Color.BLACK);

    mLeftWidth = (int) typedArray
        .getDimension(R.styleable.TitleBarView_left_width, LayoutParams.WRAP_CONTENT);
    mLeftHeight = (int) typedArray
        .getDimension(R.styleable.TitleBarView_left_height, LayoutParams.WRAP_CONTENT);
    mLeftText = typedArray.getString(R.styleable.TitleBarView_left_text);
    mLeftTextBold = typedArray.getBoolean(R.styleable.TitleBarView_left_text_bold, false);
    mLeftTextSize = typedArray.getDimension(R.styleable.TitleBarView_left_text_size, 14);
    mLeftTextColor = typedArray.getColor(R.styleable.TitleBarView_left_text_color, Color.BLACK);
    mLeftDrawable = typedArray.getDrawable(R.styleable.TitleBarView_left_drawable);
    mLeftDrawableWidth = (int) typedArray
        .getDimension(R.styleable.TitleBarView_left_drawable_width,
            ViewUtil.dp2px(getContext(), 20));
    mLeftDrawableHeight = (int) typedArray
        .getDimension(R.styleable.TitleBarView_left_drawable_height,
            ViewUtil.dp2px(getContext(), 20));
    mLeftDrawableColor = typedArray.getColor(R.styleable.TitleBarView_left_drawable_color, -1);

    mRightWidth = (int) typedArray
        .getDimension(R.styleable.TitleBarView_right_width, LayoutParams.WRAP_CONTENT);
    mRightHeight = (int) typedArray
        .getDimension(R.styleable.TitleBarView_right_height, LayoutParams.WRAP_CONTENT);
    mRightText = typedArray.getString(R.styleable.TitleBarView_right_text);
    mRightTextBold = typedArray.getBoolean(R.styleable.TitleBarView_right_text_bold, false);
    mRightTextSize = typedArray.getDimension(R.styleable.TitleBarView_right_text_size, 14);
    mRightTextColor = typedArray.getColor(R.styleable.TitleBarView_right_text_color, Color.BLACK);
    mRightDrawable = typedArray.getDrawable(R.styleable.TitleBarView_right_drawable);
    mRightDrawableWidth = (int) typedArray
        .getDimension(R.styleable.TitleBarView_right_drawable_width,
            ViewUtil.dp2px(getContext(), 20));
    mRightDrawableHeight = (int) typedArray
        .getDimension(R.styleable.TitleBarView_right_drawable_height,
            ViewUtil.dp2px(getContext(), 20));
    mRightDrawableColor = typedArray.getColor(R.styleable.TitleBarView_right_drawable_color, -1);
    typedArray.recycle();

    if (!TextUtils.isEmpty(mTitleText)) {
      initTvTitle();
    }
    if (!TextUtils.isEmpty(mLeftText) || mLeftDrawable != null) {
      initTvLeft();
    }
    if (!TextUtils.isEmpty(mRightText) || mRightDrawable != null) {
      initTvRight();
    }
  }

  public TextView getTitleView() {
    return mTvTitle;
  }

  public TextView getRightView() {
    return mTvRight;
  }

  public void setOnClickListener(OnClickListener onClickListener) {
    mOnClickListener = onClickListener;
  }

  public void setTvVisable(int id, boolean flag) {
    if (id == LEFT_TEXT) {
      mTvLeft.setVisibility(flag ? VISIBLE : GONE);
    } else {
      mTvRight.setVisibility(flag ? VISIBLE : GONE);
    }
  }

  /**
   * 设置文字
   */
  public void setTitleText(String text) {
    mTitleText = text;
    if (mTvTitle == null) {
      initTvTitle();
    } else {
      mTvTitle.setText(mTitleText);
    }
  }

  public void setTitleTextBold(boolean bold) {
    mTitleTextBold = bold;
    if (mTvTitle != null) {
      mTvTitle
          .setTypeface(mTvTitle.getTypeface(), mTitleTextBold ? Typeface.BOLD : Typeface.NORMAL);
    }
  }

  public void setTitleTextColor(int color) {
    mTitleTextColor = color;
    if (mTvTitle != null) {
      mTvTitle.setTextColor(mTitleTextColor);
    }
  }

  public void setTitleTextSize(int size) {
    mTitleTextSize = size;
    if (mTvTitle != null) {
      mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleTextSize);
    }
  }

  public void setLeftText(String text) {
    mLeftText = text;
    if (mTvLeft == null) {
      initTvLeft();
    } else {
      mTvLeft.setText(mLeftText);
    }
  }

  public void setLeftTextBold(boolean bold) {
    mLeftTextBold = bold;
    if (mTvLeft != null) {
      mTvLeft.setTypeface(mTvLeft.getTypeface(), mLeftTextBold ? Typeface.BOLD : Typeface.NORMAL);
    }
  }

  public void setLetTextColor(int color) {
    mLeftTextColor = color;
    if (mTvLeft != null) {
      mTvLeft.setTextColor(mLeftTextColor);
    }
  }

  public void setLeftTextSize(int size) {
    mLeftTextSize = size;
    if (mTvLeft != null) {
      mTvLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftTextSize);
    }
  }

  public void setRightText(String text) {
    mRightText = text;
    if (mRightText == null) {
      initTvRight();
    } else {
      mTvRight.setText(mRightText);
    }
  }

  public void setRightTextBold(boolean bold) {
    mRightTextBold = bold;
    if (mRightText != null) {
      mTvRight
          .setTypeface(mTvRight.getTypeface(), mRightTextBold ? Typeface.BOLD : Typeface.NORMAL);
    }
  }

  public void setRightTextColor(int color) {
    mRightTextColor = color;
    if (mRightText != null) {
      mTvRight.setTextColor(mRightTextColor);
    }
  }

  public void setRightTextSize(int size) {
    mRightTextSize = size;
    if (mRightText != null) {
      mTvRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightTextSize);
    }
  }

  /**
   * 设置图片，颜色为 ColorInt，非 ColorRes
   */
  public void setLeftDrawable(Drawable drawable) {
    setLeftDrawable(drawable, -1, ViewUtil.dp2px(getContext(), 20),
        ViewUtil.dp2px(getContext(), 20));
  }

  public void setLeftDrawable(Drawable drawable, @ColorInt int drawableColor, int width,
      int height) {
    if (drawable != null) {
      mLeftDrawable = drawable;
      if (drawableColor != -1) {
        mLeftDrawableColor = drawableColor;
        mLeftDrawable = DrawableUtil.tintDrawable(mLeftDrawable, mLeftDrawableColor);
      }
      if (mTvLeft == null) {
        initTvLeft();
      } else {
        DrawableUtil.resize(mLeftDrawable, width, height);
        mTvLeft.setCompoundDrawables(mLeftDrawable, null, null, null);
      }
    }
  }

  public void setLeftDrawableColor(int drawableColor) {
    if (drawableColor != -1) {
      mLeftDrawableColor = drawableColor;
    }
    if (mLeftDrawable != null) {
      mLeftDrawable = DrawableUtil.tintDrawable(mLeftDrawable, mLeftDrawableColor);
      mTvLeft.setCompoundDrawables(mLeftDrawable, null, null, null);
    }
  }

  public void setRightDrawable(Drawable drawable) {
    setRightDrawable(drawable, -1, mRightWidth, mRightHeight);
  }

  public void setRightDrawable(Drawable drawable, @ColorInt int drawableColor, int width,
      int height) {
    if (drawable != null) {
      mRightDrawable = drawable;
      if (drawableColor != -1) {
        mRightDrawableColor = drawableColor;
        mRightDrawable = DrawableUtil.tintDrawable(mRightDrawable, mRightDrawableColor);
      }
      if (mTvRight == null) {
        initTvRight();
      } else {
        DrawableUtil.resize(mRightDrawable, width, height);
        mTvRight.setCompoundDrawables(mRightDrawable, null, null, null);
      }
    }
  }

  public void setRightDrawableColor(int drawableColor) {
    if (drawableColor != -1) {
      mRightDrawableColor = drawableColor;
    }
    if (mRightDrawable != null) {
      mRightDrawable = DrawableUtil.tintDrawable(mRightDrawable, mRightDrawableColor);
      mTvRight.setBackground(mRightDrawable);
    }
  }

  /**
   * 设置大小
   */
  public void setTvTitleSize(int width, int height) {
    mTitleWidth = width;
    mTitleHeight = height;
    if (mTvTitle == null) {
      initTvLeft();
    } else {
      LayoutParams params = (LayoutParams) mTvTitle.getLayoutParams();
      params.width = mTitleWidth;
      params.height = mTitleHeight;
      mTvTitle.setLayoutParams(params);
    }
  }

  public void setTvLeftSize(int width, int height) {
    mLeftWidth = width;
    mLeftHeight = height;
    if (mTvLeft == null) {
      initTvLeft();
    } else {
      LayoutParams params = (LayoutParams) mTvLeft.getLayoutParams();
      params.width = mLeftWidth;
      params.height = mLeftHeight;
      mTvLeft.setLayoutParams(params);
    }
  }

  public void setTvRightSize(int width, int height) {
    mRightWidth = width;
    mRightHeight = height;
    if (mTvRight == null) {
      initTvRight();
    } else {
      LayoutParams params = (LayoutParams) mTvRight.getLayoutParams();
      params.width = mRightWidth;
      params.height = mRightHeight;
      mTvRight.setLayoutParams(params);
    }
  }

  private void initTvTitle() {
    mTvTitle = new TextView(getContext());
    addTextView(mTvTitle, mTitleWidth, mTitleHeight,
        new int[]{RelativeLayout.CENTER_IN_PARENT});
    setTextViewData(mTvTitle, mTitleText, mTitleTextSize, mTitleTextColor, mTitleTextBold, null, 0,
        mTitleWidth, mTitleHeight);
  }

  private void initTvLeft() {
    mTvLeft = new TextView(getContext());
    mTvLeft.setGravity(Gravity.CENTER_VERTICAL);
    mTvLeft.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnClickListener != null) {
          mOnClickListener.leftClick();
        }
      }
    });
    addTextView(mTvLeft, mLeftWidth, mLeftHeight,
        new int[]{RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.CENTER_VERTICAL});
    setTextViewData(mTvLeft, mLeftText, mLeftTextSize, mLeftTextColor, mLeftTextBold, mLeftDrawable,
        mLeftDrawableColor, mLeftDrawableWidth, mLeftDrawableHeight);
  }

  private void initTvRight() {
    mTvRight = new TextView(getContext());
    mTvRight.setGravity(Gravity.CENTER_VERTICAL);
    mTvRight.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mOnClickListener != null) {
          mOnClickListener.rightClick();
        }
      }
    });
    addTextView(mTvRight, mRightWidth, mRightHeight,
        new int[]{RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.CENTER_VERTICAL});
    setTextViewData(mTvRight, mRightText, mRightTextSize, mRightTextColor, mRightTextBold,
        mRightDrawable, mRightDrawableColor, mRightDrawableWidth, mRightDrawableHeight);
  }

  /**
   * 设置控件数据
   *
   * @param textView 控件
   * @param text 文字
   * @param textSize 文字大小
   * @param textColor 文字颜色
   * @param isBold 是否加粗
   * @param drawable 背景图片
   * @param drawableColor 背景图片颜色
   */
  private void setTextViewData(TextView textView,
      String text, float textSize, int textColor, boolean isBold,
      Drawable drawable, int drawableColor, int width, int height) {
    textView.setText(text);
    textView.setGravity(Gravity.CENTER);
    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
    textView.setTextColor(textColor);
    textView.setTypeface(textView.getTypeface(), isBold ? Typeface.BOLD : Typeface.NORMAL);
    DrawableUtil.resize(drawable, width, height);
    if (drawable != null) {
      if (drawableColor != -1) {
        textView
            .setCompoundDrawables(DrawableUtil.tintDrawable(drawable, drawableColor), null, null,
                null);
      } else {
        textView.setCompoundDrawables(drawable, null, null, null);
      }
    }
  }

  /**
   * 设置控件的布局
   *
   * @param textView 控件
   * @param width 宽
   * @param height 高
   * @param rules 规则
   */
  private void addTextView(TextView textView, int width, int height, int[] rules) {
    LayoutParams layoutParam;
    layoutParam = new LayoutParams(width, height);
    for (int i = 0; i < rules.length; i++) {
      layoutParam.addRule(rules[i], TRUE);
    }
    addView(textView, layoutParam);
  }

  /**
   * 事件监听
   */
  public interface OnClickListener {

    void leftClick();

    void rightClick();
  }
}