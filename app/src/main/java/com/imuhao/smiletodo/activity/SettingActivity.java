package com.imuhao.smiletodo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.utils.ThemeUtils;
import com.imuhao.smiletodo.utils.UiHelper;

/**
 * Created by smile on 16-10-25.
 */

public class SettingActivity extends Activity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setBackgroundColor(ThemeUtils.getThemeColor());
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.tv_theme_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UiHelper.showThemeColor(SettingActivity.this);
            }
        });
    }
}
