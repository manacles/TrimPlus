package com.tbs.trimplus.module.user.view;

import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.user.bean.Like;

public interface IgetMyLikeView {

    void getMyLike(BaseList<Like> collectBaseList);

    void delCheckLike(BaseObject baseObject);

    void delAllLike(BaseObject baseObject);
}
