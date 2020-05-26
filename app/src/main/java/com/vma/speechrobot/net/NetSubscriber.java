package com.vma.speechrobot.net;

import android.content.Context;
import com.example.common.BuildConfig;
import com.example.common.net.ApiException;
import com.example.common.net.ResponseCode;
import com.example.common.utils.L;
import com.vma.speechrobot.utils.UserInfoManager;
import com.vma.speechrobot.view.activity.LoginActivity;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.net.ConnectException;
import rx.Subscriber;

/**
 * Created by xxdr on 2018/4/28.
 */

public abstract class NetSubscriber<T> extends Subscriber<T> {

  private static final String TAG = "NetSubscriber";

  protected WeakReference<Context> mContext;

  public NetSubscriber(Context context) {
    mContext = new WeakReference<>(context);
  }

  @Override
  public void onError(Throwable e) {
    if (e != null) {
      if (BuildConfig.IS_DEBUG) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        L.d(TAG, String.format("onError: %s", sw.toString()));
      }
      if (e instanceof ConnectException) {
        com.example.common.utils.init.T.showShort("网络连接有问题");
      }
      if (e instanceof ApiException) {
        switch (((ApiException) e).code) {
          case ResponseCode.AUTH_ERROR:
            if (mContext.get() != null) {
              UserInfoManager.delete();
              LoginActivity.clearLaunch(mContext.get());
            }
            break;
          case 400:
            com.example.common.utils.init.T
                .showShort(String.format("err_code：%d", ((ApiException) e).errorResponse.err_code),
                    com.example.common.utils.init.T.ERROR);
            break;
          case 404:
            com.example.common.utils.init.T.showNetworkError();
            break;
          default:
            com.example.common.utils.init.T.showShort(e.getMessage());
            break;
        }
      }
    } else {
      L.d(TAG, "onError: e is null");
    }
  }

  @Override
  public void onNext(T t) {
  }

  @Override
  public void onCompleted() {
  }
}
