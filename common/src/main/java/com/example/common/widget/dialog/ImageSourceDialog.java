package com.example.common.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.example.common.R;
import com.example.common.widget.BaseDialog;

/**
 * Created by xxdr on 2018/4/11.
 */

public class ImageSourceDialog extends BaseDialog {

  private TextView mTvCamera;
  private TextView mTvAlbum;
  private TextView mTvCancel;

  private ClickListener mListener;

  public ImageSourceDialog(Context context, ClickListener listener) {
    super(context, R.layout.common_image_source_dialog);
    mListener = listener;
  }

  @Override
  public void initContentView() {
    mTvCamera = getView(R.id.tv_camera);
    mTvAlbum = getView(R.id.tv_album);
    mTvCancel = getView(R.id.tv_cancel);

    initEvent();
  }

  private void initEvent() {
    if (mListener != null) {
      mTvCamera.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mListener.onCamera();
          dismiss();
        }
      });
      mTvAlbum.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mListener.onAlbum();
          dismiss();
        }
      });
      mTvCancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          mListener.onCancel();
          dismiss();
        }
      });
    }
  }

  public interface ClickListener {

    void onCamera();

    void onAlbum();

    void onCancel();
  }
}
