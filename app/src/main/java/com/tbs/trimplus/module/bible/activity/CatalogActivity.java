package com.tbs.trimplus.module.bible.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.module.bible.adapter.CatalogAdapter;
import com.tbs.trimplus.module.bible.bean.Catalog;
import com.tbs.trimplus.module.bible.utils.CatalogManage;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CatalogActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.recyclerview_selected)
    RecyclerView recyclerviewSelected;
    @BindView(R.id.recyclerview_noselected)
    RecyclerView recyclerviewNoselected;

    private ArrayList<Catalog> selectedCatalogs;
    private ArrayList<Catalog> noselectedCatalogs;
    private ArrayList<Catalog> allCatalogs = new ArrayList<>();

    private CatalogAdapter selectedAdapter;
    private CatalogAdapter noselectedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        ButterKnife.bind(this);

        toolbarTitle.setText("频道管理");
        toolbarRight.setText("保存");
        initToolbar(toolbar);
        toolbarRight.setVisibility(View.GONE);

        initData();
        initListener();
    }

    private void initData() {
        selectedCatalogs = CatalogManage.getSelectedCatalog(this, true);
        noselectedCatalogs = CatalogManage.getSelectedCatalog(this, false);

        selectedAdapter = new CatalogAdapter(this, selectedCatalogs, true);
        noselectedAdapter = new CatalogAdapter(this, noselectedCatalogs, false);

        GridLayoutManager manager1 = new GridLayoutManager(this, 4);
        GridLayoutManager manager2 = new GridLayoutManager(this, 4);

        recyclerviewSelected.setLayoutManager(manager1);
        recyclerviewNoselected.setLayoutManager(manager2);

        recyclerviewSelected.setAdapter(selectedAdapter);
        recyclerviewNoselected.setAdapter(noselectedAdapter);

    }


    private void initListener() {
        selectedAdapter.setOnItemClickListener(new CatalogAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Catalog catalog = selectedCatalogs.get(position);
                catalog.setSelected(false);

                noselectedCatalogs.add(catalog);
                noselectedAdapter.notifyDataSetChanged();

                selectedCatalogs.remove(position);
                selectedAdapter.notifyDataSetChanged();

                toolbarRight.setVisibility(View.VISIBLE);
            }
        });

        noselectedAdapter.setOnItemClickListener(new CatalogAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Catalog catalog = noselectedCatalogs.get(position);
                catalog.setSelected(true);

                selectedCatalogs.add(catalog);
                selectedAdapter.notifyDataSetChanged();

                noselectedCatalogs.remove(position);
                noselectedAdapter.notifyDataSetChanged();

                toolbarRight.setVisibility(View.VISIBLE);
            }
        });

        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allCatalogs.addAll(selectedCatalogs);
                allCatalogs.addAll(noselectedCatalogs);

                String string = JSON.toJSONString(allCatalogs);
                CacheUtil.putString(CatalogActivity.this, Constant.BIBLE_CATALOG, "mCatalog", string);
                ToastUtil.sToast(CatalogActivity.this, "保存成功");

                //更新了频道，bible页面数据刷新
                setResult(321);
                finish();
            }
        });
    }


}
