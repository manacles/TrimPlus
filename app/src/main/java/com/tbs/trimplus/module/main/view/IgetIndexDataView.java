package com.tbs.trimplus.module.main.view;

import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.main.bean.Home;

public interface IgetIndexDataView {
    /**
     * 获取主页数据
     */
    void getIndexData(BaseObject<Home> homeBaseObject);
}
