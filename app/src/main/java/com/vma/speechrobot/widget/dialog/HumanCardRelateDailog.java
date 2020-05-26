package com.vma.speechrobot.widget.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.divider.GridSpacingItemDecoration;
import com.example.common.adapter.recycler.layout_manager.CustomGridLayoutManager;
import com.example.common.utils.ViewUtil;
import com.example.common.widget.BaseDialog;
import com.vma.speechrobot.R;
import com.vma.speechrobot.adapter.HumanCardRelateAdapter;
import com.vma.speechrobot.bean.Employee;
import com.vma.speechrobot.bean.HumanCardRelate;
import com.vma.speechrobot.bean.TaskCardOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019/1/3
 * Author: libaibing
 * Email：
 * Des：
 */

public class HumanCardRelateDailog  extends BaseDialog {

    public interface HumanCardRelateDailogListener{
        void onClick( List<HumanCardRelate> mCustomer);
    }

    private HumanCardRelateDailogListener mHumanCardRelateDailogListener;

    public void setmHumanCardRelateDailogListener(HumanCardRelateDailogListener mHumanCardRelateDailogListener) {
        this.mHumanCardRelateDailogListener = mHumanCardRelateDailogListener;
    }

    private ImageView ivClose;
    private TextView tv_title,tvCust,tv_ok,tv_cancel;

    private List<HumanCardRelate> mCustomer=new ArrayList<>();
    private RecyclerView recycler_view;
    private HumanCardRelateAdapter mHumanCardRelateAdapter;

    private List<TaskCardOption> mCustomer1=new ArrayList<>();
    private List<Employee> mCustomer2=new ArrayList<>();

    private Integer cust_id=null;

    public HumanCardRelateDailog(Context context) {
        super(context, R.layout.dialog_humancardrelate, ViewUtil.dp2px(context, 310),
                ViewUtil.dp2px(context, 180));
    }

    public void setmCustomer1(List<TaskCardOption> mCustomer1) {
        this.mCustomer1 = mCustomer1;
    }

    public void setmCustomer2(List<Employee> mCustomer2) {
        this.mCustomer2 = mCustomer2;
    }

    public void setmCustomer(List<HumanCardRelate> mCustomer0) {
        this.mCustomer.clear();
        this.mCustomer.addAll(mCustomer0);
    }

    @Override
    public void initContentView() {
        ivClose = getView(R.id.iv_close);
        tv_title = getView(R.id.tv_title);

        tvCust = getView(R.id.tvCust);

        tv_cancel = getView(R.id.tv_cancel);
        tv_ok = getView(R.id.tv_ok);

        recycler_view = getView(R.id.recycler_view);

        mHumanCardRelateAdapter = new HumanCardRelateAdapter(mContext);
        mHumanCardRelateAdapter.setmItemHumanCardRelateListener(new HumanCardRelateAdapter.ItemHumanCardRelateListener() {
            @Override
            public void onClick(HumanCardRelate item, int position) {
                mCustomer.remove(position);
                mHumanCardRelateAdapter.set(mCustomer);
            }
        });
        CustomGridLayoutManager layoutManagerTime = new CustomGridLayoutManager(mContext, 1);
        layoutManagerTime.setScrollEnabled(true);
        recycler_view.setLayoutManager(layoutManagerTime);
        recycler_view.addItemDecoration(
                new GridSpacingItemDecoration(1, ViewUtil.dp2px(mContext, 0), ViewUtil.dp2px(mContext, 0),
                        true));
        recycler_view.setAdapter(mHumanCardRelateAdapter);

        mHumanCardRelateAdapter.set(mCustomer);


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
                if(mHumanCardRelateDailogListener!=null){
                    mHumanCardRelateDailogListener.onClick(mCustomer);
                }
                dismiss();
            }
        });

        tvCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹窗
                AddHumanCardRelateDailog mAddHumanCardRelateDailog =  new AddHumanCardRelateDailog(mContext);
                mAddHumanCardRelateDailog.setmCustomer1(mCustomer1);
                mAddHumanCardRelateDailog.setmCustomer2(mCustomer2);
                mAddHumanCardRelateDailog.setmAddHumanCardRelateDailogListener(new AddHumanCardRelateDailog.AddHumanCardRelateDailogListener() {
                    @Override
                    public void onClick(HumanCardRelate cust_id) {
                        mCustomer.add(cust_id);
                        mHumanCardRelateAdapter.set(mCustomer);

                    }
                });
                mAddHumanCardRelateDailog.show();
            }
        });

    }



}

