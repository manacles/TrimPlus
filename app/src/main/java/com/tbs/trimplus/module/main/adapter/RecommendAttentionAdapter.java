package com.tbs.trimplus.module.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tbs.trimplus.R;
import com.tbs.trimplus.module.main.bean.Mine;
import com.tbs.trimplus.utils.GlideUtil;

import java.util.List;

public class RecommendAttentionAdapter extends RecyclerView.Adapter<RecommendAttentionAdapter.ViewHolder> {

    private Context context;
    List<Mine.FollowBean> list;

    public RecommendAttentionAdapter(Context context, List<Mine.FollowBean> followBeanList) {
        this.context = context;
        this.list = followBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend_attention, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideUtil.glideLoader(context, list.get(position).getIcon(), R.drawable.icon_head_default,
                R.drawable.icon_head_default, holder.iv_recommend_icon, GlideUtil.CIRCLE_IMAGE);
        holder.tv_recommend_name.setText(list.get(position).getNick());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_recommend_icon;
        TextView tv_recommend_name;
        ImageView iv_recommend_add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_recommend_icon = itemView.findViewById(R.id.iv_recommend_icon);
            tv_recommend_name = itemView.findViewById(R.id.tv_recommend_name);
            iv_recommend_add = itemView.findViewById(R.id.iv_recommend_add);
        }
    }
}
