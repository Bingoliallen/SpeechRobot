package com.vma.speechrobot.widget.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.divider.GridSpacingItemDecoration;
import com.example.common.adapter.recycler.layout_manager.CustomGridLayoutManager;
import com.example.common.utils.ViewUtil;
import com.example.common.widget.BaseDialog;
import com.vma.speechrobot.R;
import com.vma.speechrobot.adapter.CallNumberAdapter;
import com.vma.speechrobot.adapter.YinRuAdapter;
import com.vma.speechrobot.bean.KeyNameBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2018/12/26
 * Author: libaibing
 * Email：
 * Des：
 */

public class TaskCallNumberDialog  extends BaseDialog {

    public interface TaskCallNumberDialogListener{
       void onClick(List<KeyNameBean> call_time);
    }

    private TaskCallNumberDialogListener mTaskCallNumberDialogListener;

    private TextView tv_cancel,tvSelect,tv_ok;
    private RecyclerView recycler_view;
    private CallNumberAdapter mCallNumberAdapter;
    private List<KeyNameBean> call_time=new ArrayList<>();

    private List<KeyNameBean> call_time1=new ArrayList<>();

    private EditText mEditText;
    private ImageView iv_item;
    private boolean isSelectAll=false;


    public TaskCallNumberDialog(Context context,List<KeyNameBean> call_time0) {
        super(context, R.layout.dialog_call_number, ViewUtil.dp2px(context, 170),
                ViewUtil.dp2px(context, 180));
        call_time=call_time0;

    }


    public void setmTaskCallNumberDialogListener(TaskCallNumberDialogListener mTaskCallNumberDialogListener) {
        this.mTaskCallNumberDialogListener = mTaskCallNumberDialogListener;
    }

    @Override
    public void initContentView() {
        isSelectAll=false;
        tv_cancel = getView(R.id.tv_cancel);
        recycler_view = getView(R.id.recycler_view);
        mEditText = getView(R.id.mEditText);
        iv_item = getView(R.id.iv_item);
        tv_ok = getView(R.id.tv_ok);
        tvSelect= getView(R.id.tvSelect);

       /* for(int i=0;i<call_time.size();i++){
            call_time.get(i).isSelect=false;

        }*/


        int num=0;
        for(int i=0;i<call_time.size();i++){
            if( call_time.get(i).isSelect){
                num++;
            }
        }
        tvSelect.setText(String.format("已选择%s个", num));
        if(num==call_time.size()){
            isSelectAll=true;
            iv_item.setImageResource(R.drawable.danxuan1);
        }


        mCallNumberAdapter = new CallNumberAdapter(mContext);
        mCallNumberAdapter.setOnRecyclerItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener() {
            @Override
            public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, Object data, int position) {
                mCallNumberAdapter.getData().get(position).isSelect=!mCallNumberAdapter.getData().get(position).isSelect;
                int num=0;
                for(int i=0;i<mCallNumberAdapter.getData().size();i++){
                    if( mCallNumberAdapter.getData().get(i).isSelect){
                        num++;
                    }
                }
                tvSelect.setText(String.format("已选择%s个", num));
                if(num==call_time.size()){
                    isSelectAll=true;
                    iv_item.setImageResource(R.drawable.danxuan1);
                }else{
                    isSelectAll=false;
                    iv_item.setImageResource(R.drawable.danxuan);
                }
                mCallNumberAdapter.notifyDataSetChanged();

            }
        });


        CustomGridLayoutManager layoutManagerTime = new CustomGridLayoutManager(mContext, 1);
        layoutManagerTime.setScrollEnabled(true);
        recycler_view.setLayoutManager(layoutManagerTime);
        recycler_view.addItemDecoration(
                new GridSpacingItemDecoration(1, ViewUtil.dp2px(mContext, 0), ViewUtil.dp2px(mContext, 0),
                        true));
        recycler_view.setAdapter(mCallNumberAdapter);
        mCallNumberAdapter.set(call_time);


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        iv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isSelectAll){
                    isSelectAll=false;
                    iv_item.setImageResource(R.drawable.danxuan);
                }else{
                    isSelectAll=true;
                    iv_item.setImageResource(R.drawable.danxuan1);
                }

               for(int i=0;i<mCallNumberAdapter.getData().size();i++){
                   mCallNumberAdapter.getData().get(i).isSelect=isSelectAll;
               }
               if(isSelectAll){
                   tvSelect.setText(String.format("已选择%s个", mCallNumberAdapter.getData().size()));
               }else{
                   tvSelect.setText(String.format("已选择%s个", 0));
               }

                mCallNumberAdapter.notifyDataSetChanged();
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String ed=editable.toString();
                if(!TextUtils.isEmpty(ed)){
                    call_time1.clear();
                    for(int i=0;i<call_time.size();i++){
                        if(call_time.get(i).name.indexOf(ed)!=-1){
                            call_time1.add(call_time.get(i));
                        }
                    }
                    mCallNumberAdapter.set(call_time1);
                }else{
                    mCallNumberAdapter.set(call_time);
                }


            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<KeyNameBean> call_time2=new ArrayList<>();
                for(int i=0;i<mCallNumberAdapter.getData().size();i++){
                    if(mCallNumberAdapter.getData().get(i).isSelect){
                        call_time2.add(mCallNumberAdapter.getData().get(i));
                    }
                }

                if(mTaskCallNumberDialogListener!=null){
                    mTaskCallNumberDialogListener.onClick(call_time2);
                }
                dismiss();
            }
        });

    }



}


