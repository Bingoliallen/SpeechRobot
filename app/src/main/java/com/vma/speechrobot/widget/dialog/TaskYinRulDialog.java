package com.vma.speechrobot.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.divider.GridSpacingItemDecoration;
import com.example.common.adapter.recycler.layout_manager.CustomGridLayoutManager;
import com.example.common.utils.ViewUtil;
import com.example.common.widget.BaseDialog;
import com.vma.speechrobot.R;
import com.vma.speechrobot.adapter.YinRuAdapter;
import com.vma.speechrobot.bean.Customer;
import com.vma.speechrobot.bean.KeyValueBean;
import com.vma.speechrobot.presenter.TaskCreatePresenter;
import com.vma.speechrobot.widget.CustomSpinner;
import com.vma.speechrobot.widget.SpinerPopWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/25.
 */

public class TaskYinRulDialog extends BaseDialog {

    public interface TaskYinRulDialogListener{
        void onClick(List<Customer> mCustomer);
    }
    private TaskYinRulDialogListener mTaskYinRulDialogListener;

    public void setmTaskYinRulDialogListener(TaskYinRulDialogListener mTaskYinRulDialogListener) {
        this.mTaskYinRulDialogListener = mTaskYinRulDialogListener;
    }

    private ImageView ivClose,iv_item;
    private TextView tv_title,tvCust,tv_page,tv_total,tv_cancel,tv_ok;

    private List<Customer> mCustomer=new ArrayList<>();
    private RecyclerView recycler_view;
    private YinRuAdapter mYinRuAdapter;
    private int page_num=1;
    private int page_size=10;
    private int total_num;
    private boolean isSelectALL=false;
    private CustomSpinner sp_register_zkzt;
    private MyArrayAdapter zkztadapterResult;

    private Integer cust_id=null;
    private TaskCreatePresenter mPresenter;

    public TaskYinRulDialog(Context context, TaskCreatePresenter mPresenter) {
        super(context, R.layout.dialog_yinru, ViewUtil.dp2px(context, 310),
                ViewUtil.dp2px(context, 180));
       this.mPresenter=mPresenter;
    }



   /* public void setTotal_num(int total_num) {
        this.total_num = total_num;
    }

    public void setmCustomer(int page_num, List<Customer> mCustomer0) {
        this.page_num = page_num;
        mCustomer=mCustomer0;
    }*/

    public void refesh(int total_num,int page_num, List<Customer> mCustomer0){
        this.total_num = total_num;
        this.page_num = page_num;

        int mPages = (int) Math.ceil(total_num * 1.0 / page_size);
        tv_page.setText("共"+mPages+"页");

        mCustomer=mCustomer0;

        int num=0;
        int totalNum=0;
        for(int i=0;i<mCustomer.size();i++){
            if( mCustomer.get(i).isSelect){
                totalNum+=mCustomer.get(i).effective_num;
                num++;
            }
        }
        tv_total.setText("已选择"+num+"份名单，有效客户"+totalNum+"个");


        mYinRuAdapter.set(mCustomer);
    }

    @Override
    public void initContentView() {
        sp_register_zkzt = (CustomSpinner) getView(R.id.sp_register_zkzt);


        iv_item = getView(R.id.iv_item);
        ivClose = getView(R.id.iv_close);
        tv_title = getView(R.id.tv_title);
        tv_cancel = getView(R.id.tv_cancel);
        tv_ok = getView(R.id.tv_ok);

        tvCust = getView(R.id.tvCust);
        tv_page = getView(R.id.tv_page);
        tv_total = getView(R.id.tv_total);
        int mPages = (int) Math.ceil(total_num * 1.0 / page_size);
        tv_page.setText("共"+mPages+"页");

        recycler_view = getView(R.id.recycler_view);

        mYinRuAdapter = new YinRuAdapter(mContext);
        mYinRuAdapter.setOnRecyclerItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<Customer>() {
            @Override
            public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, Customer data, int position) {
                mCustomer.get(position).isSelect=!mCustomer.get(position).isSelect;
                int num=0;
                int totalNum=0;
                for(int i=0;i<mCustomer.size();i++){
                    if( mCustomer.get(i).isSelect){
                        totalNum+=mCustomer.get(i).effective_num;
                        num++;
                    }
                }
                tv_total.setText("已选择"+num+"份名单，有效客户"+totalNum+"个");
                mYinRuAdapter.notifyDataSetChanged();
            }
        });
        mYinRuAdapter.setPage(page_num);
        CustomGridLayoutManager layoutManagerTime = new CustomGridLayoutManager(mContext, 1);
        layoutManagerTime.setScrollEnabled(true);
        recycler_view.setLayoutManager(layoutManagerTime);
        recycler_view.addItemDecoration(
                new GridSpacingItemDecoration(1, ViewUtil.dp2px(mContext, 0), ViewUtil.dp2px(mContext, 0),
                        true));
        recycler_view.setAdapter(mYinRuAdapter);

        mYinRuAdapter.set(mCustomer);


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
                List<Customer> mCustomerS=new ArrayList<>();
                for(int i=0;i<mCustomer.size();i++){
                    if( mCustomer.get(i).isSelect){
                        mCustomerS.add(mCustomer.get(i));
                    }
                }
                if(mTaskYinRulDialogListener!=null){
                    mTaskYinRulDialogListener.onClick(mCustomerS);
                }
                dismiss();
            }
        });

        final List<KeyValueBean<Integer, String>> listVoice = new ArrayList<>();
        listVoice.add(new KeyValueBean(null, "全部客户名单"));
        listVoice.add(new KeyValueBean(new Integer(0), "未拨打名单"));
        listVoice.add(new KeyValueBean(new Integer(1), "已拨打名单"));

        zkztadapterResult = new MyArrayAdapter(mContext, listVoice);
        zkztadapterResult.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_register_zkzt.setAdapter(zkztadapterResult);

        sp_register_zkzt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int arg2, long l) {
                KeyValueBean<Integer, String> data = listVoice.get(arg2);
                cust_id = data.key;
                if (data.key == null) {
                    tvCust.setText(null);
                } else {
                    tvCust.setText(data.value);
                }
                isSelectALL=false;
                iv_item.setImageResource(R.drawable.danxuan);

                mPresenter.customer_list(cust_id,page_num,page_size);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        sp_register_zkzt.setSelection(0, true);


       iv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!isSelectALL){
                   isSelectALL=true;
                   iv_item.setImageResource(R.drawable.danxuan1);
               }else{
                   isSelectALL=false;
                   iv_item.setImageResource(R.drawable.danxuan);
               }

                for(Customer c :mCustomer){
                    c.isSelect=isSelectALL;
                }

                int num=0;
                int totalNum=0;
                for(int i=0;i<mCustomer.size();i++){
                    if( mCustomer.get(i).isSelect){
                        totalNum+=mCustomer.get(i).effective_num;
                        num++;
                    }
                }
                tv_total.setText("已选择"+num+"份名单，有效客户"+totalNum+"个");

                mYinRuAdapter.notifyDataSetChanged();
            }
        });



    }




    private class MyArrayAdapter extends ArrayAdapter<KeyValueBean<Integer, String>> {

        private List<KeyValueBean<Integer, String>> mList;

        public MyArrayAdapter(Context context, List<KeyValueBean<Integer, String>> objects) {
            super(context, android.R.layout.simple_list_item_1, android.R.id.text1, objects);
            mList = objects;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, null);
            }
            TextView tvName = (TextView) convertView.findViewById(android.R.id.text1);

            KeyValueBean<Integer, String> mBean = mList.get(position);
            tvName.setText(mBean.value);

            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(getContext()).inflate(
                        android.R.layout.simple_list_item_1, null);
            TextView text = (TextView) convertView
                    .findViewById(android.R.id.text1);
            KeyValueBean<Integer, String> mBean = mList.get(position);
            text.setText(mBean.value);
            return convertView;
        }

    }


}

