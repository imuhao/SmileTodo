package com.imuhao.smiletodo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imuhao.smiletodo.R;
import com.imuhao.smiletodo.inter.ThemeColorListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by smile on 16-11-1.
 */

public class ThemeColorAdapter extends RecyclerView.Adapter<ThemeColorHolder> {
    private List<Integer> mColors;
    private Context mContext;
    private ThemeColorListener mListener;

    public ThemeColorAdapter(Context context, ThemeColorListener listener, List<Integer> list) {
        mContext = context;
        mColors = list;
        mListener = listener;
    }

    @Override
    public ThemeColorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_theme_color, parent, false);
        return new ThemeColorHolder(view);
    }

    @Override
    public void onBindViewHolder(ThemeColorHolder holder, int position) {
        final Integer color = mColors.get(position);
        holder.mImageView.setImageResource(color);
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onColorChoose(color);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mColors.size();
    }
}

class ThemeColorHolder extends RecyclerView.ViewHolder {
    CircleImageView mImageView;

    public ThemeColorHolder(View itemView) {
        super(itemView);
        mImageView = (CircleImageView) itemView.findViewById(R.id.theme_color);
    }
}
