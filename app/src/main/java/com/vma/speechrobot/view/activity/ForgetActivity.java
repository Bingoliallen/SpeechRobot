package com.vma.speechrobot.view.activity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import com.example.common.utils.BarUtil;
import com.example.common.utils.ValidateUtil;
import com.example.common.utils.init.T;
import com.example.common.view.activity.BaseActivity;
import com.example.common.widget.CountDownButton;
import com.example.common.widget.CountDownButton.OnClickListener;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.request.FindPwdBean;
import com.vma.speechrobot.presenter.ForgetPresenter;
import com.vma.speechrobot.presenter.impl.IForgetPresenter;

public class ForgetActivity extends BaseActivity<ForgetPresenter> implements
    IForgetPresenter.IView {

  @BindView(R.id.mScrollView)
  ScrollView mScrollView;
  @BindView(R.id.tv_back)
  TextView tv_back;
  @BindView(R.id.et_account)
  EditText etAccount;
  @BindView(R.id.et_code)
  EditText etCode;
  @BindView(R.id.btn_code)
  CountDownButton btnCode;
  @BindView(R.id.et_password)
  EditText etPassword;
  @BindView(R.id.et_password_again)
  EditText etPasswordAgain;
  @BindView(R.id.btn_submit)
  Button btnSubmit;
//  @BindView(R.id.tv_type_enterprise)
//  TextView tvTypeEnterprise;
//  @BindView(R.id.tv_type_staff)
//  TextView tvTypeStaff;

  private int mAdminType = 5;

  public static void launch(Context context) {
    Intent intent = new Intent(context, ForgetActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected int getContentViewId() {
    return R.layout.activity_forget;
  }

  @Override
  protected void beforeSetContentView() {
    super.beforeSetContentView();
    BarUtil.fullScreen(this);
    BarUtil.hideActionBar(this);
  }

  @Override
  protected ForgetPresenter createPresenter() {
    return new ForgetPresenter(this);
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

  @Override
  protected void initEvent() {
    super.initEvent();
    tv_back.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
    btnCode.setListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mPresenter.getCode(etAccount.getText().toString());
      }

      @Override
      public boolean beforeOnClick() {
        boolean flag = ValidateUtil.isMobile(etAccount.getText().toString());
        if (!flag) {
          T.showShort("请输入正确的手机号码");
        }
        return flag;
      }
    });
    mScrollView.post(new Runnable() {
      @Override
      public void run() {
        mScrollView.scrollTo(0, 0);
      }
    });
  }

  @OnClick(R.id.btn_submit)
  void submitClick() {
    if (check()) {
      FindPwdBean request = new FindPwdBean();
      request.admin_type = mAdminType;
      request.mobile = etAccount.getText().toString();
      request.msg_code = etCode.getText().toString();
      request.password = etPassword.getText().toString();
      mPresenter.forget(request);
    }
  }

  @Override
  public void forgetSuccess() {
    T.showShort("修改成功");
    finish();
  }

  private boolean check() {
    if (!ValidateUtil.isMobile(etAccount.getText().toString())) {
      T.showShort("请输入正确的手机号");
      return false;
    }
    if (TextUtils.isEmpty(etCode.getText().toString())) {
      T.showShort("请输入验证码");
      return false;
    }
    if (TextUtils.isEmpty(etPassword.getText().toString())) {
      T.showShort("请输入新密码");
      return false;
    }
    if (etPassword.getText().toString().length() < 6
        || etPassword.getText().toString().length() > 12) {
      T.showShort("请输入正确的新密码");
      return false;
    }
    if (TextUtils.isEmpty(etPasswordAgain.getText().toString())) {
      T.showShort("再次输入新密码");
      return false;
    }
    if (!etPassword.getText().toString().equals(etPasswordAgain.getText().toString())) {
      T.showShort("密码输入不一致");
      return false;
    }
    return true;
  }
}
