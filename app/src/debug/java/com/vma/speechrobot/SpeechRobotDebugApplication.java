package com.vma.speechrobot;

import com.example.common.CommonDebugApplication;
import org.greenrobot.eventbus.EventBus;

public class SpeechRobotDebugApplication extends CommonDebugApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    EventBus.builder().addIndex(new MyEventBusIndex()).installDefaultEventBus();
  }
}
