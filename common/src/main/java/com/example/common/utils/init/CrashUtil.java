package com.example.common.utils.init;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import com.example.common.BaseAppProfile;
import com.example.common.CommonAppProfile;
import com.example.common.utils.DeviceUtil;
import com.example.common.utils.DiskUtil;
import com.example.common.utils.FileUtil;
import com.example.common.utils.TimeUtil;
import com.example.common.utils.wrapper.IntentWrapper;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * Created by xxdr on 2018/1/15.
 */

public class CrashUtil implements Thread.UncaughtExceptionHandler {

  private static final String TAG = "CrashUtil";

  private static final int TYPE_SAVE_SDCARD = 1; //崩溃日志保存本地SDCard  --建议开发模式使用
  private static final int TYPE_SAVE_REMOTE = 2; //崩溃日志保存远端服务器 --建议生产模式使用
  private static final int TYPE_SAVE = TYPE_SAVE_SDCARD;  //崩溃保存日志模式
  private static final String CRASH_LOG_DELIVER = "http://www.baidu.com";

  private Application mContext;
  private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

  private CrashUtil() {
  }

  private static class SingletonHolder {

    private static final CrashUtil INSTANCE = new CrashUtil();
  }

  public static CrashUtil getInstance() {
    return SingletonHolder.INSTANCE;
  }

  /**
   * 设置自定异常处理类
   */
  public void init(Application application) {
    mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
    mContext = application;
  }

  /**
   * 进行重写捕捉异常
   */
  @Override
  public void uncaughtException(final Thread thread, final Throwable ex) {
    if (TYPE_SAVE == TYPE_SAVE_SDCARD) {
      // 1,保存信息到sdcard中
      final String filePath = saveToSdcard(mContext, ex);
      IntentWrapper.openFile(mContext, filePath);
      //new Thread(new Runnable() {
      //  @Override
      //  public void run() {
      //    Looper.prepare();
      //    AlertDialog dialog = new AlertDialog.Builder(mContext)
      //        .setMessage("恭喜你找到了新的BUG！\n\n请将文件发给开发。")
      //        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
      //
      //          @Override
      //          public void onClick(DialogInterface dialog, int which) {
      //            IntentWrapper.openFile(mContext, filePath);
      //            android.os.Process.killProcess(android.os.Process.myPid());
      //          }
      //        }).setCancelable(false).create();
      //    dialog.setCanceledOnTouchOutside(false);
      //    dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
      //    dialog.show();
      //    Looper.loop();
      //  }
      //}).start();
    } else if (TYPE_SAVE == TYPE_SAVE_REMOTE) {
      // 2,异常崩溃信息投递到服务器
      saveToServer(mContext, ex);
    }

    // 3,应用准备退出
    // T.showOnThread(mContext, "很抱歉,程序发生异常,即将推出.", Toast.LENGTH_LONG);

    //if (mDefaultCrashHandler != null) {
    //  mDefaultCrashHandler.uncaughtException(thread, ex);
    //} else {
    //  android.os.Process.killProcess(android.os.Process.myPid());
    //}
  }

  /**
   * 保存异常信息到sdcard中
   */
  public static String saveToSdcard(Context context, Throwable ex) {
    StringBuilder builder = new StringBuilder();
    String filePath = null;
    // 添加异常信息
    builder.append(getExceptionInfo(ex));
    if (DeviceUtil.isSDCardMounted()) {
      filePath = context.getExternalFilesDir(TAG) + File.separator + String.format("crash_%s.txt",
          TimeUtil.long2Str(System.currentTimeMillis(), "yyyy_MM_dd_HH_mm_ss"));
      FileUtil.createFile(builder.toString().getBytes(), filePath);
    }
    return filePath;
  }

  /**
   * 进行把数据投递至服务器
   */
  public void saveToServer(Context context, Throwable ex) {
  }

  /**
   * 获取并且转化异常信息
   */
  public static String getExceptionInfo(Throwable ex) {
    StringWriter sw = new StringWriter();
    ex.printStackTrace(new PrintWriter(sw));
    StringBuilder builder = new StringBuilder();
    getDeviceInfo(builder);
    builder.append("---------Crash Log Begin---------\n\n");
    builder.append(sw.toString() + "\n");
    builder.append("---------Crash Log End---------\n");
    return builder.toString();
  }

  /**
   * 获取设备信息
   */
  public static void getDeviceInfo(StringBuilder builder) {
    builder.append(String.format("App Version:%s_%d\n", CommonAppProfile.getAppVersionName(),
        CommonAppProfile.getAppVersionCode()));
    builder.append(
        String.format("Android Version:%s_%s\n", Build.VERSION.RELEASE, Build.VERSION.SDK_INT));
    builder.append(String.format("System:%s\n", Build.DISPLAY));
    builder.append(String.format("Vendor:%s\n", Build.MANUFACTURER));
    builder.append(String.format("Model:%s\n", Build.MODEL));
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      builder.append(String.format("CPU ABI:%s\n", Arrays.toString(Build.SUPPORTED_ABIS)));
    } else {
      builder.append(String.format("CPU ABI:%s\n", Build.CPU_ABI));
    }
    builder.append(String
        .format("Battery:%d%%\n", DeviceUtil.getBatteryPercent(BaseAppProfile.getApplication())));
    builder.append(String.format("Rooted:%s\n", DeviceUtil.isRooted()));
    builder.append(String
        .format("Ram:%.01f%% [%s]\n", DeviceUtil.getRamPercent(BaseAppProfile.getApplication()),
            DiskUtil.getFormatSize(DeviceUtil.getTotalRAM())));
    builder.append(String.format("SDCard:%.01f%% [%s]\n", DeviceUtil.getSDPercent(),
        DiskUtil.getFormatSize(DeviceUtil.getSDTotalSize())));
    builder.append(
        String.format("Network:%s\n", DeviceUtil.getNetworkInfo(BaseAppProfile.getApplication())));
    builder.append("\n");
  }
}
