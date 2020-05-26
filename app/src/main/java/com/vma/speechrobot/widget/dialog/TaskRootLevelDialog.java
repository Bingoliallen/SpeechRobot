package com.vma.speechrobot.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.common.utils.ViewUtil;
import com.example.common.widget.BaseDialog;
import com.vma.speechrobot.R;

/**
 * Date: 2019/1/4
 * Author: libaibing
 * Email：
 * Des：
 */

public class TaskRootLevelDialog  extends BaseDialog {

    public interface TaskRootLevelDialogListener {
        void onClick(Integer transfer_key_num, Integer transfer_call_num, Integer transfer_call_time);
    }

    private TaskRootLevelDialogListener mTaskRootLevelDialogListener;

    public void setmTaskKHLevelDialogListener(TaskRootLevelDialogListener mTaskRootLevelDialogListener) {
        this.mTaskRootLevelDialogListener = mTaskRootLevelDialogListener;
    }

    private ImageView ivClose;
    private TextView tv_title, tv_cancel, tv_ok;
    private EditText edNum11, edNum12, edNum13;
    private ScrollView mScrollView;
;

    public TaskRootLevelDialog(Context context) {
        super(context, R.layout.dialog_root_level, ViewUtil.dp2px(context, 310),
                ViewUtil.dp2px(context, 120));
    }

    @Override
    public void initContentView() {

        edNum11 = getView(R.id.edNum11);
        edNum12 = getView(R.id.edNum12);
        edNum13 = getView(R.id.edNum13);


        edNum11.setSelection(edNum11.getText().length());
        edNum12.setSelection(edNum12.getText().length());
        edNum13.setSelection(edNum13.getText().length());



        tv_ok = getView(R.id.tv_ok);
        tv_cancel = getView(R.id.tv_cancel);
        ivClose = getView(R.id.iv_close);
        tv_title = getView(R.id.tv_title);
        mScrollView = getView(R.id.mScrollView);

        mScrollView.post(new Runnable() {
            @Override
            public void run() {
                mScrollView.scrollTo(0, 0);
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer transfer_key_num = null;
                Integer transfer_call_num = null;
                Integer transfer_call_time = null;
                transfer_key_num = Integer.valueOf(edNum11.getText().toString().trim());
                transfer_call_num = Integer.valueOf(edNum12.getText().toString().trim());
                transfer_call_time = Integer.valueOf(edNum13.getText().toString().trim());
                if (mTaskRootLevelDialogListener != null) {
                    mTaskRootLevelDialogListener.onClick( transfer_key_num,  transfer_call_num,  transfer_call_time);
                }
                dismiss();
            }
        });



    }


}


