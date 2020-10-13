package com.tbs.trimplus.module.api;


import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.common.bean.ResultList;
import com.tbs.trimplus.module.bible.bean.Author;
import com.tbs.trimplus.module.bible.bean.BibleDetail;
import com.tbs.trimplus.module.bible.bean.Catalog;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.main.bean.Bible;
import com.tbs.trimplus.module.main.bean.Home;
import com.tbs.trimplus.module.main.bean.Mine;
import com.tbs.trimplus.module.user.bean.AuthorList;
import com.tbs.trimplus.module.user.bean.City;
import com.tbs.trimplus.module.user.bean.Collect;
import com.tbs.trimplus.module.user.bean.Like;
import com.tbs.trimplus.module.user.bean.UserInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface Api {

    /**
     * 账号密码登录
     */
    @FormUrlEncoded
    @POST("tapp/passport/app_login")
    Observable<User> loginByPassword(@FieldMap Map<String, Object> params);

    /**
     * 短信验证码登录
     */
    @FormUrlEncoded
    @POST("tapp/passport/fast_register_mt")
    Observable<User> loginByVerifyCode(@FieldMap Map<String, Object> params);

    /**
     * 我的界面数据获取
     */
    @FormUrlEncoded
    @POST("zapp/myself/index")
    Observable<BaseObject<Mine>> getMineData(@FieldMap Map<String, Object> params);

    /**
     * 个人信息数据获取
     */
    @FormUrlEncoded
    @POST("zapp/myself/user_info")
    Observable<BaseObject<UserInfo>> getUserInfoData(@FieldMap Map<String, Object> params);


    /**
     * 修改个人信息（field：1.修改昵称）
     */
    @FormUrlEncoded
    @POST("tapp/user/chage_user_info")
    Observable<User> changeUserInfo(@FieldMap Map<String, Object> params);

    /**
     * 修改个人信息--性别
     */
    @FormUrlEncoded
    @POST("zapp/myself/set_gender")
    Observable<BaseObject> setGender(@FieldMap Map<String, Object> params);

    /**
     * 修改个人信息--装修阶段
     */
    @FormUrlEncoded
    @POST("zapp/myself/set_decorate")
    Observable<BaseObject> setDecorate(@FieldMap Map<String, Object> params);

    /**
     * 获取城市信息
     */
    @FormUrlEncoded
    @POST("tapp/util/change_city")
    Observable<ResultList<City>> getCity(@FieldMap Map<String, Object> params);

    /**
     * 获取历史记录
     */
    @FormUrlEncoded
    @POST("zapp/myself/history_record")
    Observable<BaseList<Bible>> getHistoryRecord(@FieldMap Map<String, Object> params);

    /**
     * 意见反馈
     */
    @FormUrlEncoded
    @POST("tapp/util/feedBackMt")
    Observable<ResultList> feedback(@FieldMap Map<String, Object> params);

    /**
     * 主页数据获取
     */
    @FormUrlEncoded
    @POST("zapp/index/index")
    Observable<BaseObject<Home>> getIndexData(@FieldMap Map<String, Object> params);

    /**
     * 获取bible分类
     */
    @FormUrlEncoded
    @POST("zapp/DecorateBook/get_catalog")
    Observable<BaseList<Catalog>> getCataLog(@FieldMap Map<String, Object> params);

    /**
     * 获取bible分类列表
     */
    @FormUrlEncoded
    @POST("zapp/DecorateBook/get_article")
    Observable<BaseList<Bible>> getArticle(@FieldMap Map<String, Object> params);

    /**
     * 获取bilbe搜索列表
     */
    @FormUrlEncoded
    @POST("zapp/DecorateBook/get_article_by_title")
    Observable<BaseList<Bible>> getSearchArticle(@FieldMap Map<String, Object> params);

    /**
     * 记录bible文章浏览数
     */
    @FormUrlEncoded
    @POST("zapp/DecorateBook/record_view_count")
    Observable<BaseObject> recordViewCount(@FieldMap Map<String, Object> params);

    /**
     * 获取bible详情数据
     */
    @FormUrlEncoded
    @POST("zapp/DecorateBook/article_detail")
    Observable<BaseObject<BibleDetail>> getArticleDetail(@FieldMap Map<String, Object> params);

    /**
     * 关注作者
     */
    @FormUrlEncoded
    @POST("zapp/DecorateBook/follow")
    Observable<BaseObject> followAuthor(@FieldMap Map<String, Object> params);

    /**
     * bible详情页点赞
     */
    @FormUrlEncoded
    @POST("zapp/DecorateBook/like")
    Observable<BaseObject> likeArticle(@FieldMap Map<String, Object> params);

    /**
     * bible详情页收藏
     */
    @FormUrlEncoded
    @POST("zapp/DecorateBook/collect")
    Observable<BaseObject> collectArticle(@FieldMap Map<String, Object> params);

    /**
     * 获取作者详情数据
     */
    @FormUrlEncoded
    @POST("zapp/index/author_detail")
    Observable<BaseObject<Author>> getAuthorDetail(@FieldMap Map<String, Object> params);

    /**
     * 搜索推荐关键词
     */
    @FormUrlEncoded
    @POST("zapp/DecorateBook/get_key_word")
    Observable<BaseList<Map>> getKeyWord(@FieldMap Map<String, Object> params);

    /**
     * 关注人列表数据
     */
    @FormUrlEncoded
    @POST("zapp/myself/my_attention")
    Observable<BaseList<AuthorList>> getMyAttention(@FieldMap Map<String, Object> params);

    /**
     * 收藏列表数据
     */
    @FormUrlEncoded
    @POST("zapp/myself/my_collect")
    Observable<BaseList<Collect>> getMyCollect(@FieldMap Map<String, Object> params);

    /**
     * 删除选中的收藏
     */
    @FormUrlEncoded
    @POST("zapp/myself/del_collect")
    Observable<BaseObject> delCheckCollect(@FieldMap Map<String, Object> params);

    /**
     * 删除所有的收藏
     */
    @FormUrlEncoded
    @POST("zapp/myself/batch_del_collect")
    Observable<BaseObject> delAllCollect(@FieldMap Map<String, Object> params);

    /**
     * 点赞列表数据
     */
    @FormUrlEncoded
    @POST("zapp/myself/my_like")
    Observable<BaseList<Like>> getMyLike(@FieldMap Map<String, Object> params);

    /**
     * 删除选中的点赞
     */
    @FormUrlEncoded
    @POST("zapp/myself/del_like")
    Observable<BaseObject> delCheckLike(@FieldMap Map<String, Object> params);

    /**
     * 删除所有的点赞
     */
    @FormUrlEncoded
    @POST("zapp/myself/batch_del_like")
    Observable<BaseObject> delAllLike(@FieldMap Map<String, Object> params);

    /*get和post两种请求写法
     */

    /*
        @FormUrlEncoded
        @POST("get")
        Observable<BaseObject<User>> LoginPswd(@FieldMap Map<String, Object> praise);

        @GET("getTitle")
        Observable<BaseObject<String>> getTitle(@Query("id") String titleId);


        @GET("pushMsg/getPushMessageList")
        Observable<ActionBean> PushMessageList(@Query("session") String session,
                                               @Query("pageIndex") String pageIndex,
                                               @Query("pageSize") String pageSize);

        */
}

