package com.imuhao.smiletodo.utils;

import com.imuhao.smiletodo.bean.TodoBean;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Smile
 * @time 2017/2/23  下午2:54
 * @desc ${TODD}
 */
public class ListUtils {
  public static void sortTop(List<TodoBean> items) {
    Collections.sort(((List<TodoBean>) items), new Comparator<TodoBean>() {
      @Override public int compare(TodoBean o1, TodoBean o2) {
        return o1.getIsTop() ? -1 : 0;
      }
    });
  }
}
