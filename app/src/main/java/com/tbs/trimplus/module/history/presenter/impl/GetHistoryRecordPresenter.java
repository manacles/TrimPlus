package com.tbs.trimplus.module.history.presenter.impl;

import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.history.presenter.IgetHistoryRecordPresenter;
import com.tbs.trimplus.module.history.view.IgetHistoryRecordView;
import com.tbs.trimplus.module.main.bean.Bible;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetHistoryRecordPresenter implements IgetHistoryRecordPresenter {

    private IModel model;
    private IgetHistoryRecordView view;

    public GetHistoryRecordPresenter(IModel model, IgetHistoryRecordView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getHistoryRecord(Map<String, Object> params) {
        model.getHistoryRecord(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseList<Bible>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseList<Bible> bibleBaseList) {
                        view.getHistoryRecord(bibleBaseList);
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
