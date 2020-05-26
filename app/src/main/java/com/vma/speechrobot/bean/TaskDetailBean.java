package com.vma.speechrobot.bean;

import java.util.List;

public class TaskDetailBean {

  /**
   * call_num (integer, optional): 通话轮次数 ,
   * call_time (integer, optional): 通话时间 ,
   * dialect (string, optional): 方言 ,
   * exclude_date (string, optional): 排除的日期 ,
   * id (integer, optional): 任务id ,
   * key_open (integer, optional): 关键字短信是否开启 0 否 1是 ,
   * key_template_name (string, optional): 关键字短信模版名称 ,
   * message_level (string, optional): 初筛短信级别 ,
   * message_template (integer, optional): 初筛短信模版 ,
   * message_template_name (string, optional): 初筛短信模版名称 ,
   * mobile (string, optional): 机器坐席号码 ,
   * start_time (string, optional): 启动时间 ,
   * start_type (integer, optional): 启动方式 1立即启动 2定时启动 ,
   * status (integer, optional): 任务进度类型 0未开始 1进行中 2暂停 3终止 4完成 ,
   * task_name (string, optional): 任务名称 ,
   * task_time (string, optional): 外呼时间 ,
   * valid_key_num (integer, optional): 有效关键字数 ,
   * week_date (string, optional): 星期集合 1周一 2周二 以此类推 ,
   * whispering_title (string, optional): 话术标题 ,
   * wx_level (string, optional): 初筛微信推送级别 ,
   * wx_user_id (string, optional): 销售人员
   * * wx_user_id (string, optional): 销售人员名称
   */

  public int call_num;
  public int call_time;
  public String dialect;
  public String exclude_date;
  public int id;
  public int key_open;
  public String key_template_name;
  public String message_level;
  public int message_template;
  public String message_template_name;
  public String mobile;
  public String start_time;
  public int start_type;
  public int status;
  public String task_name;
  public String task_time;
  public int valid_key_num;
  public String week_date;
  public String whispering_title;
  public String wx_level;
  public String wx_user_id;
  public List<String> wx_user_list;
}
