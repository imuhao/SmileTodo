package com.imuhao.smiletodo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import android.os.Bundle;
import com.imuhao.smiletodo.bean.DaoMaster;
import com.imuhao.smiletodo.bean.DaoSession;
import com.imuhao.smiletodo.utils.MyActivityManager;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by smile on 16-10-25.
 */

public class App extends Application {
  private static DaoSession daoSession;
  public static Context mContext;

  @Override public void onCreate() {
    super.onCreate();
    mContext = getApplicationContext();
    initActivityLifecycle();
    initGreenDao();
  }

  private void initActivityLifecycle() {
    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
      @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

      }

      @Override public void onActivityStarted(Activity activity) {

      }

      @Override public void onActivityResumed(Activity activity) {
        MyActivityManager.getInstance().setCurrentActivity(activity);
      }

      @Override public void onActivityPaused(Activity activity) {

      }

      @Override public void onActivityStopped(Activity activity) {

      }

      @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

      }

      @Override public void onActivityDestroyed(Activity activity) {

      }
    });
  }

  private void initGreenDao() {
    DaoMaster.DevOpenHelper openHelper =
        new DaoMaster.DevOpenHelper(getApplicationContext(), "smile.db", null);
    DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDatabase());
    daoSession = daoMaster.newSession();
  }

  public static DaoSession getSession() {
    return daoSession;
  }
}
