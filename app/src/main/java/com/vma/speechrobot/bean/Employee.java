package com.vma.speechrobot.bean;

/**
 * Created by Administrator on 2019/1/1.
 */

public class Employee {
    /*{
        "employee_name": "string",
            "id": 0
    }*/
    public int id;//员工id
    public String employee_name;
    public boolean isSelect;
    public int wx_user_id;

    public Employee(int id, String employee_name, boolean isSelect, int wx_user_id) {
        this.id = id;
        this.employee_name = employee_name;
        this.isSelect = isSelect;
        this.wx_user_id = wx_user_id;
    }
}
