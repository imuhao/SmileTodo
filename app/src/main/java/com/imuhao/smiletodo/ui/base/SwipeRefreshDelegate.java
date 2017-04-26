package com.imuhao.smiletodo.ui.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.utils.ThemeUtils;

/**
 * @author Smile
 * @time 2017/2/25  上午9:33
 * @desc ${TODD}
 */
public class SwipeRefreshDelegate {

  private SwipeRefreshLayout mSwipeRefreshLayout;
  private OnSwipeRefreshListener mSwipeListener;
  private boolean isRefresh;

  public SwipeRefreshDelegate(OnSwipeRefreshListener listener) {
    mSwipeListener = listener;
  }

  public void attach(Activity activity) {
    mSwipeRefreshLayout = (SwipeRefreshLayout) activity.findViewById(R.id.refreshLayout);
    mSwipeRefreshLayout.setColorSchemeColors(ThemeUtils.getThemeColor());
    trySwipeRefresh();
  }

  public void attach(Fragment fragment) {
    mSwipeRefreshLayout =
        (SwipeRefreshLayout) fragment.getActivity().findViewById(R.id.refreshLayout);
    trySwipeRefresh();
  }

  public void trySwipeRefresh() {
    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override public void onRefresh() {
        isRefresh = true;
        mSwipeListener.onRefresh();
      }
    });
  }

  public void setRefresh(final boolean refresh) {
    if (!refresh) {
      //让子弹飞一会~
      mSwipeRefreshLayout.postDelayed(new Runnable() {
        @Override public void run() {
          isRefresh = false;
          mSwipeRefreshLayout.setRefreshing(false);
        }
      }, 1000);
    } else {
      mSwipeRefreshLayout.setRefreshing(true);
    }
  }

  public void setEnable(boolean enable) {
    mSwipeRefreshLayout.setEnabled(enable);
  }

  public void setVisibility(int visibility) {
    mSwipeRefreshLayout.setVisibility(visibility);
  }

  public interface OnSwipeRefreshListener {
    void onRefresh();
  }
}
