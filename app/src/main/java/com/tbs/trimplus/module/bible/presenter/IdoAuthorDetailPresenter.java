package com.tbs.trimplus.module.bible.presenter;

import java.util.Map;

public interface IdoAuthorDetailPresenter {

    //获取作者详情数据
    void getAuthorDetail(Map<String, Object> params);

    //关注作者
    void followAuthor(Map<String, Object> params);
}
