package com.example.common.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.AppCompatDrawableManager;
import android.util.Base64;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;

public class BitmapUtil {

  public static final String ALBUM_NAME = "Common";

  private BitmapUtil() {
  }

  /**
   * scale
   */
  public static Bitmap zoom(Bitmap bitmap, float zf) {
    Matrix matrix = new Matrix();
    matrix.postScale(zf, zf);
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
  }

  public static Bitmap zoom(Bitmap bitmap, float wf, float hf) {
    Matrix matrix = new Matrix();
    matrix.postScale(wf, hf);
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
  }

  /**
   * byte to bitmap
   */
  public static Bitmap byte2Bitmap(byte[] b) {
    if (b.length != 0) {
      return BitmapFactory.decodeByteArray(b, 0, b.length);
    } else {
      return null;
    }
  }

  /**
   * bitmap to byte
   */
  public static byte[] bitmap2Byte(Bitmap bitmap) {
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    float scale = Math.min(1, Math.min(600f / bitmap.getWidth(), 600f / bitmap.getHeight()));
    Matrix matrix = new Matrix();
    matrix.postScale(scale, scale);
    Bitmap imgSmall = Bitmap
        .createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    imgSmall.compress(Bitmap.CompressFormat.PNG, 100, stream);
    byte[] result = stream.toByteArray();
    return result;
  }

  /**
   * bitmap to string
   */
  public static String bitmap2Str(Bitmap bitmap) {
    return Base64.encodeToString(bitmap2Byte(bitmap), Base64.DEFAULT);
  }

  public static String bitmap2Str(String path) {
    return Base64.encodeToString(bitmap2Byte(file2Bitmap(path)), Base64.DEFAULT);
  }

  /**
   * file to bitmap
   */
  public static Bitmap file2Bitmap(String filePath) {
    int screenWidth = DeviceUtil.getDeviceWidth();
    int screenHeight = DeviceUtil.getDeviceHeight();
    return file2Bitmap(filePath, screenWidth, screenHeight);
  }

  public static Bitmap file2Bitmap(String filePath, int reqWidth, int reqHeight) {
    BitmapFactory.Options opt = new BitmapFactory.Options();
    opt.inJustDecodeBounds = true;
    Bitmap bmp = BitmapFactory.decodeFile(filePath, opt);
    opt.inSampleSize = calculateInSampleSize(opt, reqWidth, reqHeight);
    opt.inJustDecodeBounds = false;
    bmp = BitmapFactory.decodeFile(filePath, opt);
    return bmp;
  }

  /**
   * calculate bitmap value of scale
   */
  private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
      int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;
    if (height > reqHeight || width > reqWidth) {
      // Calculate ratios of height and width to requested height and
      // width
      final int heightRatio = Math.round((float) height / (float) reqHeight);
      final int widthRatio = Math.round((float) width / (float) reqWidth);
      // Choose the smallest ratio as inSampleSize value, this will
      // guarantee
      // a final image with both dimensions larger than or equal to the
      // requested height and width.
      inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }
    return inSampleSize;
  }

  /**
   * bitmap to file
   */
  public static File bitmap2File(Context context, Bitmap bitmap) {
    File file = new File(context.getFilesDir() + "/temp" + System.currentTimeMillis() + ".png");
    saveBitmap(bitmap, file.getAbsolutePath());
    return file;
  }

  /**
   * bitmap to drawable
   */
  public static Drawable bitmap2Drawable(Context context, Bitmap bm) {
    return new BitmapDrawable(context.getResources(), bm);
  }

  /**
   * drawable to bitmap
   */
  public static Bitmap drawable2Bitmap(Drawable drawable) {
    if (drawable == null) {
      return null;
    }
    Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
        Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);
    return bitmap;
  }

  /**
   * 可读取矢量图
   * 由于appcompat的版本超过23，会导致安卓4.4读取失败，报错如下：
   * android.content.res.Resources$NotFoundException: File res/drawable...
   * 后面发现要使用 AppCompatActivity 就不会报错
   */
  public static Bitmap drawable2Bitmap(Context context, int resId) {
    Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, resId);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      drawable = (DrawableCompat.wrap(drawable)).mutate();
    }
    return drawable2Bitmap(drawable);
  }

  public static Bitmap drawable2Bitmap(Context context, int resId, @ColorInt int color) {
    Drawable drawable = AppCompatDrawableManager.get().getDrawable(context, resId);
    drawable = DrawableUtil.tintDrawable(drawable, color);
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      drawable = (DrawableCompat.wrap(drawable)).mutate();
    }
    return drawable2Bitmap(drawable);
  }

  /**
   * 保存图片至手机图库
   */
  public static boolean saveBitmap2Local(Context context, Bitmap bitmap, String filePath) {
    if (saveBitmap(bitmap, filePath)) {
      addPic2Gallery(context, filePath);
      return true;
    } else {
      return false;
    }
  }

  /**
   * 保存位图
   * true 成功	false 失败
   */
  public static boolean saveBitmap(Bitmap bitmap, String filePath) {
    if (bitmap == null) {
      return false;
    }
    return FileUtil.createFile(bitmap2Byte(bitmap), filePath);
  }

  /**
   * 读取本地资源的图片，以最省内存的方式
   */
  public static Bitmap getBitmap(Context context, int resId) {
    BitmapFactory.Options opt = new BitmapFactory.Options();
    opt.inPreferredConfig = Config.RGB_565;
    opt.inPurgeable = true;
    opt.inInputShareable = true;
    // 获取资源图片
    InputStream is = context.getResources().openRawResource(resId);
    return BitmapFactory.decodeStream(is, null, opt);
  }

  /**
   * 获取缩略图
   */
  public static Bitmap getThumbBitmap(Context context, Uri uri) {
    ContentResolver resolver = context.getContentResolver();
    Uri thumb = Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI,
        uri.getLastPathSegment());
    Bitmap bitmap = null;
    try {
      bitmap = MediaStore.Images.Media.getBitmap(resolver, thumb);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return bitmap;
  }

  /**
   * 添加图片到图库
   */
  public static void addPic2Gallery(Context context, String path) {
    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    File f = new File(path);
    Uri contentUri = Uri.fromFile(f);
    mediaScanIntent.setData(contentUri);
    context.sendBroadcast(mediaScanIntent);
  }

  /**
   * 获取保存图片的目录
   */
  public static File getAlbumDir() {
    File dir = new File(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ALBUM_NAME);
    if (!dir.exists()) {
      dir.mkdirs();
    }
    return dir;
  }

  /**
   * 判断照片角度
   */
  public static int readPictureDegree(String path) {
    int degree = 0;
    try {
      ExifInterface exifInterface = new ExifInterface(path);
      int orientation = exifInterface.getAttributeInt(
          ExifInterface.TAG_ORIENTATION,
          ExifInterface.ORIENTATION_NORMAL);
      switch (orientation) {
        case ExifInterface.ORIENTATION_ROTATE_90:
          degree = 90;
          break;
        case ExifInterface.ORIENTATION_ROTATE_180:
          degree = 180;
          break;
        case ExifInterface.ORIENTATION_ROTATE_270:
          degree = 270;
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return degree;
  }

  /**
   * 旋转照片
   */
  public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
    if (bitmap != null) {
      Matrix m = new Matrix();
      m.postRotate(degress);
      bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
      return bitmap;
    }
    return bitmap;
  }

  /**
   * 创建QR二维码图片
   */
  public static Bitmap createQRCodeBitmap(String content) {
    // 用于设置QR二维码参数
    Hashtable<EncodeHintType, Object> qrParam = new Hashtable<EncodeHintType, Object>();
    // 设置QR二维码的纠错级别——这里选择最高H级别
    qrParam.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    // 设置编码方式
    qrParam.put(EncodeHintType.CHARACTER_SET, "UTF-8");
    // 生成QR二维码数据——这里只是得到一个由true和false组成的数组
    // 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
    try {
      BitMatrix bitMatrix = new MultiFormatWriter()
          .encode(content, BarcodeFormat.QR_CODE, 300, 300, qrParam);
      // 开始利用二维码数据创建Bitmap图片，分别设为黑白两色
      int w = bitMatrix.getWidth();
      int h = bitMatrix.getHeight();
      int[] data = new int[w * h];
      for (int y = 0; y < h; y++) {
        for (int x = 0; x < w; x++) {
          if (bitMatrix.get(x, y)) {
            data[y * w + x] = 0xff000000;// 黑色
          } else {
            data[y * w + x] = -1;// -1 相当于0xffffffff 白色
          }
        }
      }
      // 创建一张bitmap图片，采用最高的效果显示
      Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
      // 将上面的二维码颜色数组传入，生成图片颜色
      bitmap.setPixels(data, 0, w, 0, 0, w, h);
      return bitmap;
    } catch (WriterException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * create rounded corner Bitmap base on original Bitmap
   */
  public static Bitmap createCornerBitmap(Bitmap bitmap, float roundPX) {
    Bitmap targetBmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
    Canvas canvas = new Canvas(targetBmp);

    final int color = 0xff424242;
    final Paint paint = new Paint();
    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
    final RectF rectF = new RectF(rect);
    paint.setAntiAlias(true);
    canvas.drawARGB(0, 0, 0, 0);
    paint.setColor(color);
    canvas.drawRoundRect(rectF, roundPX, roundPX, paint);
    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
    canvas.drawBitmap(bitmap, rect, rect, paint);
    return targetBmp;
  }

  /**
   * create circle Bitmap base on original Bitmap
   */
  public static Bitmap createCircularBitmap(Bitmap bitmap) {
    int targetWidth = bitmap.getWidth();
    int targetHeight = bitmap.getHeight();
    Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Config.ARGB_8888);
    Canvas canvas = new Canvas(targetBitmap);
    Path path = new Path();
    path.addCircle(
        ((float) targetWidth - 1) / 2,
        ((float) targetHeight - 1) / 2,
        (Math.min(((float) targetWidth), ((float) targetHeight)) / 2),
        Path.Direction.CCW);
    canvas.clipPath(path);
    canvas.drawBitmap(
        bitmap,
        new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
        new Rect(0, 0, targetWidth, targetHeight),
        null);
    return targetBitmap;
  }

  /**
   * 创建阴影 Bitmap
   */
  public static Bitmap createReflectionBitmap(Bitmap bitmap, int reflectionHeight) {
    if (reflectionHeight == 0) {
      return bitmap;
    }
    //This will not scale but will flip on the Y axis
    Matrix matrix = new Matrix();
    matrix.preScale(1, -1);
    //Create a Bitmap with the flip matix applied to it.
    int height = bitmap.getHeight();
    int width = bitmap.getWidth();
    Bitmap reflectionBitmap = Bitmap
        .createBitmap(bitmap, 0, height - reflectionHeight, width, reflectionHeight, matrix, false);
    //Create a new bitmap with same width but taller to fit reflection
    Bitmap bitmapWithReflection = Bitmap
        .createBitmap(width, (height + reflectionHeight), Config.ARGB_8888);
    //Create a new Canvas with the bitmap that's big enough for the image plus gap plus reflection
    Canvas canvas = new Canvas(bitmapWithReflection);
    //Draw in the original image
    canvas.drawBitmap(bitmap, 0, 0, null);

    /**Draw in the gap
     Paint deafaultPaint = new Paint();
     canvas.drawRect(0, height, width, height + 0, deafaultPaint);
     */

    //Draw in the reflection
    canvas.drawBitmap(reflectionBitmap, 0, height + 0, null);

    /**Create a shader that is a linear gradient that covers the reflection
     Paint paint = new Paint();
     LinearGradient shader = new LinearGradient(0, height, 0,
     bitmapWithReflection.getHeight() + 0, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
     //Set the paint to use this shader (linear gradient)
     paint.setShader(shader);
     //Set the Transfer mode to be porter duff and destination in
     paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
     //Draw a rectangle using the paint with our linear gradient
     canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + 0, paint);
     */

    return bitmapWithReflection;
  }
}
