package com.vma.speechrobot.presenter;

import com.example.common.net.RxHelper;
import com.example.common.net.RxHelper.Response2DataFuncRESTful;
import com.example.common.presenter.impl.BasePresenter;
import com.vma.speechrobot.bean.UserInfoBean;
import com.vma.speechrobot.model.UserModel;
import com.vma.speechrobot.net.NetDialogSubscriber;
import com.vma.speechrobot.presenter.impl.ILoginPresenter;
import com.vma.speechrobot.utils.UserInfoManager;

public class LoginPresenter extends BasePresenter<ILoginPresenter.IView, UserModel> implements
    ILoginPresenter {

  public LoginPresenter(IView view) {
    super(view);
  }

  @Override
  protected UserModel createModel() {
    return new UserModel();
  }

  @Override
  public void login(String account, String password, int adminType, int loginType) {
    getCompositeSubscription().add(mModel.login(account, password, adminType, loginType)
        .compose(RxHelper.io_main())
        .map(new Response2DataFuncRESTful())
        .subscribe(new NetDialogSubscriber<UserInfoBean>(getView().getContext()) {

          @Override
          public int configuration() {
            return DEFAULT;
          }

          @Override
          public void onNext(UserInfoBean userInfoBean) {
            super.onNext(userInfoBean);
            if (isAttach()) {
              UserInfoManager.save(userInfoBean);
              getView().loginSuccess();
            }
          }
        }));
  }
}
