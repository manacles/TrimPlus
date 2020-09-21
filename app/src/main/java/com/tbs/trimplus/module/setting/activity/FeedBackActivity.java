package com.tbs.trimplus.module.setting.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.common.bean.ResultList;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.setting.presenter.impl.FeedbackPresenter;
import com.tbs.trimplus.module.setting.view.IfeedbackView;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.NetUtil;
import com.tbs.trimplus.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity implements IfeedbackView {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_feedback_content)
    EditText etFeedbackContent;
    @BindView(R.id.et_feedback_contact)
    EditText etFeedbackContact;

    private FeedbackPresenter feedbackPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);

        toolbarTitle.setText("意见反馈");
        toolbarRight.setText("发送");
        initToolbar(toolbar);

        feedbackPresenter = new FeedbackPresenter(new Model(), this);
    }

    @OnClick(R.id.toolbar_right)
    public void onViewClicked() {
        if ("".equals(etFeedbackContent.getText().toString().trim())) {
            ToastUtil.sToast(this, "反馈不能为空");
            return;
        }
        if (NetUtil.isNetAvailable(this)) {
            feedbackRequest();
        } else {
            ToastUtil.sToast(this, "请检查网络~");
        }
    }

    private void feedbackRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("mode", android.os.Build.MODEL); // 手机型号
        params.put("mode_version", AppUtil.getAppVersion(this));
        params.put("mobile", etFeedbackContact.getText().toString().trim());
        params.put("content", etFeedbackContent.getText().toString().trim());
        params.put("token", CacheUtil.getString(this, Constant.USER_INFO, "token"));
        feedbackPresenter.feedback(params);
    }

    @Override
    public void feedback(ResultList resultList) {
        if (resultList.getError_code().equals("0")) {
            ToastUtil.sToast(this, resultList.getMsg());
            finish();
        } else {
            ToastUtil.sToast(this, resultList.getMsg());
        }
    }
}
