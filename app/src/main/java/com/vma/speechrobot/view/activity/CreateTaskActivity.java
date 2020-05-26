package com.vma.speechrobot.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.pickerview.popwindow.DatePickerPopWin;
import com.example.common.BaseAppProfile;
import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.consts.CommonPreferenceConst;
import com.example.common.net.HttpConfig;
import com.example.common.utils.BarUtil;
import com.example.common.utils.Spanny;
import com.example.common.utils.init.T;
import com.example.common.utils.wrapper.PreferenceWrapper;
import com.example.common.view.activity.BaseActivity;
import com.google.gson.Gson;
import com.leon.lfilepickerlibrary.utils.Constant;
import com.vma.speechrobot.LFile.LFilePickerT;
import com.vma.speechrobot.R;
import com.vma.speechrobot.bean.CallNumber;
import com.vma.speechrobot.bean.CardSloBean;
import com.vma.speechrobot.bean.CreateTask;
import com.vma.speechrobot.bean.Customer;
import com.vma.speechrobot.bean.Employee;
import com.vma.speechrobot.bean.HumanCardRelate;
import com.vma.speechrobot.bean.KeyNameBean;
import com.vma.speechrobot.bean.KeyValueBean;
import com.vma.speechrobot.bean.MessageCount;
import com.vma.speechrobot.bean.MessageTemplate;
import com.vma.speechrobot.bean.PageBean;
import com.vma.speechrobot.bean.ReqAddTask;
import com.vma.speechrobot.bean.TaskCardOption;
import com.vma.speechrobot.bean.TaskDefault;
import com.vma.speechrobot.bean.TimeTemplateOption;
import com.vma.speechrobot.bean.WhisperingOption;
import com.vma.speechrobot.presenter.TaskCreatePresenter;
import com.vma.speechrobot.utils.ExcelManager;
import com.vma.speechrobot.utils.ExcelUtil;
import com.vma.speechrobot.widget.SpinerPopWindow;
import com.vma.speechrobot.widget.TimePickerPopWin;
import com.vma.speechrobot.widget.dialog.AddTaskCallNumberDialog;
import com.vma.speechrobot.widget.dialog.CardSlotDialog;
import com.vma.speechrobot.widget.dialog.HumanCardRelateDailog;
import com.vma.speechrobot.widget.dialog.MessageDialog;
import com.vma.speechrobot.widget.dialog.TaskCallNumberDialog;
import com.vma.speechrobot.widget.dialog.TaskKHLevelDialog;
import com.vma.speechrobot.widget.dialog.TaskMsgModeDialog;
import com.vma.speechrobot.widget.dialog.TaskMsgModeEndDialog;
import com.vma.speechrobot.widget.dialog.TaskRootLevelDialog;
import com.vma.speechrobot.widget.dialog.TaskWcPushDialog;
import com.vma.speechrobot.widget.dialog.TaskWcPushErrorDialog;
import com.vma.speechrobot.widget.dialog.TaskYinRulDialog;
import com.vma.speechrobot.widget.dialog.WarningDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;

public class CreateTaskActivity extends BaseActivity<TaskCreatePresenter> implements TaskCreatePresenter.IView {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tv_right;


    @BindView(R.id.toFisrt)
    TextView toFisrt;
    @BindView(R.id.toSecond)
    TextView toSecond;

    @BindView(R.id.mLinerFirst)
    LinearLayout mLinerFirst;
    @BindView(R.id.mLinerSecond)
    LinearLayout mLinerSecond;


    @BindView(R.id.wc_push1)
    TextView wc_push1;
    @BindView(R.id.msgCallEnd)
    TextView msgCallEnd;
    @BindView(R.id.msgCall)
    TextView msgCall;
    @BindView(R.id.khLevel)
    TextView khLevel;

    @BindView(R.id.tvYinRu)
    TextView tvYinRu;
    @BindView(R.id.tv_callnumber)
    TextView tv_callnumber;
    @BindView(R.id.ivWB1)
    ImageView ivWB1;
    @BindView(R.id.ivWB2)
    ImageView ivWB2;
    @BindView(R.id.ivWB3)
    ImageView ivWB3;
    @BindView(R.id.ivWB4)
    ImageView ivWB4;
    @BindView(R.id.ivWB5)
    ImageView ivWB5;
    @BindView(R.id.ivWB6)
    ImageView ivWB6;
    @BindView(R.id.ivWB7)
    ImageView ivWB7;


    @BindView(R.id.tvCreate0)
    EditText tvCreate0;

    @BindView(R.id.tvCreate1)
    TextView tvCreate1;
    @BindView(R.id.tvCreateTime)
    TextView tvCreateTime;
    @BindView(R.id.tvCreateQD)
    TextView tvCreateQD;
    @BindView(R.id.tvCreateVoice)
    TextView tvCreateVoice;

    @BindView(R.id.wc_push2)
    TextView wc_push2;
    @BindView(R.id.tv_message_sum)
    TextView tv_message_sum;

    @BindView(R.id.box0)
    CheckBox box0;
    @BindView(R.id.box1)
    CheckBox box1;
    @BindView(R.id.box3)
    CheckBox box3;


    @BindView(R.id.tv_close)
    TextView tv_close;

    @BindView(R.id.tvToCame)
    TextView tvToCame;

    @BindView(R.id.tv_callnumber1)
    TextView tv_callnumber1;
    @BindView(R.id.tv_callnumber2)
    TextView tv_callnumber2;
    @BindView(R.id.tv_callnumber3)
    TextView tv_callnumber3;

    @BindView(R.id.tv_callnumberLook)
    TextView tv_callnumberLook;

    @BindView(R.id.tv_callnumberDelete)
    TextView tv_callnumberDelete;

    @BindView(R.id.tv_callnumberMode)
    TextView tv_callnumberMode;

    @BindView(R.id.tvDaoRu)
    TextView tvDaoRu;

    @BindView(R.id.tvShuRu)
    TextView tvShuRu;

    @BindView(R.id.all_num)
    TextView all_num;
    @BindView(R.id.effective_num)
    TextView effective_num;

    @BindView(R.id.tvToCard)
    TextView tvToCard;


    @BindView(R.id.edremarks)
    EditText edremarks;

    @BindView(R.id.edit_query)
    EditText edcall_rate;
    Integer call_rate = null;


    @BindView(R.id.mLinear)
    LinearLayout mLinear;
    @BindView(R.id.tvCreateQD2)
    LinearLayout tvCreateQD2;
    @BindView(R.id.tvCreateQD2_)
    TextView tvCreateQD2_;
    @BindView(R.id.iv_delete1)
    ImageView iv_delete1;


    @BindView(R.id.tvCreateQD1)
    LinearLayout tvCreateQD1;
    @BindView(R.id.tvCreateQD1_)
    TextView tvCreateQD1_;
    @BindView(R.id.iv_delete2)
    ImageView iv_delete2;

    private int yinruNum = 0;
    private int shuruNum = 0;
    private int shuruNumE = 0;
    private int daoruNum = 0;
    private int daoruNumE = 0;

    private List<HumanCardRelate> mHumanCardRelate = new ArrayList<>();//添加的转人工坐席
    private List<CallNumber> mCallNumber = new ArrayList<>();//输入的客户

    private List<CallNumber> mDaoRuNumber = new ArrayList<>();//导入的客户
    private List<Customer> mYinRuNumber = new ArrayList<>();//引入的客户


    private boolean isCheck = false;

    private SpinerPopWindow mSpinerPopWindowCount;//话术模板
    private SpinerPopWindow mSpinerPopWindowLevel;//时间模板
    private SpinerPopWindow mSpinerPopWindowStatus;//启动方式

    private SpinerPopWindow mSpinerPopWindowVoice;//语音识别


    private String voice_id = "mandarin";//语音识别
    private AddTaskCallNumberDialog mAddTaskCallNumberDialog;
    private TaskWcPushDialog mTaskWcPushDialog;
    private TaskMsgModeDialog mTaskMsgModeDialog;
    private TaskMsgModeEndDialog mTaskMsgModeEndDialog;
    private TaskKHLevelDialog mTaskKHLevelDialog;
    private TaskYinRulDialog mTaskYinRulDialog;
    private TaskCallNumberDialog mTaskCallNumberDialog;
    private TaskWcPushErrorDialog mTaskWcPushErrorDialog;
    private HumanCardRelateDailog mHumanCardRelateDailog;
    private MessageDialog mMessageDialog;
    private TaskRootLevelDialog mTaskRootLevelDialog;
    private CardSlotDialog mCardSlotDialog;

    public static void launch(Context context) {
        Intent intent = new Intent(context, CreateTaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_create_task;
    }

    @Override
    protected TaskCreatePresenter createPresenter() {
        return new TaskCreatePresenter(this);
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


        mCallNumber.clear();
        mTaskYinRulDialog = new TaskYinRulDialog(CreateTaskActivity.this, mPresenter);
        mTaskYinRulDialog.initContentView();
        mTaskYinRulDialog.setmTaskYinRulDialogListener(new TaskYinRulDialog.TaskYinRulDialogListener() {
            @Override
            public void onClick(List<Customer> mCustomer) {
                if (mCustomer != null) {
                    yinruNum = 0;
                    mYinRuNumber.clear();
                    mYinRuNumber.addAll(mCustomer);
                    for (int i = 0; i < mCustomer.size(); i++) {
                        yinruNum += mCustomer.get(i).effective_num;

                    }
                    if ((shuruNum + daoruNum) > 0) {
                        tv_callnumberLook.setVisibility(View.VISIBLE);
                        tv_callnumberDelete.setVisibility(View.VISIBLE);
                    } else {
                        tv_callnumberLook.setVisibility(View.GONE);
                        tv_callnumberDelete.setVisibility(View.GONE);
                    }
                    all_num.setText("" + (yinruNum + shuruNum + daoruNum));
                    effective_num.setText("" + (yinruNum + shuruNum + daoruNum));
                }
            }
        });
        tv_title.setText("创建营销任务");
        tv_right.setText("创建任务");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commit();

            }
        });
        tvToCard.setVisibility(View.GONE);
        tvToCame.setVisibility(View.GONE);
        box1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                isCheck = isChecked;
                if (isCheck) {
                    tv_close.setText("开启");
                    tvToCame.setVisibility(View.VISIBLE);
                    tvToCard.setVisibility(View.VISIBLE);
                } else {
                    tv_close.setText("关闭");
                    tvToCame.setVisibility(View.GONE);
                    tvToCard.setVisibility(View.GONE);
                }
            }
        });
        box0.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    is_default_whispering = 1;
                } else {
                    is_default_whispering = 0;
                }
            }
        });

        box3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    is_default_time_template = 1;
                } else {
                    is_default_time_template = 0;
                }
            }
        });

        // mandarin 粤语 cantonese 四川话 lmz
        List<KeyValueBean<String, String>> listVoice = new ArrayList<>();
        listVoice.add(new KeyValueBean("mandarin", "普通话"));
        listVoice.add(new KeyValueBean("lmz", "四川话"));
        listVoice.add(new KeyValueBean("cantonese", "粤语"));
        mSpinerPopWindowVoice = new SpinerPopWindow<>(this, listVoice,
                new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<String, String>>() {

                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                      KeyValueBean<String, String> data, int position) {
                        voice_id = data.key;
                        if (data.key == null) {
                            tvCreateVoice.setText(null);
                        } else {
                            tvCreateVoice.setText(data.value);
                        }

                        mSpinerPopWindowVoice.dismiss();
                    }
                });


        List<KeyValueBean<Integer, String>> listLevel = new ArrayList<>();

        listLevel.add(new KeyValueBean(new Integer(1), "立即启动"));
        listLevel.add(new KeyValueBean(new Integer(2), "定时启动"));

        mSpinerPopWindowStatus = new SpinerPopWindow<>(this, listLevel,
                new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<Integer, String>>() {

                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                      KeyValueBean<Integer, String> data, int position) {
                        start_type = data.key;
                        if (start_type == 2) {
                            mLinear.setVisibility(View.VISIBLE);
                        } else {
                            mLinear.setVisibility(View.GONE);
                        }
                        if (data.key == null) {
                            tvCreateQD.setText(null);
                        } else {
                            tvCreateQD.setText(data.value);
                        }

                        mSpinerPopWindowStatus.dismiss();
                    }
                });


        List<KeyValueBean<String, String>> listCount = new ArrayList<>();
        //listCount.add(new KeyValueBean(null, "-------"));
        mSpinerPopWindowCount = new SpinerPopWindow<>(this, listCount,
                new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<String, String>>() {
                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                      KeyValueBean<String, String> data, int position) {
                        whispering_id = Integer.valueOf(data.key);
                        whispering_title = data.value;
                        if (data.key == null) {
                            tvCreate1.setText(null);
                        } else {
                            tvCreate1.setText(data.value);
                        }

                        mSpinerPopWindowCount.dismiss();
                    }
                });

        List<KeyValueBean<String, String>> listStatus = new ArrayList<>();
        // listStatus.add(new KeyValueBean(null, "系统时间模板"));
        mSpinerPopWindowLevel = new SpinerPopWindow<>(this, listStatus,
                new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<String, String>>() {
                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                      KeyValueBean<String, String> data, int position) {
                        time_template_id = Integer.valueOf(data.key);
                        if (data.key == null) {
                            tvCreateTime.setText(null);
                        } else {
                            tvCreateTime.setText(data.value);
                        }

                        mSpinerPopWindowLevel.dismiss();
                    }
                });


    }

    @Override
    protected void initEvent() {
        super.initEvent();
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toFisrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFisrt.setTextColor(getResources().getColor(R.color.text_color1));
                toSecond.setTextColor(getResources().getColor(R.color.text_color2));
                toFisrt.setBackground(getResources().getDrawable(R.drawable.create_task_shape));
                toSecond.setBackground(getResources().getDrawable(R.drawable.create_task_shape1));
                mLinerFirst.setVisibility(View.VISIBLE);
                mLinerSecond.setVisibility(View.GONE);
            }
        });
        toSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFisrt.setTextColor(getResources().getColor(R.color.text_color2));
                toSecond.setTextColor(getResources().getColor(R.color.text_color1));
                toFisrt.setBackground(getResources().getDrawable(R.drawable.create_task_shape2));
                toSecond.setBackground(getResources().getDrawable(R.drawable.create_task_shape3));
                mLinerFirst.setVisibility(View.GONE);
                mLinerSecond.setVisibility(View.VISIBLE);
            }
        });
        wc_push1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskWcPushDialog = new TaskWcPushDialog(CreateTaskActivity.this);
                mTaskWcPushDialog.setWx_user_list_a(wx_user_list_a);
                mTaskWcPushDialog.setWx_user_list_b(wx_user_list_b);
                mTaskWcPushDialog.setWx_user_list_c(wx_user_list_c);

                mTaskWcPushDialog.setIs_default_wx_a(is_default_wx_a);
                mTaskWcPushDialog.setIs_default_wx_b(is_default_wx_b);
                mTaskWcPushDialog.setIs_default_wx_c(is_default_wx_c);
                mTaskWcPushDialog.setCall_time(mEmployee);
                mTaskWcPushDialog.setmTaskWcPushDialogListener(new TaskWcPushDialog.TaskWcPushDialogListener() {
                    @Override
                    public void onClick(List<String> wx_user_list_a, Integer is_default_wx_a,
                                        List<String> wx_user_list_b, Integer is_default_wx_b,
                                        List<String> wx_user_list_c, Integer is_default_wx_c) {
                        CreateTaskActivity.this.wx_user_list_a = wx_user_list_a;
                        CreateTaskActivity.this.is_default_wx_a = is_default_wx_a;

                        CreateTaskActivity.this.wx_user_list_b = wx_user_list_b;
                        CreateTaskActivity.this.is_default_wx_b = is_default_wx_b;
                        CreateTaskActivity.this.wx_user_list_c = wx_user_list_c;
                        CreateTaskActivity.this.is_default_wx_c = is_default_wx_c;
                        if (wx_user_list_a.size() > 0 || wx_user_list_b.size() > 0 || wx_user_list_c.size() > 0) {
                            wc_push1.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape_p));
                        } else {
                            wc_push1.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape));
                        }

                    }
                });

                mTaskWcPushDialog.show();
            }
        });

        wc_push2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskWcPushErrorDialog = new TaskWcPushErrorDialog(CreateTaskActivity.this);
                mTaskWcPushErrorDialog.setWx_exception_list(wx_exception_list);
                mTaskWcPushErrorDialog.setWx_complete_list(wx_complete_list);
                mTaskWcPushErrorDialog.setIs_default_task_exception(is_default_task_exception);
                mTaskWcPushErrorDialog.setIs_default_task_complete(is_default_task_complete);

                mTaskWcPushErrorDialog.setCall_time(mEmployee);
                mTaskWcPushErrorDialog.setmTaskWcPushErrorDialogListener(new TaskWcPushErrorDialog.TaskWcPushErrorDialogListener() {
                    @Override
                    public void onClick(List<String> wx_exception_list, Integer is_default_task_exception,
                                        List<String> wx_complete_list, Integer is_default_task_complete) {
                        CreateTaskActivity.this.wx_exception_list = wx_exception_list;
                        CreateTaskActivity.this.is_default_task_exception = is_default_task_exception;
                        CreateTaskActivity.this.wx_complete_list = wx_complete_list;
                        CreateTaskActivity.this.is_default_task_complete = is_default_task_complete;

                        if (wx_exception_list.size() > 0 || wx_complete_list.size() > 0) {
                            wc_push2.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape_p));
                        } else {
                            wc_push2.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape));
                        }
                    }
                });

                mTaskWcPushErrorDialog.show();
            }
        });

        msgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskMsgModeDialog = new TaskMsgModeDialog(CreateTaskActivity.this);
                mTaskMsgModeDialog.setKey_template(key_template);
                mTaskMsgModeDialog.setIs_default_key_template(is_default_key_template);
                mTaskMsgModeDialog.setmCustomer(call_time2);
                mTaskMsgModeDialog.setmTaskMsgModeDialogListener(new TaskMsgModeDialog.TaskMsgModeDialogListener() {
                    @Override
                    public void onClick(Integer key_template, Integer is_default_key_template) {
                        CreateTaskActivity.this.key_template = key_template;
                        CreateTaskActivity.this.is_default_key_template = is_default_key_template;
                        if (key_template != null) {
                            msgCall.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape_p));
                        } else {
                            msgCall.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape));
                        }

                    }
                });
                mTaskMsgModeDialog.show();
            }
        });

        msgCallEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTaskMsgModeEndDialog = new TaskMsgModeEndDialog(CreateTaskActivity.this);
                mTaskMsgModeEndDialog.setMessage_template_a(message_template_a);
                mTaskMsgModeEndDialog.setMessage_template_b(message_template_b);
                mTaskMsgModeEndDialog.setMessage_template_c(message_template_c);
                mTaskMsgModeEndDialog.setIs_default_message_template_a(is_default_message_template_a);
                mTaskMsgModeEndDialog.setIs_default_message_template_b(is_default_message_template_b);
                mTaskMsgModeEndDialog.setIs_default_message_template_c(is_default_message_template_c);
                mTaskMsgModeEndDialog.setmCustomer(call_time);
                mTaskMsgModeEndDialog.setmTaskMsgModeEndDialogListener(new TaskMsgModeEndDialog.TaskMsgModeEndDialogListener() {
                    @Override
                    public void onClick(Integer message_template_a, Integer is_default_message_template_a,
                                        Integer message_template_b, Integer is_default_message_template_b,
                                        Integer message_template_c, Integer is_default_message_template_c) {

                        CreateTaskActivity.this.message_template_a = message_template_a;
                        CreateTaskActivity.this.is_default_message_template_a = is_default_message_template_a;
                        CreateTaskActivity.this.message_template_b = message_template_b;
                        CreateTaskActivity.this.is_default_message_template_b = is_default_message_template_b;
                        CreateTaskActivity.this.message_template_c = message_template_c;
                        CreateTaskActivity.this.is_default_message_template_c = is_default_message_template_c;
                        if (message_template_a != null || message_template_b != null || message_template_c != null) {
                            msgCallEnd.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape_p));
                        } else {
                            msgCallEnd.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape));
                        }

                    }
                });
                mTaskMsgModeEndDialog.show();

            }
        });

        khLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskKHLevelDialog = new TaskKHLevelDialog(CreateTaskActivity.this);
                mTaskKHLevelDialog.setValid_key_num_a(valid_key_num_a);
                mTaskKHLevelDialog.setValid_key_num_b(valid_key_num_b);
                mTaskKHLevelDialog.setValid_key_num_c(valid_key_num_c);

                mTaskKHLevelDialog.setCall_num_a(call_num_a);
                mTaskKHLevelDialog.setCall_num_b(call_num_b);
                mTaskKHLevelDialog.setCall_num_c(call_num_c);

                mTaskKHLevelDialog.setCall_time_a(call_time_a);
                mTaskKHLevelDialog.setCall_time_b(call_time_b);
                mTaskKHLevelDialog.setCall_time_c(call_time_c);


                mTaskKHLevelDialog.setIs_default_user_level_a(is_default_user_level_a);
                mTaskKHLevelDialog.setIs_default_user_level_b(is_default_user_level_b);
                mTaskKHLevelDialog.setIs_default_user_level_c(is_default_user_level_c);
                mTaskKHLevelDialog.setmTaskKHLevelDialogListener(new TaskKHLevelDialog.TaskKHLevelDialogListener() {
                    @Override
                    public void onClick(Integer valid_key_num_a, Integer call_num_a, Integer call_time_a, Integer is_default_user_level_a,
                                        Integer valid_key_num_b, Integer call_num_b, Integer call_time_b, Integer is_default_user_level_b,
                                        Integer valid_key_num_c, Integer call_num_c, Integer call_time_c, Integer is_default_user_level_c) {
                        CreateTaskActivity.this.valid_key_num_a = valid_key_num_a;
                        CreateTaskActivity.this.call_num_a = call_num_a;
                        CreateTaskActivity.this.call_time_a = call_time_a;
                        CreateTaskActivity.this.is_default_user_level_a = is_default_user_level_a;


                        CreateTaskActivity.this.valid_key_num_b = valid_key_num_b;
                        CreateTaskActivity.this.call_num_b = call_num_b;
                        CreateTaskActivity.this.call_time_b = call_time_b;
                        CreateTaskActivity.this.is_default_user_level_b = is_default_user_level_b;


                        CreateTaskActivity.this.valid_key_num_c = valid_key_num_c;
                        CreateTaskActivity.this.call_num_c = call_num_c;
                        CreateTaskActivity.this.call_time_c = call_time_c;
                        CreateTaskActivity.this.is_default_user_level_c = is_default_user_level_c;

                    }
                });
                mTaskKHLevelDialog.show();
            }
        });
        tvDaoRu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadFile();

            }
        });

        tvShuRu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAddTaskCallNumberDialog = new AddTaskCallNumberDialog(CreateTaskActivity.this);
                mAddTaskCallNumberDialog.setmAddTaskCallNumberDialogListener(new AddTaskCallNumberDialog.AddTaskCallNumberDialogListener() {
                    @Override
                    public void onClick(String cust_id1, String cust_id2) {
                        mCallNumber.add(new CallNumber(cust_id1, cust_id2));
                        shuruNum = mCallNumber.size();

                        List<CallNumber> mDaoRuNumberEnd = new ArrayList<>();//输入的客户
                        for (CallNumber bean : mCallNumber) {
                            mDaoRuNumberEnd.add(new CallNumber(bean.moblie, bean.name));
                        }
                        for (int i = 0; i < mCallNumber.size(); i++)  //外循环是循环的次数
                        {
                            for (int j = mCallNumber.size() - 1; j > i; j--)  //内循环是 外循环一次比较的次数
                            {
                                if (mCallNumber.get(i).moblie.equals(mCallNumber.get(j).moblie)) {
                                    mDaoRuNumberEnd.remove(j);
                                }

                            }
                        }

                        shuruNumE = mDaoRuNumberEnd.size();

                        if ((shuruNum + daoruNum) > 0) {
                            tv_callnumberLook.setVisibility(View.VISIBLE);
                            tv_callnumberDelete.setVisibility(View.VISIBLE);
                        } else {
                            tv_callnumberLook.setVisibility(View.GONE);
                            tv_callnumberDelete.setVisibility(View.GONE);
                        }

                        all_num.setText("" + (yinruNum + shuruNum + daoruNum));
                        effective_num.setText("" + (yinruNum + shuruNumE + daoruNumE));
                    }
                });
                mAddTaskCallNumberDialog.show();


            }
        });


        tvYinRu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  mTaskYinRulDialog.setmCustomer(page_num,mCustomer);
                mTaskYinRulDialog.setTotal_num(total_num);*/
                mTaskYinRulDialog.show();

            }
        });

        tv_callnumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskCallNumberDialog = new TaskCallNumberDialog(CreateTaskActivity.this, listFreeTask);
                mTaskCallNumberDialog.setmTaskCallNumberDialogListener(new TaskCallNumberDialog.TaskCallNumberDialogListener() {
                    @Override
                    public void onClick(List<KeyNameBean> call_time) {

                        if (call_time != null) {
                            card_relate_list.clear();
                            Spanny spannyLevel = new Spanny();
                            for (KeyNameBean mKeyNameBean : call_time) {
                                card_relate_list.add(Integer.valueOf(mKeyNameBean.key));
                                spannyLevel.append(mKeyNameBean.name).append("\n");
                            }
                            tv_callnumber.setText(spannyLevel);

                        }

                    }
                });
                mTaskCallNumberDialog.show();
            }
        });
        ivWB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageDialog = new MessageDialog(CreateTaskActivity.this);
                mMessageDialog.setTitle("话术模板");
                mMessageDialog.setText("外呼任务将采用选中的话术跟客户进行对话，设置默认话术后，下次创建任务将默认使用该话术。" +
                        "\n" +
                        "        1.1 如果话术修改后没提交，做感叹号提示：话术已变更，请提交审核，审核通过前将使用最后一次审核通过的话术版本。");
                mMessageDialog.show();
            }
        });
        ivWB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageDialog = new MessageDialog(CreateTaskActivity.this);
                mMessageDialog.setTitle("意向客户推送");
                mMessageDialog.setText("将意向客户通过微信公众号推送给指定的销售人员。");
                mMessageDialog.show();
            }
        });
        ivWB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageDialog = new MessageDialog(CreateTaskActivity.this);
                mMessageDialog.setTitle("任务情况推送");
                mMessageDialog.setText("任务异常或者任务结束后，通过微信公众号推送给指定的销售人员。");
                mMessageDialog.show();
            }
        });
        ivWB7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageDialog = new MessageDialog(CreateTaskActivity.this);
                mMessageDialog.setTitle("可用短信数量");
                mMessageDialog.setText("短信充值请联系上级。");
                mMessageDialog.show();
            }
        });

        ivWB4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageDialog = new MessageDialog(CreateTaskActivity.this);
                mMessageDialog.setTitle("通话过程中短信");
                mMessageDialog.setText("通话过程中，客户命中指定关键字，将会推送一条指定短信给客户。");
                mMessageDialog.show();
            }
        });
        ivWB5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageDialog = new MessageDialog(CreateTaskActivity.this);
                mMessageDialog.setTitle("通话结束后短信");
                mMessageDialog.setText("通话结束后，推送一条指定短信给对应等级的客户。");
                mMessageDialog.show();
            }
        });
        ivWB6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMessageDialog = new MessageDialog(CreateTaskActivity.this);
                mMessageDialog.setTitle("客户等级条件设置");
                mMessageDialog.setText("设置为默认后，以后每次创建任务将使用该参数，不需要重复设置\n" +
                        "等级默认值：\n" +
                        "A类：关键字1个，通话轮次3轮，通话时长30秒\n" +
                        "B类：关键字1个，通话轮次2轮，通话时长20秒\n" +
                        "C类：关键字1个，通话轮次2轮，通话时长10秒\n");
                mMessageDialog.show();
            }
        });


        tvCreate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countClick();
            }
        });
        tvCreateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levelClick();
            }
        });
        tvCreateQD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusClick();
            }
        });
        tvCreateVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiceClick();
            }
        });

        tvToCame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHumanCardRelateDailog = new HumanCardRelateDailog(CreateTaskActivity.this);
                mHumanCardRelateDailog.setmCustomer(mHumanCardRelate);
                mHumanCardRelateDailog.setmCustomer1(mCustomer1);
                mHumanCardRelateDailog.setmCustomer2(transferemployee);
                mHumanCardRelateDailog.setmHumanCardRelateDailogListener(new HumanCardRelateDailog.HumanCardRelateDailogListener() {
                    @Override
                    public void onClick(List<HumanCardRelate> mCustomer) {
                        if (mCustomer != null) {

                            mHumanCardRelate.clear();
                            mHumanCardRelate.addAll(mCustomer);

                        }
                    }
                });
                mHumanCardRelateDailog.show();
            }
        });
        tv_callnumberLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹窗查看
                card_slot_list.clear();
                List<CardSloBean> mCustomer = new ArrayList<>();
                for (CallNumber bean : mCallNumber) {
                    card_slot_list.add(new ReqAddTask.CardSlotListBean(bean.moblie, bean.name));
                    mCustomer.add(new CardSloBean(bean.moblie, bean.name));
                }
                for (CallNumber bean : mDaoRuNumber) {
                    card_slot_list.add(new ReqAddTask.CardSlotListBean(bean.moblie, bean.name));
                    mCustomer.add(new CardSloBean(bean.moblie, bean.name));
                }


                mCardSlotDialog = new CardSlotDialog(CreateTaskActivity.this);
                mCardSlotDialog.setmCustomer(mCustomer);

                mCardSlotDialog.show();
            }
        });
        tv_callnumberDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WarningDialog mWarningDialog = new WarningDialog(CreateTaskActivity.this);
                mWarningDialog.setTitle("温馨提示");
                mWarningDialog.setText("确定删除吗?");
                mWarningDialog.setmWarningDialogListener(new WarningDialog.WarningDialogListener() {
                    @Override
                    public void onClick() {
                        mCallNumber.clear();
                        mDaoRuNumber.clear();
                        mYinRuNumber.clear();
                        yinruNum=0;
                        shuruNum=0;
                        daoruNum=0;
                        shuruNumE=0;
                        daoruNumE=0;
                        all_num.setText("" + (yinruNum + shuruNum + daoruNum));
                        effective_num.setText("" + (yinruNum + shuruNumE + daoruNumE));
                    }
                });
                mWarningDialog.show();

            }
        });

        tv_callnumberMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下载模板 http://121.40.216.49:50444/static/xls/ems/营销对象模板.xlsx?v=1546495056025

                if (ContextCompat.checkSelfPermission(CreateTaskActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(CreateTaskActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(CreateTaskActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            346);
                } else {
                    String url = PreferenceWrapper
                            .getString(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
                                    CommonPreferenceConst.PREFERENCE_KEY.EXE, null);
                   // String url = HttpConfig.IP + "static/xls/ems/营销对象模板.xlsx";
                    if (!TextUtils.isEmpty(url)) {
                        DownloadManager downloadManager = (DownloadManager) getContext()
                                .getSystemService(Context.DOWNLOAD_SERVICE);
                        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                        //下载时，下载完成后显示通知
                        //设置通知栏标题
                        request.setTitle("下载");
                        request.setDescription("营销对象模板");

                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        //创建目录
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir() ;
                        //下载的路径，第一个参数是文件夹名称，第二个参数是下载的文件名
                        request.setDestinationInExternalPublicDir( getString(R.string.app_name),
                                "营销对象模板.xlsx");
                        request.setVisibleInDownloadsUi(true);
                        downloadManager.enqueue(request);
                    } else {
                        T.showShort("下载出错");
                    }
                }




            }
        });

        tvToCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTaskRootLevelDialog = new TaskRootLevelDialog(CreateTaskActivity.this);
                mTaskRootLevelDialog.setmTaskKHLevelDialogListener(new TaskRootLevelDialog.TaskRootLevelDialogListener() {
                    @Override
                    public void onClick(Integer transfer_key_num, Integer transfer_call_num, Integer transfer_call_time) {
                        CreateTaskActivity.this.transfer_key_num = transfer_key_num;
                        CreateTaskActivity.this.transfer_call_num = transfer_call_num;
                        CreateTaskActivity.this.transfer_call_time = transfer_call_time;
                    }
                });
                mTaskRootLevelDialog.show();
            }
        });


        edcall_rate.setSelection(edcall_rate.getText().length());
        edcall_rate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 这部分是处理如果输入框内小数点后有俩位，那么舍弃最后一位赋值，光标移动到最后


                // 这里处理用户 多次输入.的处理
                if (s.toString().startsWith("0") && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        edcall_rate.setText(s.subSequence(0, 1));
                        edcall_rate.setSelection(1);
                        return;
                    }
                }
            }
        });

        tvCreateQD1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDatePickerDialog1();
            }
        });

        tvCreateQD2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDatePickerDialog2();
            }
        });

        iv_delete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCreateQD1_.setText(null);
                start_time=null;
            }
        });
        iv_delete2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCreateQD2_.setText(null);
                end_time=null;
            }
        });

    }




    Calendar c;
    int mYear = 0, mMonth = 0, mDay = 0;
    /**
     * 年-月-日 显示格式
     */
    public static String DATE_TO_STRING_SHORT_PATTERN = "yyyy-MM-dd";
    private String start_time;
    private String end_time;

    private void toDatePickerDialog1() {


        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_TO_STRING_SHORT_PATTERN);
            String dateInString_ = mYear + "-" + mMonth + "-" + mDay;
            Date date = formatter.parse(dateInString_);
            String create_time_begin1 = formatter.format(date).toString();
            DatePickerPopWin pickerPopWin = new DatePickerPopWin.Builder(CreateTaskActivity.this, new DatePickerPopWin.OnDatePickedListener() {
                @Override
                public void onDatePickCompleted(int year, int month, int day, String dateDesc) {
                    mYear = year;
                    mMonth = month;
                    mDay = day;
                    start_time = dateDesc;
                    tvCreateQD1_.setText(dateDesc);

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
            pickerPopWin.showPopWin(CreateTaskActivity.this);


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private int mHour;
    private int mMinute;
    private void toDatePickerDialog2() {
        TimePickerPopWin timePickerPopWin = new TimePickerPopWin.Builder(CreateTaskActivity.this,
                new TimePickerPopWin.OnTimePickListener() {
                    @Override
                    public void onTimePickCompleted(int hour, int minute, String AM_PM, String time) {

                        mMinute=minute;

                        if(AM_PM.equals("AM")){
                            mHour=hour;
                            if(hour<10){

                                if(minute<10){
                                    end_time="0"+hour+":"+"0"+minute;
                                }else{
                                    end_time="0"+hour+":"+minute;
                                }
                            }else{
                                if(minute<10){
                                    end_time=    hour+":"+"0"+minute;
                                }else{
                                    end_time=    hour+":"+minute;
                                }

                            }

                        }else{

                            mHour=12+hour;
                            if(mHour==24){
                                mHour=0;
                            }
                            if(mHour==0){
                                if(minute<10){
                                    end_time="0"+mHour+":"+"0"+minute;
                                }else{
                                    end_time="0"+mHour+":"+minute;
                                }

                            }else{
                                if(minute<10){
                                    end_time=mHour+":"+"0"+minute;
                                }else{
                                    end_time=mHour+":"+minute;
                                }

                            }

                        }
                        tvCreateQD2_.setText(end_time);


                    }
                }).textConfirm("确定")
                .textCancel("取消")
                .btnTextSize(8)
                .viewTextSize(8)
                .colorCancel(Color.parseColor("#999999"))
                .colorConfirm(Color.parseColor("#009900"))
                .build();
        timePickerPopWin.showPopWin(CreateTaskActivity.this);
    }


    Integer is_use = null;

    @Override
    protected void initData() {
        super.initData();

        mPresenter.getWhisperingOption();
        mPresenter.getTaskCardOption();

        mPresenter.taskTimeTemplateOption();
        mPresenter.customer_list(is_use, page_num, 10);
        mPresenter.employee(null);
        mPresenter.messageTemplate(1);
        mPresenter.messageTemplate2(2);
        mPresenter.getHumanCardRelate();
        mPresenter.message_count();
        mPresenter.transferemployee();

    }

    @Override
    public Context getContext() {
        return this;
    }

    private List<KeyNameBean> listALLTask = new ArrayList<>();

    @Override
    public void fillgetTaskCardOption(TaskCardOption[] data) {
        if (data != null && data.length > 0) {
            tv_callnumber1.setText("" + data.length);

            listALLTask.clear();
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    listALLTask.add(new KeyNameBean("" + data[i].card_id, data[i].machine_name + "(" + data[i].mobile + ")"));
                }
            }


        }
        mPresenter.getFreeTaskCardOption();
    }

    private List<WhisperingOption> mWhisperingOption = new ArrayList<>();

    @Override
    public void fillgetWhisperingOption(WhisperingOption[] data) {
        if (data != null && data.length > 0) {
            mWhisperingOption.clear();
            List<KeyValueBean<String, String>> listCount = new ArrayList<>();
            // listCount.add(new KeyValueBean(null, "-------"));

            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    mWhisperingOption.add(data[i]);
                    listCount.add(new KeyValueBean("" + data[i].id, data[i].title));
                }

            }

            mSpinerPopWindowCount = new SpinerPopWindow<>(this, listCount,
                    new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<String, String>>() {
                        @Override
                        public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                          KeyValueBean<String, String> data, int position) {
                            whispering_id = Integer.valueOf(data.key);
                            whispering_title = data.value;
                            if (data.key == null) {
                                tvCreate1.setText(null);
                            } else {
                                tvCreate1.setText(data.value);
                            }

                            mSpinerPopWindowCount.dismiss();
                        }
                    });
        }
    }

    private List<KeyNameBean> listFreeTask = new ArrayList<>();

    @Override
    public void fillgetFreeTaskCardOption(TaskCardOption[] data) {

        //tv_callnumber
        if (data != null && data.length > 0) {

            tv_callnumber3.setText("" + data.length);
            listFreeTask.clear();
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    listFreeTask.add(new KeyNameBean("" + data[i].id, data[i].machine_name + "(" + data[i].mobile + ")"));
                }
            }
            if (listALLTask.size() > listFreeTask.size()) {
                tv_callnumber2.setText("" + (listALLTask.size() - listFreeTask.size()));
            }


        }


    }


    private List<Employee> mEmployee = new ArrayList<>();

    @Override
    public void fillemployee(Employee[] data) {
        if (data != null && data.length > 0) {
            mEmployee.clear();
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    mEmployee.add(data[i]);
                }

            }
        }
    }

    @Override
    public void fillmessage_count(MessageCount data) {

        tv_message_sum.setText(data.getMessage_use_count() + "/" + data.getMessage_all_count());
    }

    private List<MessageTemplate> call_time = new ArrayList<>();
    private List<MessageTemplate> call_time2 = new ArrayList<>();

    @Override
    public void fillmessageTemplate(MessageTemplate[] data) {
        //
        if (data != null && data.length > 0) {
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    call_time.add(data[i]);
                }

            }
        }
    }

    @Override
    public void fillmessageTemplate2(MessageTemplate[] data) {
        if (data != null && data.length > 0) {
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    call_time2.add(data[i]);
                }

            }
        }
    }

    private List<TimeTemplateOption> mTimeTemplateOption = new ArrayList<>();

    @Override
    public void filltaskTimeTemplateOption(TimeTemplateOption[] data) {
        if (data != null && data.length > 0) {
            mTimeTemplateOption.clear();
            List<KeyValueBean<String, String>> listStatus = new ArrayList<>();
            //   listStatus.add(new KeyValueBean(null, "系统时间模板"));
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    mTimeTemplateOption.add(data[i]);
                    listStatus.add(new KeyValueBean("" + data[i].id, data[i].template_name));
                }

            }
            mSpinerPopWindowLevel = new SpinerPopWindow<>(this, listStatus,
                    new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<String, String>>() {
                        @Override
                        public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                          KeyValueBean<String, String> data, int position) {
                            time_template_id = Integer.valueOf(data.key);
                            if (data.key == null) {
                                tvCreateTime.setText(null);
                            } else {
                                tvCreateTime.setText(data.value);
                            }

                            mSpinerPopWindowLevel.dismiss();
                        }
                    });


        }
        mPresenter.taskdefault();
    }


    private List<TaskCardOption> mCustomer1 = new ArrayList<>();

    @Override
    public void fillgetHumanCardRelate(TaskCardOption[] data) {
        if (data != null && data.length > 0) {
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    mCustomer1.add(data[i]);
                }

            }
        }
    }

    private List<Customer> mCustomer = new ArrayList<>();
    private int page_num = 1;

    private int total_num;

    @Override
    public void fillcustomer_list(PageBean<Customer> data) {
        if (data != null) {
            total_num = data.total_num;
        }
        if (data != null && data.data_list != null && data.data_list.size() != 0) {
            mCustomer.clear();
            mCustomer.addAll(data.data_list);
            if (data.data_list.size() == 10) {

                //  page_num++;
            }
            if (mTaskYinRulDialog != null) {
                mTaskYinRulDialog.refesh(total_num, page_num, mCustomer);
            }

        }

    }

    @Override
    public void filltaskdefault(TaskDefault data) {

        if (data == null) {
            return;
        }
        if ((data.call_num_a != null && data.call_num_a != 0) || (data.call_time_a != null && data.call_time_a != 0) || (data.valid_key_num_a != null && data.valid_key_num_a != 0)) {
            is_default_user_level_a = 1;
        }
        if ((data.call_num_b != null && data.call_num_b != 0) || (data.call_time_b != null && data.call_time_b != 0) || (data.valid_key_num_b != null && data.valid_key_num_b != 0)) {
            is_default_user_level_b = 1;
        }
        if ((data.call_num_c != null && data.call_num_c != 0) || (data.call_time_c != null && data.call_time_c != 0) || (data.valid_key_num_c != null && data.valid_key_num_c != 0)) {
            is_default_user_level_c = 1;
        }
        if (data.key_template != null && data.key_template != 0) {
            is_default_key_template = 1;
        }
        if (data.message_template_a != null && data.message_template_a != 0) {
            is_default_message_template_a = 1;
        }
        if (data.message_template_b != null && data.message_template_b != 0) {
            is_default_message_template_b = 1;
        }
        if (data.message_template_c != null && data.message_template_c != 0) {
            is_default_message_template_c = 1;
        }
        if (data.time_template_id != null && data.time_template_id != 0) {
            is_default_time_template = 1;
            box3.setChecked(true);
            for (TimeTemplateOption bean : mTimeTemplateOption) {
                if (bean.id == data.time_template_id) {
                    tvCreateTime.setText(bean.template_name);
                }
            }
        }


        if (data.whispering_id != null && data.whispering_id != 0) {
            is_default_whispering = 1;
            box0.setChecked(true);
            if (!TextUtils.isEmpty(data.whispering_title)) {
                whispering_title = data.whispering_title;
                tvCreate1.setText(whispering_title);
            } else {
                for (WhisperingOption bean : mWhisperingOption) {
                    if (bean.id == data.whispering_id) {
                        whispering_title = bean.title;
                        tvCreate1.setText(bean.title);
                    }
                }
            }

        }


        call_num_a = data.call_num_a;
        call_num_b = data.call_num_b;
        call_num_c = data.call_num_c;

        call_time_a = data.call_time_a;
        call_time_b = data.call_time_b;
        call_time_c = data.call_time_c;

        key_template = data.key_template;
        message_template_a = data.message_template_a;
        message_template_b = data.message_template_b;
        message_template_c = data.message_template_c;

        time_template_id = data.time_template_id;

        valid_key_num_a = data.valid_key_num_a;
        valid_key_num_b = data.valid_key_num_b;
        valid_key_num_c = data.valid_key_num_c;

        whispering_id = data.whispering_id;


        if (data.wx_complete_list != null && data.wx_complete_list.size() > 0) {
            wx_complete_list = data.wx_complete_list;
            is_default_task_complete = 1;
        }
        if (data.wx_exception_list != null && data.wx_exception_list.size() > 0) {
            wx_exception_list = data.wx_exception_list;
            is_default_task_exception = 1;
        }
        if (data.wx_user_list_a != null && data.wx_user_list_a.size() > 0) {
            wx_user_list_a = data.wx_user_list_a;
            is_default_wx_a = 1;
        }
        if (data.wx_user_list_b != null && data.wx_user_list_b.size() > 0) {
            wx_user_list_b = data.wx_user_list_b;
            is_default_wx_b = 1;
        }
        if (data.wx_user_list_c != null && data.wx_user_list_c.size() > 0) {
            wx_user_list_c = data.wx_user_list_c;
            is_default_wx_c = 1;
        }

        if ( (wx_user_list_a!=null &&wx_user_list_a.size() > 0) || (wx_user_list_b!=null &&wx_user_list_b.size() > 0) || (wx_user_list_c!=null &&wx_user_list_c.size() > 0)) {
            wc_push1.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape_p));
        } else {
            wc_push1.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape));
        }

        if (  (wx_exception_list!=null &&wx_exception_list.size() > 0) || (wx_complete_list!=null &&wx_complete_list.size() > 0)) {
            wc_push2.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape_p));
        } else {
            wc_push2.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape));
        }


        if (key_template != null && key_template != 0) {
            msgCall.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape_p));
        } else {
            msgCall.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape));
        }

        //data.message_template_a != null && data.message_template_a != 0
        if ( ( message_template_a != null && message_template_a != 0) || ( message_template_b != null &&message_template_b != 0) || ( message_template_c != null && message_template_c != null) ) {
            msgCallEnd.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape_p));
        } else {
            msgCallEnd.setBackground(getResources().getDrawable(R.drawable.create_task_tv_shape));
        }


    }

    @Override
    public void fillcreatetask(CreateTask data) {
        if (data != null) {
            Toast.makeText(this, "创建成功！", Toast.LENGTH_SHORT).show();
            EventBus.getDefault().post("EventBus_A");
            finish();
            //刷新

        }
    }


    private List<Employee> transferemployee = new ArrayList<>();

    @Override
    public void filltransferemployee(Employee[] data) {
        if (data != null && data.length > 0) {
            transferemployee.clear();
            for (int i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    transferemployee.add(data[i]);
                }

            }
        }
    }


    private void countClick() {
        mSpinerPopWindowCount.setWidth(tvCreate1.getWidth());
        mSpinerPopWindowCount.setHeight(tvCreate1.getWidth() * 3 / 5);
        mSpinerPopWindowCount.showAsDropDown(tvCreate1);
    }

    private void levelClick() {
        mSpinerPopWindowLevel.setWidth(tvCreateTime.getWidth());
        //  mSpinerPopWindowLevel.setHeight(tvCreateTime.getWidth()*3/5);
        mSpinerPopWindowLevel.showAsDropDown(tvCreateTime);
    }

    private void statusClick() {
        mSpinerPopWindowStatus.setWidth(tvCreateQD.getWidth());
        //   mSpinerPopWindowStatus.setHeight(tvCreateQD.getWidth()*3/5);
        mSpinerPopWindowStatus.showAsDropDown(tvCreateQD);
    }


    private void voiceClick() {
        mSpinerPopWindowVoice.setWidth(tvCreateVoice.getWidth());
        // mSpinerPopWindowVoice.setHeight(tvCreateVoice.getWidth()*3/5);
        mSpinerPopWindowVoice.showAsDropDown(tvCreateVoice);
    }


    private int REQUESTCODE_FROM_ACTIVITY = 1000;
    private int requestPermissionsCode = 345;
    private int MAX_ATTACHMENT_COUNT = 1;

    //选择附件
    private void uploadFile() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                ) {


            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    requestPermissionsCode);
        } else {

            new LFilePickerT()
                    .withActivity(CreateTaskActivity.this)
                    .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                    .withTitle("选择excel文件")
                    .withFileFilter(new String[]{".xls", "xlsx"})
                    .withIsGreater(false)
                    .withFileSize(80 * 1024 * 1024)
                    .withMaxNum(MAX_ATTACHMENT_COUNT)
                    .withMutilyMode(false)
                    .start();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }


    //音乐文件(mp3、wav、ogg等)、视频文件(3gp、mp4等)、图片文件(jpg、png、gif等)、安装包(apk)、文档(txt、doc、ppt、pdf、xls等)
    private void doNext(int requestCode, int[] grantResults) {

        if (requestCode == 346) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                String url = PreferenceWrapper
                        .getString(BaseAppProfile.getApplication(), CommonPreferenceConst.MAIN_PREFERENCE_FILE,
                                CommonPreferenceConst.PREFERENCE_KEY.EXE, null);

               // String url = HttpConfig.IP + "static/xls/ems/营销对象模板.xlsx";
                if (!TextUtils.isEmpty(url)) {
                    DownloadManager downloadManager = (DownloadManager) getContext()
                            .getSystemService(Context.DOWNLOAD_SERVICE);
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                    //下载时，下载完成后显示通知
                    //设置通知栏标题
                    request.setTitle("下载");
                    request.setDescription("营销对象模板");

                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    //创建目录
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir() ;
                    //下载的路径，第一个参数是文件夹名称，第二个参数是下载的文件名
                    request.setDestinationInExternalPublicDir( getString(R.string.app_name),
                            "营销对象模板.xlsx");
                    request.setVisibleInDownloadsUi(true);
                    downloadManager.enqueue(request);
                } else {
                    T.showShort("下载出错");
                }
            } else {
                // Permission Denied
            }
        }else if (requestCode == requestPermissionsCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted

                new LFilePickerT()
                        .withActivity(CreateTaskActivity.this)
                        .withRequestCode(REQUESTCODE_FROM_ACTIVITY)
                        .withTitle("选择excel文件")
                        .withFileFilter(new String[]{".xls", "xlsx"})
                        .withIsGreater(false)
                        .withFileSize(80 * 1024 * 1024)
                        .withMaxNum(MAX_ATTACHMENT_COUNT)
                        .withMutilyMode(false)
                        .start();
            } else {
                // Permission Denied
            }
        }
    }

    //选择附件回调
    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE_FROM_ACTIVITY) {

            final List<String> list = data.getStringArrayListExtra(Constant.RESULT_INFO);
            if (list.size() > 0 && list.size() != 1) {
                Toast.makeText(CreateTaskActivity.this, "一次只能选择一个excel文件", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < list.size(); i++) {
                final String filePath = list.get(i);
                Log.e("CreateTaskActivity", "------------filePath-------------=" + filePath);
                if (filePath == null) {
                    Toast.makeText(CreateTaskActivity.this, "excel文件不存在", Toast.LENGTH_SHORT).show();
                    return;
                }

                final File file = new File(filePath);
                if (file.isDirectory()) {
                    Log.e("CreateTaskActivity", "--------filepath-----isDirectory---:" + filePath + " is directory");
                    return;
                } else {

                    final String col = "";
                    final String row = "";

                    new AsyncTask<Void, Void,List<List<Object>>>() {

                        @Override
                        protected  List<List<Object>> doInBackground(Void... params) {
                             return ExcelUtil.read(filePath);
                            // return  ExcelManager.getInstance().analyze(filePath);
                           /* try {

                               *//* ZzExcelCreator zzExcelCreator = ZzExcelCreator
                                        .getInstance()
                                        .openExcel(file)
                                        .openSheet(0);   //打开第1个sheet
                                String content = zzExcelCreator.getCellContent(Integer.parseInt(col), Integer.parseInt(row));
                                zzExcelCreator.close();
                                return content;*//*
                            } catch (IOException | BiffException | WriteException e) {
                                e.printStackTrace();
                                return null;
                            }*/
                        }

                        @Override
                        protected void onPostExecute(List<List<Object>> aVoid) {
                            super.onPostExecute(aVoid);
                            if (aVoid == null) {
                                Toast.makeText(CreateTaskActivity.this, "读取失败！", Toast.LENGTH_SHORT).show();
                            } else {
                                daoruNum = 0;
                                daoruNumE = 0;
                                mDaoRuNumber.clear();
                              //  String obj2 = new Gson().toJson(aVoid);
                             //  Toast.makeText(CreateTaskActivity.this, obj2+":读取:"+aVoid.size(), Toast.LENGTH_LONG).show();
/*
                                for(  int i=0;i<aVoid.size();i++  ){
                                    List<List<String>>  mList1 =aVoid.get("sheet"+(i+1));
                                    for (List<String> mList : mList1) {
                                        if (mList.size() > 0) {
                                            CallNumber mCallNumber = new CallNumber();
                                            if (mList.size() == 1) {
                                                mCallNumber.moblie = mList.get(0).toString();
                                                mCallNumber.name = "客户" + mCallNumber.moblie;
                                            } else if (mList.size() >= 2) {
                                                mCallNumber.moblie = mList.get(0).toString();
                                                mCallNumber.name = mList.get(1).toString();
                                            }
                                            if (AddTaskCallNumberDialog.isMobileNO(mCallNumber.moblie)) {
                                                String  mobiles1=AddTaskCallNumberDialog.getENum(mCallNumber.moblie);
                                                mCallNumber.moblie =mobiles1;
                                                mDaoRuNumber.add(mCallNumber);
                                            }
                                        }

                                    }

                                }
*/

                                for (List<Object> mList : aVoid) {
                                    if (mList.size() > 0) {
                                        CallNumber mCallNumber = new CallNumber();
                                        if (mList.size() == 1) {
                                            mCallNumber.moblie = mList.get(0).toString();
                                            mCallNumber.name = "客户" + mCallNumber.moblie;
                                        } else if (mList.size() >= 2) {
                                            mCallNumber.moblie = mList.get(0).toString();
                                            if(!TextUtils.isEmpty(mList.get(1).toString())){
                                                mCallNumber.name = mList.get(1).toString();
                                            }else{
                                                mCallNumber.name = "客户" + mCallNumber.moblie;
                                            }

                                        }
                                        if (AddTaskCallNumberDialog.isMobileNO(mCallNumber.moblie)) {
                                            String  mobiles1=AddTaskCallNumberDialog.getENum(mCallNumber.moblie);
                                            mCallNumber.moblie =mobiles1;
                                            mDaoRuNumber.add(mCallNumber);
                                        }
                                    }

                                }



                                daoruNum = mDaoRuNumber.size();
                                if ((shuruNum + daoruNum) > 0) {
                                    tv_callnumberLook.setVisibility(View.VISIBLE);
                                    tv_callnumberDelete.setVisibility(View.VISIBLE);
                                } else {
                                    tv_callnumberLook.setVisibility(View.GONE);
                                    tv_callnumberDelete.setVisibility(View.GONE);
                                }

                                List<CallNumber> mDaoRuNumberEnd = new ArrayList<>();//输入的客户
                                for (CallNumber bean : mDaoRuNumber) {
                                    mDaoRuNumberEnd.add(new CallNumber(bean.moblie, bean.name));

                                }
                                for (int i = 0; i < mDaoRuNumber.size(); i++)  //外循环是循环的次数
                                {
                                    for (int j = mDaoRuNumber.size() - 1; j > i; j--)  //内循环是 外循环一次比较的次数
                                    {
                                        if (mDaoRuNumber.get(i).moblie.equals(mDaoRuNumber.get(j).moblie)) {
                                            mDaoRuNumberEnd.remove(j);
                                        }

                                    }
                                }


                                daoruNumE = mDaoRuNumberEnd.size();
                                all_num.setText("" + (yinruNum + shuruNum + daoruNum));
                                effective_num.setText("" + (yinruNum + shuruNumE + daoruNumE));
                               // Toast.makeText(CreateTaskActivity.this, "导入" + mDaoRuNumber.size() + "个号码，有效" + mDaoRuNumberEnd.size() + "个", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.execute();

                }
            }
        }

    }

    Integer call_num_a = null;//A通话轮次数  客户等级条件设置弹窗  //
    Integer call_num_b = null;//B通话轮次数  //
    Integer call_num_c = null;// C通话轮次数  //

    Integer call_time_a = null;// A通话时间 客户等级条件设置弹窗  //
    Integer call_time_b = null;// B通话时间  //
    Integer call_time_c = null;// C通话时间  //
    List<Integer> card_relate_list = new ArrayList<>();//机器人坐席列表  //
    List<ReqAddTask.CardSlotListBean> card_slot_list = new ArrayList<>();//营销对象列表数据   //
    List<Integer> customer_list = new ArrayList<>();//客户名单列表   //
    Integer exception_num = null;//  异常次数  //
    Integer is_default_key_template = null; // 关键字短信是否默认 0否 1是   //

    Integer is_default_message_template_a = null; //A级初筛短信模版是否默认 0否 1是   //
    Integer is_default_message_template_b = null; //B级初筛短信模版是否默认 0否 1是   //
    Integer is_default_message_template_c = null; //C级初筛短信模版是否默认 0否 1是   //

    Integer is_default_task_complete = null; //任务完成是否默认 0否1 是 //
    Integer is_default_task_exception = null; // 任务异常是否默认 0否1 是 //

    Integer is_default_time_template = 0; //时间模板是否默认 0否 1是

    Integer is_default_user_level_a = null; //A级初筛短信模版是否默认 0否 1是   //
    Integer is_default_user_level_b = null; //B级初筛短信模版是否默认 0否 1是   //
    Integer is_default_user_level_c = null; //C级初筛短信模版是否默认 0否 1是   //

    Integer is_default_whispering = 0; //话术是否默认 0否1 是  //

    Integer is_default_wx_a = null; // A级别微信推送是否默认 0否1 是 //
    Integer is_default_wx_b = null; // B级别微信推送是否默认 0否1 是 //
    Integer is_default_wx_c = null; // C级别微信推送是否默认 0否1 是 //

    Integer is_transfer = 0; //人工转接 0否 1是  //

    Integer key_open = null; //关键字短信是否开启 0 否 1是

    Integer key_template = null; // 关键字短信模版         //
    Integer message_template_a = null; // A级初筛短信模版  //
    Integer message_template_b = null; // B级初筛短信模版  //
    Integer message_template_c = null; // C级初筛短信模版  //

    String remarks = null; //备注  //
    List<ReqAddTask.SeatListBean> seat_list = null;//坐席列表   //
    Integer start_type = 1; //启动方式 1立即启动 2定时启动  //
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // 2019-01-04T07:46:54.848Z

    private String task_name = null;//任务名   //
    Integer time_template_id = null; //时间模版id   默认：系统时间模板 id:0  //


    Integer transfer_call_num = null; //通话轮次数(人工转接) ,  //
    Integer transfer_call_time = null; // 通话时间(人工转接) ,  //
    Integer transfer_key_num = null; //有效关键字数(人工转接) ,  //

    Integer valid_key_num_a = null; // A有效关键字数 , //
    Integer valid_key_num_b = null; // B有效关键字数 , //
    Integer valid_key_num_c = null; // C有效关键字数 , //

    Integer whispering_id = null; //话术id ,//
    String whispering_title = null; //话术名 ,//

    List<String> wx_complete_list = null;//任务异常完成销售人员ID //
    List<String> wx_exception_list = null;// 任务异常推送销售人员ID //

    List<String> wx_user_list_a = null;//A级微信人员ID  //
    List<String> wx_user_list_b = null;//B级微信人员ID  //
    List<String> wx_user_list_c = null;//C级微信人员ID  //

    /**
     * 提交
     */
    private void commit() {
        task_name = tvCreate0.getText().toString().trim();
        if (TextUtils.isEmpty(task_name)) {
            Toast.makeText(this, "请输入任务名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (whispering_id == null) {
            Toast.makeText(this, "请选择话术模版", Toast.LENGTH_SHORT).show();
            return;
        }

        if (yinruNum == 0 && shuruNum == 0 && daoruNum == 0) {
            Toast.makeText(this, "请填写客户号码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (card_relate_list.size() == 0) {
            Toast.makeText(this, "请选择外呼坐席", Toast.LENGTH_SHORT).show();
            return;
        }


        if (time_template_id == null) {
            Toast.makeText(this, "请选择时间模版", Toast.LENGTH_SHORT).show();
            return;
        }
        if (start_type == null) {
            Toast.makeText(this, "请选择启动方式", Toast.LENGTH_SHORT).show();
            return;
        }

        if(start_type==2){
            if(start_time==null){
                Toast.makeText(this, "请选择启动日期", Toast.LENGTH_SHORT).show();
                return;
            }
            if(end_time==null){
                Toast.makeText(this, "请选择启动时分", Toast.LENGTH_SHORT).show();
                return;
            }



        }


        if (voice_id == null) {
            Toast.makeText(this, "请选择语音识别", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isCheck) {
            is_transfer = 1;
        } else {
            is_transfer = 0;
        }


        if (is_transfer == 1) {
            if (mHumanCardRelate != null && mHumanCardRelate.size() == 0) {
                Toast.makeText(this, "请添加人工坐席", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        ReqAddTask mReqAddTask = new ReqAddTask();
        mReqAddTask.setCall_num_a(call_num_a);
        mReqAddTask.setCall_num_b(call_num_b);
        mReqAddTask.setCall_num_c(call_num_c);

        call_rate = Integer.valueOf(edcall_rate.getText().toString().trim());//呼叫频率
        mReqAddTask.setCall_rate(call_rate);

        mReqAddTask.setCall_time_a(call_time_a);
        mReqAddTask.setCall_time_b(call_time_b);
        mReqAddTask.setCall_time_c(call_time_c);

        mReqAddTask.setCard_relate_list(card_relate_list);

        card_slot_list.clear();
        for (CallNumber bean : mCallNumber) {
            card_slot_list.add(new ReqAddTask.CardSlotListBean(bean.moblie, bean.name));
        }
        for (CallNumber bean : mDaoRuNumber) {
            card_slot_list.add(new ReqAddTask.CardSlotListBean(bean.moblie, bean.name));
        }
        for (Customer bean : mYinRuNumber) {
            // card_slot_list.add(new ReqAddTask.CardSlotListBean(bean.moblie,bean.name)); ???调接口？
        }
        mReqAddTask.setCard_slot_list(card_slot_list);

        customer_list.clear();
        for (Customer bean : mYinRuNumber) {
            customer_list.add(bean.id);
        }
        mReqAddTask.setCustomer_list(customer_list);

        mReqAddTask.setDialect(voice_id);

        mReqAddTask.setException_num(exception_num);//选要在弹窗里增加？


        mReqAddTask.setIs_default_key_template(is_default_key_template);

        mReqAddTask.setIs_default_message_template_a(is_default_message_template_a);
        mReqAddTask.setIs_default_message_template_b(is_default_message_template_b);
        mReqAddTask.setIs_default_message_template_c(is_default_message_template_c);


        mReqAddTask.setIs_default_task_complete(is_default_task_complete);
        mReqAddTask.setIs_default_task_exception(is_default_task_exception);


        mReqAddTask.setIs_default_user_level_a(is_default_user_level_a);
        mReqAddTask.setIs_default_user_level_b(is_default_user_level_b);
        mReqAddTask.setIs_default_user_level_c(is_default_user_level_c);
        mReqAddTask.setIs_default_whispering(is_default_whispering);
        mReqAddTask.setIs_default_wx_a(is_default_wx_a);
        mReqAddTask.setIs_default_wx_b(is_default_wx_b);
        mReqAddTask.setIs_default_wx_c(is_default_wx_c);

        mReqAddTask.setKey_open(key_open);
        mReqAddTask.setKey_template(key_template);
        mReqAddTask.setMessage_template_a(message_template_a);
        mReqAddTask.setMessage_template_b(message_template_b);
        mReqAddTask.setMessage_template_c(message_template_c);

        remarks = edremarks.getText().toString().trim();
        mReqAddTask.setRemarks(remarks);


        mReqAddTask.setIs_transfer(is_transfer);

        if (is_transfer == 1) {
            if (mHumanCardRelate.size() > 0) {
                seat_list = new ArrayList<>();
            }

            for (HumanCardRelate bean : mHumanCardRelate) {
                seat_list.add(new ReqAddTask.SeatListBean(bean.user_id, bean.id, null));
            }
            mReqAddTask.setSeat_list(seat_list);
        }

        if (start_type == 2) {
            try {
                //yyyy-MM-dd HH:mm:ss
                String dateInString_ = mYear + "-" + mMonth + "-" + mDay+" "+mHour+":"+mMinute+":00" ;
                Date date = null;
                date = format.parse(dateInString_);
                String create_time_begin1 = format.format(date).toString();
                mReqAddTask.setStart_time(create_time_begin1);

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }




        mReqAddTask.setStart_type(start_type);
        mReqAddTask.setTask_name(task_name);


        mReqAddTask.setIs_default_time_template(is_default_time_template);
        mReqAddTask.setTime_template_id(time_template_id);

        mReqAddTask.setTransfer_call_num(transfer_call_num);
        mReqAddTask.setTransfer_call_time(transfer_call_time);
        mReqAddTask.setTransfer_key_num(transfer_key_num);
        mReqAddTask.setValid_key_num_a(valid_key_num_a);
        mReqAddTask.setValid_key_num_b(valid_key_num_b);
        mReqAddTask.setValid_key_num_c(valid_key_num_c);

        mReqAddTask.setWhispering_id(whispering_id);
        mReqAddTask.setWhispering_title(whispering_title);

        mReqAddTask.setWx_complete_list(wx_complete_list);
        mReqAddTask.setWx_exception_list(wx_exception_list);
        mReqAddTask.setWx_user_list_a(wx_user_list_a);
        mReqAddTask.setWx_user_list_b(wx_user_list_b);
        mReqAddTask.setWx_user_list_c(wx_user_list_c);


        mPresenter.createtask(mReqAddTask);

    }


}
