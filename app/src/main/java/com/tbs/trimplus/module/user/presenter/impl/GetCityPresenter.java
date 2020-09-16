package com.tbs.trimplus.module.user.presenter.impl;

import com.tbs.trimplus.common.bean.ResultList;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.user.bean.City;
import com.tbs.trimplus.module.user.presenter.IgetCityPresenter;
import com.tbs.trimplus.module.user.view.IgetCityView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetCityPresenter implements IgetCityPresenter {

    private IModel model;
    private IgetCityView view;

    public GetCityPresenter(IModel model, IgetCityView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getCity(Map<String, Object> params) {
        model.getCity(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultList<City>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultList<City> cityResultList) {
                        view.getCity(cityResultList);
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
