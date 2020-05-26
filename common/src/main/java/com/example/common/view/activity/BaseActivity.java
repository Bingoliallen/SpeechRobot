package com.example.common.view.activity;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import butterknife.ButterKnife;
import com.example.common.BaseApplication;
import com.example.common.presenter.impl.BasePresenter;
import com.example.common.utils.AppUtil;
import com.example.common.utils.DensityUtil;
import com.example.common.utils.PermissionUtil;
import com.example.common.utils.SoftKeyboardUtil;
import com.example.common.utils.ViewUtil;
import com.example.common.utils.wrapper.IntentWrapper;
import com.example.common.widget.TitleBarView;

/**
 * Created by xxdr on 2017/7/12.
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {

  public TitleBarView titleBar;
  protected String TAG = this.getClass().getSimpleName();
  protected FragmentManager mFragmentManager;
  protected T mPresenter;
  protected View mContentView;
  protected PermissionUtil mPermissionUtil;

  //protected static void launch(Activity activity, Intent intent) {
  //  // 取消动画
  //  //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
  //  activity.startActivity(intent);
  //  //activity.overridePendingTransition(R.anim.common_activity_slide_from_right, R.anim.common_activity_slide_to_left);
  //}

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    beforeSetContentView();
    setContentView(getContentViewId());
    ButterKnife.bind(this);

    if (getApplication() instanceof BaseApplication) {
      BaseApplication application = (BaseApplication) getApplication();
      application.addActivity(this);
    }
    mFragmentManager = getSupportFragmentManager();
    mPresenter = createPresenter();

    initDialog();
    initView();
    initEvent();
    initData();
  }



  @Override
  protected void onResume() {
    super.onResume();
    DensityUtil.setDensity(this);
    if (!AppUtil.isNetworkAvailable(this)) {
      // 提示是否前往设置网络链接
      mNetDialog.show();
      WindowManager.LayoutParams params = mNetDialog.getWindow().getAttributes();
      params.width = ViewUtil.dp2px(this, 270);
      params.height =ViewUtil.dp2px(this, 180) ;
      mNetDialog.getWindow().setAttributes(params);

      com.example.common.utils.init.T.showNetworkError();
    }
    //if (BuildConfig.IS_DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
    //  if (!Settings.canDrawOverlays(this)) {
    //    com.example.common.utils.init.T.showShort("测试模式需要打开悬浮窗。");
    //    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
    //    intent.setData(Uri.parse("package:" + getPackageName()));
    //    startActivityForResult(intent, 0);
    //  }
    //}
    if (mPermissionUtil != null) {
      if (mPermissionUtil.needCheck) {
        mPermissionUtil.checkPermissions();
      }
    }
  }

  @Override
  protected void onDestroy() {
    if (getApplication() instanceof BaseApplication) {
      BaseApplication application = (BaseApplication) getApplication();
      application.removeActivity(this);
    }
    if (mPresenter != null) {
      mPresenter.onDestroy();
      mPresenter = null;
    }
    super.onDestroy();
  }

  protected void beforeSetContentView() {
    DensityUtil.setDensity(this);
  }

  /**
   * 返回布局文件
   */
  protected abstract int getContentViewId();

  protected abstract T createPresenter();

  /**
   * 初始化控件
   */
  protected void initView() {
    mContentView = findViewById(android.R.id.content);
    titleBar = findViewById(com.example.common.R.id.title_bar);
    changeWidgetStyle();
  }

  /**
   * 设置事件
   */
  protected void initEvent() {
    if (mContentView != null) {
      mContentView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          SoftKeyboardUtil.hideSoftKeyboard(BaseActivity.this);
        }
      });
    }
  }

  /**
   * 初始化数据
   */
  protected void initData() {
  }

  /**
   * 更新控件样式
   */
  protected void changeWidgetStyle() {
  }

  @Override
  public void finish() {
    super.finish();
    //overridePendingTransition(R.anim.common_activity_slide_from_left, R.anim.common_activity_slide_to_right);
  }

  public void onRequestPermissionsResult(int requestCode, String[] permissions,
      int[] paramArrayOfInt) {
    if (requestCode == PermissionUtil.PERMISSION_REQUEST_CODE) {
      if (mPermissionUtil != null) {
        if (!mPermissionUtil.verifyPermissions(paramArrayOfInt)) {
          mPermissionUtil.showDialog();
          mPermissionUtil.needCheck = false;
        }
      }
    }
  }

  // protected void showFinishDialog() {
  //     if (mFinishDialog != null && !mFinishDialog.isShowing())
  //         mFinishDialog.show();
  // }

  // @Override
  // public boolean onKeyDown(int keyCode, KeyEvent event) {
  //     if (keyCode == KeyEvent.KEYCODE_BACK) {//返回按钮事件
  //         finish();
  //     }
  //     return super.onKeyDown(keyCode, event);
  // }

  /**
   * 之前遇到一个情况，onBackPressed 无效
   */
  // @Override
  // public void onBackPressed() {
  //     finish();
  // }


  /**
   * 私有初始化对话框控件
   */
  private AlertDialog mNetDialog;

  //private AlertDialog mFinishDialog;
  public void initDialog() {
    mNetDialog = new AlertDialog.Builder(this)
        .setTitle("提示")
        .setMessage("无网络连接，是否前往设置。")
        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            IntentWrapper.startSystemSettings(BaseActivity.this);
          }
        })
        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
          @SuppressLint("MissingPermission")
          @Override
          public void onClick(DialogInterface dialog, int which) {
            finish();
           /* android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
            ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
            manager.killBackgroundProcesses(getPackageName());*/


          }
        })
        .setCancelable(false)
        .create();
    mNetDialog.setCanceledOnTouchOutside(false);



    //    mFinishDialog = AlertDialogWrapper.create(this, "提示", "确认要退出程序吗？",
    //            new DialogInterface.OnClickListener() {
    //                @Override
    //                public void onClick(DialogInterface dialog, int which) {
    //                    finish();
    //                }
    //            }, null,
    //            true, false);
  }

  /**
   * 如果第一个打开不是 SplashActivity，那么使用这个可以主题去除白屏
   */
  // <style name="AppTheme.Launcher" parent="AppTheme">
  //     <!--关闭启动窗口-->
  //     <item name="android:windowDisablePreview">true</item>
  // </style>
}