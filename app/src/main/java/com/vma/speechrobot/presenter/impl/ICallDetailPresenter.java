package com.vma.speechrobot.presenter.impl;

import com.example.common.presenter.IBasePresenter;
import com.vma.speechrobot.bean.CallDetailBean;

public interface ICallDetailPresenter extends IBasePresenter {

  void getCallDetail(int id);

  void updateCallTime(int id);

  interface IView extends IBasePresenter.IView {

    void fillCallDetail(CallDetailBean data);

    void updateCallTimeSuccess();
  }
}
