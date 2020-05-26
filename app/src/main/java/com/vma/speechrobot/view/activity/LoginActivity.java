package com.vma.speechrobot.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.example.common.utils.BarUtil;
import com.example.common.utils.init.T;
import com.example.common.view.activity.BaseActivity;
import com.vma.speechrobot.R;
import com.vma.speechrobot.presenter.LoginPresenter;
import com.vma.speechrobot.presenter.impl.ILoginPresenter;
import com.vma.speechrobot.utils.DimenTool;

public class LoginActivity extends BaseActivity<LoginPresenter> implements ILoginPresenter.IView {

  /*@BindView(R.id.iv_logo)
  ImageView ivLogo;*/
  @BindView(R.id.tv_title)
  TextView tvTitle;
  @BindView(R.id.et_account)
  EditText etAccount;
  @BindView(R.id.et_password)
  EditText etPassword;
  @BindView(R.id.btn_submit)
  Button btnSubmit;
//  @BindView(R.id.tv_type_enterprise)
//  TextView tvTypeEnterprise;
//  @BindView(R.id.tv_type_staff)
//  TextView tvTypeStaff;

  private int mAdminType = 5;

  public static void launch(Context context) {
    DimenTool.gen();
    Intent intent = new Intent(context, LoginActivity.class);
    context.startActivity(intent);
  }

  public static void clearLaunch(Context context) {
    T.showShort("请登录");
    Intent intent = new Intent(context, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
  }

  @Override
  protected int getContentViewId() {
    return R.layout.activity_login;
  }

  @Override
  protected LoginPresenter createPresenter() {
    return new LoginPresenter(this);
  }

  @Override
  protected void beforeSetContentView() {
    super.beforeSetContentView();
    BarUtil.fullScreen(this);
    BarUtil.hideActionBar(this);
  }

  @Override
  public Context getContext() {
    return this;
  }

//  @Override
//  protected void initView() {
//    super.initView();
//    loginType(tvTypeEnterprise);
//  }
//
//  @OnClick({R.id.tv_type_enterprise, R.id.tv_type_staff})
//  void loginType(View view) {
//    tvTypeEnterprise.setSelected(false);
//    tvTypeStaff.setSelected(false);
//    view.setSelected(true);
//    switch (view.getId()) {
//      case R.id.tv_type_enterprise:
//        mAdminType = 5;
//        break;
//      case R.id.tv_type_staff:
//        mAdminType = 6;
//        break;
//    }
//  }

  @OnClick(R.id.tv_forget)
  void forgetClick() {
    ForgetActivity.launch(this);
  }

  @OnClick(R.id.btn_submit)
  void submitClick() {
    if (checkSubmit()) {
      mPresenter
          .login(etAccount.getText().toString(), etPassword.getText().toString(), mAdminType, 1);
    }
  }

  private boolean checkSubmit() {
    if (TextUtils.isEmpty(etAccount.getText().toString())) {
      T.showShort("请输入账号");
      return false;
    }
    if (TextUtils.isEmpty(etPassword.getText().toString())) {
      T.showShort("请输入密码");
      return false;
    }
    return true;
  }

  @Override
  public void loginSuccess() {
    MainActivity.clearLaunch(this);
  }
}
