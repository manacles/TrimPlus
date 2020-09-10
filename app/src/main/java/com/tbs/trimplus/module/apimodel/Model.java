package com.tbs.trimplus.module.apimodel;

import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.api.Api;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.main.bean.Mine;
import com.tbs.trimplus.utils.ApiUtil;

import java.util.Map;

import io.reactivex.Observable;

public class Model implements IModel {

    private Api api;

    private Api ApiInstance() {
        if (api != null) {
            return api;
        } else {
            return ApiUtil.getInstance().createRetrofitApi(Api.class);
        }
    }

    //账号密码登录
    @Override
    public Observable<User> loginByPassword(Map<String, Object> params) {
        api = ApiInstance();
        return api.loginByPassword(params);
    }

    //获取Mine界面数据
    @Override
    public Observable<BaseObject<Mine>> getMineData(Map<String, Object> params) {
        api = ApiInstance();
        return api.getMineData(params);
    }
}