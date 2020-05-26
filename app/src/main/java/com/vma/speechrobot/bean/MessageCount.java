package com.vma.speechrobot.bean;

/**
 * Date: 2019/1/3
 * Author: libaibing
 * Email：
 * Des：
 */

public class MessageCount {


    /**
     * id : 0
     * message_all_count : 0
     * message_use_count : 0
     */

    private int id;
    private int message_all_count;
    private int message_use_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMessage_all_count() {
        return message_all_count;
    }

    public void setMessage_all_count(int message_all_count) {
        this.message_all_count = message_all_count;
    }

    public int getMessage_use_count() {
        return message_use_count;
    }

    public void setMessage_use_count(int message_use_count) {
        this.message_use_count = message_use_count;
    }
}
