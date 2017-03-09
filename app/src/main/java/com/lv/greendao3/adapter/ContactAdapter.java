package com.lv.greendao3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lv.greendao3.R;
import com.lv.greendao3.model.User;

import cn.carbs.android.avatarimageview.library.AvatarImageView;
import me.yokeyword.indexablerv.IndexableAdapter;

/**
 * Created by Lv on 2017/3/8.
 */

public class ContactAdapter extends IndexableAdapter<User> {

    private LayoutInflater mInflater;

    private Context context;

    public ContactAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateTitleViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.layout_title_item, parent, false);
        return new TitleViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder onCreateContentViewHolder(ViewGroup parent) {
        View view = mInflater.inflate(R.layout.layout_contact_item, parent, false);
        return new ContentViewHolder(view);
    }

    @Override
    public void onBindTitleViewHolder(RecyclerView.ViewHolder holder, String indexTitle) {
        TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
        int position = holder.getAdapterPosition() + 1;
        titleViewHolder.avatarImageTitle.setTextAndColor(indexTitle, AvatarImageView.COLORS[position % 6]);
    }

    @Override
    public void onBindContentViewHolder(RecyclerView.ViewHolder holder, User entity) {

        ContentViewHolder contentViewHolder = (ContentViewHolder) holder;
        contentViewHolder.textContactName.setText(entity.getName());
        String firstChar = String.valueOf(entity.getName().charAt(0));
        contentViewHolder.avatarImageContact.setTextAndColor(firstChar, AvatarImageView.COLORS[(int) (entity.getId() % 6)]);
        if (entity.getSexy() == User.MAN) {
            contentViewHolder.textSexyFlag.setText("男生");
            contentViewHolder.textSexyFlag.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            contentViewHolder.textSexyFlag.setText("女生");
            contentViewHolder.textSexyFlag.setTextColor(context.getResources().getColor(R.color.red600));
        }
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {

        AvatarImageView avatarImageTitle;

        public TitleViewHolder(View itemView) {
            super(itemView);

            avatarImageTitle = (AvatarImageView) itemView.findViewById(R.id.avatar_image_title);
        }
    }

    class ContentViewHolder extends RecyclerView.ViewHolder {

        AvatarImageView avatarImageContact;
        TextView textContactName;
        TextView textSexyFlag;

        public ContentViewHolder(View itemView) {
            super(itemView);

            avatarImageContact = (AvatarImageView) itemView.findViewById(R.id.avatar_image_contact);
            textContactName = (TextView) itemView.findViewById(R.id.text_contact_name);
            textSexyFlag = (TextView) itemView.findViewById(R.id.text_sexy_flag);
        }
    }
}
