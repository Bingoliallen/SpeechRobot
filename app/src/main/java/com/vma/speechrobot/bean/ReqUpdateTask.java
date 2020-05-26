package com.vma.speechrobot.bean;

/**
 * Created by Administrator on 2019/1/6.
 */

public class ReqUpdateTask {
    public ReqUpdateTask(Integer id, int status) {
        this.id = id;
        this.status = status;
    }

    public Integer id;
    public Integer status;

}
