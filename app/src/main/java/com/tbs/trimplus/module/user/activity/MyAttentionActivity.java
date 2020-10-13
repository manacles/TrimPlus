package com.tbs.trimplus.module.user.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.user.adapter.AuthorListAdapter;
import com.tbs.trimplus.module.user.bean.AuthorList;
import com.tbs.trimplus.module.user.presenter.impl.GetMyAttentionPresenter;
import com.tbs.trimplus.module.user.view.IgetMyAttentionView;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyAttentionActivity extends BaseActivity implements IgetMyAttentionView {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.iv_data_empty)
    ImageView ivDataEmpty;

    private GetMyAttentionPresenter getMyAttentionPresenter;
    private AuthorListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_attention);
        ButterKnife.bind(this);

        initToolbar(toolbar);
        toolbarTitle.setText("我的关注");

        getMyAttentionPresenter = new GetMyAttentionPresenter(new Model(), this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        getMyAttentionRequest();

        initListener();
    }

    private void initListener() {
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyAttentionRequest();
            }
        });
    }

    private void getMyAttentionRequest() {
        ivDataEmpty.setVisibility(View.GONE);
        swiperefreshlayout.setRefreshing(true);

        String uid = CacheUtil.getString(this, Constant.USER_INFO, "uid");
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("uid", uid);
        getMyAttentionPresenter.getMyAttention(params);
        LogUtil.e("--------getMyAttentionRequest---------");
    }

    @Override
    public void getMyAttention(BaseList<AuthorList> authorListBaseList) {
        swiperefreshlayout.setRefreshing(false);
        if (authorListBaseList.getStatus().equals("200")) {
            LogUtil.e("--------getMyAttention---------");
            ArrayList<AuthorList> authorLists = authorListBaseList.getData();

            if (authorLists.size() == 0) {
                ivDataEmpty.setVisibility(View.VISIBLE);
            } else {
                adapter = new AuthorListAdapter(this, authorLists);
                recyclerview.setAdapter(adapter);
            }
        } else {
            ivDataEmpty.setVisibility(View.VISIBLE);
        }

    }


}
