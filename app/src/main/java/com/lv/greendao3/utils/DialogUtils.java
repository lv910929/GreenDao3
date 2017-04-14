package com.lv.greendao3.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lv.greendao3.R;
import com.lv.greendao3.data.DbManager;
import com.lv.greendao3.model.User;


/**
 * Created by Lv on 2016/4/14.
 */
public class DialogUtils {

    public static final int REQUEST_CAMERA = 110;
    public static final int REQUEST_CHOOSE = 120;

    public static void showInfoDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setTitle(title)
                .setCancelable(true)
                .setPositiveButton("确定", null)
                .show();
    }

    public static void showActionDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("确定", onClickListener)
                .setNegativeButton("取消", null)
                .show();
    }

    public static void showContactDialog(final Context context, final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (user == null) {
            builder.setTitle("新增");
        } else {
            builder.setTitle("修改");
        }
        builder.setView(getView(context, user));
        builder.setCancelable(true);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (validateSignIn(context)) {
                    if (user == null) {//说明是新增
                        DbManager.addUser(userName, userSexy);
                    } else {
                        user.setName(userName);
                        user.setSexy(userSexy);
                        DbManager.updateUserById(user);
                    }
                    IntentUtil.notifyUpdateContacts();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private static TextInputEditText editSignName;
    private static RadioGroup radioGroupSexy;
    private static RadioButton radioButtonMan;
    private static RadioButton radioButtonWoman;

    private static View getView(Context context, User user) {

        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_user_layout, null);
        editSignName = (TextInputEditText) contentView.findViewById(R.id.edit_text_name);
        radioGroupSexy = (RadioGroup) contentView.findViewById(R.id.radio_group_sexy);
        radioButtonMan = (RadioButton) contentView.findViewById(R.id.radio_button_man);
        radioButtonWoman = (RadioButton) contentView.findViewById(R.id.radio_button_woman);

        if (user != null) {
            editSignName.setText(user.getName());
            if (user.getSexy() == User.MAN) {
                radioButtonMan.setChecked(true);
            } else {
                radioButtonWoman.setChecked(true);
            }
        }
        return contentView;
    }

    //报名姓名
    private static String userName;
    //性别
    private static int userSexy;

    private static boolean validateSignIn(Context context) {
        boolean result = true;
        userName = editSignName.getText().toString().trim();
        StringBuilder builder = new StringBuilder();

        if (TextUtils.isEmpty(userName)) {
            builder.append("姓名不能为空\n");
        }
        if (!TextUtils.isEmpty(builder)) {
            result = false;
        }
        if (radioGroupSexy.getCheckedRadioButtonId() == R.id.radio_button_man) {
            userSexy = User.MAN;
        } else {
            userSexy = User.WOMAN;
        }
        return result;
    }

}
