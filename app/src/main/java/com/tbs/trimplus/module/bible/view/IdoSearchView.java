package com.tbs.trimplus.module.bible.view;

import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.module.main.bean.Bible;

import java.util.Map;

public interface IdoSearchView {

    /**
     * 获取搜索页推荐关键词
     */
    void getKeyWord(BaseList<Map> mapBaseList);

    /**
     * 获取bible搜索列表
     */
    void getSearchArticle(BaseList<Bible> bibleBaseList);
}
