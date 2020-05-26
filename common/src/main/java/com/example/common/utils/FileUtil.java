package com.example.common.utils;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import com.example.common.BaseAppProfile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件工具类
 */
public class FileUtil {

  public enum FileType {
    IMG,
    AUDIO,
    VIDEO,
    FILE,
  }

  private static final String TAG = "FileUtil";
  private static File CACHE_DIR = !DeviceUtil.isSDCardMounted() ?
      BaseAppProfile.getApplication().getFilesDir() :
      BaseAppProfile.getApplication().getExternalCacheDir();

  private FileUtil() {
    /* cannot be instantiated */
    throw new UnsupportedOperationException("cannot be instantiated");
  }

  private static String getFileProviderName(Context context) {
    return context.getPackageName() + ".fileprovider";
  }


  /**
   * 创建临时文件
   */
  public static File getTempFile(FileType type) {
    try {
      File file = File.createTempFile(type.toString(), null, CACHE_DIR);
      file.deleteOnExit();
      return file;
    } catch (IOException e) {
      return null;
    }
  }


  /**
   * 获取缓存文件地址
   */
  public static String getCacheFilePath(String fileName) {
    return CACHE_DIR.getAbsolutePath() + File.separator + fileName;
  }


  /**
   * 判断缓存文件是否存在
   */
  public static boolean isCacheFileExist(String fileName) {
    File file = new File(getCacheFilePath(fileName));
    return file.exists();
  }


  /**
   * 保存数据到指定路径
   */
  public static boolean createFile(byte[] data, String filePath) {
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    try {
      File f = new File(filePath);
      if (!f.getParentFile().exists()) {//创建文件夹
        f.getParentFile().mkdirs();
      }
      if (!f.exists()) {//创建文件
        f.createNewFile();
      }
      fos = new FileOutputStream(f);
      bos = new BufferedOutputStream(fos);
      bos.write(data);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      IOUtil.close(bos);
      IOUtil.close(fos);
    }
    return true;
  }


  /**
   * delete file or folder
   */
  public static boolean deleteFile(File f) {
    if (f == null) {
      return false;
    }
    if (f.isDirectory()) {
      String[] children = f.list();
      for (int i = 0; i < children.length; i++) {
        boolean success = deleteFile(new File(f, children[i]));
        if (!success) {
          return false;
        }
      }
    }
    if (f.isFile() && !f.exists()) {
      return false;
    }
    return f.delete();
  }


  /**
   * 获取文件夹大小
   */
  public static long getFolderSize(File file) throws Exception {
    long size = 0;
    try {
      File[] fileList = file.listFiles();
      for (int i = 0; i < fileList.length; i++) {
        // 如果下面还有文件
        if (fileList[i].isDirectory()) {
          size = size + getFolderSize(fileList[i]);
        } else {
          size = size + fileList[i].length();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return size;
  }


  /**
   * 截取文件名
   */
  public static String getFileName(String path) {
    if (TextUtils.isEmpty(path)) {
      return null;
    }
    int start = path.lastIndexOf("/");
    if (start != -1) {
      return path.substring(start + 1);
    } else {
      return null;
    }
  }


  /**
   * 将数据存储为文件
   *
   * @param data 数据
   * @param fileName 文件名
   * @param type 文件类型
   */
  public static File createFile(byte[] data, String fileName, String type) {
    if (DeviceUtil.isSDCardMounted()) {
      File dir = BaseAppProfile.getApplication().getExternalFilesDir(type);
      if (dir != null) {
        File f = new File(dir, fileName);
        try {
          if (f.createNewFile()) {
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(data);
            fos.flush();
            fos.close();
            return f;
          }
        } catch (IOException e) {
          Log.e(TAG, "create file error" + e);
          return null;
        }
      }
    }
    return null;
  }


  public static Uri file2Uri(Context context, File file) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      return FileProvider.getUriForFile(context, getFileProviderName(context), file);
    } else {
      return Uri.fromFile(file);
    }
  }


  /**
   * 从 URI 获取图片文件地址
   *
   * @param context 上下文
   * @param uri 文件uri
   */
  public static String getImageFilePath(Context context, Uri uri) {
    if (uri == null) {
      return null;
    }
    String path = null;
    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    if (isKitKat) {
      if (!isMediaDocument(uri)) {
        try {
          final String docId = DocumentsContract.getDocumentId(uri);
          final String[] split = docId.split(":");
          Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
          final String selection = "_id=?";
          final String[] selectionArgs = new String[]{split[1]};
          path = getDataColumn(context, contentUri, selection, selectionArgs);
        } catch (IllegalArgumentException e) {
          path = null;
        }
      }
    }
    if (path == null) {
      String[] projection = {MediaStore.Images.Media.DATA};
      Cursor cursor = ((Activity) context).managedQuery(uri, projection, null, null, null);
      if (cursor != null) {
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
      }
      path = null;
    }
    return path;
  }


  /**
   * 得到 URI 的实际文件路径
   *
   * @param context 上下文
   * @param uri 文件uri
   */
  public static String getFilePath(Context context, Uri uri) {
    if (uri == null) {
      return null;
    }
    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    // DocumentProvider
    if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
      // ExternalStorageProvider
      if (isExternalStorageDocument(uri)) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];

        if ("primary".equalsIgnoreCase(type)) {
          return Environment.getExternalStorageDirectory() + "/" + split[1];
        }

        // TODO handle non-primary volumes
      }
      // DownloadsProvider
      else if (isDownloadsDocument(uri)) {

        final String id = DocumentsContract.getDocumentId(uri);
        final Uri contentUri = ContentUris
            .withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

        return getDataColumn(context, contentUri, null, null);
      }
      // MediaProvider
      else if (isMediaDocument(uri)) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];

        Uri contentUri = null;
        if ("image".equals(type)) {
          contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if ("video".equals(type)) {
          contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else if ("audio".equals(type)) {
          contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }

        final String selection = "_id=?";
        final String[] selectionArgs = new String[]{split[1]};
        return getDataColumn(context, contentUri, selection, selectionArgs);
      }
    }
    // MediaStore (and general)
    else if ("content".equalsIgnoreCase(uri.getScheme())) {
      return getDataColumn(context, uri, null, null);
    }
    // File
    else if ("file".equalsIgnoreCase(uri.getScheme())) {
      return uri.getPath();
    }
    return null;
  }


  /**
   * Get the value of the data column for this Uri. This is useful for
   * MediaStore Uris, and other file-based ContentProviders.
   *
   * @param context The context.
   * @param uri The Uri to query.
   * @param selection (Optional) Filter used in the query.
   * @param selectionArgs (Optional) Selection arguments used in the query.
   * @return The value of the _data column, which is typically a file path.
   */
  private static String getDataColumn(Context context, Uri uri, String selection,
      String[] selectionArgs) {
    Cursor cursor = null;
    final String column = "_data";
    final String[] projection = {column};
    try {
      cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
      if (cursor != null && cursor.moveToFirst()) {
        final int index = cursor.getColumnIndexOrThrow(column);
        return cursor.getString(index);
      }
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
    return null;
  }

  private static boolean isMediaDocument(Uri uri) {
    return "com.android.providers.media.documents".equals(uri.getAuthority());
  }

  private static boolean isExternalStorageDocument(Uri uri) {
    return "com.android.externalstorage.documents".equals(uri.getAuthority());
  }

  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is DownloadsProvider.
   */
  private static boolean isDownloadsDocument(Uri uri) {
    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
  }

}