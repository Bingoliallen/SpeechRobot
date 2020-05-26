package com.vma.speechrobot.bean;

import java.util.List;

public class PageBean<T> {

  /**
   * data_list (Array[AppCallRecordPage], optional): 分页数据 ,
   * page_num (integer, optional): 当前分页数 ,
   * page_size (integer, optional): 分页大小 ,
   * total_num (integer, optional): 总记录数
   */

  public int page_num;
  public int page_size;
  public int total_num;
  public List<T> data_list;
}
