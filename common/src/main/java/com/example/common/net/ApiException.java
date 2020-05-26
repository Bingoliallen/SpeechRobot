package com.example.common.net;

import com.example.common.net.response.ErrorResponse;

/**
 * Created by xxdr on 2018/4/28.
 */

public class ApiException extends RuntimeException {

  public int code;
  public ErrorResponse errorResponse;

  public ApiException(int code, ErrorResponse errorResponse) {
    super(errorResponse.err_msg);
    this.code = code;
    this.errorResponse = errorResponse;
  }

  public ApiException(int code, String error) {
    super(error);
    this.code = code;
  }
}