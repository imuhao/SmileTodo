package com.imuhao.smiletodo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.imuhao.smiletodo.R;

/**
 * Created by smile on 16-10-25.
 */

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
