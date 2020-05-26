package com.vma.speechrobot.bean;

/**
 * Created by Administrator on 2019/1/6.
 */

public class CardSloBean {
    private String mobile;
    private String user_name;
    public  String remark;

    public CardSloBean(String mobile, String user_name) {
        this.mobile = mobile;
        this.user_name = user_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
