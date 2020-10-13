package com.tbs.trimplus.module.user.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.common.bean.BaseList;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.user.adapter.CollectAdapter;
import com.tbs.trimplus.module.user.bean.Collect;
import com.tbs.trimplus.module.user.presenter.impl.GetMyCollectPresenter;
import com.tbs.trimplus.module.user.view.IgetMyCollectView;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.ToastUtil;
import com.tbs.trimplus.view.CustomSelectDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCollectActivity extends BaseActivity implements IgetMyCollectView {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_data_empty)
    ImageView ivDataEmpty;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout;
    @BindView(R.id.tv_select_all)
    TextView tvSelectAll;
    @BindView(R.id.tv_del_all)
    TextView tvDelAll;
    @BindView(R.id.tv_del)
    TextView tvDel;
    @BindView(R.id.rl_del)
    RelativeLayout rlDel;

    private CollectAdapter adapter;

    private GetMyCollectPresenter getMyCollectPresenter;
    private int page = 1;

    private String uid;

    private boolean isDel;
    private boolean isSelectedAll;

    private List<String> ids = new ArrayList<>();
    private List<String> allId = new ArrayList<>();
    private ArrayList<Collect> collects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollect);
        ButterKnife.bind(this);

        initToolbar(toolbar);
        toolbarTitle.setText("我的收藏");
        toolbarRight.setText("管理");

        getMyCollectPresenter = new GetMyCollectPresenter(new Model(), this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        getMyCollectRequest();

        initListener();

    }

    //初始化数据(用于删除操作成功后刷新页面)
    private void initData() {
        page = 1;
        ids.clear();
        allId.clear();
        collects.clear();

        isDel = false;
        isSelectedAll = false;

        toolbarRight.setText("管理");
        rlDel.setVisibility(View.GONE);
        getMyCollectRequest();
    }


    private void initListener() {
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getMyCollectRequest();
            }
        });
    }

    private void getMyCollectRequest() {
        ivDataEmpty.setVisibility(View.GONE);
        swiperefreshlayout.setRefreshing(true);

        uid = CacheUtil.getString(this, Constant.USER_INFO, "uid");
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("uid", uid);
        params.put("page", page);
        params.put("page_size", 500);
        getMyCollectPresenter.getMyCollect(params);
        LogUtil.e("--------getMyAttentionRequest---------");
    }

    @Override
    public void getMyCollect(BaseList<Collect> collectBaseList) {
        swiperefreshlayout.setRefreshing(false);
        if (collectBaseList.getStatus().equals("200")) {
            LogUtil.e("--------getMyAttention---------");
            collects = collectBaseList.getData();
            //设置页面展示的所有收藏id
            for (int i = 0; i < collects.size(); i++) {
                allId.add(collects.get(i).getId());
            }

            if (collects.size() == 0) {
                ivDataEmpty.setVisibility(View.VISIBLE);
            } else {
                adapter = new CollectAdapter(this, collects);
                recyclerview.setAdapter(adapter);
            }
        } else {
            ivDataEmpty.setVisibility(View.VISIBLE);
        }

        if (adapter != null) {
            adapter.setOnItemClickListener(new CollectAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, Collect collect) {
                    CollectAdapter.ViewHolder holder = (CollectAdapter.ViewHolder) view.getTag();
                    if (isDel) {
                        //管理收藏页面
                        if (collect.isChecked()) {
                            //选中状态下点击
                            collect.setChecked(false);
                            holder.check.setImageResource(R.drawable.no_check);

                            //处理ids
                            ids.remove(collect.getId());
                            if (ids.size() < collects.size()) {
                                tvSelectAll.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.no_check), null, null, null);
                                isSelectedAll = false;
                            }
                        } else {
                            //非选中状态下点击
                            collect.setChecked(true);
                            holder.check.setImageResource(R.drawable.check);

                            //处理ids
                            if (!ids.contains(collect.getId())) {
                                ids.add(collect.getId());
                            }
                            if (ids.size() == collects.size()) {
                                tvSelectAll.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.check), null, null, null);
                                isSelectedAll = true;
                            }
                        }
                    } else {
                        // 跳到详情页
                        holder.goStartActivity(collect.getAid(), collect.getAuthor_id());
                    }
                }
            });
        }
    }

    @OnClick({R.id.toolbar_right, R.id.tv_select_all, R.id.tv_del_all, R.id.tv_del})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right:
                if (isDel) {
                    //管理状态下点击
                    rlDel.setVisibility(View.GONE);
                    toolbarRight.setText("管理");
                    isDel = false;
                    if (adapter != null) {
                        adapter.setDel(false);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    //非管理状态下点击
                    rlDel.setVisibility(View.VISIBLE);
                    toolbarRight.setText("完成");
                    isDel = true;
                    if (adapter != null) {
                        adapter.setDel(true);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;
            case R.id.tv_select_all:
                if (isSelectedAll) {
                    //全选状态下点击
                    ids.clear();
                    tvSelectAll.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.no_check), null, null, null);
                    isSelectedAll = false;
                    if (adapter != null) {
                        adapter.setSelectedAll(false);
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    //非全选状态下点击
                    ids.clear();
                    ids.addAll(allId);

                    tvSelectAll.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.check), null, null, null);
                    isSelectedAll = true;
                    if (adapter != null) {
                        adapter.setSelectedAll(true);
                        adapter.notifyDataSetChanged();
                    }
                }

                break;
            case R.id.tv_del_all:
                CustomSelectDialog deleteDialog = new CustomSelectDialog(MyCollectActivity.this,
                        "您确定清空所有收藏吗？", 1, R.style.custom_dialog) {
                    @Override
                    public void onSureClick() {
                        delAllCollectRequest();
                    }
                };
                deleteDialog.show();
                break;
            case R.id.tv_del:
                delCheckCollectRequest();
                break;
        }
    }

    private void delCheckCollectRequest() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < ids.size(); i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            stringBuilder.append(ids.get(i));
        }
        String idStr = String.valueOf(stringBuilder);

        LogUtil.e(idStr);

        if (TextUtils.isEmpty(idStr)) {
            return;
        }

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("id", idStr);
        getMyCollectPresenter.delCheckCollect(params);

    }

    @Override
    public void delCheckCollect(BaseObject baseObject) {
        LogUtil.e(baseObject.toString());
        if (baseObject.getStatus().equals("200")) {
            initData();
        }
        ToastUtil.sToast(this, baseObject.getMsg());
    }

    private void delAllCollectRequest() {
        if (TextUtils.isEmpty(uid)) {
            return;
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("uid", uid);
        getMyCollectPresenter.delAllCollect(params);
    }

    @Override
    public void delAllCollect(BaseObject baseObject) {
        LogUtil.e(baseObject.toString());
        if (baseObject.getStatus().equals("200")) {
            initData();
        }
        ToastUtil.sToast(this, baseObject.getMsg());
    }
}
