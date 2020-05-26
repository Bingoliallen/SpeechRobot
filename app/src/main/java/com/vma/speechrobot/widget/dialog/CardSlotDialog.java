package com.vma.speechrobot.widget.dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.common.adapter.recycler.divider.GridSpacingItemDecoration;
import com.example.common.adapter.recycler.layout_manager.CustomGridLayoutManager;
import com.example.common.utils.ViewUtil;
import com.example.common.widget.BaseDialog;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.CardSloBean;
import com.vma.speechrobot.bean.CardSlotAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2019/1/4
 * Author: libaibing
 * Email：
 * Des：
 */

public class CardSlotDialog  extends BaseDialog {

    public interface CardSlotDialogListener{
        void onClick( List<CardSloBean> mCustomer);
    }

    private CardSlotDialogListener mCardSlotDialogListener;

    public void setmHumanCardRelateDailogListener(CardSlotDialogListener mCardSlotDialogListener) {
        this.mCardSlotDialogListener = mCardSlotDialogListener;
    }

    private ImageView ivClose;
    private TextView tv_title,tvCust1,tvCust2,tvCust3,tv_ok,tv_cancel;

    private List<CardSloBean> mCustomer=new ArrayList<>();
    private List<CardSloBean> mCustomer2=new ArrayList<>();
    private List<CardSloBean> mCustomer3=new ArrayList<>();
    private RecyclerView recycler_view;
    private CardSlotAdapter mCardSlotAdapter;

    private boolean isA=true;
    private boolean isB=false;
    private boolean isC=false;

    public CardSlotDialog(Context context) {
        super(context, R.layout.dialog_card_slot, ViewUtil.dp2px(context, 310),
                ViewUtil.dp2px(context, 180));
    }


    public void setmCustomer(List<CardSloBean> mCustomer0) {
        this.mCustomer.clear();
        mCustomer2.clear();
        mCustomer3.clear();

        this.mCustomer.addAll(mCustomer0);
        for(CardSloBean bean  :mCustomer0){
            mCustomer2.add(new CardSloBean(bean.getMobile(), bean.getUser_name()));
        }


        for (int i = 0; i < mCustomer0.size(); i++)  //外循环是循环的次数
        {
            for (int j = mCustomer0.size() - 1 ; j > i; j--)  //内循环是 外循环一次比较的次数
            {
                if (mCustomer0.get(i).getMobile().equals(mCustomer0.get(j).getMobile()))
                {
                    mCustomer.get(j).remark="重复号码";
                    mCustomer3.add(mCustomer.get(j));
                    mCustomer2.remove(j);
                }

            }
        }


    }

    @Override
    public void initContentView() {
        ivClose = getView(R.id.iv_close);
        tv_title = getView(R.id.tv_title);

        tvCust1 = getView(R.id.tvCust1);
        tvCust2 = getView(R.id.tvCust2);
        tvCust3 = getView(R.id.tvCust3);
        tvCust1.setText("全部"+"("+mCustomer.size()+")");
        tvCust2.setText("有效客户"+"("+mCustomer2.size()+")");
        tvCust3.setText("无效客户"+"("+mCustomer3.size()+")");
        tvCust1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isA){
                    isA=true;
                    tvCust1.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvCust1.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape_p));
                    isB=false;
                    tvCust2.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvCust2.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape1));
                    isC=false;
                    tvCust3.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvCust3.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape2));

                    mCardSlotAdapter.set(mCustomer);

                }

            }
        });

        tvCust2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isB){
                    isA=false;
                    tvCust1.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvCust1.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape));
                    isB=true;
                    tvCust2.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvCust2.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape1_p));
                    isC=false;
                    tvCust3.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvCust3.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape2));

                    mCardSlotAdapter.set(mCustomer2);

                }

            }
        });
        tvCust3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!isC){
                    isA=false;
                    tvCust1.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvCust1.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape));
                    isB=false;
                    tvCust2.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme11));
                    tvCust2.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape1));
                    isC=true;
                    tvCust3.setTextColor(mContext.getResources().getColor(R.color.common_app_text_theme12));
                    tvCust3.setBackground(mContext.getResources().getDrawable(R.drawable.create_task_dialog_tv_shape2_p));

                    mCardSlotAdapter.set(mCustomer3);

                }

            }
        });




        tv_cancel = getView(R.id.tv_cancel);
        tv_ok = getView(R.id.tv_ok);

        recycler_view = getView(R.id.recycler_view);

        mCardSlotAdapter = new CardSlotAdapter(mContext);

        CustomGridLayoutManager layoutManagerTime = new CustomGridLayoutManager(mContext, 1);
        layoutManagerTime.setScrollEnabled(true);
        recycler_view.setLayoutManager(layoutManagerTime);
        recycler_view.addItemDecoration(
                new GridSpacingItemDecoration(1, ViewUtil.dp2px(mContext, 0), ViewUtil.dp2px(mContext, 0),
                        true));
        recycler_view.setAdapter(mCardSlotAdapter);

        mCardSlotAdapter.set(mCustomer);


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
                if(mCardSlotDialogListener!=null){
                    mCardSlotDialogListener.onClick(mCustomer);
                }
                dismiss();
            }
        });


    }



}


