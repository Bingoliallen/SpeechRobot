package com.vma.speechrobot.presenter.impl;

import com.example.common.presenter.IBasePresenter;

public interface ILoginPresenter extends IBasePresenter {

  void login(String account, String password, int adminType, int loginType);

  interface IView extends IBasePresenter.IView {

    void loginSuccess();
  }
}
