package com.vma.speechrobot.widget;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.vma.speechrobot.R;


public class MorePopWindow extends PopupWindow {
    public interface MorePopWindowListener {
        void onClick0(int id);
        void onClick1(int id);

        void onClick2(int id);

        void onClick3(int id);
    }

   private MorePopWindowListener mMorePopWindowListener;
    private int mid;
    private int status;
    public void setmMorePopWindowListener(MorePopWindowListener mMorePopWindowListener) {
        this.mMorePopWindowListener = mMorePopWindowListener;
    }

    @SuppressLint("InflateParams")
    public MorePopWindow(final Activity context,int id,int status1) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.popupwindow_add, null);
        mid =id;
        status =status1;
        // 设置SelectPicPopupWindow的View
        this.setContentView(content);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

        RelativeLayout re_chatroom0 = (RelativeLayout) content.findViewById(R.id.re_chatroom0);
        RelativeLayout re_chatroom = (RelativeLayout) content.findViewById(R.id.re_chatroom);
        RelativeLayout re_addfriends = (RelativeLayout) content.findViewById(R.id.re_addfriends);

        RelativeLayout re_scanner = (RelativeLayout) content.findViewById(R.id.re_scanner);
        //0未开始 1进行中 2暂停 3终止 4完成 ,
        if(status==0){
            re_chatroom.setVisibility(View.VISIBLE);//暂停
            re_addfriends.setVisibility(View.VISIBLE);//终止
            re_scanner.setVisibility(View.VISIBLE);//删除
        }else  if(status==1){
            re_chatroom.setVisibility(View.VISIBLE);//暂停
            re_addfriends.setVisibility(View.VISIBLE);//终止
        }else  if(status==2){
            re_chatroom0.setVisibility(View.VISIBLE);//开始
            re_addfriends.setVisibility(View.VISIBLE);//终止
            re_scanner.setVisibility(View.VISIBLE);//删除

        }else  if(status==3){
            re_scanner.setVisibility(View.VISIBLE);//删除
        }else  if(status==4){
            re_scanner.setVisibility(View.VISIBLE);//删除
        }
        re_chatroom0.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if(mMorePopWindowListener!=null){
                    mMorePopWindowListener.onClick0(mid);
                }
                MorePopWindow.this.dismiss();


            }

        });


        re_addfriends.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if(mMorePopWindowListener!=null){
                    mMorePopWindowListener.onClick2(mid);
                }
                MorePopWindow.this.dismiss();


            }

        });
        re_chatroom.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                if(mMorePopWindowListener!=null){
                    mMorePopWindowListener.onClick1(mid);
                }
                MorePopWindow.this.dismiss();

            }

        });
        re_scanner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMorePopWindowListener!=null){
                    mMorePopWindowListener.onClick3(mid);
                }
                MorePopWindow.this.dismiss();
            }
        });


    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }
}
