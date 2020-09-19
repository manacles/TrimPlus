package com.tbs.trimplus.module.apimodel;


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

public interface IModel {
    //账号密码登录
    Observable<User> loginByPassword(Map<String, Object> params);

    //短信验证码登录
    Observable<User> loginByVerifyCode(Map<String, Object> params);

    //获取Mine界面数据
    Observable<BaseObject<Mine>> getMineData(Map<String, Object> params);

    //获取个人信息数据
    Observable<BaseObject<UserInfo>> getUserInfoData(Map<String, Object> params);

    //修改个人信息
    Observable<User> changeUserInfo(Map<String, Object> params);

    //修改性别
    Observable<BaseObject> setGender(Map<String, Object> params);

    //修改装修阶段
    Observable<BaseObject> setDecorate(Map<String, Object> params);

    //获取城市信息
    Observable<ResultList<City>> getCity(Map<String, Object> params);

    //获取历史记录
    Observable<BaseList<Bible>> getHistoryRecord(Map<String, Object> params);
}
