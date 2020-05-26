package com.vma.speechrobot.event;

public class MainMenuEvent {

  public static final int HOMEPAGE = 1;
  public static final int TASK_MANAGER = 2;

  public static final int TASK_CALL = 3;

  public Integer position;

  public MainMenuEvent() {
  }

  public MainMenuEvent(int position) {
    this.position = position;
  }
}
