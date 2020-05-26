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
import com.vma.speechrobot.adapter.TaskWcPushAdapter;
import com.vma.speechrobot.bean.Employee;
import com.vma.speechrobot.utils.MyLayoutAnimationHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019/1/2
 * Author: libaibing
 * Email：
 * Des：
 */

public class TaskWcPushErrorDialog  extends BaseDialog {
    public interface TaskWcPushErrorDialogListener{
        void onClick(List<String> wx_exception_list,Integer is_default_task_exception,
                     List<String> wx_complete_list,Integer is_default_task_complete);
    }

    private  TaskWcPushErrorDialogListener mTaskWcPushErrorDialogListener;

    private ImageView ivMR1;
    private ImageView ivMR2;

    private ImageView ivClose;
    private TextView tv_title,tv_cancel,tv_ok;

    private TextView tvSwitch1;
    private TextView tvSwitch2;

    private ScrollView mScrollView;
    private CheckBox box1;
    private CheckBox box2;
    private RecyclerView recycler_view1;
    private RecyclerView recycler_view2;
    private TaskWcPushAdapter mTaskWcPushAdapter1;
    private TaskWcPushAdapter mTaskWcPushAdapter2;
    private List<Employee> call_time1=new ArrayList<>();
    private List<Employee> call_time2=new ArrayList<>();
    private boolean isA=true;
    private boolean isB=false;

    private boolean isCheckA=false;
    private boolean isCheckB=false;
    public TaskWcPushErrorDialog(Context context) {
        super(context, R.layout.dialog_wc_push_error, ViewUtil.dp2px(context, 300),
                ViewUtil.dp2px(context, 180));

    }

    public void setCall_time(List<Employee> call_time) {
        this.call_time1.clear();
        for(Employee bean:call_time){
            this.call_time1.add(new Employee(bean.id,bean.employee_name,bean.isSelect,bean.wx_user_id) );
        }

        this.call_time2.clear();
        for(Employee bean:call_time){
            this.call_time2.add(new Employee(bean.id,bean.employee_name,bean.isSelect,bean.wx_user_id) );
        }

    }

    public void setmTaskWcPushErrorDialogListener(TaskWcPushErrorDialogListener mTaskWcPushErrorDialogListener) {
        this.mTaskWcPushErrorDialogListener = mTaskWcPushErrorDialogListener;
    }

    @Override
    public void initContentView() {

        ivMR1 = getView(R.id.ivMR1);
        ivMR2 = getView(R.id.ivMR2);
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

        viewGroup1 = (LinearLayout) getView(R.id.root_view1);
        viewGroup2 = (LinearLayout) getView(R.id.root_view2);
        viewGroup1.setVisibility(View.VISIBLE);
        viewGroup2.setVisibility(View.GONE);

        ivClose = getView(R.id.iv_close);
        tv_title = getView(R.id.tv_title);
        tvSwitch1 = getView(R.id.tvSwitch1);
        tvSwitch2 = getView(R.id.tvSwitch2);
        tv_cancel = getView(R.id.tv_cancel);
        tv_ok = getView(R.id.tv_ok);

        tvSwitch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isA){
                    isA=true;
                    tvSwitch1.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvSwitch1.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape_p));

                    isB=false;
                    tvSwitch2.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch2.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape2));

                    viewGroup1.setVisibility(View.VISIBLE);
                    viewGroup2.setVisibility(View.GONE);
                    viewGroup1.setLayoutAnimation(controller);
                    viewGroup1.scheduleLayoutAnimation();
                }

            }
        });

        tvSwitch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isB){
                    isA=false;
                    tvSwitch1.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch1.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape));
                    isB=true;
                    tvSwitch2.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvSwitch2.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape2_p));

                    viewGroup1.setVisibility(View.GONE);
                    viewGroup2.setVisibility(View.VISIBLE);
                    viewGroup2.setLayoutAnimation(controller);
                    viewGroup2.scheduleLayoutAnimation();
                }

            }
        });


        box1 = getView(R.id.box1);
        box2 = getView(R.id.box2);
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
        recycler_view1 = getView(R.id.recycler_view1);
        recycler_view2 = getView(R.id.recycler_view2);
        mTaskWcPushAdapter1 = new TaskWcPushAdapter(mContext);
        mTaskWcPushAdapter1.setOnRecyclerItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<Employee>() {
            @Override
            public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, Employee data, int position) {

                mTaskWcPushAdapter1.getData().get(position).isSelect=!mTaskWcPushAdapter1.getData().get(position).isSelect;
                mTaskWcPushAdapter1.notifyDataSetChanged();

            }
        });
        CustomGridLayoutManager layoutManagerTime = new CustomGridLayoutManager(mContext, 5);
        layoutManagerTime.setScrollEnabled(true);
        recycler_view1.setLayoutManager(layoutManagerTime);
        recycler_view1.addItemDecoration(
                new GridSpacingItemDecoration(5, ViewUtil.dp2px(mContext, 0), ViewUtil.dp2px(mContext, 6),
                        true));
        recycler_view1.setAdapter(mTaskWcPushAdapter1);

        if(wx_exception_list!=null && wx_exception_list.size()>0){
            for(String bean:wx_exception_list){
                for(Employee mEmployee:call_time1){
                    if(bean.equals(""+mEmployee.wx_user_id)){
                        mEmployee.isSelect=true;
                    }
                }

            }
        }

        mTaskWcPushAdapter1.set(call_time1);


        mTaskWcPushAdapter2 = new TaskWcPushAdapter(mContext);
        mTaskWcPushAdapter2.setOnRecyclerItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<Employee>() {
            @Override
            public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, Employee data, int position) {

                mTaskWcPushAdapter2.getData().get(position).isSelect=!mTaskWcPushAdapter2.getData().get(position).isSelect;
                mTaskWcPushAdapter2.notifyDataSetChanged();

            }
        });
        CustomGridLayoutManager layoutManagerTime2 = new CustomGridLayoutManager(mContext, 5);
        layoutManagerTime2.setScrollEnabled(true);
        recycler_view2.setLayoutManager(layoutManagerTime2);
        recycler_view2.addItemDecoration(
                new GridSpacingItemDecoration(5, ViewUtil.dp2px(mContext, 0), ViewUtil.dp2px(mContext, 6),
                        true));
        recycler_view2.setAdapter(mTaskWcPushAdapter2);

        if(wx_complete_list!=null && wx_complete_list.size()>0){
            for(String bean:wx_complete_list){
                for(Employee mEmployee:call_time2){
                    if(bean.equals(""+mEmployee.wx_user_id)){
                        mEmployee.isSelect=true;
                    }
                }

            }
        }

        mTaskWcPushAdapter2.set(call_time2);


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
                wx_exception_list.clear();
                for(Employee mEmployee:call_time1){
                    if(mEmployee.isSelect){
                        wx_exception_list.add(""+mEmployee.wx_user_id);
                    }
                 }
                if(isCheckA){
                    is_default_task_exception=1;
                }else{
                    is_default_task_exception=0;
                }

                wx_complete_list.clear();
                for(Employee mEmployee:call_time2){
                    if(mEmployee.isSelect){
                        wx_complete_list.add(""+mEmployee.wx_user_id);
                    }
                }
                if(isCheckB){
                    is_default_task_complete=1;
                }else{
                    is_default_task_complete=0;
                }


                if(mTaskWcPushErrorDialogListener!=null){
                    mTaskWcPushErrorDialogListener.onClick(wx_exception_list,is_default_task_exception,wx_complete_list,is_default_task_complete);
                }
                dismiss();
            }
        });

        if(is_default_task_exception!=null && is_default_task_exception ==1){
            isCheckA=true;
            box1.setChecked(isCheckA);
        }
        if(is_default_task_complete!=null && is_default_task_complete ==1){
            isCheckB=true;
            box2.setChecked(isCheckB);
        }



        viewGroup1.setLayoutAnimation(controller);
        viewGroup1.scheduleLayoutAnimation();


    }
    List<String> wx_exception_list=new ArrayList<>();
    Integer is_default_task_exception;

    List<String> wx_complete_list=new ArrayList<>();
    Integer is_default_task_complete;

    public void setWx_exception_list(List<String> wx_exception_list) {
        if(wx_exception_list!=null){
            this.wx_exception_list = wx_exception_list;
        }
    }

    public void setIs_default_task_exception(Integer is_default_task_exception) {
        this.is_default_task_exception = is_default_task_exception;
    }

    public void setWx_complete_list(List<String> wx_complete_list) {
        if(wx_complete_list!=null){
            this.wx_complete_list = wx_complete_list;
        }
    }

    public void setIs_default_task_complete(Integer is_default_task_complete) {
        this.is_default_task_complete = is_default_task_complete;
    }

    LayoutAnimationController controller = MyLayoutAnimationHelper.makeLayoutAnimationController();
    LinearLayout viewGroup1;
    LinearLayout viewGroup2;

}

