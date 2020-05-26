package com.vma.speechrobot.bean;

import java.util.List;

public class TaskInfoBean {


  /**
   * admin_name (string, optional): 创建人 ,
   * call_count (Array[RspMapValue], optional): 对话轮次 ,
   * call_level (Array[RspMapValue], optional): 意向客户等级 ,
   * call_progress (string, optional): 外呼进度 ,
   * call_result (Array[RspMapValue], optional): 接听状态 ,
   * call_success_rate (string, optional): 电话接通率 ,
   * call_time (Array[RspMapValue], optional): 通话时长 ,
   * create_time (string, optional): 任务创建的时间 ,
   * id (integer, optional): 任务id ,
   * status (integer, optional): 任务进度类型 0未开始 1进行中 2暂停 3终止 4完成 ,
   * task_name (string, optional): 任务名称
   */

  public String admin_name;
  public String call_progress;
  public String call_success_rate;
  public String create_time;
  public int id;
  public int status;
  public String task_name;
  public List<KeyNameBean> call_count;
  public List<KeyNameBean> call_level;
  public List<KeyNameBean> call_result;
  public List<KeyNameBean> call_time;
}
