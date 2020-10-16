package com.tbs.trimplus.module.user.activity;

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
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.bible.activity.BibleDetailActivity;
import com.tbs.trimplus.module.bible.utils.ReadMessageManage;
import com.tbs.trimplus.module.user.adapter.MessageAdapter;
import com.tbs.trimplus.module.user.bean.Message;
import com.tbs.trimplus.module.user.presenter.impl.GetPushLogPresenter;
import com.tbs.trimplus.module.user.view.IgetPushLogView;
import com.tbs.trimplus.utils.ToastUtil;
import com.tbs.trimplus.view.RecyclerViewOnUpScrollListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageCenterActivity extends BaseActivity implements IgetPushLogView {
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
    private ArrayList<Message.DataBeanX.DataBean> data = new ArrayList<>();
    private ArrayList<Message.DataBeanX.DataBean> tempList = new ArrayList<>();//临时数据表用于加载更多承载体
    private MessageAdapter adapter;

    private GetPushLogPresenter getPushLogPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        ButterKnife.bind(this);

        toolbarTitle.setText("消息中心");
        initToolbar(toolbar);

        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        initListener();

        getPushLogPresenter = new GetPushLogPresenter(new Model(), this);
        loadingLayout.setVisibility(View.VISIBLE);
        getPushLogRequest(page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> haveReads = ReadMessageManage.getHaveRead(this);
        if (haveReads != null && adapter != null) {
            adapter.setHaveReads(haveReads);
            adapter.notifyDataSetChanged();
        }
    }

    private void initListener() {
        recyclerview.addOnScrollListener(onScrollListener);
        swiperefreshlayout.setOnRefreshListener(onRefreshListener);
    }

    private void getPushLogRequest(int mpage) {
        ivDataEmpty.setVisibility(View.GONE);

        swiperefreshlayout.setRefreshing(false);

        HashMap<String, Object> params = new HashMap<>();
        params.put("page", mpage);
        params.put("page_size", 10);
        getPushLogPresenter.getPushLog(params);
    }

    @Override
    public void getPushLog(com.tbs.trimplus.module.user.bean.Message message) {
        loadingLayout.setVisibility(View.GONE);
        if (adapter != null) {
            adapter.hideFootView();
        }
        if (message.getError_code().equals("0")) {
            tempList = (ArrayList<Message.DataBeanX.DataBean>) message.getData().getData();
            if (tempList.size() == 0 && page == 1) {
                ivDataEmpty.setVisibility(View.VISIBLE);
            }
            data.addAll(tempList);
            tempList.clear();

            if (adapter == null) {
                adapter = new MessageAdapter(this, data);
                adapter.setOnItemClickListener(onRecyclerViewItemClickListener);
                recyclerview.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        } else if (message.getError_code().equals("201")) {
            ivDataEmpty.setVisibility(View.VISIBLE);
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
            getPushLogRequest(page);
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
            getPushLogRequest(page);
        }
    };

    private MessageAdapter.OnRecyclerViewItemClickListener onRecyclerViewItemClickListener = new MessageAdapter.OnRecyclerViewItemClickListener() {
        @Override
        public void onRecyclerViewItemClick(View view, Message.DataBeanX.DataBean data) {

            ReadMessageManage.setHaveRead(MessageCenterActivity.this, data.getAid());
            // 跳到详情页
            goStartActivity(data.getAid(), data.getUid());
        }
    };

    private void goStartActivity(String articleId, String authorId) {
        Intent intent = new Intent(this, BibleDetailActivity.class);
        intent.putExtra("id", articleId);
        intent.putExtra("author_id", authorId);
        startActivity(intent);
    }

}
