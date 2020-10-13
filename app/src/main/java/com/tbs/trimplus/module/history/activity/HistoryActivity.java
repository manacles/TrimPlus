package com.tbs.trimplus.module.history.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.bible.activity.BibleDetailActivity;
import com.tbs.trimplus.module.history.adapter.HistoryAdapter;
import com.tbs.trimplus.module.history.presenter.impl.GetHistoryRecordPresenter;
import com.tbs.trimplus.module.history.view.IgetHistoryRecordView;
import com.tbs.trimplus.module.main.bean.Bible;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.ToastUtil;
import com.tbs.trimplus.view.RecyclerViewOnUpScrollListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends BaseActivity implements IgetHistoryRecordView {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_data_empty)
    ImageView ivDataEmpty;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;

    private int page = 1;
    private ArrayList<Bible> data = new ArrayList<>();
    private ArrayList<Bible> tempList = new ArrayList<>();//临时数据表用于加载更多承载体
    private HistoryAdapter adapter;

    private GetHistoryRecordPresenter getHistoryRecordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);

        toolbarTitle.setText("历史记录");
        initToolbar(toolbar);

        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        initListener();

        getHistoryRecordPresenter = new GetHistoryRecordPresenter(new Model(), this);
        getHistoryRecordRequest(page);
    }

    private void initListener() {
        recyclerview.addOnScrollListener(onScrollListener);
        swiperefreshlayout.setOnRefreshListener(onRefreshListener);
    }

    private void getHistoryRecordRequest(int mpage) {
        if (adapter == null) {
            loadingLayout.setVisibility(View.VISIBLE);
        }
        swiperefreshlayout.setRefreshing(false);
        String uid = CacheUtil.getString(this, Constant.USER_INFO, "uid");
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("page", mpage);
        params.put("uid", uid);
        params.put("page_size", 10);
        getHistoryRecordPresenter.getHistoryRecord(params);
    }

    @Override
    public void getHistoryRecord(BaseList<Bible> bibleBaseList) {
        loadingLayout.setVisibility(View.GONE);
        if (adapter != null) {
            adapter.hideFootView();
        }
        if (bibleBaseList.getStatus().equals("200")) {
            tempList = bibleBaseList.getData();
            if (tempList.size() == 0 && page == 1) {
                ivDataEmpty.setVisibility(View.VISIBLE);
            }
            data.addAll(tempList);
            tempList.clear();

            if (adapter == null) {
                adapter = new HistoryAdapter(this, data);
                adapter.setOnItemClickListener(onRecyclerViewItemClickListener);
                recyclerview.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else if (bibleBaseList.getStatus().equals("201")) {
            ToastUtil.sToast(this, "没有更多数据！");
        } else {
            ToastUtil.sToast(this, "请求错误！");
        }
    }

    private RecyclerViewOnUpScrollListener onScrollListener = new RecyclerViewOnUpScrollListener() {
        @Override
        public void onLoadMore() {
            page++;
            adapter.showFootView();
            getHistoryRecordRequest(page);
        }
    };

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            //下拉刷新重置
            page = 1;
            data.clear();
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
            getHistoryRecordRequest(page);
        }
    };

    private HistoryAdapter.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = new HistoryAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onRecyclerViewItemClick(View view, Bible data) {
            // 跳到详情页
            goStartActivity(data.getAid(), data.getAuthor_id());
        }
    };

    private void goStartActivity(String articleId, String authorId) {
        Intent intent = new Intent(this, BibleDetailActivity.class);
        intent.putExtra("id", articleId);
        intent.putExtra("author_id", authorId);
        startActivity(intent);
    }
}
