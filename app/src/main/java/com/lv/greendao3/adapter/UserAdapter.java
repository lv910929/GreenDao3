package com.lv.greendao3.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lv.greendao3.ContactActivity;
import com.lv.greendao3.R;
import com.lv.greendao3.model.User;
import com.lv.greendao3.utils.ActivityUtil;
import com.lv.greendao3.widget.SmoothCheckBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.carbs.android.avatarimageview.library.AvatarImageView;

/**
 * Created by Lv on 2017/2/27.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;
    private Context context;
    private boolean isEditMode;

    private Set<User> selectUsers;

    private EditUserListener editUserListener;

    public UserAdapter(Context context, boolean isEditMode, EditUserListener editUserListener) {
        this.context = context;
        this.isEditMode = isEditMode;
        this.editUserListener = editUserListener;
        users = new ArrayList<>();
        selectUsers = new HashSet<>();
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;
        notifyDataSetChanged();
    }

    public Set<User> getSelectUsers() {
        return selectUsers;
    }

    //全不选
    public void unSelectAll() {
        for (int i = 0; i < users.size(); i++) {
            users.get(i).setSelect(false);
        }
        selectUsers.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final User user = users.get(position);
        String firstChar = String.valueOf(user.getName().charAt(0));
        holder.avatarImageUser.setTextAndColor(firstChar, AvatarImageView.COLORS[position % 6]);
        if (user.getSexy() == User.MAN) {
            holder.textSexyFlag.setText("男生");
            holder.textSexyFlag.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.textSexyFlag.setText("女生");
            holder.textSexyFlag.setTextColor(context.getResources().getColor(R.color.red600));
        }
        if (isEditMode) {
            holder.checkboxUserItem.setVisibility(View.VISIBLE);
            holder.btnEditUser.setVisibility(View.VISIBLE);
        } else {
            holder.checkboxUserItem.setVisibility(View.GONE);
            holder.btnEditUser.setVisibility(View.GONE);
        }
        holder.textUserName.setText(user.getName());
        if (user.isSelect()) {
            holder.checkboxUserItem.setChecked(true);
        } else {
            holder.checkboxUserItem.setChecked(false);
        }
        holder.btnEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editUserListener.editUser(user);
            }
        });
        holder.checkboxUserItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkboxUserItem.isChecked()) {//之前是选中状态
                    holder.checkboxUserItem.setChecked(false);
                    user.setSelect(false);
                    selectUsers.remove(user);
                } else {//之前是未选状态
                    holder.checkboxUserItem.setChecked(true);
                    user.setSelect(true);
                    selectUsers.add(user);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("user", user);
                bundle.putInt("colorId", position % 6);
                ActivityUtil.startActivity((Activity) context, ContactActivity.class, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SmoothCheckBox checkboxUserItem;
        AvatarImageView avatarImageUser;
        TextView textUserName;
        TextView textSexyFlag;
        ImageView btnEditUser;

        public ViewHolder(View itemView) {
            super(itemView);
            checkboxUserItem = (SmoothCheckBox) itemView.findViewById(R.id.checkbox_user_item);
            avatarImageUser = (AvatarImageView) itemView.findViewById(R.id.avatar_image_user);
            textUserName = (TextView) itemView.findViewById(R.id.text_user_name);
            textSexyFlag = (TextView) itemView.findViewById(R.id.text_sexy_flag);
            btnEditUser = (ImageView) itemView.findViewById(R.id.btn_edit_user);

        }
    }
}
