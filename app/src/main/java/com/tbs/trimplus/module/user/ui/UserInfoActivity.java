package com.tbs.trimplus.module.user.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.ListPopupWindow;
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
import com.tbs.trimplus.view.BottomListPopupWindow;
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
    private String uid;

    private BottomListPopupWindow sexPopupWindow;
    private BottomListPopupWindow decorateTypePopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);

        initToolbar(toolbar);
        toolbarTitle.setText("个人信息");

        uid = CacheUtil.getString(this, Constant.USER_INFO, "uid");

        initView();
        initListenter();

        getUserInfoDataPresenter = new GetUserInfoDataPresenter(new Model(), this);
        getUserInfoDataRequest();
    }

    private void initView() {
        String mark = CacheUtil.getString(this, Constant.USER_INFO, "mark");
        if (mark.equals("3")) {
            llUserinfoSex.setVisibility(View.GONE);
            llUserinfoDecorateType.setVisibility(View.GONE);
        }
    }

    private void initListenter() {
        //修改昵称
        llUserinfoName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this, ChangeUserNameActivity.class);
                intent.putExtra("username", tvUserinfoName.getText().toString().trim());
                startActivityForResult(intent, Constant.CHANGE_USERNAME_REQUESTCODE);
            }
        });

        //修改性别
        llUserinfoSex.setOnClickListener(new View.OnClickListener() {
            String[] data = Constant.SEX_DATA;

            @Override
            public void onClick(View v) {
                sexPopupWindow = new BottomListPopupWindow(UserInfoActivity.this, "选择性别", data);
                sexPopupWindow.showAtLocation(UserInfoActivity.this.findViewById(R.id.userinfo_layout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                sexPopupWindow.setOnItemClickListener(new BottomListPopupWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (tvUserinfoSex.getText().toString().trim().equals(data[position])) {
                            return;
                        }
                        setGenderRequest(position);
                    }
                });
            }
        });

        //城市
        llUserinfoCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoActivity.this,ChangeCityActivity.class);
                startActivityForResult(intent,Constant.CHANGE_USER_CITY_RESULTCODE);
            }
        });

        //装修阶段
        llUserinfoDecorateType.setOnClickListener(new View.OnClickListener() {
            String[] data = Constant.DECORATETYPE_DATA;

            @Override
            public void onClick(View v) {
                decorateTypePopupWindow = new BottomListPopupWindow(UserInfoActivity.this,
                        "装修阶段", data);
                decorateTypePopupWindow.showAtLocation(UserInfoActivity.this.findViewById(R.id.userinfo_layout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                decorateTypePopupWindow.setOnItemClickListener(new BottomListPopupWindow.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (tvUserinfoDecorateType.getText().toString().trim().equals(data[position])) {
                            return;
                        }
                        setDecorateRequest(position);
                    }
                });

            }
        });

        //退出登录
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

    private void setGenderRequest(int sex) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("token", AppUtil.getDateToken());
        param.put("uid", uid);
        param.put("gender", sex);
        getUserInfoDataPresenter.setGender(param, sex);
    }

    @Override
    public void setGender(BaseObject baseObject, int sex) {
        if (baseObject.getStatus().equals("200")) {
            tvUserinfoSex.setText(Constant.SEX_DATA[sex]);
            CacheUtil.putString(this, Constant.USER_INFO, "gender", Constant.SEX_DATA[sex]);
        }
        LogUtil.e(baseObject.toString());
    }

    private void setDecorateRequest(int decorateType) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("decorate_type", decorateType + 1);
        param.put("token", AppUtil.getDateToken());
        param.put("uid", uid);
        getUserInfoDataPresenter.setDecorate(param, decorateType);
    }

    @Override
    public void setDecorate(BaseObject baseObject, int decorateType) {
        if (baseObject.getStatus().equals("200")) {
            tvUserinfoDecorateType.setText(Constant.DECORATETYPE_DATA[decorateType]);
            CacheUtil.putString(this, Constant.USER_INFO, "decorate_type", Constant.DECORATETYPE_DATA[decorateType]);
        }
        LogUtil.e(baseObject.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constant.CHANGE_USERNAME_REQUESTCODE:
                if (data != null) {
                    tvUserinfoName.setText(data.getStringExtra("newname"));
                }
                break;
            default:
                break;

        }
    }
}
