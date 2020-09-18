package com.tbs.trimplus.module.login.presenter.impl;

import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.login.presenter.IloginByVerifyCodePresenter;
import com.tbs.trimplus.module.login.view.IloginByVerifyCodeView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginByVerifyCodePresenter implements IloginByVerifyCodePresenter {

    private IModel model;
    private IloginByVerifyCodeView view;

    public LoginByVerifyCodePresenter(IModel model, IloginByVerifyCodeView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void loginByVerifyCode(Map<String, Object> params) {
        model.loginByVerifyCode(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(User user) {
                        view.loginByVerifyCode(user);
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
