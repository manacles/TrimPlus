package com.tbs.trimplus.module.main.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseFragment;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.history.activity.HistoryActivity;
import com.tbs.trimplus.module.login.activity.LoginActivity;
import com.tbs.trimplus.module.main.adapter.RecyclerViewMineAdapter;
import com.tbs.trimplus.module.main.bean.Mine;
import com.tbs.trimplus.module.main.presenter.impl.GetMineDataPresenter;
import com.tbs.trimplus.module.main.view.IgetMineDataView;
import com.tbs.trimplus.module.user.activity.MyCollectActivity;
import com.tbs.trimplus.module.user.activity.UserInfoActivity;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.GlideUtil;
import com.tbs.trimplus.utils.ToastUtil;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends BaseFragment implements IgetMineDataView {

    @BindView(R.id.iv_mine_icon)
    ImageView ivMineIcon;
    @BindView(R.id.tv_mine_name)
    TextView tvMineName;
    @BindView(R.id.rl_mine_user)
    RelativeLayout rlMineUser;
    @BindView(R.id.tv_mine_num_scan)
    TextView tvMineNumScan;
    @BindView(R.id.ll_mine_scan)
    LinearLayout llMineScan;
    @BindView(R.id.tv_mine_num_collect)
    TextView tvMineNumCollect;
    @BindView(R.id.ll_mine_collect)
    LinearLayout llMineCollect;
    @BindView(R.id.tv_mine_num_like)
    TextView tvMineNumLike;
    @BindView(R.id.ll_mine_like)
    LinearLayout llMineLike;
    @BindView(R.id.ll_mine_user_num)
    LinearLayout llMineUserNum;
    @BindView(R.id.recyclerview_mine)
    RecyclerView recyclerviewMine;

    private GetMineDataPresenter getMineDataPresenter;

    private RecyclerViewMineAdapter recyclerViewMineAdapter;
    private Mine mine;
    private boolean isLogin = false;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_mine, null);
        ButterKnife.bind(this, view);

        mine = new Mine();
        getMineDataPresenter = new GetMineDataPresenter(new Model(), this);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        //初始化用户数据
        initUserInfo();
        LogUtil.e("------onResume-------");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            initUserInfo();
            LogUtil.e("------onHiddenChanged-------");
        }
    }

    private void initUserInfo() {
        if (AppUtil.isLogin(context)) {
            isLogin = true;

            String icon = CacheUtil.getString(context, Constant.USER_INFO, "icon");
            String name = CacheUtil.getString(context, Constant.USER_INFO, "name");
            String uid = CacheUtil.getString(context, Constant.USER_INFO, "uid");

            LogUtil.e("------uid---" + uid + "------------");

            llMineUserNum.setVisibility(View.VISIBLE);
            tvMineName.setText(name);
            GlideUtil.glideLoader(context, icon, R.drawable.icon_head_default, R.drawable.icon_head_default,
                    ivMineIcon, GlideUtil.CIRCLE_IMAGE);

            //如果登录状态，获取数据
            getMineDataRequest(uid);
        } else {
            isLogin = false;

            llMineUserNum.setVisibility(View.GONE);
            ivMineIcon.setImageResource(R.drawable.icon_head_default);
            tvMineName.setText("点击登录");
        }
        //设置Mine页面数据
        recyclerViewMineAdapter = new RecyclerViewMineAdapter(context, mine, isLogin);
        recyclerviewMine.setAdapter(recyclerViewMineAdapter);
        recyclerviewMine.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));

    }


    @OnClick({R.id.rl_mine_user, R.id.ll_mine_scan, R.id.ll_mine_collect, R.id.ll_mine_like})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_mine_user:
                /*
                 * 1.用户未登录状态----》进入登录页面
                 * 2.用户已经登录状态----》进入用户的详情界面
                 */
                if (AppUtil.isLogin(context)) {
                    //已经登录状态
                    startActivity(new Intent(context, UserInfoActivity.class));
                } else {
                    //未登录
                    startActivity(new Intent(context, LoginActivity.class));
                }
                break;
            case R.id.ll_mine_scan:
                startActivity(new Intent(context, HistoryActivity.class));
                break;
            case R.id.ll_mine_collect:
                startActivity(new Intent(context, MyCollectActivity.class));
                break;
            case R.id.ll_mine_like:
                break;
        }
    }


    @Override
    public void DialogStatus(int status, String error) {

    }

    private void getMineDataRequest(String uid) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("uid", uid);
        getMineDataPresenter.getMineData(params);
    }


    @Override
    public void getMineData(BaseObject<Mine> mineBaseObject) {
        if (mineBaseObject.getStatus().equals("200")) {
            mine = mineBaseObject.getData();
            //获取到数据，刷新recyclerview展示数据
            showData();
        } else {
            ToastUtil.sToast(context, mineBaseObject.getMsg());
        }
    }

    private void showData() {
        //登录状态，设置数据
        if (mine != null) {
            tvMineNumScan.setText(mine.getView_count());
            tvMineNumCollect.setText(mine.getCollect_count());
            tvMineNumLike.setText(mine.getTup_count());
            recyclerViewMineAdapter.refreshData(mine);
        }
    }


}
