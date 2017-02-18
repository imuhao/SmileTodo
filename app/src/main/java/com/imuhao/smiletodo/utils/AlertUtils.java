package com.imuhao.smiletodo.utils;

import com.imuhao.smiletodo.R;
import com.tapadoo.alerter.Alerter;

/**
 * @author Smile
 * @time 2017/2/18  下午6:09
 * @desc ${TODD}
 */
public class AlertUtils {

  public static void show(String txt) {
    show(txt, null);
  }

  public static void show(String txt, String title) {
    Alerter.create(MyActivityManager.getInstance().getCurrentActivity())
        .setIcon(R.drawable.ic_face)
        .setBackgroundColor(R.color.backgroundColor)
        .setText(txt)
        .setTitle(title)
        .show();
  }
}
