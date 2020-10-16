package com.tbs.trimplus.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.tbs.trimplus.R;

public class BottomListPopupWindow extends PopupWindow {
    private Context context;
    private String title;
    private String[] data;

    private Window window;
    private View view;
    private TextView popTitle;
    private TextView popDismiss;
    private ListView listView;

    public BottomListPopupWindow(Context context, String title, String[] data) {
        super(context);
        this.title = title;
        this.data = data;
        this.context = context;

        view = LayoutInflater.from(context).inflate(R.layout.list_popupwindow, null);
        setContentView(view);
        Activity activity = (Activity) context;
        window = activity.getWindow();

        //设置PopupWindow弹出窗体的宽
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置PopupWindow弹出窗体的高
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置PopupWindow弹出窗体可点击
        setFocusable(true);

        popTitle = view.findViewById(R.id.pop_title);
        popDismiss = view.findViewById(R.id.pop_dismiss);
        listView = view.findViewById(R.id.listview);

        initBackground();
        initData();

        initListener();
    }

    private void initData() {
        if (TextUtils.isEmpty(title)) {
            popTitle.setHeight(2);
        } else {
            popTitle.setText(title);
        }
    }

    private void initListener() {
        popDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = view.findViewById(R.id.select_layout).getTop();
                int touchHeight = (int) event.getY();
                if (height > touchHeight && event.getAction() == MotionEvent.ACTION_UP) {
                    dismiss();
                }
                return true;
            }
        });

        listView.setAdapter(new MyAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                    dismiss();
                }
            }
        });
    }

    //私有属性
    private OnItemClickListener onItemClickListener = null;

    //setter方法
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //回调接口
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private void initBackground() {
        ColorDrawable dw = new ColorDrawable(0x80000000);
        setBackgroundDrawable(dw);
        window.setStatusBarColor(0x80000000);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        window.setStatusBarColor(context.getResources().getColor(R.color.colorPrimary));
    }

    class MyAdapter extends BaseAdapter {
        ViewHolder viewHolder;

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_popupwindow, null);
                viewHolder = new ViewHolder();
                viewHolder.textView = convertView.findViewById(R.id.pop_item);
                viewHolder.pop_line = convertView.findViewById(R.id.pop_line);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.textView.setText(data[position]);
            if (TextUtils.isEmpty(title) && position == 0) {
                viewHolder.pop_line.setVisibility(View.GONE);
            }
            return convertView;
        }


        // view item
        private class ViewHolder {
            TextView textView;
            View pop_line;
        }
    }


}
