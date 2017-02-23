package com.imuhao.smiletodo.ui.home;

import com.imuhao.smiletodo.bean.TodoBean;
import java.util.List;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * @author Smile
 * @time 2017/2/19  下午3:07
 * @desc ${TODD}
 */
public class TodoAdapter extends MultiTypeAdapter {

  public List<TodoBean> getItems() {
    return (List<TodoBean>) items;
  }
}
