package com.tbs.trimplus.config;

public class AppConfig {

    //正式环境
//    public static final String TOBOSU_URL = "https://www.tobosu.com";
//    public static final String M_TOBOSU_URL = "https://m.tobosu.com/";

    //测试环境
    public static final String TOBOSU_URL = "http://www.dev.tobosu.com/";
    private static final String M_TOBOSU_URL = "http://m.dev.tobosu.com/";


    //获取图形验证码（未 使用）:图形验证码验证有问题，跳过图形验证，直接短信验证
    public static final String PIC_CODE_URL = TOBOSU_URL + "tapp/passport/get_pic_code";

    //发送短信验证码
    public static final String APP_SMS_CODE = TOBOSU_URL + "tapp/passport/app_sms_code";

    /*** 上传头像的接口*/
    public static final String UPLOAD_ICON = TOBOSU_URL + "cloud/upload/upload_for_ke?";
    //修改用户头像
    public static final String UPLOADHEADPICTUREURL = TOBOSU_URL + "tapp/user/chage_user_info";

    // TODO: 2018/6/26 发单页地址******************************************
    private static final String APP_TYPE = "&app_type=2";     //app_type 1是土拨鼠; 2是装好家

    //免费报价
    public static final String FREE_PRICE_PAGE = M_TOBOSU_URL + "free_price_page?channel=app&subchannel=android" + APP_TYPE;
    //免费设计
    public static final String QUOTE = M_TOBOSU_URL + "quote?channel=app&subchannel=android" + APP_TYPE;
}
