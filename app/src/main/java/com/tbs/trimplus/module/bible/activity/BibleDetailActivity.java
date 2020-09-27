package com.tbs.trimplus.module.bible.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.github.chrisbanes.photoview.PhotoView;
import com.tbs.trimplus.R;
import com.tbs.trimplus.base.BaseActivity;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.bible.bean.BibleDetail;
import com.tbs.trimplus.module.bible.presenter.impl.DoBibleDetailPresenter;
import com.tbs.trimplus.module.bible.view.IdoBibleDetailView;
import com.tbs.trimplus.module.login.activity.LoginActivity;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.DensityUtil;
import com.tbs.trimplus.utils.GlideUtil;
import com.tbs.trimplus.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BibleDetailActivity extends BaseActivity implements IdoBibleDetailView {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_right)
    TextView toolbarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_bible_icon)
    ImageView ivBibleIcon;
    @BindView(R.id.tv_bible_name)
    TextView tvBibleName;
    @BindView(R.id.tv_bible_title)
    TextView tvBibleTitle;
    @BindView(R.id.ll_bible_content)
    LinearLayout llBibleContent;
    @BindView(R.id.tv_need_decoration)
    TextView tvNeedDecoration;
    @BindView(R.id.iv_favorites)
    ImageView ivFavorites;
    @BindView(R.id.iv_good)
    ImageView ivGood;
    @BindView(R.id.tv_good)
    TextView tvGood;
    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;
    @BindView(R.id.tv_bible_time)
    TextView tvBibleTime;
    @BindView(R.id.tv_attention)
    TextView tvAttention;

    private Intent intent;
    private String id;          //文章id
    private String authorId;    //作者id
    private String uid;         //用户id

    private DoBibleDetailPresenter doBibleDetailPresenter;
    private BibleDetail bibleDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bible_detail);
        ButterKnife.bind(this);

        intent = getIntent();
        doBibleDetailPresenter = new DoBibleDetailPresenter(new Model(), this);

        //初始化页面
        initView();
        //记录页面浏览数
        recordViewCountRequest();
        //获取bible详情数据
        getArticleDetailRequest();

    }

    private void initView() {
        toolbarTitle.setMaxEms(10);
        toolbarTitle.setMaxLines(1);
        toolbarTitle.setEllipsize(TextUtils.TruncateAt.END);
        toolbarTitle.setTextSize(18);

        toolbarRight.setBackground(getDrawable(R.drawable.share));
        initToolbar(toolbar);
    }

    private void recordViewCountRequest() {
        uid = CacheUtil.getString(this, Constant.USER_INFO, "uid");
        id = intent.getStringExtra("id");
        authorId = intent.getStringExtra("author_id");

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("uid", uid);
        params.put("aid", id);
        doBibleDetailPresenter.recordViewCount(params);
    }

    private void getArticleDetailRequest() {
        loadingLayout.setVisibility(View.VISIBLE);

        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("id", id);                   //文章id 上个界面传来的
        params.put("author_id", authorId);      //作者的ID 上个界面传来的
        if (AppUtil.isLogin(this)) {
            params.put("uid", uid);             //用户的ID
        }
        doBibleDetailPresenter.getArticleDetail(params);
    }

    @Override
    public void getArticleDetail(BaseObject<BibleDetail> bibleDetailBaseObject) {
        loadingLayout.setVisibility(View.GONE);

        if (bibleDetailBaseObject.getStatus().equals("200")) {
            bibleDetail = bibleDetailBaseObject.getData();

            toolbarTitle.setText(bibleDetail.getTitle());
            GlideUtil.glideLoader(this, bibleDetail.getAuthor_img(), 0, 0, ivBibleIcon, GlideUtil.CIRCLE_IMAGE);
            tvBibleName.setText(bibleDetail.getAuthor_name());
            tvBibleTime.setText(bibleDetail.getTime());
            if (!bibleDetail.getIs_follow().equals("0")) {
                tvAttention.setText("已关注");
                tvAttention.setTextColor(Color.GRAY);
            }
            tvBibleTitle.setText(bibleDetail.getTitle());

            if (!bibleDetail.getTup_count().equals("0")) {
                tvGood.setText(bibleDetail.getTup_count());
            }

            if (bibleDetail.getIs_collect().equals("0")) {
                ivFavorites.setImageResource(R.drawable.favorites);
            } else {
                ivFavorites.setImageResource(R.drawable.favorites_fill);
            }

            if (bibleDetail.getIs_tup().equals("0")) {
                ivGood.setImageResource(R.drawable.good);
            } else {
                ivGood.setImageResource(R.drawable.good_fill);
                tvGood.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
            //设置bible内容
            setBibleContent(bibleDetail.getContent());

        } else {
            ToastUtil.sToast(this, "网络请求失败！");
        }

    }

    private void setBibleContent(List<String> content) {
        ArrayList<String> imageUrlList = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            if (content.get(i).startsWith("http")) {
                imageUrlList.add(content.get(i));
                ImageView imageView = new ImageView(this);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        DensityUtil.dip2px(this, 178));
                lp.setMargins(DensityUtil.px2dip(this, 40), DensityUtil.px2dip(this, 20), DensityUtil.px2dip(this, 40), DensityUtil.px2dip(this, 20));
                imageView.setLayoutParams(lp);
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                GlideUtil.glideLoader(this, content.get(i), R.drawable.occupied1, R.drawable.occupied1, imageView);
                llBibleContent.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
                llBibleContent.addView(imageView);
                // TODO: 2020/9/27  图片查看
                //seePicture(imageView, imageUrlList);
            } else {
                TextView textView = new TextView(this);
                LinearLayout.LayoutParams textlp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                textlp.setMargins(DensityUtil.px2dip(this, 40), DensityUtil.px2dip(this, 20), DensityUtil.px2dip(this, 40), DensityUtil.px2dip(this, 20));
                textView.setLayoutParams(textlp);
                textView.setText(content.get(i));
                textView.setTextSize(16);
                llBibleContent.addView(textView);
            }
        }
    }

    @OnClick({R.id.toolbar_right, R.id.iv_bible_icon, R.id.tv_need_decoration,
            R.id.tv_attention, R.id.iv_favorites, R.id.iv_good})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.toolbar_right:
                // TODO: 2020/9/27 文章分享
                break;
            case R.id.iv_bible_icon:
                // TODO: 2020/9/27 进入作者详情页
                break;
            case R.id.tv_need_decoration:
                // TODO: 2020/9/27 我要装修跳转页面
                break;
            case R.id.tv_attention:
                //关注作者
                if (AppUtil.isLogin(this)) {
                    //请求关注接口
                    followAuthorRequest();
                } else {
                    //当前用户没有登录跳转登录
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.iv_favorites:
                //收藏
                if (AppUtil.isLogin(this)) {
                    collectArticleRequest();
                } else {
                    //当前用户没有登录跳转登录
                    startActivity(new Intent(this, LoginActivity.class));
                }
                break;
            case R.id.iv_good:
                likeArticleRequest();
                break;

        }
    }

    private void collectArticleRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("aid", bibleDetail.getAid());
        params.put("uid", uid);
        params.put("author_id", uid);
        params.put("system_type", "1");
        //拿到当前用户的收藏情况选择传入的收藏类型
        if (bibleDetail.getIs_collect().equals("0")) {
            //未收藏 请求收藏
            params.put("type", "1");
        } else {
            //已收藏 请求取消收藏
            params.put("type", "2");
        }
        doBibleDetailPresenter.collectArticle(params);
    }

    @Override
    public void collectArticle(BaseObject baseObject) {
        String msg = baseObject.getMsg();
        if (baseObject.getStatus().equals("200")) {

            if (msg.equals("收藏成功")) {
                ivFavorites.setImageResource(R.drawable.favorites_fill);
                Animation animation = AnimationUtils.loadAnimation(this, R.anim.good_animation);
                ivFavorites.startAnimation(animation);
                bibleDetail.setIs_collect("1");
            } else {
                ivFavorites.setImageResource(R.drawable.favorites);
                bibleDetail.setIs_collect("0");
            }
        }
        ToastUtil.sToast(this, msg);
    }

    private void likeArticleRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("aid", id);
        if (AppUtil.isLogin(this)) {
            params.put("uid", uid);
        }
        params.put("author_id", authorId);
        params.put("system_type", 1);
        doBibleDetailPresenter.likeArticle(params);
    }

    @Override
    public void likeArticle(BaseObject baseObject) {
        String msg = baseObject.getMsg();
        if (baseObject.getStatus().equals("200")) {
            ivGood.setImageResource(R.drawable.good_fill);
            tvGood.setText((Integer.parseInt(bibleDetail.getTup_count()) + 1) + "");

            if (msg.equals("点赞成功")) {
                Animation alpha = AnimationUtils.loadAnimation(this, R.anim.good_animation);
                ivGood.startAnimation(alpha);
            }
        }
        ToastUtil.sToast(this, msg);
    }

    private void followAuthorRequest() {
        HashMap<String, Object> params = new HashMap<>();
        params.put("token", AppUtil.getDateToken());
        params.put("uid", uid);
        params.put("author_id", authorId);
        params.put("system_type", "1");
        doBibleDetailPresenter.followAuthor(params);
    }

    @Override
    public void followAuthor(BaseObject baseObject) {
        String msg = baseObject.getMsg();
        if (msg.equals("关注成功")) {
            tvAttention.setText("已关注");
            tvAttention.setTextColor(Color.GRAY);
        } else {
            tvAttention.setText("+ 关注");
            tvAttention.setTextColor(getResources().getColor(R.color.colorPrimary));
        }
        ToastUtil.sToast(this, msg);
    }
}
