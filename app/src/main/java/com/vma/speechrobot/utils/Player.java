package com.vma.speechrobot.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.devbrackets.android.exomedia.AudioPlayer;
import com.devbrackets.android.exomedia.listener.OnBufferUpdateListener;
import com.example.common.utils.L;
import com.vma.speechrobot.R;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Player implements OnBufferUpdateListener {

  private static final String TAG = "Player";

  private AudioPlayer mMediaPlayer;    // 媒体播放器
  private SeekBar mSeekBar;            // 拖动条
  private TextView mTvProgress;
  private ImageView mIvControl;
  private Timer mTimer = new Timer();  // 计时器

  // 初始化播放器
  public Player(Context context, SeekBar seekBar, TextView textView, ImageView imageView) {
    super();
    mSeekBar = seekBar;
    mTvProgress = textView;
    mIvControl = imageView;
    mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
      public void onStopTrackingTouch(SeekBar seekBar) {
      }

      public void onStartTrackingTouch(SeekBar seekBar) {
      }

      public void onProgressChanged(SeekBar seekBar, int progress,
          boolean fromUser) {

        if (mMediaPlayer != null && fromUser) {
          L.d(TAG, "onProgressChanged: " + progress);
          mMediaPlayer.seekTo(progress * mMediaPlayer.getDuration() / seekBar.getMax());
          if (!mMediaPlayer.isPlaying()) {
            play();
          }
        }
      }
    });
    try {
      mMediaPlayer = new AudioPlayer(context);
      mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
      mMediaPlayer.setOnBufferUpdateListener(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // 每一秒触发一次
    mTimer.schedule(timerTask, 0, 1000);
  }

  // 计时器
  TimerTask timerTask = new TimerTask() {

    @Override
    public void run() {
      if (mMediaPlayer == null) {
        return;
      }
      if (mMediaPlayer.isPlaying() && !mSeekBar.isPressed()) {
        handler.sendEmptyMessage(0); // 发送消息
      }
    }
  };

  @SuppressLint("HandlerLeak")
  Handler handler = new Handler() {
    public void handleMessage(android.os.Message msg) {
      long position = mMediaPlayer.getCurrentPosition();
      long duration = mMediaPlayer.getDuration();
      if (duration > 0) {
        // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
        long pos = mSeekBar.getMax() * position / duration;
        mTvProgress.setText(String.format("%s/%s",
            String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(position),
                TimeUnit.MILLISECONDS.toSeconds(position) - TimeUnit.MINUTES
                    .toSeconds(TimeUnit.MILLISECONDS.toMinutes(position))),
            String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES
                    .toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)))));
        mSeekBar.setProgress((int) pos);
      }
    }
  };

  /**
   * @param url url地址
   */
  public void setUrl(String url) {
    mMediaPlayer.reset();
    mMediaPlayer.setDataSource(Uri.parse(url)); // 设置数据源
    mMediaPlayer.prepareAsync(); // prepare自动播放
  }

  public boolean isPlaying() {
    return mMediaPlayer.isPlaying();
  }

  public void play() {
    mMediaPlayer.start();
    mIvControl.setBackgroundResource(R.drawable.pause);
  }

  // 暂停
  public void pause() {
    mMediaPlayer.pause();
    mIvControl.setBackgroundResource(R.drawable.talk_3);
  }

  // 停止
  public void stop() {
    if (mMediaPlayer != null) {
      mMediaPlayer.stopPlayback();
      mMediaPlayer.release();
      mMediaPlayer = null;
    }
  }

  public void onDestroy() {
    stop();
    if (mTimer != null) {
      mTimer.cancel();
      timerTask.cancel();
      mTimer.purge();
      timerTask = null;
      mTimer = null;
    }
    handler = null;
  }

  /**
   * 缓冲更新
   */
  @Override
  public void onBufferingUpdate(int percent) {
    mSeekBar.setSecondaryProgress(percent);
//    long currentProgress = mSeekBar.getMax() * mMediaPlayer.getCurrentPosition() / mMediaPlayer.getDuration();
  }
}
