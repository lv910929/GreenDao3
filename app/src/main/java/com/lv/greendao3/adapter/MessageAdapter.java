package com.lv.greendao3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lv.greendao3.R;
import com.lv.greendao3.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lv on 2017/3/14.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<Message> messages;
    private Context context;

    public MessageAdapter(Context context) {
        this.context = context;
        messages = new ArrayList<>();
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageMessageState;
        TextView textMessageTitle;
        TextView textMessageTime;
        TextView textMessageContent;

        public ViewHolder(View itemView) {
            super(itemView);

            imageMessageState = (ImageView) itemView.findViewById(R.id.image_message_state);
            textMessageTitle = (TextView) itemView.findViewById(R.id.text_message_title);
            textMessageTime = (TextView) itemView.findViewById(R.id.text_message_time);
            textMessageContent = (TextView) itemView.findViewById(R.id.text_message_content);
        }

    }
}
