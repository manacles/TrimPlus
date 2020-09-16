package com.tbs.trimplus.module.user.view;

import com.tbs.trimplus.common.bean.ResultList;
import com.tbs.trimplus.module.user.bean.City;

public interface IgetCityView {
    //处理获取的城市信息
    void getCity(ResultList<City> cityResultList);
}
