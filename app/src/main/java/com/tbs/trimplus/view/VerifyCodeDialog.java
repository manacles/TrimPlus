package com.tbs.trimplus.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.tbs.trimplus.R;
import com.tbs.trimplus.common.fastjson.NetData;
import com.tbs.trimplus.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyCodeDialog extends Dialog {
    @BindView(R.id.iv_pic_code)
    ImageView ivPicCode;
    @BindView(R.id.tv_another)
    TextView tvAnother;
    @BindView(R.id.et_pic_code)
    EditText etPicCode;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;

    private Context context;
    private String phone;

    /**
     *
     * 图形验证码弹窗，
     * 因图形验证接口始终错误，跳过图形验证弹窗，直接发送短信验证码
     * 该弹窗未使用
     */
    public VerifyCodeDialog(@NonNull Context context, int themeResId, String tempPhone) {
        super(context, themeResId);
        this.context = context;
        this.phone = tempPhone;

        View view = LayoutInflater.from(context).inflate(R.layout.verify_code_dialog, null);
        this.setContentView(view);
        ButterKnife.bind(this, view);

        Bitmap bitmap = NetData.getPicCode();
        if (bitmap != null) {
            ivPicCode.setImageBitmap(bitmap);
        }

    }


    @OnClick({R.id.tv_another, R.id.btn_cancel, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_another:
                Bitmap bitmap = NetData.getPicCode();
                if (bitmap != null) {
                    ivPicCode.setImageBitmap(bitmap);
                }
                break;
            case R.id.btn_cancel:
                dismiss();
                break;
            case R.id.btn_confirm:
                String etCode = etPicCode.getText().toString().trim();
                if (etCode.length() == 0) {
                    ToastUtil.sToast(context, "验证码为空！");
                    return;
                }

                break;
        }
    }
}
