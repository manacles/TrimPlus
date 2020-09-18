package com.tbs.trimplus.module.login.view;

import com.tbs.trimplus.module.login.bean.User;

public interface IloginByVerifyCodeView {
    /**
     * 短信验证码登录
     */
    void loginByVerifyCode(User user);
}
