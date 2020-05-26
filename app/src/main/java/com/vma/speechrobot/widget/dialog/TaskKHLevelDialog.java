package com.vma.speechrobot.widget.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.common.utils.ViewUtil;
import com.example.common.widget.BaseDialog;
import com.vma.speechrobot.R;
import com.vma.speechrobot.utils.MyLayoutAnimationHelper;

/**
 * Date: 2018/12/25
 * Author: libaibing
 * Email：
 * Des：
 */

public class TaskKHLevelDialog extends BaseDialog {

    public interface TaskKHLevelDialogListener {
        void onClick(Integer valid_key_num_a, Integer call_num_a, Integer call_time_a, Integer is_default_user_level_a,
                     Integer valid_key_num_b, Integer call_num_b, Integer call_time_b, Integer is_default_user_level_b,
                     Integer valid_key_num_c, Integer call_num_c, Integer call_time_c, Integer is_default_user_level_c);
    }

    private TaskKHLevelDialogListener mTaskKHLevelDialogListener;

    public void setmTaskKHLevelDialogListener(TaskKHLevelDialogListener mTaskKHLevelDialogListener) {
        this.mTaskKHLevelDialogListener = mTaskKHLevelDialogListener;
    }

    private ImageView ivClose;
    private TextView tv_title, tv_cancel, tv_ok;

    private TextView tvSwitch1;
    private TextView tvSwitch2;
    private TextView tvSwitch3;

    private EditText edNum11, edNum12, edNum13;
    private EditText edNum21, edNum22, edNum23;
    private EditText edNum31, edNum32, edNum33;


    private ScrollView mScrollView;
    private CheckBox box1;
    private CheckBox box2;
    private CheckBox box3;

/*    private  int edNum11, edNum21, edNum31;
    private  int edNum12, edNum22, edNum32;
    private  int edNum13, edNum23, edNum33;*/

    private boolean isA = true;
    private boolean isB = false;
    private boolean isC = false;

    private boolean isCheck1 = false;
    private boolean isCheck2 = false;
    private boolean isCheck3 = false;

    public TaskKHLevelDialog(Context context) {
        super(context, R.layout.dialog_kh_level, ViewUtil.dp2px(context, 310),
                ViewUtil.dp2px(context, 180));

    }

    @Override
    public void initContentView() {

        viewGroup1 = (LinearLayout) getView(R.id.root_view1);
        viewGroup2 = (LinearLayout) getView(R.id.root_view2);
        viewGroup3 = (LinearLayout) getView(R.id.root_view3);
        viewGroup1.setVisibility(View.VISIBLE);
        viewGroup2.setVisibility(View.GONE);
        viewGroup3.setVisibility(View.GONE);

        edNum11 = getView(R.id.edNum11);
        edNum12 = getView(R.id.edNum12);
        edNum13 = getView(R.id.edNum13);

        edNum21 = getView(R.id.edNum21);
        edNum22 = getView(R.id.edNum22);
        edNum23 = getView(R.id.edNum23);

        edNum31 = getView(R.id.edNum31);
        edNum32 = getView(R.id.edNum32);
        edNum33 = getView(R.id.edNum33);



        tv_ok = getView(R.id.tv_ok);
        tv_cancel = getView(R.id.tv_cancel);

        ivClose = getView(R.id.iv_close);
        tv_title = getView(R.id.tv_title);
        tvSwitch1 = getView(R.id.tvSwitch1);
        tvSwitch2 = getView(R.id.tvSwitch2);
        tvSwitch3 = getView(R.id.tvSwitch3);


        box1 = getView(R.id.box1);
        box2 = getView(R.id.box2);
        box3 = getView(R.id.box3);

        box1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCheck1 = isChecked;
            }
        });
        box2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCheck2 = isChecked;
            }
        });
        box3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCheck3 = isChecked;
            }
        });


        tvSwitch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isA) {
                    isA = true;
                    tvSwitch1.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvSwitch1.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape_p));
                    isB = false;
                    tvSwitch2.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch2.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape1));
                    isC = false;
                    tvSwitch3.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch3.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape2));

                    viewGroup1.setVisibility(View.VISIBLE);
                    viewGroup2.setVisibility(View.GONE);
                    viewGroup3.setVisibility(View.GONE);
                    viewGroup1.setLayoutAnimation(controller);
                    viewGroup1.scheduleLayoutAnimation();
                }

            }
        });

        tvSwitch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isB) {
                    isA = false;
                    tvSwitch1.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch1.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape));
                    isB = true;
                    tvSwitch2.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvSwitch2.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape1_p));
                    isC = false;
                    tvSwitch3.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch3.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape2));

                    viewGroup2.setVisibility(View.VISIBLE);
                    viewGroup1.setVisibility(View.GONE);
                    viewGroup3.setVisibility(View.GONE);
                    viewGroup2.setLayoutAnimation(controller);
                    viewGroup2.scheduleLayoutAnimation();
                }

            }
        });
        tvSwitch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isC) {
                    isA = false;
                    tvSwitch1.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch1.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape));
                    isB = false;
                    tvSwitch2.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch2.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape1));
                    isC = true;
                    tvSwitch3.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvSwitch3.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape2_p));

                    viewGroup3.setVisibility(View.VISIBLE);
                    viewGroup1.setVisibility(View.GONE);
                    viewGroup2.setVisibility(View.GONE);
                    viewGroup3.setLayoutAnimation(controller);
                    viewGroup3.scheduleLayoutAnimation();
                }

            }
        });


        box1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCheck1 = isChecked;
            }
        });

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

                valid_key_num_a = Integer.valueOf(edNum11.getText().toString().trim());
                call_num_a = Integer.valueOf(edNum12.getText().toString().trim());
                call_time_a = Integer.valueOf(edNum13.getText().toString().trim());



                valid_key_num_b = Integer.valueOf(edNum21.getText().toString().trim());
                call_num_b = Integer.valueOf(edNum22.getText().toString().trim());
                call_time_b = Integer.valueOf(edNum23.getText().toString().trim());



                valid_key_num_c = Integer.valueOf(edNum31.getText().toString().trim());
                call_num_c = Integer.valueOf(edNum32.getText().toString().trim());
                call_time_c = Integer.valueOf(edNum33.getText().toString().trim());


                if (isCheck1) {
                    is_default_user_level_a = 1;
                } else {
                    is_default_user_level_a = 0;
                }

                if (isCheck2) {
                    is_default_user_level_b = 1;
                } else {
                    is_default_user_level_b = 0;
                }

                if (isCheck3) {
                    is_default_user_level_c = 1;
                } else {
                    is_default_user_level_c = 0;
                }
                if (mTaskKHLevelDialogListener != null) {
                    mTaskKHLevelDialogListener.onClick( valid_key_num_a,  call_num_a,  call_time_a,  is_default_user_level_a,
                             valid_key_num_b,  call_num_b,  call_time_b,  is_default_user_level_b,
                             valid_key_num_c,  call_num_c,  call_time_c,  is_default_user_level_c);
                }
                dismiss();
            }
        });

        if(call_num_a!=null &&call_num_a!=0){
            edNum12.setText(""+call_num_a);
        }
        if(call_num_b!=null && call_num_b!=0){
            edNum22.setText(""+call_num_b);
        }
        if(call_num_c!=null && call_num_c!=0){
            edNum32.setText(""+call_num_c);
        }

        if(call_time_a!=null && call_time_a!=0){
            edNum13.setText(""+call_time_a);
        }
        if(call_time_b!=null && call_time_b!=0){
            edNum23.setText(""+call_time_b);
        }
        if(call_time_c!=null && call_time_c!=0){
            edNum33.setText(""+call_time_c);
        }

        if(valid_key_num_a!=null && valid_key_num_a!=0){
            edNum11.setText(""+valid_key_num_a);
        }
        if(valid_key_num_b!=null && valid_key_num_b!=0){
            edNum21.setText(""+valid_key_num_b);
        }
        if(valid_key_num_c!=null && valid_key_num_c!=0){
            edNum31.setText(""+valid_key_num_c);
        }

        if(is_default_user_level_a!=null && is_default_user_level_a==1){
            isCheck1=true;
            box1.setChecked(isCheck1);
        }

        if(is_default_user_level_b!=null && is_default_user_level_b==1){
            isCheck2=true;
            box2.setChecked(isCheck2);
        }
        if(is_default_user_level_c!=null && is_default_user_level_c==1){
            isCheck3=true;
            box3.setChecked(isCheck3);
        }
        edNum11.setSelection(edNum11.getText().length());
        edNum12.setSelection(edNum12.getText().length());
        edNum13.setSelection(edNum13.getText().length());

        edNum21.setSelection(edNum21.getText().length());
        edNum22.setSelection(edNum22.getText().length());
        edNum23.setSelection(edNum23.getText().length());

        edNum31.setSelection(edNum31.getText().length());
        edNum32.setSelection(edNum32.getText().length());
        edNum33.setSelection(edNum33.getText().length());

        viewGroup1.setLayoutAnimation(controller);
        viewGroup1.scheduleLayoutAnimation();


    }
    Integer valid_key_num_a = null;
    Integer call_num_a = null;
    Integer call_time_a = null;
    Integer is_default_user_level_a = null;

    Integer valid_key_num_b = null;
    Integer call_num_b = null;
    Integer call_time_b = null;
    Integer is_default_user_level_b = null;


    Integer valid_key_num_c = null;
    Integer call_num_c = null;
    Integer call_time_c = null;
    Integer is_default_user_level_c = null;

    public void setValid_key_num_a(Integer valid_key_num_a) {
        this.valid_key_num_a = valid_key_num_a;
    }

    public void setCall_num_a(Integer call_num_a) {
        this.call_num_a = call_num_a;
    }

    public void setCall_time_a(Integer call_time_a) {
        this.call_time_a = call_time_a;
    }

    public void setIs_default_user_level_a(Integer is_default_user_level_a) {
        this.is_default_user_level_a = is_default_user_level_a;
    }

    public void setValid_key_num_b(Integer valid_key_num_b) {
        this.valid_key_num_b = valid_key_num_b;
    }

    public void setCall_num_b(Integer call_num_b) {
        this.call_num_b = call_num_b;
    }

    public void setCall_time_b(Integer call_time_b) {
        this.call_time_b = call_time_b;
    }

    public void setIs_default_user_level_b(Integer is_default_user_level_b) {
        this.is_default_user_level_b = is_default_user_level_b;
    }

    public void setValid_key_num_c(Integer valid_key_num_c) {
        this.valid_key_num_c = valid_key_num_c;
    }

    public void setCall_num_c(Integer call_num_c) {
        this.call_num_c = call_num_c;
    }

    public void setCall_time_c(Integer call_time_c) {
        this.call_time_c = call_time_c;
    }

    public void setIs_default_user_level_c(Integer is_default_user_level_c) {
        this.is_default_user_level_c = is_default_user_level_c;
    }

    LayoutAnimationController controller = MyLayoutAnimationHelper.makeLayoutAnimationController();
    LinearLayout viewGroup1;
    LinearLayout viewGroup2;
    LinearLayout viewGroup3;
}

