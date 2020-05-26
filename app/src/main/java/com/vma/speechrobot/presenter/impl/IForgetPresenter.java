package com.vma.speechrobot.presenter.impl;

import com.example.common.presenter.IBasePresenter;
import com.vma.speechrobot.bean.request.FindPwdBean;

public interface IForgetPresenter extends IBasePresenter {

  void forget(FindPwdBean request);

  void getCode(String mobile);

  interface IView extends IBasePresenter.IView {

    void forgetSuccess();
  }
}
