package com.tbs.trimplus.module.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.user.presenter.impl.ChangeUserInfoPresenter;
import com.tbs.trimplus.module.user.view.IchangeUserInfoView;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.NetUtil;
import com.tbs.trimplus.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChangeUserNameActivity extends BaseActivity implements IchangeUserInfoView {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_new_username)
    EditText etNewUsername;
    @BindView(R.id.iv_cancel)
    ImageView ivCancel;
    @BindView(R.id.ll_parent)
    LinearLayout llParent;

    private String oldUsername;
    private ChangeUserInfoPresenter changeUserInfoPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user_name);
        ButterKnife.bind(this);

        initToolbar(toolbar);
        toolbarTitle.setText("用户昵称");
        toolbarRight.setText("保存");

        changeUserInfoPresenter = new ChangeUserInfoPresenter(new Model(), this);

        initData();

        initListener();
    }

    private void initData() {
        Intent intent = getIntent();
        oldUsername = intent.getStringExtra("username");
        oldUsername = (oldUsername != null && oldUsername.length() > 8) ? oldUsername.substring(0, 8) : oldUsername;
        etNewUsername.setText(oldUsername);
        etNewUsername.setSelection(oldUsername.length());
    }


    private void initListener() {

        toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetUtil.isNetAvailable(ChangeUserNameActivity.this)) {
                    if ("".equals(etNewUsername.getText().toString().trim())) {
                        ToastUtil.sToast(ChangeUserNameActivity.this, "用户名不能为空");
                        return;
                    } else if (!oldUsername.equals(etNewUsername.getText().toString().trim())) {
                        changeUserInfoRequest();
                    } else {
                        ToastUtil.sToast(ChangeUserNameActivity.this, "您没做修改");
                        return;
                    }
                } else {
                    ToastUtil.sToast(ChangeUserNameActivity.this, "网络断开了~");
                    return;
                }
            }
        });

        etNewUsername.addTextChangedListener(new TextWatcher() {
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
                } else {
                    ivCancel.setVisibility(View.VISIBLE);
                }
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etNewUsername.setText("");
            }
        });
    }

    /*
     * 数字代表的字段类型(1.昵称 2.性别 3.城市 4.小区名 id 81 5:头像)
     * */
    private void changeUserInfoRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", CacheUtil.getString(this, Constant.USER_INFO, "token"));
        params.put("field", "1"); // 1是修改昵称
        params.put("new", etNewUsername.getText().toString().trim());
        changeUserInfoPresenter.changeUserInfo(params);
    }

    @Override
    public void changeUserInfo(User user) {
        LogUtil.e(user.toString());
        if (user.getError_code().equals("0")) {

            Intent intent = new Intent();
            intent.putExtra("newname", etNewUsername.getText().toString().trim());
            setResult(Constant.CHANGE_USERNAME_RESULTCODE, intent);

            CacheUtil.putString(this, Constant.USER_INFO, "name", etNewUsername.getText().toString().trim());
            finish();
        } else if (user.getError_code().equals("511")) {
            ToastUtil.sToast(this, "装修公司不能修改昵称");
        } else {
            ToastUtil.sToast(this, user.getMsg());
        }

    }
}
