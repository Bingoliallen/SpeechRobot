package com.vma.speechrobot.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DownloadReceiver extends BroadcastReceiver {


  @Override
  public void onReceive(Context context, Intent intent) {
    DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
      Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show();
    } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
      //点击通知栏进入下载管理页面
      Intent intent1 = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
      intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent1);
    }
  }
}