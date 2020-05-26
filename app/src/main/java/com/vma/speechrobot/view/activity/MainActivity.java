package com.vma.speechrobot.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.adapter.recycler.divider.LinearLayoutColorDivider;
import com.example.common.presenter.impl.BasePresenter;
import com.example.common.utils.BarUtil;
import com.example.common.utils.FragmentUtil;
import com.example.common.utils.init.T;
import com.example.common.view.activity.BaseActivity;
import com.vma.speechrobot.R;
import com.vma.speechrobot.adapter.MainMenuAdapter;
import com.vma.speechrobot.bean.MenuItemBean;
import com.vma.speechrobot.event.MainMenuEvent;
import com.vma.speechrobot.utils.UserInfoManager;
import com.vma.speechrobot.view.fragment.HomepageFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

  @BindView(R.id.fl_container)
  FrameLayout flContainer;
  @BindView(R.id.iv_avatar)
  ImageView ivAvatar;
  @BindView(R.id.tv_name)
  TextView tvName;
  @BindView(R.id.recycler_view_menu)
  RecyclerView recyclerViewMenu;
  @BindView(R.id.dl_menu)
  DrawerLayout dlMenu;
  private MainMenuAdapter mAdapter;
  private FragmentUtil mFragmentUtil;

  public static void launch(Context context) {
    Intent intent = new Intent(context, MainActivity.class);
    context.startActivity(intent);
  }

  public static void clearLaunch(Context context) {
    Intent intent = new Intent(context, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  @Override
  protected int getContentViewId() {
    return R.layout.activity_main;
  }

  @Override
  protected BasePresenter createPresenter() {
    return null;
  }

  @Subscribe(threadMode = ThreadMode.MAIN)
  public void onEvent(MainMenuEvent event) {
    if (event.position != null) {
      switch (event.position) {
        case MainMenuEvent.HOMEPAGE:
          if (dlMenu.isDrawerOpen(Gravity.LEFT)) {
            dlMenu.closeDrawer(Gravity.LEFT);
          }
          mFragmentUtil.changeFragment(R.id.fl_container, HomepageFragment.class);
          break;
        case MainMenuEvent.TASK_MANAGER:
          if (dlMenu.isDrawerOpen(Gravity.LEFT)) {
            dlMenu.closeDrawer(Gravity.LEFT);
          }
//          mFragmentUtil.changeFragment(R.id.fl_container, TaskManagerFragment.class);
          TaskManagerNewActivity.launch(this);
          break;
        case MainMenuEvent.TASK_CALL:
          if (dlMenu.isDrawerOpen(Gravity.LEFT)) {
            dlMenu.closeDrawer(Gravity.LEFT);
          }
//          mFragmentUtil.changeFragment(R.id.fl_container, TaskManagerFragment.class);
          CallRecordActivity.launch(this);
          break;
      }
    } else if (!dlMenu.isDrawerOpen(Gravity.LEFT)) {
      dlMenu.openDrawer(Gravity.LEFT);
    }
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    UserInfoManager.getMacKey(this);
    super.onCreate(savedInstanceState);
    EventBus.getDefault().register(this);
  }

  @Override
  protected void onDestroy() {
    EventBus.getDefault().unregister(this);
    super.onDestroy();
  }

  @Override
  protected void beforeSetContentView() {
    super.beforeSetContentView();
    BarUtil.fullScreen(this);
    BarUtil.hideActionBar(this);

  }

  @Override
  protected void initView() {
    super.initView();
    mFragmentUtil = new FragmentUtil(getSupportFragmentManager());
   // ImageWrapper.setCircleImage(ivAvatar, UserInfoManager.get().icon);
    tvName.setText(UserInfoManager.get().name);
    mFragmentUtil.changeFragment(R.id.fl_container, HomepageFragment.class);
    mAdapter = new MainMenuAdapter(this);
    recyclerViewMenu
        .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    recyclerViewMenu.addItemDecoration(
        new LinearLayoutColorDivider(getResources(), R.color.common_app_text_theme8,
            R.dimen.divider_thin, LinearLayoutManager.VERTICAL));
    recyclerViewMenu.setAdapter(mAdapter);
    List<MenuItemBean> menus = new ArrayList<>();
    menus.add(new MenuItemBean(R.mipmap.nav_1, "首页"));
    menus.add(new MenuItemBean(R.mipmap.nav_2, "任务管理"));
    menus.add(new MenuItemBean(R.mipmap.nav_3, "呼叫记录"));
    menus.add(new MenuItemBean(R.mipmap.nav_4, "退出登录"));
    mAdapter.set(menus);
  }

  @Override
  protected void initEvent() {
    super.initEvent();
  }

  @OnClick(R.id.iv_avatar)
  void avatarClick() {

  }

  @Override
  public void onBackPressed() {
    if (dlMenu.isDrawerOpen(Gravity.LEFT)) {
      dlMenu.closeDrawer(Gravity.LEFT);
    } else {
      T.exitApplication(this);
    }
  }

}
