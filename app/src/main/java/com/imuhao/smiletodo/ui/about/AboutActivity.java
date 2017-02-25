package com.imuhao.smiletodo.ui.about;

import android.support.v7.widget.Toolbar;
import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.ui.base.BaseActivity;
import com.imuhao.smiletodo.utils.ThemeUtils;

/**
 * Created by smile on 16-10-25.
 */

public class AboutActivity extends BaseActivity {

  @Override protected int getLayoutId() {
    return R.layout.activity_about;
  }

  public void initView() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setBackgroundColor(ThemeUtils.getThemeColor());
  }
}
