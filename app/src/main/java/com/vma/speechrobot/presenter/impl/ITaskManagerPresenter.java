package com.vma.speechrobot.presenter.impl;

import com.example.common.presenter.IBasePresenter;
import com.vma.speechrobot.bean.CreateTask;
import com.vma.speechrobot.bean.PageBean;
import com.vma.speechrobot.bean.ReqUpdateTask;
import com.vma.speechrobot.bean.TaskBean;
import com.vma.speechrobot.bean.TaskCardOption;
import com.vma.speechrobot.bean.WhisperingOption;

import retrofit2.Response;

public interface ITaskManagerPresenter extends IBasePresenter {

  void getTaskList(String name, int pageNum, int pageSize);
  void getTaskManangerList(
          Integer status,
                           String card_relate_id,
                           String whispering_id,
                           String create_time_begin,
                           String create_time_end,
                           String idOrName, int pageNum, int pageSize);
  void getTaskCardOption();
  void getWhisperingOption();
  void updatetask(ReqUpdateTask reqUpdateTask);
  void deletetask(int id);


  interface IView extends IBasePresenter.IView {

    void fillTaskList(PageBean<TaskBean> data);
    void fillTaskManangerList(PageBean<TaskBean> data);
    void fillTaskCardOption(TaskCardOption[] data);
    void fillWhisperingOption(WhisperingOption[] data);
    void fillupdatetask();
    void filldeletetask();

  }
}
