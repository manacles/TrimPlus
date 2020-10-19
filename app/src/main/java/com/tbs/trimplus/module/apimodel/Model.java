package com.tbs.trimplus.module.apimodel;

import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.common.bean.ResultList;
import com.tbs.trimplus.module.api.Api;
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
import com.tbs.trimplus.utils.ApiUtil;

import java.util.Map;

import io.reactivex.Observable;

public class Model implements IModel {

    private Api api;

    private Api ApiInstance() {
        if (api != null) {
            return api;
        } else {
            return ApiUtil.getInstance().createRetrofitApi(Api.class);
        }
    }

    //账号密码登录
    @Override
    public Observable<User> loginByPassword(Map<String, Object> params) {
        api = ApiInstance();
        return api.loginByPassword(params);
    }

    //短信验证码登录
    @Override
    public Observable<User> loginByVerifyCode(Map<String, Object> params) {
        api = ApiInstance();
        return api.loginByVerifyCode(params);
    }

    //获取Mine界面数据
    @Override
    public Observable<BaseObject<Mine>> getMineData(Map<String, Object> params) {
        api = ApiInstance();
        return api.getMineData(params);
    }

    //获取个人信息数据
    @Override
    public Observable<BaseObject<UserInfo>> getUserInfoData(Map<String, Object> params) {
        api = ApiInstance();
        return api.getUserInfoData(params);
    }

    //修改个人信息
    @Override
    public Observable<User> changeUserInfo(Map<String, Object> params) {
        api = ApiInstance();
        return api.changeUserInfo(params);
    }

    //修改性别
    @Override
    public Observable<BaseObject> setGender(Map<String, Object> params) {
        api = ApiInstance();
        return api.setGender(params);
    }

    //修改装修阶段
    @Override
    public Observable<BaseObject> setDecorate(Map<String, Object> params) {
        api = ApiInstance();
        return api.setDecorate(params);
    }

    //获取城市信息
    @Override
    public Observable<ResultList<City>> getCity(Map<String, Object> params) {
        api = ApiInstance();
        return api.getCity(params);
    }

    //修改城市信息
    @Override
    public Observable<BaseObject> setCity(Map<String, Object> params) {
        api = ApiInstance();
        return api.setCity(params);
    }

    //获取历史记录
    @Override
    public Observable<BaseList<Bible>> getHistoryRecord(Map<String, Object> params) {
        api = ApiInstance();
        return api.getHistoryRecord(params);
    }

    //意见反馈
    @Override
    public Observable<ResultList> feedback(Map<String, Object> params) {
        api = ApiInstance();
        return api.feedback(params);
    }

    //获取主页数据
    @Override
    public Observable<BaseObject<Home>> getIndexData(Map<String, Object> params) {
        api = ApiInstance();
        return api.getIndexData(params);
    }

    //获取bible分类
    @Override
    public Observable<BaseList<Catalog>> getCataLog(Map<String, Object> params) {
        api = ApiInstance();
        return api.getCataLog(params);
    }

    //获取bible分类列表
    @Override
    public Observable<BaseList<Bible>> getArticle(Map<String, Object> params) {
        api = ApiInstance();
        return api.getArticle(params);
    }

    //获取bible搜索列表


    @Override
    public Observable<BaseList<Bible>> getSearchArticle(Map<String, Object> params) {
        api = ApiInstance();
        return api.getSearchArticle(params);
    }

    //记录bible浏览数
    @Override
    public Observable<BaseObject> recordViewCount(Map<String, Object> params) {
        api = ApiInstance();
        return api.recordViewCount(params);
    }

    //获取bible详情数据
    @Override
    public Observable<BaseObject<BibleDetail>> getArticleDetail(Map<String, Object> params) {
        api = ApiInstance();
        return api.getArticleDetail(params);
    }

    //关注作者
    @Override
    public Observable<BaseObject> followAuthor(Map<String, Object> params) {
        api = ApiInstance();
        return api.followAuthor(params);
    }

    //bible详情页点赞
    @Override
    public Observable<BaseObject> likeArticle(Map<String, Object> params) {
        api = ApiInstance();
        return api.likeArticle(params);
    }

    //bible详情页收藏
    @Override
    public Observable<BaseObject> collectArticle(Map<String, Object> params) {
        api = ApiInstance();
        return api.collectArticle(params);
    }

    //获取作者详情数据
    @Override
    public Observable<BaseObject<Author>> getAuthorDetail(Map<String, Object> params) {
        api = ApiInstance();
        return api.getAuthorDetail(params);
    }

    //获取搜索关键词
    @Override
    public Observable<BaseList<Map>> getKeyWord(Map<String, Object> params) {
        api = ApiInstance();
        return api.getKeyWord(params);
    }

    //关注列表
    @Override
    public Observable<BaseList<AuthorList>> getMyAttention(Map<String, Object> params) {
        api = ApiInstance();
        return api.getMyAttention(params);
    }

    //收藏列表
    @Override
    public Observable<BaseList<Collect>> getMyCollect(Map<String, Object> params) {
        api = ApiInstance();
        return api.getMyCollect(params);
    }

    //删除选中收藏
    @Override
    public Observable<BaseObject> delCheckCollect(Map<String, Object> params) {
        api = ApiInstance();
        return api.delCheckCollect(params);
    }

    //删除所有收藏
    @Override
    public Observable<BaseObject> delAllCollect(Map<String, Object> params) {
        api = ApiInstance();
        return api.delAllCollect(params);
    }

    //点赞列表
    @Override
    public Observable<BaseList<Like>> getMyLike(Map<String, Object> params) {
        api = ApiInstance();
        return api.getMyLike(params);
    }

    //删除选中点赞
    @Override
    public Observable<BaseObject> delCheckLike(Map<String, Object> params) {
        api = ApiInstance();
        return api.delCheckLike(params);
    }

    //删除所有点赞
    @Override
    public Observable<BaseObject> delAllLike(Map<String, Object> params) {
        api = ApiInstance();
        return api.delAllLike(params);
    }

    //获取消息中心数据
    @Override
    public Observable<Message> getPushLog(Map<String, Object> params) {
        api = ApiInstance();
        return api.getPushLog(params);
    }
}
