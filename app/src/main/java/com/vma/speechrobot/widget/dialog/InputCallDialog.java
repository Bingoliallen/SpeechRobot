package com.vma.speechrobot.widget.dialog;

import android.content.Context;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.utils.ViewUtil;
import com.example.common.widget.BaseDialog;
import com.vma.speechrobot.R;

/**
 * Date: 2019/1/9
 * Author: libaibing
 * Email：
 * Des：
 */

public class InputCallDialog extends BaseDialog {

    public interface InputCallDialogListener{
        void onClick(Integer cust_id1,Integer cust_id2);
    }

    private InputCallDialogListener mInputCallDialogListener;

    public void setmInputCallDialogListener(InputCallDialogListener mInputCallDialogListener) {
        this.mInputCallDialogListener = mInputCallDialogListener;
    }

    private ImageView ivClose;
    private TextView tv_title,tv_ok,tv_cancel;
    private EditText tvCust1,tvCust2;

    private String cust_id2=null;
    private String cust_id1=null;

    public InputCallDialog(Context context) {
        super(context, R.layout.dialog_input_call, ViewUtil.dp2px(context, 210),
                ViewUtil.dp2px(context, 100));
    }


    @Override
    public void initContentView() {
        ivClose = getView(R.id.iv_close);
        tv_cancel = getView(R.id.tv_cancel);
        tv_ok = getView(R.id.tv_ok);
        tv_title = getView(R.id.tv_title);

        tvCust1 = getView(R.id.tvCust1);
        tvCust2 = getView(R.id.tvCust2);


        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cust_id1=tvCust1.getText().toString().trim();
                if(TextUtils.isEmpty(cust_id1)){
                    Toast.makeText(mContext,"请输入最小通话轮次",Toast.LENGTH_SHORT).show();
                    return;
                }
                cust_id2=tvCust2.getText().toString().trim();
                if(TextUtils.isEmpty(cust_id2)){
                    Toast.makeText(mContext,"请输入最大通话轮次",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.valueOf(cust_id1)>Integer.valueOf(cust_id2) ){
                    Toast.makeText(mContext,"最大通话轮次不小于最小通话轮次",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(mInputCallDialogListener!=null){
                    mInputCallDialogListener.onClick(Integer.valueOf(cust_id1),Integer.valueOf(cust_id2));
                }
                dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if(start_call_count!=null){
            tvCust1.setText(""+start_call_count);
            tvCust1.setSelection(tvCust1.getText().length());
        }
        if(end_call_count!=null){
            tvCust2.setText(""+end_call_count);
            tvCust2.setSelection(tvCust2.getText().length());
        }
    }

   private Integer start_call_count;
    private Integer end_call_count;

    public void setStart_call_count(Integer start_call_count) {
        this.start_call_count = start_call_count;
    }

    public void setEnd_call_count(Integer end_call_count) {
        this.end_call_count = end_call_count;
    }
}