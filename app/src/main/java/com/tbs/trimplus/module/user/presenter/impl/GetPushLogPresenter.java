package com.tbs.trimplus.module.user.presenter.impl;

import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.user.bean.Message;
import com.tbs.trimplus.module.user.presenter.IgetPushLogPresenter;
import com.tbs.trimplus.module.user.view.IgetPushLogView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetPushLogPresenter implements IgetPushLogPresenter {

    private IModel model;
    private IgetPushLogView view;

    public GetPushLogPresenter(IModel model, IgetPushLogView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getPushLog(Map<String, Object> params) {
        model.getPushLog(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Message>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Message message) {
                        view.getPushLog(message);
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
