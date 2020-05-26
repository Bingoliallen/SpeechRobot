package com.example.common.widget.viewpager_indicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.common.R;
import com.example.common.utils.DeviceUtil;
import java.util.List;

/**
 * Created by xxdr on 2018/3/27.
 */
public class ViewPagerIndicator extends HorizontalScrollView {

  private List<String> mTitles;
  private ViewPager mViewPager;
  private OnPageChangeListener mOnPageChangeListener;
  private OnDrawRuleListener mOnDrawRuleListener;

  private int mTextColorNormal;
  private int mTextColorHighLight;
  private int mTextSizeNormal;
  private int mTextSizeHighLight;
  private Paint mPaint;
  private Path mPath;

  private LinearLayout mLinearLayout;
  private int mVisibleItemCount;
  private int mTabWidth;
  private int mItemPadding;
  private int mTranslationX;
  private int mPosition;

  public ViewPagerIndicator(Context context) {
    super(context);
  }

  public ViewPagerIndicator(Context context, AttributeSet attrs) {
    super(context, attrs);
    setHorizontalScrollBarEnabled(false);
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ViewPagerIndicator);
    mVisibleItemCount = a.getInt(R.styleable.ViewPagerIndicator_visible_tab_count, 0);
    mItemPadding = (int) a.getDimension(R.styleable.ViewPagerIndicator_item_padding, 0);
    int paintColor = a.getColor(R.styleable.ViewPagerIndicator_paint_color, Color.BLACK);
    mTextColorNormal = a.getColor(R.styleable.ViewPagerIndicator_text_color_normal, Color.LTGRAY);
    mTextColorHighLight = a
        .getColor(R.styleable.ViewPagerIndicator_text_color_highlight, Color.BLACK);
    mTextSizeNormal = (int) a.getDimension(R.styleable.ViewPagerIndicator_text_size_normal, 18);
    mTextSizeHighLight = (int) a
        .getDimension(R.styleable.ViewPagerIndicator_text_size_highlight, 18);
    mPaint = new Paint();
    mPaint.setAntiAlias(true);
    mPaint.setColor(paintColor);
    mPaint.setPathEffect(new CornerPathEffect(3));
    a.recycle();
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    if (mPath == null) {
      mPath = new Path();
    } else {
      mPath.reset();
    }
    if (mOnDrawRuleListener != null) {
      mOnDrawRuleListener
          .onDrawRule(getContext(), mPath, mLinearLayout.getChildAt(mPosition).getWidth());
    }
    canvas.save();
    canvas.translate(mTranslationX, getHeight());
    canvas.drawPath(mPath, mPaint);
    canvas.restore();
    super.dispatchDraw(canvas);
  }

  /**
   * 指示符跟随手指进行滚动
   */
  private void scroll(int position, float positionOffset) {
    View selectedChild = mLinearLayout.getChildAt(position);
    mTranslationX = selectedChild.getLeft() + selectedChild.getWidth() / 2;
    if (positionOffset > 0f && position < mLinearLayout.getChildCount() - 1) {
      // Draw the selection partway between the tabs
      View nextChild = mLinearLayout.getChildAt(position + 1);
      mTranslationX = (int) (positionOffset * (nextChild.getLeft() + nextChild.getWidth() / 2)
          + (1.0f - positionOffset) * mTranslationX);
    }
    invalidate();// 重绘控件
  }


  /**
   * 设置高亮文本
   */
  private void setTextColor(int position) {
    int n = mLinearLayout.getChildCount();
    for (int i = 0; i < n; i++) {
      View view = mLinearLayout.getChildAt(i);
      if (view instanceof TextView) {
        if (i == position) {
          ((TextView) view).setTextColor(mTextColorHighLight);
          ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSizeHighLight);
        } else {
          ((TextView) view).setTextColor(mTextColorNormal);
          ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSizeNormal);
        }
      }
    }
  }

  /**
   * 设置标签点击事件
   */
  private void setOnItemClickEvent(int position) {
    if (mViewPager != null) {
      mViewPager.setCurrentItem(position);
    }
  }

  /**
   * 根据标题添加标签
   */
  public void addTitles(List<String> titles) {
    removeAllViews();
    int screenWidth = DeviceUtil.getDeviceWidth();
    if (mVisibleItemCount > 0) {
      mTabWidth = screenWidth / mVisibleItemCount;
    }
    if (titles != null) {
      mTitles = titles;
      mLinearLayout = new LinearLayout(getContext());
      mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
      for (int i = 0; i < titles.size(); i++) {
        String title = titles.get(i);
        TextView tv = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT);
        if (mVisibleItemCount > 0) {
          lp.width = mTabWidth;
        }
        tv.setPadding(mItemPadding, 0, mItemPadding, 0);
        tv.setText(title);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tv.setTextColor(mTextColorNormal);
        tv.setLayoutParams(lp);
        final int j = i;
        tv.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
            setOnItemClickEvent(j);
          }
        });
        mLinearLayout.addView(tv);
      }
      addView(mLinearLayout);
    }
  }

  /**
   * 设置 ViewPager
   */
  public void setViewPager(ViewPager viewPager, int position) {
    mViewPager = viewPager;
    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mPosition = position;
        scroll(position, positionOffset);
        final View selectedChild = mLinearLayout.getChildAt(position);
        final View nextChild = position + 1 < mLinearLayout.getChildCount() ?
            mLinearLayout.getChildAt(position + 1) : null;
        final int selectedWidth = selectedChild != null ? selectedChild.getWidth() : 0;
        final int nextWidth = nextChild != null ? nextChild.getWidth() : 0;

        // base scroll amount: places center of tab in center of parent
        int scrollBase = selectedChild.getLeft() + (selectedWidth / 2) - (getWidth() / 2);
        // offset amount: fraction of the distance between centers of tabs
        int scrollOffset = (int) ((selectedWidth + nextWidth) * 0.5f * positionOffset);

        scrollTo((ViewCompat.getLayoutDirection(ViewPagerIndicator.this)
            == ViewCompat.LAYOUT_DIRECTION_LTR) ? scrollBase + scrollOffset
            : scrollBase - scrollOffset, 0);
        if (mOnPageChangeListener != null) {
          mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
      }

      @Override
      public void onPageSelected(int position) {
        setTextColor(position);
        if (mOnPageChangeListener != null) {
          mOnPageChangeListener.onPageSelected(position);
        }
      }

      @Override
      public void onPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
          mOnPageChangeListener.onPageScrollStateChanged(state);
        }
      }
    });
    viewPager.setCurrentItem(position);
    setTextColor(position);
  }

  public void setOnPageChangeListener(OnPageChangeListener listener) {
    mOnPageChangeListener = listener;
  }

  public void setOnDrawRuleListener(OnDrawRuleListener listener) {
    mOnDrawRuleListener = listener;
  }

  /**
   * 给用户提供被 indicator 占用的 ViewPager 的接口
   */
  public interface OnPageChangeListener {

    void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

    void onPageSelected(int position);

    void onPageScrollStateChanged(int state);
  }

  /**
   * 给用户提供 indicator 的绘画接口
   */
  public interface OnDrawRuleListener {

    void onDrawRule(Context context, Path path, int tabWidth);
  }
}