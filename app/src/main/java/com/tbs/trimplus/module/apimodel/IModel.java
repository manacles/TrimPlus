package com.tbs.trimplus.module.apimodel;


import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.main.bean.Mine;

import java.util.Map;

import io.reactivex.Observable;

public interface IModel {
    //账号密码登录
    Observable<User> loginByPassword(Map<String, Object> params);

    //获取Mine界面数据
    Observable<BaseObject<Mine>> getMineData(Map<String, Object> params);
}
