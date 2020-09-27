package com.tbs.trimplus.module.bible.presenter.impl;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.bible.bean.BibleDetail;
import com.tbs.trimplus.module.bible.presenter.IdoBibleDetailPresenter;
import com.tbs.trimplus.module.bible.view.IdoBibleDetailView;
import com.tbs.trimplus.module.main.bean.Bible;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DoBibleDetailPresenter implements IdoBibleDetailPresenter {

    private IModel model;
    private IdoBibleDetailView view;

    public DoBibleDetailPresenter(IModel model, IdoBibleDetailView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void recordViewCount(Map<String, Object> params) {
        model.recordViewCount(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject baseObject) {
                        LogUtil.e("DoBibleDetailPresenter----" + baseObject.getStatus() + baseObject.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void getArticleDetail(Map<String, Object> params) {
        model.getArticleDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject<BibleDetail>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject<BibleDetail> bibleDetailBaseObject) {
                        view.getArticleDetail(bibleDetailBaseObject);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void followAuthor(Map<String, Object> params) {
        model.followAuthor(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject baseObject) {
                        view.followAuthor(baseObject);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void likeArticle(Map<String, Object> params) {
        model.likeArticle(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject baseObject) {
                        view.likeArticle(baseObject);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void collectArticle(Map<String, Object> params) {
        model.collectArticle(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject baseObject) {
                        view.collectArticle(baseObject);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
