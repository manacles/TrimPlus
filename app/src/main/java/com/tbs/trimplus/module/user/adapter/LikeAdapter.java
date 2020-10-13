package com.tbs.trimplus.module.user.adapter;

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
import com.tbs.trimplus.module.user.bean.Like;
import com.tbs.trimplus.utils.GlideUtil;

import java.util.ArrayList;

public class LikeAdapter extends RecyclerView.Adapter {

    private Context context;
    private ArrayList<Like> data;

    private boolean isDel;
    private boolean isSelectedAll;

    public LikeAdapter(Context context, ArrayList<Like> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_like_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Like Like = data.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        Glide.with(context).load(Like.getImage_url()).into(viewHolder.pic);
        GlideUtil.glideLoader(context, Like.getIcon(), 0, 0, viewHolder.icon, GlideUtil.CIRCLE_IMAGE);
        viewHolder.title.setText(Like.getTitle());
        viewHolder.count.setText(Like.getTup_count());
        viewHolder.author.setText(Like.getNick());

        viewHolder.itemView.setTag(viewHolder);

        if (isDel) {
            viewHolder.check.setVisibility(View.VISIBLE);
            if (isSelectedAll) {
                viewHolder.check.setImageResource(R.drawable.check);
                Like.setChecked(true);
            } else {
                viewHolder.check.setImageResource(R.drawable.no_check);
                Like.setChecked(false);
            }
        } else {
            viewHolder.check.setVisibility(View.GONE);
        }

        viewHolder.setListener(Like);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pic;
        ImageView icon;
        TextView title;
        TextView count;
        TextView author;
        public ImageView check;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.iv_author_icon);
            pic = itemView.findViewById(R.id.iv_pic);
            title = itemView.findViewById(R.id.tv_title);
            count = itemView.findViewById(R.id.tv_count);
            author = itemView.findViewById(R.id.tv_author_name);
            check = itemView.findViewById(R.id.iv_check);
        }

        public void setListener(Like Like) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(view, Like);
                    }
                }
            });
        }

        public void goStartActivity(String articleId, String authorId) {
            Intent intent = new Intent(context, BibleDetailActivity.class);
            intent.putExtra("id", articleId);
            intent.putExtra("author_id", authorId);
            context.startActivity(intent);
        }

    }

    public void setDel(boolean del) {
        isDel = del;
    }

    public void setSelectedAll(boolean selectedAll) {
        isSelectedAll = selectedAll;
    }

    //recyclerview通过接口回调的形式给item添加点击事件
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onClick(View view, Like Like);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
