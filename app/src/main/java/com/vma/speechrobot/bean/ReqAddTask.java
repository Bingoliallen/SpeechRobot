package com.vma.speechrobot.bean;

import java.util.List;

/**
 * Date: 2019/1/3
 * Author: libaibing
 * Email：
 * Des：
 */

public class ReqAddTask {



  /*
    ReqAddTask {
        call_num_a (integer, optional): A通话轮次数 ,
                call_num_b (integer, optional): B通话轮次数 ,
                call_num_c (integer, optional): C通话轮次数 ,
                call_rate (integer, optional): 拨打频率 ,
                call_time_a (integer, optional): A通话时间 ,
                call_time_b (integer, optional): B通话时间 ,
                call_time_c (integer, optional): C通话时间 ,
                card_relate_list (Array[integer]): 机器人坐席列表 ,
                card_slot_list (Array[营销对象]): 营销对象列表数据 ,
                customer_list (Array[integer], optional): 客户名单列表 ,
                dialect (string, optional): 方言 普通话 mandarin 粤语 cantonese 四川话 lmz ,
                exception_num (integer, optional): 异常次数 ,
                is_default_key_template (integer, optional): 关键字短信是否默认 0否 1是 ,
                is_default_message_template_a (integer, optional): A级初筛短信模版是否默认 0否 1是 ,
                is_default_message_template_b (integer, optional): B级初筛短信模版是否默认 0否 1是 ,
                is_default_message_template_c (integer, optional): C级初筛短信模版是否默认 0否 1是 ,
                is_default_task_complete (integer, optional): 任务完成是否默认 0否1 是 ,
        is_default_task_exception (integer, optional): 任务异常是否默认 0否1 是 ,
        is_default_time_template (integer, optional): 时间模板是否默认 0否 1是 ,
                is_default_user_level_a (integer, optional): A级初筛短信模版是否默认 0否 1是 ,
                is_default_user_level_b (integer, optional): B级初筛短信模版是否默认 0否 1是 ,
                is_default_user_level_c (integer, optional): C级初筛短信模版是否默认 0否 1是 ,

                is_default_whispering (integer, optional): 话术是否默认 0否1 是 ,
        is_default_wx_a (integer, optional): A级别微信推送是否默认 0否1 是 ,
        is_default_wx_b (integer, optional): B级别微信推送是否默认 0否1 是 ,
        is_default_wx_c (integer, optional): C级别微信推送是否默认 0否1 是 ,
        is_transfer (integer): 人工转接 0否 1是 ,
                key_open (integer, optional): 关键字短信是否开启 0 否 1是 ,
                key_template (integer, optional): 关键字短信模版 ,
                message_template_a (integer, optional): A级初筛短信模版 ,
                message_template_b (integer, optional): B级初筛短信模版 ,
                message_template_c (integer, optional): C级初筛短信模版 ,

                remarks (string, optional): 备注 ,
                seat_list (Array[ReqTaskTransfer], optional): 坐席列表 ,
                start_time (string, optional): 定时开始时间 定时启动时必填 ,
        start_type (integer): 启动方式 1立即启动 2定时启动 ,
                task_name (string): 任务名 ,
                time_template_id (integer): 时间模版id ,

                transfer_call_num (integer, optional): 通话轮次数(人工转接) ,
                transfer_call_time (integer, optional): 通话时间(人工转接) ,
                transfer_key_num (integer, optional): 有效关键字数(人工转接) ,
                valid_key_num_a (integer, optional): A有效关键字数 ,
                valid_key_num_b (integer, optional): B有效关键字数 ,
                valid_key_num_c (integer, optional): C有效关键字数 ,

                whispering_id (integer): 话术id ,
                whispering_title (string): 话术名 ,
                wx_complete_list (Array[string], optional): 任务异常完成销售人员ID ,
                wx_exception_list (Array[string], optional): 任务异常推送销售人员ID ,
                wx_user_list_a (Array[string], optional): A级微信人员ID ,
                wx_user_list_b (Array[string], optional): B级微信人员ID ,
                wx_user_list_c (Array[string], optional): C级微信人员ID
    }营销对象 {
        mobile (string): 客户号码 ,
                user_name (string): 客户姓名
    }ReqTaskTransfer {
        employee_id (integer, optional): 员工ID ,
                human_id (integer, optional): 人工坐席ID ,
                robot_id (integer): 机器坐席ID
    }*/

    private Integer call_num_a;
    private Integer call_num_b;
    private Integer call_num_c;
    private Integer call_rate;
    private Integer call_time_a;
    private Integer call_time_b;
    private Integer call_time_c;
    private String dialect;
    private Integer exception_num;
    private Integer is_default_key_template;
    private Integer is_default_message_template_a;
    private Integer is_default_message_template_b;
    private Integer is_default_message_template_c;
    private Integer is_default_task_complete;
    private Integer is_default_task_exception;
    private Integer is_default_time_template;
    private Integer is_default_user_level_a;
    private Integer is_default_user_level_b;
    private Integer is_default_user_level_c;
    private Integer is_default_whispering;
    private Integer is_default_wx_a;
    private Integer is_default_wx_b;
    private Integer is_default_wx_c;
    private Integer is_transfer;
    private Integer key_open;
    private Integer key_template;
    private Integer message_template_a;
    private Integer message_template_b;
    private Integer message_template_c;
    private String remarks;
    private String start_time;
    private Integer start_type;
    private String task_name;
    private Integer time_template_id;
    private Integer transfer_call_num;
    private Integer transfer_call_time;
    private Integer transfer_key_num;
    private Integer valid_key_num_a;
    private Integer valid_key_num_b;
    private Integer valid_key_num_c;
    private Integer whispering_id;
    private String whispering_title;
    private List<Integer> card_relate_list;
    private List<CardSlotListBean> card_slot_list;
    private List<Integer> customer_list;
    private List<SeatListBean> seat_list;
    private List<String> wx_complete_list;
    private List<String> wx_exception_list;
    private List<String> wx_user_list_a;
    private List<String> wx_user_list_b;
    private List<String> wx_user_list_c;

    public Integer getCall_num_a() {
        return call_num_a;
    }

    public void setCall_num_a(Integer call_num_a) {
        this.call_num_a = call_num_a;
    }

    public Integer getCall_num_b() {
        return call_num_b;
    }

    public void setCall_num_b(Integer call_num_b) {
        this.call_num_b = call_num_b;
    }

    public Integer getCall_num_c() {
        return call_num_c;
    }

    public void setCall_num_c(Integer call_num_c) {
        this.call_num_c = call_num_c;
    }

    public Integer getCall_rate() {
        return call_rate;
    }

    public void setCall_rate(Integer call_rate) {
        this.call_rate = call_rate;
    }

    public Integer getCall_time_a() {
        return call_time_a;
    }

    public void setCall_time_a(Integer call_time_a) {
        this.call_time_a = call_time_a;
    }

    public Integer getCall_time_b() {
        return call_time_b;
    }

    public void setCall_time_b(Integer call_time_b) {
        this.call_time_b = call_time_b;
    }

    public Integer getCall_time_c() {
        return call_time_c;
    }

    public void setCall_time_c(Integer call_time_c) {
        this.call_time_c = call_time_c;
    }

    public String getDialect() {
        return dialect;
    }

    public void setDialect(String dialect) {
        this.dialect = dialect;
    }

    public Integer getException_num() {
        return exception_num;
    }

    public void setException_num(Integer exception_num) {
        this.exception_num = exception_num;
    }

    public Integer getIs_default_key_template() {
        return is_default_key_template;
    }

    public void setIs_default_key_template(Integer is_default_key_template) {
        this.is_default_key_template = is_default_key_template;
    }

    public Integer getIs_default_message_template_a() {
        return is_default_message_template_a;
    }

    public void setIs_default_message_template_a(Integer is_default_message_template_a) {
        this.is_default_message_template_a = is_default_message_template_a;
    }

    public Integer getIs_default_message_template_b() {
        return is_default_message_template_b;
    }

    public void setIs_default_message_template_b(Integer is_default_message_template_b) {
        this.is_default_message_template_b = is_default_message_template_b;
    }

    public Integer getIs_default_message_template_c() {
        return is_default_message_template_c;
    }

    public void setIs_default_message_template_c(Integer is_default_message_template_c) {
        this.is_default_message_template_c = is_default_message_template_c;
    }

    public Integer getIs_default_task_complete() {
        return is_default_task_complete;
    }

    public void setIs_default_task_complete(Integer is_default_task_complete) {
        this.is_default_task_complete = is_default_task_complete;
    }

    public Integer getIs_default_task_exception() {
        return is_default_task_exception;
    }

    public void setIs_default_task_exception(Integer is_default_task_exception) {
        this.is_default_task_exception = is_default_task_exception;
    }

    public Integer getIs_default_time_template() {
        return is_default_time_template;
    }

    public void setIs_default_time_template(Integer is_default_time_template) {
        this.is_default_time_template = is_default_time_template;
    }

    public Integer getIs_default_user_level_a() {
        return is_default_user_level_a;
    }

    public void setIs_default_user_level_a(Integer is_default_user_level_a) {
        this.is_default_user_level_a = is_default_user_level_a;
    }

    public Integer getIs_default_user_level_b() {
        return is_default_user_level_b;
    }

    public void setIs_default_user_level_b(Integer is_default_user_level_b) {
        this.is_default_user_level_b = is_default_user_level_b;
    }

    public Integer getIs_default_user_level_c() {
        return is_default_user_level_c;
    }

    public void setIs_default_user_level_c(Integer is_default_user_level_c) {
        this.is_default_user_level_c = is_default_user_level_c;
    }

    public Integer getIs_default_whispering() {
        return is_default_whispering;
    }

    public void setIs_default_whispering(Integer is_default_whispering) {
        this.is_default_whispering = is_default_whispering;
    }

    public Integer getIs_default_wx_a() {
        return is_default_wx_a;
    }

    public void setIs_default_wx_a(Integer is_default_wx_a) {
        this.is_default_wx_a = is_default_wx_a;
    }

    public Integer getIs_default_wx_b() {
        return is_default_wx_b;
    }

    public void setIs_default_wx_b(Integer is_default_wx_b) {
        this.is_default_wx_b = is_default_wx_b;
    }

    public Integer getIs_default_wx_c() {
        return is_default_wx_c;
    }

    public void setIs_default_wx_c(Integer is_default_wx_c) {
        this.is_default_wx_c = is_default_wx_c;
    }

    public Integer getIs_transfer() {
        return is_transfer;
    }

    public void setIs_transfer(Integer is_transfer) {
        this.is_transfer = is_transfer;
    }

    public Integer getKey_open() {
        return key_open;
    }

    public void setKey_open(Integer key_open) {
        this.key_open = key_open;
    }

    public Integer getKey_template() {
        return key_template;
    }

    public void setKey_template(Integer key_template) {
        this.key_template = key_template;
    }

    public Integer getMessage_template_a() {
        return message_template_a;
    }

    public void setMessage_template_a(Integer message_template_a) {
        this.message_template_a = message_template_a;
    }

    public Integer getMessage_template_b() {
        return message_template_b;
    }

    public void setMessage_template_b(Integer message_template_b) {
        this.message_template_b = message_template_b;
    }

    public Integer getMessage_template_c() {
        return message_template_c;
    }

    public void setMessage_template_c(Integer message_template_c) {
        this.message_template_c = message_template_c;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public Integer getStart_type() {
        return start_type;
    }

    public void setStart_type(Integer start_type) {
        this.start_type = start_type;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public Integer getTime_template_id() {
        return time_template_id;
    }

    public void setTime_template_id(Integer time_template_id) {
        this.time_template_id = time_template_id;
    }

    public Integer getTransfer_call_num() {
        return transfer_call_num;
    }

    public void setTransfer_call_num(Integer transfer_call_num) {
        this.transfer_call_num = transfer_call_num;
    }

    public Integer getTransfer_call_time() {
        return transfer_call_time;
    }

    public void setTransfer_call_time(Integer transfer_call_time) {
        this.transfer_call_time = transfer_call_time;
    }

    public Integer getTransfer_key_num() {
        return transfer_key_num;
    }

    public void setTransfer_key_num(Integer transfer_key_num) {
        this.transfer_key_num = transfer_key_num;
    }

    public Integer getValid_key_num_a() {
        return valid_key_num_a;
    }

    public void setValid_key_num_a(Integer valid_key_num_a) {
        this.valid_key_num_a = valid_key_num_a;
    }

    public Integer getValid_key_num_b() {
        return valid_key_num_b;
    }

    public void setValid_key_num_b(Integer valid_key_num_b) {
        this.valid_key_num_b = valid_key_num_b;
    }

    public Integer getValid_key_num_c() {
        return valid_key_num_c;
    }

    public void setValid_key_num_c(Integer valid_key_num_c) {
        this.valid_key_num_c = valid_key_num_c;
    }

    public Integer getWhispering_id() {
        return whispering_id;
    }

    public void setWhispering_id(Integer whispering_id) {
        this.whispering_id = whispering_id;
    }

    public String getWhispering_title() {
        return whispering_title;
    }

    public void setWhispering_title(String whispering_title) {
        this.whispering_title = whispering_title;
    }

    public List<Integer> getCard_relate_list() {
        return card_relate_list;
    }

    public void setCard_relate_list(List<Integer> card_relate_list) {
        this.card_relate_list = card_relate_list;
    }

    public List<CardSlotListBean> getCard_slot_list() {
        return card_slot_list;
    }

    public void setCard_slot_list(List<CardSlotListBean> card_slot_list) {
        this.card_slot_list = card_slot_list;
    }

    public List<Integer> getCustomer_list() {
        return customer_list;
    }

    public void setCustomer_list(List<Integer> customer_list) {
        this.customer_list = customer_list;
    }

    public List<SeatListBean> getSeat_list() {
        return seat_list;
    }

    public void setSeat_list(List<SeatListBean> seat_list) {
        this.seat_list = seat_list;
    }

    public List<String> getWx_complete_list() {
        return wx_complete_list;
    }

    public void setWx_complete_list(List<String> wx_complete_list) {
        this.wx_complete_list = wx_complete_list;
    }

    public List<String> getWx_exception_list() {
        return wx_exception_list;
    }

    public void setWx_exception_list(List<String> wx_exception_list) {
        this.wx_exception_list = wx_exception_list;
    }

    public List<String> getWx_user_list_a() {
        return wx_user_list_a;
    }

    public void setWx_user_list_a(List<String> wx_user_list_a) {
        this.wx_user_list_a = wx_user_list_a;
    }

    public List<String> getWx_user_list_b() {
        return wx_user_list_b;
    }

    public void setWx_user_list_b(List<String> wx_user_list_b) {
        this.wx_user_list_b = wx_user_list_b;
    }

    public List<String> getWx_user_list_c() {
        return wx_user_list_c;
    }

    public void setWx_user_list_c(List<String> wx_user_list_c) {
        this.wx_user_list_c = wx_user_list_c;
    }

    public static class CardSlotListBean {
        public CardSlotListBean(String mobile, String user_name) {
            this.mobile = mobile;
            this.user_name = user_name;
        }

        /**
         * mobile : string
         * user_name : string
         */

        private String mobile;
        private String user_name;


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

    public static class SeatListBean {
        public SeatListBean(Integer employee_id, Integer human_id, Integer robot_id) {
            this.employee_id = employee_id;
            this.human_id = human_id;
            this.robot_id = robot_id;
        }

        /**
         * employee_id : 0
         * human_id : 0
         * robot_id : 0
         */

        private Integer employee_id;
        private Integer human_id;
        private Integer robot_id;

        public Integer getEmployee_id() {
            return employee_id;
        }

        public void setEmployee_id(Integer employee_id) {
            this.employee_id = employee_id;
        }

        public Integer getHuman_id() {
            return human_id;
        }

        public void setHuman_id(Integer human_id) {
            this.human_id = human_id;
        }

        public Integer getRobot_id() {
            return robot_id;
        }

        public void setRobot_id(Integer robot_id) {
            this.robot_id = robot_id;
        }
    }
}
