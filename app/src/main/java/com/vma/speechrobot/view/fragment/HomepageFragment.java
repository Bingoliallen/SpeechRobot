package com.vma.speechrobot.view.fragment;

import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintLayout.LayoutParams;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;

import com.example.common.BaseAppProfile;
import com.example.common.consts.CommonTimeConst;
import com.example.common.utils.Spanny;
import com.example.common.utils.ViewUtil;
import com.example.common.view.fragment.BaseFragment;
import com.example.common.widget.TitleBarView.OnClickListener;
import com.example.common.widget.pull2refresh.TaobaoHeader;
import com.github.abel533.echarts.data.Data;
import com.github.abel533.echarts.style.TextStyle;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.HomepageBean;
import com.vma.speechrobot.bean.StatisticsInfo;
import com.vma.speechrobot.bean.StatisticsInfoBean;
import com.vma.speechrobot.bean.StatisticsLevel;
import com.vma.speechrobot.event.MainMenuEvent;
import com.vma.speechrobot.presenter.HomepagePresenter;
import com.vma.speechrobot.presenter.impl.IHomepagePresenter.IView;
import com.vma.speechrobot.view.activity.CallRecordActivity;
import com.vma.speechrobot.view.activity.CreateTaskActivity;
import com.vma.speechrobot.view.activity.TaskManagerNewActivity;
import com.vma.speechrobot.widget.VerticalCenterImageSpan;
import com.vma.speechrobot.widget.echarts.EchartOptionUtil;
import com.vma.speechrobot.widget.echarts.EchartView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.greenrobot.eventbus.EventBus;

public class HomepageFragment extends BaseFragment<HomepagePresenter> implements IView {

    @BindView(R.id.tv_Time1)
    TextView tv_Time1;
    @BindView(R.id.tv_Time2)
    TextView tv_Time2;
    @BindView(R.id.tv_Time3)
    TextView tv_Time3;


    @BindView(R.id.tv_Time4)
    TextView tv_Time4;
    @BindView(R.id.tv_Time5)
    TextView tv_Time5;
    @BindView(R.id.tv_Time6)
    TextView tv_Time6;


    @BindView(R.id.lineChart1)
    EchartView lineChart1;

    @BindView(R.id.lineChart)
    EchartView lineChart;

/*  @BindView(R.id.mLiner1)
  LinearLayout mLiner1;*/

  //  @BindView(R.id.cl1)
   // ConstraintLayout mCl1;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @BindView(R.id.tv_num1)
    TextView tv_num1;
    @BindView(R.id.tv_num2)
    TextView tv_num2;
    @BindView(R.id.tv_num3)
    TextView tv_num3;
    @BindView(R.id.tv_num4)
    TextView tv_num4;
    @BindView(R.id.tv_num5)
    TextView tv_num5;
    @BindView(R.id.tv_num6)
    TextView tv_num6;
    @BindView(R.id.tv_num7)
    TextView tv_num7;
    @BindView(R.id.tv_num8)
    TextView tv_num8;
    @BindView(R.id.tv_num9)
    TextView tv_num9;
    @BindView(R.id.tv_num10)
    TextView tv_num10;
    @BindView(R.id.tv_num11)
    TextView tv_num11;


    @BindView(R.id.taskNum)
    TextView taskNum;
    @BindView(R.id.taskingNum)
    TextView taskingNum;
    @BindView(R.id.rebootNum)
    TextView rebootNum;
    @BindView(R.id.msgNum)
    TextView msgNum;

  //  @BindView(R.id.tv_call_sum)
  //  TextView tvCallSum;
  //  @BindView(R.id.tv_task_sum)
 //   TextView tvTaskSum;
  //  @BindView(R.id.tv_tasking_sum)
  //  TextView tvTaskingSum;
   // @BindView(R.id.tv_message_sum)
   // TextView tvMessageSum;
  //  @BindView(R.id.tv_robot_sum)
  //  TextView tvRobotSum;

    @BindView(R.id.tvSpeed1)
    TextView tvSpeed1;
    @BindView(R.id.tvSpeed2)
    TextView tvSpeed2;
    @BindView(R.id.tvSpeed3)
    TextView tvSpeed3;


    //  @BindView(R.id.pie_view)
//  PieChartView pieCharView;
//  @BindView(R.id.recycler_view_color)
//  RecyclerView recyclerViewColor;
//  private ColorAdapter mColorAdapter;
//  @BindView(R.id.pieChart)
  //  EchartView pieChartWebView;

    private HomepageBean mData;


    @Override
    protected int getContentViewId() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected HomepagePresenter createPresenter() {
        return new HomepagePresenter(this);
    }

    @Override
    public void onDestroy() {
        if (lineChart != null) {
            lineChart.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            lineChart.clearHistory();
          //  ((ViewGroup) lineChart.getParent()).removeView(lineChart);
            lineChart.destroy();
        }
        if (lineChart1 != null) {
            lineChart1.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            lineChart1.clearHistory();
          //  ((ViewGroup) lineChart1.getParent()).removeView(lineChart1);
            lineChart1.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void initView() {
        super.initView();
        tv_Time1.setSelected(true);
        tv_Time2.setSelected(false);
        tv_Time3.setSelected(false);
        tv_Time4.setSelected(true);
        tv_Time5.setSelected(false);
        tv_Time6.setSelected(false);
        refresh.setRefreshHeader(new TaobaoHeader(mContext));
//    mColorAdapter = new ColorAdapter(getContext());
//    recyclerViewColor.setLayoutManager(new GridLayoutManager(mContext, 3));
//    recyclerViewColor.setAdapter(mColorAdapter);

     /*   pieChartWebView = new EchartView(BaseAppProfile.getApplication());
        ConstraintLayout.LayoutParams lp = new LayoutParams(0, ViewUtil.dp2px(mContext, 275));
        lp.topMargin = ViewUtil.dp2px(mContext, 20);
        lp.leftMargin = ViewUtil.dp2px(mContext, 10);
        lp.rightMargin = ViewUtil.dp2px(mContext, 10);
        lp.leftToLeft = LayoutParams.PARENT_ID;
        lp.rightToRight = LayoutParams.PARENT_ID;
        lp.topToTop = R.id.view2;
        lp.bottomToBottom = LayoutParams.PARENT_ID;
        mCl1.addView(pieChartWebView, lp);*/
        //  mLiner1.removeAllViews();
        // mLiner1.addView(lineChart,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTitleBar.setOnClickListener(new OnClickListener() {
            @Override
            public void leftClick() {
                EventBus.getDefault().post(new MainMenuEvent());
            }

            @Override
            public void rightClick() {

            }
        });
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh((int) (10 * CommonTimeConst.S));
                tv_Time1.setSelected(true);
                tv_Time2.setSelected(false);
                tv_Time3.setSelected(false);
                tv_Time4.setSelected(true);
                tv_Time5.setSelected(false);
                tv_Time6.setSelected(false);
                mPresenter.getHomepage();
                mPresenter.getHomepageStatisticsInfo("1");
            }
        });

        tvSpeed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskManagerNewActivity.launch(getActivity());
            }
        });
        tvSpeed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallRecordActivity.launch(getActivity());
            }
        });
        tvSpeed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateTaskActivity.launch(getActivity());
            }
        });
        tv_Time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_Time1.setSelected(true);
                tv_Time2.setSelected(false);
                tv_Time3.setSelected(false);
                mPresenter.getHomepageStatisticsInfo("1");
            }
        });
        tv_Time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_Time1.setSelected(false);
                tv_Time2.setSelected(true);
                tv_Time3.setSelected(false);
                mPresenter.getHomepageStatisticsInfo("2");
            }
        });
        tv_Time3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_Time1.setSelected(false);
                tv_Time2.setSelected(false);
                tv_Time3.setSelected(true);
                mPresenter.getHomepageStatisticsInfo("3");
            }
        });


        tv_Time4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_Time4.setSelected(true);
                tv_Time5.setSelected(false);
                tv_Time6.setSelected(false);
                mPresenter.getHomepageStatisticsInfoLevel("1");
            }
        });

        tv_Time5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_Time4.setSelected(false);
                tv_Time5.setSelected(true);
                tv_Time6.setSelected(false);
                mPresenter.getHomepageStatisticsInfoLevel("2");
            }
        });

        tv_Time6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_Time4.setSelected(false);
                tv_Time5.setSelected(false);
                tv_Time6.setSelected(true);
                mPresenter.getHomepageStatisticsInfoLevel("3");
            }
        });



    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getHomepage();
        mPresenter.getHomepageStatisticsInfo("1");
        mPresenter.getHomepageStatisticsInfoLevel("1");
    }

    @Override
    public void fillHomepage(HomepageBean homepage) {
        if (refresh != null) {
            refresh.finishRefresh();
        }
        mData = homepage;
      //  refreshPieChart();


        Spanny callCountSpanny1 = new Spanny();
        callCountSpanny1.append(String.valueOf(homepage.task_total_num),
                new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.common_task)),
                new RelativeSizeSpan(1f),
                new StyleSpan(Typeface.NORMAL))
                .append("\n")
                .append("任务总数", new RelativeSizeSpan(0.3f));
        taskNum.setText(callCountSpanny1);

        Spanny callCountSpanny2 = new Spanny();
        callCountSpanny2.append(String.valueOf(homepage.task_num),
                new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.common_task)),
                new RelativeSizeSpan(1f),
                new StyleSpan(Typeface.NORMAL))
                .append("\n")
                .append("进行中任务", new RelativeSizeSpan(0.3f));
        taskingNum.setText(callCountSpanny2);


        Spanny callCountSpanny3 = new Spanny();
        callCountSpanny3.append(String.valueOf(homepage.robot_num),
                new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.common_task)),
                new RelativeSizeSpan(1f),
                new StyleSpan(Typeface.NORMAL))
                .append("\n")
                .append("外呼坐席数", new RelativeSizeSpan(0.3f));
        rebootNum.setText(callCountSpanny3);

        Spanny callCountSpanny4 = new Spanny();
        callCountSpanny4.append(String.valueOf(homepage.message_num),
                new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.common_task)),
                new RelativeSizeSpan(1f),
                new StyleSpan(Typeface.NORMAL))
                .append("\n")
                .append("短信数量", new RelativeSizeSpan(0.3f));
        msgNum.setText(callCountSpanny4);

        String callNums = String.valueOf(homepage.call_all_count);
        if (!TextUtils.isEmpty(callNums)) {

            int lenght = callNums.length();
            if (lenght == 1) {
                tv_num1.setText(callNums);

            } else if (lenght == 2) {
                tv_num2.setText(callNums.substring(0, 1));
                tv_num1.setText(callNums.substring(1, lenght));

            } else if (lenght == 3) {
                tv_num3.setText(callNums.substring(0, 1));
                tv_num2.setText(callNums.substring(1, 2));
                tv_num1.setText(callNums.substring(2, lenght));
            } else if (lenght == 4) {
                tv_num4.setText(callNums.substring(0, 1));
                tv_num3.setText(callNums.substring(1, 2));
                tv_num2.setText(callNums.substring(2, 3));
                tv_num1.setText(callNums.substring(3, lenght));
            } else if (lenght == 5) {
                tv_num5.setText(callNums.substring(0, 1));
                tv_num4.setText(callNums.substring(1, 2));
                tv_num3.setText(callNums.substring(2, 3));
                tv_num2.setText(callNums.substring(3, 4));
                tv_num1.setText(callNums.substring(4, lenght));
            } else if (lenght == 6) {
                tv_num6.setText(callNums.substring(0, 1));
                tv_num5.setText(callNums.substring(1, 2));
                tv_num4.setText(callNums.substring(2, 3));
                tv_num3.setText(callNums.substring(3, 4));
                tv_num2.setText(callNums.substring(4, 5));
                tv_num1.setText(callNums.substring(5, lenght));
            } else if (lenght == 7) {
                tv_num7.setText(callNums.substring(0, 1));
                tv_num6.setText(callNums.substring(1, 2));
                tv_num5.setText(callNums.substring(2, 3));
                tv_num4.setText(callNums.substring(3, 4));
                tv_num3.setText(callNums.substring(4, 5));
                tv_num2.setText(callNums.substring(5, 6));
                tv_num1.setText(callNums.substring(6, lenght));
            } else if (lenght == 8) {
                tv_num8.setText(callNums.substring(0, 1));
                tv_num7.setText(callNums.substring(1, 2));
                tv_num6.setText(callNums.substring(2, 3));
                tv_num5.setText(callNums.substring(3, 4));
                tv_num4.setText(callNums.substring(4, 5));
                tv_num3.setText(callNums.substring(5, 6));
                tv_num2.setText(callNums.substring(6, 7));
                tv_num1.setText(callNums.substring(7, lenght));
            } else if (lenght == 9) {
                tv_num9.setText(callNums.substring(0, 1));
                tv_num8.setText(callNums.substring(1, 2));
                tv_num7.setText(callNums.substring(2, 3));
                tv_num6.setText(callNums.substring(3, 4));
                tv_num5.setText(callNums.substring(4, 5));
                tv_num4.setText(callNums.substring(5, 6));
                tv_num3.setText(callNums.substring(6, 7));
                tv_num2.setText(callNums.substring(7, 8));
                tv_num1.setText(callNums.substring(8, lenght));
            } else if (lenght == 10) {
                tv_num10.setText(callNums.substring(0, 1));
                tv_num9.setText(callNums.substring(1, 2));
                tv_num8.setText(callNums.substring(2, 3));
                tv_num7.setText(callNums.substring(3, 4));
                tv_num6.setText(callNums.substring(4, 5));
                tv_num5.setText(callNums.substring(5, 6));
                tv_num4.setText(callNums.substring(6, 7));
                tv_num3.setText(callNums.substring(7, 8));
                tv_num2.setText(callNums.substring(8, 9));
                tv_num1.setText(callNums.substring(9, lenght));
            } else if (lenght == 11) {
                tv_num11.setText(callNums.substring(0, 1));
                tv_num10.setText(callNums.substring(1, 2));
                tv_num9.setText(callNums.substring(2, 3));
                tv_num8.setText(callNums.substring(3, 4));
                tv_num7.setText(callNums.substring(4, 5));
                tv_num6.setText(callNums.substring(5, 6));
                tv_num5.setText(callNums.substring(6, 7));
                tv_num4.setText(callNums.substring(7, 8));
                tv_num3.setText(callNums.substring(8, 9));
                tv_num2.setText(callNums.substring(9, 10));
                tv_num1.setText(callNums.substring(10, lenght));
            }

        }


     /*   Spanny callCountSpanny = new Spanny();
        callCountSpanny.append(String.valueOf(homepage.call_all_count),
                new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.common_app_text_theme1)),
                new RelativeSizeSpan(2f),
                new StyleSpan(Typeface.BOLD))
                .append("\n ", new RelativeSizeSpan(1.5f))
                .append("呼叫总数");
        tvCallSum.setText(callCountSpanny);

        Spanny taskSumSpanny = new Spanny();
        taskSumSpanny.append("   任务总数", new VerticalCenterImageSpan(mContext, R.drawable.home_1))
                .append("\n ", new RelativeSizeSpan(1f))
                .append(String.format("%d", homepage.task_total_num),
                        new StyleSpan(Typeface.BOLD),
                        new RelativeSizeSpan(1.6f));
        tvTaskSum.setText(taskSumSpanny);

        Spanny taskingSumSpanny = new Spanny();
        taskingSumSpanny.append("   进行中任务", new VerticalCenterImageSpan(mContext, R.drawable.home_2))
                .append("\n ", new RelativeSizeSpan(1f))
                .append(String.format("%d", homepage.task_num),
                        new StyleSpan(Typeface.BOLD),
                        new RelativeSizeSpan(1.6f));
        tvTaskingSum.setText(taskingSumSpanny);

        Spanny messageSumSpanny = new Spanny();
        messageSumSpanny.append("   剩余短信数量", new VerticalCenterImageSpan(mContext, R.drawable.home_3))
                .append("\n ", new RelativeSizeSpan(1f))
                .append(String.format("%d", homepage.message_num),
                        new StyleSpan(Typeface.BOLD),
                        new RelativeSizeSpan(1.6f));
        tvMessageSum.setText(messageSumSpanny);

        Spanny robotSumSpanny = new Spanny();
        robotSumSpanny.append("   机器坐席", new VerticalCenterImageSpan(mContext, R.drawable.home_4))
                .append("\n ", new RelativeSizeSpan(1f))
                .append(String.format("%d", homepage.robot_num),
                        new StyleSpan(Typeface.BOLD),
                        new RelativeSizeSpan(1.6f));
        tvRobotSum.setText(robotSumSpanny);*/

//    ArrayList<PieceDataHolder> data = new ArrayList();
//    double total =
//        homepage.level_A + homepage.level_B + homepage.level_C + homepage.level_D + homepage.level_E
//            + homepage.level_F;
//    data.add(new PieceDataHolder(homepage.level_A,
//        ContextCompat.getColor(mContext, R.color.common_app_levelA),
//        String.format("A级:%.2f%%|%d", homepage.level_A * 100 / total, homepage.level_A)));
//    data.add(new PieceDataHolder(homepage.level_B,
//        ContextCompat.getColor(mContext, R.color.common_app_levelB),
//        String.format("B级:%.2f%%|%d", homepage.level_B * 100 / total, homepage.level_B)));
//    data.add(new PieceDataHolder(homepage.level_C,
//        ContextCompat.getColor(mContext, R.color.common_app_levelC),
//        String.format("C级:%.2f%%|%d", homepage.level_C * 100 / total, homepage.level_C)));
//    data.add(new PieceDataHolder(homepage.level_D,
//        ContextCompat.getColor(mContext, R.color.common_app_levelD),
//        String.format("D级:%.2f%%|%d", homepage.level_D * 100 / total, homepage.level_D)));
//    data.add(new PieceDataHolder(homepage.level_E,
//        ContextCompat.getColor(mContext, R.color.common_app_levelE),
//        String.format("E级:%.2f%%|%d", homepage.level_E * 100 / total, homepage.level_E)));
//    data.add(new PieceDataHolder(homepage.level_F,
//        ContextCompat.getColor(mContext, R.color.common_app_levelF),
//        String.format("F级:%.2f%%|%d", homepage.level_F * 100 / total, homepage.level_F)));
//    pieCharView.setData(data);
//
//    List<ColorBean> colors = new ArrayList<>();
//    colors
//        .add(new ColorBean(String.format("A级:%.2f%%", homepage.level_A * 100 / total),
//            ContextCompat.getColor(mContext, R.color.common_app_levelA)));
//    colors
//        .add(new ColorBean(String.format("B级:%.2f%%", homepage.level_B * 100 / total),
//            ContextCompat.getColor(mContext, R.color.common_app_levelB)));
//    colors
//        .add(new ColorBean(String.format("C级:%.2f%%", homepage.level_C * 100 / total),
//            ContextCompat.getColor(mContext, R.color.common_app_levelC)));
//    colors
//        .add(new ColorBean(String.format("D级:%.2f%%", homepage.level_D * 100 / total),
//            ContextCompat.getColor(mContext, R.color.common_app_levelD)));
//    colors
//        .add(new ColorBean(String.format("E级:%.2f%%", homepage.level_E * 100 / total),
//            ContextCompat.getColor(mContext, R.color.common_app_levelE)));
//    colors
//        .add(new ColorBean(String.format("F级:%.2f%%", homepage.level_F * 100 / total),
//            ContextCompat.getColor(mContext, R.color.common_app_levelF)));
//    mColorAdapter.set(colors);
    }

/*
    private void refreshPieChart() {
        if (mData != null) {
            double total = mData.level_A + mData.level_B + mData.level_C + mData.level_D + mData.level_E
                    + mData.level_F;
            List<Data> list = new ArrayList<>();
            TextStyle levelATextStyle = new TextStyle();
            levelATextStyle.setColor("#3ec093");
            TextStyle levelBTextStyle = new TextStyle();
            levelBTextStyle.setColor("#f7ad60");
            TextStyle levelCTextStyle = new TextStyle();
            levelCTextStyle.setColor("#0091db");
            TextStyle levelDTextStyle = new TextStyle();
            levelDTextStyle.setColor("#3bc1c3");
            TextStyle levelETextStyle = new TextStyle();
            levelETextStyle.setColor("#ea5f5f");
            TextStyle levelFTextStyle = new TextStyle();
            levelFTextStyle.setColor("#a858ef");

            list.add(new Data(String.format("A级:%.2f%%", mData.level_A * 100 / total), mData.level_A)
                    .textStyle(levelATextStyle));
            list.add(new Data(String.format("B级:%.2f%%", mData.level_B * 100 / total), mData.level_B)
                    .textStyle(levelBTextStyle));
            list.add(new Data(String.format("C级:%.2f%%", mData.level_C * 100 / total), mData.level_C)
                    .textStyle(levelCTextStyle));
            list.add(new Data(String.format("D级:%.2f%%", mData.level_D * 100 / total), mData.level_D)
                    .textStyle(levelDTextStyle));
            list.add(new Data(String.format("E级:%.2f%%", mData.level_E * 100 / total), mData.level_E)
                    .textStyle(levelETextStyle));
            list.add(new Data(String.format("F级:%.2f%%", mData.level_F * 100 / total), mData.level_F)
                    .textStyle(levelFTextStyle));
            for (Data data : list) {
                data.setIcon("roundRect");
            }
            Data[] datas = new Data[list.size()];
            list.toArray(datas);
            pieChartWebView.refreshEchartsWithOption(EchartOptionUtil.getPieChartOpioons(datas));
            lineChart1.refreshEchartsWithOption(EchartOptionUtil.getPieChartOpioons(datas, "20%", "40%"));
        }
    }
*/

    private void refreshLineChart() {
        Object[] x = new Object[]{
                "", "", "", "", "", "", ""
        };

        Object[][] y4 = new Object[4][];
        Object[] y = new Object[]{
                0, 0, 0, 0, 0, 0, 0
        };
        y4[0] = y;

        Object[] y1 = new Object[]{
                0, 0, 0, 0, 0, 0, 0
        };
        y4[1] = y1;

        Object[] y2 = new Object[]{
                0, 0, 0, 0, 0, 0, 0
        };
        y4[2] = y2;

        Object[] y3 = new Object[]{
                0, 0, 0, 0, 0, 0, 0
        };
        y4[3] = y3;

        lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y4));
    }

    @Override
    public void fillHomepageStatisticsInfo(StatisticsInfoBean homepage) {
        if (refresh != null) {
            refresh.finishRefresh();
        }
        if (homepage != null && homepage.statistice_list != null && homepage.statistice_list.size() != 0) {
            Object[] x = new Object[homepage.statistice_list.size()];
            Object[][] y4 = new Object[4][];
            Object[] y = new Object[homepage.statistice_list.size()];
            Object[] y1 = new Object[homepage.statistice_list.size()];
            Object[] y2 = new Object[homepage.statistice_list.size()];
            Object[] y3 = new Object[homepage.statistice_list.size()];
            for (int i = 0; i < homepage.statistice_list.size(); i++) {
                StatisticsInfo mStat = homepage.statistice_list.get(i);
                if (mStat != null) {
                    x[i] = mStat.sdate;

                    y[i] = mStat.call_all_count;
                    y1[i] =mStat.call_success;
                    y2[i] =mStat.call_fail;
                    y3[i] =mStat.call_refuse;
                }
            }

            y4[0] = y;
            y4[1] = y1;
            y4[2] = y2;
            y4[3] = y3;
            lineChart.refreshEchartsWithOption(EchartOptionUtil.getLineChartOptions(x, y4));
        } else {
            refreshLineChart();
        }

    }

    private String change(int count){
        String det=null;
        if(count>10000){
            det=div(count,1000,0)+"W";
        }else if(count>1000){
            det=div(count,1000,0)+"K";
        }else{
            det=""+count;
        }
        return det;
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            return 0;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }




    @Override
    public void fillHomepageStatisticsInfoLevel(StatisticsLevel homepage) {
        if (refresh != null) {
            refresh.finishRefresh();
        }

        if (homepage != null) {
            double total = homepage.level_a + homepage.level_b + homepage.level_c + homepage.level_d + homepage.level_e
                    + homepage.level_f;
            List<Data> list = new ArrayList<>();
            TextStyle levelATextStyle = new TextStyle();
            levelATextStyle.setColor("#3ec093");
            TextStyle levelBTextStyle = new TextStyle();
            levelBTextStyle.setColor("#f7ad60");
            TextStyle levelCTextStyle = new TextStyle();
            levelCTextStyle.setColor("#0091db");
            TextStyle levelDTextStyle = new TextStyle();
            levelDTextStyle.setColor("#3bc1c3");
            TextStyle levelETextStyle = new TextStyle();
            levelETextStyle.setColor("#ea5f5f");
            TextStyle levelFTextStyle = new TextStyle();
            levelFTextStyle.setColor("#a858ef");

            list.add(new Data(String.format("A级:%.2f%%", homepage.level_a * 100 / total), homepage.level_a)
                    .textStyle(levelATextStyle));
            list.add(new Data(String.format("B级:%.2f%%", homepage.level_b * 100 / total), homepage.level_b)
                    .textStyle(levelBTextStyle));
            list.add(new Data(String.format("C级:%.2f%%", homepage.level_c * 100 / total), homepage.level_c)
                    .textStyle(levelCTextStyle));
            list.add(new Data(String.format("D级:%.2f%%", homepage.level_d * 100 / total), homepage.level_d)
                    .textStyle(levelDTextStyle));
            list.add(new Data(String.format("E级:%.2f%%", homepage.level_e * 100 / total), homepage.level_e)
                    .textStyle(levelETextStyle));
            list.add(new Data(String.format("F级:%.2f%%", homepage.level_f * 100 / total), homepage.level_f)
                    .textStyle(levelFTextStyle));
            for (Data data : list) {
                data.setIcon("roundRect");
            }
            Data[] datas = new Data[list.size()];
            list.toArray(datas);
          //  pieChartWebView.refreshEchartsWithOption(EchartOptionUtil.getPieChartOpioons(datas));
            lineChart1.refreshEchartsWithOption(EchartOptionUtil.getPieChartOpioons(datas, "20%", "40%"));
        }


    }

}
