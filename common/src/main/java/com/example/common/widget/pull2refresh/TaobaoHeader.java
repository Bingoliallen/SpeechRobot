package com.example.common.widget.pull2refresh;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.common.R;
import com.example.common.utils.ViewUtil;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;

/**
 * Created by xxdr on 2018/1/9.
 */

public class TaobaoHeader extends RelativeLayout implements RefreshHeader {

  private TaobaoView mTaobaoView;
  private TextView mTextView;
  private RotateAnimation mRefreshingAnimation;
  private RefreshKernel mRefreshKernel;
  private int mColor = Color.LTGRAY;

  public TaobaoHeader(Context context) {
    this(context, null);
  }

  public TaobaoHeader(Context context, @ColorInt int color) {
    super(context);
    mColor = color;
    initView(context);
  }

  public TaobaoHeader(Context context, @Nullable AttributeSet attrs) {
    this(context, null, 0);
  }

  public TaobaoHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initView(context);
  }

  private void initView(Context context) {
    LinearLayout linearLayout = new LinearLayout(context);
    LinearLayout.LayoutParams linearLayoutLP = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
    linearLayout.setGravity(Gravity.CENTER_VERTICAL);
    linearLayout.setLayoutParams(linearLayoutLP);

    LayoutParams relativeLayoutLP = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewUtil.dp2px(getContext(), 70));
    relativeLayoutLP.addRule(CENTER_IN_PARENT);
    addView(linearLayout, relativeLayoutLP);

    mTaobaoView = new TaobaoView(context, mColor);
    mTaobaoView.setProgress(0);
    mRefreshingAnimation = (RotateAnimation) AnimationUtils
        .loadAnimation(context, R.anim.common_taobao_header_rotate);
    LinearInterpolator lir = new LinearInterpolator();
    mRefreshingAnimation.setInterpolator(lir);
    LinearLayout.LayoutParams taobaoViewLP = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    mTaobaoView.setLayoutParams(taobaoViewLP);
    linearLayout.addView(mTaobaoView);

    mTextView = new TextView(context);
    mTextView.setText("下拉刷新");
    mTextView.setTextSize(12);
    mTextView.setTextColor(mColor);
    mTextView.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams textViewLP = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    textViewLP.setMargins(ViewUtil.dp2px(getContext(), 15), 0, 0, 0);
    mTextView.setLayoutParams(textViewLP);
    linearLayout.addView(mTextView);
  }

  @Override
  public void onPullingDown(float percent, int offset, int headerHeight, int extendHeight) {
    double newPercent = (offset - ViewUtil.dp2px(getContext(), 10)) * 1.0 / headerHeight;
    newPercent = newPercent * 100;
    //mTaobaoView.stopProgress(false);
    if (mRefreshKernel != null) {
      if (mRefreshKernel.getRefreshLayout().getState() == RefreshState.ReleaseToRefresh) {
        mTextView.setText("释放刷新");
        //mTaobaoView.stopProgress(true);
      }
    }
    //mTaobaoView.setProgress((int) newPercent);
    mTaobaoView.setProgress((int) Math.min(newPercent, 86));
  }

  @Override
  public void onReleasing(float percent, int offset, int headerHeight, int extendHeight) {
  }

  @Override
  public void onRefreshReleased(RefreshLayout layout, int headerHeight, int extendHeight) {
    mTextView.setText("正在刷新");
    mTaobaoView.setIsShowIcon(false);
    mTaobaoView.startAnimation(mRefreshingAnimation);
  }

  @NonNull
  @Override
  public View getView() {
    return this;
  }

  @NonNull
  @Override
  public SpinnerStyle getSpinnerStyle() {
    return SpinnerStyle.Scale;
  }

  @Override
  public void setPrimaryColors(@ColorInt int... colors) {

  }

  @Override
  public void onInitialized(@NonNull RefreshKernel kernel, int height, int extendHeight) {
    mRefreshKernel = kernel;
  }

  @Override
  public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {
  }

  @Override
  public void onStartAnimator(@NonNull RefreshLayout layout, int height, int extendHeight) {
  }

  @Override
  public int onFinish(@NonNull RefreshLayout layout, boolean success) {
    return 0;
  }

  @Override
  public boolean isSupportHorizontalDrag() {
    return false;
  }

  @Override
  public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState,
      RefreshState newState) {
    if (newState.draging) {
      mTaobaoView.setIsShowIcon(true);
      mRefreshingAnimation.cancel();
      mTextView.setText("下拉刷新");
    }
  }
}

