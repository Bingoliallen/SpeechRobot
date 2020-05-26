package com.example.common.net;

import android.util.Log;

import com.example.common.BaseAppProfile;
import com.example.common.net.response.CommonResponse;
import com.example.common.net.response.ErrorResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by xxdr on 2017/7/31.
 */

public class RxHelper {

  private RxHelper() {
  }

  /**
   * rxJava 线程设置
   */
  public static <T> Observable.Transformer<T, T> io_main() {
    return new Observable.Transformer<T, T>() {
      @Override
      public Observable<T> call(Observable<T> tObservable) {
        return tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  /**
   * 返回元素
   */
  public static class Response2DataFunc<T> implements Func1<CommonResponse<T>, T> {

    @Override
    public T call(CommonResponse<T> response) {
      if(response!=null){
       Log.e("LBB","-------------返回结果："+response.toString()) ;
      }
      if (response == null) {
        throw new ApiException(ResponseCode.RESPONSE_NULL, "response is null");
      } else if (ResponseCode.SUCCESS == response.code) {
        return response.results;
      } else {
        throw new ApiException(response.code, response.error);
      }
    }
  }

  /**
   * 返回元素
   */
  public static class Response2DataFuncRESTful<T> implements Func1<Response<T>, T> {

    @Override
    public T call(Response<T> response) {
      if (ResponseCode.SUCCESS == response.code()) {
        return response.body();
      } else {
        Converter<ResponseBody, ErrorResponse> errorConverter = BaseAppProfile.app_client.retrofit
            .responseBodyConverter(ErrorResponse.class, new Annotation[0]);
        ErrorResponse error = null;
        try {
          error = errorConverter.convert(response.errorBody());
        } catch (IOException e) {
          e.printStackTrace();
        }
        if (error == null) {
          throw new ApiException(response.code(),
              new ErrorResponse(ResponseCode.RESPONSE_NULL, "error response is null"));
        } else {
          throw new ApiException(response.code(), error);
        }
      }
    }
  }
}

