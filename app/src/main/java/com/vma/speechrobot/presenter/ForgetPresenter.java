package com.vma.speechrobot.presenter;

import com.example.common.net.RxHelper;
import com.example.common.net.RxHelper.Response2DataFuncRESTful;
import com.example.common.presenter.impl.BasePresenter;
import com.vma.speechrobot.bean.request.FindPwdBean;
import com.vma.speechrobot.model.UserModel;
import com.vma.speechrobot.net.NetDialogSubscriber;
import com.vma.speechrobot.net.NetSubscriber;
import com.vma.speechrobot.presenter.impl.IForgetPresenter;
import okhttp3.ResponseBody;

public class ForgetPresenter extends BasePresenter<IForgetPresenter.IView, UserModel> implements
    IForgetPresenter {

  public ForgetPresenter(IView view) {
    super(view);
  }

  @Override
  protected UserModel createModel() {
    return new UserModel();
  }

  @Override
  public void forget(FindPwdBean request) {
    getCompositeSubscription().add(mModel.findPwd(request)
        .compose(RxHelper.io_main())
        .map(new Response2DataFuncRESTful())
        .subscribe(new NetDialogSubscriber<ResponseBody>(getView().getContext()) {
          @Override
          public int configuration() {
            return CANCEL;
          }

          @Override
          public void onNext(ResponseBody responseBody) {
            super.onNext(responseBody);
            if (isAttach()) {
              getView().forgetSuccess();
            }
          }
        }));
  }

  @Override
  public void getCode(String mobile) {
    getCompositeSubscription().add(mModel.getCode(mobile)
        .compose(RxHelper.io_main())
        .map(new Response2DataFuncRESTful())
        .subscribe(new NetSubscriber<ResponseBody>(getView().getContext()) {
        }));
  }
}
