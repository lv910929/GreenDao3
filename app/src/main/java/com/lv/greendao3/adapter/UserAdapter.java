package com.lv.greendao3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lv.greendao3.R;
import com.lv.greendao3.model.User;
import com.lv.greendao3.widget.SmoothCheckBox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Lv on 2017/2/27.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> users;
    private Context context;
    private boolean isEditMode;

    private Set<User> selectUsers;

    public UserAdapter(Context context, boolean isEditMode) {
        this.context = context;
        this.isEditMode = isEditMode;
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_user_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final User user = users.get(position);
        if (user.getSexy() == User.MAN) {
            holder.imageUser.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_man));
            holder.textSexyFlag.setText("男生");
            holder.textSexyFlag.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        } else {
            holder.imageUser.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_women));
            holder.textSexyFlag.setText("女生");
            holder.textSexyFlag.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }
        if (isEditMode) {
            holder.checkboxUserItem.setVisibility(View.VISIBLE);
        } else {
            holder.checkboxUserItem.setVisibility(View.GONE);
        }
        holder.textUserName.setText(user.getName());
        if (user.isSelect()){
            holder.checkboxUserItem.setChecked(true);
        }else {
            holder.checkboxUserItem.setChecked(false);
        }
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
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        SmoothCheckBox checkboxUserItem;
        ImageView imageUser;
        TextView textUserName;
        TextView textSexyFlag;

        public ViewHolder(View itemView) {
            super(itemView);
            checkboxUserItem = (SmoothCheckBox) itemView.findViewById(R.id.checkbox_user_item);
            imageUser = (ImageView) itemView.findViewById(R.id.image_user);
            textUserName = (TextView) itemView.findViewById(R.id.text_user_name);
            textSexyFlag = (TextView) itemView.findViewById(R.id.text_sexy_flag);
        }
    }
}
