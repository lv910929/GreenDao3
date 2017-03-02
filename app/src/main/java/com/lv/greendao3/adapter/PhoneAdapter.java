package com.lv.greendao3.adapter;

import android.app.Activity;
import android.content.Context;
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

    public PhoneAdapter(Context context) {
        this.context = context;
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
        holder.btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.doSendSMSTo((Activity) context, phone.getPhoneNumber(), "");
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtil.intentPhoneCall(context, phone.getPhoneNumber());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MyToast.showShortToast("修改电话：" + phone.getPhoneNumber());
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
}
