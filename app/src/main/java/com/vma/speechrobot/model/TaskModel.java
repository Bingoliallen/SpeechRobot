package com.vma.speechrobot.model;

import com.example.common.BaseAppProfile;
import com.vma.speechrobot.api.AppApi;
import com.vma.speechrobot.bean.ReqAddCallRecordRead;
import com.vma.speechrobot.bean.ReqAddTask;
import com.vma.speechrobot.bean.ReqUpdateTask;

import rx.Observable;

public class TaskModel {

  public Observable getRecordRead(String macKey,ReqAddCallRecordRead req) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .getRecordRead(macKey,req);
  }

  public Observable transferemployee(String macKey) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .transferemployee(macKey);
  }


  public Observable deletetask(String macKey,int id) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .deletetask(macKey,id);
  }

  public Observable updatetask(String macKey,ReqUpdateTask reqUpdateTask) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .updatetask(macKey,reqUpdateTask);
  }


  public Observable createtask(String macKey,ReqAddTask mReqAddTask) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .createtask(macKey,mReqAddTask);
  }

  public Observable taskdefault(String macKey) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .taskdefault(macKey);
  }
  /*public Observable customerDeatil(Integer id,String macKey) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .customerDeatil(id,macKey);
  }



  public Observable firm_task(String macKey) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .firm_task(macKey);
  }*/

  public Observable customer_list(String macKey,Integer is_use ,
                                  Integer page_num,Integer page_size) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .customer_list(macKey,is_use,page_num,page_size);
  }



  public Observable getHumanCardRelate(String macKey) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .getHumanCardRelate(macKey);
  }

  public Observable taskTimeTemplateOption(String macKey) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .taskTimeTemplateOption(macKey);
  }


  public Observable message_count(String macKey) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .message_count(macKey);
  }

  public Observable messageTemplate(String macKey,Integer type) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .messageTemplate(macKey,type);
  }

  public Observable employee(String macKey,String employee_name) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .employee(macKey,employee_name);
  }

  public Observable getFreeTaskCardOption(String macKey) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .getFreeTaskCardOption(macKey);
  }

  public Observable getWhisperingOption(String macKey) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .getWhisperingOption(macKey);
  }

  public Observable getTaskCardOption(String macKey) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .getTaskCardOption(macKey);
  }

  public Observable getTaskManangerList(String macKey,
                                        Integer status,
                                        String card_relate_id,
                                        String whispering_id,
                                        String create_time_begin,
                                        String create_time_end,
                                        String idOrName, int pageNum, int pageSize) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .getTaskManangerList(macKey, status,
                    card_relate_id, whispering_id, create_time_begin, create_time_end, idOrName, pageNum, pageSize);
  }

  public Observable getTaskList(String macKey, int pageNum, int pageSize, String idOrName,
      Integer status) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
        .getTaskList(macKey, pageNum, pageSize, idOrName, status);
  }

  public Observable getCallRecord(String macKey, int pageNum, int pageSize, int id, String level,
      Integer result,  Integer start_call_count,
                         Integer end_call_count,
                        Integer start_call_all_time,
                       Integer end_call_all_time, Integer status) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
        .getCallRecord(macKey, pageNum, pageSize, id, level, result,
                start_call_count, end_call_count,
                start_call_all_time, end_call_all_time,status);
  }


  public Observable getCallRecordC(String macKey, int pageNum, int pageSize, String id_or_mobile_or_card_mobile, String level,
                                   Integer start_call_count,
                                   Integer end_call_count,
                                  Integer start_call_all_time,
                                   Integer end_call_all_time ,   Integer status, Integer result,String start_time,String end_time) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .getCallRecordC(macKey, pageNum, pageSize, id_or_mobile_or_card_mobile,
                    level,start_call_count,
                    end_call_count,
                    start_call_all_time,
                    end_call_all_time,status, result, start_time, end_time);
  }


  public Observable export(String macKey, int pageNum, int pageSize, Integer task_id  , String id_or_mobile_or_card_mobile, String level,
                           Integer start_call_count,
                           Integer end_call_count,
                           Integer start_call_all_time,
                           Integer end_call_all_time  , Integer status, Integer result,String start_time,String end_time) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .export(macKey, pageNum, pageSize ,task_id , id_or_mobile_or_card_mobile,
                    level, start_call_count,
                     end_call_count,
                     start_call_all_time,
                     end_call_all_time,status, result, start_time, end_time);
  }


  public Observable exportC(String macKey, int pageNum, int pageSize, Integer task_id  , String id_or_mobile_or_card_mobile, String level,
                           Integer call_count,
                           Integer call_all_time, Integer status, Integer result,String start_time,String end_time) {
    return BaseAppProfile.app_client.getApi(AppApi.class)
            .exportC(macKey, pageNum, pageSize ,task_id , id_or_mobile_or_card_mobile,
                    level, call_count,
                    call_all_time,status, result, start_time, end_time);
  }




  public Observable getCallDetail(String macKey, Integer id) {
    return BaseAppProfile.app_client.getApi(AppApi.class).getCallDetail(macKey, id);
  }

  public Observable updateCallTime(String macKey, int id) {
    return BaseAppProfile.app_client.getApi(AppApi.class).updateCallNum(macKey, id);
  }

  public Observable getTaskInfo(String macKey, int id) {
    return BaseAppProfile.app_client.getApi(AppApi.class).getTaskInfo(macKey, id);
  }

  public Observable getTaskDetail(String macKey, int id) {
    return BaseAppProfile.app_client.getApi(AppApi.class).getTaskDetail(macKey, id);
  }
}
