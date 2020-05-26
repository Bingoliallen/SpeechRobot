package com.vma.speechrobot.view.fragment;

import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.example.common.consts.CommonTimeConst;
import com.example.common.presenter.impl.BasePresenter;
import com.example.common.utils.Spanny;
import com.example.common.utils.TimeUtil;
import com.example.common.view.fragment.BaseFragment;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.TaskDetailBean;
import com.vma.speechrobot.bean.TaskInfoBean;
import com.vma.speechrobot.enums.TaskStatus;
import com.vma.speechrobot.presenter.TaskInfoPresenter;
import com.vma.speechrobot.presenter.impl.ITaskInfoPresenter;

import butterknife.BindView;

/**
 * Date: 2018/12/27
 * Author: libaibing
 * Email：
 * Des：
 */

public class TaskManagerInfoFragment extends BaseFragment<TaskInfoPresenter> implements
        ITaskInfoPresenter.IView {

    public interface TaskManagerInfoFragmentListener{
        void fillTaskInfoF();
        void fillTaskInfoS();
    }

    private TaskManagerInfoFragmentListener mTaskManagerInfoFragmentListener;

    public void setmTaskManagerInfoFragmentListener(TaskManagerInfoFragmentListener mTaskManagerInfoFragmentListener) {
        this.mTaskManagerInfoFragmentListener = mTaskManagerInfoFragmentListener;
    }

    private int mId;

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvCreate)
    TextView tvCreate;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.tvSuccess)
    TextView tvSuccess;

    @BindView(R.id.tv_level1)
    TextView tv_level1;
    @BindView(R.id.tv_level2)
    TextView tv_level2;
    @BindView(R.id.tv_level3)
    TextView tv_level3;
    @BindView(R.id.tv_level4)
    TextView tv_level4;
    @BindView(R.id.tv_level5)
    TextView tv_level5;
    @BindView(R.id.tv_level6)
    TextView tv_level6;


    @BindView(R.id.tv_call1)
    TextView tv_call1;
    @BindView(R.id.tv_call2)
    TextView tv_call2;
    @BindView(R.id.tv_call3)
    TextView tv_call3;
    @BindView(R.id.tv_call4)
    TextView tv_call4;


    @BindView(R.id.tv_time1)
    TextView tv_time1;
    @BindView(R.id.tv_time2)
    TextView tv_time2;
    @BindView(R.id.tv_time3)
    TextView tv_time3;
    @BindView(R.id.tv_time4)
    TextView tv_time4;


    @BindView(R.id.tv_count1)
    TextView tv_count1;
    @BindView(R.id.tv_count2)
    TextView tv_count2;
    @BindView(R.id.tv_count3)
    TextView tv_count3;
    @BindView(R.id.tv_count4)
    TextView tv_count4;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_task__manager_info;
    }

    @Override
    protected TaskInfoPresenter createPresenter() {
        return new TaskInfoPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();
        mId = getArguments().getInt("id");
        if(mId !=-1){
            mPresenter.getTaskInfo(mId);
        }

    }

    public  void refresh(int mId){
        this.mId = mId;
        mPresenter.getTaskInfo(mId);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected boolean lazyLoad() {

        return super.lazyLoad();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void fillTaskInfoF() {
         if(mTaskManagerInfoFragmentListener!=null){
             mTaskManagerInfoFragmentListener.fillTaskInfoF();
         }
    }

    @Override
    public void fillTaskInfo(TaskInfoBean data) {
        if (data == null) {
            return;
        }

        if(mTaskManagerInfoFragmentListener!=null){
            mTaskManagerInfoFragmentListener.fillTaskInfoS();
        }
        tvName.setText(data.task_name);
        tvCreate.setText(String.format("%s创建于%s", data.admin_name, TimeUtil
                .long2Str(Long.parseLong(data.create_time), String
                        .format("%s-%s-%s %s:%s", CommonTimeConst.YEAR, CommonTimeConst.MONTH,
                                CommonTimeConst.DAY, CommonTimeConst.HOUR, CommonTimeConst.MINUTE))));
        /*switch (data.status) {
            case TaskStatus.NO_START:
                tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme3));
                tvStatus.setText("未开始");
                break;
            case TaskStatus.PROCESS:
                tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme1));
                tvStatus.setText("进行中");
                break;
            case TaskStatus.PAUSE:
                tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme6));
                tvStatus.setText("已暂停");
                break;
            case TaskStatus.STOP:
                tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme6));
                tvStatus.setText("已结束");
                break;
            case TaskStatus.COMPLETE:
                tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme5));
                tvStatus.setText("已完成");
                break;
        }*/
        tvProgress.setText(String.format("外呼进度 %s", data.call_progress));
        tvSuccess.setText(String.format("电话接通率 %s", data.call_success_rate));


        if (data.call_level != null) {
            if(data.call_level.size()==1) {
                Spanny spanny = new Spanny();
                spanny.append(data.call_level.get(0).name)
                        .append("\n")
                        .append(data.call_level.get(0).key);
                tv_level1.setText(spanny);
            }else  if(data.call_level.size()==2) {
                Spanny spanny = new Spanny();
                spanny.append(data.call_level.get(0).name)
                        .append("\n")
                        .append(data.call_level.get(0).key);
                tv_level1.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(1).name)
                        .append("\n")
                        .append(data.call_level.get(1).key);
                tv_level2.setText(spanny);
            }else  if(data.call_level.size()==3) {
                Spanny spanny = new Spanny();
                spanny.append(data.call_level.get(0).name)
                        .append("\n")
                        .append(data.call_level.get(0).key);
                tv_level1.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(1).name)
                        .append("\n")
                        .append(data.call_level.get(1).key);
                tv_level2.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(2).name)
                        .append("\n")
                        .append(data.call_level.get(2).key);
                tv_level3.setText(spanny);
            }else  if(data.call_level.size()==4) {
                Spanny spanny = new Spanny();
                spanny.append(data.call_level.get(0).name)
                        .append("\n")
                        .append(data.call_level.get(0).key);
                tv_level1.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(1).name)
                        .append("\n")
                        .append(data.call_level.get(1).key);
                tv_level2.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(2).name)
                        .append("\n")
                        .append(data.call_level.get(2).key);
                tv_level3.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(3).name)
                        .append("\n")
                        .append(data.call_level.get(3).key);
                tv_level4.setText(spanny);
            }else  if(data.call_level.size()==5) {
                Spanny spanny = new Spanny();
                spanny.append(data.call_level.get(0).name)
                        .append("\n")
                        .append(data.call_level.get(0).key);
                tv_level1.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(1).name)
                        .append("\n")
                        .append(data.call_level.get(1).key);
                tv_level2.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(2).name)
                        .append("\n")
                        .append(data.call_level.get(2).key);
                tv_level3.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(3).name)
                        .append("\n")
                        .append(data.call_level.get(3).key);
                tv_level4.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(4).name)
                        .append("\n")
                        .append(data.call_level.get(4).key);
                tv_level5.setText(spanny);
            }else  if(data.call_level.size()==6) {
                Spanny spanny = new Spanny();
                spanny.append(data.call_level.get(0).name)
                        .append("\n")
                        .append(data.call_level.get(0).key);
                tv_level1.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(1).name)
                        .append("\n")
                        .append(data.call_level.get(1).key);
                tv_level2.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(2).name)
                        .append("\n")
                        .append(data.call_level.get(2).key);
                tv_level3.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(3).name)
                        .append("\n")
                        .append(data.call_level.get(3).key);
                tv_level4.setText(spanny);

                spanny = new Spanny();
                spanny.append(data.call_level.get(4).name)
                        .append("\n")
                        .append(data.call_level.get(4).key);
                tv_level5.setText(spanny);
                spanny = new Spanny();
                spanny.append(data.call_level.get(5).name)
                        .append("\n")
                        .append(data.call_level.get(5).key);
                tv_level6.setText(spanny);
            }
        }

        if(data.call_result!=null){
            if(data.call_result.size()==1){
                tv_call1.setText(data.call_result.get(0).name);
            }else  if(data.call_result.size()==2){
                tv_call1.setText(data.call_result.get(0).name);
                tv_call2.setText(data.call_result.get(1).name);
            }else  if(data.call_result.size()==3){
                tv_call1.setText(data.call_result.get(0).name);
                tv_call2.setText(data.call_result.get(1).name);
                tv_call3.setText(data.call_result.get(2).name);
            }else  if(data.call_result.size()==4){
                tv_call1.setText(data.call_result.get(0).name);
                tv_call2.setText(data.call_result.get(1).name);
                tv_call3.setText(data.call_result.get(2).name);
                tv_call4.setText(data.call_result.get(3).name);
            }

        }

        if(data.call_time!=null){
            if(data.call_time.size()==1){
                tv_time1.setText(data.call_time.get(0).name);
            }else  if(data.call_time.size()==2){
                tv_time1.setText(data.call_time.get(0).name);
                tv_time2.setText(data.call_time.get(1).name);
            }else  if(data.call_time.size()==3){
                tv_time1.setText(data.call_time.get(0).name);
                tv_time2.setText(data.call_time.get(1).name);
                tv_time3.setText(data.call_time.get(2).name);
            }else  if(data.call_time.size()==4){
                tv_time1.setText(data.call_time.get(0).name);
                tv_time2.setText(data.call_time.get(1).name);
                tv_time3.setText(data.call_time.get(2).name);
                tv_time4.setText(data.call_time.get(3).name);
            }

        }

        if(data.call_count!=null){
            if(data.call_count.size()==1){
                tv_count1.setText(data.call_count.get(0).name);
            }else  if(data.call_time.size()==2){
                tv_count1.setText(data.call_count.get(0).name);
                tv_count2.setText(data.call_count.get(1).name);
            }else  if(data.call_time.size()==3){
                tv_count1.setText(data.call_count.get(0).name);
                tv_count2.setText(data.call_count.get(1).name);
                tv_count3.setText(data.call_count.get(2).name);
            }else  if(data.call_time.size()==4){
                tv_count1.setText(data.call_count.get(0).name);
                tv_count2.setText(data.call_count.get(1).name);
                tv_count3.setText(data.call_count.get(2).name);
                tv_count4.setText(data.call_count.get(3).name);
            }

        }

    }

    @Override
    public void fillTaskDetail(TaskDetailBean data) {

    }
}
