package com.tbs.trimplus.module.user.presenter.impl;

import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.user.bean.UserInfo;
import com.tbs.trimplus.module.user.presenter.IchangeUserInfoPresenter;
import com.tbs.trimplus.module.user.view.IchangeUserInfoView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChangeUserInfoPresenter implements IchangeUserInfoPresenter {

    private IModel model;
    private IchangeUserInfoView view;

    public ChangeUserInfoPresenter(IModel model, IchangeUserInfoView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void changeUserInfo(Map<String, Object> params) {
        model.changeUserInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<User>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(User user) {
                        view.changeUserInfo(user);
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
