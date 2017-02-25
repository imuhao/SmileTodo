package com.imuhao.smiletodo.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * @author Smile
 * @time 2017/2/25  上午9:32
 * @desc ${TODD}
 */
public abstract class BaseRefreshActivity extends BaseActivity
    implements SwipeRefreshDelegate.OnSwipeRefreshListener {

  private SwipeRefreshDelegate mSwipeRefreshDelegate;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mSwipeRefreshDelegate = new SwipeRefreshDelegate(this);
    mSwipeRefreshDelegate.attach(this);
    loadData(true);
  }

  protected abstract void loadData(boolean clear);

  @Override public void onRefresh() {
    loadData(true);
  }

  protected void setRefresh(boolean refresh) {
    mSwipeRefreshDelegate.setRefresh(refresh);
  }

  protected void setVisibility(int visibility) {
    mSwipeRefreshDelegate.setVisibility(visibility);
  }
}
