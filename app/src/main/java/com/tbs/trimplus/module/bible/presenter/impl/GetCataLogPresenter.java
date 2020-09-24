package com.tbs.trimplus.module.bible.presenter.impl;

import android.content.Context;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.module.apimodel.IModel;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.bible.bean.Catalog;
import com.tbs.trimplus.module.bible.presenter.IgetCataLogPresenter;
import com.tbs.trimplus.module.bible.utils.CatalogManage;
import com.tbs.trimplus.module.main.bean.Bible;
import com.tbs.trimplus.module.welcome.activity.WelcomeActivity;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GetCataLogPresenter implements IgetCataLogPresenter {

    private IModel model;
    private Context context;

    public GetCataLogPresenter(Context context, IModel model) {
        this.context = context;
        this.model = model;
    }

    @Override
    public void getCataLog(Map<String, Object> params) {
        model.getCataLog(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseList<Catalog>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(BaseList<Catalog> catalogBaseList) {
                        if (catalogBaseList.getStatus().equals("200")) {
                            CatalogManage.setDefaultCatalog(context, catalogBaseList.getData());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
