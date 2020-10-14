package com.tbs.trimplus.module.user.adapter;

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
import com.tbs.trimplus.module.bible.utils.ReadMessageManage;
import com.tbs.trimplus.module.user.bean.Message;
import com.tbs.trimplus.utils.GlideUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Message.DataBeanX.DataBean> data;

    private static final int NORMAL = 1;
    private static final int FOOTER_VIEW = 4;

    private View itemView;

    private View footView;
    private LinearLayout footLayout;

    private List<String> haveReads;

    public void setHaveReads(List<String> haveReads) {
        this.haveReads = haveReads;
    }

    public MessageAdapter(Context context, ArrayList<Message.DataBeanX.DataBean> bibleArrayList) {
        this.context = context;
        this.data = bibleArrayList;
        this.inflater = LayoutInflater.from(context);

        haveReads = ReadMessageManage.getHaveRead(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == NORMAL) {
            itemView = inflater.inflate(R.layout.item_message_center, parent, false);
            NormalHolder holder = new NormalHolder(itemView);
            itemView.setOnClickListener(this);
            return holder;
        }
        if (viewType == FOOTER_VIEW) {
            footView = inflater.inflate(R.layout.item_foot, parent, false);
            footLayout = footView.findViewById(R.id.layout_foot);
            return new FootViewHolder(footView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position < data.size()) {
            Message.DataBeanX.DataBean bible = data.get(position);
            if (holder instanceof NormalHolder) {
                NormalHolder normalHolder = (NormalHolder) holder;

                normalHolder.msg_title.setText(bible.getTitle());
                normalHolder.msg_text_description.setText(bible.getDescription());
                normalHolder.msg_time.setText(MessageFormat.format("{0}{1}", bible.getTime_rec().getTime(), bible.getTime_rec().getTime_unit()));

                if (haveReads != null && haveReads.contains(bible.getAid())) {
                    normalHolder.msg_iv_read_flag.setVisibility(View.GONE);
                }

                GlideUtil.glideLoader(context, bible.getHeader_pic_url(), 0, 0,
                        normalHolder.msg_head, GlideUtil.CIRCLE_IMAGE);

                normalHolder.itemView.setTag(bible);
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
            return NORMAL;
        } else {
            return FOOTER_VIEW;
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onRecyclerViewItemClick(view, (Message.DataBeanX.DataBean) view.getTag());
        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onRecyclerViewItemClick(View view, Message.DataBeanX.DataBean data);
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

    private class NormalHolder extends RecyclerView.ViewHolder {
        TextView msg_title;
        TextView msg_time;
        TextView msg_text_description;
        ImageView msg_iv_read_flag;
        ImageView msg_head;

        public NormalHolder(@NonNull View itemView) {
            super(itemView);
            msg_title = itemView.findViewById(R.id.msg_title);
            msg_time = itemView.findViewById(R.id.msg_time);
            msg_text_description = itemView.findViewById(R.id.msg_text_description);
            msg_iv_read_flag = itemView.findViewById(R.id.msg_iv_read_flag);
            msg_head = itemView.findViewById(R.id.msg_head);
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View view) {
            super(view);
        }
    }
}
