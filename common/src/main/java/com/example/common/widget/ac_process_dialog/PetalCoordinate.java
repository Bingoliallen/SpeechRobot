package com.example.common.widget.ac_process_dialog;

/**
 * coordinate to draw a petal in flower
 */
public class PetalCoordinate {

  private int mStartX, mStartY, mEndX, mEndY;

  public PetalCoordinate(int starX, int startY, int endX, int endY) {
    this.mStartX = starX;
    this.mStartY = startY;
    this.mEndX = endX;
    this.mEndY = endY;
  }

  public int getStartX() {
    return mStartX;
  }

  public int getStartY() {
    return mStartY;
  }

  public int getEndX() {
    return mEndX;
  }

  public int getEndY() {
    return mEndY;
  }
}
