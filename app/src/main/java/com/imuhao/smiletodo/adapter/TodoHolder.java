package com.imuhao.smiletodo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.imuhao.smiletodo.R;

/**
 * Created by smile on 16-10-29.
 */

public class TodoHolder extends RecyclerView.ViewHolder {
    TextView tvTitle;
    TextView tvFirstTitle;
    TextView tvTime;

    public TodoHolder(View itemView) {
        super(itemView);
        tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        tvFirstTitle = (TextView) itemView.findViewById(R.id.tv_first_title);
        tvTime = (TextView) itemView.findViewById(R.id.tv_time);
    }
}
