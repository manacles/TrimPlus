package com.tbs.trimplus.module.bible.view;

import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.module.main.bean.Bible;

public interface IgetArticleView {
    /**
     * 获取bible分类列表
     */
    void getArticle(BaseList<Bible> bibleBaseList);
}
