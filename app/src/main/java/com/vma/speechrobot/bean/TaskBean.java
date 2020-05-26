package com.vma.speechrobot.bean;

public class TaskBean {

  /**
   * admin_name (string, optional): 创建人 ,
   * card_id (integer, optional): 卡槽id ,
   * card_relate_id (integer, optional): 机器坐席id ,
   * complete_user_count (integer, optional): 完成用户数 ,
   * create_time (string, optional): 任务创建的时间 ,
   * id (integer, optional): 任务id ,
   * mobile (string, optional): 机器坐席号码 ,
   * status (integer, optional): 任务进度类型 0未开始 1进行中 2暂停 3终止 4完成 ,
   * task_name (string, optional): 任务名称 ,
   * user_count (integer, optional): 用户总数
   */

  public String admin_name;
  public int card_id;
  public int card_relate_id;
  public int complete_user_count;
  public String create_time;
  public int id;
  public String mobile;
  public int status;
  public String task_name;
  public int user_count;
  public int is_exception;
  public int is_new;
  public int is_over_time;
  public int is_sleep;
  public boolean isSelect=false;
}
