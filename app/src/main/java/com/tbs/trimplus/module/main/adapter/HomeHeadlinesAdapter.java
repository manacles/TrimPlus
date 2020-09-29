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

import com.bumptech.glide.Glide;
import com.tbs.trimplus.R;
import com.tbs.trimplus.module.bible.activity.BibleDetailActivity;
import com.tbs.trimplus.module.main.bean.Home;

import java.util.List;

public class HomeHeadlinesAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Home.ArticleBean> data;

    public HomeHeadlinesAdapter(Context context, List<Home.ArticleBean> articleBeanArrayList) {
        this.context = context;
        this.data = articleBeanArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_home_article, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.textView.setText(data.get(position).getTitle());
        Glide.with(context).load(data.get(position).getImage_url()).placeholder(R.drawable.test).into(viewHolder.imageView);

        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goStartActivity(data.get(position).getAid(), data.get(position).getUid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_image);
            textView = itemView.findViewById(R.id.tv_title);
        }
    }

    private void goStartActivity(String articleId, String authorId) {
        Intent intent = new Intent(context, BibleDetailActivity.class);
        intent.putExtra("id", articleId);
        intent.putExtra("author_id", authorId);
        context.startActivity(intent);
    }
}
