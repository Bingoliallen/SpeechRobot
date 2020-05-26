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
import com.example.common.utils.Spanny;
import com.example.common.utils.ViewUtil;
import com.example.common.widget.BaseDialog;
import com.vma.speechrobot.R;
import com.vma.speechrobot.adapter.TaskWcPushAdapter;
import com.vma.speechrobot.bean.Employee;
import com.vma.speechrobot.utils.MyLayoutAnimationHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2018/12/25
 * Author: libaibing
 * Email：
 * Des：
 */

public class TaskWcPushDialog extends BaseDialog {
    public interface TaskWcPushDialogListener{
        void onClick(List<String> wx_user_list_a,Integer is_default_wx_a,
                     List<String> wx_user_list_b,Integer is_default_wx_b,
                     List<String> wx_user_list_c,Integer is_default_wx_c);
    }
    private TaskWcPushDialogListener mTaskWcPushDialogListener;

    private ImageView ivMR1;
    private ImageView ivMR2;
    private ImageView ivMR3;

    private ImageView ivClose;
    private TextView tv_title,tv_cancel,tv_ok;

    private TextView tvSwitch1;
    private TextView tvSwitch2;
    private TextView tvSwitch3;
    private ScrollView mScrollView;
    private CheckBox box1;
    private CheckBox box2;
    private CheckBox box3;
    private RecyclerView recycler_view1;
    private RecyclerView recycler_view2;
    private RecyclerView recycler_view3;
    private TaskWcPushAdapter mTaskWcPushAdapter1;
    private TaskWcPushAdapter mTaskWcPushAdapter2;
    private TaskWcPushAdapter mTaskWcPushAdapter3;
    private List<Employee> call_time1=new ArrayList<>();
    private List<Employee> call_time2=new ArrayList<>();
    private List<Employee> call_time3=new ArrayList<>();
    private boolean isA=true;
    private boolean isB=false;
    private boolean isC=false;
    private boolean isCheckA=false;
    private boolean isCheckB=false;
    private boolean isCheckC=false;
    public TaskWcPushDialog(Context context) {
        super(context, R.layout.dialog_wc_push, ViewUtil.dp2px(context, 300),
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


        this.call_time3.clear();
        for(Employee bean:call_time){
            this.call_time3.add(new Employee(bean.id,bean.employee_name,bean.isSelect,bean.wx_user_id) );
        }

    }

    public void setmTaskWcPushDialogListener(TaskWcPushDialogListener mTaskWcPushDialogListener) {
        this.mTaskWcPushDialogListener = mTaskWcPushDialogListener;
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
        tv_cancel = getView(R.id.tv_cancel);
        tv_ok = getView(R.id.tv_ok);
        ivClose = getView(R.id.iv_close);
        tv_title = getView(R.id.tv_title);
        tvSwitch1 = getView(R.id.tvSwitch1);
        tvSwitch2 = getView(R.id.tvSwitch2);
        tvSwitch3 = getView(R.id.tvSwitch3);
        tvSwitch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isA){
                    isA=true;
                    tvSwitch1.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvSwitch1.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape_p));
                    isB=false;
                    tvSwitch2.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch2.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape1));
                    isC=false;
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
                if(!isB){
                    isA=false;
                    tvSwitch1.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch1.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape));
                    isB=true;
                    tvSwitch2.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvSwitch2.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape1_p));
                    isC=false;
                    tvSwitch3.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch3.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape2));


                    viewGroup1.setVisibility(View.GONE);
                    viewGroup2.setVisibility(View.VISIBLE);
                    viewGroup3.setVisibility(View.GONE);
                    viewGroup2.setLayoutAnimation(controller);
                    viewGroup2.scheduleLayoutAnimation();
                }

            }
        });
        tvSwitch3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isC){
                    isA=false;
                    tvSwitch1.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch1.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape));
                    isB=false;
                    tvSwitch2.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvSwitch2.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape1));
                    isC=true;
                    tvSwitch3.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvSwitch3.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape2_p));



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
        recycler_view1 = getView(R.id.recycler_view1);
        recycler_view2 = getView(R.id.recycler_view2);
        recycler_view3 = getView(R.id.recycler_view3);

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

        if(wx_user_list_a!=null && wx_user_list_a.size()>0){
            for(String bean:wx_user_list_a){
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

        if(wx_user_list_b!=null && wx_user_list_b.size()>0){
            for(String bean:wx_user_list_b){
                for(Employee mEmployee:call_time2){
                    if(bean.equals(""+mEmployee.wx_user_id)){
                        mEmployee.isSelect=true;
                    }
                }

            }
        }

        mTaskWcPushAdapter2.set(call_time2);



        mTaskWcPushAdapter3 = new TaskWcPushAdapter(mContext);
        mTaskWcPushAdapter3.setOnRecyclerItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<Employee>() {
            @Override
            public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, Employee data, int position) {
                mTaskWcPushAdapter3.getData().get(position).isSelect=!mTaskWcPushAdapter3.getData().get(position).isSelect;
                mTaskWcPushAdapter3.notifyDataSetChanged();

            }
        });
        CustomGridLayoutManager layoutManagerTime3 = new CustomGridLayoutManager(mContext, 5);
        layoutManagerTime3.setScrollEnabled(true);
        recycler_view3.setLayoutManager(layoutManagerTime3);
        recycler_view3.addItemDecoration(
                new GridSpacingItemDecoration(5, ViewUtil.dp2px(mContext, 0), ViewUtil.dp2px(mContext, 6),
                        true));
        recycler_view3.setAdapter(mTaskWcPushAdapter3);

        if(wx_user_list_c!=null && wx_user_list_c.size()>0){
            for(String bean:wx_user_list_c){
                for(Employee mEmployee:call_time3){
                    if(bean.equals(""+mEmployee.wx_user_id)){
                        mEmployee.isSelect=true;
                    }
                }

            }
        }
        mTaskWcPushAdapter3.set(call_time3);

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
                wx_user_list_a.clear();
                for(Employee mEmployee:call_time1){
                    if(mEmployee.isSelect){
                        wx_user_list_a.add(""+mEmployee.wx_user_id);
                    }
                }
                if(isCheckA){
                    is_default_wx_a=1;
                }else{
                    is_default_wx_a=0;
                }

                wx_user_list_b.clear();
                for(Employee mEmployee:call_time2){
                    if(mEmployee.isSelect){
                        wx_user_list_b.add(""+mEmployee.wx_user_id);
                    }
                }
                if(isCheckB){
                    is_default_wx_b=1;
                }else{
                    is_default_wx_b=0;
                }


                wx_user_list_c.clear();
                for(Employee mEmployee:call_time3){
                    if(mEmployee.isSelect){
                        wx_user_list_c.add(""+mEmployee.wx_user_id);
                    }
                }
                if(isCheckC){
                    is_default_wx_c=1;
                }else{
                    is_default_wx_c=0;
                }


                if(mTaskWcPushDialogListener!=null){
                    mTaskWcPushDialogListener.onClick(
                            wx_user_list_a,is_default_wx_a,
                            wx_user_list_b,is_default_wx_b,
                            wx_user_list_c,is_default_wx_c);
                }
                dismiss();
            }
        });

        if(is_default_wx_a!=null && is_default_wx_a ==1){
            isCheckA=true;
            box1.setChecked(isCheckA);
        }
        if(is_default_wx_b!=null && is_default_wx_b ==1){
            isCheckB=true;
            box2.setChecked(isCheckB);
        }
        if(is_default_wx_c!=null && is_default_wx_c ==1){
            isCheckC=true;
            box3.setChecked(isCheckC);
        }
        viewGroup1.setLayoutAnimation(controller);
        viewGroup1.scheduleLayoutAnimation();
    }

    List<String> wx_user_list_a=new ArrayList<>();
    Integer is_default_wx_a;

    List<String> wx_user_list_b=new ArrayList<>();
    Integer is_default_wx_b;

    List<String> wx_user_list_c=new ArrayList<>();
    Integer is_default_wx_c;

    public void setWx_user_list_a(List<String> wx_user_list_a) {
        if(wx_user_list_a!=null){
            this.wx_user_list_a = wx_user_list_a;
        }

    }

    public void setIs_default_wx_a(Integer is_default_wx_a) {
        this.is_default_wx_a = is_default_wx_a;
    }

    public void setWx_user_list_b(List<String> wx_user_list_b) {
        if(wx_user_list_b!=null){
            this.wx_user_list_b = wx_user_list_b;
        }

    }

    public void setIs_default_wx_b(Integer is_default_wx_b) {
        this.is_default_wx_b = is_default_wx_b;
    }

    public void setWx_user_list_c(List<String> wx_user_list_c) {
        if(wx_user_list_c!=null){
            this.wx_user_list_c = wx_user_list_c;
        }

    }

    public void setIs_default_wx_c(Integer is_default_wx_c) {
        this.is_default_wx_c = is_default_wx_c;
    }

    LayoutAnimationController controller = MyLayoutAnimationHelper.makeLayoutAnimationController();
    LinearLayout viewGroup1;
    LinearLayout viewGroup2;
    LinearLayout viewGroup3;
}
