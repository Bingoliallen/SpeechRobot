package com.vma.speechrobot.model;

import com.example.common.BaseAppProfile;
import com.example.common.net.HttpConfig;
import com.vma.speechrobot.api.AppApi;
import rx.Observable;

public class HomepageModel {

  public Observable getHomepage(String macKey) {
    return BaseAppProfile.app_client.getApi(AppApi.class).getHomepage(macKey);
  }

  public Observable getHomepageStatisticsInfo(String macKey,String date_type) {
    return BaseAppProfile.app_client.getApi(AppApi.class).getHomepageStatisticsInfo(macKey,date_type);
  }

  public Observable getHomepageStatisticsInfoLevel(String macKey,String date_type) {
    return BaseAppProfile.app_client.getApi(AppApi.class).getHomepageStatisticsInfoLevel(macKey,date_type);
  }



}
