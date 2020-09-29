package com.tbs.trimplus.module.bible.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.bible.adapter.AuthorDetailAdapter;
import com.tbs.trimplus.module.bible.bean.Author;
import com.tbs.trimplus.module.bible.presenter.impl.DoAuthorDetailPresenter;
import com.tbs.trimplus.module.bible.view.IdoAuthorDetilView;
import com.tbs.trimplus.module.login.activity.LoginActivity;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.GlideUtil;
import com.tbs.trimplus.utils.ToastUtil;
import com.tbs.trimplus.view.RecyclerViewOnUpScrollListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AuthorDetailActivity extends BaseActivity implements IdoAuthorDetilView {


    @BindView(R.id.iv_author_icon)
    ImageView ivAuthorIcon;
    @BindView(R.id.tv_attention)
    TextView tvAttention;
    @BindView(R.id.tv_total_num)
    TextView tvTotalNum;
    @BindView(R.id.recyclerview_author_article)
    RecyclerView recyclerviewAuthorArticle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;
//    @BindView(R.id.nestedScrollView)
//    NestedScrollView nestedScrollView;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_author_name)
    TextView tvAuthorName;

    private Intent intent;
    private String authorId;
    private String pageNum;
    private int page = 1;

    private DoAuthorDetailPresenter doAuthorDetailPresenter;
    private Author author;
    private List<Author.ArticleBean> list = new ArrayList<>();
    private AuthorDetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_detail);
        ButterKnife.bind(this);

        initView();

        doAuthorDetailPresenter = new DoAuthorDetailPresenter(new Model(), this);
        getAuthorDetailRequest(page);
    }

    private void initView() {
        getWindow().setStatusBarColor(Color.GRAY);

        intent = getIntent();
        authorId = intent.getStringExtra("author_id");
        pageNum = intent.getStringExtra("page_num");

        initToolbar(toolbar);

        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerviewAuthorArticle.setLayoutManager(manager);

        recyclerviewAuthorArticle.addOnScrollListener(onScrollListener);
    }

    private void getAuthorDetailRequest(int page) {
        LogUtil.e(page + "---" + pageNum);
        if (page == 1) {
            ivAuthorIcon.setVisibility(View.GONE);
            findViewById(R.id.ll_author).setVisibility(View.GONE);
            loadingLayout.setVisibility(View.VISIBLE);
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("author_id", authorId);
        if (AppUtil.isLogin(this)) {
            params.put("uid", CacheUtil.getString(this, Constant.USER_INFO, "uid"));
        }
        params.put("page", page);
        if (TextUtils.isEmpty(pageNum)) {
            params.put("page_size", 10);
        } else {
            params.put("page_size", pageNum);
        }
        doAuthorDetailPresenter.getAuthorDetail(params);
    }

    @Override
    public void getAuthorDetail(BaseObject<Author> authorBaseObject) {
        ivAuthorIcon.setVisibility(View.VISIBLE);
        findViewById(R.id.ll_author).setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);

        if (adapter != null) {
            adapter.hideFootView();
        }

        if (authorBaseObject.getStatus().equals("200")) {
            author = authorBaseObject.getData();

            if (page == 1) {
                toolbarTitle.setText(author.getAuthor().getNick());
                GlideUtil.glideLoader(this, author.getAuthor().getIcon(),
                        0, 0, ivAuthorIcon, GlideUtil.CIRCLE_IMAGE);
                tvAuthorName.setText(author.getAuthor().getNick());
                if (!author.getAuthor().getIs_follow().equals("0")) {
                    tvAttention.setText("已关注");
                    tvAttention.setTextColor(Color.GRAY);
                }
                tvTotalNum.setText(author.getAuthor().getArticle_count());
            }

            showArticle(author.getArticle());

        } else {
            ToastUtil.sToast(this, authorBaseObject.getMsg());
        }
    }

    private void showArticle(List<Author.ArticleBean> article) {
        if (author.getArticle().size() == 0) {
            //当前没有更多数据
            ToastUtil.sToast(this, "没有更多数据了~");
        }
        list.addAll(article);
        if (adapter == null) {
            adapter = new AuthorDetailAdapter(this, list);
            recyclerviewAuthorArticle.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }


    @OnClick(R.id.tv_attention)
    public void onViewClicked() {
        //关注作者
        if (AppUtil.isLogin(this)) {
            //请求关注接口
            followAuthorRequest();
        } else {
            //当前用户没有登录跳转登录
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void followAuthorRequest() {
        String uid = CacheUtil.getString(this, Constant.USER_INFO, "uid");
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("uid", uid);
        params.put("author_id", authorId);
        params.put("system_type", "1");
        doAuthorDetailPresenter.followAuthor(params);
    }

    @Override
    public void followAuthor(BaseObject baseObject) {
        String msg = baseObject.getMsg();
        if (msg.equals("关注成功")) {
            tvAttention.setText("已关注");
            tvAttention.setTextColor(Color.GRAY);
        } else {
            tvAttention.setText("+ 关注");
            tvAttention.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        ToastUtil.sToast(this, msg);
    }

    private RecyclerViewOnUpScrollListener onScrollListener = new RecyclerViewOnUpScrollListener() {
        @Override
        public void onLoadMore() {
            LogUtil.e("------onLoadMore-");
            page++;
            adapter.showFootView();
            getAuthorDetailRequest(page);
        }
    };
}
