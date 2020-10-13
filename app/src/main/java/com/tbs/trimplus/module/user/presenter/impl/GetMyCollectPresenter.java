package com.tbs.trimplus.module.user.presenter.impl;

import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.user.bean.Collect;
import com.tbs.trimplus.module.user.presenter.IgetMyCollectPresenter;
import com.tbs.trimplus.module.user.view.IgetMyCollectView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetMyCollectPresenter implements IgetMyCollectPresenter {

    private IModel model;
    private IgetMyCollectView view;

    public GetMyCollectPresenter(IModel model, IgetMyCollectView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getMyCollect(Map<String, Object> params) {
        model.getMyCollect(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseList<Collect>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseList<Collect> collectBaseList) {
                        view.getMyCollect(collectBaseList);
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
    public void delCheckCollect(Map<String, Object> params) {
        model.delCheckCollect(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject baseObject) {
                        view.delCheckCollect(baseObject);
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
    public void delAllCollect(Map<String, Object> params) {
        model.delAllCollect(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject baseObject) {
                        view.delAllCollect(baseObject);
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
