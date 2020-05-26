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

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Date: 2019/1/3
 * Author: libaibing
 * Email：
 * Des：
 */

public class AddTaskCallNumberDialog extends BaseDialog {

    public interface AddTaskCallNumberDialogListener {
        void onClick(String cust_id1, String cust_id2);
    }

    private AddTaskCallNumberDialogListener mAddTaskCallNumberDialogListener;

    public void setmAddTaskCallNumberDialogListener(AddTaskCallNumberDialogListener mAddTaskCallNumberDialogListener) {
        this.mAddTaskCallNumberDialogListener = mAddTaskCallNumberDialogListener;
    }

    private ImageView ivClose;
    private TextView tv_title, tv_ok, tv_cancel;
    private EditText tvCust1, tvCust2;

    private String cust_id2 = null;
    private String cust_id1 = null;

    public AddTaskCallNumberDialog(Context context) {
        super(context, R.layout.add_dialog_call_number, ViewUtil.dp2px(context, 210),
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
                cust_id1 = tvCust1.getText().toString().trim();
                if (!isPhoneNumber(cust_id1)) {
                    Toast.makeText(mContext, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                cust_id2 = tvCust2.getText().toString().trim();
                if (TextUtils.isEmpty(cust_id2)) {
                    cust_id2 = "客户" + cust_id1;
                }
                if (mAddTaskCallNumberDialogListener != null) {
                    mAddTaskCallNumberDialogListener.onClick(cust_id1, cust_id2);
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
    }

    /**
     * 判断手机号是否符合规范
     *
     * @param phoneNo 输入的手机号
     * @return
     */
    public static boolean isPhoneNumber(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }
        if (phoneNo.length() == 11) {
            for (int i = 0; i < 11; i++) {
                if (!PhoneNumberUtils.isISODigit(phoneNo.charAt(i))) {
                    return false;
                }
            }
            Pattern p = Pattern.compile("^((13[^4,\\D])" + "|(134[^9,\\D])" +
                    "|(14[5,7])" +
                    "|(15[^4,\\D])" +
                    "|(17[3,6-8])" +
                    "|(18[0-9]))\\d{8}$");
            Matcher m = p.matcher(phoneNo);
            return m.matches();
        }
        return false;
    }


    /**
     * 正则判断手机格式
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        String sPhone;
        if (isENum(mobiles)) {
             sPhone = ds.format(Double.parseDouble(mobiles)).trim();
        }else{
            sPhone =mobiles;
        }

        String telRegex = "13\\d{9}|14[57]\\d{8}|15[012356789]\\d{8}|18[01256789]\\d{8}|17[0678]\\d{8}";
        if (TextUtils.isEmpty(sPhone)) return false;
        else return sPhone.matches(telRegex);
    }


    static Pattern pattern = Pattern.compile("(-?\\d+\\.?\\d*)[Ee]{1}[\\+-]?[0-9]*");
    static DecimalFormat ds = new DecimalFormat("0");
    public static boolean isENum(String input) {//判断输入字符串是否为科学计数法
        return pattern.matcher(input).matches();
    }

    public static String getENum(String  mobiles) {
        if (isENum(mobiles)) {
           return ds.format(Double.parseDouble(mobiles)).trim();
        }
        return mobiles;
    }

}
