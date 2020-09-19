package com.tbs.trimplus.module.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tbs.trimplus.R;
import com.tbs.trimplus.module.history.activity.HistoryActivity;
import com.tbs.trimplus.module.login.activity.LoginActivity;
import com.tbs.trimplus.module.main.bean.Mine;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewMineAdapter extends RecyclerView.Adapter {

    private Context context;
    private LayoutInflater inflater;
    private boolean isLogin;
    private boolean isRecommondOpen = true;
    private List<Mine.AttentionBean> attentionBeanList = new ArrayList<>();
    private List<Mine.FollowBean> followBeanList = new ArrayList<>();

    private static final int OPTION = 0;
    private static final int MY_ATTENTION = 1;
    private static final int RECOMMEND_ATTENTION = 2;

    public RecyclerViewMineAdapter(Context context, Mine mine, boolean isLogin) {
        this.context = context;
        this.isLogin = isLogin;
        if (mine != null) {
            attentionBeanList = mine.getAttention();
            followBeanList = mine.getFollow();
        }

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MY_ATTENTION) {
            return new MyAttentionViewHolder(context, inflater.inflate(R.layout.mine_my_attention, parent, false));
        } else if (viewType == RECOMMEND_ATTENTION) {
            return new RecommendAttentionViewHolder(context, inflater.inflate(R.layout.mine_recommend_attention, parent, false));
        } else if (viewType == OPTION) {
            return new OptionViewHolder(inflater.inflate(R.layout.item_mine_option, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == OPTION) {
            OptionViewHolder optionViewHolder = (OptionViewHolder) holder;
            //页面设置
            if (position == 2) {
                optionViewHolder.divideTop.setVisibility(View.VISIBLE);
                optionViewHolder.divideBottom.setVisibility(View.VISIBLE);
            }
            if (position == 3 || position == 4 || position == 5) {
                optionViewHolder.divideLine.setVisibility(View.VISIBLE);
            }

            optionViewHolder.setData(position);

        } else if (getItemViewType(position) == MY_ATTENTION) {
            MyAttentionViewHolder myAttentionViewHolder = (MyAttentionViewHolder) holder;
            if (isLogin) {
                //页面设置
                myAttentionViewHolder.recyclerView.setVisibility(View.VISIBLE);
                myAttentionViewHolder.divideLine.setVisibility(View.VISIBLE);
            } else {
                myAttentionViewHolder.recyclerView.setVisibility(View.GONE);
                myAttentionViewHolder.divideLine.setVisibility(View.GONE);
            }
            myAttentionViewHolder.setData();

        } else if (getItemViewType(position) == RECOMMEND_ATTENTION) {
            RecommendAttentionViewHolder recommendAttentionViewHolder = (RecommendAttentionViewHolder) holder;
            //页面设置
            if (isLogin) {
                recommendAttentionViewHolder.recyclerView.setVisibility(View.VISIBLE);
            } else {
                recommendAttentionViewHolder.recyclerView.setVisibility(View.GONE);
            }
            recommendAttentionViewHolder.setData();
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    @Override
    public int getItemViewType(int position) {
        int type;
        if (position == 0) {
            type = MY_ATTENTION;
        } else if (position == 1) {
            type = RECOMMEND_ATTENTION;
        } else {
            type = OPTION;
        }
        return type;
    }

    private class OptionViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout rlMineOption;
        TextView tvMineOptionTitle;
        ImageView ivMineOptionRight;
        View divideTop;
        View divideLine;
        View divideBottom;

        public OptionViewHolder(View itemView) {
            super(itemView);
            rlMineOption = itemView.findViewById(R.id.rl_mine_option);
            tvMineOptionTitle = itemView.findViewById(R.id.tv_mine_option_title);
            ivMineOptionRight = itemView.findViewById(R.id.iv_mine_option_right);
            divideTop = itemView.findViewById(R.id.divide_top);
            divideLine = itemView.findViewById(R.id.divide_line);
            divideBottom = itemView.findViewById(R.id.divide_bottom);
        }

        public void setData(int position) {
            tvMineOptionTitle.setText(Constant.MINE_OPTION[position]);

            rlMineOption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position) {
                        case 2:
                            if (AppUtil.isLogin(context)) {
                                //已经登录状态
                                context.startActivity(new Intent(context, HistoryActivity.class));
                            } else {
                                //未登录
                                context.startActivity(new Intent(context, LoginActivity.class));
                            }
                            break;
                        case 3:
                            if (AppUtil.isLogin(context)) {
                                //已经登录状态
                                ToastUtil.sToast(context, "消息中心");
                            } else {
                                //未登录
                                context.startActivity(new Intent(context, LoginActivity.class));
                            }
                            break;
                        case 4:
                            if (AppUtil.isLogin(context)) {
                                //已经登录状态
                                ToastUtil.sToast(context, "意见反馈");
                            } else {
                                //未登录
                                context.startActivity(new Intent(context, LoginActivity.class));
                            }
                            break;
                        case 5:
                            ToastUtil.sToast(context, "分享好友");
                            break;
                        case 6:
                            ToastUtil.sToast(context, "系统设置");
                            break;
                        default:
                            break;

                    }
                }
            });
        }
    }

    private class MyAttentionViewHolder extends RecyclerView.ViewHolder {
        MyAttentionAdapter myAttentionAdapter;
        RelativeLayout rlMyAttention;
        RecyclerView recyclerView;
        View divideLine;

        public MyAttentionViewHolder(Context context, View itemView) {
            super(itemView);
            rlMyAttention = itemView.findViewById(R.id.rl_my_attention);
            divideLine = itemView.findViewById(R.id.divide_line);
            recyclerView = itemView.findViewById(R.id.recyclerview_my_attention);

            myAttentionAdapter = new MyAttentionAdapter(context, attentionBeanList);
            recyclerView.setAdapter(myAttentionAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        }

        public void setData() {
            rlMyAttention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AppUtil.isLogin(context)) {
                        //已经登录状态
                        ToastUtil.sToast(context, "我的关注");
                    } else {
                        //未登录
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                }
            });

        }
    }

    private class RecommendAttentionViewHolder extends RecyclerView.ViewHolder {
        RecommendAttentionAdapter recommendAttentionAdapter;
        RecyclerView recyclerView;
        RelativeLayout rlRecommendAttention;
        ImageView ivRecommendAttentionRight;

        public RecommendAttentionViewHolder(Context context, View itemView) {
            super(itemView);
            rlRecommendAttention = itemView.findViewById(R.id.rl_recommend_attention);
            ivRecommendAttentionRight = itemView.findViewById(R.id.iv_recommend_attention_right);
            recyclerView = itemView.findViewById(R.id.recyclerview_recommend_attention);


            recommendAttentionAdapter = new RecommendAttentionAdapter(context, followBeanList);
            recyclerView.setAdapter(recommendAttentionAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));

        }

        public void setData() {
            rlRecommendAttention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (AppUtil.isLogin(context)) {
                        //已经登录状态
                        if (isRecommondOpen) {
                            recyclerView.setVisibility(View.GONE);
                            ivRecommendAttentionRight.setImageResource(R.drawable.nmf_back);
                            isRecommondOpen = false;
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            ivRecommendAttentionRight.setImageResource(R.drawable.nmf_back_down);
                            isRecommondOpen = true;
                        }
                    } else {
                        //未登录
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                }
            });
        }
    }

    public void refreshData(Mine mine) {
        this.attentionBeanList = mine.getAttention();
        this.followBeanList = mine.getFollow();

        this.notifyItemChanged(0);
        this.notifyItemChanged(1);
    }

}
