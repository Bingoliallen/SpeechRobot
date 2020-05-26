package com.example.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.common.R;
import com.example.common.utils.DeviceUtil;
import com.example.common.utils.ValueUtil;
import com.example.common.utils.ViewUtil;
import java.util.LinkedHashMap;


/**
 * Created by xxdr on 2017/11/4.
 */

public class NavView extends LinearLayout {

  private LinkedHashMap<String, Integer> mTabs;
  private int mItemTextSize;
  private int mTextColor;
  private int mCurrentIndex = -1;

  public NavView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavView);
    mItemTextSize = (int) typedArray
        .getDimension(R.styleable.NavView_itemsTextSize, ViewUtil.sp2px(getContext(), 11));
    mTextColor = typedArray
        .getResourceId(R.styleable.NavView_text_color, R.color.common_nav_view_text);
    typedArray.recycle();
  }

  public void setData(LinkedHashMap<String, Integer> tabs) {
    mTabs = tabs;
    if (mTabs != null) {
      initView();
    }
  }

  /**
   * 为控件设置点击事件
   */
  public void setOnClickListener(final OnClickListener listener) {
    if (listener == null) {
      return;
    }
    int size = getItemSize();
    if (size > 0) {
      for (int i = 0; i < size; i++) {
        Button button = findViewById(i);
        button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            changeItemStatus(v.getId());
            listener.onClick(v);
            mCurrentIndex = v.getId();
          }
        });
      }
    }
  }

  /**
   * 返回 item 的数量
   */
  public int getItemSize() {
    if (mTabs != null && mTabs.keySet().size() > 0) {
      return mTabs.keySet().size();
    }
    return 0;
  }

  /**
   * 点击第一个按钮
   */
  public void clickFirstItem() {
    findViewById(0).callOnClick();
  }

  /**
   * 初始化
   */
  private void initView() {
    int size = getItemSize();
    final int width = DeviceUtil.getDeviceWidth() / size;
    ValueUtil.map2traversal(mTabs, new ValueUtil.MapTraversalCallBack<String, Integer>() {

      @Override
      public void call(String key, Integer value, int index) {
        int length = ViewUtil.dp2px(getContext(), 20);
        Button button = new Button(getContext());
        button.setBackground(null);
        button.setText(key);
        button.setGravity(Gravity.CENTER);
        button.setTextColor(getResources().getColorStateList(mTextColor));

        Drawable drawable = getResources().getDrawable(value);
        drawable.setBounds(0, 0, length, length);
        button.setCompoundDrawables(null, drawable, null, null);

        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, mItemTextSize);
        button.setPadding(0, ViewUtil.dp2px(getContext(), 8), 0, ViewUtil.dp2px(getContext(), 4));
        button.setId(index);

        LayoutParams layoutParams = new LayoutParams(width, LayoutParams.MATCH_PARENT);
        addView(button, layoutParams);
      }
    });
  }

  /**
   * 修改 item 的状态
   */
  private void changeItemStatus(int viewId) {
    if (viewId < 0) {
      return;
    }
    if (viewId == mCurrentIndex) {
      return;
    }
    if (mCurrentIndex >= 0) {
      Button btnLast = findViewById(mCurrentIndex);
      btnLast.setSelected(false);
    }
    Button btnCurrent = findViewById(viewId);
    btnCurrent.setSelected(true);
  }

  public interface OnClickListener {

    void onClick(View v);
  }
}
