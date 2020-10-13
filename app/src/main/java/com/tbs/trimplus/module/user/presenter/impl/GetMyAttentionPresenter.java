package com.tbs.trimplus.module.user.presenter.impl;

import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.user.bean.AuthorList;
import com.tbs.trimplus.module.user.presenter.IgetMyAttentionPresenter;
import com.tbs.trimplus.module.user.view.IgetMyAttentionView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetMyAttentionPresenter implements IgetMyAttentionPresenter {

    private IModel model;
    private IgetMyAttentionView view;

    public GetMyAttentionPresenter(IModel model, IgetMyAttentionView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getMyAttention(Map<String, Object> params) {
        model.getMyAttention(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseList<AuthorList>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseList<AuthorList> authorListBaseList) {
                        view.getMyAttention(authorListBaseList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
