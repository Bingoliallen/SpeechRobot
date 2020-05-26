package com.vma.speechrobot.widget.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.List;


/**
 * Date: 2018/12/25
 * Author: libaibing
 * Email：
 * Des：
 */

public class TaskMsgModeDialog extends BaseDialog {

    public interface TaskMsgModeDialogListener{
        void onClick(Integer key_template,Integer is_default_key_template);
    }
    private TaskMsgModeDialogListener mTaskMsgModeDialogListener;
    private ImageView ivMR1;
    private ImageView ivClose;
    private TextView tv_title,tv_cancel,tv_ok;


    private ScrollView mScrollView;
    private CheckBox box1;
    private TaskMsgModeEndAdapter mTaskMsgModeEndAdapter;
    private RecyclerView recycler_view;
    private List<MessageTemplate> mCustomer=new ArrayList<>();

    private boolean isCheck=false;
    private MessageTemplate mMessageTemplate;

    public void setmTaskMsgModeDialogListener(TaskMsgModeDialogListener mTaskMsgModeDialogListener) {
        this.mTaskMsgModeDialogListener = mTaskMsgModeDialogListener;
    }

    public TaskMsgModeDialog(Context context) {
        super(context, R.layout.dialog_msg_mode, ViewUtil.dp2px(context, 278),
                ViewUtil.dp2px(context, 180));

    }


    public void setmCustomer(List<MessageTemplate> mCustomer1) {
        this.mCustomer.clear();
        this.mCustomer.addAll(mCustomer1);
    }

    @Override
    public void initContentView() {

        ivMR1 = getView(R.id.ivMR1);

        ivMR1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDialog mMessageDialog = new MessageDialog(mContext);
                mMessageDialog.setTitle("默认设置");
                mMessageDialog.setText("设置为默认后，以后每次创建任务将会推送给对应的销售人员，不需要重复设置。");
                mMessageDialog.show();
            }
        });
        ivClose = getView(R.id.iv_close);
        tv_title = getView(R.id.tv_title);
        tv_cancel = getView(R.id.tv_cancel);
        tv_ok = getView(R.id.tv_ok);
        box1 = getView(R.id.box1);
        box1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCheck=isChecked;
            }
        });
        recycler_view = getView(R.id.recycler_view);

        mTaskMsgModeEndAdapter = new TaskMsgModeEndAdapter(mContext);
        mTaskMsgModeEndAdapter.setOnRecyclerItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<MessageTemplate>() {
            @Override
            public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, MessageTemplate data, int position) {
               boolean is=data.isSelect;
                mMessageTemplate=null;
                for(MessageTemplate d :mCustomer){
                    d.isSelect=false;
                }
                mCustomer.get(position).isSelect=!is;
                if(mCustomer.get(position).isSelect){
                    mMessageTemplate=data;
                }
                mTaskMsgModeEndAdapter.set(mCustomer);


            }
        });
        CustomGridLayoutManager layoutManagerTime = new CustomGridLayoutManager(mContext, 5);
        layoutManagerTime.setScrollEnabled(true);
        recycler_view.setLayoutManager(layoutManagerTime);
        recycler_view.addItemDecoration(
                new GridSpacingItemDecoration(5, ViewUtil.dp2px(mContext, 6), ViewUtil.dp2px(mContext, 6),
                        true));
        recycler_view.setAdapter(mTaskMsgModeEndAdapter);

        if(key_template!=null && key_template!=0){
            for(MessageTemplate d :mCustomer){
                if(d.id==key_template){
                    d.isSelect=true;
                }else{
                    d.isSelect=false;
                }

            }
        }

        mTaskMsgModeEndAdapter.set(mCustomer);

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

                if(mMessageTemplate !=null){
                    key_template=mMessageTemplate.id;
                }else{
                    key_template=null;
                }

                if(isCheck){
                    is_default_key_template=1;
                }else{
                    is_default_key_template=0;
                }
                if(mTaskMsgModeDialogListener!=null){
                    mTaskMsgModeDialogListener.onClick(key_template,is_default_key_template);
                }
                dismiss();
            }
        });
        if(is_default_key_template!=null && is_default_key_template ==1){
            isCheck=true;
            box1.setChecked(isCheck);
        }

    }

    public void setKey_template(Integer key_template) {
        this.key_template = key_template;
    }

    public void setIs_default_key_template(Integer is_default_key_template) {
        this.is_default_key_template = is_default_key_template;
    }

    Integer key_template=null;

    Integer is_default_key_template=null;

}
