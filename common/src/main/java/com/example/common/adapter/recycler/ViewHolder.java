package com.example.common.adapter.recycler;

import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.text.Spanned;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.common.image.ImageWrapper;

public class ViewHolder extends RecyclerView.ViewHolder {

  public int viewType;
  private View mConvertView;
  private SparseArray<View> mViews;

  public ViewHolder(View view) {
    super(view);
    mConvertView = view;
    mViews = new SparseArray<>();
  }

  public View getView() {
    return mConvertView;
  }

  /**
   * 通过控件ID获取对应控件
   */
  @SuppressWarnings("unchecked")
  public <T extends View> T getView(int viewId) {
    View view = mViews.get(viewId);
    if (view == null) {
      view = mConvertView.findViewById(viewId);
      mViews.put(viewId, view);
    }
    return (T) view;
  }

  public ViewHolder setText(int viewId, String text) {
    TextView tv = getView(viewId);
    tv.setText(text);
    return this;
  }

  public ViewHolder setText(int viewId, @StringRes int resId) {
    TextView tv = getView(viewId);
    tv.setText(resId);
    return this;
  }

  public ViewHolder setText(int viewId, Spanned spanned) {
    TextView tv = getView(viewId);
    tv.setText(spanned);
    return this;
  }

  public ViewHolder setImage(int viewId, @DrawableRes int resId) {
    ImageView iv = getView(viewId);
    iv.setImageResource(resId);
    return this;
  }

  public ViewHolder setImage(int viewId, Bitmap bm) {
    ImageView iv = getView(viewId);
    iv.setImageBitmap(bm);
    return this;
  }

  public ViewHolder setImage(int viewId, String url) {
    ImageView iv = getView(viewId);
    ImageWrapper.setImage(iv, url);
    return this;
  }
}
