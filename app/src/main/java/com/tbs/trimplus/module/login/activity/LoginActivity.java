package com.tbs.trimplus.module.login.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.tbs.trimplus.R;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.login.presenter.impl.LoginByPasswordPresenter;
import com.tbs.trimplus.module.login.view.IloginByPasswordView;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.MD5Util;
import com.tbs.trimplus.utils.NetUtil;
import com.tbs.trimplus.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements IloginByPasswordView {

    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.tv_verify_code)
    TextView tvVerifyCode;
    @BindView(R.id.et_verify_code)
    EditText etVerifyCode;
    @BindView(R.id.btn_login_bymessage)
    Button btnLoginBymessage;
    @BindView(R.id.ll_login_bymessage)
    LinearLayout llLoginBymessage;
    @BindView(R.id.et_login_username)
    EditText etLoginUsername;
    @BindView(R.id.et_login_password)
    EditText etLoginPassword;
    @BindView(R.id.btn_login_bypassword)
    Button btnLoginBypassword;
    @BindView(R.id.ll_login_bypassword)
    LinearLayout llLoginBypassword;
    @BindView(R.id.btn_login_bywechat)
    Button btnLoginBywechat;
    @BindView(R.id.btn_login_switch)
    Button btnLoginSwitch;

    private int defaultLoginWay;    //1.短信验证码登录；2.账号密码登录;

    private LoginByPasswordPresenter loginByPasswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initViews();

        defaultLoginWay = 1;
        loginByPasswordPresenter = new LoginByPasswordPresenter(new Model(), this);

    }

    private void initViews() {
        etLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) return;
                StringBuffer stringBuffer = new StringBuffer();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == '-') {
                        continue;
                    } else {
                        stringBuffer.append(s.charAt(i));
                        if ((stringBuffer.length() == 4 || stringBuffer.length() == 9) && stringBuffer.charAt(stringBuffer.length() - 1) != '-') {
                            stringBuffer.insert(stringBuffer.length() - 1, '-');
                        }
                    }
                }
                if (!stringBuffer.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (stringBuffer.charAt(start) == '-') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    etLoginPhone.setText(stringBuffer.toString());
                    etLoginPhone.setSelection(index);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick({R.id.iv_login_close, R.id.tv_verify_code, R.id.btn_login_bymessage,
            R.id.btn_login_bypassword, R.id.btn_login_bywechat, R.id.btn_login_switch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_login_close:
                finish();
                break;
            case R.id.tv_verify_code:
                break;
            case R.id.btn_login_bymessage:
                break;
            case R.id.btn_login_bypassword:
                // 账号密码登录
                String name = etLoginUsername.getText().toString().trim();
                String psd = etLoginPassword.getText().toString().trim();
                if ("".equals(name)) {
                    ToastUtil.sToast(this, "账号不能为空");
                    return;
                } else if ("".equals(psd)) {
                    ToastUtil.sToast(this, "密码不能为空");
                    return;
                }

                if (NetUtil.isNetAvailable(this)) {
                    loginByPasswordRequset(name, psd);
                } else {
                    ToastUtil.sToast(this, "网络不佳,请稍后再试~");
                }

                break;
            case R.id.btn_login_bywechat:
                break;
            case R.id.btn_login_switch:
                switchLoginWay(defaultLoginWay);
                break;
        }
    }

    private void switchLoginWay(int loginWay) {
        if (loginWay == 1) {
            llLoginBypassword.setVisibility(View.VISIBLE);
            llLoginBymessage.setVisibility(View.GONE);
            defaultLoginWay = 2;
            btnLoginSwitch.setText("短信登录");
        } else if (loginWay == 2) {
            llLoginBypassword.setVisibility(View.GONE);
            llLoginBymessage.setVisibility(View.VISIBLE);
            defaultLoginWay = 1;
            btnLoginSwitch.setText("账号登录");
        }
    }

    @Override
    public void DialogStatus(int status, String error) {
        ToastUtil.sToast(this, error);
    }

    private void loginByPasswordRequset(String name, String psd) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("mobile", name);
        params.put("pass", MD5Util.md5(psd));
        loginByPasswordPresenter.loginByPassword(params);
    }

    @Override
    public void loginByPassword(User user) {
        if (user.getError_code().equals("0")) {
            ToastUtil.sToast(this, "登陆成功");
            AppUtil.saveUserInfo(this, user);
            finish();
        } else {
            ToastUtil.sToast(this, user.getMsg());
        }
    }
}
