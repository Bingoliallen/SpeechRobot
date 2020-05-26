package com.example.common.view.activity.advert;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.common.R;
import com.example.common.consts.CommonPreferenceConst;
import com.example.common.consts.CommonTimeConst;
import com.example.common.presenter.impl.BasePresenter;
import com.example.common.utils.BarUtil;
import com.example.common.utils.TimerTask;
import com.example.common.utils.wrapper.PreferenceWrapper;
import com.example.common.view.activity.BaseActivity;

/**
 * Created by xxdr on 2018/3/12.
 */

public abstract class BaseAdvertActivity<T extends BasePresenter> extends BaseActivity<T> implements
    AdvertImageLoadTask.CallBack, TimerTask.CallBack {

  public static final String PARAMS_URL = "img_url";
  public static final int SHOW_SECONDS = 3;

  private ImageView mIvAd;
  private TextView mTvSkip;
  private int mCurrentSecond = 0;

  private TimerTask<BaseAdvertActivity> mTimerTask;

  @Override
  protected int getContentViewId() {
    return R.layout.common_activity_base_advert;
  }

  @Override
  protected void beforeSetContentView() {
    super.beforeSetContentView();
    BarUtil.hideActionBar(this);
    BarUtil.fullScreen(this);
  }

  @Override
  protected void initView() {
    super.initView();
    mIvAd = (ImageView) findViewById(R.id.iv_ad);
    mTvSkip = (TextView) findViewById(R.id.tv_skip);
    mTimerTask = new TimerTask<>();
  }

  @Override
  protected void initData() {
    super.initData();

    // finishOnTime();
    mTimerTask.startTimerTask(this, 0, CommonTimeConst.S);

    String oldUrl = PreferenceWrapper.getString(this, CommonPreferenceConst.MAIN_PREFERENCE_FILE,
        CommonPreferenceConst.PREFERENCE_KEY.SPLASH_URL, "");
    if (TextUtils.isEmpty(oldUrl)) {
      onFinish();
      finish();
    }
    new AdvertImageLoadTask(this).execute(oldUrl, getIntent().getStringExtra(PARAMS_URL));
  }

  /**
   * 定时关闭
   */
  // private void finishOnTime() {
  //     Handler handler = new Handler();
  //     handler.postDelayed(new Runnable() {
  //         @Override
  //         public void run() {
  //             if (!isDestroyed() && !isFinishing()) {
  //                 onFinish();
  //                 finish();
  //             }
  //         }
  //     }, SHOW_SECONDS * 1000);
  // }
  @Override
  protected void initEvent() {
    super.initEvent();

    mTvSkip.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onFinish();
        finish();
      }
    });
  }


  @Override
  public void onBackPressed() {
  }

  @Override
  protected void onDestroy() {
    mTimerTask.stopTimerTask();
    super.onDestroy();
  }

  @Override
  public void setImage(String filePath) {
    Glide.with(this).load(filePath).centerCrop().dontAnimate().into(mIvAd);
  }

  @Override
  final public void call() {
    if (mCurrentSecond == SHOW_SECONDS) {
      if (!isDestroyed() && !isFinishing()) {
        onFinish();
        finish();
      }
    } else {
      mTvSkip.setText(
          getString(R.string.common_base_advert_count_down_btn, SHOW_SECONDS - mCurrentSecond));
      mCurrentSecond++;
    }
  }

  protected abstract void onFinish();
}