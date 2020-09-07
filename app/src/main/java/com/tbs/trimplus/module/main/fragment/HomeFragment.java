package com.tbs.trimplus.module.main.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseFragment;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.banner_home)
    Banner bannerHome;
    @BindView(R.id.btn_home_freedesign)
    Button btnHomeFreedesign;
    @BindView(R.id.btn_home_freeprice)
    Button btnHomeFreeprice;
    @BindView(R.id.btn_home_calculator)
    Button btnHomeCalculator;
    @BindView(R.id.tv_home_more_headlines)
    TextView tvHomeMoreHeadlines;
    @BindView(R.id.recyclerview_home_headlines)
    RecyclerView recyclerviewHomeHeadlines;
    @BindView(R.id.recyclerview_home_author)
    RecyclerView recyclerviewHomeAuthor;

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }
}
