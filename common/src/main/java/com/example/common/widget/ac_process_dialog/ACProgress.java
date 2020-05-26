package com.example.common.widget.ac_process_dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import com.example.common.R;

public abstract class ACProgress extends Dialog {

  protected ACProgress(Context context) {
    this(context, R.style.ACPLDialog);
  }

  protected ACProgress(Context context, int theme) {
    super(context, theme);
    getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
  }

  protected int getMinimumSideOfScreen(Context context) {
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = windowManager.getDefaultDisplay();
    if (Build.VERSION.SDK_INT >= 13) {
      Point size = new Point();
      display.getSize(size);
      return Math.min(size.x, size.y);
    } else {
      return Math.min(display.getWidth(), display.getHeight());
    }
  }

}
