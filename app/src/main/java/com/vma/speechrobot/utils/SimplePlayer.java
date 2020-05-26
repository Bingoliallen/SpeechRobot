package com.vma.speechrobot.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import java.io.IOException;

public class SimplePlayer {

  private MediaPlayer mMediaPlayer;    // 媒体播放器
  private String mUrl;

  public SimplePlayer() {
    mMediaPlayer = new MediaPlayer();
    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型
  }

  public void play(String url) {
    if (url.equals(mUrl) && mMediaPlayer.isPlaying()) {
      stop();
    } else {
      mMediaPlayer.reset();
      try {
        mMediaPlayer.setDataSource(url); // 设置数据源
        mUrl = url;
        mMediaPlayer.prepare(); // prepare自动播放
        mMediaPlayer.start();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void stop() {
    mMediaPlayer.stop();
  }

  public void onDestroy() {
    if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
      mMediaPlayer.stop();
      mMediaPlayer = null;
    }
  }
}
