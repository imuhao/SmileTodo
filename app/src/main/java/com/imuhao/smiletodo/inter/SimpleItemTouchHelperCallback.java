package com.imuhao.smiletodo.inter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by smile on 16-10-25.
 */

public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
  private ItemTouchHelperCallback mHelper;

  public SimpleItemTouchHelperCallback(ItemTouchHelperCallback helper) {
    mHelper = helper;
  }

  @Override public boolean isLongPressDragEnabled() {
    return false;
  }

  @Override public boolean isItemViewSwipeEnabled() {
    return true;
  }

  @Override
  public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
    int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
    int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
    return makeMovementFlags(dragFlags, swipeFlags);
  }

  @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
      RecyclerView.ViewHolder target) {
    return true;
  }

  @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    mHelper.onItemRemoved(viewHolder);
  }
}
