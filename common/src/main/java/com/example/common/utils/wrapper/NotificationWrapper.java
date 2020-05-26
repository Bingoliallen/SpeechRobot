package com.example.common.utils.wrapper;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;
import java.util.ArrayList;

/**
 * Created by xxdr on 2018/1/15.
 */

public class NotificationWrapper {

  private int mNotificationId;
  private NotificationManager mNotificationManager;
  private Notification mNotification;
  private Notification.Builder mBuilder;
  private Context mContext;

  public NotificationWrapper(Context context, int id) {
    this.mNotificationId = id;
    mContext = context;
    // 获取系统服务来初始化对象
    mNotificationManager = (NotificationManager) mContext
        .getSystemService(Activity.NOTIFICATION_SERVICE);
    mBuilder = new Notification.Builder(mContext);
  }

  /**
   * 设置在顶部通知栏中的各种信息
   */
  private void setBuilder(PendingIntent pIntent,
      int smallIcon, Bitmap largeIcon, String ticker,
      String title, String content,
      boolean sound, boolean vibrate, boolean lights) {

    // 删除时开启一个服务
    // Intent deleteIntent = new Intent(mContext, DeleteService.class);
    // int deleteCode = (int) SystemClock.uptimeMillis();
    // PendingIntent deletePendingIntent = PendingIntent.getService(mContext, deleteCode, deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    // mBuilder.setDeleteIntent(deletePendingIntent);

    // mBuilder.setOngoing(true);// 将Ongoing设为true 那么notification将不能滑动删除
    // mBuilder.setDefaults(Notification.DEFAULT_ALL);// 设置使用默认的声音
    // mBuilder.setVibrate(new long[]{0, 100, 200, 300});// 设置自定义的振动
    // mBuilder.setSound(Uri.parse("file:///sdcard/click.mp3"));
    // mBuilder.setNumber(messageList.size());

    // 状态栏部分
    mBuilder.setContentIntent(pIntent);// 该通知要启动的Intent
    mBuilder.setSmallIcon(smallIcon);// 设置顶部状态栏的小图标
    mBuilder.setLargeIcon(largeIcon);
    mBuilder.setTicker(ticker);// 在顶部状态栏中的提示信息

    // 通知栏部分
    mBuilder.setContentTitle(title);// 设置通知中心的标题
    mBuilder.setContentText(content);// 设置通知中心中的内容
    mBuilder.setWhen(System.currentTimeMillis());

    // 将AutoCancel设为true后，当你点击通知栏的notification后，它会自动被取消消失,
    // 不设置的话点击消息后也不清除，但可以滑动删除
    mBuilder.setAutoCancel(true);

    // 从Android4.1开始，可以通过以下方法，设置notification的优先级，
    // 优先级越高的，通知排的越靠前，优先级低的，不会在手机最顶部的状态栏显示图标
    mBuilder.setPriority(Notification.PRIORITY_MAX);

    // Notification.DEFAULT_ALL         //铃声、闪光、震动均系统默认。
    // Notification.DEFAULT_SOUND       //系统默认铃声。
    // Notification.DEFAULT_VIBRATE     // 系统默认震动。
    // Notification.DEFAULT_LIGHTS      // 系统默认闪光。
    int defaults = 0;
    if (sound) {
      defaults |= Notification.DEFAULT_SOUND;
    }
    if (vibrate) {
      defaults |= Notification.DEFAULT_VIBRATE;
    }
    if (lights) {
      defaults |= Notification.DEFAULT_LIGHTS;
    }
    mBuilder.setDefaults(defaults);
  }

  /**
   * 发送通知
   */
  public void sent() {
    mNotification = mBuilder.build();
    // 发送该通知
    mNotificationManager.notify(mNotificationId, mNotification);
  }

  /**
   * 普通的通知
   */
  public void notifyNormal(PendingIntent pIntent,
      int smallIcon, String ticker,
      String title, String content,
      boolean sound, boolean vibrate, boolean lights) {

    setBuilder(pIntent, smallIcon, null, ticker, title, content, sound, vibrate, lights);
    sent();
  }

  /**
   * 可以容纳多行提示文本的通知信息
   */
  public void notifyNormalMoreLine(PendingIntent pIntent,
      int smallIcon, String ticker,
      String title, String content,
      boolean sound, boolean vibrate, boolean lights) {

    setBuilder(pIntent, smallIcon, null, ticker, title, content, sound, vibrate, lights);
    mNotification = new Notification.BigTextStyle(mBuilder).bigText(content).build();
    sent();
  }

  /**
   * 进行多项设置的通知(在小米上似乎不能设置大图标，系统默认大图标为应用图标)
   */
  public void notifyMailBox(PendingIntent pIntent,
      int smallIcon, int largeIcon, String ticker,
      String title, String content,
      ArrayList<String> messageList,
      boolean sound, boolean vibrate, boolean lights) {

    Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), largeIcon);
    setBuilder(pIntent, smallIcon, bitmap, ticker, title, content, sound, vibrate, lights);
    //设置通知样式为收件箱样式,在通知中心中两指往外拉动，就能出线更多内容，但是很少见
    Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
    for (String msg : messageList) {
      inboxStyle.addLine(msg);
    }
    inboxStyle.setSummaryText(String.format("[%d条]%s", messageList.size(), title));
    mBuilder.setStyle(inboxStyle);
    sent();
  }


  /**
   * 容纳大图片的通知
   */
  public void notifyBigPicture(PendingIntent pIntent,
      int smallIcon, String ticker,
      String title, String content,
      int bigPic, int sampleSize,
      boolean sound, boolean vibrate, boolean lights) {

    setBuilder(pIntent, smallIcon, null, ticker, title, null, sound, vibrate, lights);
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inScaled = true;
    options.inSampleSize = sampleSize;
    Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), bigPic, options);
    Notification.BigPictureStyle picStyle = new Notification.BigPictureStyle();
    picStyle.bigPicture(bitmap);
    picStyle.bigLargeIcon(bitmap);
    mBuilder.setContentText(content);
    mBuilder.setStyle(picStyle);
    sent();
  }

  /**
   * 自定义视图的通知
   */
  public void notifyCustomview(RemoteViews remoteViews, PendingIntent pIntent,
      int smallIcon, String ticker,
      boolean sound, boolean vibrate, boolean lights) {
    setBuilder(pIntent, smallIcon, null, ticker, null, null, sound, vibrate, lights);
    mBuilder.setContent(remoteViews);
    mNotification = mBuilder.build();
    sent();
  }

  /**
   * 有进度条的通知，可以设置为模糊进度或者精确进度
   */
  public void notifyProgress(PendingIntent pIntent,
      int smallIcon, String ticker,
      String title, String content,
      boolean sound, boolean vibrate, boolean lights,
      ProcessCallBack callBack) {

    setBuilder(pIntent, smallIcon, null, ticker, title, content, sound, vibrate, lights);
    if (callBack != null) {
      callBack.task(mBuilder);
    }
  }

  /**
   * 里面有两个按钮的通知
   */
  public void notifyButton(PendingIntent pIntent,
      int smallIcon, int largeIcon, String ticker,
      String title, String content,
      int leftBtnIcon, String leftText, PendingIntent leftPIntent,
      int rightBtnIcon, String rightText, PendingIntent rightPIntent,
      boolean sound, boolean vibrate, boolean lights) {

    Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), largeIcon);
    setBuilder(pIntent, smallIcon, bitmap, ticker, title, content, sound, vibrate, lights);
    mBuilder.addAction(leftBtnIcon, leftText, leftPIntent);
    mBuilder.addAction(rightBtnIcon, rightText, rightPIntent);
    sent();
  }

  /**
   * 根据id清除通知
   */
  public void clear() {
    mNotificationManager.cancel(mNotificationId);
  }

  /**
   * 清除所有通知
   */
  public void clearAll() {
    mNotificationManager.cancelAll();
  }

  public interface ProcessCallBack {

    void task(Notification.Builder builder);
  }
}
