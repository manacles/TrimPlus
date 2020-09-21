package com.tbs.trimplus.module.setting.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.utils.DataCleanManager;
import com.tbs.trimplus.utils.ToastUtil;
import com.tbs.trimplus.view.CustomSelectDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_cache)
    TextView tvCache;
    @BindView(R.id.ll_clean_cache)
    LinearLayout llCleanCache;
    @BindView(R.id.ll_assess)
    LinearLayout llAssess;
    @BindView(R.id.ll_contribution)
    LinearLayout llContribution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        toolbarTitle.setText("设置");
        initToolbar(toolbar);

        initData();
    }


    private void initData() {
        //获取缓存
        try {
            if ("0.0Byte".equals(DataCleanManager.getTotalCacheSize(getApplicationContext()))) {
                tvCache.setText("0 M");
            } else {
                tvCache.setText(DataCleanManager.getTotalCacheSize(getApplicationContext()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.ll_clean_cache, R.id.ll_assess, R.id.ll_contribution})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_clean_cache:
                //清除缓存
                CustomSelectDialog cleanCacheDialog = new CustomSelectDialog(this,
                        "是否清除所有缓存", 1, R.style.custom_dialog) {
                    @Override
                    public void onSureClick() {
                        cleanCache();
                    }
                };
                cleanCacheDialog.show();
                break;
            case R.id.ll_assess:
                //去评价
                String mAddress = "market://details?id=" + getPackageName();
                Intent marketIntent = new Intent("android.intent.action.VIEW");
                marketIntent.setData(Uri.parse(mAddress));
                startActivity(Intent.createChooser(marketIntent, "请选择要查看的市场软件"));

                break;
            case R.id.ll_contribution:
                //投稿地址
                CustomSelectDialog contributionDialog = new CustomSelectDialog(this,
                        "投稿地址：您可以登录pc端开通媒体平台哦", 2, R.style.custom_dialog) {
                    @Override
                    public void onSureClick() {

                    }
                };
                contributionDialog.show();
                break;
        }
    }

    private void cleanCache() {
        if (!"0 M".contentEquals(tvCache.getText())) {
            DataCleanManager.clearAllCache(getApplicationContext());
            tvCache.setText("0 M");
        } else {
            ToastUtil.sToast(this, "你已清理过缓存");
        }
    }
}
