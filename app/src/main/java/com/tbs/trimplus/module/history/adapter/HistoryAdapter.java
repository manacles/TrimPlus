package com.tbs.trimplus.module.history.adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tbs.trimplus.R;
import com.tbs.trimplus.module.main.bean.Bible;
import com.tbs.trimplus.utils.GlideUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Bible> data;

    private String type;
    private static final int RIGHT_PIC = 1;
    private static final int THREE_PIC = 2;
    private static final int BIG_PIC = 3;
    private static final int FOOTER_VIEW = 4;

    private View itemRight;
    private View itemThree;
    private View itemBig;

    private View footView;
    private LinearLayout footLayout;

    private String searchWord;

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    private Object modifyTitle(String title) {
        if (!TextUtils.isEmpty(searchWord)) {
            if (title != null && !title.isEmpty() && title.contains(searchWord)) {
                String head = title.substring(0, title.indexOf(searchWord));
                String foot = title.substring(head.length() + searchWord.length());
                return Html.fromHtml(head + "<font color=\"#FC4141\">" + searchWord + "</font>" + foot);
            }
        }
        return title;
    }

    public HistoryAdapter(Context context, ArrayList<Bible> bibleArrayList) {
        this.context = context;
        this.data = bibleArrayList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == RIGHT_PIC) {
            itemRight = inflater.inflate(R.layout.item_bible_right_pic, parent, false);
            RightHolder holder = new RightHolder(itemRight);
            itemRight.setOnClickListener(this);
            return holder;
        }
        if (viewType == THREE_PIC) {
            itemThree = inflater.inflate(R.layout.item_bible_three_pic, parent, false);
            ThreeHolder holder = new ThreeHolder(itemThree);
            itemThree.setOnClickListener(this);
            return holder;
        }
        if (viewType == BIG_PIC) {
            itemBig = inflater.inflate(R.layout.item_bible_big_pic, parent, false);
            BigHolder holder = new BigHolder(itemBig);
            itemBig.setOnClickListener(this);
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
            Bible bible = data.get(position);
            if (holder instanceof RightHolder && type.equals("1")) {
                RightHolder rightHolder = (RightHolder) holder;

                rightHolder.tv_title.setText((CharSequence) modifyTitle(bible.getTitle()));
                rightHolder.tv_author_name.setText(bible.getAuthor_name());
                rightHolder.tv_time.setText(bible.getTime());
                rightHolder.tv_view_count.setText(bible.getView_count());
                rightHolder.tv_collect_count.setText(bible.getCollect_count());
                rightHolder.tv_tup_count.setText(bible.getTup_count());

                if (bible.getImg_url() != null && bible.getImg_url().size() > 0 && bible.getImg_url().get(0) != null) {
                    GlideUtil.glideLoader(context, bible.getImg_url().get(0), R.drawable.occupied1, R.drawable.occupied1,
                            rightHolder.iv_right_pic, GlideUtil.ROUND_IMAGE);
                }
                rightHolder.itemView.setTag(bible);
            } else if (holder instanceof ThreeHolder && type.equals("2")) {
                ThreeHolder threeHolder = (ThreeHolder) holder;

                threeHolder.tv_title.setText((CharSequence) modifyTitle(bible.getTitle()));
                threeHolder.tv_author_name.setText(bible.getAuthor_name());
                threeHolder.tv_time.setText(bible.getTime());
                threeHolder.tv_view_count.setText(bible.getView_count());
                threeHolder.tv_collect_count.setText(bible.getCollect_count());
                threeHolder.tv_tup_count.setText(bible.getTup_count());

                if (bible.getImg_url() != null && bible.getImg_url().size() > 0) {
                    if (bible.getImg_url().get(0) != null) {
                        GlideUtil.glideLoader(context, bible.getImg_url().get(0), R.drawable.occupied1, R.drawable.occupied1,
                                threeHolder.iv_left_pic, GlideUtil.ROUND_IMAGE);
                    }
                    if (bible.getImg_url().get(1) != null) {
                        GlideUtil.glideLoader(context, bible.getImg_url().get(1), R.drawable.occupied1, R.drawable.occupied1,
                                threeHolder.iv_mid_pic, GlideUtil.ROUND_IMAGE);
                    }
                    if (bible.getImg_url().get(2) != null) {
                        GlideUtil.glideLoader(context, bible.getImg_url().get(2), R.drawable.occupied1, R.drawable.occupied1,
                                threeHolder.iv_right_pic, GlideUtil.ROUND_IMAGE);
                    }
                }
                threeHolder.itemView.setTag(bible);
            } else if (holder instanceof BigHolder && type.equals("3")) {
                BigHolder bigHolder = (BigHolder) holder;

                bigHolder.tv_title.setText((CharSequence) modifyTitle(bible.getTitle()));
                bigHolder.tv_author_name.setText(bible.getAuthor_name());
                bigHolder.tv_time.setText(bible.getTime());
                bigHolder.tv_view_count.setText(bible.getView_count());
                bigHolder.tv_collect_count.setText(bible.getCollect_count());
                bigHolder.tv_tup_count.setText(bible.getTup_count());

                if (bible.getImg_url() != null && bible.getImg_url().size() > 0 && bible.getImg_url().get(0) != null) {
                    GlideUtil.glideLoader(context, bible.getImg_url().get(0), R.drawable.occupied1, R.drawable.occupied1,
                            bigHolder.iv_big_pic, GlideUtil.ROUND_IMAGE);
                }
                bigHolder.itemView.setTag(bible);
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
            type = data.get(position).getStyle();
            switch (type) {
                case "1":
                    return RIGHT_PIC;
                case "2":
                    return THREE_PIC;
                case "3":
                    return BIG_PIC;
                default:
                    return FOOTER_VIEW;
            }
        } else {
            return FOOTER_VIEW;
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onRecyclerViewItemClick(view, (Bible) view.getTag());
        }
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onRecyclerViewItemClick(View view, Bible data);
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

    private class RightHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_author_name;
        TextView tv_time;
        TextView tv_view_count;
        TextView tv_collect_count;
        TextView tv_tup_count;
        ImageView iv_right_pic;

        public RightHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_author_name = itemView.findViewById(R.id.tv_author_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_view_count = itemView.findViewById(R.id.tv_view_count);
            tv_collect_count = itemView.findViewById(R.id.tv_collect_count);
            tv_tup_count = itemView.findViewById(R.id.tv_tup_count);
            iv_right_pic = itemView.findViewById(R.id.iv_right_pic);
        }
    }

    private class ThreeHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_author_name;
        TextView tv_time;
        TextView tv_view_count;
        TextView tv_collect_count;
        TextView tv_tup_count;
        ImageView iv_left_pic;
        ImageView iv_mid_pic;
        ImageView iv_right_pic;

        public ThreeHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_author_name = itemView.findViewById(R.id.tv_author_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_view_count = itemView.findViewById(R.id.tv_view_count);
            tv_collect_count = itemView.findViewById(R.id.tv_collect_count);
            tv_tup_count = itemView.findViewById(R.id.tv_tup_count);
            iv_left_pic = itemView.findViewById(R.id.iv_left_pic);
            iv_mid_pic = itemView.findViewById(R.id.iv_mid_pic);
            iv_right_pic = itemView.findViewById(R.id.iv_right_pic);
        }
    }

    private static class BigHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_author_name;
        TextView tv_time;
        TextView tv_view_count;
        TextView tv_collect_count;
        TextView tv_tup_count;
        ImageView iv_big_pic;

        public BigHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_author_name = itemView.findViewById(R.id.tv_author_name);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_view_count = itemView.findViewById(R.id.tv_view_count);
            tv_collect_count = itemView.findViewById(R.id.tv_collect_count);
            tv_tup_count = itemView.findViewById(R.id.tv_tup_count);
            iv_big_pic = itemView.findViewById(R.id.iv_big_pic);
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {
        public FootViewHolder(View view) {
            super(view);
        }
    }
}
