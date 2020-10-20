package com.tbs.trimplus.module.main.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseFragment;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.config.AppConfig;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.main.activity.MainActivity;
import com.tbs.trimplus.module.main.adapter.HomeAuthorAdapter;
import com.tbs.trimplus.module.main.adapter.HomeHeadlinesAdapter;
import com.tbs.trimplus.module.main.bean.Home;
import com.tbs.trimplus.module.main.presenter.impl.GetIndexDataPresenter;
import com.tbs.trimplus.module.main.view.IgetIndexDataView;
import com.tbs.trimplus.module.welcome.activity.WebViewActivity;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseFragment implements IgetIndexDataView {

    @BindView(R.id.banner_home)
    Banner bannerHome;
    @BindView(R.id.iv_home_freedesign)
    ImageView ivHomeFreedesign;
    @BindView(R.id.iv_home_freeprice)
    ImageView ivHomeFreeprice;
    @BindView(R.id.iv_home_calculator)
    ImageView ivHomeCalculator;
    @BindView(R.id.tv_home_more_headlines)
    TextView tvHomeMoreHeadlines;
    @BindView(R.id.recyclerview_home_headlines)
    RecyclerView recyclerviewHomeHeadlines;
    @BindView(R.id.recyclerview_home_author)
    RecyclerView recyclerviewHomeAuthor;


    private GetIndexDataPresenter getIndexDataPresenter;

    private List<Home.CarouselBean> carouselBeanArrayList = new ArrayList<>();
    private List<Home.ArticleBean> articleBeanArrayList = new ArrayList<>();
    private List<Home.AuthorBean> authorBeanArrayList = new ArrayList<>();

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.fragment_home, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        getIndexDataPresenter = new GetIndexDataPresenter(new Model(), this);
        getIndexDataRequest();
    }

    @OnClick({R.id.iv_home_freedesign, R.id.iv_home_freeprice, R.id.iv_home_calculator, R.id.tv_home_more_headlines})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home_freedesign:
                Intent intent;
                intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", AppConfig.QUOTE);
                startActivity(intent);
                break;
            case R.id.iv_home_freeprice:
                Intent intent2;
                intent2 = new Intent(context, WebViewActivity.class);
                intent2.putExtra("mLoadingUrl", AppConfig.FREE_PRICE_PAGE);
                startActivity(intent2);
                break;
            case R.id.iv_home_calculator:
                break;
            case R.id.tv_home_more_headlines:
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getRgMain().check(R.id.rb_bible);
                break;
        }
    }

    private void getIndexDataRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("is_default", "4");
        params.put("subchannel", "android");
        params.put("chcode", "");
        getIndexDataPresenter.getIndexData(params);
    }

    @Override
    public void getIndexData(BaseObject<Home> homeBaseObject) {
        if (homeBaseObject.getStatus().equals("200")) {
            LogUtil.e(homeBaseObject.toString());

            carouselBeanArrayList = homeBaseObject.getData().getCarousel();
            articleBeanArrayList = homeBaseObject.getData().getArticle();
            authorBeanArrayList = homeBaseObject.getData().getAuthor();

            showBanner();
            showArticle();
            showAuthor();

        } else {
            ToastUtil.sToast(context, homeBaseObject.getMsg());
        }
    }

    private void showAuthor() {
        LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recyclerviewHomeAuthor.setLayoutManager(manager);
        recyclerviewHomeAuthor.setAdapter(new HomeAuthorAdapter(context, authorBeanArrayList));
    }

    private void showArticle() {
        LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        recyclerviewHomeHeadlines.setLayoutManager(manager);
        recyclerviewHomeHeadlines.setAdapter(new HomeHeadlinesAdapter(context, articleBeanArrayList));
    }

    private void showBanner() {
        bannerHome.setAdapter(new BannerImageAdapter<Home.CarouselBean>(carouselBeanArrayList) {

            @Override
            public void onBindView(BannerImageHolder holder, Home.CarouselBean data, int position, int size) {
                holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                //图片加载自己实现
                Glide.with(context)
                        .load(data.getImg_url())
                        //.apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }
        })
                .setLoopTime(3000)
                .addBannerLifecycleObserver(null)//添加生命周期观察者
                .setIndicator(new CircleIndicator(context));

        //设置点击事件
        bannerHome.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                Intent intent;
                intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("mLoadingUrl", carouselBeanArrayList.get(position).getContent_url());
                startActivity(intent);
            }
        });
    }


}
