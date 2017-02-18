package com.imuhao.smiletodo.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.inter.ItemTouchHelperAdapter;
import com.imuhao.smiletodo.model.TodoBean;
import com.imuhao.smiletodo.model.TodoDaoManager;
import com.imuhao.smiletodo.ui.addtask.AddTaskActivity;
import com.imuhao.smiletodo.utils.AlertUtils;
import com.like.LikeButton;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by smile on 16-10-25.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder>
    implements ItemTouchHelperAdapter {
  private List<TodoBean> mData = new ArrayList<>();
  private Activity mActivity;

  private static final int TYPE_NULL = 0X001;
  private static final int TYPE_DATE = 0x002;

  public TodoAdapter(Activity activity) {
    mActivity = activity;
  }

  public void setData(List<TodoBean> data) {
    mData = data;
    notifyDataSetChanged();
  }

  @Override public int getItemCount() {
    if (mData == null || mData.size() == 0) {
      return 1;
    } else {
      return mData.size();
    }
  }

  @Override public int getItemViewType(int position) {
    if (mData == null || mData.size() == 0) {
      return TYPE_NULL;
    } else {
      return TYPE_DATE;
    }
  }

  public TodoBean getItem(int position) {
    return mData.get(position);
  }

  @Override public TodoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = null;
    if (viewType == TYPE_DATE) {
      view = LayoutInflater.from(mActivity).inflate(R.layout.item_todo_task, parent, false);
    } else if (viewType == TYPE_NULL) {
      view = LayoutInflater.from(mActivity).inflate(R.layout.item_todo_null, parent, false);
    }
    return new TodoHolder(view);
  }

  @Override public void onBindViewHolder(final TodoHolder holder, final int position) {
    if (getItemViewType(position) == TYPE_NULL) return;

    final TodoBean bean = mData.get(position);

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
    //跳转修改任务页
    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Intent intent = new Intent(mActivity, AddTaskActivity.class);
        intent.putExtra("todo", getItem(position));
        mActivity.startActivity(intent);
      }
    });

    //显示星星
    holder.lbTop.setVisibility(bean.getIsTop() ? View.VISIBLE : View.GONE);
    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
      @Override public boolean onLongClick(View v) {
        bean.setIsTop(!bean.getIsTop());
        TodoDaoManager.update(bean);
        notifyItemChanged(holder.getAdapterPosition());
        return true;
      }
    });
  }

  @Override public void onItemRemoved(RecyclerView.ViewHolder viewHolder) {
    int position = viewHolder.getAdapterPosition();
    if (mData.isEmpty() || position > mData.size() - 1) {
      return;
    }
    TodoBean removeBean = mData.remove(position);
    TodoDaoManager.remove(removeBean);
    notifyItemRemoved(position);
    AlertUtils.show( "删除 " + removeBean.getTitle() + " 成功!");
  }

  //拖动的时候回调
  @Override public void onItemMove(int fromPosition, int toPosition) {

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

  class TodoHolder extends RecyclerView.ViewHolder {
    TextView tvTitle;
    TextView tvFirstTitle;
    TextView tvTime;
    LikeButton lbTop;

    public TodoHolder(View itemView) {
      super(itemView);
      tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
      tvFirstTitle = (TextView) itemView.findViewById(R.id.tv_first_title);
      tvTime = (TextView) itemView.findViewById(R.id.tv_time);
      lbTop = (LikeButton) itemView.findViewById(R.id.lb_top);
    }
  }
}


