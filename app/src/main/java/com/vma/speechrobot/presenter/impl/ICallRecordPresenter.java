package com.vma.speechrobot.presenter.impl;

import com.example.common.presenter.IBasePresenter;
import com.vma.speechrobot.bean.CallRecordBean;
import com.vma.speechrobot.bean.Export;
import com.vma.speechrobot.bean.PageBean;
import com.vma.speechrobot.bean.ReqAddCallRecordRead;

import org.json.JSONObject;

public interface ICallRecordPresenter extends IBasePresenter {

  void getCallRecord(int pageNum, int pageSize, int id, String level, Integer result, Integer start_call_count,
                     Integer end_call_count,
                     Integer start_call_all_time,
                     Integer end_call_all_time, Integer status);

  void getCallRecordC(int pageNum, int pageSize, String id_or_mobile_or_card_mobile, String level,
                      Integer start_call_count,
                      Integer end_call_count,
                      Integer start_call_all_time,
                      Integer end_call_all_time  , Integer result, Integer status,String start_time,String end_time);
  void export(int pageNum, int pageSize, Integer task_id  , String id_or_mobile_or_card_mobile, String level,
              Integer start_call_count,
              Integer end_call_count,
              Integer start_call_all_time,
              Integer end_call_all_time  , Integer result, Integer status,String start_time,String end_time);

  void exportC(int pageNum, int pageSize, Integer task_id  , String id_or_mobile_or_card_mobile, String level,
              Integer call_count,
              Integer call_all_time  , Integer result, Integer status,String start_time,String end_time);

  void getRecordRead(ReqAddCallRecordRead req);


  interface IView extends IBasePresenter.IView {

    void fillCallRecord(PageBean<CallRecordBean> data);
    void fillCallRecordC(PageBean<CallRecordBean> data);
    void fillexport(Export data);
    void fillexportC(Export data);

    void fillgetRecordRead();
  }

}
