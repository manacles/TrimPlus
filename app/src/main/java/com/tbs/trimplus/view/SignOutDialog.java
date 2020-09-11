package com.tbs.trimplus.view;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tbs.trimplus.R;
import com.tbs.trimplus.module.user.ui.UserInfoActivity;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.ToastUtil;

public abstract class SignOutDialog {
    private Context context;
    private Dialog dialog;

    public SignOutDialog(Context context, int themeResId) {
        this.context = context;
        this.dialog = new Dialog(context, themeResId);
    }

    public void show() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sign_out, null);
        dialog.setContentView(view);

        Button btnCancel = view.findViewById(R.id.btn_cancel);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSureClick();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public abstract void onSureClick();


}