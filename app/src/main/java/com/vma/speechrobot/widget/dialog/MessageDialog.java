package com.vma.speechrobot.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.utils.Spanny;
import com.example.common.utils.ViewUtil;
import com.example.common.widget.BaseDialog;
import com.vma.speechrobot.R;

/**
 * Created by Administrator on 2018/12/31.
 */

public class MessageDialog extends BaseDialog {

    private ImageView ivClose;
    private TextView tv_title,msg;

    private String text;
    private String title;
    public MessageDialog(Context context) {
        super(context, R.layout.dialog_message, ViewUtil.dp2px(context, 178),
                ViewUtil.dp2px(context, 120));
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void initContentView() {
        ivClose = getView(R.id.iv_close);
        tv_title = getView(R.id.tv_title);

        msg = getView(R.id.msg);
        tv_title.setText(title);
        msg.setText(text);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
