package com.example.common.net.response;

/**
 * Created by xxdr on 2018/4/27.
 */

public class ErrorResponse {

  /**
   * err_code   错误码
   * err_msg    错误原因
   * request_id
   * err_type   0系统错误;1业务错误
   */
  public int err_code;
  public String err_msg;
  public String request_id;
  public int err_type;

  public ErrorResponse(int err_code, String err_msg) {
    this.err_code = err_code;
    this.err_msg = err_msg;
  }
}
