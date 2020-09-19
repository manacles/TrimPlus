package com.tbs.trimplus.module.user.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.common.bean.ResultList;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.user.bean.City;
import com.tbs.trimplus.module.user.presenter.impl.GetCityPresenter;
import com.tbs.trimplus.module.user.view.IgetCityView;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeCityActivity extends BaseActivity implements IgetCityView {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    GetCityPresenter getCityPresent;
    ArrayList<City> cityArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_city);
        ButterKnife.bind(this);

        //初始化toolbar
        toolbarTitle.setText("选择城市");
        initToolbar(toolbar);

        //获取城市信息
        getCityPresent = new GetCityPresenter(new Model(), this);
        getCityRequest();
    }

    private void getCityRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("gfd", AppUtil.getDateToken());
        getCityPresent.getCity(params);
    }

    @Override
    public void getCity(ResultList<City> cityResultList) {
        if (cityResultList.getError_code().equals("0")) {
            cityArrayList = cityResultList.getData();
            LogUtil.e(cityResultList.toString());
        } else {
            ToastUtil.sToast(this, cityResultList.getMsg());
        }
    }
}
