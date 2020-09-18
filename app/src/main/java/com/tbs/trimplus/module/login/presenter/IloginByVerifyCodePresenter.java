package com.tbs.trimplus.module.login.presenter;

import java.util.Map;

public interface IloginByVerifyCodePresenter {
    /**
     * 短信验证码登录
     */
    void loginByVerifyCode(Map<String, Object> params);
}
