package com.vma.speechrobot.bean;

import java.util.List;

public class CallDetailBean {

  /**
   * all_autio (string, optional): 全程录音 ,
   * call_all_time (integer, optional): 通话时长 ,
   * call_count (integer, optional): 通话轮次 ,
   * call_label (string, optional): 通话标签 ,
   * call_result (string, optional): 呼叫结果 ,
   * call_start_time (string, optional): 呼叫时间 ,
   * card_mobile (string, optional): 机器坐席号码 ,
   * id (integer, optional): 通话记录id ,
   * keyword_list (Array[string], optional): 关键字列表 ,
   * mobile (string, optional): 客户电话 ,
   * record_details_list (Array[CallRecordDetails], optional): 通话记录详情列表 ,
   * status (integer, optional): 分配状态 0未分配 1已分配 ,
   * task_name (string, optional): 任务名称 ,
   * user_level (string, optional): 用户等级 ,
   * user_name (string, optional): 客户名字 ,
   * whispering_title (string, optional): 话术名称
   */

  public String all_autio;
  public int call_all_time;
  public int call_count;

  public String call_label;
  public String call_result;
  public String call_start_time;
  public String card_mobile;
  public int id;
  public String mobile;
  public int status;
  public String task_name;
  public String user_level;
  public String user_name;
  public String whispering_title;
  public List<String> keyword_list;
  public List<KeyWordBean> keywords;
  public List<RecordDetailsListBean> record_details_list;

  public static class RecordDetailsListBean {

    /**
     * call_audio (string, optional): 通话音频 ,
     * call_content (string, optional): 通话内容 ,
     * call_name (string, optional): 具体显示名字（为空，则显示默认） ,
     * call_type (integer, optional): 主叫方 1 系统 2客户
     */
    public static final int SYSTEM = 1;
    public static final int CUSTOMER = 2;

    public String call_audio;
    public String call_content;
    public String call_name;
    public int call_type;
  }

  public static class KeyWordBean {

    /**
     * key_word (string, optional): 关键字 ,
     * status (integer, optional): 是否有效 0无效 1有效
     */
    public static final int AVAILABLE = 1;
    public static final int UNAVAILABLE = 0;

    public String key_word;
    public Integer status;
  }
}
