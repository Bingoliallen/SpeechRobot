package com.vma.speechrobot.presenter;

import android.text.TextUtils;
import com.example.common.net.RxHelper;
import com.example.common.net.RxHelper.Response2DataFuncRESTful;
import com.example.common.presenter.impl.BasePresenter;
import com.example.common.utils.ValueUtil;
import com.vma.speechrobot.bean.TaskDetailBean;
import com.vma.speechrobot.bean.TaskInfoBean;
import com.vma.speechrobot.model.TaskModel;
import com.vma.speechrobot.net.NetSubscriber;
import com.vma.speechrobot.presenter.impl.ITaskInfoPresenter;
import com.vma.speechrobot.utils.UserInfoManager;
import java.util.Arrays;
import rx.functions.Func1;

public class TaskInfoPresenter extends BasePresenter<ITaskInfoPresenter.IView, TaskModel> implements
    ITaskInfoPresenter {

  public TaskInfoPresenter(IView view) {
    super(view);
  }

  @Override
  protected TaskModel createModel() {
    return new TaskModel();
  }

  @Override
  public void getTaskInfo(int id) {
    String macKey = UserInfoManager.getMacKey(getView().getContext());
    if (TextUtils.isEmpty(macKey)) {
      return;
    }
    getCompositeSubscription()
        .add(mModel.getTaskInfo(macKey, id)
            .compose(RxHelper.io_main())
            .map(new Response2DataFuncRESTful())
            .subscribe(new NetSubscriber<TaskInfoBean>(getView().getContext()) {
              @Override
              public void onNext(TaskInfoBean taskInfoBean) {
                super.onNext(taskInfoBean);
                if (isAttach()) {
                  getView().fillTaskInfo(taskInfoBean);
                }
              }

              @Override
              public void onError(Throwable e) {
                super.onError(e);
                if (isAttach()) {
                  getView().fillTaskInfoF();
                }
              }
            }));
  }

  @Override
  public void getTaskDetail(int id) {
    String macKey = UserInfoManager.getMacKey(getView().getContext());
    if (TextUtils.isEmpty(macKey)) {
      return;
    }
    getCompositeSubscription()
        .add(mModel.getTaskDetail(macKey, id)
            .compose(RxHelper.io_main())
            .map(new Response2DataFuncRESTful())
            .map(new Func1<TaskDetailBean, TaskDetailBean>() {

              @Override
              public TaskDetailBean call(TaskDetailBean taskDetailBean) {
                String[] weekData = taskDetailBean.week_date.split(",");
                for (int i = 0; i < weekData.length; i++) {
                  weekData[i] = weekTrans(weekData[i]);
                }
                taskDetailBean.week_date = ValueUtil.array2String(Arrays.asList(weekData), "，");
                String[] excludeData = taskDetailBean.exclude_date.split(",");
                for (int i = 0; i < excludeData.length; i++) {
                  excludeData[i] = weekTrans(excludeData[i]);
                }
                taskDetailBean.exclude_date = ValueUtil
                    .array2String(Arrays.asList(excludeData), "，");
                return taskDetailBean;
              }
            })
            .subscribe(new NetSubscriber<TaskDetailBean>(getView().getContext()) {
              @Override
              public void onNext(TaskDetailBean taskDetailBean) {
                super.onNext(taskDetailBean);
                if (isAttach()) {
                  getView().fillTaskDetail(taskDetailBean);
                }
              }
            }));
  }

  private static String weekTrans(String i) {
    switch (i) {
      case "1":
        return "周一";
      case "2":
        return "周二";
      case "3":
        return "周三";
      case "4":
        return "周四";
      case "5":
        return "周五";
      case "6":
        return "周六";
      case "7":
        return "周日";
    }
    return "";
  }
}
