package com.vma.speechrobot.presenter.impl;

import com.example.common.presenter.IBasePresenter;
import com.vma.speechrobot.bean.HomepageBean;
import com.vma.speechrobot.bean.StatisticsInfoBean;
import com.vma.speechrobot.bean.StatisticsLevel;

public interface IHomepagePresenter extends IBasePresenter {

  void getHomepage();
  void getHomepageStatisticsInfo(String date_type);
  void getHomepageStatisticsInfoLevel(String date_type);




  interface IView extends IBasePresenter.IView {

    void fillHomepage(HomepageBean homepage);
    void fillHomepageStatisticsInfo(StatisticsInfoBean homepage);
    void fillHomepageStatisticsInfoLevel(StatisticsLevel homepage);

  }

}
