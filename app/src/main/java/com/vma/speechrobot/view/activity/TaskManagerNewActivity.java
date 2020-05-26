package com.vma.speechrobot.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.consts.CommonTimeConst;
import com.example.common.utils.BarUtil;
import com.example.common.utils.ViewUtil;
import com.example.common.utils.init.T;
import com.example.common.view.activity.BaseActivity;
import com.example.common.widget.pull2refresh.TaobaoHeader;
import com.example.common.widget.viewpager_indicator.ViewPagerIndicator;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.vma.speechrobot.R;
import com.vma.speechrobot.adapter.TaskManagerAdapter;
import com.vma.speechrobot.adapter.viewpager.TaskPagerNewAdapter;
import com.vma.speechrobot.bean.CreateTask;
import com.vma.speechrobot.bean.KeyValueBean;
import com.vma.speechrobot.bean.PageBean;
import com.vma.speechrobot.bean.ReqUpdateTask;
import com.vma.speechrobot.bean.TaskBean;
import com.vma.speechrobot.bean.TaskCardOption;
import com.vma.speechrobot.bean.WhisperingOption;
import com.vma.speechrobot.presenter.TaskManagerPresenter;
import com.vma.speechrobot.presenter.impl.ITaskManagerPresenter;
import com.vma.speechrobot.view.fragment.TaskManagerInfoFragment;
import com.vma.speechrobot.view.fragment.TaskManagerRecordFragment;
import com.vma.speechrobot.widget.MorePopWindow;
import com.vma.speechrobot.widget.SpinerPopWindow;
import com.vma.speechrobot.widget.dialog.WarningDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import retrofit2.Response;

public class TaskManagerNewActivity extends BaseActivity<TaskManagerPresenter> implements
        ITaskManagerPresenter.IView, TaskManagerAdapter.onRecyclerItemClickedListener {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.tvfocus)
    TextView tvfocus;


    @BindView(R.id.mTask1)
    TextView mTask1;

    @BindView(R.id.iv_delete1)
    ImageView iv_delete1;
    @BindView(R.id.iv_delete2)
    ImageView iv_delete2;

    @BindView(R.id.mTask21_)
    TextView mTask21_;
    @BindView(R.id.mTask2_)
    TextView mTask2_;

    @BindView(R.id.mTask21)
    LinearLayout mTask21;
    @BindView(R.id.mTask2)
    LinearLayout mTask2;



    @BindView(R.id.mTask3)
    TextView mTask3;
    @BindView(R.id.mTask4)
    TextView mTask4;
    @BindView(R.id.mTask5)
    EditText mTask5;

    @BindView(R.id.mTask6)
    TextView mTask6;

    @BindView(R.id.mTask7)
    TextView mTask7;

    @BindView(R.id.mTask8)
    EditText mTask8;
    @BindView(R.id.mTask9)
    TextView mTask9;

    @BindView(R.id.iv_dingye)
    ImageView iv_dingye;
    @BindView(R.id.iv_shangyiye)
    ImageView iv_shangyiye;
    @BindView(R.id.iv_xiayiye)
    ImageView iv_xiayiye;
    @BindView(R.id.iv_diye)
    ImageView iv_diye;

    @BindView(R.id.mRight)
    LinearLayout mRight;


    @BindView(R.id.recycler_view)
    RecyclerView recyclerViewTask;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private TaskManagerAdapter mTaskManagerAdapter;
    // private List<TaskBean> mData=new ArrayList<>();

    @BindView(R.id.indicator_task)
    ViewPagerIndicator indicatorTask;
    @BindView(R.id.view_pager_task)
    ViewPager viewPagerTask;

    private TaskPagerNewAdapter mTaskPagerNewAdapter;
    private int mId = -1;

    private int mPageNum = 1;
    private int mPageSize = 10;

    private Integer status = null;
    private String create_time_begin = null;
    private String create_time_end = null;
    private String card_relate_id = null;
    private String whispering_id = null;
    private String mName = null;


    private SpinerPopWindow mSpinerPopWindowStatus;
    private SpinerPopWindow mSpinerPopWindowTime;
    private SpinerPopWindow mSpinerPopWindowLevel;
    private SpinerPopWindow mSpinerPopWindowCount;
    private List<Fragment> mFragments = new ArrayList<>();

    public static void launch(Context context) {
        Intent intent = new Intent(context, TaskManagerNewActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_task_manager_new;
    }

    @Override
    protected TaskManagerPresenter createPresenter() {
        return new TaskManagerPresenter(this);
    }


    @Override
    protected void beforeSetContentView() {
        super.beforeSetContentView();
        BarUtil.fullScreen(this);
        BarUtil.hideActionBar(this);
    }


    @Override
    protected void initView() {
        super.initView();
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH) + 1;
        mDay = c.get(Calendar.DAY_OF_MONTH);

        mYear1 = c.get(Calendar.YEAR);
        mMonth1 = c.get(Calendar.MONTH) + 1;
        mDay1 = c.get(Calendar.DAY_OF_MONTH);

        fragment1.setmTaskManagerInfoFragmentListener(new TaskManagerInfoFragment.TaskManagerInfoFragmentListener() {
            @Override
            public void fillTaskInfoF() {
                mRight.setVisibility(View.INVISIBLE);
            }

            @Override
            public void fillTaskInfoS() {
                mRight.setVisibility(View.VISIBLE);
            }
        });

        tvfocus.requestFocus();
        tv_title.setText("任务管理");
        tv_right.setText("创建任务");
        tv_right.setVisibility(View.VISIBLE);

        List<KeyValueBean<Integer, String>> listLevel = new ArrayList<>();
        listLevel.add(new KeyValueBean(null, "全部"));
        //0 1进行中 2暂停 3终止 4完成
        listLevel.add(new KeyValueBean(new Integer(0), "未开始"));
        listLevel.add(new KeyValueBean(new Integer(1), "进行中"));
        listLevel.add(new KeyValueBean(new Integer(2), "暂停"));
        listLevel.add(new KeyValueBean(new Integer(3), "终止"));
        listLevel.add(new KeyValueBean(new Integer(4), "完成"));
        mSpinerPopWindowStatus = new SpinerPopWindow<>(this, listLevel,
                new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<Integer, String>>() {

                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                      KeyValueBean<Integer, String> data, int position) {
                        status = data.key;
                        if (data.key == null) {
                            mTask1.setText(null);
                        } else {
                            mTask1.setText(data.value);
                        }
                        mName = null;
                        mPageNum = 1;
                        mTask8.setText("" + mPageNum);
                        mTask8.setSelection(mTask8.getText().length());
                        refresh();
                        mSpinerPopWindowStatus.dismiss();
                    }
                });

        List<KeyValueBean<String, String>> listStatus = new ArrayList<>();
        listStatus.add(new KeyValueBean(null, "全部"));
        mSpinerPopWindowLevel = new SpinerPopWindow<>(this, listStatus,
                new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<String, String>>() {
                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                      KeyValueBean<String, String> data, int position) {
                        card_relate_id = data.key;
                        if (data.key == null) {
                            mTask3.setText(null);
                        } else {
                            mTask3.setText(data.value);
                        }
                        mName = null;
                        mPageNum = 1;
                        mTask8.setText("" + mPageNum);
                        mTask8.setSelection(mTask8.getText().length());
                        refresh();
                        mSpinerPopWindowLevel.dismiss();
                    }
                });


        List<KeyValueBean<String, String>> listCount = new ArrayList<>();
        listCount.add(new KeyValueBean(null, "全部"));
        mSpinerPopWindowCount = new SpinerPopWindow<>(this, listCount,
                new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<String, String>>() {
                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                      KeyValueBean<String, String> data, int position) {
                        whispering_id = data.key;
                        if (data.key == null) {
                            mTask4.setText(null);
                        } else {
                            mTask4.setText(data.value);
                        }
                        mName = null;
                        mPageNum = 1;
                        mTask8.setText("" + mPageNum);
                        mTask8.setSelection(mTask8.getText().length());
                        refresh();
                        mSpinerPopWindowCount.dismiss();
                    }
                });

        mFragments.clear();
        Bundle args = new Bundle();
        args.putInt("id", mId);
        fragment1.setArguments(args);
        mFragments.add(fragment1);

        Bundle args2 = new Bundle();
        args2.putInt("id", mId);
        fragment2.setArguments(args2);
        mFragments.add(fragment2);

        mTaskPagerNewAdapter = new TaskPagerNewAdapter(getSupportFragmentManager(), mFragments);
        List<String> titles = new ArrayList<>();
        titles.add("基础信息");
        titles.add("通话记录");
        indicatorTask.addTitles(titles);
        indicatorTask.setOnDrawRuleListener(new BarDrawRuleListener());
        indicatorTask.setViewPager(viewPagerTask, 0);
        viewPagerTask.setAdapter(mTaskPagerNewAdapter);

        mTaskManagerAdapter = new TaskManagerAdapter(this);
        mTaskManagerAdapter.setmMorePopWindowListener(new MorePopWindow.MorePopWindowListener() {
            @Override
            public void onClick0(final int id) {
                WarningDialog mWarningDialog = new WarningDialog(TaskManagerNewActivity.this);
                mWarningDialog.setTitle("温馨提示");
                mWarningDialog.setText("确定开始任务吗?");
                mWarningDialog.setmWarningDialogListener(new WarningDialog.WarningDialogListener() {
                    @Override
                    public void onClick() {
                        mPresenter.updatetask(new ReqUpdateTask(id, 1));
                    }
                });
                mWarningDialog.show();
            }

            @Override
            public void onClick1(final int id) {
                WarningDialog mWarningDialog = new WarningDialog(TaskManagerNewActivity.this);
                mWarningDialog.setTitle("温馨提示");
                mWarningDialog.setText("确定暂停任务吗?");
                mWarningDialog.setmWarningDialogListener(new WarningDialog.WarningDialogListener() {
                    @Override
                    public void onClick() {
                        mPresenter.updatetask(new ReqUpdateTask(id, 2));
                    }
                });
                mWarningDialog.show();
            }

            @Override
            public void onClick2(final int id) {
                WarningDialog mWarningDialog = new WarningDialog(TaskManagerNewActivity.this);
                mWarningDialog.setTitle("温馨提示");
                mWarningDialog.setText("确定终止任务吗?");
                mWarningDialog.setmWarningDialogListener(new WarningDialog.WarningDialogListener() {
                    @Override
                    public void onClick() {
                        mPresenter.updatetask(new ReqUpdateTask(id, 3));
                    }
                });
                mWarningDialog.show();
            }

            @Override
            public void onClick3(final int id) {
                WarningDialog mWarningDialog = new WarningDialog(TaskManagerNewActivity.this);
                mWarningDialog.setTitle("温馨提示");
                mWarningDialog.setText("确定删除任务吗?");
                mWarningDialog.setmWarningDialogListener(new WarningDialog.WarningDialogListener() {
                    @Override
                    public void onClick() {
                        mPresenter.deletetask(id);
                    }
                });
                mWarningDialog.show();
            }
        });
        mTaskManagerAdapter.setMonRecyclerItemClickedListener(this);
        recyclerViewTask
                .setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewTask.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ViewUtil.dp2px(TaskManagerNewActivity.this, 0);
            }
        });
        recyclerViewTask.setAdapter(mTaskManagerAdapter);
        refresh.setRefreshHeader(new TaobaoHeader(this));
        refresh.setRefreshFooter(new FalsifyFooter(this));
        // mTaskManagerAdapter.set(mData);
        mTask1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusClick();
            }
        });
        mTask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDatePickerDialog1();

            }
        });
        mTask21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDatePickerDialog2();
            }
        });

        mTask3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levelClick();
            }
        });
        mTask4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countClick();
            }
        });
        mTask6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mName = mTask5.getText().toString();
                mPageNum = 1;
                mTask8.setText("" + mPageNum);
                mTask8.setSelection(mTask8.getText().length());
                refresh();
            }
        });
        iv_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTask2_.setText(null);
                create_time_begin=null;
            }
        });
        iv_delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTask21_.setText(null);
                create_time_end=null;
            }
        });

    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTask8.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    mPageNum = Integer.valueOf(mTask8.getText().toString().trim());
                    mPresenter.getTaskManangerList(status,
                            card_relate_id, whispering_id, create_time_begin, create_time_end, mName, mPageNum, mPageSize);

                }
                return false;
            }
        });
        mTask8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 这部分是处理如果输入框内小数点后有俩位，那么舍弃最后一位赋值，光标移动到最后
                if (s.toString().startsWith("0")) {
                    mTask8.setText("1");
                    mTask8.setSelection(mTask8.getText().length());
                }

                // 这里处理用户 多次输入.的处理
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mTask8.setText(s.subSequence(0, 1));
                        mTask8.setSelection(1);
                        return;
                    }
                }
                if (s.toString().trim().length() > 0) {
                    int mPageNum1 = Integer.valueOf(s.toString().trim());
                    if (mPageNum1 > totalPages) {
                        if (s.toString().trim().length() > 1) {
                            mTask8.setText(s.subSequence(0, s.toString().trim().length() - 1));
                            mTask8.setSelection(mTask8.getText().length());
                        } else {
                            mTask8.setText("" + totalPages);
                            mTask8.setSelection(mTask8.getText().length());
                        }
                        Toast.makeText(TaskManagerNewActivity.this, "最多只有" + totalPages + "页", Toast.LENGTH_LONG).show();
                    }

                }


            }
        });
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateTaskActivity.launch(TaskManagerNewActivity.this);
            }
        });

        refresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore((int) (0));

            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh((int) (10 * CommonTimeConst.S));


                mPageNum = 1;
                mTask8.setText("" + mPageNum);
                mTask8.setSelection(mTask8.getText().length());

              /*  mName = null;
                status = null;
                create_time_begin = null;
                create_time_end = null;
                card_relate_id = null;
                whispering_id = null;*/


                mPresenter.getTaskManangerList(status,
                        card_relate_id, whispering_id, create_time_begin, create_time_end, mName, mPageNum, mPageSize);
            }
        });

        iv_dingye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageNum = 1;
                mTask8.setText("" + mPageNum);
                mTask8.setSelection(mTask8.getText().length());
                mPresenter.getTaskManangerList(status,
                        card_relate_id, whispering_id, create_time_begin, create_time_end, mName, mPageNum, mPageSize);
            }
        });

        iv_shangyiye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageNum--;
                if (mPageNum < 1) {
                    mPageNum = 1;
                }
                mTask8.setText("" + mPageNum);
                mTask8.setSelection(mTask8.getText().length());
                mPresenter.getTaskManangerList(status,
                        card_relate_id, whispering_id, create_time_begin, create_time_end, mName, mPageNum, mPageSize);
            }
        });

        iv_xiayiye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageNum++;
                if (mPageNum > totalPages) {
                    mPageNum = totalPages;
                }
                mTask8.setText("" + mPageNum);
                mTask8.setSelection(mTask8.getText().length());
                mPresenter.getTaskManangerList(status,
                        card_relate_id, whispering_id, create_time_begin, create_time_end, mName, mPageNum, mPageSize);
            }
        });


        iv_diye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageNum = totalPages;
                mTask8.setText("" + mPageNum);
                mTask8.setSelection(mTask8.getText().length());
                mPresenter.getTaskManangerList(status,
                        card_relate_id, whispering_id, create_time_begin, create_time_end, mName, mPageNum, mPageSize);
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mName = null;
        mPresenter.getTaskCardOption();
        mPresenter.getWhisperingOption();
        refresh();
    }

    private void refresh() {

        mPresenter.getTaskManangerList(status,
                card_relate_id, whispering_id, create_time_begin, create_time_end, mName, mPageNum, mPageSize);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);

        super.onDestroy();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void fillTaskList(PageBean<TaskBean> data) {

    }

    private int totalPages = 1;

    @Override
    public void fillTaskManangerList(PageBean<TaskBean> data) {
        if (refresh != null) {
            refresh.finishLoadmore();
            refresh.finishRefresh();
        }
        if (data != null) {
            mTask7.setText("共" + data.total_num + "条");
            int pa = data.total_num % data.page_size;
            if (pa > 0) {
                totalPages = (data.total_num / data.page_size + 1);
                mTask9.setText("共" + (data.total_num / data.page_size + 1) + "页");
            } else {
                totalPages = (data.total_num / data.page_size + 0);
                mTask9.setText("共" + data.total_num / data.page_size + "页");
            }
            if(totalPages<=0){
                totalPages=1;
            }

        } else {
            mTask7.setText("共" + "0条");
            mTask9.setText("共" + "0页");
        }
        if (data != null && data.data_list != null && data.data_list.size() != 0) {

            // mTaskManagerAdapter.setWithPaging(mPageNum, mPageSize, data.data_list);
            mTaskManagerAdapter.set(data.data_list);
            mTaskManagerAdapter.getData().get(0).isSelect = true;
            mId = mTaskManagerAdapter.getData().get(0).id;
            mTaskManagerAdapter.notifyDataSetChanged();
            mRight.setVisibility(View.VISIBLE);
            setFragmentRight();

           /* if (data.data_list.size() == mPageSize) {
              //  mPageNum++;
            }*/
        } else {
            mTaskManagerAdapter.set(new ArrayList<TaskBean>());
            mRight.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void fillTaskCardOption(TaskCardOption[] data) {
        if (data != null && data.length > 0) {
            List<KeyValueBean<String, String>> listStatus = new ArrayList<>();
            listStatus.add(new KeyValueBean(null, "全部"));
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    listStatus.add(new KeyValueBean("" + data[i].id, data[i].machine_name + "(" + data[i].mobile + ")"));
                }

            }
            mSpinerPopWindowLevel = new SpinerPopWindow<>(this, listStatus,
                    new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<String, String>>() {
                        @Override
                        public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                          KeyValueBean<String, String> data, int position) {
                            card_relate_id = data.key;
                            if (data.key == null) {
                                mTask3.setText(null);
                            } else {
                                mTask3.setText(data.value);
                            }
                            mName = null;
                            mPageNum = 1;
                            mTask8.setText("" + mPageNum);
                            mTask8.setSelection(mTask8.getText().length());
                            refresh();
                            mSpinerPopWindowLevel.dismiss();
                        }
                    });


        }
    }

    @Override
    public void fillWhisperingOption(WhisperingOption[] data) {
        if (data != null && data.length > 0) {

            List<KeyValueBean<String, String>> listCount = new ArrayList<>();
            listCount.add(new KeyValueBean(null, "全部"));

            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    listCount.add(new KeyValueBean("" + data[i].id, data[i].title));
                }

            }

            mSpinerPopWindowCount = new SpinerPopWindow<>(this, listCount,
                    new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<String, String>>() {
                        @Override
                        public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                          KeyValueBean<String, String> data, int position) {
                            whispering_id = data.key;
                            if (data.key == null) {
                                mTask4.setText(null);
                            } else {
                                mTask4.setText(data.value);
                            }
                            mName = null;
                            mPageNum = 1;
                            mTask8.setText("" + mPageNum);
                            mTask8.setSelection(mTask8.getText().length());
                            refresh();
                            mSpinerPopWindowCount.dismiss();
                        }
                    });
        }
    }

    @Override
    public void fillupdatetask() {
        T.showShort("任务修改成功");
        mName = null;
        refresh();
    }

    @Override
    public void filldeletetask() {
        T.showShort("任务删除成功");
        mName = null;
        refresh();
    }

    @Override
    public void onRecyclerItemClicked(TaskBean data, int position) {
        for (int i = 0; i < mTaskManagerAdapter.getData().size(); i++) {
            mTaskManagerAdapter.getData().get(i).isSelect = false;
        }
        mTaskManagerAdapter.getData().get(position).isSelect = true;
        mId = mTaskManagerAdapter.getData().get(position).id;
        mTaskManagerAdapter.notifyDataSetChanged();
        setFragmentRight();
    }

    private class BarDrawRuleListener implements ViewPagerIndicator.OnDrawRuleListener {

        @Override
        public void onDrawRule(Context context, Path path, int tabWidth) {
            int width = ViewUtil.dp2px(context, 16);
            int height = ViewUtil.dp2px(context, 0.5f);
            path.moveTo(-width / 2, 0);
            path.lineTo(width / 2, 0);
            path.lineTo(width / 2, -height);
            path.lineTo(-width / 2, -height);
            path.close();
        }
    }

    private void statusClick() {
        mSpinerPopWindowStatus.setWidth(mTask1.getWidth());

        mSpinerPopWindowStatus.showAsDropDown(mTask1);
    }

    private void levelClick() {
        mSpinerPopWindowLevel.setWidth((mTask3.getWidth()/5)*9);
        mSpinerPopWindowLevel.setHeight(2 * mTask3.getWidth());
        mSpinerPopWindowLevel.showAsDropDown(mTask3);
    }

    private void countClick() {
        mSpinerPopWindowCount.setWidth((mTask4.getWidth()/2)*3);
        mSpinerPopWindowCount.setHeight(2 * mTask4.getWidth());
        mSpinerPopWindowCount.showAsDropDown(mTask4);
    }

    Calendar c;
    int mYear = 0, mMonth = 0, mDay = 0;
    //String dateInString;
    int mYear1= 0, mMonth1= 0, mDay1= 0;
    //String dateInString1;
    /**
     * 年-月-日 显示格式
     */
    public static String DATE_TO_STRING_SHORT_PATTERN = "yyyy-MM-dd";

    private void toDatePickerDialog1() {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_TO_STRING_SHORT_PATTERN);
            String dateInString_ = mYear + "-" + mMonth + "-" + mDay;
            Date date = formatter.parse(dateInString_);
            String create_time_begin1 = formatter.format(date).toString();
            DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(TaskManagerNewActivity.this, new DatePickerPopWin.OnDatePickedListener() {
                @Override
                public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                    mYear = year;
                    mMonth = month;
                    mDay = day;
                    create_time_begin = dateDesc;
                    mTask2_.setText(dateDesc);

                    if (!TextUtils.isEmpty(create_time_begin) && !TextUtils.isEmpty(create_time_end)) {
                        mName = null;
                        mPageNum = 1;
                        mTask8.setText("" + mPageNum);
                        mTask8.setSelection(mTask8.getText().length());
                        refresh();
                    }

                    //   Toast.makeText(TaskManagerNewActivity.this, dateDesc, Toast.LENGTH_SHORT).show();
                }
            })
                    .textConfirm("确定") //text of confirm button
                    .textCancel("取消") //text of cancel button
                    .btnTextSize(8) // button text size
                    .viewTextSize(8) // pick view text size
                    .colorCancel(Color.parseColor("#999999")) //color of cancel button
                    .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                    .minYear(1900) //min year in loop
                    .maxYear(2550) // max year in loop
                    .showDayMonthYear(false) // shows like dd mm yyyy (default is false)
                    .dateChose(create_time_begin1) // date chose when init popwindow
                    .build();
            pickerPopWin.showPopWin(TaskManagerNewActivity.this);


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void toDatePickerDialog2() {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_TO_STRING_SHORT_PATTERN);
            String dateInString_1 = mYear1 + "-" + mMonth1 + "-" + mDay1;
            Date src = formatter.parse(dateInString_1);

            String dateInString_2 = mYear + "-" + mMonth + "-" + mDay;
            Date dst = formatter.parse(dateInString_2);

            if(src.before(dst)){
                mYear1 = mYear;
                mMonth1 = mMonth;
                mDay1 = mDay;
            }

            String dateInString_ = mYear1 + "-" + mMonth1 + "-" + mDay1;
            Date date = formatter.parse(dateInString_);



            String create_time_end1 = formatter.format(date).toString();
            DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(TaskManagerNewActivity.this, new DatePickerPopWin.OnDatePickedListener() {
                @Override
                public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                    mYear1 = year;
                    mMonth1 = month;
                    mDay1 = day;
                    create_time_end = dateDesc;
                    mTask21_.setText(dateDesc);

                    if (!TextUtils.isEmpty(create_time_begin) && !TextUtils.isEmpty(create_time_end)) {
                        mName = null;
                        mPageNum = 1;
                        mTask8.setText("" + mPageNum);
                        mTask8.setSelection(mTask8.getText().length());
                        refresh();
                    }

                    // Toast.makeText(TaskManagerNewActivity.this, dateDesc, Toast.LENGTH_SHORT).show();
                }
            })
                    .textConfirm("确定") //text of confirm button
                    .textCancel("取消") //text of cancel button
                    .btnTextSize(8) // button text size
                    .viewTextSize(8) // pick view text size
                    .colorCancel(Color.parseColor("#999999")) //color of cancel button
                    .colorConfirm(Color.parseColor("#009900"))//color of confirm button
                    .minYear(1900) //min year in loop
                    .maxYear(2550) // max year in loop
                    .showDayMonthYear(false) // shows like dd mm yyyy (default is false)
                    .dateChose(create_time_end1) // date chose when init popwindow
                    .build();
            pickerPopWin.showPopWin(TaskManagerNewActivity.this);


        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    TaskManagerInfoFragment fragment1 = new TaskManagerInfoFragment();
    TaskManagerRecordFragment fragment2 = new TaskManagerRecordFragment();

    private void setFragmentRight() {
        fragment1.refresh(mId);
        fragment2.refresh1(mId);
        indicatorTask.setViewPager(viewPagerTask, 0);
    }


    //消息推送通知收到事件
    @Subscribe
    public void onEvent(String name) {

        if (name.equals("EventBus_A")) {
            //刷新
            mName = null;
            mPageNum = 1;
            mTask8.setText("" + mPageNum);
            mTask8.setSelection(mTask8.getText().length());

            refresh();
        } else {

        }
    }


}
