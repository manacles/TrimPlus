package com.tbs.trimplus.config;

public class AppConfig {

    //正式环境
//    public static final String TOBOSU_URL = "https://www.tobosu.com";

    //测试环境
    public static final String TOBOSU_URL = "http://www.dev.tobosu.com/";


    //获取图形验证码（未 使用）:图形验证码验证有问题，跳过图形验证，直接短信验证
    public static final String PIC_CODE_URL = TOBOSU_URL + "tapp/passport/get_pic_code";

    //发送短信验证码
    public static final String APP_SMS_CODE = TOBOSU_URL + "tapp/passport/app_sms_code";


}
