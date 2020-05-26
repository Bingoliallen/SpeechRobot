package com.vma.speechrobot.presenter.impl;

import com.example.common.presenter.IBasePresenter;
import com.vma.speechrobot.bean.TaskDetailBean;
import com.vma.speechrobot.bean.TaskInfoBean;

public interface ITaskInfoPresenter extends IBasePresenter {

  void getTaskInfo(int id);

  void getTaskDetail(int id);

  interface IView extends IBasePresenter.IView {
    void fillTaskInfoF();
    void fillTaskInfo(TaskInfoBean data);

    void fillTaskDetail(TaskDetailBean data);
  }
}
