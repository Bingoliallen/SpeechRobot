package com.example.common.utils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by xxdr on 2018/4/17.
 */

public class FragmentUtil {

  public static final String FRAGMENT_NAME = "fragment_name";

  private FragmentManager mFragmentManager;
  private Fragment mCurrentFragment;
  private int mCurrentNumber = 0;

  public FragmentUtil(FragmentManager fragmentManager) {
    mFragmentManager = fragmentManager;
  }

  /**
   * 更换 fragment
   */
  public void changeFragment(int containerViewId, Class classFragment) {
    changeFragment(containerViewId, classFragment, 0);
  }

  public void changeFragment(int containerViewId, Class classFragment, int number) {
    changeFragment(containerViewId, classFragment, number, null);
  }

  public void changeFragment(int containerViewId, Class classFragment, Bundle args) {
    changeFragment(containerViewId, classFragment, 0, args);
  }

  /**
   * 更换 fragment
   */
  public void changeFragment(int containerViewId, Class classFragment, int number, Bundle args) {
    if (mCurrentFragment != null && mCurrentFragment.getClass() == classFragment
        && mCurrentNumber == number) {
      return;
    }
    FragmentTransaction ft = mFragmentManager.beginTransaction();
    if (mCurrentFragment != null) {
      ft.hide(mCurrentFragment);
    }
    String fragmentName = String.format("%s%d", classFragment.getName(), number);
    Fragment fragment = findFragmentByTag(classFragment, number);
    if (fragment == null) {
      fragment = newInstanceFragment(classFragment);
      if (args != null) {
        args.putString(FRAGMENT_NAME, fragmentName);
        fragment.setArguments(args);
      }
    }
    mCurrentFragment = fragment;
    mCurrentNumber = number;
    if (!fragment.isAdded()) {
      ft.add(containerViewId, fragment, fragmentName);
    } else {
      ft.show(fragment);
    }
    ft.commit();
  }

  public Class getCurrentFragmentClass() {
    if (mCurrentFragment == null) {
      return null;
    }
    return mCurrentFragment.getClass();
  }

  /**
   * 创建fragment实例
   */
  @SuppressWarnings("unchecked")
  private <T extends Fragment> T newInstanceFragment(Class<T> fragmentClass) {
    Fragment showFragment = null;
    try {
      showFragment = fragmentClass.newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return (T) showFragment;
  }

  @SuppressWarnings("unchecked")
  private <T extends Fragment> T findFragmentByTag(Class<T> fragmentClass, int number) {
    return (T) mFragmentManager
        .findFragmentByTag(String.format("%s%d", fragmentClass.getName(), number));
  }

}