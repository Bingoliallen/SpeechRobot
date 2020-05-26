package com.example.common.image;

import android.content.Context;
import android.util.SparseArray;
import android.widget.ImageView;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.example.common.R;
import com.example.common.image.glide_transform.GlideCircleTransform;
import com.example.common.image.glide_transform.GlideRoundRectTransform;

/**
 * Created by xxdr on 2017/11/14.
 */

public class ImageWrapper {

  /**
   * 占位符
   */
  private static int sPlaceHolder = R.drawable.common_image_placeholder1;
  public static int CENTER_CROP = 1;
  public static int FIT_CENTER = 2;

  private static GlideCircleTransform sCircleTransform = null;
  private static SparseArray<GlideRoundRectTransform> sRoundTransforms = new SparseArray<>();

  /**
   * 圆形转换器
   */
  private static BitmapTransformation getCircleBitmapTransform(Context context) {
    if (sCircleTransform == null) {
      synchronized (ImageWrapper.class) {
        if (sCircleTransform == null) {
          sCircleTransform = new GlideCircleTransform(context);
        }
      }
    }
    return sCircleTransform;
  }

  /**
   * 圆角转换器
   */
  private static synchronized BitmapTransformation getRoundBitmapTransform(Context context,
      int radiusDp) {
    GlideRoundRectTransform transformation = sRoundTransforms.get(radiusDp);
    if (transformation == null) {
      transformation = new GlideRoundRectTransform(context, radiusDp);
      sRoundTransforms.put(radiusDp, transformation);
    }
    return transformation;
  }

  private static <T> void setImage(ImageView iv, T imgPath, BitmapTransformation transformation,
      int placeHolder, int type) {
    DrawableRequestBuilder<Integer> thumbnail = Glide.with(iv.getContext())
        .load(placeHolder).centerCrop();
    if (transformation != null) {
      thumbnail.bitmapTransform(transformation);
    }
    thumbnail.animate(android.R.anim.fade_in).into(iv);
    DrawableRequestBuilder<T> builder = Glide.with(iv.getContext()).load(imgPath);
    if (type == CENTER_CROP) {
      builder = builder.centerCrop();
    } else if (type == FIT_CENTER) {
      builder = builder.fitCenter();
    }
    builder = builder.animate(android.R.anim.fade_in)
        .thumbnail(thumbnail);
    if (transformation != null) {
      builder.bitmapTransform(transformation);
    }
    builder.into(iv);
  }

  public static <T> void setImage(ImageView iv, T imgPath, int placeHolder, int type) {
    setImage(iv, imgPath, null, placeHolder, type);
  }

  public static <T> void setImage(ImageView iv, T imgPath, int type) {
    setImage(iv, imgPath, sPlaceHolder, type);
  }

  public static <T> void setImage(ImageView iv, T imgPath) {
    setImage(iv, imgPath, CENTER_CROP);
  }

  public static <T> void setCircleImage(ImageView iv, T imgPath, int placeHolder, int type) {
    BitmapTransformation transformation = getCircleBitmapTransform(iv.getContext());
    setImage(iv, imgPath, transformation, placeHolder, type);
  }

  public static <T> void setCircleImage(ImageView iv, T imgPath, int type) {
    setCircleImage(iv, imgPath, sPlaceHolder, type);
  }

  public static <T> void setCircleImage(ImageView iv, T imgPath) {
    setCircleImage(iv, imgPath, CENTER_CROP);
  }

  public static <T> void setRoundImage(ImageView iv, T imgPath, int radiusDp, int placeHolder,
      int type) {
    BitmapTransformation transformation = getRoundBitmapTransform(iv.getContext(), radiusDp);
    setImage(iv, imgPath, transformation, placeHolder, type);
  }

  public static <T> void setRoundImage(ImageView iv, T imgPath, int radiusDp, int type) {
    setRoundImage(iv, imgPath, radiusDp, sPlaceHolder, type);
  }

  public static <T> void setRoundImage(ImageView iv, T imgPath, int radiusDp) {
    setRoundImage(iv, imgPath, radiusDp, CENTER_CROP);
  }
}
