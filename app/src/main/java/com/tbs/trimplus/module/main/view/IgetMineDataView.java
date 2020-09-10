package com.tbs.trimplus.module.main.view;

import com.tbs.trimplus.common.ICommonView;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.main.bean.Mine;

public interface IgetMineDataView extends ICommonView {

    //处理获取Mine数据
    void getMineData(BaseObject<Mine> mineBaseObject);
}
