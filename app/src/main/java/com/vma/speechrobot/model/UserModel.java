package com.vma.speechrobot.model;

import com.example.common.BaseAppProfile;
import com.vma.speechrobot.api.AppApi;
import com.vma.speechrobot.bean.request.FindPwdBean;
import rx.Observable;

public class UserModel {

  public Observable login(String account, String password, int adminType, int loginType) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
        .login(account, password, adminType, loginType);
  }

  public Observable findPwd(FindPwdBean request) {
    return BaseAppProfile.app_client.getApi(AppApi.class).findPwd(request);
  }

  public Observable getCode(String mobile){
    return BaseAppProfile.app_client.getApi(AppApi.class).getCode(mobile);
  }
}
