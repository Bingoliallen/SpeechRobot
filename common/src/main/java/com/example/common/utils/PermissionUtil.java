package com.example.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.example.common.utils.wrapper.IntentWrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xxdr on 2018/4/17.
 */

public class PermissionUtil {

  public static final int PERMISSION_REQUEST_CODE = 0;

  /**
   * 判断是否需要检测，防止不停的弹框
   */
  public boolean needCheck = true;

  private Activity mActivity;
  private String[] mPermissions;
  private AlertDialog mPermissionDialog;

  public PermissionUtil(Activity activity, String... permissions) {
    mActivity = activity;
    mPermissions = permissions;
    if (mPermissionDialog == null) {
      mPermissionDialog = new AlertDialog.Builder(mActivity).setTitle("提示")
          .setMessage("当前应用缺少必要权限。\n\n请点击\"设置\"-\"权限\"-打开所需权限。")
          .setPositiveButton("确定", new DialogInterface.OnClickListener() { //拒绝, 退出应用
            @Override
            public void onClick(DialogInterface dialog, int which) {
              IntentWrapper.startAppSettings(mActivity);
              mActivity.finish();
            }
          }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              mActivity.finish();
            }
          }).setCancelable(false).create();
      mPermissionDialog.setCanceledOnTouchOutside(false);
    }
  }

  /**
   * 检查权限
   */
  public void checkPermissions() {
    if (mPermissions == null || mPermissions.length == 0) {
      return;
    }
    List<String> needRequestPermissionList = findDeniedPermissions(mPermissions);
    if (null != needRequestPermissionList && needRequestPermissionList.size() > 0) {
      ActivityCompat.requestPermissions(mActivity,
          needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]),
          PERMISSION_REQUEST_CODE);
    }
  }

  /**
   * 检测是否说有的权限都已经授权
   */
  public boolean verifyPermissions(int[] grantResults) {
    for (int result : grantResults) {
      if (result != PackageManager.PERMISSION_GRANTED) {
        return false;
      }
    }
    return true;
  }

  public void showDialog() {
    if (mPermissionDialog != null) {
      mPermissionDialog.show();
    }
  }

  /**
   * 获取权限集中需要申请权限的列表
   */
  private List<String> findDeniedPermissions(String[] permissions) {
    List<String> needRequestPermissionList = new ArrayList<>();
    for (String permission : permissions) {
      if (ContextCompat.checkSelfPermission(mActivity, permission)
          != PackageManager.PERMISSION_GRANTED
          || ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission)) {
        needRequestPermissionList.add(permission);
      }
    }
    return needRequestPermissionList;
  }
}
