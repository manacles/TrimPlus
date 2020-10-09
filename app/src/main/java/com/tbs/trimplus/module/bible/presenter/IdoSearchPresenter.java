package com.tbs.trimplus.module.bible.presenter;

import java.util.Map;

public interface IdoSearchPresenter {

    /**
     * 获取搜索页关键词
     */
    void getKeyWord(Map<String, Object> params);

    /**
     * 获取bible搜索列表
     */
    void getSearchArticle(Map<String, Object> params);
}
