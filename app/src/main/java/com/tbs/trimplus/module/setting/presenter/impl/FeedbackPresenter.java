package com.tbs.trimplus.module.setting.presenter.impl;

import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.common.bean.ResultList;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.main.bean.Bible;
import com.tbs.trimplus.module.setting.presenter.IfeedbackPresenter;
import com.tbs.trimplus.module.setting.view.IfeedbackView;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FeedbackPresenter implements IfeedbackPresenter {

    private IModel model;
    private IfeedbackView view;

    public FeedbackPresenter(IModel model, IfeedbackView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void feedback(Map<String, Object> params) {
        model.feedback(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResultList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(ResultList resultList) {
                        view.feedback(resultList);
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
