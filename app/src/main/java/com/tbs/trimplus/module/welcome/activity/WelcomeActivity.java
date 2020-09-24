package com.tbs.trimplus.module.welcome.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.bible.presenter.impl.GetCataLogPresenter;
import com.tbs.trimplus.module.main.activity.MainActivity;
import com.tbs.trimplus.utils.AppUtil;

import java.util.HashMap;

public class WelcomeActivity extends AppCompatActivity {

    private GetCataLogPresenter getCataLogPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        setBibleCatalog();

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    public void setBibleCatalog() {
        getCataLogPresenter = new GetCataLogPresenter(this, new Model());
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken()); // 此token和用户登录后获得的token无关
        getCataLogPresenter.getCataLog(params);
    }
}
