package com.example.common.utils;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import com.example.common.utils.wrapper.IntentWrapper;

/**
 * Created by xxdr on 2018/4/12.
 */

public class PictureUtil {

  public static final int REQUEST_IMAGE_GET = 0;
  public static final int REQUEST_IMAGE_CAPTURE = 1;
  public static final int REQUEST_IMAGE_CROP = 2;

  private static final String IMAGE_FOLDER = "Pictures";
  private static final String IMAGE_NAME = "CommonPicture";
  public static Uri result;
  private static Uri mPictureUri;
  private static Uri mCropPictureUri;

  public static void takePicture(Context context) {
    mPictureUri = PictureUtil.createPictureUri(context);
    result = mPictureUri;
    IntentWrapper.takePicture(context, mPictureUri, REQUEST_IMAGE_CAPTURE);
  }

  public static void pickPicture(Context context) {
    IntentWrapper.pickPicture(context, PictureUtil.REQUEST_IMAGE_GET);
  }

  public static void pickPicture(Fragment fragment) {
    IntentWrapper.pickPicture(fragment, PictureUtil.REQUEST_IMAGE_GET);
  }

  public static void cropPicture(Context context, Uri srcUri, boolean isSquare) {
    mCropPictureUri = PictureUtil.createPictureUri(context);
    result = mCropPictureUri;
    IntentWrapper
        .cropPicture(context, srcUri, mCropPictureUri, isSquare, PictureUtil.REQUEST_IMAGE_CROP);
  }

  /**
   * 创建一条图片地址uri,用于保存拍照后的照片
   */
  public static Uri createPictureUri(Context context) {
    final Uri imageFilePath;
    long time = System.currentTimeMillis();
    String imageName = String.format("%s%d", IMAGE_NAME, time);
    if (DeviceUtil.isSDCardMounted()) {
      String path = String
          .format("%s/%s%d.jpeg", context.getExternalFilesDir(IMAGE_FOLDER).getAbsolutePath(),
              IMAGE_NAME, time);
      ContentValues values = new ContentValues(5);
      values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
      values.put(MediaStore.Images.Media.DATA, path);
      values.put(MediaStore.Images.Media.TITLE, imageName);
      values.put(MediaStore.Images.Media.DATE_TAKEN, time);
      values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
      imageFilePath = context.getContentResolver()
          .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    } else {
      ContentValues values = new ContentValues(4);
      values.put(MediaStore.Images.Media.DISPLAY_NAME, imageName);
      values.put(MediaStore.Images.Media.TITLE, imageName);
      values.put(MediaStore.Images.Media.DATE_TAKEN, time);
      values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
      imageFilePath = context.getContentResolver()
          .insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, values);
    }
    return imageFilePath;
  }
}
