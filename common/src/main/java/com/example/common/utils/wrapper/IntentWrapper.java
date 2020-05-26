package com.example.common.utils.wrapper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import com.example.common.utils.FileUtil;
import java.io.File;

/**
 * Created by xxdr on 2017/12/1.
 */

public class IntentWrapper {

  private static final String TAG = "IntentWrapper";

  private IntentWrapper() {
  }

  public static boolean startActivity(Context context, Intent intent) {
    if (intent.resolveActivity(context.getPackageManager()) != null) {
      context.startActivity(intent);
      return true;
    }
    return false;
  }

  public static boolean startActivityForResult(Activity activity, Intent intent, int requestCode) {
    if (intent.resolveActivity(activity.getPackageManager()) != null) {
      activity.startActivityForResult(intent, requestCode);
      return true;
    }
    return false;
  }

  /**
   * 打开home界面
   */
  public static void startHome(Context context) {
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addCategory(Intent.CATEGORY_HOME);
    startActivity(context, intent);
  }

  /**
   * 拨号
   */
  public static void call(Context context, String tel, boolean direct) {
    if (direct) {
      Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tel));
      startActivity(context, intent);
    } else {
      Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(context, intent);
    }
  }

  /**
   * 发送短信
   */
  public static void sendSMS(Context context, String phone, String content) {
    Intent it = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
    it.putExtra("sms_body", content);
    startActivity(context, it);
  }

  /**
   * 启动APP的设置
   */
  public static void startAppSettings(Context context) {
    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
    intent.setData(Uri.parse("package:" + context.getPackageName()));
    startActivity(context, intent);
  }

  /**
   * 启动系统的设置
   */
  public static void startSystemSettings(Context context) {
    Intent intent = new Intent(Settings.ACTION_SETTINGS);
    startActivity(context, intent);
  }


  /**
   * 打开qq聊天界面
   */
  public static void openQQChat(Context context, String qq) {
    String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
    Intent it = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(context, it);
  }

  /**
   * 打开文件
   */
  public static void openFile(Context context, String filePath) {
    Intent intent = new Intent("android.intent.action.VIEW");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri uri = FileUtil.file2Uri(context, new File(filePath));
    intent.setDataAndType(uri, "text/plain");
    startActivity(context, intent);
  }


  /**
   * 拍照
   */
  public static void takePicture(Context context, Uri uri, int requestCode) {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
    startActivityForResult((Activity) context, intent, requestCode);
  }

  /**
   * 图片查看器
   */
  public static void pickPicture(Context context, int requestCode) {
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");
    startActivityForResult((Activity) context, intent, requestCode);
  }

  /**
   * 图片查看器
   */
  public static void pickPicture(Fragment fragment, int requestCode) {
    Intent intent = new Intent(Intent.ACTION_PICK);
    intent.setType("image/*");
    fragment.startActivityForResult(intent, requestCode);
  }

  /**
   * 图片裁剪器
   */
  public static void cropPicture(Context context, Uri sourceUri, Uri outputUri, boolean isSquare,
      int requestCode) {
    // 开始切割
    Intent intent = new Intent("com.android.camera.action.CROP");
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
      intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    }
    intent.setDataAndType(sourceUri, "image/*");
    intent.putExtra("crop", "true");
    if (isSquare) {
      intent.putExtra("aspectX", 1); // 裁剪框比例
      intent.putExtra("aspectY", 1);
    }
    //intent.putExtra("outputX", 600); // 输出图片大小
    //intent.putExtra("outputY", 600);
    intent.putExtra("scale", true);
    intent.putExtra("return-data", false); // 不直接返回数据
    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri); // 返回一个文件
    intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
    startActivityForResult((Activity) context, intent, requestCode);
  }

  /**
   * 适配 Android N 第三方应用打开文件的问题
   */
  // AndroidManifest.xml
  // <provider
  //      android:authorities="${applicationId}.fileprovider"
  //      android:exported="false"
  //      android:grantUriPermissions="true"
  //      android:name="android.support.v4.content.FileProvider">
  //          <meta-data
  //              android:name="android.support.FILE_PROVIDER_PATHS"
  //              android:resource="@xml/file_paths"/>
  //  </provider>

  // main/res/xml/file_paths.xml
  // <?xml version="1.0" encoding="utf-8"?>
  // <paths>
  //     <external-files-path
  //         name="CrashUtil"
  //         path="CrashUtil" />
  // </paths>
}
