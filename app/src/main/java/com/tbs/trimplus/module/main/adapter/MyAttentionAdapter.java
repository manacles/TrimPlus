package com.tbs.trimplus.module.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tbs.trimplus.R;
import com.tbs.trimplus.module.main.bean.Mine;
import com.tbs.trimplus.utils.GlideUtil;

import java.util.List;

public class MyAttentionAdapter extends RecyclerView.Adapter<MyAttentionAdapter.ViewHolder> {

    private Context context;
    List<Mine.AttentionBean> list;

    public MyAttentionAdapter(Context context, List<Mine.AttentionBean> attentionBeanList) {
        this.context = context;
        this.list = attentionBeanList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_attention, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GlideUtil.glideLoader(context, list.get(position).getHeader_pic_url(), R.drawable.icon_head_default,
                R.drawable.icon_head_default, holder.imageView, GlideUtil.CIRCLE_IMAGE);
        holder.textView.setText(list.get(position).getNick());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.ll_my_attention_item);
            imageView = itemView.findViewById(R.id.iv_my_attention_icon);
            textView = itemView.findViewById(R.id.tv_my_attention_name);
        }
    }
}
