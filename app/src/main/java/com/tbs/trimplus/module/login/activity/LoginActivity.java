package com.tbs.trimplus.module.login.activity;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.common.fastjson.NetData;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.login.bean.User;
import com.tbs.trimplus.module.login.presenter.impl.LoginByPasswordPresenter;
import com.tbs.trimplus.module.login.presenter.impl.LoginByVerifyCodePresenter;
import com.tbs.trimplus.module.login.view.IloginByPasswordView;
import com.tbs.trimplus.module.login.view.IloginByVerifyCodeView;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.MD5Util;
import com.tbs.trimplus.utils.NetUtil;
import com.tbs.trimplus.utils.ToastUtil;
import com.tbs.trimplus.view.CustomWaitDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements IloginByPasswordView, IloginByVerifyCodeView {

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
    private LoginByVerifyCodePresenter loginByVerifyCodePresenter;

    private int countDown;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            LogUtil.e(countDown + "");
            countDown--;
            tvVerifyCode.setText(countDown + "秒");

            if (countDown < 1) {
                tvVerifyCode.setText("获取验证码");
                setVerifyCodeTextView(true);
                handler.removeCallbacks(this);
                countDown = 59;
            } else {
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initViews();

        defaultLoginWay = 1;
        loginByPasswordPresenter = new LoginByPasswordPresenter(new Model(), this);
        loginByVerifyCodePresenter = new LoginByVerifyCodePresenter(new Model(), this);

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
                String tempPhone = etLoginPhone.getText().toString().trim().replace("-", "");
                if ("".equals(tempPhone)) {
                    ToastUtil.sToast(this, "电话号码不能为空");
                    return;
                }

                if (AppUtil.judgePhone(this, tempPhone)) {
                    //设置60s后可点击
                    countDown = 59;
                    tvVerifyCode.setText(countDown + "秒");
                    setVerifyCodeTextView(false);

                    handler.postDelayed(runnable, 1000);//每秒执行一次runnable.

                    String json = NetData.smsCode(tempPhone);
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.getInt("error_code") == 0) {
                            ToastUtil.sToast(this, "发送成功");

                        } else {
                            ToastUtil.sToast(this, jsonObject.getString("msg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.btn_login_bymessage:
                if ("".equals(etLoginPhone.getText().toString().trim())) {
                    ToastUtil.sToast(this, "电话号码不能为空");
                    return;
                } else if ("".equals(etVerifyCode.getText().toString().trim())) {
                    ToastUtil.sToast(this, "短信验证码不能为空");
                    return;
                } else if (etVerifyCode.getText().toString().trim().length() != 4) {
                    ToastUtil.sToast(this, "短信验证码不正确");
                    return;
                } else {
                    if (NetUtil.isNetAvailable(this)) {
                        showLoadingView();
                        loginByVerifyCodeRequest();

                    } else {
                        hideLoadingView();

                        ToastUtil.sToast(this, "请检查网络是否可用");
                        return;
                    }
                }

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
                    showLoadingView();

                    loginByPasswordRequset(name, psd);
                } else {
                    hideLoadingView();
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

    //设置获取验证码按钮是否可点击
    private void setVerifyCodeTextView(boolean clickable) {
        if (clickable) {
            tvVerifyCode.setBackground(getDrawable(R.drawable.shape_login_text_bg));
        } else {
            tvVerifyCode.setBackground(getDrawable(R.drawable.shape_login_text_bg_unclickable));
        }
        tvVerifyCode.setClickable(clickable);
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
        hideLoadingView();

        if (user.getError_code().equals("0")) {
            ToastUtil.sToast(this, "登陆成功");
            AppUtil.saveUserInfo(this, user);
            finish();
        } else {
            ToastUtil.sToast(this, user.getMsg());
        }
    }

    private void loginByVerifyCodeRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("system_type", "1"); // 1是安卓， 2是ios
        params.put("platform_type", "2"); // 1是土拨鼠， 2是装好家
        params.put("chcode", "");
        params.put("mobile", etLoginPhone.getText().toString().trim().replace("-", ""));
        params.put("msg_code", etVerifyCode.getText().toString().trim());
        loginByVerifyCodePresenter.loginByVerifyCode(params);
    }

    @Override
    public void loginByVerifyCode(User user) {
        hideLoadingView();

        if (user.getError_code().equals("0")) {
            ToastUtil.sToast(this, "登陆成功");
            AppUtil.saveUserInfo(this, user);
            finish();
        } else {
            ToastUtil.sToast(this, user.getMsg());
        }
    }

    private CustomWaitDialog waitDialog;

    private void showLoadingView() {
        waitDialog = new CustomWaitDialog(this);
        waitDialog.show();
    }

    private void hideLoadingView() {
        if (waitDialog != null) {
            waitDialog.dismiss();
        }
    }
}
