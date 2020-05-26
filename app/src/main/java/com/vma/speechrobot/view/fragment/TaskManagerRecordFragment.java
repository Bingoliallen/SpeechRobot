package com.vma.speechrobot.view.fragment;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.adapter.recycler.BaseRecyclerAdapter;
import com.example.common.presenter.impl.BasePresenter;
import com.example.common.utils.ViewUtil;
import com.example.common.utils.init.T;
import com.example.common.view.fragment.BaseFragment;
import com.vma.speechrobot.R;
import com.vma.speechrobot.adapter.CallRecordNewAdapter;
import com.vma.speechrobot.bean.CallRecordBean;
import com.vma.speechrobot.bean.Export;
import com.vma.speechrobot.bean.KeyValueBean;
import com.vma.speechrobot.bean.PageBean;
import com.vma.speechrobot.bean.ReqAddCallRecordRead;
import com.vma.speechrobot.presenter.CallRecordPresenter;
import com.vma.speechrobot.presenter.impl.ICallRecordPresenter;
import com.vma.speechrobot.view.activity.CallDetailNewActivity;
import com.vma.speechrobot.view.activity.CallRecordActivity;
import com.vma.speechrobot.view.activity.TaskManagerNewActivity;
import com.vma.speechrobot.widget.SpinerPopWindow;
import com.vma.speechrobot.widget.dialog.InputCallDialog;
import com.vma.speechrobot.widget.dialog.InputTimeDialog;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Date: 2018/12/27
 * Author: libaibing
 * Email：
 * Des：
 */

public class TaskManagerRecordFragment extends BaseFragment<CallRecordPresenter> implements
        ICallRecordPresenter.IView {

    @BindView(R.id.tvLevel)
    TextView tvLevel;

    @BindView(R.id.tvCount)
    LinearLayout tvCount;
    @BindView(R.id.tvTime)
    LinearLayout tvTime;

    @BindView(R.id.tvCount_)
    TextView tvCount_;
    @BindView(R.id.tvTime_)
    TextView tvTime_;

    @BindView(R.id.iv_delete0)
    ImageView iv_delete0;
    @BindView(R.id.iv_delete01)
    ImageView iv_delete01;


    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvResult)
    TextView tvResult;
    @BindView(R.id.tvPages)
    TextView tvPages;

    @BindView(R.id.tvexport)
    TextView tvexport;


    @BindView(R.id.mTask8)
    EditText mTask8;
    @BindView(R.id.iv_dingye)
    ImageView iv_dingye;
    @BindView(R.id.iv_shangyiye)
    ImageView iv_shangyiye;
    @BindView(R.id.iv_xiayiye)
    ImageView iv_xiayiye;
    @BindView(R.id.iv_diye)
    ImageView iv_diye;

    @BindView(R.id.recycler_view_call_record)
    RecyclerView recyclerViewCallRecord;

    CallRecordNewAdapter mCallRecordNewAdapter;
    //  protected List<CallRecordBean> mData=new ArrayList<>();


    private SpinerPopWindow mSpinerPopWindowLevel;
    private SpinerPopWindow mSpinerPopWindowCount;
    private SpinerPopWindow mSpinerPopWindowTime;
    private SpinerPopWindow mSpinerPopWindowStatus;
    private SpinerPopWindow mSpinerPopWindowResult;

    private String mLevel = null;
    //private Integer mCount = null;
    // private Integer mTime = null;
    private Integer mStatus = null;
    private Integer mResult = null;

    private int mId;
    private int mPageNum = 1;
    private int mPageSize = 10;
    private int mPages = 1;

    private Integer start_call_all_time = null;
    private Integer end_call_all_time = null;

    private Integer start_call_count = null;
    private Integer end_call_count = null;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_task__manager_record;
    }

    @Override
    protected CallRecordPresenter createPresenter() {
        return new CallRecordPresenter(this);
    }

    @Override
    protected void initView() {
        super.initView();


        mId = getArguments().getInt("id");

        List<KeyValueBean<String, String>> listLevel = new ArrayList<>();
        listLevel.add(new KeyValueBean(null, "全部"));
        listLevel.add(new KeyValueBean("A", "A级"));
        listLevel.add(new KeyValueBean("B", "B级"));
        listLevel.add(new KeyValueBean("C", "C级"));
        listLevel.add(new KeyValueBean("D", "D级"));
        listLevel.add(new KeyValueBean("E", "E级"));
        listLevel.add(new KeyValueBean("F", "F级"));
        mSpinerPopWindowLevel = new SpinerPopWindow<>(mContext, listLevel,
                new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<String, String>>() {

                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                      KeyValueBean<String, String> data, int position) {
                        mLevel = data.key;
                        if (data.key == null) {
                            tvLevel.setText(null);
                        } else {
                            tvLevel.setText(data.value);
                        }
                        refresh(mId);
                        mSpinerPopWindowLevel.dismiss();
                    }
                });

        List<KeyValueBean<Integer, String>> listCount = new ArrayList<>();
        listCount.add(new KeyValueBean(null, "全部"));
        listCount.add(new KeyValueBean(1, "0~3轮"));
        listCount.add(new KeyValueBean(2, "4~6轮"));
        listCount.add(new KeyValueBean(3, "7~10轮"));
        listCount.add(new KeyValueBean(4, "11~20轮"));
        listCount.add(new KeyValueBean(5, "21~30轮"));
        listCount.add(new KeyValueBean(6, "大于30轮"));
        mSpinerPopWindowCount = new SpinerPopWindow<>(mContext, listCount,
                new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<Integer, String>>() {
                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                      KeyValueBean<Integer, String> data, int position) {
                        // mCount = data.key;
                        if (data.key == null) {
                            tvCount_.setText(null);
                        } else {
                            tvCount_.setText(data.value);
                        }
                        refresh(mId);
                        mSpinerPopWindowCount.dismiss();
                    }
                });

        List<KeyValueBean<Integer, String>> listTime = new ArrayList<>();
        listTime.add(new KeyValueBean(null, "全部"));
        listTime.add(new KeyValueBean(1, "小于10秒"));
        listTime.add(new KeyValueBean(2, "10秒~20秒"));
        listTime.add(new KeyValueBean(3, "20秒~30秒"));
        listTime.add(new KeyValueBean(4, "30秒~40秒"));
        listTime.add(new KeyValueBean(5, "40秒~50秒"));
        listTime.add(new KeyValueBean(6, "50秒~1分钟"));
        listTime.add(new KeyValueBean(7, "1~2分钟"));
        listTime.add(new KeyValueBean(8, "大于2分钟"));
        mSpinerPopWindowTime = new SpinerPopWindow<>(mContext, listTime,
                new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<Integer, String>>() {
                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                      KeyValueBean<Integer, String> data, int position) {
                        // mTime = data.key;
                        if (data.key == null) {
                            tvTime_.setText(null);
                        } else {
                            tvTime_.setText(data.value);
                        }
                        refresh(mId);
                        mSpinerPopWindowTime.dismiss();
                    }
                });

        List<KeyValueBean<Integer, String>> listStatus = new ArrayList<>();
        listStatus.add(new KeyValueBean(null, "全部"));
        listStatus.add(new KeyValueBean(0, "未分配"));
        listStatus.add(new KeyValueBean(1, "已分配"));
        mSpinerPopWindowStatus = new SpinerPopWindow<>(mContext, listStatus,
                new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<Integer, String>>() {
                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                      KeyValueBean<Integer, String> data, int position) {
                        mStatus = data.key;
                        if (data.key == null) {
                            tvStatus.setText(null);
                        } else {
                            tvStatus.setText(data.value);
                        }
                        refresh(mId);
                        mSpinerPopWindowStatus.dismiss();
                    }
                });

        List<KeyValueBean<Integer, String>> listResult = new ArrayList<>();
        listResult.add(new KeyValueBean(null, "全部"));
        listResult.add(new KeyValueBean(1, "已接通"));
        listResult.add(new KeyValueBean(2, "无法接通"));
        listResult.add(new KeyValueBean(3, "拒接"));
        listResult.add(new KeyValueBean(4, "外呼失败"));
        listResult.add(new KeyValueBean(5, "白名单过滤"));
        listResult.add(new KeyValueBean(6, "D类客户过滤"));
        listResult.add(new KeyValueBean(7, "频率限制"));
        listResult.add(new KeyValueBean(8, "空号"));
        mSpinerPopWindowResult = new SpinerPopWindow<>(mContext, listResult,
                new BaseRecyclerAdapter.OnRecyclerItemClickListener<KeyValueBean<Integer, String>>() {
                    @Override
                    public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view,
                                                      KeyValueBean<Integer, String> data, int position) {
                        mResult = data.key;
                        if (data.key == null) {
                            tvResult.setText(null);
                        } else {
                            tvResult.setText(data.value);
                        }
                        refresh(mId);
                        mSpinerPopWindowResult.dismiss();
                    }
                });


        mCallRecordNewAdapter = new CallRecordNewAdapter(mContext);
        mCallRecordNewAdapter.setOnRecyclerItemClickListener(new BaseRecyclerAdapter.OnRecyclerItemClickListener<CallRecordBean>() {
            @Override
            public void onRecyclerItemClicked(BaseRecyclerAdapter adapter, View view, CallRecordBean data, int position) {
                mPresenter.getRecordRead(new ReqAddCallRecordRead(data.id));

                ArrayList<Integer> idList = new ArrayList<>();
                for (CallRecordBean bean : mCallRecordNewAdapter.getData()) {
                    idList.add(new Integer(bean.id));
                }

                CallDetailNewActivity.launch(mContext, idList, position);
            }
        });


        recyclerViewCallRecord
                .setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerViewCallRecord.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = ViewUtil.dp2px(mContext, 0);
                outRect.bottom = ViewUtil.dp2px(mContext, 0);
            }
        });
        recyclerViewCallRecord.setAdapter(mCallRecordNewAdapter);

        //  mCallRecordNewAdapter.set(mData);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mTask8.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

                if (arg1 == EditorInfo.IME_ACTION_SEARCH) {
                    mPageNum = Integer.valueOf(mTask8.getText().toString().trim());
                    mPresenter.getCallRecord(mPageNum, mPageSize, mId, mLevel, mResult, start_call_count, end_call_count, start_call_all_time, end_call_all_time, mStatus);
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
                    if (mPageNum1 > mPages) {
                        if (s.toString().trim().length() > 1) {
                            mTask8.setText(s.subSequence(0, s.toString().trim().length() - 1));
                            mTask8.setSelection(mTask8.getText().length());
                        } else {
                            mTask8.setText("" + mPages);
                            mTask8.setSelection(mTask8.getText().length());
                        }
                        Toast.makeText(mContext, "最多只有" + mPages + "页", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });


        tvLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                levelClick();
            }
        });

        tvCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // countClick();

                InputCallDialog mInputCallDialog = new InputCallDialog(mContext);
                mInputCallDialog.setStart_call_count(start_call_count);
                mInputCallDialog.setEnd_call_count(end_call_count);
                mInputCallDialog.setmInputCallDialogListener(new InputCallDialog.InputCallDialogListener() {
                    @Override
                    public void onClick(Integer cust_id1, Integer cust_id2) {
                        start_call_count = cust_id1;
                        end_call_count = cust_id2;
                        tvCount_.setText(start_call_count + "~" + end_call_count + "轮");

                        refresh(mId);


                    }
                });
                mInputCallDialog.show();

            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // timeClick();

                InputTimeDialog mInputTimeDialog = new InputTimeDialog(mContext);
                mInputTimeDialog.setStart_call_all_time(start_call_all_time);
                mInputTimeDialog.setEnd_call_all_time(end_call_all_time);
                mInputTimeDialog.setmInputTimeDialogListener(new InputTimeDialog.InputTimeDialogListener() {
                    @Override
                    public void onClick(Integer cust_id1, Integer cust_id2) {
                        start_call_all_time = cust_id1;
                        end_call_all_time = cust_id2;
                        tvTime_.setText(start_call_all_time + "~" + end_call_all_time + "秒");

                        refresh(mId);
                    }
                });
                mInputTimeDialog.show();

            }
        });

        tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusClick();
            }
        });

        tvResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultClick();
            }
        });

        tvexport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            346);
                } else {
                    mPresenter.export(mPageNum, mPageSize, mId, id_or_mobile_or_card_mobile,
                            mLevel, start_call_count, end_call_count, start_call_all_time, end_call_all_time, mResult, mStatus, start_time, end_time);

                }

            }
        });

        iv_dingye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageNum = 1;
                mTask8.setText("" + mPageNum);
                mTask8.setSelection(mTask8.getText().length());
                mPresenter.getCallRecord(mPageNum, mPageSize, mId, mLevel, mResult, start_call_count, end_call_count, start_call_all_time, end_call_all_time, mStatus);
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
                mPresenter.getCallRecord(mPageNum, mPageSize, mId, mLevel, mResult, start_call_count, end_call_count, start_call_all_time, end_call_all_time, mStatus);
            }
        });

        iv_xiayiye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageNum++;
                if (mPageNum > mPages) {
                    mPageNum = mPages;
                }
                mTask8.setText("" + mPageNum);
                mTask8.setSelection(mTask8.getText().length());
                mPresenter.getCallRecord(mPageNum, mPageSize, mId, mLevel, mResult, start_call_count, end_call_count, start_call_all_time, end_call_all_time, mStatus);
            }
        });


        iv_diye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPageNum = mPages;
                mTask8.setText("" + mPageNum);
                mTask8.setSelection(mTask8.getText().length());
                mPresenter.getCallRecord(mPageNum, mPageSize, mId, mLevel, mResult, start_call_count, end_call_count, start_call_all_time, end_call_all_time, mStatus);
            }
        });

        iv_delete0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvCount_.setText(null);
                start_call_count = null;
                end_call_count = null;
                refresh(mId);
            }
        });

        iv_delete01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvTime_.setText(null);

                start_call_all_time = null;
                end_call_all_time = null;
                refresh(mId);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {

        if (requestCode == 346) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.export(mPageNum, mPageSize, mId, id_or_mobile_or_card_mobile,
                        mLevel, start_call_count, end_call_count, start_call_all_time, end_call_all_time, mResult, mStatus, start_time, end_time);
            } else {
                // Permission Denied
            }
        }
    }

    private String id_or_mobile_or_card_mobile;
    private String start_time, end_time;

    @Override
    protected void initData() {
        super.initData();
        if (mId != -1) {
            mLevel = null;
            start_call_count = null;
            end_call_count = null;

            start_call_all_time = null;
            end_call_all_time = null;
            mStatus = null;
            mResult = null;

            mPageNum = 1;
            mPageSize = 10;

            refresh(mId);
        }
    }

    @Override
    protected boolean lazyLoad() {
        return super.lazyLoad();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void fillCallRecord(PageBean<CallRecordBean> data) {
        if (data != null) {
            mPages = (int) Math.ceil(data.total_num * 1.0 / data.page_size);
            tvPages.setText(String.format("共%d页", mPages));
            mCallRecordNewAdapter.set(data.data_list);

            if (mPages <= 0) {
                mPages = 1;
            }
            //   etPageNum.setText(String.valueOf(mPageNum));
        }
    }

    @Override
    public void fillCallRecordC(PageBean<CallRecordBean> data) {

    }

    @Override
    public void fillexport(Export data) {
        if (data != null && !TextUtils.isEmpty(data.getUrl())) {
            DownloadManager downloadManager = (DownloadManager) getContext()
                    .getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(data.getUrl()));
            //下载时，下载完成后显示通知
            //创建目录
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();

            request
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            //下载的路径，第一个参数是文件夹名称，第二个参数是下载的文件名
            request.setDestinationInExternalPublicDir(getString(R.string.app_name),
                    String.format("%s-%d-%s", "通话记录", mPageNum, ".xls"));
            request.setVisibleInDownloadsUi(true);
            downloadManager.enqueue(request);
        } else {
            T.showShort("导出数据出错");
        }
    }

    @Override
    public void fillexportC(Export data) {
        if (data != null && !TextUtils.isEmpty(data.getUrl())) {
            DownloadManager downloadManager = (DownloadManager) getContext()
                    .getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(data.getUrl()));
            //下载时，下载完成后显示通知
            request
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            //下载的路径，第一个参数是文件夹名称，第二个参数是下载的文件名
            request.setDestinationInExternalPublicDir(getString(R.string.app_name),
                    String.format("%s-%d", "通话记录", mPageNum));
            request.setVisibleInDownloadsUi(true);
            downloadManager.enqueue(request);
        } else {
            T.showShort("导出数据出错");
        }
    }

    @Override
    public void fillgetRecordRead() {
        refresh(mId);
    }

    public void refresh1(int mId) {
        tvLevel.setText(null);
        mLevel = null;

        tvStatus.setText(null);
        mStatus = null;

        tvResult.setText(null);
        mResult = null;

        tvCount_.setText(null);
        start_call_count = null;
        end_call_count = null;

        tvTime_.setText(null);
        start_call_all_time = null;
        end_call_all_time = null;


        this.mId = mId;
        mPresenter.getCallRecord(mPageNum, mPageSize, mId, mLevel, mResult, start_call_count, end_call_count, start_call_all_time, end_call_all_time, mStatus);
    }

    private void refresh(int mId) {
        this.mId = mId;
        mPresenter.getCallRecord(mPageNum, mPageSize, mId, mLevel, mResult, start_call_count, end_call_count, start_call_all_time, end_call_all_time, mStatus);
    }

    private void levelClick() {
        mSpinerPopWindowLevel.setWidth(tvLevel.getWidth());
        mSpinerPopWindowLevel.showAsDropDown(tvLevel);
    }

    private void countClick() {
        mSpinerPopWindowCount.setWidth(tvCount.getWidth());
        mSpinerPopWindowCount.showAsDropDown(tvCount);
    }

    private void timeClick() {
        mSpinerPopWindowTime.setWidth(tvTime.getWidth());
        mSpinerPopWindowTime.showAsDropDown(tvTime);
    }

    private void statusClick() {
        mSpinerPopWindowStatus.setWidth(tvStatus.getWidth());
        mSpinerPopWindowStatus.showAsDropDown(tvStatus);
    }

    private void resultClick() {
        mSpinerPopWindowResult.setWidth(tvResult.getWidth());
        mSpinerPopWindowResult.showAsDropDown(tvResult);
    }


}
