package com.tbs.trimplus.module.apimodel;

import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.common.bean.ResultList;
import com.tbs.trimplus.module.api.Api;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.main.bean.Bible;
import com.tbs.trimplus.module.main.bean.Mine;
import com.tbs.trimplus.module.user.bean.City;
import com.tbs.trimplus.module.user.bean.UserInfo;
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

    //短信验证码登录
    @Override
    public Observable<User> loginByVerifyCode(Map<String, Object> params) {
        api = ApiInstance();
        return api.loginByVerifyCode(params);
    }

    //获取Mine界面数据
    @Override
    public Observable<BaseObject<Mine>> getMineData(Map<String, Object> params) {
        api = ApiInstance();
        return api.getMineData(params);
    }

    //获取个人信息数据
    @Override
    public Observable<BaseObject<UserInfo>> getUserInfoData(Map<String, Object> params) {
        api = ApiInstance();
        return api.getUserInfoData(params);
    }

    //修改个人信息
    @Override
    public Observable<User> changeUserInfo(Map<String, Object> params) {
        api = ApiInstance();
        return api.changeUserInfo(params);
    }

    //修改性别
    @Override
    public Observable<BaseObject> setGender(Map<String, Object> params) {
        api = ApiInstance();
        return api.setGender(params);
    }

    //修改装修阶段
    @Override
    public Observable<BaseObject> setDecorate(Map<String, Object> params) {
        api = ApiInstance();
        return api.setDecorate(params);
    }

    //获取城市信息
    @Override
    public Observable<ResultList<City>> getCity(Map<String, Object> params) {
        api = ApiInstance();
        return api.getCity(params);
    }

    //获取历史记录
    @Override
    public Observable<BaseList<Bible>> getHistoryRecord(Map<String, Object> params) {
        api = ApiInstance();
        return api.getHistoryRecord(params);
    }

    //意见反馈
    @Override
    public Observable<ResultList> feedback(Map<String, Object> params) {
        api = ApiInstance();
        return api.feedback(params);
    }
}
