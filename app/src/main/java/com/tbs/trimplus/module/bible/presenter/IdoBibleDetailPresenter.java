package com.tbs.trimplus.module.bible.presenter;

import java.util.Map;

public interface IdoBibleDetailPresenter {
    //记录页面浏览数
    void recordViewCount(Map<String, Object> params);

    //获取bible详情数据
    void getArticleDetail(Map<String, Object> params);

    //关注作者
    void followAuthor(Map<String, Object> params);

    //点赞文章
    void likeArticle(Map<String, Object> params);

    //收藏文章
    void collectArticle(Map<String, Object> params);
}
