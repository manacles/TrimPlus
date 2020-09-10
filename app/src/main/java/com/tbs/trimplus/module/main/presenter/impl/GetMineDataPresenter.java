package com.tbs.trimplus.module.main.presenter.impl;

import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.main.bean.Mine;
import com.tbs.trimplus.module.main.presenter.IgetMineDataPresenter;
import com.tbs.trimplus.module.main.view.IgetMineDataView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetMineDataPresenter implements IgetMineDataPresenter {

    private IModel model;
    private IgetMineDataView view;

    public GetMineDataPresenter(IModel model, IgetMineDataView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getMineData(Map<String, Object> params) {
        model.getMineData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject<Mine>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject<Mine> mineBaseObject) {
                        view.getMineData(mineBaseObject);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.DialogStatus(1, e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
