package com.tbs.trimplus.module.login.presenter.impl;

import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.login.presenter.IloginByPasswordPresenter;
import com.tbs.trimplus.module.login.view.IloginByPasswordView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginByPasswordPresenter implements IloginByPasswordPresenter {
    private IModel model;
    private IloginByPasswordView view;

    public LoginByPasswordPresenter(IModel model, IloginByPasswordView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void loginByPassword(Map<String, Object> params) {
        model.loginByPassword(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(User user) {
                        view.loginByPassword(user);
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
