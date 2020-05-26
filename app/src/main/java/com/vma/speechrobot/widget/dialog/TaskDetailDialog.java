package com.vma.speechrobot.widget.dialog;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.common.utils.Spanny;
import com.example.common.utils.ViewUtil;
import com.example.common.widget.BaseDialog;
import com.vma.speechrobot.R;

public class TaskDetailDialog extends BaseDialog {

  private ImageView ivClose;
  private TextView tvContent;
  private Spanny spanny;

  public TaskDetailDialog(Context context, Spanny spanny) {
    super(context, R.layout.dialog_task_detail, ViewUtil.dp2px(context, 325),
        ViewUtil.dp2px(context, 400));
    this.spanny = spanny;
  }

  @Override
  public void initContentView() {
    ivClose = getView(R.id.iv_close);
    tvContent = getView(R.id.tv_content);
    tvContent.setText(spanny);
    ivClose.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });
  }
}
