package com.imuhao.smiletodo.utils;

import android.app.Activity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.adapter.ThemeColorAdapter;
import com.imuhao.smiletodo.inter.ThemeColorListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smile on 16-11-1.
 */

public class UiHelper {

    /**
     * 设置主题颜色
     */
    public static void showThemeColor(final Activity context) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dialog_theme_color, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final AlertDialog dialog = builder.setView(view).show();

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_theme_title);
        tvTitle.setTextColor(ThemeUtils.getThemeColor());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.theme_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        List<Integer> colors = new ArrayList<>();
        colors.add(R.color.theme_green);
        colors.add(R.color.theme_red_base);
        colors.add(R.color.theme_red);
        colors.add(R.color.theme_blue);
        colors.add(R.color.theme_blue_light);
        colors.add(R.color.theme_balck);
        colors.add(R.color.theme_teal);
        colors.add(R.color.theme_brown);
        ThemeColorAdapter adapter = new ThemeColorAdapter(context, new ThemeColorListener() {
            @Override
            public void onColorChoose(Integer color) {
                ThemeUtils.setThemeColor(context.getResources().getColor(color));
                dialog.dismiss();
                context.recreate();
            }
        }, colors);
        recyclerView.setAdapter(adapter);
    }
}
