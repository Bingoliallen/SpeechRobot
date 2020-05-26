package com.example.common.view.activity;

import android.Manifest.permission;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import com.example.common.presenter.impl.BasePresenter;
import com.example.common.utils.PermissionUtil;
import com.example.common.utils.PictureUtil;
import com.example.common.widget.dialog.ImageSourceDialog;

/**
 * Created by xxdr on 2017/12/4.
 */
public abstract class BasePictureActivity<T extends BasePresenter> extends BaseActivity<T> {

  public static final int CROP = 1;
  public static final int SQUARE = 2;

  private ImageSourceDialog mWindow;

  /**
   * 解决三星拍照旋转导致再次 onCreate，搭配 manifests 文件中 activity 组件的三行配置代码
   * android:configChanges="orientation|screenSize|keyboardHidden|navigation"
   * android:windowSoftInputMode="adjustResize"
   * android:screenOrientation="portrait"
   */
  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

  @Override
  protected void initView() {
    super.initView();
    mPermissionUtil = new PermissionUtil(this, permission.CAMERA,
        permission.WRITE_EXTERNAL_STORAGE);
    mWindow = new ImageSourceDialog(this, new ImageSourceDialog.ClickListener() {
      @Override
      public void onCamera() {
        PictureUtil.takePicture(BasePictureActivity.this);
      }

      @Override
      public void onAlbum() {
        PictureUtil.pickPicture(BasePictureActivity.this);
      }

      @Override
      public void onCancel() {
      }
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
      switch (requestCode) {
        // 小图切割
        case PictureUtil.REQUEST_IMAGE_CROP:
          setImageViewByUri(PictureUtil.result);
          break;
        // 相册选取
        case PictureUtil.REQUEST_IMAGE_GET:
          if ((configuration() & CROP) == CROP) {
            PictureUtil.cropPicture(this, data.getData(), (configuration() & SQUARE) == SQUARE);
          } else {
            setImageViewByUri(data.getData());
          }
          break;
        // 拍照
        case PictureUtil.REQUEST_IMAGE_CAPTURE:
          if ((configuration() & CROP) == CROP) {
            PictureUtil.cropPicture(this, PictureUtil.result, (configuration() & SQUARE) == SQUARE);
          } else {
            setImageViewByUri(PictureUtil.result);
          }
          break;
      }
    }
  }

  protected void showDialog() {
    mWindow.showAtBottom();
  }

  /**
   * 子类重写这个方法来显示图片
   */
  protected abstract void setImageViewByUri(Uri uri);

  /**
   * 子类重写这个方法来设置裁剪
   */
  protected abstract int configuration();

  // AndroidManifest.xml
  // <provider
  //     android:name="android.support.v4.content.FileProvider"
  //     android:authorities="${applicationId}.provider"
  //     android:exported="false"
  //     android:grantUriPermissions="true">
  //         <meta-data
  //             android:name="android.support.FILE_PROVIDER_PATHS"
  //             android:resource="@xml/provider_paths" />
  // </provider>

  // main/res/xml/provider_paths.xml
  // <?xml version="1.0" encoding="utf-8"?>
  // <paths>
  //     <external-path
  //             name="my_pictures"
  //             path="Android/data/com.example.xxdr.common/files/Pictures/" />
  // </paths>
}