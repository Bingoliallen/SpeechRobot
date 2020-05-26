package com.vma.speechrobot.presenter;

import android.text.TextUtils;
import com.example.common.net.RxHelper;
import com.example.common.presenter.impl.BasePresenter;
import com.vma.speechrobot.bean.CreateTask;
import com.vma.speechrobot.bean.Customer;
import com.vma.speechrobot.bean.Employee;
import com.vma.speechrobot.bean.MessageCount;
import com.vma.speechrobot.bean.MessageTemplate;
import com.vma.speechrobot.bean.PageBean;
import com.vma.speechrobot.bean.ReqAddTask;
import com.vma.speechrobot.bean.TaskCardOption;
import com.vma.speechrobot.bean.TaskDefault;
import com.vma.speechrobot.bean.TimeTemplateOption;
import com.vma.speechrobot.bean.WhisperingOption;
import com.vma.speechrobot.model.TaskModel;
import com.vma.speechrobot.net.NetSubscriber;
import com.vma.speechrobot.presenter.impl.ITaskCreatePresenter;
import com.vma.speechrobot.utils.UserInfoManager;

/**
 * Created by Administrator on 2018/12/31.
 */

public class TaskCreatePresenter extends BasePresenter<ITaskCreatePresenter.IView, TaskModel> implements
        ITaskCreatePresenter {

    public TaskCreatePresenter(IView view) {
        super(view);
    }


    @Override
    protected TaskModel createModel() {
        return new TaskModel();
    }


    @Override
    public void transferemployee() {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.transferemployee(macKey)
                        .compose(RxHelper.io_main())
                        .map(new RxHelper.Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<Employee[]>(getView().getContext()) {
                            @Override
                            public void onNext(Employee[] taskInfoBean) {
                                super.onNext(taskInfoBean);
                                if (isAttach()) {
                                    getView().filltransferemployee(taskInfoBean);
                                }
                            }
                        }));
    }

    @Override
    public void createtask(ReqAddTask mReqAddTask) {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.createtask(macKey,mReqAddTask)
                        .compose(RxHelper.io_main())
                        .map(new RxHelper.Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<CreateTask>(getView().getContext()) {
                            @Override
                            public void onNext(CreateTask taskInfoBean) {
                                super.onNext(taskInfoBean);
                                if (isAttach()) {
                                    getView().fillcreatetask(taskInfoBean);
                                }
                            }
                        }));
    }

    @Override
    public void taskdefault() {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.taskdefault(macKey)
                        .compose(RxHelper.io_main())
                        .map(new RxHelper.Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<TaskDefault>(getView().getContext()) {
                            @Override
                            public void onNext(TaskDefault taskInfoBean) {
                                super.onNext(taskInfoBean);
                                if (isAttach()) {
                                    getView().filltaskdefault(taskInfoBean);
                                }
                            }
                        }));
    }

    @Override
    public void message_count() {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.message_count(macKey)
                        .compose(RxHelper.io_main())
                        .map(new RxHelper.Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<MessageCount>(getView().getContext()) {
                            @Override
                            public void onNext(MessageCount taskInfoBean) {
                                super.onNext(taskInfoBean);
                                if (isAttach()) {
                                    getView().fillmessage_count(taskInfoBean);
                                }
                            }
                        }));
    }

    @Override
    public void messageTemplate(Integer type) {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.messageTemplate(macKey,type)
                        .compose(RxHelper.io_main())
                        .map(new RxHelper.Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<MessageTemplate[]>(getView().getContext()) {
                            @Override
                            public void onNext(MessageTemplate[] taskInfoBean) {
                                super.onNext(taskInfoBean);
                                if (isAttach()) {
                                    getView().fillmessageTemplate(taskInfoBean);
                                }
                            }
                        }));
    }

    @Override
    public void messageTemplate2(Integer type) {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.messageTemplate(macKey,type)
                        .compose(RxHelper.io_main())
                        .map(new RxHelper.Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<MessageTemplate[]>(getView().getContext()) {
                            @Override
                            public void onNext(MessageTemplate[] taskInfoBean) {
                                super.onNext(taskInfoBean);
                                if (isAttach()) {
                                    getView().fillmessageTemplate2(taskInfoBean);
                                }
                            }
                        }));
    }


    @Override
    public void employee(String employee_name) {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.employee(macKey,employee_name)
                        .compose(RxHelper.io_main())
                        .map(new RxHelper.Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<Employee[]>(getView().getContext()) {
                            @Override
                            public void onNext(Employee[] taskInfoBean) {
                                super.onNext(taskInfoBean);
                                if (isAttach()) {
                                    getView().fillemployee(taskInfoBean);
                                }
                            }
                        }));
    }

    @Override
    public void getFreeTaskCardOption() {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.getFreeTaskCardOption(macKey)
                        .compose(RxHelper.io_main())
                        .map(new RxHelper.Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<TaskCardOption[]>(getView().getContext()) {
                            @Override
                            public void onNext(TaskCardOption[] taskInfoBean) {
                                super.onNext(taskInfoBean);
                                if (isAttach()) {
                                    getView().fillgetFreeTaskCardOption(taskInfoBean);
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
        getCompositeSubscription()
                .add(mModel.getWhisperingOption(macKey)
                        .compose(RxHelper.io_main())
                        .map(new RxHelper.Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<WhisperingOption[]>(getView().getContext()) {
                            @Override
                            public void onNext(WhisperingOption[] taskInfoBean) {
                                super.onNext(taskInfoBean);
                                if (isAttach()) {
                                    getView().fillgetWhisperingOption(taskInfoBean);
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
        getCompositeSubscription()
                .add(mModel.getTaskCardOption(macKey)
                        .compose(RxHelper.io_main())
                        .map(new RxHelper.Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<TaskCardOption[]>(getView().getContext()) {
                            @Override
                            public void onNext(TaskCardOption[] taskInfoBean) {
                                super.onNext(taskInfoBean);
                                if (isAttach()) {
                                    getView().fillgetTaskCardOption(taskInfoBean);
                                }
                            }
                        }));
    }

    @Override
    public void taskTimeTemplateOption() {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.taskTimeTemplateOption(macKey)
                        .compose(RxHelper.io_main())
                        .map(new RxHelper.Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<TimeTemplateOption[]>(getView().getContext()) {
                            @Override
                            public void onNext(TimeTemplateOption[] taskInfoBean) {
                                super.onNext(taskInfoBean);
                                if (isAttach()) {
                                    getView().filltaskTimeTemplateOption(taskInfoBean);
                                }
                            }
                        }));
    }

    @Override
    public void getHumanCardRelate() {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.getHumanCardRelate(macKey)
                        .compose(RxHelper.io_main())
                        .map(new RxHelper.Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<TaskCardOption[]>(getView().getContext()) {
                            @Override
                            public void onNext(TaskCardOption[] taskInfoBean) {
                                super.onNext(taskInfoBean);
                                if (isAttach()) {
                                    getView().fillgetHumanCardRelate(taskInfoBean);
                                }
                            }
                        }));
    }


    @Override
    public void customer_list( Integer is_use , Integer page_num,Integer page_size) {

        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription()
                .add(mModel.customer_list(macKey,is_use,page_num,page_size)
                        .compose(RxHelper.io_main())
                        .map(new RxHelper.Response2DataFuncRESTful())
                        .subscribe(new NetSubscriber<PageBean<Customer>>(getView().getContext()) {
                            @Override
                            public void onNext(PageBean<Customer>  taskInfoBean) {
                                super.onNext(taskInfoBean);
                                if (isAttach()) {
                                    getView().fillcustomer_list(taskInfoBean);
                                }
                            }
                        }));

    }


}
