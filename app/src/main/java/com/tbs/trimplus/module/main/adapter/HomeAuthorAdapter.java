package com.tbs.trimplus.module.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tbs.trimplus.R;
import com.tbs.trimplus.module.bible.activity.AuthorDetailActivity;
import com.tbs.trimplus.module.bible.adapter.AuthorDetailAdapter;
import com.tbs.trimplus.module.main.bean.Home;
import com.tbs.trimplus.utils.GlideUtil;

import java.util.List;

public class HomeAuthorAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Home.AuthorBean> data;

    public HomeAuthorAdapter(Context context, List<Home.AuthorBean> authorBeanArrayList) {
        this.context = context;
        this.data = authorBeanArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_author, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.tv_name.setText(data.get(position).getNick());
        viewHolder.tv_view_count.setText("" + data.get(position).getView_count());
        viewHolder.tv_article_count.setText("" + data.get(position).getArticle_count());

        GlideUtil.glideLoader(context, data.get(position).getIcon(), R.drawable.occupied2,
                R.drawable.occupied2, viewHolder.iv_icon, GlideUtil.CIRCLE_IMAGE);

        if (position == 0) {
            viewHolder.iv_rank.setBackgroundResource(R.drawable.gold);
        } else if (position == 1) {
            viewHolder.iv_rank.setBackgroundResource(R.drawable.silver);
        } else if (position == 2) {
            viewHolder.iv_rank.setBackgroundResource(R.drawable.brown);
        } else if (position == 3) {
            viewHolder.iv_rank.setBackgroundResource(R.drawable.img4);
        } else if (position == 4) {
            viewHolder.iv_rank.setBackgroundResource(R.drawable.img5);
        } else if (position == 5) {
            viewHolder.iv_rank.setBackgroundResource(R.drawable.img6);
        } else if (position == 6) {
            viewHolder.iv_rank.setBackgroundResource(R.drawable.img7);
        } else if (position == 7) {
            viewHolder.iv_rank.setBackgroundResource(R.drawable.img8);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AuthorDetailActivity.class);
                intent.putExtra("author_id", data.get(position).getUid());
                intent.putExtra("page_num", data.get(position).getArticle_count());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_icon;
        ImageView iv_rank;
        TextView tv_name;
        TextView tv_view_count;
        TextView tv_article_count;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            iv_rank = itemView.findViewById(R.id.iv_rank);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_view_count = itemView.findViewById(R.id.tv_view_count);
            tv_article_count = itemView.findViewById(R.id.tv_article_count);
        }
    }
}
