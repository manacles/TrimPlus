package com.tbs.trimplus.module.main.adapter;

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
import com.tbs.trimplus.module.main.bean.Mine;
import com.tbs.trimplus.utils.AppUtil;
import com.tbs.trimplus.utils.CacheUtil;
import com.tbs.trimplus.utils.Constant;
import com.tbs.trimplus.utils.GlideUtil;
import com.tbs.trimplus.utils.ToastUtil;

import java.util.HashMap;
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
        holder.iv_recommend_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AuthorDetailActivity.class);
                intent.putExtra("author_id", list.get(position).getUid());
                context.startActivity(intent);
            }
        });

        holder.setListener(position);
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements IdoAuthorDetilView {
        ImageView iv_recommend_icon;
        TextView tv_recommend_name;
        ImageView iv_recommend_add;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_recommend_icon = itemView.findViewById(R.id.iv_recommend_icon);
            tv_recommend_name = itemView.findViewById(R.id.tv_recommend_name);
            iv_recommend_add = itemView.findViewById(R.id.iv_recommend_add);
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
                iv_recommend_add.setImageResource(R.drawable.is_recommend);
            } else {
                iv_recommend_add.setImageResource(R.drawable.recommend_add);
            }
            ToastUtil.sToast(context, msg);
        }

        public void setListener(int position) {
            iv_recommend_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    followAuthorRequest(list.get(position).getUid());
                }
            });
        }

        @Override
        public void getAuthorDetail(BaseObject<Author> authorBaseObject) {

        }

    }




}
