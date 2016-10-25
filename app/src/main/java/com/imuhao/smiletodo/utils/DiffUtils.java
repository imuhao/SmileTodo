package com.imuhao.smiletodo.utils;

import android.content.Context;

/**
 * Created by smile on 16-10-10.
 */

public class DiffUtils {

    public static int dp2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }
}
