package com.tbs.trimplus.module.user.presenter.impl;

import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.user.bean.Like;
import com.tbs.trimplus.module.user.presenter.IgetMyLikePresenter;
import com.tbs.trimplus.module.user.view.IgetMyLikeView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetMyLikePresenter implements IgetMyLikePresenter {

    private IModel model;
    private IgetMyLikeView view;

    public GetMyLikePresenter(IModel model, IgetMyLikeView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getMyLike(Map<String, Object> params) {
        model.getMyLike(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseList<Like>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseList<Like> likeBaseList) {
                        view.getMyLike(likeBaseList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void delCheckLike(Map<String, Object> params) {
        model.delCheckLike(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject baseObject) {
                        view.delCheckLike(baseObject);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void delAllLike(Map<String, Object> params) {
        model.delAllLike(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject baseObject) {
                        view.delAllLike(baseObject);
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
