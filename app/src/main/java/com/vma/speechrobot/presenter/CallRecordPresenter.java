package com.vma.speechrobot.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.example.common.net.RxHelper;
import com.example.common.net.RxHelper.Response2DataFuncRESTful;
import com.example.common.presenter.impl.BasePresenter;
import com.vma.speechrobot.bean.CallRecordBean;
import com.vma.speechrobot.bean.Export;
import com.vma.speechrobot.bean.PageBean;
import com.vma.speechrobot.bean.ReqAddCallRecordRead;
import com.vma.speechrobot.model.TaskModel;
import com.vma.speechrobot.net.NetSubscriber;
import com.vma.speechrobot.presenter.impl.ICallRecordPresenter;
import com.vma.speechrobot.utils.UserInfoManager;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import rx.functions.Action1;
import rx.functions.Func1;

public class CallRecordPresenter extends
    BasePresenter<ICallRecordPresenter.IView, TaskModel> implements ICallRecordPresenter {

  public CallRecordPresenter(IView view) {
    super(view);
  }

  @Override
  protected TaskModel createModel() {
    return new TaskModel();
  }

  @Override
  public void getCallRecord(int pageNum, int pageSize, int id, String level, Integer result,
                            Integer start_call_count,
                            Integer end_call_count,
                            Integer start_call_all_time,
                            Integer end_call_all_time, Integer status) {
    String macKey = UserInfoManager.getMacKey(getView().getContext());
    if (TextUtils.isEmpty(macKey)) {
      return;
    }
    getCompositeSubscription().add(mModel
        .getCallRecord(macKey, pageNum, pageSize, id,
            level, result, start_call_count, end_call_count, start_call_all_time, end_call_all_time, status)
        .compose(RxHelper.io_main())
        .map(new Response2DataFuncRESTful())
        .subscribe(new NetSubscriber<PageBean<CallRecordBean>>(getView().getContext()) {
          @Override
          public void onNext(PageBean<CallRecordBean> callRecordBeanPageBean) {
            super.onNext(callRecordBeanPageBean);
            if (isAttach()) {
              getView().fillCallRecord(callRecordBeanPageBean);
            }
          }
        }));
  }

  @Override
  public void getCallRecordC(int pageNum, int pageSize, String id_or_mobile_or_card_mobile, String level,
                             Integer start_call_count,
                             Integer end_call_count,
                             Integer start_call_all_time,
                             Integer end_call_all_time , Integer status, Integer result,String start_time,String end_time) {
    String macKey = UserInfoManager.getMacKey(getView().getContext());
    if (TextUtils.isEmpty(macKey)) {
      return;
    }
    getCompositeSubscription().add(mModel
            .getCallRecordC(macKey,pageNum, pageSize, id_or_mobile_or_card_mobile,
                    level,start_call_count, end_call_count,start_call_all_time, end_call_all_time,status, result, start_time, end_time)
            .compose(RxHelper.io_main())
            .map(new Response2DataFuncRESTful())
            .subscribe(new NetSubscriber<PageBean<CallRecordBean>>(getView().getContext()) {
              @Override
              public void onNext(PageBean<CallRecordBean> callRecordBeanPageBean) {
                super.onNext(callRecordBeanPageBean);
                if (isAttach()) {
                  getView().fillCallRecordC(callRecordBeanPageBean);
                }
              }
            }));
  }

    @Override
    public void export(int pageNum, int pageSize, Integer task_id, String id_or_mobile_or_card_mobile, String level,
                       Integer start_call_count,
                       Integer end_call_count,
                       Integer start_call_all_time,
                       Integer end_call_all_time,  Integer status, Integer result,String start_time, String end_time) {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription().add(mModel
                .export(macKey,pageNum, pageSize, task_id , id_or_mobile_or_card_mobile,
                        level,start_call_count, end_call_count,start_call_all_time, end_call_all_time,status, result, start_time, end_time)
                .compose(RxHelper.io_main())
                .map(new Response2DataFuncRESTful())
                .subscribe(new NetSubscriber<Export>(getView().getContext()) {
                    @Override
                    public void onNext(Export callRecordBeanPageBean) {
                        super.onNext(callRecordBeanPageBean);
                        if (isAttach()) {
                            getView().fillexport(callRecordBeanPageBean);
                        }
                    }
                }));
    }

    @Override
    public void exportC(int pageNum, int pageSize, Integer task_id, String id_or_mobile_or_card_mobile, String level, Integer call_count, Integer call_all_time, Integer result, Integer status, String start_time, String end_time) {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription().add(mModel
                .exportC(macKey,pageNum, pageSize, task_id , id_or_mobile_or_card_mobile,
                        level,call_count, call_all_time,status, result, start_time, end_time)
                .compose(RxHelper.io_main())
                .map(new Response2DataFuncRESTful())
                .subscribe(new NetSubscriber<Export>(getView().getContext()) {
                    @Override
                    public void onNext(Export callRecordBeanPageBean) {
                        super.onNext(callRecordBeanPageBean);
                        if (isAttach()) {
                            getView().fillexportC(callRecordBeanPageBean);
                        }
                    }
                }));
    }

    @Override
    public void getRecordRead(ReqAddCallRecordRead req) {
        String macKey = UserInfoManager.getMacKey(getView().getContext());
        if (TextUtils.isEmpty(macKey)) {
            return;
        }
        getCompositeSubscription().add(mModel
                .getRecordRead(macKey,req)
                .compose(RxHelper.io_main())
                .map(new Response2DataFuncRESTful())
                .subscribe(new NetSubscriber<ResponseBody>(getView().getContext()) {
                    @Override
                    public void onNext(ResponseBody callRecordBeanPageBean) {
                        super.onNext(callRecordBeanPageBean);
                        if (isAttach()) {
                            getView().fillgetRecordRead();
                        }
                    }
                }));
    }
}
