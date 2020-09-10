package com.tbs.trimplus.module.login.view;

import com.tbs.trimplus.common.ICommonView;
import com.tbs.trimplus.module.login.bean.User;

public interface IloginByPasswordView extends ICommonView {
    /**
     * 密码登录
     */
    void loginByPassword(User user);
}
