package com.tbs.trimplus.module.bible.view;

import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.bible.bean.BibleDetail;

public interface IdoBibleDetailView {
    /**
     * 获取bible详情数据
     */
    void getArticleDetail(BaseObject<BibleDetail> bibleDetailBaseObject);

    /**
     * 关注作者
     */
    void followAuthor(BaseObject baseObject);

    /**
     * 点赞文章
     */
    void likeArticle(BaseObject baseObject);

    /**
     * 收藏文章
     */
    void collectArticle(BaseObject baseObject);
}
