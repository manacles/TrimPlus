package com.tbs.trimplus.module.main.presenter.impl;

import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.main.bean.Home;
import com.tbs.trimplus.module.main.bean.Mine;
import com.tbs.trimplus.module.main.presenter.IgetIndexDataPresenter;
import com.tbs.trimplus.module.main.view.IgetIndexDataView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetIndexDataPresenter implements IgetIndexDataPresenter {

    private IModel model;
    private IgetIndexDataView view;

    public GetIndexDataPresenter(IModel model, IgetIndexDataView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getIndexData(Map<String, Object> params) {
        model.getIndexData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject<Home>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject<Home> homeBaseObject) {
                        view.getIndexData(homeBaseObject);
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
