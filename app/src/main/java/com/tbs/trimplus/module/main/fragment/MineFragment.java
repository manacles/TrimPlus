package com.tbs.trimplus.module.main.fragment;

import android.view.View;
import android.widget.TextView;

import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseFragment;

public class MineFragment extends BaseFragment {

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_home,null);
        TextView textView  = new TextView(context);
        textView.setText("Mine");
        return view;
    }

    @Override
    public void initData() {
        super.initData();
    }
}
