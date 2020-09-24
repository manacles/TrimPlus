package com.tbs.trimplus.module.bible.presenter.impl;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.bible.bean.Catalog;
import com.tbs.trimplus.module.bible.presenter.IgetArticlePresenter;
import com.tbs.trimplus.module.bible.utils.CatalogManage;
import com.tbs.trimplus.module.bible.view.IgetArticleView;
import com.tbs.trimplus.module.main.bean.Bible;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetArticlePresenter implements IgetArticlePresenter {

    private IModel model;
    private IgetArticleView view;

    public GetArticlePresenter(IModel model, IgetArticleView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getArticle(Map<String, Object> params) {
        model.getArticle(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseList<Bible>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseList<Bible> bibleBaseList) {
                        view.getArticle(bibleBaseList);
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
