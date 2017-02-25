package com.imuhao.smiletodo.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author Smile
 * @time 2017/2/25  上午10:25
 * @desc ${TODD}
 */
public abstract class BaseActivity extends AppCompatActivity {

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    initView();
  }

  protected abstract int getLayoutId();

  protected abstract void initView();
}
