package com.imuhao.smiletodo.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.bean.TodoBean;
import com.imuhao.smiletodo.bean.TodoDaoManager;
import com.imuhao.smiletodo.ui.addtask.AddTaskActivity;
import com.imuhao.smiletodo.utils.ColorUtils;
import com.like.LikeButton;
import me.drakeet.multitype.ItemViewProvider;

/**
 * @author Smile
 * @time 2017/2/19  下午2:36
 * @desc ${TODD}
 */
public class TodoViewProvider extends ItemViewProvider<TodoBean, TodoViewProvider.ViewHolder> {

  @NonNull @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater,
      @NonNull ViewGroup parent) {
    View view = inflater.inflate(R.layout.item_todo_task, parent, false);
    return new ViewHolder(view);
  }

  @Override
  protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull TodoBean bean) {
    holder.bindData(bean);
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    TextView tvTitle;
    TextView tvFirstTitle;
    TextView tvTime;
    LikeButton lbTop;

    ViewHolder(View itemView) {
      super(itemView);
      tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
      tvFirstTitle = (TextView) itemView.findViewById(R.id.tv_first_title);
      tvTime = (TextView) itemView.findViewById(R.id.tv_time);
      lbTop = (LikeButton) itemView.findViewById(R.id.lb_top);
    }

    void bindData(final TodoBean bean) {
      //设置标题
      tvTitle.setText(bean.getTitle());
      //设置头文字
      if (bean.getTitle().length() > 0) {
        tvFirstTitle.setText(String.valueOf(bean.getTitle().charAt(0)).toUpperCase());
      }
      //设置随机颜色
      tvFirstTitle.getBackground()
          .setColorFilter(Color.parseColor(ColorUtils.getRandColorCode()), PorterDuff.Mode.SRC);

      //设置时间
      boolean timeIsEmpty = TextUtils.isEmpty(bean.getTime());
      tvTime.setVisibility(timeIsEmpty ? View.GONE : View.VISIBLE);
      if (!timeIsEmpty) tvTime.setText(bean.getTime());

      //跳转修改任务页
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          Intent intent = new Intent(itemView.getContext(), AddTaskActivity.class);
          intent.putExtra("todo", bean);
          itemView.getContext().startActivity(intent);
        }
      });

      //显示星星
      lbTop.setVisibility(bean.getIsTop() ? View.VISIBLE : View.GONE);
      itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override public boolean onLongClick(View v) {
          bean.setIsTop(!bean.getIsTop());
          TodoDaoManager.update(bean);
          getAdapter().notifyItemChanged(getAdapterPosition());
          return true;
        }
      });
    }
  }
}
