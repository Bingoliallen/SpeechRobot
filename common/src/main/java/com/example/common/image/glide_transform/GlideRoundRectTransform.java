package com.example.common.image.glide_transform;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

/**
 * Created by zyl06 on 9/4/16.
 */

public class GlideRoundRectTransform extends BitmapTransformation {

  private float mRadius;

  public GlideRoundRectTransform(Context context, float radiusDp) {
    super(context);
    mRadius = Resources.getSystem().getDisplayMetrics().density * radiusDp;
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
    return roundCrop(pool, bitmap);
  }

  private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
    if (source == null) {
      return null;
    }

    Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
    if (result == null) {
      result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(result);
    Paint paint = new Paint();
    paint.setShader(
        new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
    paint.setAntiAlias(true);
    RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
    canvas.drawRoundRect(rectF, mRadius, mRadius, paint);
    return result;
  }

  @Override
  public String getId() {
    return "GlideRoundRectTransform: " + mRadius;
  }
}
