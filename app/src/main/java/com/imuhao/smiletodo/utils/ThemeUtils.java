package com.imuhao.smiletodo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.imuhao.smiletodo.App;

/**
 * Created by smile on 16-11-1.
 * 设置主题的颜色
 */

public class ThemeUtils {
//    private static int defaultColor = Color.parseColor("#1ed48a");
    private static int defaultColor = Color.parseColor("#303F9F");

    //设置主题的颜色
    public static void setThemeColor(int color) {
        SharedPreferences sp = App.mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        sp.edit().putInt("ThemeColor", color).apply();
    }

    //获取主题的颜色
    public static int getThemeColor() {
        SharedPreferences sp = App.mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        return sp.getInt("ThemeColor", defaultColor);
    }
}
