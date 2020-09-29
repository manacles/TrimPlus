package com.tbs.trimplus.module.bible.view;

import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.bible.bean.Author;

public interface IdoAuthorDetilView {
    /**
     * 获取作者详情数据
     */
    void getAuthorDetail(BaseObject<Author> authorBaseObject);

    /**
     * 关注作者
     */
    void followAuthor(BaseObject baseObject);
}
