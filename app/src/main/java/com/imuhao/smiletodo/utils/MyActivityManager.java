package com.imuhao.smiletodo.utils;

import android.app.Activity;
import java.lang.ref.WeakReference;

/**
 * @author Smile
 * @time 2017/2/18  下午6:28
 * @desc ${TODD}
 */
public class MyActivityManager {

  private static MyActivityManager sInstance = new MyActivityManager();
  private WeakReference<Activity> sCurrentActivityWeakRef;

  public static MyActivityManager getInstance() {
    return sInstance;
  }

  public Activity getCurrentActivity() {
    Activity currentActivity = null;
    if (sCurrentActivityWeakRef != null) {
      currentActivity = sCurrentActivityWeakRef.get();
    }
    return currentActivity;
  }

  public void setCurrentActivity(Activity activity) {
    sCurrentActivityWeakRef = new WeakReference<Activity>(activity);
  }
}
