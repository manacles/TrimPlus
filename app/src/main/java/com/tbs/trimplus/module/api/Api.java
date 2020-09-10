package com.tbs.trimplus.module.api;


import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.main.bean.Mine;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface Api {

    /**
     * 账号密码登录
     */
    @FormUrlEncoded
    @POST("tapp/passport/app_login")
    Observable<User> loginByPassword(@FieldMap Map<String, Object> params);

    /**
     * 我的界面数据获取
     */
    @FormUrlEncoded
    @POST("zapp/myself/index")
    Observable<BaseObject<Mine>> getMineData(@FieldMap Map<String, Object> params);



    @GET("getTitle")
    Observable<BaseObject<String>> getTitle(@Query("id") String titleId);

    /*get和post两种请求写法
     */

    /**
     * 账号密码登录
     *//*
        @FormUrlEncoded
        @POST("get")
        Observable<BaseObject<User>> LoginPswd(@FieldMap Map<String, Object> praise);


        @GET("pushMsg/getPushMessageList")
        Observable<ActionBean> PushMessageList(@Query("session") String session,
                                               @Query("pageIndex") String pageIndex,
                                               @Query("pageSize") String pageSize);

        */
}
