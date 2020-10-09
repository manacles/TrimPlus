package com.tbs.trimplus.module.bible.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.bible.adapter.SearchWordAdapter;
import com.tbs.trimplus.module.bible.presenter.impl.DoSearchPresenter;
import com.tbs.trimplus.module.bible.utils.SearchHistoryManage;
import com.tbs.trimplus.module.bible.view.IdoSearchView;
import com.tbs.trimplus.module.history.adapter.HistoryAdapter;
import com.tbs.trimplus.module.main.bean.Bible;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.NetUtil;
import com.tbs.trimplus.utils.ToastUtil;
import com.tbs.trimplus.view.CustomSelectDialog;
import com.tbs.trimplus.view.RecyclerViewOnUpScrollListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity implements IdoSearchView {

    @BindView(R.id.et_bible_search)
    EditText etBibleSearch;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.iv_delete_history)
    ImageView ivDeleteHistory;
    @BindView(R.id.rl_search_history)
    RelativeLayout rlSearchHistory;
    @BindView(R.id.recyclerview_history)
    RecyclerView recyclerviewHistory;
    @BindView(R.id.iv_see)
    ImageView ivSee;
    @BindView(R.id.rl_search_recommend)
    RelativeLayout rlSearchRecommend;
    @BindView(R.id.recyclerview_recommend)
    RecyclerView recyclerviewRecommend;
    @BindView(R.id.ll_no_search)
    LinearLayout llNoSearch;
    @BindView(R.id.recyclerview_data)
    RecyclerView recyclerviewData;
    @BindView(R.id.iv_data_empty)
    ImageView ivDataEmpty;
    @BindView(R.id.iv_net_out)
    ImageView ivNetOut;
    @BindView(R.id.tv_set_net)
    TextView tvSetNet;
    @BindView(R.id.netout_layout)
    RelativeLayout netoutLayout;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;

    private SearchWordAdapter historyAdapter;
    private SearchWordAdapter recommendAdapter;

    private DoSearchPresenter doSearchPresenter;

    private boolean canSee = false;
    private String searchWord;

    private int page = 1;
    private ArrayList<Bible> data = new ArrayList<>();
    private ArrayList<Bible> tempList = new ArrayList<>();//临时数据表用于加载更多承载体
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        doSearchPresenter = new DoSearchPresenter(new Model(), this);
        recyclerviewData.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        initHistory();
        getKeyWordRequest();

        initListener();
    }

    private void initHistory() {
        List<String> list = SearchHistoryManage.getHistory(this);
        if (list != null) {
            rlSearchHistory.setVisibility(View.VISIBLE);

            historyAdapter = new SearchWordAdapter(this, list);
            recyclerviewHistory.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
            recyclerviewHistory.setAdapter(historyAdapter);
        } else {
            rlSearchHistory.setVisibility(View.GONE);
        }
    }

    private void getKeyWordRequest() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("token", AppUtil.getDateToken());
        doSearchPresenter.getKeyWord(param);
    }

    @Override
    public void getKeyWord(BaseList<Map> mapBaseList) {
        if (mapBaseList.getStatus().equals("200")) {
            List<Map> list = mapBaseList.getData();
            if (list != null && list.size() > 0) {
                List<String> data = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    data.add((String) list.get(i).get("key_word"));
                }

                recommendAdapter = new SearchWordAdapter(this, data);
                recyclerviewRecommend.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.HORIZONTAL));
                recyclerviewRecommend.setAdapter(recommendAdapter);
            }
        }
        if (recommendAdapter != null) {
            recommendAdapter.setOnItemClickListener(new SearchWordAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view) {
                    searchWord = (String) view.getTag();
                    etBibleSearch.setText(searchWord);
                    etBibleSearch.setSelection(searchWord.length());
                }
            });
        }
    }

    private void getSearchArticleRequest() {

        llNoSearch.setVisibility(View.GONE);
        swiperefreshlayout.setVisibility(View.VISIBLE);

        swiperefreshlayout.setRefreshing(false);
        if (adapter == null) {
            loadingLayout.setVisibility(View.VISIBLE);
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("title", searchWord);
        params.put("page", page);
        params.put("page_size", 10);
        doSearchPresenter.getSearchArticle(params);
    }

    @Override
    public void getSearchArticle(BaseList<Bible> bibleBaseList) {
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
                adapter.setSearchWord(searchWord);
                adapter.setOnItemClickListener(onRecyclerViewItemClickListener);
                recyclerviewData.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else if (bibleBaseList.getStatus().equals("201")) {
            if (page == 1) {
                ivDataEmpty.setVisibility(View.VISIBLE);
            } else {
                ToastUtil.sToast(this, "没有更多数据！");
            }
        } else {
            if (page == 1) {
                ivDataEmpty.setVisibility(View.VISIBLE);
            }
            LogUtil.e("------" + bibleBaseList.getStatus() + "--" + bibleBaseList.getMsg() + "-----");
        }
    }

    @OnClick({R.id.iv_cancel, R.id.iv_delete_history, R.id.iv_see, R.id.tv_back, R.id.tv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                searchWord = etBibleSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(searchWord)) {
                    SearchHistoryManage.setHistory(this, searchWord);

                    if (NetUtil.isNetAvailable(this)) {
                        data.clear();
                        if (adapter != null) {
                            adapter.setSearchWord(searchWord);
                        }
                        getSearchArticleRequest();

                        tvSearch.setVisibility(View.GONE);
                        tvBack.setVisibility(View.VISIBLE);
                    } else {
                        netoutLayout.setVisibility(View.VISIBLE);
                    }

                    //收回键盘
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                }
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.iv_cancel:
                etBibleSearch.setText("");
                break;
            case R.id.iv_delete_history:
                CustomSelectDialog deleteDialog = new CustomSelectDialog(this,
                        "您确认删除所有的搜索历史吗？", 1, R.style.custom_dialog) {
                    @Override
                    public void onSureClick() {
                        SearchHistoryManage.deleteHistory(SearchActivity.this);
                        rlSearchHistory.setVisibility(View.GONE);
                        recyclerviewHistory.setVisibility(View.GONE);
                    }
                };
                deleteDialog.show();
                break;
            case R.id.iv_see:
                if (canSee) {
                    recyclerviewRecommend.setVisibility(View.VISIBLE);
                    ivSee.setImageResource(R.drawable.visible);
                    canSee = false;
                } else {
                    recyclerviewRecommend.setVisibility(View.GONE);
                    ivSee.setImageResource(R.drawable.not_visible);
                    canSee = true;
                }
                break;
        }
    }

    private void initListener() {
        etBibleSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ivCancel.setVisibility(View.GONE);
                    tvBack.setVisibility(View.VISIBLE);
                    tvSearch.setVisibility(View.GONE);
                } else {
                    ivCancel.setVisibility(View.VISIBLE);
                    tvBack.setVisibility(View.GONE);
                    tvSearch.setVisibility(View.VISIBLE);
                }
            }
        });

        if (historyAdapter != null) {
            historyAdapter.setOnItemClickListener(new SearchWordAdapter.OnItemClickListener() {

                @Override
                public void onClick(View view) {
                    searchWord = (String) view.getTag();
                    etBibleSearch.setText(searchWord);
                    etBibleSearch.setSelection(searchWord.length());
                }
            });
        }

        recyclerviewData.addOnScrollListener(onScrollListener);
        swiperefreshlayout.setOnRefreshListener(onRefreshListener);
    }

    private RecyclerViewOnUpScrollListener onScrollListener = new RecyclerViewOnUpScrollListener() {
        @Override
        public void onLoadMore() {
            page++;
            adapter.showFootView();
            getSearchArticleRequest();
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
            getSearchArticleRequest();
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
