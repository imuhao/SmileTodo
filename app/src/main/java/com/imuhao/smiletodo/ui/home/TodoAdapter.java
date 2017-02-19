package com.imuhao.smiletodo.ui.home;

import android.support.v7.widget.RecyclerView;
import com.imuhao.smiletodo.inter.ItemTouchHelperAdapter;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author Smile
 * @time 2017/2/19  下午3:07
 * @desc ${TODD}
 */
public class TodoAdapter extends MultiTypeAdapter implements ItemTouchHelperAdapter {

  @Override public void onItemRemoved(RecyclerView.ViewHolder position) {


    /*int position = getAdapterPosition();
    if (items.isEmpty() || position > mData.size() - 1) {
      return;
    }
    TodoBean removeBean = mData.remove(position);
    TodoDaoManager.remove(removeBean);
    notifyItemRemoved(position);
    AlertUtils.show("删除 " + removeBean.getTitle() + " 成功!");*/
  }
}
