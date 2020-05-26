package com.example.common.view.webload;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import com.example.common.R;
import com.example.common.presenter.impl.BasePresenter;
import com.example.common.utils.BarUtil;
import com.example.common.view.activity.BaseActivity;
import com.example.common.widget.TitleBarView.OnClickListener;

public class WebLoadActivity extends BaseActivity {

  private WebLoadFragment mFragment;

  public static void open(Context context, String title, String url, String data) {
    Intent intent = new Intent(context, WebLoadActivity.class);
    intent.putExtra(WebLoadFragment.PARAMS_TITLE, title);
    intent.putExtra(WebLoadFragment.PARAMS_URL, url);
    intent.putExtra(WebLoadFragment.PARAMS_DATA, data);
    context.startActivity(intent);
  }

  @Override
  protected void beforeSetContentView() {
    super.beforeSetContentView();
    BarUtil.hideActionBar(this);
    BarUtil.statusBarDarkMode(this);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // 开启硬件加速
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);

    super.onCreate(savedInstanceState);
  }

  @Override
  protected BasePresenter createPresenter() {
    return null;
  }

  @Override
  protected int getContentViewId() {
    return R.layout.common_activity_web_load;
  }

  @Override
  protected void initEvent() {
    super.initEvent();
    titleBar.setOnClickListener(new OnClickListener() {
      @Override
      public void leftClick() {
        finish();
      }

      @Override
      public void rightClick() {

      }
    });
  }

  @Override
  protected void initData() {
    super.initData();
    String title = getIntent().getStringExtra(WebLoadFragment.PARAMS_TITLE);
    String url = getIntent().getStringExtra(WebLoadFragment.PARAMS_URL);
    String data = getIntent().getStringExtra(WebLoadFragment.PARAMS_DATA);
    if (!TextUtils.isEmpty(title)) {
      titleBar.setTitleText(title);
    }
    mFragment = WebLoadFragment.getInstance(title, url, data);
    mFragmentManager.beginTransaction().add(R.id.rl_content, mFragment).commit();
  }

  @Override
  public void onBackPressed() {
    mFragment.goBack();
  }
}
