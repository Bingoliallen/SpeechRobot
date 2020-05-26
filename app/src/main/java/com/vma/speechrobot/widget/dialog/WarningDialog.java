package com.vma.speechrobot.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.ViewUtil;
import com.example.common.widget.BaseDialog;
import com.vma.speechrobot.R;

/**
 * Created by Administrator on 2019/1/5.
 */

public class WarningDialog extends BaseDialog {

    public interface WarningDialogListener{
       void onClick();
    }
    private WarningDialogListener mWarningDialogListener;

    public void setmWarningDialogListener(WarningDialogListener mWarningDialogListener) {
        this.mWarningDialogListener = mWarningDialogListener;
    }

    private TextView tv_title,msg,tv_cancel,tv_ok;

    private String text;
    private String title;
    public WarningDialog(Context context) {
        super(context, R.layout.dialog_warning, ViewUtil.dp2px(context, 128),
                ViewUtil.dp2px(context, 70));
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void initContentView() {

        tv_title = getView(R.id.tv_title);
        tv_ok = getView(R.id.tv_ok);
        tv_cancel = getView(R.id.tv_cancel);
        msg = getView(R.id.msg);

        tv_title.setText(title);
        msg.setText(text);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mWarningDialogListener!=null){
                    mWarningDialogListener.onClick();
                }
                dismiss();
            }
        });
    }


}
