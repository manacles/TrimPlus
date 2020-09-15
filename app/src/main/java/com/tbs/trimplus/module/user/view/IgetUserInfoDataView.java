package com.tbs.trimplus.module.user.view;

import com.tbs.trimplus.common.ICommonView;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.user.bean.UserInfo;

public interface IgetUserInfoDataView extends ICommonView {

    //处理获取个人信息数据
    void getUserInfoData(BaseObject<UserInfo> userInfoBaseObject);


    //设置性别
    void setGender(BaseObject baseObject, int sex);

    //设置装修阶段
    void setDecorate(BaseObject baseObject, int decorateType);
}
