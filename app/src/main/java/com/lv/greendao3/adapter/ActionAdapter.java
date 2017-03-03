package com.lv.greendao3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lv.greendao3.R;

/**
 * Created by Lv on 2017/3/2.
 */

public class ActionAdapter extends RecyclerView.Adapter<ActionAdapter.ViewHolder> {

    private final static String[] ACTION_TYPE_LIST = {"删除", "修改", "复制"};
    private final static int[] ACTION_ICON_LIST = {R.drawable.ic_action_delete, R.drawable.ic_action_modify, R.drawable.ic_action_clipboard};

    private Context context;

    private OnItemClickListener onItemClickListener;

    public ActionAdapter(Context context) {
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_action_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imageAction.setImageDrawable(context.getResources().getDrawable(ACTION_ICON_LIST[position]));
        holder.textAction.setText(ACTION_TYPE_LIST[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ACTION_ICON_LIST.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageAction;
        TextView textAction;

        public ViewHolder(View itemView) {
            super(itemView);
            imageAction = (ImageView) itemView.findViewById(R.id.image_action);
            textAction = (TextView) itemView.findViewById(R.id.text_action);
        }
    }
}
