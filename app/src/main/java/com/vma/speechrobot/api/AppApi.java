package com.vma.speechrobot.api;

import com.vma.speechrobot.bean.CallDetailBean;
import com.vma.speechrobot.bean.CallRecordBean;
import com.vma.speechrobot.bean.CreateTask;
import com.vma.speechrobot.bean.Customer;
import com.vma.speechrobot.bean.CustomerDeatil;
import com.vma.speechrobot.bean.Employee;
import com.vma.speechrobot.bean.Export;
import com.vma.speechrobot.bean.FirmTask;
import com.vma.speechrobot.bean.HomepageBean;
import com.vma.speechrobot.bean.MessageCount;
import com.vma.speechrobot.bean.MessageTemplate;
import com.vma.speechrobot.bean.PageBean;
import com.vma.speechrobot.bean.ReqAddCallRecordRead;
import com.vma.speechrobot.bean.ReqAddTask;
import com.vma.speechrobot.bean.ReqUpdateTask;
import com.vma.speechrobot.bean.StatisticsInfoBean;
import com.vma.speechrobot.bean.StatisticsLevel;
import com.vma.speechrobot.bean.TaskBean;
import com.vma.speechrobot.bean.TaskCardOption;
import com.vma.speechrobot.bean.TaskDefault;
import com.vma.speechrobot.bean.TaskDetailBean;
import com.vma.speechrobot.bean.TaskInfoBean;
import com.vma.speechrobot.bean.TimeTemplateOption;
import com.vma.speechrobot.bean.UserInfoBean;
import com.vma.speechrobot.bean.WhisperingOption;
import com.vma.speechrobot.bean.request.FindPwdBean;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface AppApi {

    /**
     * 获取通话详情
     */
    @GET("v1.0/call_record/{id}")
    Observable<Response<CallDetailBean>> getCallDetail(@Header("mac_key") String macKey,
                                                       @Path("id") Integer id);

 /* *//**
     * 获取通话详情
     *//*
  @GET("v1.0/app/call_detail")
  Observable<Response<CallDetailBean>> getCallDetail(@Header("mac_key") String macKey,
      @Query("id") String id);
*/

    /**
     * 获取通话记录
     */
    @GET("v1.0/call_record")
    Observable<Response<PageBean<CallRecordBean>>> getCallRecord(
            @Header("mac_key") String macKey,
            @Query("page_num") int pageNum,
            @Query("page_size") int pageSize,
            @Query("task_id") int taskId,
            @Query("user_level") String userLevel,
            @Query("call_result") Integer callResult,
            @Query("start_call_count") Integer start_call_count,
            @Query("end_call_count") Integer end_call_count,
            @Query("start_call_all_time") Integer start_call_all_time,
            @Query("end_call_all_time") Integer end_call_all_time,
            @Query("status") Integer status);


    /**
     * 新增通话记录阅读记录
     */
    @POST("v1.0/call_record/read")
    Observable<Response<ResponseBody>> getRecordRead(
            @Header("mac_key") String macKey,
            @Body ReqAddCallRecordRead req);

    /**
     * 获取通话记录
     * //  task_id 营销任务
     * //  is_transfer
     */
    @GET("v1.0/call_record")
    Observable<Response<PageBean<CallRecordBean>>> getCallRecordC(
            @Header("mac_key") String macKey,
            @Query("page_num") int pageNum,
            @Query("page_size") int pageSize,
            @Query("id_or_mobile_or_card_mobile") String id_or_mobile_or_card_mobile,
            @Query("user_level") String userLevel,
            @Query("start_call_count") Integer start_call_count,
            @Query("end_call_count") Integer end_call_count,
            @Query("start_call_all_time") Integer start_call_all_time,
            @Query("end_call_all_time") Integer end_call_all_time,
            @Query("status") Integer status,
            @Query("call_result") Integer callResult,
            @Query("start_time") String start_time,
            @Query("end_time") String end_time);

    /**
     * 导出通话记录
     */
    @GET("v1.0/call_record/android_export")
    Observable<Response<Export>> exportC(
            @Header("mac_key") String macKey,
            @Query("page_num") int pageNum,
            @Query("page_size") int pageSize,

            @Query("task_id") Integer task_id,

            @Query("id_or_mobile_or_card_mobile") String id_or_mobile_or_card_mobile,
            @Query("user_level") String userLevel,
            @Query("call_count") Integer call_count,
            @Query("call_all_time") Integer call_all_time,
            @Query("status") Integer status,
            @Query("call_result") Integer callResult,
            @Query("start_time") String start_time,
            @Query("end_time") String end_time);


    /**
     * 导出通话记录
     */
    @GET("v1.0/call_record/android_export")
    Observable<Response<Export>> export(
            @Header("mac_key") String macKey,
            @Query("page_num") int pageNum,
            @Query("page_size") int pageSize,

            @Query("task_id") Integer task_id,

            @Query("id_or_mobile_or_card_mobile") String id_or_mobile_or_card_mobile,
            @Query("user_level") String userLevel,
            @Query("start_call_count") Integer start_call_count,
            @Query("end_call_count") Integer end_call_count,
            @Query("start_call_all_time") Integer start_call_all_time,
            @Query("end_call_all_time") Integer end_call_all_time,
            @Query("status") Integer status,
            @Query("call_result") Integer callResult,
            @Query("start_time") String start_time,
            @Query("end_time") String end_time);


    /**
     * 找回密码
     */
    @POST("v1.0/app/findPwd")
    Observable<Response<ResponseBody>> findPwd(@Body FindPwdBean request);

    /**
     * 发送短信
     */
    @GET("v1.0/app/message")
    Observable<Response<ResponseBody>> getCode(@Query("mobile") String mobile);

    /**
     * 首页
     */
    @GET("v1.0/app/home_page")
    Observable<Response<HomepageBean>> getHomepage(@Header("mac_key") String macKey);


    /**
     * 获取通话次数统计列表
     * 请求 URL: http://47.98.159.55:50555/enterprise-business/v1.0/statistics_info?date_type=3&__=1546132296689
     */
    @GET("v1.0/statistics_info")
    Observable<Response<StatisticsInfoBean>> getHomepageStatisticsInfo(@Header("mac_key") String macKey,
                                                                       @Query("date_type") String date_type);


    /**
     * 获取客户等级统计列表
     */
    @GET("v1.0/statistics_info/level")
    Observable<Response<StatisticsLevel>> getHomepageStatisticsInfoLevel(@Header("mac_key") String macKey,
                                                                         @Query("date_type") String date_type);


    /**
     * 登录
     */
    @GET("v1.0/app/login")
    Observable<Response<UserInfoBean>> login(@Query("account") String account,
                                             @Query("password") String password, @Query("admin_type") int adminType,
                                             @Query("login_type") int loginType);

    /**
     * 获取任务列表
     */
    @GET("v1.0/app/task")
    Observable<Response<PageBean<TaskBean>>> getTaskList(@Header("mac_key") String macKey,
                                                         @Query("page_num") int pageNum, @Query("page_size") int pageSize,
                                                         @Query("id_or_name") String idOrName, @Query("status") Integer status);


    /**
     * 获取任务列表
     */
    @GET("v1.0/task")
    Observable<Response<PageBean<TaskBean>>> getTaskManangerList(
            @Header("mac_key") String macKey,
            @Query("status") Integer status,
            @Query("card_relate_id") String card_relate_id,
            @Query("whispering_id") String whispering_id,
            @Query("create_time_begin") String create_time_begin,
            @Query("create_time_end") String create_time_end,
            @Query("id_or_name") String idOrName,
            @Query("page_num") int pageNum,
            @Query("page_size") int pageSize);


    /**
     * 机器人坐席列表
     */
    @GET("v1.0/task/getTaskCardOption")
    Observable<Response<TaskCardOption[]>> getTaskCardOption(
            @Header("mac_key") String macKey);

    /**
     * 话术列表
     */
    @GET("v1.0/task/whisperingOption")
    Observable<Response<WhisperingOption[]>> getWhisperingOption(
            @Header("mac_key") String macKey);

    /**
     * 获取空闲机器人坐席列表接口
     */
    @GET("v1.0/task/getFreeTaskCardOption")
    Observable<Response<TaskCardOption[]>> getFreeTaskCardOption(
            @Header("mac_key") String macKey);


    /**
     * 获取销售人员
     */
    @GET("v1.0/task/employee")
    Observable<Response<Employee[]>> employee(
            @Header("mac_key") String macKey,
            @Query("employee_name") String employee_name
    );


    /**
     * 获取短信模版
     */
    @GET("v1.0/task/messageTemplate")
    Observable<Response<MessageTemplate[]>> messageTemplate(
            @Header("mac_key") String macKey,
            @Query("type") Integer type
    );


    /**
     * 获取企业短信数
     */
    @GET("v1.0/task/message_count")
    Observable<Response<MessageCount>> message_count(
            @Header("mac_key") String macKey
    );

    /**
     * 时间模板列表
     */
    @GET("v1.0/task/taskTimeTemplateOption")
    Observable<Response<TimeTemplateOption[]>> taskTimeTemplateOption(
            @Header("mac_key") String macKey
    );

    /**
     * 获取空闲人工坐席列表
     */
    @GET("v1.0/task/transfer/getHumanCardRelate")
    Observable<Response<TaskCardOption[]>> getHumanCardRelate(
            @Header("mac_key") String macKey
    );


    /**
     * 获取员工列表
     */
    @GET("v1.0/task/transfer/employee")
    Observable<Response<Employee[]>> transferemployee(
            @Header("mac_key") String macKey
    );


    /**
     * 客户名单-->获取客户名单列表
     */
    @GET("v1.0/task/customerListOption")
    Observable<Response<PageBean<Customer>>> customer_list(
            @Header("mac_key") String macKey,
            @Query("is_use") Integer is_use,
            @Query("page_num") Integer page_num,
            @Query("page_size") Integer page_size
    );


    /**
     * 默认设置
     */
    @GET("v1.0/task/default")
    Observable<Response<TaskDefault>> taskdefault(
            @Header("mac_key") String macKey
    );


    /**
     * 创建任务
     */
    @POST("v1.0/task")
    Observable<Response<CreateTask>> createtask(
            @Header("mac_key") String macKey,
            @Body ReqAddTask reqAddTask

    );

    /**
     * 编辑任务
     */
    @PUT("v1.0/task/status")
    Observable<Response<ResponseBody>> updatetask(
            @Header("mac_key") String macKey,
            @Body ReqUpdateTask req

    );

    /**
     * 删除任务信息
     */
    @DELETE("v1.0/task/{id}")
    Observable<Response<ResponseBody>> deletetask(
            @Header("mac_key") String macKey,
            @Path("id") Integer id

    );


 /* *//**
     * 获取企业下任务列表
     *//*
  @GET("v1.0/customer_info/firm_task")
  Observable<Response<FirmTask[]>> firm_task(
          @Header("mac_key") String macKey
  );


  *//**
     * 客户管理->基础信息->获取客户详情
     *//*
  @GET("v1.0/customer_info/{id}")
  Observable<Response<CustomerDeatil>> customerDeatil(
          @Path("id") Integer id,
          @Header("mac_key") String macKey
  );*/

 /* v1.0/task/whisperingOption 话术列表
  v1.0/task/getFreeTaskCardOption  机器人坐席列表
  v1.0/task/employee  获取销售人员
  v1.0/task/messageTemplate 获取短信模版
  v1.0/task/message_count 获取企业短信数
  v1.0/task/taskTimeTemplateOption 时间模板列表
  v1.0/task/transfer/getHumanCardRelate 获取空闲人工坐席列表
  v1.0/customer_list 客户名单-->获取客户名单列表
v1.0/task/default


  v1.0/customer_info 客户管理->基础信息->获取客户列表
  v1.0/customer_info/firm_task 客户管理->基础信息->查询->获取企业下任务列表(下拉)
  /v1.0/customer_info/{id} 客户管理->基础信息->获取客户详情
  */

    /**
     * 任务基础信息
     */
    @GET("v1.0/app/task_info")
    Observable<Response<TaskInfoBean>> getTaskInfo(@Header("mac_key") String macKey,
                                                   @Query("id") int id);

    /**
     * 任务详情信息
     */
    @GET("v1.0/app/task_detail")
    Observable<Response<TaskDetailBean>> getTaskDetail(@Header("mac_key") String macKey,
                                                       @Query("id") int id);

    /**
     * 更新拨打次数
     */
    @GET("v1.0/app/update_call_num")
    Observable<Response<ResponseBody>> updateCallNum(@Header("mac_key") String macKey,
                                                     @Query("id") int id);
}
