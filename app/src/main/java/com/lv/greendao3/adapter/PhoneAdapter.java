package com.lv.greendao3.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lv.greendao3.R;
import com.lv.greendao3.model.Phone;
import com.lv.greendao3.utils.ActivityUtil;
import com.lv.greendao3.utils.MyToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lv on 2017/3/1.
 */

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder> {

    private final static String[] PHONE_TYPE_LIST = {"中国移动", "中国联通", "中国电信"};
    private final static int[] PHONE_TYPE_COLORS = {R.color.title_blue, R.color.title_green, R.color.title_orange};

    private Context context;
    private List<Phone> phones;

    private EditPhoneListener editPhoneListener;

    public PhoneAdapter(Context context, EditPhoneListener editPhoneListener) {
        this.context = context;
        this.editPhoneListener = editPhoneListener;
        phones = new ArrayList<>();
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_phone_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Phone phone = phones.get(position);
        StringBuilder phoneStringBuilder = new StringBuilder();
        phoneStringBuilder.append(phone.getPhoneNumber().substring(0, 3))
                .append(" ").append(phone.getPhoneNumber().substring(3, 7))
                .append(" ").append(phone.getPhoneNumber().substring(7, 11));
        holder.textPhoneNumber.setText(phoneStringBuilder.toString());
        holder.textPhoneType.setText(PHONE_TYPE_LIST[phone.getPhoneType() - 1]);
        holder.textPhoneType.setTextColor(context.getResources().getColor(PHONE_TYPE_COLORS[phone.getPhoneType() - 1]));
        //发送短信
        holder.btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.doSendSMSTo((Activity) context, phone.getPhoneNumber(), "");
            }
        });
        //拨打电话
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.intentPhoneCall(context, phone.getPhoneNumber());
            }
        });
        //多功能操作
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                showActionDialog(phone);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return phones.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textPhoneNumber;
        TextView textPhoneType;
        ImageView btnSendSms;

        public ViewHolder(View itemView) {
            super(itemView);
            textPhoneNumber = (TextView) itemView.findViewById(R.id.text_phone_number);
            textPhoneType = (TextView) itemView.findViewById(R.id.text_phone_type);
            btnSendSms = (ImageView) itemView.findViewById(R.id.btn_send_sms);
        }
    }

    private void showActionDialog(final Phone phone) {
        new AlertDialog.Builder(context)
                .setTitle(phone.getPhoneNumber())
                .setCancelable(true)
                .setView(getView(phone))
                .show();
    }

    private RecyclerView recyclerAction;

    private View getView(final Phone phone) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_action_layout, null);
        recyclerAction = (RecyclerView) contentView.findViewById(R.id.recycler_action);
        ActionAdapter actionAdapter = new ActionAdapter(context);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        recyclerAction.setLayoutManager(gridLayoutManager);
        recyclerAction.setAdapter(actionAdapter);
        actionAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        editPhoneListener.deletePhone(phone);
                        break;
                    case 1:
                        editPhoneListener.editPhone(phone);
                        break;
                    case 2:
                        ActivityUtil.copyTextToClip(context, phone.getPhoneNumber());
                        MyToast.showShortToast("已复制到剪切板");
                        break;
                }
            }
        });
        return contentView;
    }
}
