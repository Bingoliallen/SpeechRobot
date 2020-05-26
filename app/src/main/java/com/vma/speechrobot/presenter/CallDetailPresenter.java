package com.vma.speechrobot.presenter;

import android.text.TextUtils;
import com.example.common.net.RxHelper;
import com.example.common.net.RxHelper.Response2DataFuncRESTful;
import com.example.common.presenter.impl.BasePresenter;
import com.vma.speechrobot.bean.CallDetailBean;
import com.vma.speechrobot.model.TaskModel;
import com.vma.speechrobot.net.NetDialogSubscriber;
import com.vma.speechrobot.net.NetSubscriber;
import com.vma.speechrobot.presenter.impl.ICallDetailPresenter;
import com.vma.speechrobot.utils.UserInfoManager;
import okhttp3.ResponseBody;

public class CallDetailPresenter extends
    BasePresenter<ICallDetailPresenter.IView, TaskModel> implements ICallDetailPresenter {

  public CallDetailPresenter(IView view) {
    super(view);
  }

  @Override
  protected TaskModel createModel() {
    return new TaskModel();
  }

  @Override
  public void getCallDetail(int id) {
    String macKey = UserInfoManager.getMacKey(getView().getContext());
    if (TextUtils.isEmpty(macKey)) {
      return;
    }
    getCompositeSubscription().add(
        mModel.getCallDetail(macKey, Integer.valueOf(id))
            .compose(RxHelper.io_main())
            .map(new Response2DataFuncRESTful())
            .subscribe(new NetDialogSubscriber<CallDetailBean>(getView().getContext()) {
              @Override
              public int configuration() {
                return CANCEL | FINISH;
              }

              @Override
              public void onNext(CallDetailBean callDetailBean) {
                super.onNext(callDetailBean);
                if (isAttach()) {
                  getView().fillCallDetail(callDetailBean);
                }
              }
            }));
  }

  @Override
  public void updateCallTime(int id) {
    String macKey = UserInfoManager.getMacKey(getView().getContext());
    if (TextUtils.isEmpty(macKey)) {
      return;
    }
    getCompositeSubscription()
        .add(mModel.updateCallTime(macKey, id)
            .compose(RxHelper.io_main())
            .map(new Response2DataFuncRESTful())
            .subscribe(new NetSubscriber<ResponseBody>(getView().getContext()) {
              @Override
              public void onNext(ResponseBody responseBody) {
                super.onNext(responseBody);
                if (isAttach()) {
                  getView().updateCallTimeSuccess();
                }
              }
            }));
  }
}
