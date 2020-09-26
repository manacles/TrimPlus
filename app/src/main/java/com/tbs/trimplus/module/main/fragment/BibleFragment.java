package com.tbs.trimplus.module.main.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.facebook.stetho.common.LogUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseFragment;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.bible.activity.CatalogActivity;
import com.tbs.trimplus.module.bible.adapter.BibleViewPagerAdapter;
import com.tbs.trimplus.module.bible.bean.Catalog;
import com.tbs.trimplus.module.bible.presenter.impl.GetCataLogPresenter;
import com.tbs.trimplus.module.bible.utils.CatalogManage;
import com.tbs.trimplus.module.welcome.activity.WelcomeActivity;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.NetUtil;
import com.tbs.trimplus.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BibleFragment extends BaseFragment {

    @BindView(R.id.et_bible_search)
    EditText etBibleSearch;
    @BindView(R.id.ll_bible_type)
    LinearLayout llBibleType;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager2 viewpager;

    private ArrayList<Catalog> selectedCatalogs = new ArrayList<>();

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_bible, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        String catalogStr = CacheUtil.getString(context, Constant.BIBLE_CATALOG, "mCatalog");
        if (catalogStr.equals("")) {
            if (NetUtil.isNetAvailable(context)) {
                WelcomeActivity welcomeActivity = new WelcomeActivity();
                welcomeActivity.setBibleCatalog();

                showData();
            } else {
                ToastUtil.sToast(context, "请检查网络~");
            }
        } else {
            showData();
        }
    }

    private void showData() {
        selectedCatalogs = CatalogManage.getSelectedCatalog(context, true);

        if (selectedCatalogs != null && selectedCatalogs.size() > 0) {
            //设置ViewPager的适配器
            viewpager.setAdapter(new BibleViewPagerAdapter(context, selectedCatalogs));
            //设置固定或者滑动
            tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            //重写tab点击事件，去掉自带的 滑动viewpager的效果
            tablayout.addOnTabSelectedListener(onTabSelectedListener);

            TabLayoutMediator mediator = new TabLayoutMediator(tablayout, viewpager,
                    new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            tab.setText(selectedCatalogs.get(position).getName());
                        }
                    });
            //要执行这一句才是真正将两者绑定起来
            mediator.attach();
        }
    }

    @OnClick({R.id.et_bible_search, R.id.ll_bible_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_bible_search:
                break;
            case R.id.ll_bible_type:
                startActivityForResult(new Intent(context, CatalogActivity.class), 123);
                break;
        }
    }

    private TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            viewpager.setCurrentItem(tab.getPosition(), false);
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (resultCode == 321) {
                //如果修改了频道，bible页面数据刷新
                showData();
            }
        }
    }
}
