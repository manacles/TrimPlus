package com.tbs.trimplus.module.api;


import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.common.bean.ResultList;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.main.bean.Bible;
import com.tbs.trimplus.module.main.bean.Mine;
import com.tbs.trimplus.module.user.bean.City;
import com.tbs.trimplus.module.user.bean.UserInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface Api {

    /**
     * 账号密码登录
     */
    @FormUrlEncoded
    @POST("tapp/passport/app_login")
    Observable<User> loginByPassword(@FieldMap Map<String, Object> params);

    /**
     * 短信验证码登录
     */
    @FormUrlEncoded
    @POST("tapp/passport/fast_register_mt")
    Observable<User> loginByVerifyCode(@FieldMap Map<String, Object> params);

    /**
     * 我的界面数据获取
     */
    @FormUrlEncoded
    @POST("zapp/myself/index")
    Observable<BaseObject<Mine>> getMineData(@FieldMap Map<String, Object> params);

    /**
     * 个人信息数据获取
     */
    @FormUrlEncoded
    @POST("zapp/myself/user_info")
    Observable<BaseObject<UserInfo>> getUserInfoData(@FieldMap Map<String, Object> params);


    /**
     * 修改个人信息（field：1.修改昵称）
     */
    @FormUrlEncoded
    @POST("tapp/user/chage_user_info")
    Observable<User> changeUserInfo(@FieldMap Map<String, Object> params);

    /**
     * 修改个人信息--性别
     */
    @FormUrlEncoded
    @POST("zapp/myself/set_gender")
    Observable<BaseObject> setGender(@FieldMap Map<String, Object> params);

    /**
     * 修改个人信息--装修阶段
     */
    @FormUrlEncoded
    @POST("zapp/myself/set_decorate")
    Observable<BaseObject> setDecorate(@FieldMap Map<String, Object> params);

    /**
     * 获取城市信息
     */
    @FormUrlEncoded
    @POST("tapp/util/change_city")
    Observable<ResultList<City>> getCity(@FieldMap Map<String, Object> params);

    /**
     * 获取历史记录
     */
    @FormUrlEncoded
    @POST("zapp/myself/history_record")
    Observable<BaseList<Bible>> getHistoryRecord(@FieldMap Map<String, Object> params);

    /**
     * 意见反馈
     */
    @FormUrlEncoded
    @POST("tapp/util/feedBackMt")
    Observable<ResultList> feedback(@FieldMap Map<String, Object> params);


    /*get和post两种请求写法
     */

    /*
        @FormUrlEncoded
        @POST("get")
        Observable<BaseObject<User>> LoginPswd(@FieldMap Map<String, Object> praise);

        @GET("getTitle")
        Observable<BaseObject<String>> getTitle(@Query("id") String titleId);


        @GET("pushMsg/getPushMessageList")
        Observable<ActionBean> PushMessageList(@Query("session") String session,
                                               @Query("pageIndex") String pageIndex,
                                               @Query("pageSize") String pageSize);

        */
}

