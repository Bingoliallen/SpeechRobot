package com.vma.speechrobot.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/12/30.
 */

public class StatisticsInfoBean {

   /* {
        "statistice_list": [
        {
            "call_all_count": 0,
                "call_error": 0,
                "call_fail": 0,
                "call_refuse": 0,
                "call_success": 0,
                "sdate": "string"
        }
  ],
        "today_count": 0,
            "yesterday_count": 0
    }*/

    public List<StatisticsInfo> statistice_list;
    public int today_count;
    public int yesterday_count;

}
