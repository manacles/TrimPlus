package com.tbs.trimplus.module.user.presenter;

import java.util.Map;

public interface IgetUserInfoDataPresenter {
    /**
     * 获取个人信息
     */
    void getUserInfoData(Map<String, Object> params);

    /**
     * 设置性别
     */
    void setGender(Map<String, Object> params, int sex);

    /**
     * 设置装修阶段
     */
    void setDecorate(Map<String,Object> params,int decorateType);
}
