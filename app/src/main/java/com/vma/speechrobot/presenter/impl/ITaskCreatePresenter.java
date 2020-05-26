package com.vma.speechrobot.presenter.impl;

import com.example.common.presenter.IBasePresenter;
import com.vma.speechrobot.bean.CreateTask;
import com.vma.speechrobot.bean.Customer;
import com.vma.speechrobot.bean.Employee;
import com.vma.speechrobot.bean.MessageCount;
import com.vma.speechrobot.bean.MessageTemplate;
import com.vma.speechrobot.bean.PageBean;
import com.vma.speechrobot.bean.ReqAddTask;
import com.vma.speechrobot.bean.TaskCardOption;
import com.vma.speechrobot.bean.TaskDefault;
import com.vma.speechrobot.bean.TimeTemplateOption;
import com.vma.speechrobot.bean.WhisperingOption;

/**
 * Created by Administrator on 2018/12/31.
 */

public interface ITaskCreatePresenter  extends IBasePresenter {

    void transferemployee();
    void createtask(ReqAddTask mReqAddTask);
    void taskdefault();
    void message_count();
    void messageTemplate(Integer type);
    void messageTemplate2(Integer type);
    void employee(String employee_name);
    void getFreeTaskCardOption();
    void getWhisperingOption();
    void getTaskCardOption();
    void taskTimeTemplateOption();
    void getHumanCardRelate();
 //   void firm_task();
    void customer_list(Integer is_use ,
                       Integer page_num,Integer page_size);
  //  void customerDeatil(Integer id);

    interface IView extends IBasePresenter.IView {

        void fillgetTaskCardOption(TaskCardOption[] data);
        void fillgetWhisperingOption(WhisperingOption[] data);
        void fillgetFreeTaskCardOption(TaskCardOption[] data);
        void fillemployee(Employee[] data);
        void fillmessage_count(MessageCount data);
        void fillmessageTemplate(MessageTemplate[] data);
        void fillmessageTemplate2(MessageTemplate[] data);
        void filltaskTimeTemplateOption(TimeTemplateOption[] data);
        void fillgetHumanCardRelate(TaskCardOption[] data);
        void fillcustomer_list(PageBean<Customer> data);
        //void fillfirm_task(FirmTask[] data);
       // void fillcustomerDeatil(CustomerDeatil data);
        void filltaskdefault(TaskDefault data);
        void fillcreatetask(CreateTask data);
        void filltransferemployee(Employee[] data);
    }
}
