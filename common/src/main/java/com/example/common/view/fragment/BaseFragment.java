package com.example.common.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.example.common.R;
import com.example.common.presenter.impl.BasePresenter;
import com.example.common.utils.SoftKeyboardUtil;
import com.example.common.widget.TitleBarView;

/**
 * Created by xxdr on 2017/7/12.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

  protected String TAG = this.getClass().getSimpleName();

  protected T mPresenter;
  protected View mView;
  protected Activity mContext;
  protected TitleBarView mTitleBar;
  private Unbinder mUnbinder;

  private boolean mIsInitView = false;
  private boolean mIsVisible = false;
  private boolean mIsLoaded = false;

  //public void launch(Class targetClass) {
  //  Intent intent = new Intent(mContext, targetClass);
  //  //intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
  //  mContext.startActivity(intent);
  //  //mContext.overridePendingTransition(R.anim.common_activity_slide_from_right, R.anim.common_activity_slide_to_left);
  //}

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext = (Activity) getContext();
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    if (mView == null) {
      mView = inflater.inflate(getContentViewId(), container, false);
      mUnbinder = ButterKnife.bind(this, mView);
      mIsLoaded = false;
    } else {
      ViewGroup parent = (ViewGroup) mView.getParent();
      if (parent != null) {
        parent.removeView(mView);
      }
    }
    return mView;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (mPresenter == null) {
      mPresenter = createPresenter();
    }

    if (!mIsInitView) {
      initView();
      mIsInitView = true;
      initEvent();
      initData();
    }
    if (mIsInitView && mIsVisible && !mIsLoaded) {
      mIsLoaded = lazyLoad();
    }
  }

  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    mIsVisible = isVisibleToUser;
    if (mIsInitView && mIsVisible && !mIsLoaded) {
      mIsLoaded = lazyLoad();
    }
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
    mTitleBar = findViewById(R.id.title_bar);
  }

  /**
   * 设置事件
   */
  protected void initEvent() {
    if (mView != null) {
      mView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          SoftKeyboardUtil.hideSoftKeyboard(mContext);
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
   * 懒加载，推荐在 ViewPager 中使用，替换 initData。
   */
  protected boolean lazyLoad() {
    return true;
  }

  @Override
  public void onDestroy() {
    if (mPresenter != null) {
      mPresenter.onDestroy();
      mPresenter = null;
    }
    mUnbinder.unbind();
    super.onDestroy();
  }

  protected <T extends View> T findViewById(int id) {
    View view = mView.findViewById(id);
    return (T) view;
  }
}
