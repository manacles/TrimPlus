package com.tbs.trimplus.module.user.presenter.impl;

import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.user.bean.UserInfo;
import com.tbs.trimplus.module.user.presenter.IgetUserInfoDataPresenter;
import com.tbs.trimplus.module.user.view.IgetUserInfoDataView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetUserInfoDataPresenter implements IgetUserInfoDataPresenter {

    private IModel model;
    private IgetUserInfoDataView view;

    public GetUserInfoDataPresenter(IModel model, IgetUserInfoDataView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getUserInfoData(Map<String, Object> params) {
        model.getUserInfoData(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject<UserInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject<UserInfo> userInfoBaseObject) {
                        view.getUserInfoData(userInfoBaseObject);
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

    @Override
    public void setGender(Map<String, Object> params, int sex) {
        model.setGender(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject baseObject) {
                        view.setGender(baseObject, sex);
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

    @Override
    public void setDecorate(Map<String, Object> params, int decorateType) {
        model.setDecorate(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseObject baseObject) {
                        view.setDecorate(baseObject, decorateType);
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
