package com.vma.speechrobot.bean;

public class CallRecordBean {

  /**
   * call_all_time (integer, optional): 通话时长 ,
   * call_count (integer, optional): 通话轮次 ,
   * call_num (integer, optional): 拨打客户次数 ,
   * call_result (integer, optional): 呼叫结果 1已接通 2 无法接通 3拒接 4外呼失败 ,
   * call_start_time (string, optional): 第一次呼叫时间 ,
   * id (integer, optional): id ,
   * mobile (string, optional): 客户号码 ,
   * status (integer, optional): 分配状态 0未分配 1已分配 ,
   * user_level (string, optional): 客户等级 A到F ,
   * user_name (string, optional): 客户姓名
   */

  public int call_all_time;
  public int call_count;
  public int call_num;
  public int call_result;
  public String call_start_time;
  public int id;
  public String mobile;
  public int status;
  public String user_level;
  public String user_name;
  public int is_read;


}
