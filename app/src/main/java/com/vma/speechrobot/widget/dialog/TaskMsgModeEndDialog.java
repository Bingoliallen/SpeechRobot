package com.vma.speechrobot.widget.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LayoutAnimationController;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.divider.GridSpacingItemDecoration;
import com.example.common.adapter.recycler.layout_manager.CustomGridLayoutManager;
import com.example.common.utils.ViewUtil;
import com.example.common.widget.BaseDialog;
import com.vma.speechrobot.R;
import com.vma.speechrobot.adapter.TaskMsgModeEndAdapter;
import com.vma.speechrobot.bean.MessageTemplate;
import com.vma.speechrobot.utils.MyLayoutAnimationHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2018/12/25
 * Author: libaibing
 * Email：
 * Des：
 */

public class TaskMsgModeEndDialog extends BaseDialog {

    public interface TaskMsgModeEndDialogListener{
        void onClick(Integer message_template_a,Integer is_default_message_template_a,
                     Integer message_template_b,Integer is_default_message_template_b,
                     Integer message_template_c,Integer is_default_message_template_c);
    }

    private TaskMsgModeEndDialogListener mTaskMsgModeEndDialogListener;
    private ImageView ivMR1;
    private ImageView ivMR2;
    private ImageView ivMR3;
    private ImageView ivClose;
    private TextView tv_title,tv_cancel,tv_ok;

    private TextView tvSwitch10;
    private TextView tvSwitch20;
    private TextView tvSwitch30;
    private ScrollView mScrollView;
    private CheckBox box1;
    private CheckBox box2;
    private CheckBox box3;
    private TaskMsgModeEndAdapter mTaskMsgModeEndAdapter1;
    private TaskMsgModeEndAdapter mTaskMsgModeEndAdapter2;
    private TaskMsgModeEndAdapter mTaskMsgModeEndAdapter3;
    private RecyclerView recycler_view1;
    private RecyclerView recycler_view2;
    private RecyclerView recycler_view3;
    private List<MessageTemplate> mCustomer1=new ArrayList<>();
    private List<MessageTemplate> mCustomer2=new ArrayList<>();
    private List<MessageTemplate> mCustomer3=new ArrayList<>();
    private boolean isA=true;
    private boolean isB=false;
    private boolean isC=false;

    private boolean isCheckA=false;
    private boolean isCheckB=false;
    private boolean isCheckC=false;

    Integer message_template_a=null;
    Integer message_template_b=null;
    Integer message_template_c=null;
    public TaskMsgModeEndDialog(Context context) {
        super(context, R.layout.dialog_msg_mode_end, ViewUtil.dp2px(context, 278),
                ViewUtil.dp2px(context, 180));

    }

    public void setmCustomer(List<MessageTemplate> mCustomer) {
        this.mCustomer1.clear();
        for(MessageTemplate bean:mCustomer){
            this.mCustomer1.add( new MessageTemplate(bean.id,bean.is_all,
                    bean.module,bean.name,bean.type,bean.isSelect));
        }

        this.mCustomer2.clear();
        for(MessageTemplate bean:mCustomer){
            this.mCustomer2.add( new MessageTemplate(bean.id,bean.is_all,
                    bean.module,bean.name,bean.type,bean.isSelect));
        }

        this.mCustomer3.clear();
        for(MessageTemplate bean:mCustomer){
            this.mCustomer3.add( new MessageTemplate(bean.id,bean.is_all,
                    bean.module,bean.name,bean.type,bean.isSelect));
        }
    }

    public void setmTaskMsgModeEndDialogListener(TaskMsgModeEndDialogListener mTaskMsgModeEndDialogListener) {
        this.mTaskMsgModeEndDialogListener = mTaskMsgModeEndDialogListener;
    }

    @Override
    public void initContentView() {

        ivMR1 = getView(R.id.ivMR1);
        ivMR2 = getView(R.id.ivMR2);
        ivMR3 = getView(R.id.ivMR3);
        ivMR1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialog mMessageDialog = new MessageDialog(mContext);
                mMessageDialog.setTitle("默认设置");
                mMessageDialog.setText("设置为默认后，以后每次创建任务将会推送给对应的销售人员，不需要重复设置。");
                mMessageDialog.show();
            }
        });
        ivMR2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialog mMessageDialog = new MessageDialog(mContext);
                mMessageDialog.setTitle("默认设置");
                mMessageDialog.setText("设置为默认后，以后每次创建任务将会推送给对应的销售人员，不需要重复设置。");
                mMessageDialog.show();
            }
        });
        ivMR3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialog mMessageDialog = new MessageDialog(mContext);
                mMessageDialog.setTitle("默认设置");
                mMessageDialog.setText("设置为默认后，以后每次创建任务将会推送给对应的销售人员，不需要重复设置。");
                mMessageDialog.show();
            }
        });

        viewGroup1 = (LinearLayout) getView(R.id.root_view1);
        viewGroup2 = (LinearLayout) getView(R.id.root_view2);
        viewGroup3 = (LinearLayout) getView(R.id.root_view3);
        viewGroup1.setVisibility(View.VISIBLE);
        viewGroup2.setVisibility(View.GONE);
        viewGroup3.setVisibility(View.GONE);

        ivClose = getView(R.id.iv_close);
        tv_title = getView(R.id.tv_title);
        tvSwitch10 = getView(R.id.tvSwitch10);
        tvSwitch20 = getView(R.id.tvSwitch20);
        tvSwitch30 = getView(R.id.tvSwitch30);
        tv_cancel = getView(R.id.tv_cancel);
        tv_ok = getView(R.id.tv_ok);

        tvSwitch10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isA){
                    isA=true;
                    tvSwitch10.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvSwitch10.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape_p));
                    isB=false;
                    tvSwitch20.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch20.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape1));
                    isC=false;
                    tvSwitch30.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch30.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape2));

                    viewGroup1.setVisibility(View.VISIBLE);
                    viewGroup2.setVisibility(View.GONE);
                    viewGroup3.setVisibility(View.GONE);
                    viewGroup1.setLayoutAnimation(controller);
                    viewGroup1.scheduleLayoutAnimation();
                }

            }
        });

        tvSwitch20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isB){
                    isA=false;
                    tvSwitch10.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch10.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape));
                    isB=true;
                    tvSwitch20.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvSwitch20.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape1_p));
                    isC=false;
                    tvSwitch30.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch30.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape2));

                    viewGroup1.setVisibility(View.GONE);
                    viewGroup2.setVisibility(View.VISIBLE);
                    viewGroup3.setVisibility(View.GONE);
                    viewGroup2.setLayoutAnimation(controller);
                    viewGroup2.scheduleLayoutAnimation();
                }

            }
        });
        tvSwitch30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isC){
                    isA=false;
                    tvSwitch10.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch10.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape));
                    isB=false;
                    tvSwitch20.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch20.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape1));
                    isC=true;
                    tvSwitch30.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvSwitch30.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape2_p));

                    viewGroup1.setVisibility(View.GONE);
                    viewGroup2.setVisibility(View.GONE);
                    viewGroup3.setVisibility(View.VISIBLE);
                    viewGroup3.setLayoutAnimation(controller);
                    viewGroup3.scheduleLayoutAnimation();
                }

            }
        });


        box1 = getView(R.id.box1);
        box2 = getView(R.id.box2);
        box3 = getView(R.id.box3);
        box1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCheckA=isChecked;
            }
        });

        box2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCheckB=isChecked;
            }
        });


        box3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCheckC=isChecked;
            }
        });
        mScrollView = getView(R.id.mScrollView);

        recycler_view1 = getView(R.id.recycler_view1);
        recycler_view2 = getView(R.id.recycler_view2);
        recycler_view3 = getView(R.id.recycler_view3);

        mTaskMsgModeEndAdapter1 = new TaskMsgModeEndAdapter(mContext);
        mTaskMsgModeEndAdapter1.setOnRecyclerItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<MessageTemplate>() {
            @Override
            public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, MessageTemplate data, int position) {
                boolean is=data.isSelect;
                message_template_a=null;
                for(MessageTemplate d :mCustomer1){
                    d.isSelect=false;
                }
                mCustomer1.get(position).isSelect=!is;
                if( mCustomer1.get(position).isSelect){
                    message_template_a=data.id;
                }
                mTaskMsgModeEndAdapter1.set(mCustomer1);
            }
        });
        CustomGridLayoutManager layoutManagerTime = new CustomGridLayoutManager(mContext, 5);
        layoutManagerTime.setScrollEnabled(true);
        recycler_view1.setLayoutManager(layoutManagerTime);
        recycler_view1.addItemDecoration(
                new GridSpacingItemDecoration(5, ViewUtil.dp2px(mContext, 6), ViewUtil.dp2px(mContext, 6),
                        true));
        recycler_view1.setAdapter(mTaskMsgModeEndAdapter1);

        if(message_template_a!=null && message_template_a!=0){
            for(MessageTemplate d :mCustomer1){
                if(d.id==message_template_a){
                    d.isSelect=true;
                }else{
                    d.isSelect=false;
                }

            }
        }

        mTaskMsgModeEndAdapter1.set(mCustomer1);



        mTaskMsgModeEndAdapter2 = new TaskMsgModeEndAdapter(mContext);
        mTaskMsgModeEndAdapter2.setOnRecyclerItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<MessageTemplate>() {
            @Override
            public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, MessageTemplate data, int position) {
                boolean is=data.isSelect;
                message_template_b=null;
                for(MessageTemplate d :mCustomer2){
                    d.isSelect=false;
                }
                mCustomer2.get(position).isSelect=!is;
                if(mCustomer2.get(position).isSelect){
                    message_template_b=data.id;
                }
                mTaskMsgModeEndAdapter2.set(mCustomer2);
            }
        });
        CustomGridLayoutManager layoutManagerTime2 = new CustomGridLayoutManager(mContext, 5);
        layoutManagerTime2.setScrollEnabled(true);
        recycler_view2.setLayoutManager(layoutManagerTime2);
        recycler_view2.addItemDecoration(
                new GridSpacingItemDecoration(5, ViewUtil.dp2px(mContext, 6), ViewUtil.dp2px(mContext, 6),
                        true));
        recycler_view2.setAdapter(mTaskMsgModeEndAdapter2);

        if(message_template_b!=null && message_template_b!=0){
            for(MessageTemplate d :mCustomer2){
                if(d.id==message_template_b){
                    d.isSelect=true;
                }else{
                    d.isSelect=false;
                }

            }
        }

        mTaskMsgModeEndAdapter2.set(mCustomer2);



        mTaskMsgModeEndAdapter3 = new TaskMsgModeEndAdapter(mContext);
        mTaskMsgModeEndAdapter3.setOnRecyclerItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<MessageTemplate>() {
            @Override
            public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, MessageTemplate data, int position) {
                boolean is=data.isSelect;
                message_template_c=null;
                for(MessageTemplate d :mCustomer3){
                    d.isSelect=false;
                }
                mCustomer3.get(position).isSelect=!is;
                if(mCustomer3.get(position).isSelect){
                    message_template_c=data.id;
                }
                mTaskMsgModeEndAdapter3.set(mCustomer3);
            }
        });
        CustomGridLayoutManager layoutManagerTime3 = new CustomGridLayoutManager(mContext, 5);
        layoutManagerTime3.setScrollEnabled(true);
        recycler_view3.setLayoutManager(layoutManagerTime3);
        recycler_view3.addItemDecoration(
                new GridSpacingItemDecoration(5, ViewUtil.dp2px(mContext, 6), ViewUtil.dp2px(mContext, 6),
                        true));
        recycler_view3.setAdapter(mTaskMsgModeEndAdapter3);

        if(message_template_c!=null && message_template_c!=0){
            for(MessageTemplate d :mCustomer3){
                if(d.id==message_template_c){
                    d.isSelect=true;
                }else{
                    d.isSelect=false;
                }

            }
        }
        mTaskMsgModeEndAdapter3.set(mCustomer3);



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


                if(isCheckA){
                    is_default_message_template_a=1;
                }else{
                    is_default_message_template_a=0;
                }

                if(isCheckB){
                    is_default_message_template_b=1;
                }else{
                    is_default_message_template_b=0;
                }

                if(isCheckC){
                    is_default_message_template_c=1;
                }else{
                    is_default_message_template_c=0;
                }

                if (mTaskMsgModeEndDialogListener!=null) {
                    mTaskMsgModeEndDialogListener.onClick(message_template_a,is_default_message_template_a,
                            message_template_b,is_default_message_template_b,
                            message_template_c,is_default_message_template_c);
                }
                dismiss();
            }
        });


        if(is_default_message_template_a!=null &&is_default_message_template_a ==1){
            isCheckA=true;
            box1.setChecked(isCheckA);
        }

        if(is_default_message_template_b!=null&&is_default_message_template_b ==1){
            isCheckB=true;
            box2.setChecked(isCheckB);
        }


        if(is_default_message_template_c!=null&&is_default_message_template_c ==1){
            isCheckC=true;
            box3.setChecked(isCheckC);
        }

        viewGroup1.setLayoutAnimation(controller);
        viewGroup1.scheduleLayoutAnimation();



    }
    Integer is_default_message_template_a = null;
    Integer is_default_message_template_b = null;
    Integer is_default_message_template_c = null;
    LayoutAnimationController controller = MyLayoutAnimationHelper.makeLayoutAnimationController();
    LinearLayout viewGroup1;
    LinearLayout viewGroup2;
    LinearLayout viewGroup3;

    public void setMessage_template_a(Integer message_template_a) {
        this.message_template_a = message_template_a;
    }

    public void setMessage_template_b(Integer message_template_b) {
        this.message_template_b = message_template_b;
    }

    public void setMessage_template_c(Integer message_template_c) {
        this.message_template_c = message_template_c;
    }

    public void setIs_default_message_template_a(Integer is_default_message_template_a) {
        this.is_default_message_template_a = is_default_message_template_a;
    }

    public void setIs_default_message_template_b(Integer is_default_message_template_b) {
        this.is_default_message_template_b = is_default_message_template_b;
    }

    public void setIs_default_message_template_c(Integer is_default_message_template_c) {
        this.is_default_message_template_c = is_default_message_template_c;
    }
}

