package com.tbs.trimplus.module.user.view;

import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.user.bean.Collect;

import java.util.Map;

public interface IgetMyCollectView {

    void getMyCollect(BaseList<Collect> collectBaseList);

    void delCheckCollect(BaseObject baseObject);

    void delAllCollect(BaseObject baseObject);
}
