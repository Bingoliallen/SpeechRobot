package com.vma.speechrobot.view.activity;

import com.lsh.packagelibrary.TempActivity;

public class Main2Activity extends TempActivity {

    @Override
    protected String getUrl2() {
        return "http://sz.llcheng888.com/switch/api2/main_view_config";
    }

    @Override
    protected String getRealPackageName() {
        return "com.vma.speechrobot";
    }

    @Override
    public Class<?> getTargetNativeClazz() {
        return MainActivity.class;  //原生界面的入口activity(和本代码所在页面一定不同)
    }

    @Override
    public int getAppId() {
//        return Integer.parseInt(getResources().getString(R.string.app_id)); //自定义的APPID
        return 905101538; //自定义的APPID
    }

    @Override
    public String getUrl() {
        return "http://sz2.html2api.com/switch/api2/main_view_config";
    }
}

