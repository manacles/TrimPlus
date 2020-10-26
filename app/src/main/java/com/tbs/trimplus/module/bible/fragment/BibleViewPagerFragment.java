package com.tbs.trimplus.module.bible.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseFragment;
import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.bible.activity.BibleDetailActivity;
import com.tbs.trimplus.module.bible.bean.Catalog;
import com.tbs.trimplus.module.bible.presenter.impl.GetArticlePresenter;
import com.tbs.trimplus.module.bible.utils.CatalogManage;
import com.tbs.trimplus.module.bible.view.IgetArticleView;
import com.tbs.trimplus.module.history.adapter.HistoryAdapter;
import com.tbs.trimplus.module.main.bean.Bible;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.ToastUtil;
import com.tbs.trimplus.view.RecyclerViewOnUpScrollListener;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BibleViewPagerFragment extends BaseFragment implements IgetArticleView {

    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.iv_data_empty)
    ImageView ivDataEmpty;

    private Catalog catalog;
    private int position;

    private int page = 1;
    private int pageSize = 10;
    private ArrayList<Bible> data = new ArrayList<>();
    private ArrayList<Bible> tempList = new ArrayList<>();//临时数据表用于加载更多承载体
    private HistoryAdapter adapter;

    private GetArticlePresenter getArticlePresenter;


    @Override
    public View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_bible_viewpager, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        if (getArguments() != null) {
            position = getArguments().getInt("position");

            ArrayList<Catalog> selectedCatalogs = CatalogManage.getSelectedCatalog(context, true);
            if (selectedCatalogs != null) {

                catalog = selectedCatalogs.get(position);

                recyclerview.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

                initListener();

                getArticlePresenter = new GetArticlePresenter(new Model(), this);
                loadingLayout.setVisibility(View.VISIBLE);
                getArticleRequest(page);
            }
        }

    }

    private void initListener() {
        recyclerview.addOnScrollListener(onScrollListener);
        swiperefreshlayout.setOnRefreshListener(onRefreshListener);
    }

    private void getArticleRequest(int mpage) {
        ivDataEmpty.setVisibility(View.GONE);
        swiperefreshlayout.setRefreshing(false);

        HashMap<String, Object> params = new HashMap<>();
        params.put("page", mpage + "");
        params.put("pageSize", pageSize + "");
        params.put("token", AppUtil.getDateToken());
        params.put("type_id", catalog.getId());
        if (catalog.getName().equals("推荐")) {
            params.put("status", "1");
        } else if (catalog.getName().equals("本地")) {
            String city = AppUtil.getCity(context);
            if (city.contains("市")) {
                city = city.replace("市", "");
            } else if (city.contains("县")) {
                city = city.replace("县", "");
            }
            params.put("status", "2");
            params.put("city", city);
            LogUtil.e("-----city" + city + "-----------------");
        }
        getArticlePresenter.getArticle(params);
    }

    @Override
    public void getArticle(BaseList<Bible> bibleBaseList) {
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
                adapter = new HistoryAdapter(context, data);
                adapter.setOnItemClickListener(onRecyclerViewItemClickListener);
                recyclerview.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else if (bibleBaseList.getStatus().equals("201")) {
            if (page == 1) {
                ivDataEmpty.setVisibility(View.VISIBLE);
            } else {
                ToastUtil.sToast(context, "没有更多数据！");
            }
        } else {
            if (page == 1) {
                ivDataEmpty.setVisibility(View.VISIBLE);
            }
            LogUtil.e("------" + bibleBaseList.getStatus() + "--" + bibleBaseList.getMsg() + "-----");
        }
    }

    private RecyclerViewOnUpScrollListener onScrollListener = new RecyclerViewOnUpScrollListener() {
        @Override
        public void onLoadMore() {
            page++;
            adapter.showFootView();
            getArticleRequest(page);
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
            getArticleRequest(page);
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
        Intent intent = new Intent(context, BibleDetailActivity.class);
        intent.putExtra("id", articleId);
        intent.putExtra("author_id", authorId);
        startActivity(intent);
    }
}
