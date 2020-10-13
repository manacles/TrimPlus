package com.tbs.trimplus.module.user.presenter;

import java.util.Map;

public interface IgetMyLikePresenter {

    void getMyLike(Map<String, Object> params);

    void delCheckLike(Map<String, Object> params);

    void delAllLike(Map<String, Object> params);
}
