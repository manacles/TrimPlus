package com.tbs.trimplus.module.apimodel;


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
import com.tbs.trimplus.module.user.bean.Message;
import com.tbs.trimplus.module.user.bean.UserInfo;

import java.util.Map;

import io.reactivex.Observable;

public interface IModel {
    //账号密码登录
    Observable<User> loginByPassword(Map<String, Object> params);

    //短信验证码登录
    Observable<User> loginByVerifyCode(Map<String, Object> params);

    //获取Mine界面数据
    Observable<BaseObject<Mine>> getMineData(Map<String, Object> params);

    //获取个人信息数据
    Observable<BaseObject<UserInfo>> getUserInfoData(Map<String, Object> params);

    //修改个人信息
    Observable<User> changeUserInfo(Map<String, Object> params);

    //修改性别
    Observable<BaseObject> setGender(Map<String, Object> params);

    //修改装修阶段
    Observable<BaseObject> setDecorate(Map<String, Object> params);

    //获取城市信息
    Observable<ResultList<City>> getCity(Map<String, Object> params);

    //获取历史记录
    Observable<BaseList<Bible>> getHistoryRecord(Map<String, Object> params);

    //意见反馈
    Observable<ResultList> feedback(Map<String, Object> params);

    //获取主页数据
    Observable<BaseObject<Home>> getIndexData(Map<String, Object> params);

    //获取bible分类
    Observable<BaseList<Catalog>> getCataLog(Map<String, Object> params);

    //获取bible分类列表
    Observable<BaseList<Bible>> getArticle(Map<String, Object> params);

    //获取bible搜索列表
    Observable<BaseList<Bible>> getSearchArticle(Map<String, Object> params);

    //记录bible浏览数
    Observable<BaseObject> recordViewCount(Map<String, Object> params);

    //获取bible详情数据
    Observable<BaseObject<BibleDetail>> getArticleDetail(Map<String, Object> params);

    //关注作者
    Observable<BaseObject> followAuthor(Map<String, Object> params);

    //bible详情页点赞
    Observable<BaseObject> likeArticle(Map<String, Object> params);

    //bible详情页收藏
    Observable<BaseObject> collectArticle(Map<String, Object> params);

    //获取作者详情数据
    Observable<BaseObject<Author>> getAuthorDetail(Map<String, Object> params);

    //获取搜索推荐关键词
    Observable<BaseList<Map>> getKeyWord(Map<String, Object> params);

    //获取关注人列表
    Observable<BaseList<AuthorList>> getMyAttention(Map<String, Object> params);

    //获取收藏列表
    Observable<BaseList<Collect>> getMyCollect(Map<String, Object> params);

    //删除选中收藏
    Observable<BaseObject> delCheckCollect(Map<String, Object> params);

    //删除所有收藏
    Observable<BaseObject> delAllCollect(Map<String, Object> params);

    //获取点赞列表
    Observable<BaseList<Like>> getMyLike(Map<String, Object> params);

    //删除选中点赞
    Observable<BaseObject> delCheckLike(Map<String, Object> params);

    //删除所有点赞
    Observable<BaseObject> delAllLike(Map<String, Object> params);

    //获取消息中心数据
    Observable<Message> getPushLog(Map<String, Object> params);

}
