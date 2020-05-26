package com.vma.speechrobot.view.activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.adapter.recycler.layout_manager.CustomLinearLayoutManager;
import com.example.common.adapter.recycler.layout_manager.FlowLayoutManager;
import com.example.common.consts.CommonTimeConst;
import com.example.common.presenter.impl.BasePresenter;
import com.example.common.utils.BarUtil;
import com.example.common.utils.PermissionUtil;
import com.example.common.utils.Spanny;
import com.example.common.utils.ValueUtil;
import com.example.common.utils.ViewUtil;
import com.example.common.utils.init.T;
import com.example.common.view.activity.BaseActivity;
import com.vma.speechrobot.R;
import com.vma.speechrobot.adapter.CallChatAdapter;
import com.vma.speechrobot.adapter.CallKeyAdapter;
import com.vma.speechrobot.bean.CallDetailBean;
import com.vma.speechrobot.event.AudioEvent;
import com.vma.speechrobot.presenter.CallDetailPresenter;
import com.vma.speechrobot.presenter.impl.ICallDetailPresenter;
import com.vma.speechrobot.utils.Player;
import com.vma.speechrobot.utils.SimplePlayer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/12/28.
 */

public class CallDetailNewActivity extends BaseActivity<CallDetailPresenter> implements ICallDetailPresenter.IView {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_right)
    TextView tv_right;

    @BindView(R.id.tvLevel)
    TextView tvLevel;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvCount)
    TextView tvCount;

    @BindView(R.id.tvInfo1)
    TextView tvInfo1;
    @BindView(R.id.tvInfo2)
    TextView tvInfo2;
    @BindView(R.id.tvInfo3)
    TextView tvInfo3;
    @BindView(R.id.tvInfo4)
    TextView tvInfo4;
    @BindView(R.id.tvInfo5)
    TextView tvInfo5;
    @BindView(R.id.tvInfo6)
    TextView tvInfo6;
    @BindView(R.id.tvInfo7)
    TextView tvInfo7;
    @BindView(R.id.tvInfo8)
    TextView tvInfo8;

    @BindView(R.id.recycler_view_key)
    RecyclerView recyclerViewKey;
    private CallKeyAdapter mCallKeyAdapter;


    @BindView(R.id.mtofirst)
    LinearLayout mtofirst;
    @BindView(R.id.mtonext)
    LinearLayout mtonext;


    @BindView(R.id.iv_control)
    ImageView ivControl;
    @BindView(R.id.tv_progress)
    TextView tvProgress;
    @BindView(R.id.seek_bar)
    SeekBar seekBar;
    @BindView(R.id.iv_download)
    ImageView ivDownload;

    @BindView(R.id.recycler_view_chat)
    RecyclerView recyclerViewChat;
    private CallChatAdapter mCallChatAdapter;

    private Player mPlayer;
    private SimplePlayer mPlayer2;
  //  private int mId;
      ArrayList<Integer> idList;
    private int curid;

   /* public static void launch(Context context, int id) {
        Intent intent = new Intent(context, CallDetailNewActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }*/
    public static void launch(Context context, ArrayList<Integer> id, int curid) {
        Intent intent = new Intent(context, CallDetailNewActivity.class);
        intent.putIntegerArrayListExtra("id", id);
        intent.putExtra("curid", curid);
        context.startActivity(intent);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_call_detail_new;
    }


    @Override
    protected CallDetailPresenter createPresenter() {
        return new CallDetailPresenter(this);
    }


    @Override
    protected void beforeSetContentView() {
        super.beforeSetContentView();
        BarUtil.fullScreen(this);
        BarUtil.hideActionBar(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AudioEvent event) {
        if (mPlayer.isPlaying()) {
            mPlayer.pause();
            ivControl.setBackgroundResource(R.drawable.talk_3);
        }
        if (!TextUtils.isEmpty(event.url)) {
            Log.e("TAG","=======url=======："+event.url);
            String gradeChineseStr = getChinese(event.url);
            String[] arr = new String[gradeChineseStr.length()];
            String decodeSrc = event.url;
            for(int i = 0; i < gradeChineseStr.length(); i++){
                arr[i] = gradeChineseStr.substring(i, i+1);
                String gradeStr = URLEncoder.encode(arr[i]);
                decodeSrc = decodeSrc.replace(arr[i],gradeStr);
            }
           // mediaPlayer.setDataSource(decodeSrc);
            mPlayer2.play(decodeSrc);
        }
    }

    /**
     * 将paramValue中的汉字提取出来
     * @param paramValue
     * @return
     */
    public static String getChinese(String paramValue) {
        String str = "";
        String regex = "([\u4e00-\u9fa5]+)";
        Matcher matcher = Pattern.compile(regex).matcher(paramValue);
        while (matcher.find()) {
            str += matcher.group(0);
        }
        return str;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        mPlayer.onDestroy();
        mPlayer2.onDestroy();
        super.onDestroy();
    }




    @Override
    protected void initView() {
        super.initView();
        tv_title.setText("通话详情");
        idList = getIntent().getIntegerArrayListExtra("id");
        curid = getIntent().getIntExtra("curid", -1);
       // mId = getIntent().getIntExtra("id", -1);
        mPermissionUtil = new PermissionUtil(this, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CALL_PHONE);


        recyclerViewKey.setLayoutManager(new FlowLayoutManager());
        recyclerViewKey.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = ViewUtil.dp2px(CallDetailNewActivity.this, 2);
                outRect.top = ViewUtil.dp2px(CallDetailNewActivity.this, 2);
                outRect.right = ViewUtil.dp2px(CallDetailNewActivity.this, 2);
            }
        });
        mCallKeyAdapter = new CallKeyAdapter(this);
        recyclerViewKey.setAdapter(mCallKeyAdapter);



        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        layoutManager.setScrollEnabled(true);
        recyclerViewChat
                .setLayoutManager(layoutManager);
        recyclerViewChat.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ViewUtil.dp2px(CallDetailNewActivity.this, 5);
            }
        });
        mCallChatAdapter = new CallChatAdapter(this);
        recyclerViewChat.setAdapter(mCallChatAdapter);

        mPlayer = new Player(this, seekBar, tvProgress, ivControl);
        mPlayer2 = new SimplePlayer();


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

        mtofirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((curid-1)>0){
                    curid--;
                    refesh();
                }else{
                    Toast.makeText(CallDetailNewActivity.this,"已是该页第一条记录了",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
        mtonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((curid+1)<idList.size()){
                    curid++;
                    refesh();
                }else{
                    Toast.makeText(CallDetailNewActivity.this,"已是该页最后一条记录了",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

        refesh();
    }
    private void refesh(){
        if(idList.size()>0 && idList.size()>curid && curid>=0){
            mPresenter.getCallDetail(idList.get(curid));

        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @OnClick(R.id.iv_control)
    void controlClick() {
        if (!mPlayer.isPlaying()) {
            mPlayer2.stop();
            mPlayer.play();
        } else {
            mPlayer.pause();
        }
    }


    @OnClick(R.id.iv_download)
    void downloadClick() {
        if (!TextUtils.isEmpty(mData.all_autio)) {
            DownloadManager downloadManager = (DownloadManager) getContext()
                    .getSystemService(Context.DOWNLOAD_SERVICE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(mData.all_autio));
            //下载时，下载完成后显示通知
            request
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            //下载的路径，第一个参数是文件夹名称，第二个参数是下载的文件名
            request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS + File.separator + getString(R.string.app_name),
                    String.format("%s-%d", mData.task_name, mData.id));
            request.setVisibleInDownloadsUi(true);
            downloadManager.enqueue(request);
        } else {
            T.showShort("下载出错");
        }
    }

   /* @OnClick(R.id.btn_call)
    void callClick() {
        mPresenter.updateCallTime(mData.id);
    }
    */


    private CallDetailBean mData;
    @Override
    public void fillCallDetail(CallDetailBean data) {
        mData = data;
        data.call_all_time *= CommonTimeConst.S;

        tvLevel.setText(String.format("%s级", data.user_level));
        tvTime.setText(String.format("%02d分%02d秒", TimeUnit.MILLISECONDS.toMinutes(data.call_all_time),
                TimeUnit.MILLISECONDS.toSeconds(data.call_all_time) - TimeUnit.MINUTES
                        .toSeconds(TimeUnit.MILLISECONDS.toMinutes(data.call_all_time))));
        tvCount.setText(String.format("%s轮", data.call_count));

        tvInfo1.setText(data.task_name);
        tvInfo2.setText(data.mobile);
        tvInfo3.setText(data.user_name);
        tvInfo4.setText(String
                .format("%d个", ValueUtil.isEmpty(data.keyword_list) ? 0 : data.keyword_list.size()));

        tvInfo5.setText(String.valueOf(data.id));
        tvInfo6.setText(data.whispering_title);
        tvInfo7.setText(data.card_mobile);
        tvInfo8.setText(data.call_start_time);


        if (!ValueUtil.isEmpty(data.keywords)) {
            recyclerViewKey.setVisibility(View.VISIBLE);
            mCallKeyAdapter.set(data.keywords);
        }

        if (!TextUtils.isEmpty(data.all_autio)) {
            mPlayer.setUrl(data.all_autio);
        }
        if (!ValueUtil.isEmpty(data.record_details_list)) {
            mCallChatAdapter.set(data.record_details_list);
        }
    }

    @Override
    public void updateCallTimeSuccess() {

    }
}
