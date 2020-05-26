package com.example.common.utils;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;
import java.util.Timer;

/**
 * Created by xxdr on 2017/12/1.
 */

public class TimerTask<T extends TimerTask.CallBack> {

  private Timer mTimer;
  private MyHandler mHandler;
  private java.util.TimerTask mTask;
  private boolean mIsRunning = false;

  /**
   * 开启定时
   */
  public void startTimerTask(T reference, long delay, long period) {
    if (mHandler == null) {
      mHandler = new MyHandler(reference);
    }
    if (mTimer == null) {
      mTimer = new Timer();
      mTask = new java.util.TimerTask() {
        @Override
        public void run() {
          if (mHandler != null) {
            mHandler.sendMessage(Message.obtain());
          }
        }
      };
      mIsRunning = true;
      mTimer.schedule(mTask, delay, period);
    }
  }

  /**
   * 取消定时
   */
  public void stopTimerTask() {
    if (mTimer != null) {
      mTimer.cancel();
      mTask.cancel();
      mTimer.purge();
      mIsRunning = false;
      mTask = null;
      mTimer = null;
    }
  }

  public boolean isRunning() {
    return mIsRunning;
  }

  public interface CallBack {

    void call();
  }

  private static class MyHandler<T extends CallBack> extends Handler {

    private WeakReference<T> reference;

    public MyHandler(T t) {
      reference = new WeakReference<>(t);
    }

    @Override
    public void handleMessage(Message msg) {
      if (reference.get() != null) {
        reference.get().call();
      }
    }

  }
}
