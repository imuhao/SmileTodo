package com.imuhao.smiletodo.inter;

import android.support.v7.widget.RecyclerView;

/**
 * Created by smile on 16-10-25.
 */

public interface ItemTouchHelperAdapter {
  //void onItemMove(int fromPosition, int toPosition);
  void onItemRemoved(RecyclerView.ViewHolder position);
}
