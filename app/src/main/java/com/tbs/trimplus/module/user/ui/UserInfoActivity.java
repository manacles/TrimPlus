package com.tbs.trimplus.module.user.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.user.bean.UserInfo;
import com.tbs.trimplus.module.user.presenter.impl.GetUserInfoDataPresenter;
import com.tbs.trimplus.module.user.view.IgetUserInfoDataView;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.GlideUtil;
import com.tbs.trimplus.utils.ToastUtil;
import com.tbs.trimplus.view.SignOutDialog;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserInfoActivity extends BaseActivity implements IgetUserInfoDataView {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_userinfo_name)
    TextView tvUserinfoName;
    @BindView(R.id.ll_userinfo_name)
    LinearLayout llUserinfoName;
    @BindView(R.id.iv_userinfo_icon)
    ImageView ivUserinfoIcon;
    @BindView(R.id.ll_userinfo_icon)
    LinearLayout llUserinfoIcon;
    @BindView(R.id.tv_userinfo_sex)
    TextView tvUserinfoSex;
    @BindView(R.id.ll_userinfo_sex)
    LinearLayout llUserinfoSex;
    @BindView(R.id.tv_userinfo_city)
    TextView tvUserinfoCity;
    @BindView(R.id.ll_userinfo_city)
    LinearLayout llUserinfoCity;
    @BindView(R.id.tv_userinfo_decorate_type)
    TextView tvUserinfoDecorateType;
    @BindView(R.id.ll_userinfo_decorate_type)
    LinearLayout llUserinfoDecorateType;
    @BindView(R.id.ll_userinfo_sign_out)
    LinearLayout llUserinfoSignOut;

    private GetUserInfoDataPresenter getUserInfoDataPresenter;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        initListenter();

        getUserInfoDataPresenter = new GetUserInfoDataPresenter(new Model(), this);
        getUserInfoDataRequest();
    }

    private void initListenter() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        llUserinfoSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignOutDialog signOutDialog = new SignOutDialog(UserInfoActivity.this, R.style.custom_dialog) {

                    @Override
                    public void onSureClick() {
                        SharedPreferences sharedPreferences = UserInfoActivity.this.getSharedPreferences(Constant.USER_INFO, Context.MODE_PRIVATE);
                        sharedPreferences.edit().clear().apply();
                        ToastUtil.sToast(UserInfoActivity.this, "退出成功");
                        finish();
                    }
                };
                signOutDialog.show();
            }
        });
    }

    private void getUserInfoDataRequest() {
        String uid = CacheUtil.getString(this, Constant.USER_INFO, "uid");
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("uid", uid);
        getUserInfoDataPresenter.getUserInfoData(params);
    }

    @Override
    public void getUserInfoData(BaseObject<UserInfo> userInfoBaseObject) {
        if (userInfoBaseObject.getStatus().equals("200")) {
            ToastUtil.sToast(this, "个人信息获取成功");
            LogUtil.e(userInfoBaseObject.toString());

            userInfo = userInfoBaseObject.getData();
            showData();
        } else {
            ToastUtil.sToast(this, userInfoBaseObject.getMsg());
        }
    }

    @Override
    public void DialogStatus(int status, String error) {

    }

    private void showData() {
        String icon = CacheUtil.getString(this, Constant.USER_INFO, "icon");
        GlideUtil.glideLoader(this, icon, R.drawable.icon_head_default, R.drawable.icon_head_default,
                ivUserinfoIcon, GlideUtil.CIRCLE_IMAGE);
        tvUserinfoName.setText(userInfo.getName());
        tvUserinfoSex.setText(userInfo.getGender());
        tvUserinfoCity.setText(userInfo.getCity());
        tvUserinfoDecorateType.setText(userInfo.getDecorate_type());
    }
}
