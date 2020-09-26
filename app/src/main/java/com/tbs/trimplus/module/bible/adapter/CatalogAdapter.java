package com.tbs.trimplus.module.bible.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.stetho.common.LogUtil;
import com.tbs.trimplus.R;
import com.tbs.trimplus.module.bible.bean.Catalog;
import com.tbs.trimplus.utils.DensityUtil;

import java.util.ArrayList;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.ViewHolder> {

    private static final int RECOMMEND = 1;
    private static final int OTHER = 2;

    private Context context;
    private ArrayList<Catalog> catalogs;
    private boolean isSelected;

    public CatalogAdapter(Context context, ArrayList<Catalog> catalogs, boolean isSelected) {
        this.context = context;
        this.catalogs = catalogs;
        this.isSelected = isSelected;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_catalog, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Catalog catalog = catalogs.get(position);

        holder.tv_catalog_name.setText(catalog.getName());

        if (getItemViewType(position) == OTHER) {
            if (isSelected) {
                holder.fl_item.setForeground(context.getDrawable(R.drawable.reeor_fill));
            } else {
                holder.fl_item.setForeground(context.getDrawable(R.drawable.add_fill));
            }
            holder.fl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(position);
                    }
                }
            });
        } else {
            holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.popupwindow_line));
        }
    }

    @Override
    public int getItemCount() {
        return catalogs.size();
    }

    @Override
    public int getItemViewType(int position) {
        String title = catalogs.get(position).getName();
        if (title.equals("推荐") || title.equals("本地")) {
            return RECOMMEND;
        } else {
            return OTHER;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_catalog_name;
        FrameLayout fl_item;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_catalog_name = itemView.findViewById(R.id.tv_catalog_name);
            fl_item = itemView.findViewById(R.id.fl_item);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

}
