package com.tbs.trimplus.module.bible.presenter.impl;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.bible.presenter.IdoSearchPresenter;
import com.tbs.trimplus.module.bible.view.IdoSearchView;
import com.tbs.trimplus.module.main.bean.Bible;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DoSearchPresenter implements IdoSearchPresenter {

    private IModel model;
    private IdoSearchView view;

    public DoSearchPresenter(IModel model, IdoSearchView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getKeyWord(Map<String, Object> params) {
        model.getKeyWord(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseList<Map>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseList<Map> mapBaseList) {
                        view.getKeyWord(mapBaseList);
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
    public void getSearchArticle(Map<String, Object> params) {
        model.getSearchArticle(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseList<Bible>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseList<Bible> bibleBaseList) {
                        view.getSearchArticle(bibleBaseList);
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
