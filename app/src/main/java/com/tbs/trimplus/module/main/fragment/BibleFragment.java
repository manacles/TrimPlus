package com.tbs.trimplus.module.main.fragment;

import android.view.View;
import android.widget.TextView;

import com.tbs.trimplus.base.BaseFragment;

public class BibleFragment extends BaseFragment {

    @Override
    public View initView() {
        TextView textView  = new TextView(context);
        textView.setText("Bible");
        return textView;
    }
}
