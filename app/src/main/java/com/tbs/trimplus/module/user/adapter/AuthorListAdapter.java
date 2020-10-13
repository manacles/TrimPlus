package com.tbs.trimplus.module.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tbs.trimplus.R;
import com.tbs.trimplus.common.bean.BaseObject;
import com.tbs.trimplus.module.apimodel.Model;
import com.tbs.trimplus.module.bible.activity.AuthorDetailActivity;
import com.tbs.trimplus.module.bible.bean.Author;
import com.tbs.trimplus.module.bible.presenter.impl.DoAuthorDetailPresenter;
import com.tbs.trimplus.module.bible.view.IdoAuthorDetilView;
import com.tbs.trimplus.module.user.bean.AuthorList;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.GlideUtil;
import com.tbs.trimplus.utils.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class AuthorListAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<AuthorList> data;

    public AuthorListAdapter(Context context, ArrayList<AuthorList> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_author_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AuthorList author = data.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        GlideUtil.glideLoader(context, author.getIcon(), 0, 0, viewHolder.icon, GlideUtil.CIRCLE_IMAGE);
        viewHolder.name.setText(author.getNick());
        viewHolder.article.setText(String.format("%s篇文章", author.getArticle_count()));
        viewHolder.attention.setTextColor(Color.GRAY);

        viewHolder.setListener(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements IdoAuthorDetilView {

        ImageView icon;
        TextView name;
        TextView article;
        TextView attention;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.iv_icon);
            name = itemView.findViewById(R.id.tv_author_name);
            article = itemView.findViewById(R.id.tv_article_count);
            attention = itemView.findViewById(R.id.tv_attention);
        }

        public void setListener(int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, AuthorDetailActivity.class);
                    intent.putExtra("author_id", data.get(position).getAid());
                    intent.putExtra("page_num", data.get(position).getArticle_count());
                    context.startActivity(intent);
                }
            });

            attention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    followAuthorRequest(data.get(position).getAid());
                }
            });
        }


        private DoAuthorDetailPresenter doAuthorDetailPresenter;

        private void followAuthorRequest(String authorId) {
            doAuthorDetailPresenter = new DoAuthorDetailPresenter(new Model(), this);
            String uid = CacheUtil.getString(context, Constant.USER_INFO, "uid");
            HashMap<String, Object> params = new HashMap<>();
            params.put("token", AppUtil.getDateToken());
            params.put("uid", uid);
            params.put("author_id", authorId);
            params.put("system_type", "1");
            doAuthorDetailPresenter.followAuthor(params);
        }

        @Override
        public void followAuthor(BaseObject baseObject) {
            String msg = baseObject.getMsg();
            if (msg.equals("关注成功")) {
                attention.setText("已关注");
                attention.setTextColor(Color.GRAY);
            } else {
                attention.setText("+ 关注");
                attention.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            }
        }

        @Override
        public void getAuthorDetail(BaseObject<Author> authorBaseObject) {

        }
    }

}
