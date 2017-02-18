package com.imuhao.smiletodo.ui.about;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toolbar;
import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.utils.ThemeUtils;

/**
 * Created by smile on 16-10-25.
 */

public class AboutActivity extends Activity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_about);
    initView();
  }

  private void initView() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setBackgroundColor(ThemeUtils.getThemeColor());
  }
}
