package com.vma.speechrobot.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.adapter.recycler.ViewHolder;
import com.example.common.consts.CommonTimeConst;
import com.example.common.utils.TimeUtil;
import com.example.common.utils.ValueUtil;
import com.example.common.utils.ViewUtil;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.CallRecordBean;
import com.vma.speechrobot.enums.CallResult;
import com.vma.speechrobot.view.activity.CallDetailNewActivity;

import java.util.concurrent.TimeUnit;

/**
 * Date: 2018/12/27
 * Author: libaibing
 * Email：
 * Des：
 */

public class CallRecordNewAdapter  extends BaseRecyclerAdapter<CallRecordBean> {

    public CallRecordNewAdapter(Context context) {
        super(context);
        putItemLayoutId(R.layout.recycler_item_call_record_new);
       /* setOnRecyclerItemClickListener(new OnRecyclerItemClickListener<CallRecordBean>() {
            @Override
            public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, CallRecordBean data,
                                              int position) {
               CallDetailNewActivity.launch(mContext, data.id);
            }
        });*/
    }

    @Override
    public void onBind(ViewHolder holder, CallRecordBean item, int position) {

        LinearLayout mLinerItem = holder.getView(R.id.mLinerItem);
        if(item.is_read==1){
            mLinerItem.setBackground(
                    ContextCompat.getDrawable(mContext, R.drawable.callrecord_item_shape_p));
        }else{
            mLinerItem.setBackground(
                    ContextCompat.getDrawable(mContext, R.drawable.callrecord_item_shape));
        }
        holder.setText(R.id.tv_call_id, String.format("ID：%s", item.id));
        holder.setText(R.id.tv_mobile, String.format("%s(%s)", item.mobile, item.user_name));
        holder.setText(R.id.tv_create,
                String.format("%s | %s", item.status == 0 ? "未分配" : "已分配", String.format("%s", TimeUtil
                        .long2Str(Long.parseLong(ValueUtil.null2Value(item.call_start_time, "0")), String
                                .format("%s-%s-%s %s:%s", CommonTimeConst.YEAR, CommonTimeConst.MONTH,
                                        CommonTimeConst.DAY, CommonTimeConst.HOUR, CommonTimeConst.MINUTE)))));
        TextView tvResult = holder.getView(R.id.tv_result);
        switch (item.call_result) {
            case CallResult.CALL_START:
                tvResult.setText("开始呼叫");
                tvResult.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme5));
                break;
            case CallResult.CONNECT:
                tvResult.setText("已接通");
                tvResult.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme5));
                break;
            case CallResult.NOT_CONNECT:
                tvResult.setText("无法接通");
                tvResult.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme10));
                break;
            case CallResult.REFUSE:
                tvResult.setText("拒接");
                tvResult.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme10));
                break;
            case CallResult.CALL_FAILED:
                tvResult.setText("外呼失败");
                tvResult.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme10));
                break;
            case 5:
                tvResult.setText("白名单过滤");
                tvResult.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme10));
                break;
            case 6:
                tvResult.setText("D类客户过滤");
                tvResult.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme10));
                break;
            case 7:
                tvResult.setText("频率限制");
                tvResult.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme10));
                break;
            case 8:
                tvResult.setText("空号");
                tvResult.setTextColor(ContextCompat.getColor(mContext, R.color.common_app_text_theme10));
                break;
        }
        TextView tvLevel = holder.getView(R.id.tv_level);
        TextView tvCount = holder.getView(R.id.tv_count);
        TextView tvTime = holder.getView(R.id.tv_time);
       // TextView tvCallNum = holder.getView(R.id.tv_call_num);
        if (item.call_result == CallResult.CALL_START) {
            tvLevel.setVisibility(View.GONE);
            tvCount.setVisibility(View.GONE);
            tvTime.setVisibility(View.GONE);
           // tvCallNum.setVisibility(View.GONE);
        } else {
            tvLevel.setVisibility(View.VISIBLE);
            tvCount.setVisibility(View.VISIBLE);
            tvTime.setVisibility(View.VISIBLE);
           // tvCallNum.setVisibility(View.VISIBLE);
            tvLevel.setText(String.format("%s级", item.user_level));
           /* GradientDrawable drawable = new GradientDrawable();
            drawable.setSize(tvLevel.getWidth(), tvLevel.getHeight());
            drawable.setCornerRadius(ViewUtil.dp2px(mContext, 5));*/
            if (!TextUtils.isEmpty(item.user_level)) {
                switch (item.user_level) {
                    case "A":
                        tvLevel.setBackground(ContextCompat.getDrawable(mContext, R.drawable.call_record_item_tv_shape_a));
                        break;
                    case "B":
                        tvLevel.setBackground(ContextCompat.getDrawable(mContext, R.drawable.call_record_item_tv_shape_b));
                        break;
                    case "C":
                        tvLevel.setBackground(ContextCompat.getDrawable(mContext, R.drawable.call_record_item_tv_shape_c));
                        break;
                    case "D":
                        tvLevel.setBackground(ContextCompat.getDrawable(mContext, R.drawable.call_record_item_tv_shape_d));
                        break;
                    case "E":
                        tvLevel.setBackground(ContextCompat.getDrawable(mContext, R.drawable.call_record_item_tv_shape_e));
                        break;
                    case "F":
                        tvLevel.setBackground(ContextCompat.getDrawable(mContext, R.drawable.call_record_item_tv_shape_f));
                        break;
                }
               // tvLevel.setBackground(drawable);
            }
            tvCount.setText(String.format("%d轮", item.call_count));
            long callTime = item.call_all_time * CommonTimeConst.S;
            tvTime.setText(String.format("%02d分%02d秒", TimeUnit.MILLISECONDS.toMinutes(callTime),
                            TimeUnit.MILLISECONDS.toSeconds(callTime) - TimeUnit.MINUTES
                                    .toSeconds(TimeUnit.MILLISECONDS.toMinutes(callTime))));
           // tvCallNum.setText(String.valueOf(item.call_num));
        }
    }
}
