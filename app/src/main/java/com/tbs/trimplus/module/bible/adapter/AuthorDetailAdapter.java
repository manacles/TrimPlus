package com.tbs.trimplus.module.bible.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tbs.trimplus.R;
import com.tbs.trimplus.module.bible.activity.BibleDetailActivity;
import com.tbs.trimplus.module.bible.bean.Author;
import com.tbs.trimplus.utils.GlideUtil;

import java.util.List;

public class AuthorDetailAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Author.ArticleBean> data;

    private View footView;
    private LinearLayout footLayout;

    private static final int ARTICLE = 1;
    private static final int FOOTER_VIEW = 2;

    public AuthorDetailAdapter(Context context, List<Author.ArticleBean> article) {
        this.context = context;
        this.data = article;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ARTICLE) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_author_article, parent, false);
            return new ArticleViewHolder(view);
        }
        if (viewType == FOOTER_VIEW) {
            footView = LayoutInflater.from(context).inflate(R.layout.item_foot, parent, false);
            footLayout = footView.findViewById(R.id.layout_foot);
            return new FootViewHolder(footView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position < data.size()) {
            Author.ArticleBean articleBean = data.get(position);
            if (holder instanceof ArticleViewHolder) {
                ArticleViewHolder articleViewHolder = (ArticleViewHolder) holder;
                articleViewHolder.tv_time.setText(articleBean.getTime());
                articleViewHolder.tv_time_text.setText(articleBean.getTime2());
                articleViewHolder.tv_title.setText(articleBean.getTitle());
                articleViewHolder.tv_type.setText(articleBean.getType_name());
                articleViewHolder.tv_see_num.setText(articleBean.getView_count() + "人看过");
                GlideUtil.glideLoader(context, articleBean.getImage_url(), 0, 0, articleViewHolder.iv_image);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, BibleDetailActivity.class);
                        intent.putExtra("id", articleBean.getAid());
                        intent.putExtra("author_id", articleBean.getUid());
                        context.startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : (data.size() + 1);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < data.size()) {
            return ARTICLE;
        } else {
            return FOOTER_VIEW;
        }
    }

    public void hideFootView() {
        if (footView != null) {
            footView.setVisibility(View.GONE);
        }

        if (footLayout != null) {
            footLayout.setVisibility(View.GONE);
        }
    }

    public void showFootView() {
        if (footView != null) {
            footView.setVisibility(View.VISIBLE);
        }
        if (footLayout != null) {
            footLayout.setVisibility(View.VISIBLE);
        }
    }


    class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView tv_time;
        TextView tv_time_text;
        TextView tv_title;
        TextView tv_type;
        ImageView iv_image;
        TextView tv_see_num;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_time_text = itemView.findViewById(R.id.tv_time_text);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_type = itemView.findViewById(R.id.tv_type);
            iv_image = itemView.findViewById(R.id.iv_image);
            tv_see_num = itemView.findViewById(R.id.tv_see_num);
        }
    }

    class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View view) {
            super(view);
        }
    }
}
