package com.vma.speechrobot.presenter;

import android.text.TextUtils;
import com.example.common.net.RxHelper;
import com.example.common.net.RxHelper.Response2DataFuncRESTful;
import com.example.common.presenter.impl.BasePresenter;
import com.vma.speechrobot.bean.CreateTask;
import com.vma.speechrobot.bean.PageBean;
import com.vma.speechrobot.bean.ReqUpdateTask;
import com.vma.speechrobot.bean.TaskBean;
import com.vma.speechrobot.bean.TaskCardOption;
import com.vma.speechrobot.bean.WhisperingOption;
import com.vma.speechrobot.model.TaskModel;
import com.vma.speechrobot.net.NetSubscriber;
import com.vma.speechrobot.presenter.impl.ITaskManagerPresenter;
import com.vma.speechrobot.utils.UserInfoManager;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class TaskManagerPresenter extends
    BasePresenter<ITaskManagerPresenter.IView, TaskModel> implements ITaskManagerPresenter {

  public TaskManagerPresenter(IView view) {
    super(view);
  }

  @Override
  protected TaskModel createModel() {
    return new TaskModel();
  }

  @Override
  public void getTaskList(String name, int pageNum, int pageSize) {
    String macKey = UserInfoManager.getMacKey(getView().getContext());
    if (TextUtils.isEmpty(macKey)) {
      return;
    }
    getCompositeSubscription().add(mModel
        .getTaskList(macKey, pageNum, pageSize, name,
            null)
        .compose(RxHelper.io_main())
        .map(new Response2DataFuncRESTful())
        .subscribe(new NetSubscriber<PageBean<TaskBean>>(getView().getContext()) {
          @Override
          public void onNext(PageBean<TaskBean> taskBeanPageBean) {
            super.onNext(taskBeanPageBean);
            if (isAttach()) {
              getView().fillTaskList(taskBeanPageBean);
            }
          }
        }));
  }

  @Override
  public void getTaskManangerList(Integer status,
                                  String card_relate_id,
                                  String whispering_id,
                                  String create_time_begin,
                                  String create_time_end,
                                  String idOrName, int pageNum, int pageSize) {
    String macKey = UserInfoManager.getMacKey(getView().getContext());
    if (TextUtils.isEmpty(macKey)) {
      return;
    }
    getCompositeSubscription().add(mModel
            .getTaskManangerList(macKey, status,
                    card_relate_id, whispering_id, create_time_begin, create_time_end, idOrName, pageNum, pageSize)
            .compose(RxHelper.io_main())
            .map(new Response2DataFuncRESTful())
            .subscribe(new NetSubscriber<PageBean<TaskBean>>(getView().getContext()) {
              @Override
              public void onNext(PageBean<TaskBean> taskBeanPageBean) {
                super.onNext(taskBeanPageBean);
                if (isAttach()) {
                  getView().fillTaskManangerList(taskBeanPageBean);
                }
              }
            }));
  }

    @Override
    public void getTaskCardOption() {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription().add(mModel
                .getTaskCardOption(macKey)
                .compose(RxHelper.io_main())
                .map(new Response2DataFuncRESTful())
                .subscribe(new NetSubscriber<TaskCardOption[]>(getView().getContext()) {
                    @Override
                    public void onNext(TaskCardOption[] taskBeanPageBean) {
                        super.onNext(taskBeanPageBean);
                        if (isAttach()) {
                            getView().fillTaskCardOption(taskBeanPageBean);
                        }
                    }
                }));
    }

    @Override
    public void getWhisperingOption() {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription().add(mModel
                .getWhisperingOption(macKey)
                .compose(RxHelper.io_main())
                .map(new Response2DataFuncRESTful())
                .subscribe(new NetSubscriber<WhisperingOption[]>(getView().getContext()) {
                    @Override
                    public void onNext(WhisperingOption[] taskBeanPageBean) {
                        super.onNext(taskBeanPageBean);
                        if (isAttach()) {
                            getView().fillWhisperingOption(taskBeanPageBean);
                        }
                    }
                }));
    }

    @Override
    public void updatetask(ReqUpdateTask reqUpdateTask) {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription().add(mModel
                .updatetask(macKey,reqUpdateTask)
                .compose(RxHelper.io_main())
                .map(new Response2DataFuncRESTful())
                .subscribe(new NetSubscriber<ResponseBody>(getView().getContext()) {
                    @Override
                    public void onNext(ResponseBody taskBeanPageBean) {
                        super.onNext(taskBeanPageBean);
                        if (isAttach()) {
                            getView().fillupdatetask();
                        }
                    }
                }));
    }

    @Override
    public void deletetask(int id) {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription().add(mModel
                .deletetask(macKey,id)
                .compose(RxHelper.io_main())
                .map(new Response2DataFuncRESTful())
                .subscribe(new NetSubscriber<ResponseBody>(getView().getContext()) {
                    @Override
                    public void onNext(ResponseBody taskBeanPageBean) {
                        super.onNext(taskBeanPageBean);
                        if (isAttach()) {
                            getView().filldeletetask();
                        }
                    }
                }));
    }

}
