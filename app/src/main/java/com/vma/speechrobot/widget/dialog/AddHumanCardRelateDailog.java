package com.vma.speechrobot.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.utils.ViewUtil;
import com.example.common.widget.BaseDialog;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.Employee;
import com.vma.speechrobot.bean.HumanCardRelate;
import com.vma.speechrobot.bean.TaskCardOption;
import com.vma.speechrobot.widget.CustomSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019/1/3
 * Author: libaibing
 * Email：
 * Des：
 */

public class AddHumanCardRelateDailog extends BaseDialog {

    public interface AddHumanCardRelateDailogListener {
        void onClick(HumanCardRelate cust_id);
    }

    private AddHumanCardRelateDailogListener mAddHumanCardRelateDailogListener;

    public void setmAddHumanCardRelateDailogListener(AddHumanCardRelateDailogListener mAddHumanCardRelateDailogListener) {
        this.mAddHumanCardRelateDailogListener = mAddHumanCardRelateDailogListener;
    }

    private ImageView ivClose;
    private TextView tv_title, tvCust1, tvCust2, tv_cancel, tv_ok;

    private List<TaskCardOption> mCustomer1 = new ArrayList<>();
    private List<Employee> mCustomer2 = new ArrayList<>();

    private CustomSpinner sp_register_zkzt1;
    private CustomSpinner sp_register_zkzt2;
    private MyArrayAdapter1 zkztadapterResult1;
    private MyArrayAdapter2 zkztadapterResult2;
    private TaskCardOption mTaskCardOption;
    private Employee mEmployee;

    public AddHumanCardRelateDailog(Context context) {
        super(context, R.layout.dialog_add_humancardrelate, ViewUtil.dp2px(context, 310),
                ViewUtil.dp2px(context, 180));
    }



    public void setmCustomer1(List<TaskCardOption> mCustomer) {
        this.mCustomer1.clear();
        this.mCustomer1.addAll(mCustomer);
    }

    public void setmCustomer2(List<Employee> mCustomer) {
        this.mCustomer2.clear();
        this.mCustomer2.addAll(mCustomer);
    }

    @Override
    public void initContentView() {
        ivClose = getView(R.id.iv_close);
        tv_title = getView(R.id.tv_title);

        tv_cancel = getView(R.id.tv_cancel);
        tv_ok = getView(R.id.tv_ok);

        tvCust1 = getView(R.id.tvCust1);

        tvCust2 = getView(R.id.tvCust2);
        sp_register_zkzt1 = getView(R.id.sp_register_zkzt1);
        sp_register_zkzt2 = getView(R.id.sp_register_zkzt2);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        zkztadapterResult1 = new MyArrayAdapter1(mContext, mCustomer1);
        zkztadapterResult1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_register_zkzt1.setAdapter(zkztadapterResult1);

        sp_register_zkzt1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int arg2, long l) {
                mTaskCardOption = mCustomer1.get(arg2);

                tvCust1.setText(mTaskCardOption.machine_name + "(" + mTaskCardOption.mobile + ")");


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        sp_register_zkzt1.setSelection(-1, true);


        zkztadapterResult2 = new MyArrayAdapter2(mContext, mCustomer2);
        zkztadapterResult2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_register_zkzt2.setAdapter(zkztadapterResult2);

        sp_register_zkzt2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int arg2, long l) {
                mEmployee = mCustomer2.get(arg2);

                tvCust2.setText("" + mEmployee.employee_name);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
        sp_register_zkzt2.setSelection(-1, true);


        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HumanCardRelate mHumanCardRelate = new HumanCardRelate();
                if (mTaskCardOption != null) {
                    mHumanCardRelate.id = mTaskCardOption.id;
                    mHumanCardRelate.mobile= mTaskCardOption.mobile;
                    mHumanCardRelate.machine_name = mTaskCardOption.machine_name;
                }else{
                    Toast.makeText(mContext,"请选择人工坐席",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEmployee != null) {
                    mHumanCardRelate.user_id = mEmployee.id;
                    mHumanCardRelate.employee_name = mEmployee.employee_name;
                }else{
                    Toast.makeText(mContext,"请选择销售人员",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mAddHumanCardRelateDailogListener != null) {
                    mAddHumanCardRelateDailogListener.onClick(mHumanCardRelate);
                }

                dismiss();
            }
        });
    }


    private class MyArrayAdapter1 extends ArrayAdapter<TaskCardOption> {

        private List<TaskCardOption> mList;

        public MyArrayAdapter1(Context context, List<TaskCardOption> objects) {
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

            TaskCardOption mBean = mList.get(position);
            tvName.setText(mBean.machine_name + "(" + mBean.mobile + ")");

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
            TaskCardOption mBean = mList.get(position);
            text.setText(mBean.machine_name + "(" + mBean.mobile + ")");
            return convertView;
        }

    }


    private class MyArrayAdapter2 extends ArrayAdapter<Employee> {

        private List<Employee> mList;

        public MyArrayAdapter2(Context context, List<Employee> objects) {
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

            Employee mBean = mList.get(position);
            tvName.setText("" + mBean.employee_name);

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
            Employee mBean = mList.get(position);
            text.setText("" + mBean.employee_name);
            return convertView;
        }

    }

}


