package com.imuhao.smiletodo.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.activity.AddTaskActivity;
import com.imuhao.smiletodo.inter.ItemTouchHelperAdapter;
import com.imuhao.smiletodo.model.TodoBean;
import com.imuhao.smiletodo.model.TodoDaoManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by smile on 16-10-25.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoHolder> implements ItemTouchHelperAdapter {
    private List<TodoBean> mData = new ArrayList<>();
    private Context mContext;

    private static final int TYPE_NULL = 0X001;
    private static final int TYPE_DATE = 0x002;


    public TodoAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<TodoBean> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() == 0) {
            return 1;
        } else {
            return mData.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mData == null || mData.size() == 0) {
            return TYPE_NULL;
        } else {
            return TYPE_DATE;
        }
    }

    public void addData(TodoBean bean) {
        if (mData == null)
            mData = new ArrayList<>();
        mData.add(bean);
        notifyDataSetChanged();
    }

    public TodoBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == TYPE_DATE) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_todo_task, parent, false);
        } else if (viewType == TYPE_NULL) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_todo_null, parent, false);
        }
        return new TodoHolder(view);
    }

    @Override
    public void onBindViewHolder(TodoHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_NULL) return;

        final TodoBean bean = mData.get(position);
        Log.d("imuhao", bean.toString());

        //设置标题
        String title = bean.getTitle();
        holder.tvTitle.setText(title);

        //设置头文字
        if (title.length() > 0) {
            holder.tvFirstTitle.setText(String.valueOf(title.charAt(0)).toUpperCase());
        }

        //设置随机颜色
        Drawable background = holder.tvFirstTitle.getBackground();
        String color = getRandColorCode();
        background.setColorFilter(Color.parseColor(color), PorterDuff.Mode.SRC);

        //设置时间
        String time = bean.getTime();
        if (!TextUtils.isEmpty(time) && !time.equals("null")) {
            holder.tvTime.setText(time);
            holder.tvTime.setVisibility(View.VISIBLE);
        } else {
            holder.tvTime.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddTaskActivity.class);
                intent.putExtra("todo", getItem(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onItemRemoved(RecyclerView.ViewHolder viewHolder) {
        int position = viewHolder.getAdapterPosition();
        if (mData.isEmpty() || position > mData.size() - 1) {
            return;
        }
        TodoBean removeBean = mData.remove(position);
        TodoDaoManager.remove(removeBean);
        notifyItemRemoved(position);
        Snackbar.make(viewHolder.itemView, "Delete " + removeBean.getTitle() + " the success!", Snackbar.LENGTH_LONG).show();
    }


    //拖动的时候回调
    @Override
    public void onItemMove(int fromPosition, int toPosition) {

    }

    private String getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
//        g = g.length() == 1 ? "0" + g : g;
        g = Integer.toHexString(180);
        b = b.length() == 1 ? "0" + b : b;

        return "#" + r + g + b;
    }
}

class TodoHolder extends RecyclerView.ViewHolder {
    TextView tvTitle;
    TextView tvFirstTitle;
    TextView tvTime;

    public TodoHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvFirstTitle = (TextView) itemView.findViewById(R.id.tv_first_title);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
    }
}
