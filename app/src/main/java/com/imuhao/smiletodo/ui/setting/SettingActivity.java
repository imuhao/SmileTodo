package com.imuhao.smiletodo.ui.setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.ui.base.BaseActivity;
import com.imuhao.smiletodo.utils.ThemeUtils;
import com.imuhao.smiletodo.utils.UiHelper;

/**
 * Created by smile on 16-10-25.
 */

public class SettingActivity extends BaseActivity {

  private Toolbar mToolbar;


  @Override protected int getLayoutId() {
    return R.layout.activity_setting;
  }

  public void initView() {
    mToolbar = (Toolbar) findViewById(R.id.toolbar);
    mToolbar.setBackgroundColor(ThemeUtils.getThemeColor());
    findViewById(R.id.tv_theme_color).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        UiHelper.showThemeColor(SettingActivity.this);
      }
    });
  }
}
