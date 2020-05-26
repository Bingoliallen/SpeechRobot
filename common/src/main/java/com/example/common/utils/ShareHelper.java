//package com.example.common.utils;
//
//import android.content.Context;
//import android.graphics.BitmapFactory;
//import android.text.TextUtils;
//import cn.sharesdk.framework.Platform;
//import cn.sharesdk.framework.PlatformActionListener;
//import cn.sharesdk.framework.ShareSDK;
//import cn.sharesdk.sina.weibo.SinaWeibo;
//import cn.sharesdk.tencent.qq.QQ;
//import cn.sharesdk.tencent.qzone.QZone;
//import cn.sharesdk.wechat.friends.Wechat;
//import cn.sharesdk.wechat.moments.WechatMoments;
//import com.example.common.utils.init.T;
//import com.vma.silkrouteart.R;
//import com.vma.silkrouteart.eventbus.ShareEvent;
//import java.util.HashMap;
//import org.greenrobot.eventbus.EventBus;
//
///**
// * Created by hang on 2015/10/22.
// */
//public class ShareHelper {
//
//  public static void shareWechat(Context context, String title, String content, String url,
//      String thumbnail) {
//    Wechat.ShareParams wx = new Wechat.ShareParams();
//    wx.setShareType(Platform.SHARE_WEBPAGE);
//    wx.setTitle(title);
//    wx.setText(content);
//    if (TextUtils.isEmpty(thumbnail)) {
//      wx.setImageData(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
//    } else {
//      wx.setImageUrl(thumbnail);
//    }
//    wx.setImage(url);
//    ShareSDK.getPlatform(Wechat.NAME).setPlatformActionListener(new PlatformActionListener() {
//      @Override
//      public void onError(Platform arg0, int arg1, Throwable arg2) {
//        EventBus.getDefault().post(new ShareEvent(-1));
//        T.showShort("分享失败");
//      }
//
//      @Override
//      public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
//        EventBus.getDefault().post(new ShareEvent(0));
//        T.showShort("分享完成");
//      }
//
//      @Override
//      public void onCancel(Platform arg0, int arg1) {
//        EventBus.getDefault().post(new ShareEvent(-2));
//        T.showShort("分享取消");
//      }
//    });
//    ShareSDK.getPlatform(Wechat.NAME).share(wx);
//  }
//
//  public static void shareWechatMoment(Context context, String title, String content, String url,
//      String thumbnail) {
//    WechatMoments.ShareParams moments = new WechatMoments.ShareParams();
//    moments.setShareType(Platform.SHARE_WEBPAGE);
//    moments.setTitle(title);
//    moments.setText(content);
//    if (TextUtils.isEmpty(thumbnail)) {
//      moments
//          .setImageData(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
//    } else {
//      moments.setImageUrl(thumbnail);
//    }
//    moments.setImage(url);
//    ShareSDK.getPlatform(WechatMoments.NAME)
//        .setPlatformActionListener(new PlatformActionListener() {
//          @Override
//          public void onError(Platform arg0, int arg1, Throwable arg2) {
//            EventBus.getDefault().post(new ShareEvent(-1));
//            T.showShort("分享失败");
//          }
//
//          @Override
//          public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
//            EventBus.getDefault().post(new ShareEvent(0));
//            T.showShort("分享完成");
//          }
//
//          @Override
//          public void onCancel(Platform arg0, int arg1) {
//            EventBus.getDefault().post(new ShareEvent(-2));
//            T.showShort("分享取消");
//          }
//        });
//    ShareSDK.getPlatform(WechatMoments.NAME).share(moments);
//  }
//
//  public static void shareSinaWeibo(final Context context, String url) {
//    SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
//    sp.setText(url);
//    Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//    weibo.setPlatformActionListener(new PlatformActionListener() {
//      @Override
//      public void onError(Platform arg0, int arg1, Throwable arg2) {
//        EventBus.getDefault().post(new ShareEvent(-1));
//        T.showShort("分享失败");
//      }
//
//      @Override
//      public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
//        EventBus.getDefault().post(new ShareEvent(0));
//        T.showShort("分享完成");
//      }
//
//      @Override
//      public void onCancel(Platform arg0, int arg1) {
//        EventBus.getDefault().post(new ShareEvent(-2));
//        T.showShort("分享取消");
//      }
//    }); // 设置分享事件回调
//    // 执行图文分享
//    weibo.share(sp);
//  }
//
//  public static void shareQQ(Context context, String title, String content, String url,
//      String thumbnail) {
//    QQ.ShareParams qq = new QQ.ShareParams();
//    qq.setText(content);
//    qq.setTitle(title);
//    qq.setTitleUrl(url);
//    if (TextUtils.isEmpty(thumbnail)) {
//      qq.setImageData(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
//    } else {
//      qq.setImageUrl(thumbnail);
//    }
//    ShareSDK.getPlatform(QZone.NAME).setPlatformActionListener(new PlatformActionListener() {
//      @Override
//      public void onError(Platform arg0, int arg1, Throwable arg2) {
//        T.showShort("分享失败");
//      }
//
//      @Override
//      public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
//        T.showShort("分享完成");
//      }
//
//      @Override
//      public void onCancel(Platform arg0, int arg1) {
//        T.showShort("分享取消");
//      }
//    });
//    ShareSDK.getPlatform(QQ.NAME).share(qq);
//  }
//
//  public static void shareQZone(final Context context, String title, String content, String url,
//      String thumbnail) {
//    QZone.ShareParams qzone = new QZone.ShareParams();
//    qzone.setTitle(title);
//    qzone.setTitleUrl(url);
//    qzone.setText(content);
//    qzone.setSite(url);
//    qzone.setSiteUrl(url);
//    if (TextUtils.isEmpty(thumbnail)) {
//      qzone
//          .setImageData(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher));
//    } else {
//      qzone.setImageUrl(thumbnail);
//    }
//    ShareSDK.getPlatform(QZone.NAME).setPlatformActionListener(new PlatformActionListener() {
//      @Override
//      public void onError(Platform arg0, int arg1, Throwable arg2) {
//        T.showShort("分享失败");
//      }
//
//      @Override
//      public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
//        T.showShort("分享完成");
//      }
//
//      @Override
//      public void onCancel(Platform arg0, int arg1) {
//        T.showShort("分享取消");
//      }
//    });
//    ShareSDK.getPlatform(QZone.NAME).share(qzone);
//  }
//}
