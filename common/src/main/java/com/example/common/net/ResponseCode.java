package com.example.common.net;

/**
 * Created by xxdr on 2017/7/17.
 */

public class ResponseCode {

  public static final int RESPONSE_NULL = -1;
  public static final int SUCCESS = 200;                  //成功
  public static final int LOGIC_ERROR = 500;              //业务逻辑错误
  public static final int AUTH_ERROR = 401;               //认证失败，返回登录页面

  private ResponseCode() {
    /* cannot be instantiated */
    throw new UnsupportedOperationException("cannot be instantiated");
  }
}
