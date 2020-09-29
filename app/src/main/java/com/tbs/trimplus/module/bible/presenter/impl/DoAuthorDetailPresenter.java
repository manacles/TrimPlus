package com.tbs.trimplus.module.bible.presenter.impl;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.bible.bean.Author;
import com.tbs.trimplus.module.bible.presenter.IdoAuthorDetailPresenter;
import com.tbs.trimplus.module.bible.view.IdoAuthorDetilView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DoAuthorDetailPresenter implements IdoAuthorDetailPresenter {

    private IModel model;
    private IdoAuthorDetilView view;

    public DoAuthorDetailPresenter(IModel model, IdoAuthorDetilView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getAuthorDetail(Map<String, Object> params) {
        model.getAuthorDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject<Author>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject<Author> authorBaseObject) {
                        view.getAuthorDetail(authorBaseObject);
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
}
