package com.tbs.trimplus.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tbs.trimplus.R;

public abstract class CustomSelectDialog {
    private Context context;
    private Dialog dialog;

    private TextView tvTitle;
    private Button btnCancel;
    private Button btnConfirm;
    private View line;

    private String title;
    private int type;

    public static final int NORMAL = 1;
    public static final int NOCANCEL = 2;

    public CustomSelectDialog(Context context, String title, int type, int themeResId) {
        this.context = context;
        this.dialog = new Dialog(context, themeResId);
        this.title = title;
        this.type = type;
    }

    public void show() {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_sign_out, null);
        dialog.setContentView(view);

        tvTitle = view.findViewById(R.id.tv_title);
        btnCancel = view.findViewById(R.id.btn_cancel);
        btnConfirm = view.findViewById(R.id.btn_confirm);
        line = view.findViewById(R.id.line);

        setTitle(title);
        if (type == NOCANCEL) {
            removeCancel();
        }

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

    private void setTitle(String string) {
        tvTitle.setText(string);
    }

    private void removeCancel() {
        btnCancel.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
    }

}