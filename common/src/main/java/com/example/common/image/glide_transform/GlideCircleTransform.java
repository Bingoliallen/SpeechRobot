package com.example.common.image.glide_transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

/**
 * Created by zyl06 on 9/4/16.
 */

public class GlideCircleTransform extends BitmapTransformation {

  public GlideCircleTransform(Context context) {
    super(context);
  }

  @Override
  protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {

    if (toTransform == null) {
      return null;
    }

    Bitmap result = pool.get(outWidth, outHeight, Bitmap.Config.ARGB_8888);
    if (result == null) {
      result = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
    }

    Bitmap bitmap = TransformationUtils.centerCrop(result, toTransform, outWidth, outHeight);
    return circleCrop(pool, bitmap);
  }

  private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
    if (source == null) {
      return null;
    }

    Bitmap result = pool.get(source.getWidth(), source.getWidth(), Bitmap.Config.ARGB_8888);
    if (result == null) {
      result = Bitmap.createBitmap(source.getWidth(), source.getWidth(), Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(result);
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setShader(
        new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
    canvas.drawCircle(source.getWidth() / 2, source.getWidth() / 2, source.getWidth() / 2, paint);
    return result;
  }

  @Override
  public String getId() {
    return "GlideCircleTransform";
  }
}
